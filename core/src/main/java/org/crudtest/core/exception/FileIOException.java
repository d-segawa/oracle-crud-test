package org.crudtest.core.exception;

public class FileIOException extends CrudTestException {

    public FileIOException(String message) {
        super(message);
    }

    public FileIOException(Throwable cause) {
        super(cause);
    }

    public FileIOException(String message, Throwable cause) {
        super(message, cause);
    }

}
