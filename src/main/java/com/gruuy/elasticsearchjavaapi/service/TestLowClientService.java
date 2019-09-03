package com.gruuy.elasticsearchjavaapi.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 15:22 2019/9/3
 */
public interface TestLowClientService {
    HttpEntity search();

    JSONObject insert();

    JSONObject update(String id);

    JSONObject delete(String id);
}
