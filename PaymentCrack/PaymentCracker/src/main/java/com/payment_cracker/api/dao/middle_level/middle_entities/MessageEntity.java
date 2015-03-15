package com.payment_cracker.api.dao.middle_level.middle_entities;

import java.util.Date;


public class MessageEntity implements Entity {
    private Long messageId;
    private Long userId;
    private Date date;
    private String text;

    private String subject;
    private int messageType;

    private boolean confirmToken;

    public MessageEntity() {

    }

    public Long getMessageId() {
        return messageId;
    }

    public MessageEntity setMessageId(Long messageId) {
        this.messageId = messageId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public MessageEntity setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public MessageEntity setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getText() {
        return text;
    }

    public MessageEntity setText(String text) {
        this.text = text;
        return this;
    }

    public boolean getConfirmToken() {
        return confirmToken;
    }

    public MessageEntity setConfirmToken(boolean confirmToken) {
        this.confirmToken = confirmToken;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MessageEntity setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public int getMessageType() {
        return messageType;
    }

    public MessageEntity setMessageType(int messageType) {
        this.messageType = messageType;
        return this;
    }
}
