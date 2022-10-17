package org.crudtest.core.util;

import org.crudtest.core.datasource.ConnectionManager;
import org.crudtest.core.datasource.OracleConnectionManager;

public class ConnectionUtil {

    static ConnectionManager manager;

    public static ConnectionManager getManager() {
        if (manager == null) {
            manager = new OracleConnectionManager();
        }
        return manager;
    }
}
