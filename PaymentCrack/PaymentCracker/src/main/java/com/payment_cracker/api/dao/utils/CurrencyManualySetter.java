package com.payment_cracker.api.dao.utils;

import com.payment_cracker.api.dao.exceptions.DbException;


public class CurrencyManualySetter {
    public static void main(String[] args) throws DbException {
        SetCurrencyManualy setCurrencyManualy = new SetCurrencyManualy();
        setCurrencyManualy.setCarrencyManualy();
    }
}
