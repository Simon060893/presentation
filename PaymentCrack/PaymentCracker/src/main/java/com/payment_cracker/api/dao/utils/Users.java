package com.payment_cracker.api.dao.utils;


import static com.payment_cracker.api.dao.utils.AttributesInfo.*;

public enum Users implements ContractsInterface {
    PASSWORD(ATTR_PASSWORD_OF_USER, "PASSWORD", AttributeTypes.STRING),
    FIO(ATTR_FIO_OF_USER, "FIO", AttributeTypes.STRING),
    PHONE_NUMBER(ATTR_PHONE_NUMBER_OF_USER, "PHONE_NUMBER", AttributeTypes.STRING),
    EMAIL(ATTR_EMAIL_OT_USER, "EMAIL", AttributeTypes.STRING),
    REGISTRATION_DATE(ATTR_REG_DATE_OF_USER, "REGISTRATION_DATE", AttributeTypes.DATE),
    IS_ACTIVE(ATTR_ACTIVE_OF_USER, "IS_ACTIVE", AttributeTypes.BOOLEAN),
    IS_BANNED(ATTR_BAN_OF_USER, "IS_BANNED", AttributeTypes.BOOLEAN),
    IS_ADMINISTRATOR(ATTR_ADMINISTRATOR_OF_USER, "IS_ADMINISTRATOR", AttributeTypes.BOOLEAN);


    private AttributeTypes attributeType;
    private Integer attributeId;
    private String columnName;

    private Users(Integer attributeId, String columnName, AttributeTypes attributeType) {
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
