package com.payment_cracker.api.dao.utils;

import static com.payment_cracker.api.dao.utils.AttributesInfo.*;


public enum TransactionInfo implements ContractsInterface {
    BALANCE(ATTR_AMOUNT_OF_TRANSACTION, "BALANCE", AttributeTypes.DOUBLE),
    PURSE_WHERE(ATTR_RECEIVER_PURSE_OF_TRANSACTION, "PURSE_WHERE", AttributeTypes.LONG),
    PURSE_FROM(ATTR_SENDER_PURSE_OF_TRANSACTION, "PURSE_FROM", AttributeTypes.LONG),
    SENDER_CURR_VAL(ATTR_SENDER_CURR_VAL_OF_TRANSACTION, "SENDER_CURR_VAL", AttributeTypes.DOUBLE),
    RECEIVER_CURR_VAL(ATTR_RECEIVER_CURR_VAL_OF_TRANSACTION, "RECEIVER_CURR_VAL", AttributeTypes.DOUBLE),
    DATE(ATTR_DATE_OF_TRANSACTION, "DATE", AttributeTypes.DATE),
    SENDER_CURR_LABEL(ATTR_SENDER_CURRENCY_LABEL, "SENDER_CURR_VAL", AttributeTypes.STRING),
    RECEIVER_CURR_LABEL(ATTR_RECEIVER_CURRENCY_LABEL, "RECEIVER_CURR_VAL", AttributeTypes.STRING);

    private AttributeTypes attributeType;
    private Integer attributeId;
    private Class classType;
    private String columnName;

    private TransactionInfo(Integer attributeId, String columnName, AttributeTypes attributeType) {
        this.attributeId = attributeId;
        this.columnName = columnName;
        this.attributeType = attributeType;
    }

    @Override
    public Integer getAttributeId() {
        return attributeId;
    }



    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public AttributeTypes getAttributeType() {
        return attributeType;
    }
}