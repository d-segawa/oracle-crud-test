package org.crudtest.core.repository.connection;

import org.crudtest.core.exception.JdbcException;


import java.sql.Connection;

public interface ConnectionFactory {

    Connection getConnection() throws JdbcException;

    static ConnectionFactory create() {
        return new OracleConnectionFactory();
    }
}
