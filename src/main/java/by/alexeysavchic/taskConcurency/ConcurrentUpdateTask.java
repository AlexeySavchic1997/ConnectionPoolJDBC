package by.alexeysavchic.taskConcurency;

import by.alexeysavchic.connection.ConnectionPool;
import by.alexeysavchic.exception.PoolConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConcurrentUpdateTask implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentUpdateTask.class);
    @Override
    public void run() {
        String sql = "UPDATE users SET request_counter=request_counter+1";
        try(Connection connection= ConnectionPool.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
        }
        catch (SQLException e) // custom exception
        {
            throw new PoolConnectionException(e.getMessage(), e, false, false);
        }
    }
}
