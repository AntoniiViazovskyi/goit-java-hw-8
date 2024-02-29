package com.goit.config;


import com.goit.exception.PostgresDatabaseException;
import com.goit.utils.PropertiesUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public final class PostgresDatabase {

    private static final String URL_KEY = "postgres_db.url";
    private static final String USERNAME_KEY = "postgres_db.username";
    private static final String PASSWORD_KEY = "postgres_db.password";
    private static final String DRIVER_KEY = "postgres_db.driver";
    private static PostgresDatabase instance;
    private static final ComboPooledDataSource dataSource;

    static {
        try {
            dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(PropertiesUtil.get(DRIVER_KEY));
            dataSource.setJdbcUrl(PropertiesUtil.get(URL_KEY));
            dataSource.setUser(PropertiesUtil.get(USERNAME_KEY));
            dataSource.setPassword(PropertiesUtil.get(PASSWORD_KEY));
        } catch (PropertyVetoException e) {
            throw new PostgresDatabaseException(e);
        }
    }

    private PostgresDatabase() {
    }

    public static PostgresDatabase getInstance() {
        if (instance == null) {
            instance = new PostgresDatabase();
        }
        return instance;

    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new PostgresDatabaseException(e);
        }
    }

}
