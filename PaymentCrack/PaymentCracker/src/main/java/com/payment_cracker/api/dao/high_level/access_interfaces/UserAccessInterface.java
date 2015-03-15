package com.payment_cracker.api.dao.high_level.access_interfaces;

import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.middle_level.middle_entities.UserEntity;
import com.payment_cracker.api.dao.utils.CurrencyTypes;

import java.util.List;

public interface UserAccessInterface {
    public Integer connect(String login, String password) throws DbException;
    public String createUser(String login, String password, String fio, String phoneNumber, String email) throws DbException;
    public String createPurse(CurrencyTypes currencyLabel) throws DbException;
    public void setBan(Long id, Boolean isBan) throws DbException;
    public String setAccountStage(Long id, Boolean accountStage) throws DbException;
    public String makeTransaction(Long idSender, Long idReceiver, Double money) throws DbException;
    public UserEntity getAccountInfo() throws DbException;
    public String createAdministrator(String login, String password, String fio, String phoneNumber, String email) throws DbException;
    public List<String> getMoneyAmountFromPaymentCrackerCreditCard() throws DbException;
    public List<UserEntity> getAllAccounts() throws DbException;
    public List<List<String>> getAllPursesById(Long userId) throws DbException;
}
