package com.zerobase.convpay.service;

import com.zerobase.convpay.dto.PayCancelRequest;
import com.zerobase.convpay.dto.PayCancelResponse;
import com.zerobase.convpay.dto.PayRequest;
import com.zerobase.convpay.dto.PayResponse;
import com.zerobase.convpay.type.*;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConveniencePayService {
    //private final MoneyAdapter moneyAdapter = new MoneyAdapter();
    //private final CardAdapter cardAdapter = new CardAdapter();        구현체를 사용하지 않도록 해야한다. 왜? 단일책임의 원칙을 지키기위해 클래스는 본연의 임무만을 수행해야한다.
    private final DiscountInterface discountInterface;
    private final Map<PayMethodType, PaymentInterface> paymentInterfaceMap =
            new HashMap<>();
    //private final DiscountInterface discountInterface = new DiscountByConvenience();


    public ConveniencePayService(Set<PaymentInterface> paymentInterfaceSet,
                                 DiscountInterface discountInterface) {

        paymentInterfaceSet.forEach(
                paymentInterface -> paymentInterfaceMap.put(
                        paymentInterface.getPayMethodType(),
                        paymentInterface
                )
        );
        this.discountInterface = discountInterface;
    }

    public PayResponse pay(PayRequest payRequest) {
        PaymentInterface paymentInterface = paymentInterfaceMap.get(payRequest.getPayMethodType());

        /*
        if(payRequest.getPayMethodType() == PayMethodType.CARD){
            paymentInterface = cardAdapter;
        }else{
            paymentInterface = moneyAdapter;
        }
        */

        // fail test

        // Method()

        // Exception case5
        // Exception case4

        // Exception case1
        // Exception case2
        // Exception case3

        // Success Case(only one)
        Integer discountedAmount = discountInterface.getDiscountedAmount(payRequest);
        PaymentResult payment = paymentInterface.payment(discountedAmount);


        if (payment == PaymentResult.PAYMENT_FAIL){
            return new PayResponse(PayResult.FAIL, 0);

        }

        // Success case
        return new PayResponse(PayResult.SUCCESS, discountedAmount);

    }

    public PayCancelResponse payCancel(PayCancelRequest payCancelRequest) {
        PaymentInterface paymentInterface =
                paymentInterfaceMap.get(payCancelRequest.getPayMethodType());


        CancelPaymentResult cancelPaymentResult =
                paymentInterface.cancelPayment(payCancelRequest.getPayCancelAmount());


        if (cancelPaymentResult == CancelPaymentResult.CANCEL_PAYMENT_FAIL) {
            return new PayCancelResponse(PayCancelResult.PAY_CANCEL_FAIL, 0);
        }

        // Success Case
        return new PayCancelResponse(PayCancelResult.PAY_CANCEL_SUCCESS,
                payCancelRequest.getPayCancelAmount());
    }
}
