package com.payment_cracker.api.dao.middle_level.middle_entities;


public interface PaymentEntity extends Entity {
    public Long getId();
    public void setId(Long id);
    public Long getCurrencyId();
    public void setCurrencyId(Long currencyId);
    public double getBalance();
    public void setBalance(double balance);
}
