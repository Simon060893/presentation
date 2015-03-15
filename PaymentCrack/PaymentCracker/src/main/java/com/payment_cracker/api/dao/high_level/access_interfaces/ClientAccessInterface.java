package com.payment_cracker.api.dao.high_level.access_interfaces;

import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.middle_level.middle_entities.UserEntity;


public interface ClientAccessInterface {
    public UserEntity getAccountInfo() throws DbException;
    public String createPurse(UserEntity userEntity, String currencyLabel) throws DbException;
    public String makeTransaction(Long sender, Long receiver, Double money) throws DbException;
    public String setAccountStage(Long id, boolean accountStage) throws DbException;
}
