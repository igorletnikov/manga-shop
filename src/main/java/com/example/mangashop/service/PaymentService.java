package com.example.mangashop.service;

import com.example.mangashop.model.DataTransferObject.ChargeRequest;
import com.stripe.exception.*;
import com.stripe.model.Charge;

public interface PaymentService {
    Charge pay(ChargeRequest chargeRequest) throws CardException, ApiException, AuthenticationException, InvalidRequestException, ApiConnectionException;
}
