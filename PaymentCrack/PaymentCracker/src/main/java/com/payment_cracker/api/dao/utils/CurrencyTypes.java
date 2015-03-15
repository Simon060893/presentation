package com.payment_cracker.api.dao.utils;


public enum  CurrencyTypes {
    UAH("UAH"),
    USD("USD"),
    EUR("EUR");
    public String currencyName;
    CurrencyTypes(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }
}
