package org.crudtest.core.repository;

import static org.crudtest.core.repository.SqlLiterals.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.crudtest.core.properties.ApplicationProperties;
import org.crudtest.core.repository.entity.LogTable;
import org.crudtest.core.exception.JdbcException;
import org.crudtest.core.log.AppLogger;
import org.crudtest.core.util.DateUtil;

public class OracleRepository {

    private static AppLogger log = AppLogger.getLogger(OracleRepository.class);

    static Pattern CRLF = Pattern.compile("\r\n");

    public boolean existsTable(String tableName) throws JdbcException {

        Integer result = DataBaseAssessor.<Integer> execute(countTable_sql,

                ps -> ps.setString(1, tableName), rs -> {
                    Integer count = 0;
                    if (rs.next()) {
                        count = rs.getInt(1);
                    }
                    return count;

                });
        return result > 0;
    }

    public boolean existsSequence(String sequenceName) throws JdbcException {

        Integer result = DataBaseAssessor.<Integer> execute(countSequence_sql,

                ps -> ps.setString(1, sequenceName), rs -> {

                    Integer count = 0;
                    if (rs.next()) {
                        count = rs.getInt(1);
                    }
                    return count;

                });
        return result > 0;
    }

    public void executeDdl(String ddl) throws JdbcException {
        DataBaseAssessor.<Object> execute(ddl, rs -> null);
    }

    public List<String> selectTableName(String triggerName) throws JdbcException {

        return DataBaseAssessor.<List<String>> execute(selectTableName_sql, ps -> {
            ps.setString(1, triggerName);

        }, rs -> {

            List<String> tableList = new ArrayList<>();
            while (rs.next()) {
                tableList.add(rs.getString("TABLE_NAME"));
            }
            tableList.forEach(t -> log.info(t));
            return tableList;

        });
    }

    public List<String> selectAllTriggerName(String mngTableName) throws JdbcException {

        return DataBaseAssessor.<List<String>> execute(String.format(selectTrigerName_sql, mngTableName), rs -> {

            List<String> tableList = new ArrayList<>();
            while (rs.next()) {
                tableList.add(rs.getString("TRIGGER_NAME"));
            }
            tableList.forEach(t -> log.info(t));
            return tableList;

        });
    }

    public List<LogTable> selectLog(String tableName, String targetTableName) throws JdbcException {

        String sql = String.format(selectLog_sql, tableName);

        List<LogTable> logsTables = DataBaseAssessor.<List<LogTable>> execute(sql,

                ps -> {
                    ps.setString(1, targetTableName);

                }, rs -> {

                    List<LogTable> resultList = new ArrayList<>();
                    while (rs.next()) {
                        LogTable logs = new LogTable();
                        logs.setId(rs.getString("ID"));
                        logs.setCrudType(rs.getString("CRUD_TYPE"));
                        logs.setHistoryType(rs.getString("HISTORY_TYPE"));
                        logs.setTableName(rs.getString("TABLE_NAME"));
                        logs.setData(replaceCRLF(clobToString(rs.getClob("DATA"))));
                        logs.setInserDate(DataBaseAssessor.sqlToLocalDateTime(rs.getDate("INSERT_DATE")).orElse(null));
                        log.info(logs.toString());
                        resultList.add(logs);
                    }
                    return resultList;

                });
        return logsTables;
    }

    public int deleteLog(String tableName, String targetTableName) throws JdbcException {

        String sql = String.format(deleteLog_sql, tableName);

        return DataBaseAssessor.executeUpdate(sql, ps -> {
            ps.setString(1, targetTableName);
        });
    }

    public int insertManageTable(String triggerName) throws JdbcException {

        String sql = String.format(insertManageTable_sql, ApplicationProperties.MANAGE_TABLE_NAME.getValue(),
                DateUtil.formattedSysDate(),
                ApplicationProperties.LOG_SEQ_NAME.getValue());

        java.sql.Date sysdate = new java.sql.Date(System.currentTimeMillis());

        return DataBaseAssessor.executeUpdate(sql, ps -> {
            ps.setString(1, triggerName);
            ps.setDate(2, sysdate);
            ps.setDate(3, sysdate);
        });

    }

    String clobToString(Clob clob) throws SQLException, IOException {
        if (clob == null) {
            return null;
        }
        int length;
        if (clob.length() > Integer.MAX_VALUE) {
            length = Integer.MAX_VALUE;
        } else {
            length = (int) clob.length();
        }
        char[] cArray = new char[length];
        BufferedReader bf = new BufferedReader(clob.getCharacterStream());
        bf.read(cArray);
        return new String(cArray);
    }

    String replaceCRLF(String msg) {
        if (msg == null || msg.length() == 0) {
            return msg;
        }
        return CRLF.matcher(msg).replaceAll("");
    }
}
