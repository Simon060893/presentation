package com.utils.test_work.test_api;

import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.high_level.access_actions.AccessPoint;
import com.payment_cracker.api.dao.utils.CurrencyTypes;
import com.utils.generate_names.EnglishlikeNameGenerator;
import com.utils.generate_names.Gender;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class TestAccessClassesThreads {

    public static void main(String[] args) throws SQLException, InterruptedException, DbException {
        Locale.setDefault(Locale.ENGLISH);
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<AccessPoint> users = new ArrayList<AccessPoint>();
        EnglishlikeNameGenerator englishlikeNameGenerator = new EnglishlikeNameGenerator();
        //DatabaseUtils.generateDatabase();
        int count_of_users = 10;
        //users.get(0).generateDatabase();
        Random random = new Random();
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < count_of_users; i++) {
            futures.add(executorService.submit(new Runnable() {
                private int i;

                public Runnable setI(int i) {
                    this.i = i;
                    return this;
                }

                @Override
                public void run() {
                    try {
                        users.add(new AccessPoint());
                        users.get(i).createUser("a" + (i + 1), "a" + (i + 1), englishlikeNameGenerator.generate(Gender.male).toString(), "a" + (i + 1), "a" + (i + 1));
                        users.get(i).connect("a" + (i + 1), "a" + (i + 1));
                        users.get(i).createPurse(CurrencyTypes.EUR);
                        users.get(i).createPurse(CurrencyTypes.UAH);
                        users.get(i).createPurse(CurrencyTypes.USD);
                        users.get(i).makeTransaction(
                                1l, users.get(i).getPurseByCurrencyLabel(CurrencyTypes.USD).getId(), Double.valueOf(random.nextInt(10)));
                        users.get(i).makeTransaction(
                                1l, users.get(i).getPurseByCurrencyLabel(CurrencyTypes.EUR).getId(), Double.valueOf(random.nextInt(10)));
                        users.get(i).makeTransaction(
                                1l, users.get(i).getPurseByCurrencyLabel(CurrencyTypes.UAH).getId(), Double.valueOf(random.nextInt(10)));
                        users.get(i).makeTransaction(
                                users.get(i).getPurseByCurrencyLabel(CurrencyTypes.USD).getId(), users.get((new Random()).nextInt(count_of_users)).getPurseByCurrencyLabel(CurrencyTypes.USD).getId(), Double.valueOf(random.nextInt(10)));
                        users.get(i).makeTransaction(
                                users.get(i).getPurseByCurrencyLabel(CurrencyTypes.EUR).getId(), users.get((new Random()).nextInt(count_of_users)).getPurseByCurrencyLabel(CurrencyTypes.EUR).getId(), Double.valueOf(random.nextInt(10)));
                        users.get(i).makeTransaction(
                                users.get(i).getPurseByCurrencyLabel(CurrencyTypes.UAH).getId(), users.get((new Random()).nextInt(count_of_users)).getPurseByCurrencyLabel(CurrencyTypes.UAH).getId(), Double.valueOf(random.nextInt(10)));
                        users.get(i).makeTransaction(
                                users.get(i).getPurseByCurrencyLabel(CurrencyTypes.USD).getId(), 1l, Double.valueOf(random.nextInt(10)));
                        users.get(i).makeTransaction(
                                users.get(i).getPurseByCurrencyLabel(CurrencyTypes.EUR).getId(), 1l, Double.valueOf(random.nextInt(10)));
                        users.get(i).makeTransaction(
                                users.get(i).getPurseByCurrencyLabel(CurrencyTypes.UAH).getId(), 1l, Double.valueOf(random.nextInt(10)));
                        users.get(i).close();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }.setI(i)));
        }
        for (int i = 0; i < count_of_users; i++) {
            if(futures.get(i).isDone() && (i == count_of_users - 1)) {
                executorService.shutdown();
            }
        }
//        for (int i = 0; i < count_of_users; i++) {
//            for (String s : users.get(i).getPursesInfo()) {
//                System.out.println(s);
//            }
//        }
    }
}
