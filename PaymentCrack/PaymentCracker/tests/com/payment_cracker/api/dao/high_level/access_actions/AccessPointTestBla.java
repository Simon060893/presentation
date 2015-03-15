package com.payment_cracker.api.dao.high_level.access_actions;

import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.high_level.access_interfaces.UserAccessInterface;
import com.payment_cracker.api.dao.middle_level.middle_entities.UserEntity;
import com.payment_cracker.api.dao.utils.CurrencyTypes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.SQLException;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by Катя on 10.02.2015.
 */
@RunWith(JUnit4.class)
public class AccessPointTestBla {
    private AccessPoint accessPoint;
    private  UserEntity userEntity;

    @Before
    public void init() throws InterruptedException, SQLException, DbException {
        accessPoint = new AccessPoint();
        userEntity= new UserEntity();
    }

    @Test
    public void testConstructor1() {
        assertNotNull(accessPoint);
    }

    @Test
      public void testConstructor2() {
        assertSame(accessPoint.getClass().getInterfaces()[0].getSimpleName().substring(0,3),
               UserAccessInterface.class.getSimpleName().substring(0,3));
    }

    @Test
    public void testConnect1() throws DbException {
        assertTrue(accessPoint.connect("a1", "a1"));
    }
    @Test
    public void testConnect2() throws DbException {
        accessPoint.connect("a1", "a1");
        assertTrue(accessPoint.getUserEntity().getFIO().equals("Vyacheslav Yaris"));
    }

    @Test
    public void testUpdatePursesFromTestConnect1() throws DbException {
        assertTrue(accessPoint.getPurseEntities().size() == 0);
    }
    @Test
    public void testCreate() throws DbException {
        accessPoint.createUser("bomj","bomj","Janukovich V.F.","empty","janik-nehoroshiy");
        userEntity.setLogin("bomj");
        userEntity.setPassword("bomj");
        userEntity.setFIO("Janukovich V.F.");
        userEntity.setEmail("janik-nehoroshiy");
        userEntity.setRegDate(Calendar.getInstance().getTime());
        userEntity.setActive(true);
        accessPoint.connect("bomj","bomj");
        assertSame(accessPoint.getUserEntity(), userEntity);
    }

    @Test
    public void testSetBan() throws DbException {
        accessPoint.connect("a1","a1");
        accessPoint.setBan(275998756153l,true);
       assertTrue(!accessPoint.connect("bomj", "bomj"));
    }

    @Test
    public void testMakeAdministrator() throws DbException {
        accessPoint.connect("a1", "a1");
        accessPoint.makeAdministrator(275998756153l,true);
        accessPoint.connect("bomj", "bomj");
        assertTrue(accessPoint.getUserEntity().isAdministrator());
    }

    @Test
    public void testGetAccountInfo() throws DbException {
        assertNotNull(accessPoint.getAccountInfo());
    }

    @Test
    public void testGetAccountInfoByIdAdmin() throws DbException {
        accessPoint.connect("a1", "a1");
        assertNotNull(accessPoint.getAccountInfoById(275998756153l));
    }
    @Test
    public void testGetAccountInfoByIdUsualUser() throws DbException {
        accessPoint.connect("bomj", "bomj");
        assertNull(accessPoint.getAccountInfoById(275998756153l));
    }

    @Test
    public void testCreatePurse1() throws DbException {
        accessPoint.connect("bomj", "bomj");
        accessPoint.createPurse(CurrencyTypes.EUR);
        assertTrue(accessPoint.getPurseEntities().size() > 0);
    }
    @Test
    public void testCreatePurse2() throws DbException {
        accessPoint.connect("bomj", "bomj");
        accessPoint.createPurse(CurrencyTypes.EUR);
        assertTrue(accessPoint.getPurseEntities().size()==1);
    }

    /*----------------------------CRACKING--------------------------------------------*/

    @Test
    public void testSetBanAdmin() throws DbException {
        accessPoint.connect("a1", "a1");
        accessPoint.setBan(365641454087l,true);
        assertTrue(accessPoint.getUserEntity().isBan());
    }

    @Test
    public void testUserCreateUser() throws DbException {
        accessPoint.connect("a1", "a1");
        accessPoint.createUser("Bomj","bomj","Poroshenko P.A.","gfdsg","215312");
        accessPoint.connect("Bomj", "bomj");
    }

}
