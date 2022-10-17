package org.crudtest.core.repository.oracle;

import org.crudtest.core.exception.JdbcException;
import org.crudtest.core.repository.OracleRepository;
import org.junit.jupiter.api.Test;

class OracleRepositoryTest {

    @Test
    void testSelectLogs() throws JdbcException {
        OracleRepository repo = new OracleRepository();
        repo.selectLog("CRUD_TEST_LOGS", "TRANSACTION");
    }
}
