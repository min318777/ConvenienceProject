package com.zerobase.convpay.service;

import com.zerobase.convpay.dto.PayCancelRequest;
import com.zerobase.convpay.dto.PayCancelResponse;
import com.zerobase.convpay.dto.PayRequest;
import com.zerobase.convpay.dto.PayResponse;
import com.zerobase.convpay.type.*;

public class ConveniencePayService {
    private final MoneyAdapter moneyAdapter = new MoneyAdapter();
    private final CardAdapter cardAdapter = new CardAdapter();
    private final DiscountInterface discountInterface = new DiscountByPayMethod();
    //private final DiscountInterface discountInterface = new DiscountByConvenience();

    public PayResponse pay(PayRequest payRequest) {
        PaymentInerface paymentInerface;

        if(payRequest.getPayMethodType() == PayMethodType.CARD){
            paymentInerface = cardAdapter;
        }else{
            paymentInerface = moneyAdapter;
        }

        // fail test

        // Method()

        // Exception case5
        // Exception case4

        // Exception case1
        // Exception case2
        // Exception case3

        // Success Case(only one)
        Integer discountedAmount = discountInterface.getDiscountedAmount(payRequest);
        PaymentResult payment = paymentInerface.payment(discountedAmount);


        if (payment == PaymentResult.PAYMENT_FAIL){
            return new PayResponse(PayResult.FAIL, 0);

        }

        // Success case
        return new PayResponse(PayResult.SUCCESS, discountedAmount);

    }

    public PayCancelResponse payCancel(PayCancelRequest payCancelRequest) {
        PaymentInerface paymentInerface;

        if(payCancelRequest.getPayMethodType() == PayMethodType.CARD){
            paymentInerface = cardAdapter;
        }else{
            paymentInerface = moneyAdapter;
        }

        CancelPaymentResult cancelPaymentResult = paymentInerface.cancelPayment(payCancelRequest.getPayCancelAmount());


        if (cancelPaymentResult == CancelPaymentResult.CANCEL_PAYMENT_FAIL) {
            return new PayCancelResponse(PayCancelResult.PAY_CANCEL_FAIL, 0);
        }

        // Success Case
        return new PayCancelResponse(PayCancelResult.PAY_CANCEL_SUCCESS,
                payCancelRequest.getPayCancelAmount());
    }
}
