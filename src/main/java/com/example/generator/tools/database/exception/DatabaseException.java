package com.example.generator.tools.database.exception;

import java.sql.SQLException;

public class DatabaseException extends RuntimeException {
    public DatabaseException(SQLException exception) {
        super(exception);
    }
}
