package com.utils.test_work.test_api;


import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.high_level.access_actions.AccessPoint;
import com.payment_cracker.api.dao.middle_level.middle_entities.CurrencyEntity;
import com.payment_cracker.api.dao.middle_level.middle_entities.PurseEntity;
import com.payment_cracker.api.dao.middle_level.middle_entities.TransactionEntity;
import com.payment_cracker.api.dao.utils.CurrencyTypes;
import com.utils.generate_names.EnglishlikeNameGenerator;
import com.utils.generate_names.Gender;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;


public class TestActionClasses2 {
    public static void main(String[] args) throws SQLException, InterruptedException, DbException, ParseException {
        Locale.setDefault(Locale.ENGLISH);
        List<AccessPoint> users = new ArrayList<AccessPoint>();
        EnglishlikeNameGenerator englishlikeNameGenerator = new EnglishlikeNameGenerator();
        //DatabaseUtils.generateDatabase();
        int count_of_users = 2;
        int count_of_operations = 3;
        for (int i = 0; i < count_of_users; i++) {
            users.add(new AccessPoint());
        }
        //users.get(0).generateDatabase();
        for (int i = 0; i < count_of_users; i++) {
            users.get(i).createUser("Abraham" + (i + 1), "Abraham" + (i + 1), englishlikeNameGenerator.generate(Gender.male).toString(), "a" + (i + 1), "fadfasdff@gmail.com");
        }
        for (int i = 0; i < count_of_users; i++) {
            users.get(i).connect("Abraham" + (i + 1), "Abraham" + (i + 1));
        }
        for (int i = 0; i < count_of_users; i++) {
            users.get(i).createPurse(CurrencyTypes.EUR);
        }
        for (int i = 0; i < count_of_users; i++) {
            users.get(i).createPurse(CurrencyTypes.UAH);
        }
        for (int i = 0; i < count_of_users; i++) {
            users.get(i).createPurse(CurrencyTypes.USD);
        }

        Random random = new Random();
        for (int i = 0; i < count_of_users; i++) {
            for(int j = 0; j < count_of_operations; j++) {
                users.get(i).makeTransaction(
                        111111111111l, users.get(i).getPurseByCurrencyLabel(CurrencyTypes.USD).getId(), Double.valueOf(random.nextInt(10)));
                users.get(i).makeTransaction(
                        111111111111l, users.get(i).getPurseByCurrencyLabel(CurrencyTypes.EUR).getId(), Double.valueOf(random.nextInt(10)));
                users.get(i).makeTransaction(
                        111111111111l, users.get(i).getPurseByCurrencyLabel(CurrencyTypes.UAH).getId(), Double.valueOf(random.nextInt(10)));
                users.get(i).makeTransaction(
                        users.get(i).getPurseByCurrencyLabel(CurrencyTypes.USD).getId(), users.get((new Random()).nextInt(count_of_users)).getPurseByCurrencyLabel(CurrencyTypes.USD).getId(), Double.valueOf(random.nextInt(10)));
                users.get(i).makeTransaction(
                        users.get(i).getPurseByCurrencyLabel(CurrencyTypes.EUR).getId(), users.get((new Random()).nextInt(count_of_users)).getPurseByCurrencyLabel(CurrencyTypes.EUR).getId(), Double.valueOf(random.nextInt(10)));
                users.get(i).makeTransaction(
                        users.get(i).getPurseByCurrencyLabel(CurrencyTypes.UAH).getId(), users.get((new Random()).nextInt(count_of_users)).getPurseByCurrencyLabel(CurrencyTypes.UAH).getId(), Double.valueOf(random.nextInt(10)));
                users.get(i).makeTransaction(
                        users.get(i).getPurseByCurrencyLabel(CurrencyTypes.USD).getId(), 111111111111l, Double.valueOf(random.nextInt(10)));
                users.get(i).makeTransaction(
                        users.get(i).getPurseByCurrencyLabel(CurrencyTypes.EUR).getId(), 111111111111l, Double.valueOf(random.nextInt(10)));
                users.get(i).makeTransaction(
                        users.get(i).getPurseByCurrencyLabel(CurrencyTypes.UAH).getId(), 111111111111l, Double.valueOf(random.nextInt(10)));
            }
        }
        for (int i = 0; i < count_of_users; i++) {
            for (Map.Entry<CurrencyEntity, PurseEntity> s : users.get(i).getPursesInfo().entrySet()) {
                System.out.println("Валюта " + s.getKey().getCurrencyLabel() +
                        " баланс " + s.getValue().getBalance() + " id кошелька " + s.getValue().getId());
            }
        }

        for (TransactionEntity transactionEntity: users.get(0).getLastTransactions()) {
            System.out.println(transactionEntity.toString());
        }

//        AccessPoint admin = new AccessPoint();
//        admin.connect("adminadmin", "adminadmin");
//        for (List<String> textList: admin.getAllPursesById(users.get(0).getAccountInfo().getId())) {
//            for (String s : textList) {
//                System.out.print(s + " ");
//            }
//            System.out.println("s");
//        }

        for (int i = 0; i < count_of_users; i++) {
            users.get(i).close();
            users.get(i).finalClose();
        }
    }
}
