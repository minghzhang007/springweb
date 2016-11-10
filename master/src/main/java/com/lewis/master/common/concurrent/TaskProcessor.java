package com.lewis.master.common.concurrent;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2016/11/10.
 */
public class TaskProcessor {

    private Logger logger = LoggerFactory.getLogger(TaskProcessor.class);

    private ExecutorService executorService;

    private int coreSize;

    private int poolSize;

    private String domain;

    public TaskProcessor(String domain, int coreSize, int poolSize) {
        this.domain = domain;
        this.coreSize = coreSize;
        this.poolSize = poolSize;
        init();
    }

    public TaskProcessor(ExecutorService executorService){
        this.executorService = executorService;
        addHook();
    }

    private void init() {
        createThreadPool();
        addHook();
    }

    private void createThreadPool() {
        LinkedBlockingQueue queue = new LinkedBlockingQueue(coreSize);
        executorService = new ThreadPoolExecutor(coreSize,
                poolSize,
                60L,
                TimeUnit.SECONDS,
                queue,
                new TaskProcessor.DefaulThreadFactory(this.domain),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    private void addHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (executorService != null) {
                    try {
                        executorService.shutdown();
                        executorService.awaitTermination(5L, TimeUnit.MINUTES);
                    } catch (InterruptedException e) {
                        logger.warn("Interrupted where shutting down the processor executor :\n{}", e);
                    }
                }
            }
        });
    }


    static class DefaulThreadFactory implements ThreadFactory {

        static final AtomicInteger poolNumber = new AtomicInteger(1);

        static final AtomicInteger threadNumber = new AtomicInteger(1);

        final ThreadGroup threadGroup;

        final String namePrefix;

        DefaulThreadFactory(String domain) {
            SecurityManager securityManager = System.getSecurityManager();
            threadGroup = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = domain + "-taskProcessor-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(threadGroup, r, namePrefix + threadNumber.getAndIncrement(), 0L);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            return t;
        }
    }


}
