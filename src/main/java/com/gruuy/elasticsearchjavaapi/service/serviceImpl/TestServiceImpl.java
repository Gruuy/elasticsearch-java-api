package com.gruuy.elasticsearchjavaapi.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.gruuy.elasticsearchjavaapi.dao.BaseDao;
import com.gruuy.elasticsearchjavaapi.dao.TestIndex2Dao;
import com.gruuy.elasticsearchjavaapi.entity.Test2Index;
import com.gruuy.elasticsearchjavaapi.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 17:40 2019/7/24
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestIndex2Dao testIndex2Dao;
    @Autowired
    private BaseDao baseDao;

    @Override
    public JSONObject get1() {
        return testIndex2Dao.get1();
    }

    @Override
    public String insert() {
        return testIndex2Dao.insert();
    }

    @Override
    public String createIndex() {
        return baseDao.createIndex();
    }

    @Override
    public String deleteIndex() {
        return baseDao.deleteIndex();
    }

    @Override
    public String closeIndex() {
        return testIndex2Dao.closeIndex();
    }

    @Override
    public String openIndex() {
        return testIndex2Dao.openIndex();
    }

    @Override
    public List<Test2Index> getIndex() {
        return testIndex2Dao.getIndex();
    }

    @Override
    public String update() {
        return testIndex2Dao.update();
    }

    @Override
    public String delete() {
        return testIndex2Dao.delete();
    }
}
