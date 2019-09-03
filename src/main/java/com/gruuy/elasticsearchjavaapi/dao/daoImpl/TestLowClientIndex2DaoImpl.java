package com.gruuy.elasticsearchjavaapi.dao.daoImpl;

import com.alibaba.fastjson.JSONObject;
import com.gruuy.elasticsearchjavaapi.dao.TestLowClientIndex2Dao;
import com.gruuy.elasticsearchjavaapi.util.ResponseUtil;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 15:24 2019/9/3
 */
@Service
public class TestLowClientIndex2DaoImpl implements TestLowClientIndex2Dao {
    @Autowired
    private RestClient restClient;

    @Override
    public HttpEntity search() {
        //第一个是方法  GET POST PUT DELETE 任君选择
        //第二个是端点  就是去除端口跟地址之外的全部玩意
        Request request=new Request("GET","/test2_index/_doc/_search");
        //美化  别开！  会加\n   恨死你的前端
//        request.addParameter("pretty","true");
        //查询主体
        request.setJsonEntity("{\n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  }\n" +
                "}");
        Response response=null;
        HttpEntity responseBody=null;
        try {
            response=restClient.performRequest(request);
            responseBody= response.getEntity();
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return responseBody;
    }

    @Override
    public JSONObject insert() {
        Request request=new Request("POST","/test2_index/_doc");
        request.setJsonEntity("{\n" +
                "  \"name\":\"test\",\n" +
                "  \"age\":21,\n" +
                "  \"remark\":\"测他妈的\"\n" +
                "}");
        Response response=null;
        try {
            response=restClient.performRequest(request);

        } catch (IOException e) {
            e.printStackTrace( );
        }
        try {
            return JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return null;
    }

    @Override
    public JSONObject update(String id) {
        Request request=new Request("PUT","/test2_index/_doc/"+id);
        request.setJsonEntity("{\n" +
                "  \"name\":\"test\",\n" +
                "  \"age\":21,\n" +
                "  \"remark\":\"测他妈的  妈个鸡  测！\"\n" +
                "}");
        JSONObject result= ResponseUtil.getJSONObject(restClient,request);
        return result;
    }

    @Override
    public JSONObject delete(String id) {
        Request request=new Request("DELETE","/test2_index/_doc/"+id);
        JSONObject result= ResponseUtil.getJSONObject(restClient,request);
        return result;
    }
}
