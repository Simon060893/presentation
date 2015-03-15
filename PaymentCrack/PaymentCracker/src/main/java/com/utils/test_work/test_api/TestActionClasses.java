package com.utils.test_work.test_api;

import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.high_level.access_actions.AccessPoint;

import java.sql.SQLException;


public class TestActionClasses {
    public static void main(String[] args) throws InterruptedException, SQLException, DbException {
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.connect("a1", "a1");
        accessPoint.close();
        accessPoint.connect("a1", "a1");
        accessPoint.finalClose();
    }
}
