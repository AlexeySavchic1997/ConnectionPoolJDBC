package by.alexeysavchic.taskBlockingForUpdate;

import by.alexeysavchic.connection.ConnectionPool;
import by.alexeysavchic.dto.Order;
import by.alexeysavchic.exception.PoolConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BlockingForUpdateTask
{
    Logger logger= LoggerFactory.getLogger(BlockingForUpdateTask.class);
    public void selectByCost(Integer costThreshold) {
        String sqlForSelect = "Select * from orders where summary_cost=? for update";
        String sqlForUpdate = "UPDATE orders SET summary_cost=? where id in(?)";
        List<Order>orders=new ArrayList<>();
        try(Connection connection= ConnectionPool.getConnection();
            PreparedStatement selectStatement=connection.prepareStatement(sqlForSelect)) {
            selectStatement.setInt(1, costThreshold);
            connection.setAutoCommit(false);
            ResultSet queryResult=selectStatement.executeQuery();
            while (queryResult.next())
            {
                Order order = new Order((UUID)queryResult.getObject("id"), queryResult.getString("order_name"),
                         queryResult.getInt("summary_cost"));
                orders.add(order);
            }
            queryResult.close();
            selectStatement.close();
            logger.info(orders.toString());
            NewThreadUpdateTask task=new NewThreadUpdateTask(orders);
            Thread thread2=new Thread(task);
            thread2.start();
            PreparedStatement updateStatement=connection.prepareStatement(sqlForUpdate);
            for (Order order:orders)
            {
                updateStatement.setInt(1,order.summaryCost()*0);
                updateStatement.setObject(2,order.id());
                updateStatement.addBatch();
            }
            updateStatement.executeBatch();
            connection.commit();
        }
        catch (SQLException e) // custom exception
        {
            throw new PoolConnectionException(e.getMessage(), e, false, false);
        }
    }
}
