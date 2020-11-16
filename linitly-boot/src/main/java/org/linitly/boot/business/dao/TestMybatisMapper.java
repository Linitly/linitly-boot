package org.linitly.boot.business.dao;

import org.linitly.boot.business.entity.TestMybatis;

import java.util.List;

public interface TestMybatisMapper {

    List<TestMybatis> findAll();

    Integer findCount();
}
