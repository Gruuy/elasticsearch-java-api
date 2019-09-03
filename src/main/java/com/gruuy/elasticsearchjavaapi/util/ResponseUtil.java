package com.gruuy.elasticsearchjavaapi.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * @author: Gruuy
 * @remark: 获取es结果
 * @date: Create in 16:46 2019/9/3
 */
public class ResponseUtil {
    public static JSONObject getJSONObject(RestClient restClient, Request request){
        Response response=null;
        JSONObject jsonObject=null;
        try {
            response=restClient.performRequest(request);
            jsonObject=JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace( );
            return null;
        }
        return jsonObject;
    }
}
