package com.payment_cracker.api.dao.high_level.access_actions;


import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.high_level.access_interfaces.UserAccess;
import com.payment_cracker.api.dao.high_level.access_interfaces.UserAccessInterface;
import com.payment_cracker.api.dao.middle_level.middle_actions.CurrencyServices;
import com.payment_cracker.api.dao.middle_level.middle_actions.Services;
import com.payment_cracker.api.dao.middle_level.middle_entities.*;
import com.payment_cracker.api.dao.utils.*;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class AccessPoint extends Services implements UserAccessInterface {
    private UserEntity userEntity;
    private List<PurseEntity> purseEntities = new ArrayList<PurseEntity>();
    private UserAccess userAccess;
    private SessionManager sessionManager;
    private Properties properties;
    private final Long systemCreditCard = 222222222222l;
    private static final Logger logger = Logger.getLogger("payment");

    public AccessPoint() throws InterruptedException, DbException {
        super(new SessionManager());
        Locale.setDefault(Locale.ENGLISH);
        this.sessionManager = getSessionManager();
    }

    private void setNewSessionManager() {
        sessionManager = new SessionManager();
        setSessionManager(sessionManager);
    }

    public AccessPoint(Properties properties) throws InterruptedException, DbException {
        super(new SessionManager());
        this.properties = properties;
        Locale.setDefault(Locale.ENGLISH);
        this.sessionManager = getSessionManager();
    }

    @Override
    public Integer connect(String login, String password) throws DbException {
        sessionManager.startSession();
        String hashedPassword = DAOUtils.encryptString(password);
        if (sessionManager == null) {
            setNewSessionManager();
        }
        Integer flag = 0;
        ExecutionTimeController.startCount();
        Long userId = userActionsClass.checkIsUserUnique(login, hashedPassword);
        if (userId == -1) {
            logger.debug("User does not exist!");
            flag = -1;
            sessionManager.closeSession();
            return flag;
        } else if (userId == -2) {
            logger.debug("Password is wrong!");
            flag = -1;
            sessionManager.closeSession();
            return flag;
        } else {
            this.userEntity = userActionsClass.getById(userId);
            if (userEntity.isActive()) {
                if (userEntity.isAdministrator()) {
                    userAccess = new AdministratorAccess(sessionManager);
                    updatePurses();
                    flag = 2;
                } else {
                    userAccess = new ClientAccess(sessionManager);
                    updatePurses();
                    flag = 1;
                }
                ExecutionTimeController.endCount();
                logger.debug("Connect " + ExecutionTimeController.getInfo());
            }
        }
        sessionManager.closeSession();
        return flag;
    }

    public List<TransactionEntity> getLastTransactions() throws DbException{
        sessionManager.startSession();
        List<TransactionEntity> allTransactions = purseActionsClass.getLastTransactions(userEntity.getId());
        sessionManager.closeSession();
        return allTransactions;
    }

    private void updatePurses() throws DbException {
        if (userEntity != null) {
            purseEntities.clear();
            for (CurrencyTypes currencyTypes : CurrencyTypes.values()) {
                Long purseId = purseActionsClass.getAndCheckPurseByCurrencyType(
                        currencyTypes.getCurrencyName(), userEntity.getId());
                if (purseId == -1) {

                } else {
                    this.purseEntities.add(purseActionsClass.getById(purseId));
                }
            }
        }
    }

    @Override
    public String createUser(String login, String password, String fio, String phoneNumber, String email) throws DbException {
        sessionManager.startSession();
        sessionManager.startTransaction();
        String hashedPassword = DAOUtils.encryptString(password);
        ExecutionTimeController.startCount();
        Long userId = userActionsClass.checkIsUserUnique(login, hashedPassword);
        if (userId == -1) {
            UserEntity tempUserEntity = new UserEntity(login, hashedPassword, fio, phoneNumber, email,
                    Calendar.getInstance().getTime(), true, false, false);
            tempUserEntity.setId(DAOUtils.generateId());
            userActionsClass.add(tempUserEntity);
            sessionManager.commitTransaction();

            Thread send  = new Thread(new MessageSender(createMessage(
                    MessageTypes.REGISTRATION_USER.generateMessage(tempUserEntity.getFIO()),
                    tempUserEntity,
                    MessageTypes.REGISTRATION_USER.getTypeId())));
            send.start();

            ExecutionTimeController.endCount();

            logger.debug("User creation " + ExecutionTimeController.getInfo());
            sessionManager.closeSession();
            return "User is created " + userId;

        } else if (userId == -2) {
            logger.debug("Such login already exists!");
            sessionManager.rollbackTransaction();
            sessionManager.closeSession();
            return "Such login already exists";
        } else {
            sessionManager.rollbackTransaction();
            sessionManager.closeSession();
            logger.debug("User is not created. Such login and password already exist!");
            return "User is not created. Such login and password already exist!";
        }
    }

    @Override
    public void setBan(Long id, Boolean isBan) throws DbException {
        sessionManager.startSession();
        sessionManager.startTransaction();
        userAccess.setBan(id, isBan);
        UserEntity tempUserEntity = (new UserServicesClass(super.getSessionManager())).getById(id);
        sessionManager.commitTransaction();
        sessionManager.closeSession();

        MessageTypes msg = isBan ? MessageTypes.MAKE_USER_BAN : MessageTypes.RECOVER_USER;
        Thread send  = new Thread(new MessageSender(createMessage(
                msg.generateMessage(tempUserEntity.getFIO()),
                tempUserEntity,
                msg.getTypeId())));
        send.start();
    }

    @Override
    public String setAccountStage(Long id, Boolean accountStage) throws DbException {
        sessionManager.startSession();
        sessionManager.startTransaction();
        String temp = userAccess.setAccountStage(id, accountStage);
        sessionManager.commitTransaction();
        sessionManager.closeSession();
        return temp;
    }

    public UserEntity getAccountInfo() throws DbException {
        return userEntity;
    }

    @Override
    public String createAdministrator(String login, String password, String fio, String phoneNumber, String email) throws DbException {
        sessionManager.startSession();
        String admin = userAccess.createAdministrator(login, password, fio, phoneNumber, email);
        sessionManager.closeSession();
        return admin;
    }

    @Override
    public List<String> getMoneyAmountFromPaymentCrackerCreditCard() throws DbException {
        sessionManager.startSession();
        List<String> all = userAccess.getMoneyAmountFromPaymentCrackerCreditCard();
        sessionManager.closeSession();
        return all;
    }

    @Override
    public List<UserEntity> getAllAccounts() throws DbException {
        sessionManager.startSession();
        List<UserEntity> list = userAccess.getAllAccounts();
        sessionManager.closeSession();
        return list;
    }

    @Override
    public List<List<String>> getAllPursesById(Long userId) throws DbException {
        sessionManager.startSession();
        List<List<String>> list = purseActionsClass.getPursesInfoListByUserId(userId);
        sessionManager.closeSession();
        return list;
    }

    public UserEntity getAccountInfo(Long id) throws DbException {
        sessionManager.startSession();
        UserEntity entity = userAccess.getAccountInfo(id);
        sessionManager.closeSession();
        return entity;
    }

    @Override
    public String createPurse(CurrencyTypes currencyLabel) throws DbException {
        sessionManager.startSession();
        StringBuilder info = new StringBuilder("Purse is not created");
        updateUser();
        if (userEntity.isActive() && !userEntity.isBan()) {
            Long purseId = purseActionsClass.getAndCheckPurseByCurrencyType(currencyLabel.getCurrencyName(), userEntity.getId());
            if (purseId == -1) {
                info.setLength(0);
                info.append(userAccess.createPurse(userEntity, currencyLabel.getCurrencyName()));
                logger.debug(info);
                updatePurses();
            } else {
                info.setLength(0);
                info.append("Purse with this currency already exists!");
                logger.debug(info);
            }
        }
        sessionManager.closeSession();
        return info.toString();
    }

    public HashMap<CurrencyEntity, PurseEntity> getPursesInfo() throws DbException {
        sessionManager.startSession();
        HashMap<CurrencyEntity, PurseEntity> tempMap = new HashMap<>();
        for (PurseEntity purseEntity : purseEntities) {
            tempMap.put(currencyActionsClass.getById(purseEntity.getCurrencyId()), purseEntity);
        }
        updatePurses();
        sessionManager.closeSession();
        return tempMap;
    }

    public void close() {
        userEntity = null;
        purseEntities = new ArrayList<PurseEntity>();
        userAccess = null;
    }

    public void finalClose() {
        userEntity = null;
        purseEntities = new ArrayList<PurseEntity>();
        userAccess = null;
        sessionManager.closeSession();
        sessionManager.closeFactory();
    }

    @Override
    public String makeTransaction(Long sender, Long receiver, Double money) throws DbException{
        sessionManager.startSession();
        StringBuilder info = new StringBuilder("Operation is not completed");
        updateUser();
        if (userEntity.isActive() && !userEntity.isBan()) {
            info.setLength(0);
            ExecutionTimeController.endCount();
            info.append(userAccess.makeTransaction(sender, receiver, money));
            updatePurses();
            logger.debug(info);
        }
        sessionManager.closeSession();
        return info.toString();
    }

    public void updateCurrencies() throws DbException {
        sessionManager.startSession();
        sessionManager.startTransaction();
        CurrencyServices currencyServices = new CurrencyServices(sessionManager);
        currencyActionsClass.updateAllCurrencyByYahooAPI();
        sessionManager.commitTransaction();
        sessionManager.closeSession();
    }

    public PurseEntity getPurseByCurrencyLabel(CurrencyTypes currencyLabel) throws DbException {
        sessionManager.startSession();
        updatePurses();
        if (purseEntities.size() > 0) {
            for (PurseEntity purseEntity : purseEntities) {
                CurrencyEntity currencyById = currencyActionsClass.getById(purseEntity.getCurrencyId());
                if (currencyById.getCurrencyLabel().equals(currencyLabel.getCurrencyName())) {
                    sessionManager.closeSession();
                    return purseEntity;
                }
            }
            sessionManager.closeSession();
            return null;
        } else {
            sessionManager.closeSession();
            return null;
        }

    }

    private MessageEntity createMessage(String text, UserEntity user, int messageType) {
        sessionManager.startSession();
        MessageEntity messageEntity = new MessageEntity()
                .setMessageId(DAOUtils.generateId())
                .setUserId(user.getId())
                .setDate(Calendar.getInstance().getTime())
                .setText(text)
                .setMessageType(messageType);
        sessionManager.closeSession();
        return messageEntity;
    }

    public void updateUser() throws DbException {
        this.userEntity = userActionsClass.getById(userEntity.getId());
    }


    /**
     * Created by Александр on 1/8/2015.
     */
    private class AdministratorAccess extends ClientAccess implements UserAccess {
        private SessionManager sessionManager;

        public AdministratorAccess(SessionManager sessionManager) {
            super(sessionManager);
            this.sessionManager = sessionManager;
        }

        @Override
        public List<UserEntity> getAllAccounts() throws DbException {
            return userActionsClass.getAll();
        }

        @Override
        public List<List<String>> getAllPursesById(Long userId) throws DbException {
            return purseActionsClass.getPursesInfoListByUserId(userId);
        }

        @Override
        public SessionManager getSessionManager() {
            return super.getSessionManager();
        }

        @Override
        public String createPurse(UserEntity userEntity, String currencyLabel) throws DbException {
            return "Operation is not supported";
        }

        @Override
        public String makeTransaction(Long sender, Long receiver, Double money) {
            return "Operation is not supported";
        }

        @Override
        public void setBan(Long id, boolean banStage) throws DbException {
            administratorActionsClass.setBan(id, banStage);
        }

        @Override
        public String setAccountStage(Long id, boolean accountStage) throws DbException {
            return "Operation is not supported";
        }

        @Override
        public UserEntity getAccountInfo(Long id) throws DbException {
            return userActionsClass.getById(id);
        }

        @Override
        public String createAdministrator(String login, String password, String fio, String phoneNumber, String email) throws DbException {
            sessionManager.startTransaction();
            ExecutionTimeController.startCount();
            Long userId = userActionsClass.checkIsUserUnique(login, password);
            if (userId == -1) {
                UserEntity tempUserEntity = new UserEntity(login, password, fio, phoneNumber, email,
                        Calendar.getInstance().getTime(), true, false, true);
                tempUserEntity.setId(DAOUtils.generateId());
                userActionsClass.add(tempUserEntity);
                sessionManager.commitTransaction();

                Thread send  = new Thread(new MessageSender(createMessage(
                        MessageTypes.REGISTRATION_USER.generateMessage(tempUserEntity.getFIO()),
                        tempUserEntity,
                        MessageTypes.REGISTRATION_USER.getTypeId())));
                send.start();


                ExecutionTimeController.endCount();

                logger.debug("User creation " + ExecutionTimeController.getInfo());
                return "User is created " + userId;

            } else if (userId == -2) {
                logger.debug("Such login already exists!");
                sessionManager.rollbackTransaction();
                return "Such login already exists";
            } else {
                sessionManager.rollbackTransaction();
                logger.debug("User is not created. Such login and password already exist!");
                return "User is not created. Such login and password already exist!";
            }
        }

        @Override
        public List<String> getMoneyAmountFromPaymentCrackerCreditCard() throws DbException {
            CreditCardEntity creditCardEntity = creditCardActionsClass.getById(systemCreditCard);
            CurrencyEntity currencyById = currencyActionsClass.getById(creditCardEntity.getCurrencyId());
            List<String> listOfStrings = new ArrayList<>();
            listOfStrings.add("CreditCard id: " + creditCardEntity.getId());
            listOfStrings.add("CreditCard balance: " + creditCardEntity.getBalance());
            listOfStrings.add("Currency Label: " + currencyById.getCurrencyLabel());
            return listOfStrings;
        }

        private MessageEntity createMessage(String text, UserEntity user, int messageType) {
            MessageEntity messageEntity = new MessageEntity()
                    .setMessageId(DAOUtils.generateId())
                    .setUserId(user.getId())
                    .setDate(Calendar.getInstance().getTime())
                    .setText(text)
                    .setMessageType(messageType);
            return messageEntity;
        }
    }

    /**
     * Created by Александр on 1/8/2015.
     */
    private class ClientAccess extends Services implements UserAccess {
        private SessionManager sessionManager;
        private final Double COMMISSION = 0.1;

        public ClientAccess(SessionManager sessionManager) {
            super(sessionManager);
            this.sessionManager = sessionManager;
        }

        public SessionManager getSessionManager() {
            return sessionManager;
        }

        @Override
        public String createPurse(UserEntity userEntity, String currencyLabel) throws DbException {
            boolean flag = false;
            List<CurrencyEntity> allCurrency = currencyActionsClass.getAll();
            Long currencyId = 0l;
            Date currencyDate = new Date();
            for (CurrencyEntity currencyEntity : allCurrency) {
                if (currencyEntity.getCurrencyLabel().equals(currencyLabel) && currencyDate.after(currencyEntity.getCurrencyDate())) {
                    currencyDate = currencyEntity.getCurrencyDate();
                    currencyId = currencyEntity.getCurrencyId();
                    flag = true;
                }
            }
            if (flag) {
                sessionManager.startTransaction();
                PurseEntity purseEntity = new PurseEntity(currencyId, userEntity.getId(), 0);
                purseEntity.setId(DAOUtils.generateId());
                purseActionsClass.add(userEntity, purseEntity);
                sessionManager.commitTransaction();
                logger.debug("Purse was created for user: " + purseEntity.getUserId());
            } else {
                logger.debug(("Currency type does not exist"));
            }
            return "Purse was created!";
        }

        @Override
        public String makeTransaction(Long idSender, Long idReceiver, Double money) throws DbException {
            PaymentEntity sender = paymentServicesClass.getById(idSender);
            PaymentEntity receiver = paymentServicesClass.getById(idReceiver);
            TransactionEntity<PaymentEntity, PaymentEntity> transactionEntity =
                    new TransactionEntity<>(sender, receiver, money);
            sessionManager.startTransaction();
            transactionEntity.setSenderCurrencyValue(currencyActionsClass.getById(sender.getCurrencyId()).getCurrencyValue());
            transactionEntity.setReceiverCurrencyValue(currencyActionsClass.getById(receiver.getCurrencyId()).getCurrencyValue());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            Double convertedMoney = convertCurrency(sender.getCurrencyId(), receiver.getCurrencyId(), money);
            try {
                Date currentDate = dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime()));
                transactionEntity.setDate(currentDate);
                if ((sender.getBalance() >= money + (money * COMMISSION) ) && ((money + (money * COMMISSION)) > 0)) {
                    sender.setBalance(new BigDecimal(sender.getBalance() - (money + (money * COMMISSION)))
                            .setScale(2, BigDecimal.ROUND_CEILING).doubleValue());
                    paymentServicesClass.update(sender.getId(), sender);
                    receiver.setBalance(new BigDecimal(receiver.getBalance() + convertedMoney)
                            .setScale(2, BigDecimal.ROUND_CEILING).doubleValue());
                    paymentServicesClass.update(receiver.getId(), receiver);
                    putCommissionOnDevelopersCount(convertCurrency(sender.getCurrencyId(),
                            creditCardActionsClass.getById(systemCreditCard).getCurrencyId(), money * COMMISSION));
                    paymentServicesClass.saveTransactionResult(transactionEntity);
                    sessionManager.commitTransaction();
                } else {
                    return "Operation is not completed!!!";
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackTransaction();
            }
            return "Add money operation is completed!";
        }


        private void putCommissionOnDevelopersCount(Double money) throws DbException, SQLException {
            CreditCardEntity creditCardEntity = creditCardActionsClass.getById(systemCreditCard);
            creditCardEntity.setBalance(creditCardEntity.getBalance() + money);
            creditCardActionsClass.update(systemCreditCard, creditCardEntity);
        }

        private Double convertCurrency(Long currencyFromId, Long currencyWhereId, Double money) throws DbException {
            CurrencyEntity currencyFrom = currencyActionsClass.getById(currencyFromId);
            CurrencyEntity currencyWhere = currencyActionsClass.getById(currencyWhereId);
            Double currencyValueFrom = currencyFrom.getCurrencyValue();
            Double currencyValueWhere = currencyWhere.getCurrencyValue();
            Double result = (currencyValueFrom * money) / currencyValueWhere;
            BigDecimal bigDecimal = new BigDecimal(result);
            bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_CEILING);
            result = bigDecimal.doubleValue();
            return result;
        }

        @Override
        public void setBan(Long id, boolean banStage) throws DbException {
            throw new UnsupportedOperationException();
        }

        @Override
        public String setAccountStage(Long id, boolean accountStage) throws DbException {
            userActionsClass.setAccountStage(id, accountStage);
            return "Now account active is" + (userActionsClass.getById(id).isActive());
        }

        @Override
        public UserEntity getAccountInfo(Long id) throws DbException {
            return null;
        }

        @Override
        public List<UserEntity> getAllAccounts() throws DbException {
            return null;
        }

        @Override
        public List<List<String>> getAllPursesById(Long userId) throws DbException {
            return null;
        }

        @Override
        public String createAdministrator(String login, String password, String fio, String phoneNumber, String email) throws DbException {
            return "";
        }

        @Override
        public List<String> getMoneyAmountFromPaymentCrackerCreditCard() throws DbException {
            return null;
        }

        @Override
        public UserEntity getAccountInfo() throws DbException {
            return null;
        }
    }

    private class MessageSender implements Runnable{
        private MessageEntity messageEntity;
        private SessionManager sessionManager;
        private MessageSender(MessageEntity messageEntity)
        {
            this.messageEntity = messageEntity;
        }

        @Override
        public void run() {
            try {
                sessionManager = new SessionManager();
                sessionManager.startSession();
                sessionManager.startTransaction();
                MessageServicesClass sender = new MessageServicesClass(sessionManager);
                sender.sendMessage(this.messageEntity);
                sessionManager.commitTransaction();
            } catch (DbException e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackTransaction();
            } finally {
                sessionManager.closeSession();
            }
        }
    }
}