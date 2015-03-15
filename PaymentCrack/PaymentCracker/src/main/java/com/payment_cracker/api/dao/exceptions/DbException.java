package com.payment_cracker.api.dao.exceptions;

public class DbException extends Exception {
    public DbException(){
        super();
    }
    public DbException(Exception e){
        super(e);
    }
}
