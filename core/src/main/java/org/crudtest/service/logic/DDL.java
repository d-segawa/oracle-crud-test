package org.crudtest.service.logic;

public class DDL {

    private static final String SEQ_STATEMENTS = "CREATE SEQUENCE %1$s INCREMENT BY 1 START WITH 1 MAXVALUE 999 MINVALUE 1 CYCLE CACHE 20 NOORDER";

    private static final String LOG_TABLE_STATEMENTS = "CREATE TABLE %1$s(ID CHAR(17) NOT NULL, TABLE_NAME VARCHAR2(256) NOT NULL, CRUD_TYPE VARCHAR2(10) NOT NULL, HISTORY_TYPE VARCHAR2(3) NOT NULL, DATA CLOB NOT NULL, INSERT_DATE DATE NOT NULL, RECODE_TYPE CHAR(1) NOT NULL, ERROR_INFO VARCHAR2(4000), CONSTRAINT %2$s_PK PRIMARY KEY (ID))";

    private static final String TRIGGER_MNG_T = "CREATE TABLE %1$s(ID CHAR(17) NOT NULL,TRIGGER_NAME VARCHAR2(256) NOT NULL,INSERT_DATE DATE NOT NULL,UPDATE_DATE DATE NOT NULL,DELETE_FLAG CHAR(1) NOT NULL,CONSTRAINT %2$s_PK PRIMARY KEY (ID))";

    private static final String DROP_TRIGGER = "DROP TRIGGER %1$s";

    public static String createSeq(String seqName) {
        return String.format(SEQ_STATEMENTS, seqName);
    }

    public static String createLogTable(String tableName) {
        return String.format(LOG_TABLE_STATEMENTS, tableName, tableName);
    }

    public static String createManagementTable(String tableName) {
        return String.format(TRIGGER_MNG_T, tableName, tableName);
    }

    public static String createDropTrigger(String triggerName) {
        return String.format(DROP_TRIGGER, triggerName);
    }
}
