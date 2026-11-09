package by.alexeysavchic.taskBlockingForNoKeyUpdate;

import by.alexeysavchic.connection.ConnectionPool;
import by.alexeysavchic.exception.PoolConnectionException;
import by.alexeysavchic.taskBlockingForUpdate.NewThreadUpdateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UpdateOrder implements Runnable{

    Logger logger = LoggerFactory.getLogger(NewThreadUpdateTask.class);

    @Override
    public void run() {
        String sql = "insert into orders (order_name, summary_cost, user_id, item_id) " +
                "values ('Order15', 5000, (select id from users where username='Jenya'), (select id from items where name='item6'))";
        try(Connection connection= ConnectionPool.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)) {

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) // custom exception
        {
            logger.error(e.getMessage());
            throw new PoolConnectionException(e.getMessage(), e, false, false);
        }
        logger.info(LocalDateTime.now().toString());
    }
}
