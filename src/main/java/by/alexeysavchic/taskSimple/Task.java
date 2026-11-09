package by.alexeysavchic.taskSimple;

import by.alexeysavchic.connection.ConnectionPool;
import by.alexeysavchic.dto.User;
import by.alexeysavchic.exception.PoolConnectionException;
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

public class Task
{
    Logger logger= LoggerFactory.getLogger(Task.class);
    public void selectUserByDepartment(String departmentName) {
        String sql = "Select * from users u join departments d on u.department_id=d.id where d.department_name=?";
        List<User>users=new ArrayList<>();
        try(Connection connection= ConnectionPool.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            preparedStatement.setString(1, departmentName);
            ResultSet queryResult=preparedStatement.executeQuery();
            while (queryResult.next())
            {
                User user= new User((UUID)queryResult.getObject("id"), queryResult.getString("username"),
                        queryResult.getString("email"), queryResult.getInt("request_counter"),
                        (UUID)queryResult.getObject("department_id"));
                users.add(user);
            }
            queryResult.close();
        }
        catch (SQLException e) // custom exception
        {
            logger.error(e.getMessage());
            throw new PoolConnectionException(e.getMessage(), e, false, false);
        }
        logger.info(users.toString());
    }

    public void deleteUserByDepartment(String departmentName) {
        String sql = "Delete from users where department_id in (Select u.department_id from users u join departments d on u.department_id=d.id where d.department_name=?)";
        int rowAmount;
        try(Connection connection= ConnectionPool.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql))
        {
            preparedStatement.setString(1, departmentName);
            rowAmount=preparedStatement.executeUpdate();
        }
        catch (SQLException e) // custom exception
        {
            throw new PoolConnectionException(e.getMessage(), e, false, false);
        }
        logger.info("rows are deleted: {}", rowAmount);
    }

    public void createSubscriptionTable()
    {
        String sql = "create table if not exists subscriptions," +
                "(id uuid primary key default uuidv7()," +
                "subscription_name varchar(30) unique not null)";

        try(Connection connection= ConnectionPool.getConnection();
            Statement statement =connection.createStatement();)
        {
            statement.executeUpdate(sql);
        }
        catch (SQLException e) // custom exception
        {
            throw new PoolConnectionException(e.getMessage(), e, false, false);
        }
        logger.info("table are created");
    }
}
