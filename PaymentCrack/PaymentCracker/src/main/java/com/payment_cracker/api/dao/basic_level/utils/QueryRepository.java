package com.payment_cracker.api.dao.basic_level.utils;

public class QueryRepository {
    public static final String SELECT_PARAMETERS_BY_OBJECTS = "select par.* from objects obj, parameters par where obj.objectId = ? and par.objectId = ?";
    public static final String SELECT_ATTRIBUTES_BY_OBJTYPE = " select * from ATTRIBUTES attr where objTypeId = ?";
    public static final String SELECT_ALL_OBJECTS_BY_TYPE = " select * from OBJECTS where ObjTypeId = ?";
    public static final String SELECT_ALL_ENTITIES_BY_TYPE = "Select userPar.* From Objects users, Parameters userPar Where  users.OBJTYPEID = ? AND userPar.OBJECTID = users.OBJECTID \n" +
            "ORDER BY userPar.OBJECTID";
    public static final String SELECT_OBJREFERENCES_BY_REFERENCE = "select * from objreference where reference = ?";
    public static final String SELECT_CONFIRM_MESSAGE  = "select mess_text.value\n" +
            "from OBJECTS mes, PARAMETERS mess_type, PARAMETERS mess_text, PARAMETERS mess_date\n" +
            "where mes.parentid = ?\n" +
            "and mess_type.attrid = 26\n" +
            "and mess_text.attrid = 17\n" +
            "and mess_date.attrid = 16\n" +
            "and mess_type.value = ?\n" +
            "and sysdate < mess_date.datevalue + 1 \n" +
            "and mess_type.objectid = mes.objectid\n" +
            "and mess_text.objectid = mes.objectid\n" +
            "and mess_date.objectid = mes.objectid;";

