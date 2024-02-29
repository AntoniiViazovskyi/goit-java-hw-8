package com.goit.utils;

import org.flywaydb.core.Flyway;

public class FlywayRunner {

    private static final String URL_KEY = "postgres_db.url";
    private static final String USERNAME_KEY = "postgres_db.username";
    private static final String PASSWORD_KEY = "postgres_db.password";

    private FlywayRunner() {
    }

    public static void flywayMigration() {
        var flyway = Flyway.configure()
                .dataSource(PropertiesUtil.get(URL_KEY),
                            PropertiesUtil.get(USERNAME_KEY),
                            PropertiesUtil.get(PASSWORD_KEY))
                .load();
        flyway.migrate();
    }
}
