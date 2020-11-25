package org.linitly.boot.base.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: linxiunan
 * @date: 2020/6/5 9:41
 * @descrption:
 */
@Slf4j
@EnableAsync
@Configuration
public class LinitlyAsyncConfigurer implements AsyncConfigurer {

    private static final int CORE_POOL_SIZE = 5;

    private static final int MAX_POOL_SIZE = 10;

    private static final int QUEUE_CAPACITY = 20;

    @Override
    public Executor getAsyncExecutor() {
        //根据ThreadPoolTaskExecutor 创建建线程池
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        //为线程设置初始的线程数量 5条线程
        executor.setCorePoolSize(CORE_POOL_SIZE);
        //为线程设置最大的线程数量 10条线程
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        //为任务队列设置最大任务数量
        executor.setQueueCapacity(QUEUE_CAPACITY);
        //设置 超出初始化线程的 存在时间为60秒
        //也就是 如果现有线程数超过5 则会对超出的空闲线程 设置摧毁时间 也就是60秒
        executor.setKeepAliveSeconds(60);
        //设置 线程前缀
        executor.setThreadNamePrefix("linitly-async-");
        //线程池的饱和策略 我这里设置的是 CallerRunsPolicy 也就是由用调用者所在的线程来执行任务 共有四种
        //AbortPolicy：直接抛出异常，这是默认策略；
        //CallerRunsPolicy：用调用者所在的线程来执行任务；
        //DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
        //DiscardPolicy：直接丢弃任务；
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //设置在关闭线程池时是否等待任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //设置等待终止的秒数
        executor.setAwaitTerminationSeconds(60);
        //返回设置完成的线程池
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (Throwable ex, Method method, Object... params) -> {
            log.error(MessageFormat.format("Async execution error occur: at [{0}] class [{1}] method with [{3}] parameter", method.getClass().getName(), method.getName(), Arrays.toString(params)));
            log.error(MessageFormat.format("error message: {0}", ex.getMessage()));
        };
    }
}
