package org.crudtest.core.exception;

public class JdbcException extends CrudTestException {

    public JdbcException(String message) {
        super(message);
    }

    public JdbcException(Throwable cause) {
        super(cause);
    }

    public JdbcException(String message, Throwable cause) {
        super(message, cause);
    }

}
