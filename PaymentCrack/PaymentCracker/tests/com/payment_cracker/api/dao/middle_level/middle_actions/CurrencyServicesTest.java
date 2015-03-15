package com.payment_cracker.api.dao.middle_level.middle_actions;

import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.middle_level.middle_entities.CurrencyEntity;
import com.payment_cracker.api.dao.utils.DAOUtils;
import com.payment_cracker.api.dao.utils.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Natalie on 09.02.2015.
 */

@RunWith(JUnit4.class)
public class CurrencyServicesTest {
private CurrencyServices currencyActions;
private SessionManager sessionManager;
private  Date date = new Date();
private Long id = DAOUtils.generateId();

    @Before
    public void init() throws InterruptedException, SQLException, DbException {
        Locale.setDefault(Locale.ENGLISH);
        sessionManager = new SessionManager();
        sessionManager.startSession();
        currencyActions = new CurrencyServices(sessionManager);
    }

    @Test
    public void testAdd() throws Exception {
        sessionManager.startTransaction();

        CurrencyEntity currencyEntity = new CurrencyEntity();
        currencyEntity.setCurrencyDate(date);
        currencyEntity.setCurrencyId(id);
        currencyEntity.setCurrencyLabel("UAH");
        currencyEntity.setCurrencyValue(0.04);
        currencyActions.add(currencyEntity);
        CurrencyEntity ce = currencyActions.getById(id);
        assertEquals(currencyEntity.getCurrencyId(), ce.getCurrencyId());
        assertEquals(currencyEntity.getCurrencyLabel(), ce.getCurrencyLabel());
        assertEquals(currencyEntity.getCurrencyValue(), ce.getCurrencyValue());
        assertEquals(currencyEntity.getCurrencyDate(), ce.getCurrencyDate());
        sessionManager.commitTransaction();
    }

    @Test
    public void testUpdate() throws Exception {
        testAdd();
        sessionManager.startTransaction();
        CurrencyEntity currencyEntity2 = new CurrencyEntity();
        currencyEntity2.setCurrencyDate(date);
        currencyEntity2.setCurrencyId(id);
        currencyEntity2.setCurrencyLabel("EUR");
        currencyEntity2.setCurrencyValue(1.01);
        currencyActions.update(id, currencyEntity2);
        CurrencyEntity ce = currencyActions.getById(id);
        assertEquals(currencyEntity2.getCurrencyId(), ce.getCurrencyId());
        assertEquals(currencyEntity2.getCurrencyLabel(), ce.getCurrencyLabel());
        assertEquals(currencyEntity2.getCurrencyValue(), ce.getCurrencyValue());
        assertEquals(currencyEntity2.getCurrencyDate(), ce.getCurrencyDate());
        sessionManager.commitTransaction();
    }

    @Test
    public void testGetById() throws Exception {
        testAdd();
        sessionManager.startTransaction();
        currencyActions.getById(id);
        sessionManager.commitTransaction();
    }

    @Test
    public void testUpdateAllCurrencyByYahooAPI() throws Exception {
        sessionManager.startTransaction();
        currencyActions.updateAllCurrencyByYahooAPI();
        sessionManager.commitTransaction();
    }

    @Test
    public void testGetAll() throws Exception {
        sessionManager.startTransaction();
        currencyActions.getAll();
        sessionManager.commitTransaction();
    }
}
