package com.gruuy.elasticsearchjavaapi.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.gruuy.elasticsearchjavaapi.dao.TestLowClientIndex2Dao;
import com.gruuy.elasticsearchjavaapi.service.TestLowClientService;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 15:22 2019/9/3
 */
@Service
public class TestLowClientServiceImpl implements TestLowClientService {
    @Autowired
    private TestLowClientIndex2Dao testLowClientIndex2Dao;
    @Override
    public HttpEntity search() {
        return testLowClientIndex2Dao.search();
    }

    @Override
    public JSONObject insert() {
        return testLowClientIndex2Dao.insert();
    }

    @Override
    public JSONObject update(String id) {
        return testLowClientIndex2Dao.update(id);
    }

    @Override
    public JSONObject delete(String id) {
        return testLowClientIndex2Dao.delete(id);
    }
}
