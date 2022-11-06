package org.crudtest.core.repository.connection;

import oracle.jdbc.pool.OracleDataSource;
import org.crudtest.core.exception.JdbcException;
import org.crudtest.core.properties.ApplicationProperties;

import java.sql.Connection;
import java.sql.SQLException;

public class OracleConnectionFactory implements ConnectionFactory {

    @Override
    public Connection getConnection() throws JdbcException {

        try{
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
