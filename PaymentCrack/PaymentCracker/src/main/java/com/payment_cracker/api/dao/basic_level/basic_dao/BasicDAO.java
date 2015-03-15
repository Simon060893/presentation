package com.payment_cracker.api.dao.basic_level.basic_dao;


import com.payment_cracker.api.dao.basic_level.basic_entities.*;
import com.payment_cracker.api.dao.basic_level.utils.QueryRepository;
import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.middle_level.middle_entities.PurseEntity;
import com.payment_cracker.api.dao.middle_level.middle_entities.TransactionEntity;
import com.payment_cracker.api.dao.utils.SessionManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BasicDAO {
    protected static final Logger logger = Logger.getLogger("payment");

    private SessionManager sessionManager;

    protected BasicDAO(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    protected <T extends HibernateTableInterface> void addEntity(T entity) throws DbException {
        try {
            sessionManager.getSession().save(entity);
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            throw new DbException(e);
        }
    }

    protected <T extends HibernateTableInterface> void updateEntity(T entity) throws DbException {
        try {
            sessionManager.getSession().update(entity);
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            throw new DbException(e);
        }
    }

    protected <T extends HibernateTableInterface, E> T getByIdEntity(E id, Class clazz) throws DbException {
        T entity;
        try {
            entity = (T) sessionManager.getSession().get(clazz, (Serializable) id);
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            throw new DbException(e);
        }
        return entity;
    }

    protected <T extends HibernateTableInterface> List<T> getAllEntities(Class clazz) throws DbException {
        List<T> entities;
        try {
            entities = sessionManager.getSession().createCriteria(clazz).list();
        }
        catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            throw new DbException(e);
        }
        return entities;
    }

    protected List<Parameters> getParametersList(Objects objects) {
        String id = objects.getObjectId().toString();
        Query que = sessionManager.getSession().createSQLQuery(QueryRepository.SELECT_PARAMETERS_BY_OBJECTS)
                .addEntity(Parameters.class)
                .setString(0, id)
                .setString(1, id);
        if(que.list()!=null && que.list().size()>0)
            return que.list();
        else return null;
    }

    protected List<Parameters> getAllParametersOfEntity(Integer typeId) {
        Query que = sessionManager.getSession().createSQLQuery(QueryRepository.SELECT_ALL_ENTITIES_BY_TYPE)
                .addEntity(Parameters.class)
                .setString(0, typeId.toString());
        if(que.list()!=null && que.list().size()>0)
            return que.list();
        else return null;
    }

    protected List<Objects> getAllObjectsByObjTypeId(Integer id) {
        Query que = sessionManager.getSession().createSQLQuery(QueryRepository.SELECT_ALL_OBJECTS_BY_TYPE)
                .addEntity(Objects.class)
                .setString(0, id.toString());
        if(que.list() != null && que.list().size()>0)
            return que.list();
        else return null;
    }

    protected List<Attributes> getAttributesList(Integer objTypeId) {
        Query que = sessionManager.getSession().createSQLQuery(QueryRepository.SELECT_ATTRIBUTES_BY_OBJTYPE)
                .addEntity(Attributes.class)
                .setString(0, String.valueOf(objTypeId));
        if(que.list() != null && que.list().size()>0)
            return que.list();
        else return null;
    }

    protected List<TransactionEntity> getTransactionList(Long objId) throws DbException  {
        Query que = sessionManager.getSession().createSQLQuery(QueryRepository.SELECT_TRANSACTIONS_BY_USER)
                .setLong(0, (objId)).setLong(1, (objId));
        List<Object[]> rows = que.list();
        List<TransactionEntity> transactionEntities = new ArrayList<>();
        for (Object[] row: rows){
            TransactionEntity te = new TransactionEntity();
            te.setId(Long.valueOf(row[0].toString()));
            PurseEntity purseEntity1 = new PurseEntity();
            PurseEntity purseEntity2 = new PurseEntity();
            purseEntity1.setId(Long.valueOf(row[2].toString()));
            purseEntity2.setId(Long.valueOf(row[3].toString()));
            te.setSender(purseEntity1);
            te.setReceiver(purseEntity2);
            te.setMoney(Double.valueOf(row[4].toString()));
            te.setSenderCurrencyValue(Double.valueOf(row[5].toString()));
            te.setReceiverCurrencyValue(Double.valueOf(row[6].toString()));
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                Date currentDate = dateFormat.parse(row[7].toString());
                te.setDate(currentDate);
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
                throw new DbException();
            }
            te.setSenderCurrencyLabel(row[8].toString());
            transactionEntities.add(te);
        }
        return transactionEntities;
    }

    protected List<ObjReference> getObjReferencesOfReference(Objects objects)
    {
        String id = objects.getObjectId().toString();
        Query que = sessionManager.getSession().createSQLQuery(QueryRepository.SELECT_OBJREFERENCES_BY_REFERENCE)
                .addEntity(ObjReference.class)
                .setString(0,id);
        if(que.list()!=null && que.list().size()>0)
            return que.list();
        else return null;
    }

    protected List<List<String>> getPursesInfoListByUserId(Long userId) {
        Query que = sessionManager.getSession().createSQLQuery(QueryRepository.SELECT_ACCOUNT_INFO)
                .setLong(0, (userId));
        List<Object[]> rows = que.list();
        List<List<String>> info = new ArrayList<>();
        for (Object[] row: rows){
            List<String> tempList = new ArrayList<>();
            for(Object tempInfo: row) {
                tempList.add(tempInfo.toString());
            }
            info.add(tempList);
        }
        return info;
    }
}