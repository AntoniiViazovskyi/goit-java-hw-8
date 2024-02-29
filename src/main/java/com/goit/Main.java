package com.goit;

import com.goit.utils.FlywayRunner;

public class Main {
    public static void main(String[] args) {
        FlywayRunner.flywayMigration();
    }
}
