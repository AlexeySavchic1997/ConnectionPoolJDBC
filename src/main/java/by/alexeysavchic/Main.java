package by.alexeysavchic;

import by.alexeysavchic.config.ApplicationProperties;
import by.alexeysavchic.exception.ApplicationException;
import by.alexeysavchic.exception.ErrorMessages;
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
        //Argument for start application passes in docker-compose
        try {
            ApplicationProperties applicationProperties=ApplicationProperties.getApplicationProperties();
        } catch (ApplicationException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(ErrorMessages.illegalState);
        }

        //Cases are segmented in packages. Every case matches with separate package
        switch (args[0]){
               //Simple crud
            case ("1"):
                Task task=new Task();
                task.selectUserByDepartment("IT");
                break;
                //task for loading threads and checking metrics in jfr file
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

        }
    }
}
