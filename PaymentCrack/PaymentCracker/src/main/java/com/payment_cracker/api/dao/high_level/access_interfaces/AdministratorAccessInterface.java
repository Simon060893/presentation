package com.payment_cracker.api.dao.high_level.access_interfaces;

import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.middle_level.middle_entities.UserEntity;

import java.util.List;


public interface AdministratorAccessInterface extends ClientAccessInterface {
    public void setBan(Long id, boolean banStage) throws DbException;
    public UserEntity getAccountInfo(Long id) throws DbException;
    public List<UserEntity> getAllAccounts() throws DbException;
    public List<List<String>> getAllPursesById(Long userId) throws DbException;
    public String createAdministrator(String login, String password, String fio, String phoneNumber, String email) throws DbException;
    public List<String> getMoneyAmountFromPaymentCrackerCreditCard() throws DbException;
}
