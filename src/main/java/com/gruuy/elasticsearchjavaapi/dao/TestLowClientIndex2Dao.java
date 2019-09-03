package com.gruuy.elasticsearchjavaapi.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 15:23 2019/9/3
 */
public interface TestLowClientIndex2Dao {
    HttpEntity search();

    JSONObject insert();

    JSONObject update(String id);

    JSONObject delete(String id);
}
