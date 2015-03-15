package com.payment_cracker.api.dao.middle_level.middle_actions;

import com.payment_cracker.api.dao.middle_level.middle_actions.PurseActions;
import com.payment_cracker.api.dao.middle_level.middle_entities.PurseEntity;
import com.payment_cracker.api.dao.middle_level.middle_entities.TransactionEntity;
import com.payment_cracker.api.dao.middle_level.middle_entities.UserEntity;
import com.payment_cracker.api.dao.utils.DAOUtils;
import com.payment_cracker.api.dao.utils.SessionManager;
import junit.framework.TestCase;
import org.hibernate.Session;

/**
 * Created by Alex on 09.02.2015.
 */

public class PurseActionsTest extends TestCase {
    private SessionManager sm = new SessionManager();
    private Session session = sm.startSession();
    private PurseActions purseActions = new PurseActions(sm);

    public void testAddGetById() throws Exception {

        UserEntity user = new UserEntity();
        user.setId(847849936166l);

        PurseEntity purse1 = new PurseEntity();

        purse1.setId(DAOUtils.generateId());
        purse1.setBalance(1234567);
        purse1.setPurseName("Purse1");
        purse1.setCurrencyId(885836395290l);

        purseActions.add(user, purse1);

        PurseEntity purse2 = purseActions.getById(purse1.getId());
        assertEquals(purse1, purse2);

    }

    public void testUpdatePurse() throws Exception {

        PurseEntity purse1 = purseActions.getById(926274279477l);

        //purse1.setCurrencyId(494646524224l);
        purse1.setBalance(1000);

        purseActions.update(926274279477l, purse1);

        PurseEntity purse2 = purseActions.getById(926274279477l);

        assertEquals(purse1, purse2);
    }

    public void testMakeTransaction() throws Exception {

        PurseEntity sender1 = purseActions.getById(926274279477l);
        PurseEntity receiver1 = purseActions.getById(973487884891l);

        TransactionEntity<PurseEntity, PurseEntity> transaction = new TransactionEntity();
        transaction.setId(DAOUtils.generateId());
        transaction.setSender(sender1);
        transaction.setReceiver(receiver1);
        transaction.setMoney(1.0);

        purseActions.makeTransactionFromPurseToPurse(transaction);

        PurseEntity sender2 = purseActions.getById(926274279477l);
        PurseEntity receiver2 = purseActions.getById(973487884891l);

        assertEquals(sender1, sender2);
        assertEquals(receiver1, receiver2);
    }
}
