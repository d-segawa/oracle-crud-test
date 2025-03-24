package org.crudtest.core.service.logic;

import java.util.List;

import org.crudtest.core.exception.JdbcException;
import org.crudtest.core.properties.ApplicationProperties;
import org.crudtest.core.repository.OracleRepository;
import org.crudtest.core.repository.SqlLiterals;
import org.crudtest.core.repository.entity.LogTable;
import org.junit.jupiter.api.Test;

class DataConverterTest {

    public static final String LOG_TABLE_NAME = ApplicationProperties.LOG_TABLE_NAME.getValue();

    @Test
    void testConverte() throws JdbcException {
        OracleRepository repo = new OracleRepository();
        List<LogTable> logs = repo.selectLog(SqlLiterals.selectLog_sql, LOG_TABLE_NAME, "TRANSACTION");
        DataConverter conv = new DataConverter();

        logs.forEach(log -> {
            conv.converte(log);
        });

    }

}
