package org.crudtest.repository.oracle;

import org.crudtest.exception.JdbcException;
import org.crudtest.repository.OracleRepository;
import org.junit.jupiter.api.Test;

class OracleRepositoryTest {

    @Test
    void testSelectLogs() throws JdbcException {
        OracleRepository repo = new OracleRepository();
        repo.selectLog("CRUD_TEST_LOGS", "TRANSACTION");
    }
}
