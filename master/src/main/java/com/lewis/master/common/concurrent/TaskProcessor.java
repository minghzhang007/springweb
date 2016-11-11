package com.lewis.master.common.concurrent;


import com.lewis.master.common.utils.ArrayUtil;
import com.lewis.master.common.utils.ListUtil;
import com.lewis.master.common.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
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

    public TaskProcessor(ExecutorService executorService) {
        this.executorService = executorService;
        addHook();
    }

    /**
     * 执行TaskCallback并等待执行结果
     *
     * @param taskList
     * @return 执行结果
     */
    public <T> List<T> executeTask(List<TaskCallback<T>> taskList) {
        TaskCallback[] taskCallbacks = new TaskCallback[taskList.size()];
        taskList.toArray(taskCallbacks);
        return executeTask(taskCallbacks);
    }

    public <T> List<T> executeTask(TaskCallback<T>... taskCallbacks) {
        List<T> resultList = new LinkedList<T>();
        if (ArrayUtil.isNotEmpty(taskCallbacks)) {
            final CountDownLatch latch = new CountDownLatch(taskCallbacks.length);
            List<Future<T>> futureList = ListUtil.newArrayList();
            for (final TaskCallback<T> taskCallback : taskCallbacks) {
                futureList.add(executorService.submit(new Callable<T>() {
                    public T call() throws Exception {
                        try {
                            return taskCallback.doCallback();
                        } finally {
                            latch.countDown();
                        }
                    }
                }));
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                logger.info("executing task is interrupted.");
            }
            for (Future<T> future : futureList) {
                try {
                    T t = future.get();
                    if (t != null) {
                        resultList.add(t);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("future.get() occur ex ", e);
                }
            }
        }
        return resultList;
    }

    /**
     * 执行任务，无需等待返回
     *
     * @param taskList
     */
    public void asynExecuteTask(List<TaskCallback<?>> taskList) {
        TaskCallback[] taskCallbacks = new TaskCallback[taskList.size()];
        taskList.toArray(taskCallbacks);
        asynExecuteTask(taskCallbacks);
    }

    public void asynExecuteTask(TaskCallback... taskCallbacks) {
        if (ArrayUtil.isNotEmpty(taskCallbacks)) {
            for (final TaskCallback taskCallback : taskCallbacks) {
                executorService.execute(new Runnable() {
                    public void run() {
                        try {
                            taskCallback.doCallback();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }
    }


    public <T> Map<String, T> executeIdentityTaskCallbacks(List<IdentityTaskCallback<T>> taskCallbackList) {
        IdentityTaskCallback[] taskCallbackArray = new IdentityTaskCallback[taskCallbackList.size()];
        taskCallbackList.toArray(taskCallbackArray);
        return executeIdentityTaskCallbacks(taskCallbackArray);
    }

    public <T> Map<String, T> executeIdentityTaskCallbacks(IdentityTaskCallback<T>... taskCallbacks) {
        Map<String, T> retMap = MapUtil.newHashMap();
        if (ArrayUtil.isNotEmpty(taskCallbacks)) {
            final CountDownLatch latch = new CountDownLatch(taskCallbacks.length);
            Map<String, Future<T>> futureMap = MapUtil.newHashMap();
            for (final IdentityTaskCallback<T> taskCallback : taskCallbacks) {
                Future<T> future = executorService.submit(new Callable<T>() {
                    public T call() throws Exception {
                        try {
                            return taskCallback.doCallback();
                        } finally {
                            latch.countDown();
                        }
                    }
                });
                futureMap.put(taskCallback.identity(), future);
            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                logger.info("executing task is interrupted.");
            }
            if (MapUtil.isNotEmpty(futureMap)) {
                Iterator<Map.Entry<String, Future<T>>> it = futureMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Future<T>> entry = it.next();
                    try {
                        T t = entry.getValue().get();
                        if (t != null) {
                            retMap.put(entry.getKey(), t);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return retMap;
    }

    public <T> List<T> executeTaskByConcurrentControl(int concurrentCount, List<TaskCallback<T>> taskList) {
        TaskCallback[] taskCallbacks = new TaskCallback[taskList.size()];
        taskList.toArray(taskCallbacks);
        return executeTaskByConcurrentControl(concurrentCount, taskCallbacks);
    }

    public <T> List<T> executeTaskByConcurrentControl(int concurrentCount, TaskCallback<T>... taskCallbacks) {
        List<T> retList = new LinkedList<T>();
        if (ArrayUtil.isNotEmpty(taskCallbacks)) {
            final Semaphore semaphore = new Semaphore(concurrentCount);
            List<Future<T>> futureList = new ArrayList<Future<T>>(taskCallbacks.length);
            for (final TaskCallback<T> taskCallback : taskCallbacks) {
                Future<T> future = executorService.submit(new Callable<T>() {
                    public T call() throws Exception {
                        try {
                            semaphore.acquire();
                            return taskCallback.doCallback();
                        } finally {
                            semaphore.release();
                        }
                    }
                });
                futureList.add(future);
            }
            for (Future<T> future : futureList) {
                try {
                    T t = future.get();
                    if (t != null) {
                        retList.add(t);
                    }
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return retList;
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
