package by.alexeysavchic.connection;

import by.alexeysavchic.dto.Department;
import by.alexeysavchic.dto.Order;
import by.alexeysavchic.dto.User;
import by.alexeysavchic.exception.ErrorMessages;
import by.alexeysavchic.exception.PoolConnectionException;
import by.alexeysavchic.exception.UnacceptableTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConnectionManager {
    Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    public List<User> getUsers(String sql, Object... placeHolders) {
        List<?> objects = getEntities(EntityType.USER, sql, placeHolders);
        List<User> users = (List<User>) objects;
        return users;
    }

    public List<Order> getOrders(String sql, Object... placeHolders) {
        List<?> objects = getEntities(EntityType.ORDER, sql, placeHolders);
        List<Order> orders = (List<Order>) objects;
        return orders;
    }

    public List<Department> getDepartments(String sql, Object... placeHolders) {
        List<?> objects = getEntities(EntityType.DEPARTMENT, sql, placeHolders);
        List<Department> departments = (List<Department>) objects;
        return departments;
    }

    public Integer executeOperation(String sql, Object... placeHolders) {
        Integer affectedRows;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            fillPlaceholders(preparedStatement, placeHolders);
            affectedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) // custom exception
        {
            logger.error(e.getMessage());
            throw new PoolConnectionException(e.getMessage(), e, false, false);
        }
        return affectedRows;
    }

    public void createTable(String sql) {
        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
        } catch (SQLException e) // custom exception
        {
            throw new PoolConnectionException(e.getMessage(), e, false, false);
        }
        logger.info("table are created");
    }

    private List<?> getEntities(EntityType type, String sql, Object... placeHolders) {
        List<Object> entities = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            fillPlaceholders(preparedStatement, placeHolders);
            ResultSet queryResult = preparedStatement.executeQuery();
            switch (type) {
                case USER -> {
                    addUsers(queryResult, entities);
                }
                case ORDER -> {
                    addOrders(queryResult, entities);
                }
                case DEPARTMENT -> {
                    addDepartments(queryResult, entities);
                }
            }
        } catch (SQLException e) // custom exception
        {
            logger.error(e.getMessage());
            throw new PoolConnectionException(e.getMessage(), e, false, false);
        }
        return entities;
    }

    private void fillPlaceholders(PreparedStatement preparedStatement, Object... placeHolders) throws SQLException {
        for (int i = 0; i < placeHolders.length; i++) {
            switch (placeHolders[i]) {
                case String st -> preparedStatement.setString(i + 1, st);
                case Integer in -> preparedStatement.setInt(i + 1, in);
                default -> throw new UnacceptableTypeException(ErrorMessages.unacceptableType, null, false, false);
            }
        }
    }

    private void addUsers(ResultSet queryResult, List<Object> entities) throws SQLException {
        while (queryResult.next()) {
            User user = new User((UUID) queryResult.getObject("id"), queryResult.getString("username"),
                    queryResult.getString("email"), queryResult.getInt("request_counter"),
                    (UUID) queryResult.getObject("department_id"));
            entities.add(user);
        }
        queryResult.close();
    }

    private void addOrders(ResultSet queryResult, List<Object> entities) throws SQLException {
        while (queryResult.next()) {
            Order order = new Order((UUID) queryResult.getObject("id"), queryResult.getString("order_name"),
                    queryResult.getInt("summary_cost"));
            entities.add(order);
        }
        queryResult.close();
    }

    private void addDepartments(ResultSet queryResult, List<Object> entities) throws SQLException {
        while (queryResult.next()) {
            Order order = new Order((UUID) queryResult.getObject("id"), queryResult.getString("order_name"),
                    queryResult.getInt("summary_cost"));
            entities.add(order);
        }
        queryResult.close();
    }
}
