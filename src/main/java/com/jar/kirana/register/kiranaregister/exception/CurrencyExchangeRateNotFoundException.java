package com.jar.kirana.register.kiranaregister.exception;

public class CurrencyExchangeRateNotFoundException extends RuntimeException {

    public CurrencyExchangeRateNotFoundException(String message) {
        super(message);
    }

    public CurrencyExchangeRateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
