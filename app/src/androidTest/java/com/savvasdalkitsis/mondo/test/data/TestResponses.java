package com.savvasdalkitsis.mondo.test.data;

public class TestResponses {

    public static String balance(int balance, TestCurrency currency) {
        return "{\"balance\": " + balance + ", \"currency\": \"" + currency.apiName() + "\"}";
    }
}