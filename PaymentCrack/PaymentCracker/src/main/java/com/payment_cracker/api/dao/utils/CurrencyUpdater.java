package com.payment_cracker.api.dao.utils;

import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.middle_level.middle_actions.CurrencyServices;


public class CurrencyUpdater extends CurrencyServices implements Runnable {

    public CurrencyUpdater(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(43200000);
            updateAllCurrencyByYahooAPI();
        } catch (DbException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
