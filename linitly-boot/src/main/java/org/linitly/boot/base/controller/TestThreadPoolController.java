package org.linitly.boot.base.controller;

import org.linitly.boot.base.service.TestThreadPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: linxiunan
 * @date: 2020/6/29 15:56
 * @descrption:
 */
@RestController
@RequestMapping("/pool")
public class TestThreadPoolController {

    @Autowired
    private TestThreadPoolService testThreadPoolService;

    @GetMapping("/test")
    public void test() {
        testThreadPoolService.test1();
    }
}
