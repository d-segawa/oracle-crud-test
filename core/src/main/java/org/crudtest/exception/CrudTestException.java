package org.crudtest.exception;

public class CrudTestException extends Exception {

    public CrudTestException(String message) {
        super(message);
    }

    public CrudTestException(Throwable cause) {
        super(cause);
    }

    public CrudTestException(String message, Throwable cause) {
        super(message, cause);
    }

}
