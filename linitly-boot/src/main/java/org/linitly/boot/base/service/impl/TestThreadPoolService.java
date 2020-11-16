package org.linitly.boot.base.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: linxiunan
 * @date: 2020/6/29 15:57
 * @descrption:
 */
@Slf4j
@Service
public class TestThreadPoolService {

    @Lazy
    @Autowired
    private TestThreadPoolService testThreadPoolService;

    @Async(GlobalConstant.THREAD_POOL_NAME)
    public void test1() {
        log.info("test1");
        testThreadPoolService.test2();
        testThreadPoolService.test3();
    }

    @Async(GlobalConstant.THREAD_POOL_NAME)
    public void test2() {
        log.info("test2");
    }

    @Async(GlobalConstant.THREAD_POOL_NAME)
    public void test3() {
        log.info("test3");
    }
}
