package org.crudtest.core.repository;

import java.sql.Connection;

import org.crudtest.core.exception.JdbcException;

public interface ConnectionManager {

    public Connection getConnection() throws JdbcException;

}
