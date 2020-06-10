package org.crudtest.service.logic;

import java.sql.Types;

import org.crudtest.properties.ApplicationProperties;
import org.crudtest.service.bean.MetaTableInfo;
import org.crudtest.service.bean.MetaTableInfo.ColumnInfo;

public class OracleTriggerCreator {

    private static final String HEADER = "CREATE OR REPLACE TRIGGER %1$s AFTER INSERT OR UPDATE OR DELETE ON %2$s FOR EACH ROW";

    private static final String INSERT_SQL = "INSERT INTO %1$s (ID,TABLE_NAME,CRUD_TYPE,HISTORY_TYPE,DATA,INSERT_DATE) VALUES (TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') || TO_CHAR(%2$s.NEXTVAL, 'FM000'), '%3$s', '%4$s','%5$s', %6$s, SYSDATE);";

    private static final String INSERT_VALUES = " '%1$s:' || UTL_RAW.CAST_TO_VARCHAR2(UTL_ENCODE.BASE64_ENCODE(UTL_I18N.STRING_TO_RAW(NVL(%2$s, '<NULL>'))))";

    private static final String INSERTING = " IF INSERTING THEN %1$s END IF;";

    private static final String UPDATING = " IF UPDATING THEN %1$s %2$s END IF;";

    private static final String DELETING = " IF DELETING THEN %1$s END IF;";

    final String triggerName;

    final String logTableName;

    public OracleTriggerCreator(String triggerName, String logTableName) {
        this.triggerName = triggerName;
        this.logTableName = logTableName;
    }

    public String create(MetaTableInfo tableInfo) {
        validate(tableInfo);
        StringBuilder sb = new StringBuilder();
        sb.append(createHeader(tableInfo.getTableName(), triggerName));
        sb.append(" BEGIN ");
        sb.append(createInserting(tableInfo));
        sb.append(createUpdating(tableInfo));
        sb.append(createDeleting(tableInfo));
        sb.append(" END;");
        return sb.toString();
    }

    void validate(MetaTableInfo tableInfo) {
        if (tableInfo == null || tableInfo.getColumnList().isEmpty()) {
            throw new IllegalArgumentException("DBMetaData is empty");
        }
    }

    String createHeader(String tableName, String triggerName) {
        return String.format(HEADER, triggerName, tableName);
    }

    String createInserting(MetaTableInfo tableInfo) {
        String insertValues = createNewInsertValues(tableInfo);
        String insertSql = String.format(INSERT_SQL, logTableName, ApplicationProperties.LOG_SEQ_NAME.getValue(),
                tableInfo.getTableName(), "INSERT", "NEW",
                insertValues);
        return String.format(INSERTING, insertSql);
    }

    String createUpdating(MetaTableInfo tableInfo) {
        String insertOldValues = createOldInsertValues(tableInfo);
        String insertNewValues = createNewInsertValues(tableInfo);
        String insertOldValueSql = String.format(INSERT_SQL, logTableName,
                ApplicationProperties.LOG_SEQ_NAME.getValue(),
                tableInfo.getTableName(), "UPDATE", "OLD",
                insertOldValues);
        String insertNewValueSql = String.format(INSERT_SQL, logTableName,
                ApplicationProperties.LOG_SEQ_NAME.getValue(),
                tableInfo.getTableName(), "UPDATE", "NEW",
                insertNewValues);

        return String.format(UPDATING, insertOldValueSql, insertNewValueSql);
    }

    String createDeleting(MetaTableInfo tableInfo) {
        String insertOldValues = createOldInsertValues(tableInfo);
        String insertOldValueSql = String.format(INSERT_SQL, logTableName,
                ApplicationProperties.LOG_SEQ_NAME.getValue(),
                tableInfo.getTableName(), "DELETE", "OLD",
                insertOldValues);

        return String.format(DELETING, insertOldValueSql);
    }

    String createNewInsertValues(MetaTableInfo ti) {

        StringBuilder statement = ti.getColumnList().stream().collect(() -> new StringBuilder(), (sb, column) -> {
            if (sb.length() > 0) {
                sb.append(" ||','|| ");
            }
            sb.append(String.format(INSERT_VALUES, column.getColumnName(), createNewValue(column)));
        }, (sb1, sb2) -> {
            sb1.append(sb2);
        });
        return statement.toString();
    }

    String createOldInsertValues(MetaTableInfo ti) {

        StringBuilder statement = ti.getColumnList().stream().collect(() -> new StringBuilder(), (sb, column) -> {
            if (sb.length() > 0) {
                sb.append(" ||','|| ");
            }
            sb.append(String.format(INSERT_VALUES, column.getColumnName(), createOldValue(column)));
        }, (sb1, sb2) -> {
            sb1.append(sb2);
        });
        return statement.toString();
    }

    String createNewValue(ColumnInfo column) {
        StringBuilder sb = new StringBuilder();
        switch (column.getDataType()) {

        case Types.NUMERIC:
        case Types.DECIMAL:
            sb.append("TO_CHAR(:NEW.").append(column.getColumnName()).append(")");
            break;

        case Types.TIMESTAMP:
            sb.append("TO_CHAR(:NEW.").append(column.getColumnName()).append(",'YYYY/MM/DD HH24:MI:SS')");
            break;

        default:
            sb.append(":NEW.").append(column.getColumnName());
            break;
        }
        return sb.toString();
    }

    String createOldValue(ColumnInfo column) {
        StringBuilder sb = new StringBuilder();
        switch (column.getDataType()) {

        case Types.NUMERIC:
        case Types.DECIMAL:
            sb.append("TO_CHAR(:OLD.").append(column.getColumnName()).append(")");
            break;

        case Types.TIMESTAMP:
            sb.append("TO_CHAR(:OLD.").append(column.getColumnName()).append(",'YYYY/MM/DD HH24:MI:SS')");
            break;

        default:
            sb.append(":OLD.").append(column.getColumnName());
            break;
        }
        return sb.toString();
    }

}
