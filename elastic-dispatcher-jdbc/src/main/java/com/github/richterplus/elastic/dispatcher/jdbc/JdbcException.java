package com.github.richterplus.elastic.dispatcher.jdbc;

public class JdbcException extends RuntimeException {

    public JdbcException(String msg) {
        super(msg);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
