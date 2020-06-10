package org.crudtest.datasource;

import java.sql.Connection;

import org.crudtest.exception.JdbcException;

public interface ConnectionManager {

    public Connection getConnection() throws JdbcException;

}
