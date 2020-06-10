package org.crudtest.util;

import org.crudtest.datasource.ConnectionManager;
import org.crudtest.datasource.OracleConnectionManager;

public class ConnectionUtil {

    static ConnectionManager manager;

    public static ConnectionManager getManager() {
        if (manager == null) {
            manager = new OracleConnectionManager();
        }
        return manager;
    }
}
