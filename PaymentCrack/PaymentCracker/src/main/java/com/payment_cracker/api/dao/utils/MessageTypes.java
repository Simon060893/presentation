package com.payment_cracker.api.dao.utils;

public enum MessageTypes {

    REGISTRATION_USER (1) {
        public String generateMessage(String name) {
            return new StringBuilder("Hi, ").append(name).append("! Welcome to" +
                    " PaymentCracker.").toString();
        }
    },
    MAKE_USER_INACTIVE (2) {
        public String generateMessage(String name) {
            return new StringBuilder("Hi, ").append(name).append("! You account is deleted!")
                    .toString();}
    },
    MAKE_USER_BAN (3) {
        public String generateMessage(String name) {
            return new StringBuilder("Hi, ").append(name).append("! You account is baned!" +
                    " Please, contact with our administration to get more information." +
                    " PaymentCracker@gmail.com").toString();}
    },
    RECOVER_USER (4) {
        public String generateMessage(String name) {
            return new StringBuilder("Hi, ").append(name)
                    .append("! You account is recovered").toString();
        }
    };

    public abstract String generateMessage(String name);

    private int typeId;

    MessageTypes(int i) {
        this.typeId = i;
    }

    public int getTypeId() {
        return typeId;
    }
}