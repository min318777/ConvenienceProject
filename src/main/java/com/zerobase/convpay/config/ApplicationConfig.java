package com.zerobase.convpay.config;

import com.zerobase.convpay.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class ApplicationConfig {


    @Bean
    public ConveniencePayService conveniencePayService(){
        return new ConveniencePayService(
                new HashSet<>(
                        Arrays.asList(getMoneyAdapter(), getCardAdapter())
                ),
                discountByConvenience()
        );
    }

    @Bean
    private static DiscountByConvenience discountByConvenience() {
        return new DiscountByConvenience();
    }

    @Bean
    public static CardAdapter getCardAdapter() {
        return new CardAdapter();
    }


    @Bean
    public static MoneyAdapter getMoneyAdapter() {
        return new MoneyAdapter();
    }

    public ConveniencePayService conveniencePayServiceDiscountPayMethod(){
        return new ConveniencePayService(
                new HashSet<>(
                        Arrays.asList(new MoneyAdapter(), getCardAdapter())
                ),
                new DiscountByPayMethod()
        );
    }
}
