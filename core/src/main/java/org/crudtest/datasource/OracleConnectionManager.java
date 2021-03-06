package org.crudtest.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import org.crudtest.exception.JdbcException;
import org.crudtest.properties.ApplicationProperties;

import oracle.jdbc.pool.OracleDataSource;

public class OracleConnectionManager implements ConnectionManager {

    @Override
    public Connection getConnection() throws JdbcException {
        try {
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setURL(
                    ApplicationProperties.DB_URL.getValue());
            dataSource.setUser(ApplicationProperties.DB_USER.getValue());
            dataSource.setPassword(ApplicationProperties.DB_PASS.getValue());

            return dataSource.getConnection();
        } catch (SQLException se) {
            throw new JdbcException(se);
        }
    }

}
