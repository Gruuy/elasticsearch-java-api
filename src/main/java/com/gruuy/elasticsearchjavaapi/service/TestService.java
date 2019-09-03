package com.gruuy.elasticsearchjavaapi.service;

import com.alibaba.fastjson.JSONObject;
import com.gruuy.elasticsearchjavaapi.entity.Test2Index;

import java.util.List;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 17:40 2019/7/24
 */
public interface TestService {
    JSONObject get1();

    String insert();

    String createIndex();

    String deleteIndex();

    String closeIndex();

    String openIndex();

    List<Test2Index> getIndex();

    String update();

    String delete();
}
