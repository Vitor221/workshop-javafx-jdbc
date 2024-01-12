package com.eduardo.workshopjavafxjdbc.db;

public class DbIntegrityException extends RuntimeException {

    public DbIntegrityException(String msg) {
        super(msg);
    }
}
