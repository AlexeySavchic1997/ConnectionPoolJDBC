package by.alexeysavchic;

import by.alexeysavchic.config.ApplicationProperties;
import by.alexeysavchic.exception.ApplicationException;
import by.alexeysavchic.taskBlockingForUpdate.BlockingForUpdateTask;
import by.alexeysavchic.taskBlockingForNoKeyUpdate.SelectUserTaskNoKeyBlocking;
import by.alexeysavchic.taskConcurency.ConcurrentUpdateTask;
import by.alexeysavchic.taskSimple.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Main {
    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args)
    {
        try {
            ApplicationProperties applicationProperties=ApplicationProperties.getApplicationProperties();
        } catch (ApplicationException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Cannot run program without properties");
        }

        switch (args[0]){
            case ("1"):
                Task task=new Task();
                task.deleteUserByDepartment("IT");
                break;
            case ("2"):
                long durationMillis = 10000; // 10 секунд
                long startTime = System.currentTimeMillis();
                LinkedBlockingQueue linkedBlockingQueue=new LinkedBlockingQueue<>(100);
                try(ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 0,
                        TimeUnit.SECONDS, linkedBlockingQueue, new ThreadPoolExecutor.DiscardPolicy()))
                {
                    while (System.currentTimeMillis() - startTime < durationMillis) {
                        try {
                            threadPoolExecutor.submit(new ConcurrentUpdateTask());
                        }
                        catch (Exception e)
                        {
                            logger.error(e.getMessage(),e);
                        }
                    }
                }
                break;
            case ("3"):
                BlockingForUpdateTask blockingForUpdateTask = new BlockingForUpdateTask();
                try{
                    blockingForUpdateTask.selectByCost(5000);
                }
                catch (ApplicationException e){
                    logger.error(e.getMessage(), e);
                }
                break;
            case ("4"):
                SelectUserTaskNoKeyBlocking selectUserTaskNoKeyBlocking =new SelectUserTaskNoKeyBlocking();
                try {
                    selectUserTaskNoKeyBlocking.selectUserBlock();
                }
                catch (ApplicationException e)
                {
                    logger.error(e.getMessage(),e);
                }
                break;
        }
    }
}
