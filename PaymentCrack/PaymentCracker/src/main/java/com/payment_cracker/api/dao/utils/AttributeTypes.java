package com.payment_cracker.api.dao.utils;


public enum AttributeTypes {
    STRING(1),
    DATE(2),
    BOOLEAN(3),
    DOUBLE(4),
    LONG(5),
    INTEGER(6);
    private int typeId;
    AttributeTypes(int i) {
        this.typeId = i;
    }

    public int getTypeId() {
        return typeId;
    }
}
