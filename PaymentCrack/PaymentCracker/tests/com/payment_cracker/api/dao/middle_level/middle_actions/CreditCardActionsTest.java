package com.payment_cracker.api.dao.middle_level.middle_actions;

import com.payment_cracker.api.dao.basic_level.basic_entities.Objects;
import com.payment_cracker.api.dao.basic_level.basic_entities.Parameters;
import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.middle_level.middle_entities.CreditCardEntity;
import com.payment_cracker.api.dao.utils.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Катя on 09.02.2015.
 */
@RunWith(JUnit4.class)
public class CreditCardActionsTest {
    private SessionManager session;
    private CreditCardActions creditCardActions;
    private CreditCardEntity creditCardEntity;
    private CreditCardEntity updateCreditCardEntity;
    private List<CreditCardEntity> listCreditCardEntityList;
    private Objects creditCardEntityObj;

    @Before
    public void init() {
        session = new SessionManager();
        creditCardActions = new CreditCardActions(session);//try to take null
        creditCardEntity = new CreditCardEntity(201d, 1l);
        updateCreditCardEntity = new CreditCardEntity(10.95d, 1l);
    }

    @Test
    public void testSessionManager() {
        session.closeSession();//проверка на null
        assertNotNull(session.getSession().isOpen());
    }

    @Test
    public void testConstructor() {
        assertNotNull(creditCardActions);
    }

    @Test
    public void testAdd0() throws DbException {//хер знает что проверять, дофига дкйствий в одном методе
        creditCardActions.add(creditCardEntity);
        creditCardEntityObj = creditCardActions.getObjectsDAO().getById(1l);
        assertNotNull(creditCardEntityObj);
    }

    @Test
    public void testAdd1() throws DbException {
        for (Parameters a : creditCardActions.getParametersDAO().getParametersList(creditCardEntityObj)) {
            assertNotNull(a.getValue());
        }
    }

    @Test
    public void testGetById1() throws DbException {
        assertSame(creditCardEntity.getBalance(), creditCardActions.getById(1l).getBalance());
    }

    @Test
    public void testGetById2() throws DbException {
        assertSame(creditCardEntity.getCurrencyId(), creditCardActions.getById(1l).getCurrencyId());
    }

    @Test
    public void testGetById3() throws DbException {
        assertSame(creditCardEntity, creditCardActions.getById(1l));
    }

    @Test
    public void testUpdate1() throws DbException, SQLException {
        creditCardActions.update(1l, updateCreditCardEntity);
        assertSame(creditCardEntity.getBalance(), updateCreditCardEntity.getBalance());
    }

    @Test
    public void testUpdate2() throws DbException, SQLException {
        assertSame(creditCardEntity, creditCardActions.getById(1l));
    }

    @Test
    public void testGetAll1() throws DbException, SQLException {
        listCreditCardEntityList = creditCardActions.getAll();
        assertTrue(listCreditCardEntityList.size() > 0);
    }

    @Test
    public void testGetAll2() throws DbException, SQLException {
        //in bd there is only one creditCardEntity
        assertSame(listCreditCardEntityList.get(0).getBalance(), creditCardEntity.getBalance());
    }
}
