package com.gruuy.elasticsearchjavaapi.dao;

import com.alibaba.fastjson.JSONObject;
import com.gruuy.elasticsearchjavaapi.entity.Test2Index;

import java.util.List;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 17:43 2019/7/24
 */
public interface TestIndex2Dao {
    JSONObject get1();

    String insert();

    String closeIndex();

    String openIndex();

    List<Test2Index> getIndex();

    String update();
}
