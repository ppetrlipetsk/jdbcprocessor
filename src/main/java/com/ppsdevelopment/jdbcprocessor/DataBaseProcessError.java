package com.ppsdevelopment.jdbcprocessor;

/**
 * An exception that provides information on a definitions or manipulations database
 * error or other errors.
 *
 * A {@code ppsdevelopment.DataBaseProcessError} is a subclass of {@code Exception}
 * that indicates problems that a reasonable application
 * should try to catch.
 *
 * That is, {@code Error} and its subclasses are regarded as unchecked
 * exceptions for the purposes of compile-time checking of exceptions.
 *
 * @author  PPS
 * @see DataBaseProcessor
 * @jls 11.2 Compile-Time Checking of Exceptions
 * @since   1.0
 */

public class DataBaseProcessError extends java.lang.Exception {
    public DataBaseProcessError() {
        super();
    }

    public DataBaseProcessError(String message) {
        super(message);
    }

    public DataBaseProcessError(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseProcessError(Throwable cause) {
        super(cause);
    }

    protected DataBaseProcessError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
