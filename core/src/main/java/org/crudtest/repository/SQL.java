package org.crudtest.repository;

public class SQL {

    public static final String countTable_sql = "SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME = ?";

    public static final String selectLog_sql = "SELECT ID, CRUD_TYPE, HISTORY_TYPE, TABLE_NAME, DATA, INSERT_DATE FROM %1$s WHERE TABLE_NAME = ? ORDER BY ID";

    public static final String deleteLog_sql = "DELETE FROM %1$s WHERE TABLE_NAME = ?";

    public static final String countSequence_sql = "SELECT COUNT(*) FROM USER_SEQUENCES WHERE SEQUENCE_NAME = ?";

    public static final String selectTableName_sql = "SELECT TABLE_NAME FROM USER_TRIGGERS WHERE TRIGGER_NAME LIKE ?";

    public static final String selectTrigerName_sql = "SELECT TRIGGER_NAME FROM %1$s ORDER BY ID";

    public static final String insertManageTable_sql = "INSERT INTO %1$s (ID, TRIGGER_NAME, INSERT_DATE, UPDATE_DATE, DELETE_FLAG) VALUES ('%2$s' || TO_CHAR(%3$s.NEXTVAL, 'FM000'),?,?,?,'0')";

}
