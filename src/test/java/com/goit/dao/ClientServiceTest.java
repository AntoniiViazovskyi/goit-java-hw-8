package com.goit.dao;


import com.goit.config.PostgresDatabase;
import com.goit.exception.ClientDaoException;
import com.goit.utils.FlywayRunner;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

class ClientServiceTest {

    ClientService clientService = new ClientService();

    @BeforeAll
    static void prepare() {
        FlywayRunner.flywayMigration();
    }


    @Test
    void createClientTestReturningCorrectId() {
        Assertions.assertEquals(6, clientService.create("Name"));
    }

    @Test
    void createClientTestReturningExceptionIncorrectName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> clientService.create("N"));
    }

    @Test
    void getByIdTestReturningCorrectName() {
        Assertions.assertEquals("Mary", clientService.getById(1));
    }

    @Test
    void getByIdTestThrowingExceptionIncorrectId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> clientService.getById(-1));
    }

    @Test
    void getByIdTestThrowingExceptionMissingClient() {
        Assertions.assertThrows(ClientDaoException.class, () -> clientService.getById(100));
    }

    @Test
    void setNameTestCorrectNameChange() {
        String expectedName = "Joe";
        int idToUse = 2;
        clientService.setName(idToUse, "Joe");
        Assertions.assertEquals(expectedName, clientService.getById(idToUse));
    }

    @Test
    void setNameTestThrowingExceptionMissingClient() {
        Assertions.assertThrows(ClientDaoException.class, () -> clientService.setName(100, "Joe"));
    }

    @Test
    void setNameTestThrowingExceptionIncorrectId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> clientService.setName(-1, "Joe"));
    }

    @Test
    void setNameTestReturningExceptionIncorrectName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> clientService.setName(2, "J"));
    }

    @Test
    void deleteByIdTestCorrectDeleting() {
        int idToUse = 1;
        clientService.deleteById(idToUse);
        Assertions.assertThrows(ClientDaoException.class, () -> clientService.deleteById(idToUse));
    }

    @Test
    void deleteByIdTestThrowingExceptionMissingClient() {
        Assertions.assertThrows(ClientDaoException.class, () -> clientService.deleteById(100));
    }

    @Test
    void deleteByIdTestThrowingExceptionIncorrectId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> clientService.deleteById(-1));
    }


    @AfterAll
    static void clear() throws SQLException, IOException {
        clearTables();
    }

    private static void clearTables() throws SQLException, IOException {
        try (var connection = PostgresDatabase.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(Files.readString(
                     Path.of("src/test/resources/sql/drop_tables.sql"), StandardCharsets.UTF_8))) {
            preparedStatement.executeUpdate();
        }
    }
}
