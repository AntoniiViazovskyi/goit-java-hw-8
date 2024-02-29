package com.goit.dao;

import com.goit.config.PostgresDatabase;
import com.goit.entity.Client;
import com.goit.exception.ClientDaoException;
import com.goit.exception.PostgresDatabaseException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ClientService implements ClientDao {

    private static final String CREATE_CLIENT_SQL = "insert into client (name) values (?)";
    private static final String GET_CLIENT_BY_ID_SQL = "select name from client where id = ?";
    private static final String SET_CLIENT_NAME_BY_ID = "update client set name = ? where id = ?";
    private static final String DELETE_CLIENT_BY_ID = "delete from client where id = ?";
    private static final String SELECT_ALL_CLIENTS_SQL = "select id,name from client";


    @Override
    public long create(String name) {
        if (name.length() < 2 || name.length() > 1000) {
            throw new IllegalArgumentException("Name must be more than 2 and less than 1000 characters");
        }
        long result = -1L;
        try (var connection = PostgresDatabase.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(CREATE_CLIENT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            int changedRowsCount = preparedStatement.executeUpdate();
            if (changedRowsCount == 0) {
                throw new ClientDaoException("The client was not added to the table");
            }
            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                result = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new PostgresDatabaseException(e);
        }
        return result;
    }


    @Override
    public String getById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id can't be zero or less");
        }
        String name = null;
        try (var connection = PostgresDatabase.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(GET_CLIENT_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                name = resultSet.getString(1);
            } else {
                throw new ClientDaoException(
                        "There is no Client with id = %d".formatted(id));
            }
        } catch (SQLException e) {
            throw new PostgresDatabaseException(e);
        }
        return name;
    }

    @Override
    public void setName(long id, String name) {
        if (name.length() < 2 || name.length() > 1000) {
            throw new IllegalArgumentException("Name must be more than 2 and less than 1000 characters");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("id can't be zero or less");
        }

        try (var connection = PostgresDatabase.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(SET_CLIENT_NAME_BY_ID)) {
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, id);
            int changedRowsCount = preparedStatement.executeUpdate();
            if (changedRowsCount == 0) {
                throw new ClientDaoException(
                        "The client with id = %d was not changed, or does not exist.".formatted(id));
            }
        } catch (SQLException e) {
            throw new PostgresDatabaseException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id can't be zero or less");
        }

        try (var connection = PostgresDatabase.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_CLIENT_BY_ID)) {
            preparedStatement.setLong(1, id);
            int changedRowsCount = preparedStatement.executeUpdate();
            if (changedRowsCount == 0) {
                throw new ClientDaoException(
                        "The client with id = %d was not deleted, or does not exist.".formatted(id));
            }
        } catch (SQLException e) {
            throw new PostgresDatabaseException(e);
        }
    }

    @Override
    public List<Client> listAll() {
        List<Client> list = new ArrayList<>();
        try (var connection = PostgresDatabase.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(SELECT_ALL_CLIENTS_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client(resultSet.getLong("id"),
                        resultSet.getString("name"));
                list.add(client);
            }
            return list;
        } catch (SQLException e) {
            throw new PostgresDatabaseException(e);
        }
    }
}
