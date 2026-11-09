package by.alexeysavchic.taskBlockingForNoKeyUpdate;

import by.alexeysavchic.connection.ConnectionPool;
import by.alexeysavchic.dto.User;
import by.alexeysavchic.exception.PoolConnectionException;
import by.alexeysavchic.taskSimple.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SelectUserTaskNoKeyBlocking
{
    Logger logger= LoggerFactory.getLogger(Task.class);
    public void selectUserBlock() {
        String sql = "Select * from users for no key update";
        List<User> users=new ArrayList<>();
        try(Connection connection= ConnectionPool.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            ResultSet queryResult=preparedStatement.executeQuery();
            while (queryResult.next())
            {
                User user= new User((UUID)queryResult.getObject("id"), queryResult.getString("username"),
                        queryResult.getString("email"), queryResult.getInt("request_counter"),
                        (UUID)queryResult.getObject("department_id"));
                users.add(user);
            }
            queryResult.close();
            Thread thread1=new Thread(new UpdateOrder());
            thread1.start();
            Thread.currentThread().sleep(2000);
            logger.info(LocalDateTime.now().toString());
            connection.commit();
        }
        catch (SQLException e) // custom exception
        {
            logger.error(e.getMessage());
            throw new PoolConnectionException(e.getMessage(), e, false, false);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
