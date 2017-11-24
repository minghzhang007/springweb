package com.lewis.master.common.hystrix.test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HelloWorldCommand extends HystrixCommand<String> {
    private String name;

    protected HelloWorldCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(name)).andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationThreadTimeoutInMilliseconds(500)));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        Thread.sleep(1000);
        return "Hello " + name + ", thread:" + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "别看了，执行失败了！这是替代品！";
    }

    public static void main(String[] args) {
        HelloWorldCommand command = new HelloWorldCommand("sync-hystrix");
        String result = command.execute();
        System.out.println(result);

        HelloWorldCommand asynCommand = new HelloWorldCommand("sync-hystrix");
        Future<String> future = asynCommand.queue();
        try {
            result = future.get(100, TimeUnit.MILLISECONDS);
            System.out.println(result);
            System.out.println(Thread.currentThread().getName());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        Observable<String> obserable = new HelloWorldCommand("world").observe();
        obserable.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
        obserable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("执行完成");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("抛出异常：" + e);
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext:" + s);
            }
        });

    }
}
