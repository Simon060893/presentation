package com.payment_cracker.api.dao.middle_level.middle_actions;

import com.payment_cracker.api.dao.basic_level.basic_dao.BasicDAO;
import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.middle_level.middle_entities.*;
import com.payment_cracker.api.dao.utils.SessionManager;
import org.hibernate.Session;

public class PaymentServices extends BasicDAO {
    private SessionManager sessionManager;

    protected PaymentServices(SessionManager sessionManager) {
        super(sessionManager);
        this.sessionManager = sessionManager;
    }

    protected void add(UserEntity userEntity, PaymentEntity paymentEntity) throws DbException {
        if (paymentEntity instanceof PurseEntity) {
            PurseServices purseServices = new PurseServices(sessionManager);
            purseServices.add(userEntity, (PurseEntity) paymentEntity);
        } else if (paymentEntity instanceof CreditCardEntity) {
            CreditCardServices creditCardServices = new CreditCardServices(sessionManager);
            creditCardServices.add((CreditCardEntity) paymentEntity);
        }
    }

    protected void update(Long id, PaymentEntity paymentEntity) throws DbException {
        if (paymentEntity instanceof PurseEntity) {
            PurseServices purseServices = new PurseServices(sessionManager);
            purseServices.update(id, (PurseEntity) paymentEntity);
        } else if (paymentEntity instanceof CreditCardEntity) {
            CreditCardServices creditCardServices = new CreditCardServices(sessionManager);
            creditCardServices.update(id, (CreditCardEntity) paymentEntity);
        }
    }

    protected PaymentEntity getById(Long id) throws DbException {
        PaymentEntity paymentEntity;
        try {
            PurseServices purseServices = new PurseServices(sessionManager);
            paymentEntity = purseServices.getById(id);
        } catch (Exception e) {
            try {
                CreditCardServices creditCardServices = new CreditCardServices(sessionManager);
                paymentEntity = creditCardServices.getById(id);
            } catch (DbException e1) {
                logger.error(e.getMessage(), e);
                throw new DbException();
            }
        }
        return paymentEntity;
    }

    public void saveTransactionResult(TransactionEntity transactionEntity) throws DbException {
        PurseServices purseServices = new PurseServices(sessionManager);
        purseServices.saveTransactionResult(transactionEntity);
    }
}