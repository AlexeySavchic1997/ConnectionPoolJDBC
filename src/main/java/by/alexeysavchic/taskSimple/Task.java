package by.alexeysavchic.taskSimple;

import by.alexeysavchic.connection.ConnectionManager;
import by.alexeysavchic.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Task {
    private final static ConnectionManager connectionManager = new ConnectionManager();
    Logger logger = LoggerFactory.getLogger(Task.class);

    public void selectUserByDepartment(String departmentName) {
        String sql = "Select * from users u join departments d on u.department_id=d.id where d.department_name=?";
        List<User> users = connectionManager.getUsers(sql, departmentName);
        logger.info(users.toString());
    }

    public void deleteUserByDepartment(String departmentName) {
        String sql = "Delete from users where department_id in (Select u.department_id from users u join departments d on u.department_id=d.id where d.department_name=?)";
        int rowAmount = connectionManager.executeOperation(sql, departmentName);
        logger.info("rows are deleted: {}", rowAmount);
    }

    public void createSubscriptionTable() {
        String sql = "create table if not exists subscriptions" +
                "(id uuid primary key default uuidv7()," +
                "subscription_name varchar(30) unique not null)";

        connectionManager.createTable(sql);
    }
}
