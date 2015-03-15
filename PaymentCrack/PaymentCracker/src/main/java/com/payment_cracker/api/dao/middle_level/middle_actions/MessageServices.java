package com.payment_cracker.api.dao.middle_level.middle_actions;


import com.payment_cracker.api.dao.basic_level.basic_dao.BasicDAO;
import com.payment_cracker.api.dao.basic_level.basic_entities.*;
import com.payment_cracker.api.dao.basic_level.basic_entities.Objects;
import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.middle_level.middle_entities.MessageEntity;
import com.payment_cracker.api.dao.utils.Messages;
import com.payment_cracker.api.dao.utils.SessionManager;
import com.payment_cracker.api.dao.utils.TablesInfo;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

import static com.payment_cracker.api.dao.utils.AttributesInfo.*;

public class MessageServices extends BasicDAO {

    private SessionManager sessionManager;

    public MessageServices(SessionManager sessionManager) {
        super(sessionManager);
        this.sessionManager = sessionManager;
    }

    protected void add(MessageEntity entity) throws DbException {
        Objects obj = new Objects();
        Map<Integer,Parameters> parameters =  new MessageMaker().getParams(entity);

        obj.setName("Message"+entity.getDate().toString());
        obj.setObjectId(entity.getMessageId());
        obj.setParent(new Objects().setObjectId(entity.getUserId()));
        obj.setObjectType(new ObjType().setObjectTypeId(TablesInfo.OBJ_TYPE_MESSAGE));

        super.addEntity(obj);

        for(Messages mess: Messages.values())
        {
            ParametersId parId = new ParametersId(obj,new Attributes()
                    .setAttrId(mess.getAttributeId()));
            Parameters par = parameters.get(mess.getAttributeId());
            par.setParametersId(parId);
            super.addEntity(par);
        }
    }

    protected void update(MessageEntity entity) throws DbException {
        updateConfirmToken(entity);
    }

    private void updateConfirmToken(MessageEntity entity) throws DbException {
        Parameters confirmToken = super.getByIdEntity(new ParametersId
                (new Objects().setObjectId(entity.getMessageId()),
                        new Attributes().setAttrId(
                                Messages.CONFIRM_TOKEN.getAttributeId())), Parameters.class);
        confirmToken.setValue(String.valueOf(entity.getConfirmToken()));
        super.updateEntity(confirmToken);
    }

    protected synchronized void sendMessage (MessageEntity entity) throws DbException{

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("paymentcracker@gmail.com","PaymentCrackerPaymentCracker");
            }
        });

        try {
            add(entity);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("paymentcracker@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(new UserServices(sessionManager)
                            .getById(entity.getUserId()).getEmail()));
            message.setSubject(entity.getSubject());
            message.setText(entity.getText());

            Transport.send(message);
            logger.debug("Message Send To User");
        }
        catch (MessagingException e) {
            logger.error(e.getMessage(), e);
            throw new DbException(e);
        }
    }

    protected MessageEntity getById(Long id) throws DbException {
        Objects obj = super.getByIdEntity(id, Objects.class);

        List<Parameters> parameterses = super.getParametersList(obj);

        MessageEntity messageEntity = new MessageEntity()
                .setMessageId(id)
                .setUserId(obj.getParent().getObjectId());

        messageEntity = new MessageMaker()
                .makeMessageEntity(parameterses, messageEntity);

        return messageEntity;
    }

    public List<MessageEntity> getAll() throws DbException {
        List<MessageEntity> allMessages = new ArrayList<>();

        List<Parameters> allParameters = super
                .getAllParametersOfEntity(TablesInfo.OBJ_TYPE_MESSAGE);

        int parametersQuantity = Messages.values().length;
        if(allParameters != null){
        for(int i = 0; i < allParameters.size(); i += parametersQuantity)
        {
            MessageEntity messageEntity = new MessageEntity()
                    .setMessageId(allParameters.get(i)
                            .getParametersId().getObject().getObjectId())
                    .setUserId(allParameters.get(i).getParametersId()
                            .getObject().getParent().getObjectId());

            messageEntity = new MessageMaker().makeMessageEntity
                    (allParameters.subList(i, i + parametersQuantity), messageEntity);

            allMessages.add(messageEntity);
        }}
        return allMessages;
    }


    private static class MessageMaker {

        private MessageEntity makeMessageEntity(List<Parameters> parameterses,
                                                MessageEntity messageEntity) {
            for (Parameters parameters : parameterses) {
                switch (parameters.getParametersId().getAttribute().getAttrId()) {
                    case ATTR_TEXT_OF_MESSAGE:
                        messageEntity.setText(parameters.getValue());
                        break;
                    case ATTR_DATE_OF_MESSAGE:
                        messageEntity.setDate(parameters.getDateValue());
                        break;
                    case ATTR_CONFIRM_TOKEN_OF_MESSAGE:
                        messageEntity.setConfirmToken(Boolean.parseBoolean(parameters.getValue()));
                        break;
                    case ATTR_SUBJECT_OF_MESSAGE:
                        messageEntity.setSubject(parameters.getValue());
                        break;
                    case ATTR_TYPE_OF_MESSAGE:
                        messageEntity.setMessageType(Integer.parseInt(parameters.getValue()));
                        break;
                }
            }
            return messageEntity;
        }

        private Map<Integer, Parameters> getParams(MessageEntity entity) {
            Map<Integer, Parameters> params = new HashMap<>();
            params.put(ATTR_DATE_OF_MESSAGE, new Parameters(null, entity.getDate()));
            params.put(ATTR_TEXT_OF_MESSAGE, new Parameters(entity.getText(), null));
            params.put(ATTR_CONFIRM_TOKEN_OF_MESSAGE,
                    new Parameters(String.valueOf(entity.getConfirmToken()), null));
            params.put(ATTR_SUBJECT_OF_MESSAGE, new Parameters(entity.getSubject(), null));
            params.put(ATTR_TYPE_OF_MESSAGE, new Parameters(String.valueOf(
                    entity.getMessageType()), null));
            return params;
        }

    }
}