    public static final String SELECT_TRANSACTIONS_BY_USER = "SELECT DISTINCT \n" +
            "  OBJECTS.OBJECTID, \n" +
            "  OBJECTS.NAME, \n" +
            "  PURSE_FROM.OBJECTID  AS PURSE_FROM, \n" +
            "  PURSE_WHERE.OBJECTID AS PURSE_WHERE, \n" +
            "  BALANCE.VALUE        AS BALANCE, \n" +
            "  SENDER_CURR.VALUE    AS SENDER_CURR, \n" +
            "  RECEIVER_CURR.VALUE  AS RECEIVER_CURR, \n" +
            "  DATE_VALUE.DATEVALUE AS \"DATE\",\n" +
            "  (SELECT PARAMETERS.VALUE FROM PARAMETERS WHERE PARAMETERS.OBJECTID =\n" +
            "                                  (SELECT OBJECTID FROM OBJREFERENCE\n" +
            "                                  WHERE OBJREFERENCE.REFERENCE = PURSE_FROM.OBJECTID) AND ATTRID = 9) AS \"SENDER_CURRENCY_LABEL\"\n" +
            "FROM OBJECTS, \n" +
            "  (SELECT \n" +
            "     OBJECTID, \n" +
            "     REFERENCE \n" +
            "   FROM OBJREFERENCE \n" +
            "   WHERE ATTRID = 19) PURSE_FROM, \n" +
            "  (SELECT \n" +
            "     OBJECTID, \n" +
            "     REFERENCE \n" +
            "   FROM OBJREFERENCE \n" +
            "   WHERE ATTRID = 20) PURSE_WHERE, \n" +
            "  (SELECT \n" +
            "     VALUE, \n" +
            "     OBJECTID \n" +
            "   FROM PARAMETERS \n" +
            "   WHERE ATTRID = 21) BALANCE, \n" +
            "  (SELECT \n" +
            "     VALUE,\n" +
            "     OBJECTID\n" +
            "   FROM PARAMETERS \n" +
            "   WHERE ATTRID = 22) SENDER_CURR, \n" +
            "  (SELECT \n" +
            "     VALUE, \n" +
            "     OBJECTID \n" +
            "   FROM PARAMETERS \n" +
            "   WHERE ATTRID = 23) RECEIVER_CURR, \n" +
            "  (SELECT \n" +
            "     DATEVALUE, \n" +
            "     OBJECTID \n" +
            "   FROM PARAMETERS \n" +
            "   WHERE ATTRID = 24) DATE_VALUE\n" +
            "WHERE OBJECTS.OBJECTID = PURSE_FROM.REFERENCE AND OBJECTS.OBJECTID = PURSE_WHERE.REFERENCE \n" +
            "      AND OBJECTS.OBJECTID = BALANCE.OBJECTID AND OBJECTS.OBJECTID = SENDER_CURR.OBJECTID \n" +
            "      AND OBJECTS.OBJECTID = RECEIVER_CURR.OBJECTID AND OBJECTS.OBJECTID = DATE_VALUE.OBJECTID\n" +
            "      AND DATE_VALUE.DATEVALUE BETWEEN TO_DATE(SYSDATE - 1) AND SYSDATE \n" +
            "      AND (SELECT objects.parentid \n" +
            "           FROM objects \n" +
            "           WHERE objectid = purse_from.objectid) = ? and rownum <= 500\n" +
            "    \n" +
            "union\n" +
            " \n" +
            "SELECT DISTINCT \n" +
            "  OBJECTS.OBJECTID, \n" +
            "  OBJECTS.NAME, \n" +
            "  PURSE_FROM.OBJECTID  AS PURSE_FROM, \n" +
            "  PURSE_WHERE.OBJECTID AS PURSE_WHERE, \n" +
            "  BALANCE.VALUE        AS BALANCE, \n" +
            "  SENDER_CURR.VALUE    AS SENDER_CURR, \n" +
            "  RECEIVER_CURR.VALUE  AS RECEIVER_CURR, \n" +
            "  DATE_VALUE.DATEVALUE AS \"DATE\",\n" +
            "(SELECT PARAMETERS.VALUE FROM PARAMETERS WHERE PARAMETERS.OBJECTID =\n" +
            "(SELECT OBJECTID FROM OBJREFERENCE\n" +
            "WHERE OBJREFERENCE.REFERENCE = PURSE_WHERE.OBJECTID) AND ATTRID = 9)  AS \"RECEIVER_CURRENCY_LABEL\"\n" +
            "FROM OBJECTS, \n" +
            "  (SELECT \n" +
            "     OBJECTID, \n" +
            "     REFERENCE \n" +
            "   FROM OBJREFERENCE \n" +
            "   WHERE ATTRID = 19) PURSE_FROM, \n" +
            "  (SELECT \n" +
            "     OBJECTID, \n" +
            "     REFERENCE \n" +
            "   FROM OBJREFERENCE \n" +
            "   WHERE ATTRID = 20) PURSE_WHERE, \n" +
            "  (SELECT \n" +
            "     VALUE, \n" +
            "     OBJECTID \n" +
            "   FROM PARAMETERS \n" +
            "   WHERE ATTRID = 21) BALANCE, \n" +
            "  (SELECT \n" +
            "     VALUE, \n" +
            "     OBJECTID \n" +
            "   FROM PARAMETERS \n" +
            "   WHERE ATTRID = 22) SENDER_CURR, \n" +
            "  (SELECT \n" +
            "     VALUE, \n" +
            "     OBJECTID \n" +
            "   FROM PARAMETERS \n" +
            "   WHERE ATTRID = 23) RECEIVER_CURR, \n" +
            "  (SELECT \n" +
            "     DATEVALUE, \n" +
            "     OBJECTID \n" +
            "   FROM PARAMETERS \n" +
            "   WHERE ATTRID = 24) DATE_VALUE\n" +
            "WHERE OBJECTS.OBJECTID = PURSE_FROM.REFERENCE AND OBJECTS.OBJECTID = PURSE_WHERE.REFERENCE \n" +
            "      AND OBJECTS.OBJECTID = BALANCE.OBJECTID AND OBJECTS.OBJECTID = SENDER_CURR.OBJECTID \n" +
            "      AND OBJECTS.OBJECTID = RECEIVER_CURR.OBJECTID AND OBJECTS.OBJECTID = DATE_VALUE.OBJECTID\n" +
            "      AND DATE_VALUE.DATEVALUE BETWEEN TO_DATE(SYSDATE - 1) AND SYSDATE \n" +
            "      AND (SELECT objects.parentid \n" +
            "           FROM objects \n" +
            "           WHERE objectid = purse_where.objectid) = ? and rownum <= 500";

    public static final String SELECT_ACCOUNT_INFO = "SELECT OBJECTS.OBJECTID \"PURSE_ID\",\n" +
            "  (SELECT PARAM.VALUE FROM PARAMETERS PARAM WHERE PARAM.OBJECTID = " +
            "OBJREFERENCES.OBJECTID AND ATTRID = 9) AS \"CURRENCY_LABEL\", \n" +
            "  (SELECT PARAM.VALUE FROM PARAMETERS PARAM WHERE PARAM.OBJECTID = " +
            "OBJECTS.OBJECTID AND ATTRID = 12) AS \"BALANCE\",\n" +
            "  (SELECT OBJ.NAME FROM OBJECTS OBJ WHERE OBJ.OBJECTID = OBJECTS.PARENTID) AS \"USER_LOGIN\",\n" +
            "  (SELECT PARAM.VALUE FROM PARAMETERS PARAM WHERE PARAM.OBJECTID = " +
            "OBJECTS.PARENTID AND PARAM.ATTRID = 2) AS \"USER_FIO\"\n" +
            "FROM OBJECTS, PARAMETERS, (SELECT * FROM OBJREFERENCE) OBJREFERENCES " +
            "WHERE OBJECTS.OBJECTID = PARAMETERS.OBJECTID \n" +
            "AND OBJECTS.OBJECTID = OBJREFERENCES.REFERENCE AND PARENTID = ?\n";
}


