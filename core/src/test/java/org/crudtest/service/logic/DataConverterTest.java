package org.crudtest.service.logic;

import java.util.List;

import org.crudtest.exception.JdbcException;
import org.crudtest.properties.ApplicationProperties;
import org.crudtest.repository.OracleRepository;
import org.crudtest.repository.entity.LogTable;
import org.junit.jupiter.api.Test;

class DataConverterTest {

    public static final String LOG_TABLE_NAME = ApplicationProperties.LOG_TABLE_NAME.getValue();

    @Test
    void testConverte() throws JdbcException {
        OracleRepository repo = new OracleRepository();
        List<LogTable> logs = repo.selectLog(LOG_TABLE_NAME, "TRANSACTION");
        DataConverter conv = new DataConverter();

        logs.forEach(log -> {
            conv.converte(log);
        });

    }

}
