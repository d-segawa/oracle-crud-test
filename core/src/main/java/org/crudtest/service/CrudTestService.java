package org.crudtest.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.crudtest.exception.JdbcException;
import org.crudtest.log.AppLogger;
import org.crudtest.properties.ApplicationProperties;
import org.crudtest.repository.OracleRepository;
import org.crudtest.service.bean.MetaTableInfo;
import org.crudtest.service.logic.DDL;
import org.crudtest.service.logic.OracleMetaDataCreator;
import org.crudtest.service.logic.OracleTriggerCreator;
import org.crudtest.util.DbUtil;

public class CrudTestService {

    private static AppLogger log = AppLogger.getLogger(CrudTestService.class);

    final OracleRepository oracleRepository;

    final OracleMetaDataCreator oracleMetaDataCreator;

    public static final String SCHEMA = ApplicationProperties.DB_USER.getValue().toUpperCase();

    public static final String LOG_TABLE_NAME = ApplicationProperties.LOG_TABLE_NAME.getValue();

    public static final String TRIGGER_PRIFIX = ApplicationProperties.TRIGGER_NAME_PRIFIX.getValue();

    public static final String LOG_TABLE_SEQ_NAME = ApplicationProperties.LOG_SEQ_NAME.getValue();

    public static final String MANAGE_TABLE_NAME = ApplicationProperties.MANAGE_TABLE_NAME.getValue();

    public CrudTestService() {
        this.oracleRepository = new OracleRepository();
        this.oracleMetaDataCreator = new OracleMetaDataCreator();
    }

    public String create(String tableName) {
        if (tableName == null || tableName.length() == 0) {
            return "テーブル名が空です";
        }

        try {
            createLogSequenceNonExists();
            createLogTableNonExists();
            createMangageTableNonExists();

            String upperTableName = tableName.toUpperCase();

            List<String> allTriggerNames = oracleRepository.selectAllTriggerName(MANAGE_TABLE_NAME);
            String triggerName = TRIGGER_PRIFIX + upperTableName;

            if (allTriggerNames.contains(triggerName)) {
                return "既に作成されています";

            }
            if (!existsTable(upperTableName)) {
                return "存在しないテーブルが指定されました";
            }

            createTrigger(upperTableName);
            oracleRepository.insertManageTable(triggerName);

        } catch (Exception e) {
            log.error("Error occured create trigger.", e);
            return "エラーが発生しました。 : " + e.getMessage();
        }

        return "";
    }

    public List<String> selectCreatedTriggerTableName() {
        try {
            return oracleRepository.selectAllTriggerName(MANAGE_TABLE_NAME).stream().map(triggerName -> {
                return triggerName.substring(TRIGGER_PRIFIX.length());
            }).collect(() -> new ArrayList<String>(), (list, name) -> {
                list.add(name);
            }, (list1, list2) -> {
                list1.addAll(list2);
            });
        } catch (Exception e) {
            log.error("Error occured select all trigger.", e);
            return new ArrayList<>();
        }
    }

    public String dropAllTrigger() {

        List<String> allTriggerNames;

        try {
            allTriggerNames = oracleRepository.selectAllTriggerName(MANAGE_TABLE_NAME);
        } catch (JdbcException e) {
            log.error("Error occured select manage_table.", e);
            return e.getMessage();
        }

        for (String trigger : allTriggerNames) {
            try {
                oracleRepository.executeDdl(DDL.createDropTrigger(trigger));
            } catch (JdbcException je) {
                if (je.getCause() instanceof SQLException) {
                    SQLException se = (SQLException) je.getCause();
                    if (4080 == se.getErrorCode()) {
                        //ORA-04080：トリガーが存在しないの場合は、無視する
                        log.warn(String.format("ORA-04080: トリガー%sが存在しません。", trigger), se);
                        continue;
                    }
                }
                log.error("Error occrued drop trigger.", je);
                return je.getMessage();
            }
        }

        try {
            oracleRepository.executeDdl("TRUNCATE TABLE " + MANAGE_TABLE_NAME);
        } catch (JdbcException e) {
            log.error("Error occured truncate manage_table.", e);
            return e.getMessage();
        }

        return "";
    }

    public void truncateLogTable() {
        try {
            oracleRepository.executeDdl("TRUNCATE TABLE " + LOG_TABLE_NAME);
        } catch (JdbcException e) {
            log.error("Error occured truncate log table.", e);
        }
    }

    public boolean existsLogTable() {
        try {
            return oracleRepository.existsTable(LOG_TABLE_NAME);
        } catch (JdbcException e) {
            log.error("Error occured exists table", e);
            return false;
        }
    }

    public boolean existsMngTable() {
        try {
            return oracleRepository.existsTable(MANAGE_TABLE_NAME);
        } catch (JdbcException e) {
            log.error("Error occured exists table", e);
            return false;
        }
    }

    protected void createTrigger(String tableName) throws JdbcException {

        MetaTableInfo metaTableInfo = DbUtil.getMetaData(
                oracleMetaDataCreator.create(SCHEMA, tableName));

        String triggerName = TRIGGER_PRIFIX + tableName;

        OracleTriggerCreator oracleTriggerCreator = new OracleTriggerCreator(triggerName,
                LOG_TABLE_NAME);
        String createdTriggerDdl = oracleTriggerCreator.create(metaTableInfo);
        oracleRepository.executeDdl(createdTriggerDdl);
    }

    protected void createMangageTableNonExists() throws JdbcException {
        if (!oracleRepository.existsTable(MANAGE_TABLE_NAME)) {
            String createdDdl = DDL
                    .createManagementTable(MANAGE_TABLE_NAME);
            oracleRepository.executeDdl(createdDdl);
        }
    }

    protected boolean existsTable(String tableName) throws JdbcException {
        return oracleRepository.existsTable(tableName);
    }

    protected void createLogTableNonExists() throws JdbcException {
        if (!oracleRepository.existsTable(LOG_TABLE_NAME)) {
            String createdDdl = DDL
                    .createLogTable(LOG_TABLE_NAME);
            oracleRepository.executeDdl(createdDdl);
        }
    }

    protected void createLogSequenceNonExists() throws JdbcException {
        if (!oracleRepository.existsSequence(LOG_TABLE_SEQ_NAME)) {
            String createdDdl = DDL.createSeq(LOG_TABLE_SEQ_NAME);
            oracleRepository.executeDdl(createdDdl);
        }
    }

    @Deprecated
    protected int deleteLogsTable(String tableName) throws JdbcException {
        return oracleRepository.deleteLog(LOG_TABLE_NAME, tableName);
    }

}
