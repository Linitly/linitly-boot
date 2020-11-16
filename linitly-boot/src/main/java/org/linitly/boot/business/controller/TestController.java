package org.linitly.boot.business.controller;

import org.linitly.boot.base.helper.entity.ResponseResult;
import org.linitly.boot.business.dao.TestMybatisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: linxiunan
 * @date: 2020/9/28 10:06
 * @descrption:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestMybatisMapper testMybatisMapper;

    @GetMapping("/test-mybatis")
    public ResponseResult test() {
        return new ResponseResult(testMybatisMapper.findAll());
    }

    @GetMapping("/test-count")
    public ResponseResult count() {
        return new ResponseResult(testMybatisMapper.findCount());
    }
}
