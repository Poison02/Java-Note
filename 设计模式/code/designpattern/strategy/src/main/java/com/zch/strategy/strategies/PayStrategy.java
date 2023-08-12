package com.zch.strategy.strategies;

/**
 * 通用的支付接口
 * @author Zch
 * @date 2023/8/12
 **/
public interface PayStrategy {

    boolean pay(int paymentAmount);
    void collectPaymentDetails();

}
