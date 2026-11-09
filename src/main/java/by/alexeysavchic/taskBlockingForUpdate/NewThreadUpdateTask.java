package by.alexeysavchic.taskBlockingForUpdate;

import by.alexeysavchic.connection.ConnectionPool;
import by.alexeysavchic.dto.Order;
import by.alexeysavchic.exception.PoolConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class NewThreadUpdateTask implements Runnable
{
    List<Order> orders;
    Logger logger = LoggerFactory.getLogger(NewThreadUpdateTask.class);

    public NewThreadUpdateTask(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void run() {
        String sql = "UPDATE orders SET summary_cost=? where id in(?) and summary_cost=0";
        try(Connection connection= ConnectionPool.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            for (Order order:orders)
            {
                preparedStatement.setInt(1,order.summaryCost());
                preparedStatement.setObject(2,order.id());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        catch (SQLException e) // custom exception
        {
            logger.error(e.getMessage());
            throw new PoolConnectionException(e.getMessage(), e, false, false);
        }
        logger.info(orders.toString());
    }
}
