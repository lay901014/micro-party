package edu.sjtu.party.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: lay
 * @create: 2021/08/20 09:16
 **/
public class ThreadPoolManager {

    private static final int pool_size = Runtime.getRuntime().availableProcessors() * 5;

    public static ExecutorService[] executors = new ExecutorService[pool_size];

    static {
        for (int i = 0; i < pool_size; i++) {
            executors[i] = Executors.newSingleThreadExecutor(new CustomizeThreadPoolFactory("election-pool", true));
        }
    }

    public static void execute(String identify, Runnable runnable) {
        executors[Math.abs(identify.hashCode() % pool_size)].execute(runnable);
    }


    public static class CustomizeThreadPoolFactory implements ThreadFactory {

        private final AtomicInteger count = new AtomicInteger(0);

        public CustomizeThreadPoolFactory(String nameFormat, Boolean daemon) {
            this.nameFormat = nameFormat;
            this.daemon = daemon;
        }

        private String nameFormat;

        private Boolean daemon;


        @Override
        public Thread newThread(Runnable r) {
            Thread thread = Executors.defaultThreadFactory().newThread(r);

            if(StringUtils.isNotEmpty(nameFormat)) {
                thread.setName(format(nameFormat, count.incrementAndGet()));
            }

            if(daemon != null) {
                thread.setDaemon(daemon);
            }

            return thread;
        }

        public static String format(String nameFormat, Object... args) {
            return String.format(Locale.ROOT, nameFormat, args);
        }
    }

}
