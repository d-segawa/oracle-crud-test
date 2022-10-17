package org.crudtest.core.repository;

public class ConnectionUtil {

    static ConnectionManager manager;

    public static ConnectionManager getManager() {
        if (manager == null) {
            manager = new OracleConnectionManager();
        }
        return manager;
    }
}
