package org.crudtest.core.service;

import org.crudtest.core.exception.JdbcException;
import org.junit.jupiter.api.Test;

class CrudTestServiceTest {

    @Test
    void testExecute() throws JdbcException {
        CrudTestService service = new CrudTestService();
        service.createTrigger("TRANSACTION");
    }

    @Test
    void testDropAllTrigger() {
        CrudTestService service = new CrudTestService();
        service.dropAllTrigger();
    }

}
