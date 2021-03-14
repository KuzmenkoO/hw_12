package hw_12.taskTwo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Retention(RetentionPolicy.RUNTIME)
@interface Repeat {
    int value();
}

public class CustomThreadPoolExecutor extends ThreadPoolExecutor {
    public CustomThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize, Integer.MAX_VALUE, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    @Override
    public void execute(Runnable command) {
        if (command != null) {
            Class<? extends Runnable> runnableClass = command.getClass();
            Repeat repeat = runnableClass.getAnnotation(Repeat.class);
            for (int i = 0; i < (repeat != null ? repeat.value() : 1); i++) {
                super.execute(command);
            }
        }
    }

    @Repeat(3)
    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello!");
        }
    }
}