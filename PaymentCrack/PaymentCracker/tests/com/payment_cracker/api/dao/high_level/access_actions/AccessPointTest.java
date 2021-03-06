package com.payment_cracker.api.dao.high_level.access_actions;

import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.utils.CurrencyTypes;
import com.payment_cracker.api.dao.utils.SessionManager;
import org.hibernate.type.CurrencyType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.SQLException;
import java.util.Locale;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Natalie on 12.02.2015.
 */
@RunWith(JUnit4.class)
public class AccessPointTest {
    private AccessPoint accessPoint;

    @Before
    public void init() throws InterruptedException, SQLException, DbException {
        Locale.setDefault(Locale.ENGLISH);
        accessPoint = new AccessPoint();
    }

    @Test
    public void testConnect() throws Exception {
        testCreateUser();
        assertTrue(accessPoint.connect("admin", "admin"));
    }

    @Test
    public void testCreateUser() throws Exception {
        accessPoint.createUser("c2", "c2", "ABC", "my@email.com", "380671111111");
    }

    @Test
    public void testSetBan() throws Exception {
        testConnect();
        accessPoint.setBan(accessPoint.getAccountInfo().getId(), true);
        accessPoint.close();
    }

    @Test
    public void testSetAccountStage() throws Exception {
        testConnect();
        accessPoint.setAccountStage(accessPoint.getAccountInfo().getId(), true);
        accessPoint.close();
    }

    @Test
    public void testMakeAdministrator() throws Exception {
        testConnect();
        accessPoint.makeAdministrator(accessPoint.getAccountInfo().getId(), true);
        accessPoint.close();
    }

    @Test
    public void testGetAccountInfo() throws Exception {
        testConnect();
        accessPoint.getAccountInfo();
        accessPoint.close();
    }

    @Test
    public void testGetAccountInfoById() throws Exception {
        accessPoint.getAccountInfo();
        testConnect();
        accessPoint.getAccountInfo(accessPoint.getAccountInfo().getId());
        accessPoint.close();
    }

    @Test
    public void testCreatePurse() throws Exception {
        testConnect();
        accessPoint.createPurse(CurrencyTypes.EUR);
    }

    @Test
    public void testGetPursesInfo() throws Exception {
        testConnect();
        accessPoint.getPursesInfo();
    }

    @Test
    public void testMakeTransactionFromPurseToPurse() throws Exception {
        testPutMoneyOnPurseFromCreditCard();
        accessPoint.makeTransactionFromPurseToPurse(accessPoint.getPurseByCurrencyLabel(CurrencyTypes.EUR).getId(), accessPoint.getPurseByCurrencyLabel(CurrencyTypes.EUR).getId(), 1D);
    }

    @Test
    public void testPutMoneyOnPurseFromCreditCard() throws Exception {
        testCreatePurse();
        accessPoint.putMoneyOnPurseFromCreditCard(1L, accessPoint.getPurseByCurrencyLabel(CurrencyTypes.EUR).getId(), 100D);
    }

    @Test
    public void testPutMoneyOnCreditCardFromPurse() throws Exception {
        testMakeTransactionFromPurseToPurse();
        accessPoint.putMoneyOnCreditCardFromPurse(accessPoint.getPurseByCurrencyLabel(CurrencyTypes.EUR).getId(), 1L, 1D);
    }

    @Test
    public void testUpdateCurrencies() throws Exception {
        testConnect();
        accessPoint.updateCurrencies();
        accessPoint.close();
    }

    @Test
    public void testGetPurseByCurrencyLabel() throws Exception {
        testConnect();
        accessPoint.getPurseByCurrencyLabel(CurrencyTypes.EUR);
        accessPoint.close();
    }
}
