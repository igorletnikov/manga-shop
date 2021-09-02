package com.example.mangashop.service.impl;

import com.example.mangashop.model.DataTransferObject.ChargeRequest;
import com.example.mangashop.model.Product;
import com.example.mangashop.model.ShoppingCart;
import com.example.mangashop.model.User;
import com.example.mangashop.model.enumerations.CartStatus;
import com.example.mangashop.model.exceptions.*;
import com.example.mangashop.repository.ShoppingCartRepository;
import com.example.mangashop.service.PaymentService;
import com.example.mangashop.service.ProductService;
import com.example.mangashop.service.ShoppingCartService;
import com.example.mangashop.service.UserService;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {


    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final ProductService productService;
    private PaymentService paymentService;


    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserService userService, ProductService productService, PaymentService paymentService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userService = userService;
        this.productService = productService;
        this.paymentService = paymentService;
    }



    @Override
    public ShoppingCart findActiveShoppingCartByUsername(String userId) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED).orElseThrow(
                () -> new ShoppingCartIsNotActiveException()
        );
    }

    @Override
    public ShoppingCart createShoppingCart(String userId) {
        User user = this.userService.findById(userId);
        if(this.shoppingCartRepository.existsByUserUsernameAndStatus(userId, CartStatus.CREATED)){
            throw new ActiveShoppingCartAlreadyExist();
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart addProductToShoppingCart(String userId, Long productId) throws ProductAlreadyInShoppingCartException {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        Product product= this.productService.findById(productId);
        for (Product p : shoppingCart.getProducts()) {
            if (p.getId().equals(productId)) {
                throw new ProductAlreadyInShoppingCartException(product.getName());
            }
        }
//        if(shoppingCart.getProducts() == null){
//            shoppingCart.setProducts(new ArrayList<>());
//        }
        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeProductFromShoppingCart(String userId, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        shoppingCart.setProducts(
                shoppingCart.getProducts()
                        .stream()
                        .filter(item -> !item.getId().equals(productId))
                        .collect(Collectors.toList())
        );
        return this.shoppingCartRepository.save(shoppingCart);


    }



    @Override
    public ShoppingCart getActiveShoppingCart(String userId) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(
                userId,CartStatus.CREATED
        ).orElseGet(
                () -> {
                    ShoppingCart shoppingCart= new ShoppingCart();
                    User user =this.userService.findById(userId);
                    shoppingCart.setUser(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                }
        );
    }

    @Override
    public ShoppingCart cancelActiveShoppingCart(String userId){
        ShoppingCart shoppingCart=this.shoppingCartRepository.findByUserUsernameAndStatus
                (userId,CartStatus.CREATED).orElseThrow(
                () -> new ShoppingCartIsNotActiveException()
        );

        shoppingCart.setStatus(CartStatus.CANCELED);

        return this.shoppingCartRepository.save(shoppingCart);

    }

    @Override
    @Transactional
    public ShoppingCart checkoutShoppingCart(String userId, ChargeRequest chargeRequest) throws TransactionFailedException {

        ShoppingCart shoppingCart=this.shoppingCartRepository.findByUserUsernameAndStatus(userId,CartStatus.CREATED).orElseThrow(
                () -> new ShoppingCartIsNotActiveException()
        );
        if (shoppingCart == null) {
            throw new ShoppingCartIsNotActiveException();
        }

        float price = 0;
        List<Product> products = shoppingCart.getProducts();

        for (Product product : products) {
            if (product.getQuantity() <= 0) {
                throw new ProductOutOfStockException(product.getId());
            }
            product.setQuantity(product.getQuantity() -1);
            price += product.getPrice();
        }

        Charge charge=null;
        try{
            charge=this.paymentService.pay(chargeRequest);
        }catch (CardException | ApiException | AuthenticationException | ApiConnectionException | InvalidRequestException e){
            throw new TransactionFailedException(userId, e.getMessage());
        }
        shoppingCart.setProducts(products);
        shoppingCart.setStatus(CartStatus.FINISHED);
        return this.shoppingCartRepository.save(shoppingCart);
    }

}

