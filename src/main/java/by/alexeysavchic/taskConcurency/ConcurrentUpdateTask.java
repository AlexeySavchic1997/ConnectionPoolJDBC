package by.alexeysavchic.taskConcurency;

import by.alexeysavchic.connection.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentUpdateTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ConcurrentUpdateTask.class);
    private final static ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public void run() {
        String sql = "UPDATE users SET request_counter=request_counter+1";
        connectionManager.executeOperation(sql);
    }
}
