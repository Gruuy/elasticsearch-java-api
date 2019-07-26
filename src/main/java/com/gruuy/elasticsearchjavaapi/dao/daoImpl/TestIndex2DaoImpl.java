package com.gruuy.elasticsearchjavaapi.dao.daoImpl;

import com.alibaba.fastjson.JSONObject;
import com.gruuy.elasticsearchjavaapi.dao.TestIndex2Dao;
import com.gruuy.elasticsearchjavaapi.entity.CreateIndex;
import com.gruuy.elasticsearchjavaapi.entity.Test2Index;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.profile.ProfileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 17:43 2019/7/24
 */
@Service
public class TestIndex2DaoImpl implements TestIndex2Dao {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /** 索引名 */
    private static final String index="test2_index";

    /** 类型名（7.0开始移除类型，固定了） */
    private static final String type="_doc";

    /** get连接 */
    private GetRequest getRequest=new GetRequest(index);
    /** get返回 */
    private GetResponse getResponse=null;

    @Override
    public JSONObject get1() {
        //定义类型
        getRequest.type(type);
        //ID
        getRequest.id("1");
        try {
            //拿!
            getResponse=restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace( );
        } catch (ElasticsearchException ex){
            if(ex.status()==RestStatus.BAD_REQUEST){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("error","连接错误！");
                return jsonObject;
            }
        }

        //把返回的转成json对象到上一级
        if(getResponse!=null) {
            return JSONObject.parseObject(getResponse.getSourceAsString());
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("error","the id was Null!");
        return jsonObject;
    }

    @Override
    public String insert() {
        //定义映射对象
        Test2Index test2Index=new Test2Index("科罗娜",9,"fastest!");
        //封装参数  要把对象转成json字符串
        IndexRequest indexRequest=new IndexRequest("test2_index").type("_doc").id("100").source(JSONObject.toJSONString(test2Index),XContentType.JSON);
        //超时时间
        indexRequest.timeout(TimeValue.timeValueSeconds(5));
        IndexResponse indexResponse= null;
        try {
            //干！
            indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace( );
        }catch (ElasticsearchException ex){
            //如果版本头冲突，则是出现并发问题，不能插入！
            if(ex.status( )==RestStatus.CONFLICT){
                return "出现版本冲突！请重试！";
            }
        }
        String str="";
        if(indexResponse.getResult() == DocWriteResponse.Result.CREATED){
            str="创建成功！";
        }else if(indexResponse.getResult()== DocWriteResponse.Result.UPDATED){
            str="更新成功！";
        }
        return str;
    }

    @Override
    public String closeIndex() {
        CloseIndexRequest closeIndexRequest=new CloseIndexRequest("test2_index");
        closeIndexRequest.timeout(TimeValue.timeValueSeconds(5));
        AcknowledgedResponse acknowledgedResponse=null;
        try {
            acknowledgedResponse=restHighLevelClient.indices().close(closeIndexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace( );
            return "关闭失败！";
        }
        if(acknowledgedResponse.isAcknowledged()) {
            return "关闭成功！";
        }
        return "关闭失败！";
    }

    @Override
    public String openIndex() {
        OpenIndexRequest openIndexRequest=new OpenIndexRequest("test2_index");
        openIndexRequest.timeout(TimeValue.timeValueSeconds(5));
        AcknowledgedResponse acknowledgedResponse=null;
        try {
            acknowledgedResponse=restHighLevelClient.indices().open(openIndexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace( );
        }catch (ElasticsearchException ex){
            if(ex.status()==RestStatus.NOT_FOUND){
                return "索引不存在！";
            }
        }
        if(acknowledgedResponse.isAcknowledged()){
            return "开启成功！";
        }
        return "开启失败！";
    }

    @Override
    public List<Test2Index> getIndex() {
        //新建查询连接
        SearchRequest searchRequest=new SearchRequest("test2_index");
        //查询参数
        searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery( )).timeout(TimeValue.timeValueSeconds(5)));
        SearchResponse searchResponse=null;
        try {
            //获取返回值
            searchResponse=restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace( );
        }
        //封装成java对象
        List<Test2Index> list=new ArrayList<>();
        for(SearchHit searchHit:searchResponse.getHits()){
            //alibaba牛逼！
            JSONObject jsonObject=JSONObject.parseObject(searchHit.getSourceAsString());
            Test2Index item=JSONObject.toJavaObject(jsonObject,Test2Index.class);
            list.add(item);
        }
        return list;
    }

    @Override
    public String update() {
        Test2Index test2Index=new Test2Index("fast",10,"Rock And STanSen");
        UpdateRequest updateRequest=new UpdateRequest("test2_index","_doc","101").doc(JSONObject.toJSONString(test2Index),XContentType.JSON);
        updateRequest.timeout(TimeValue.timeValueSeconds(5));
        //当冲突时，重试的最大次数
        updateRequest.retryOnConflict(3);
        //如果不存在，则表明部分文档必须用作upsert文档。
        updateRequest.docAsUpsert(true);
        UpdateResponse updateResponse=null;
        try {
            updateResponse=restHighLevelClient.update(updateRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace( );
        }catch (ElasticsearchException ex){
            if(ex.status()==RestStatus.CONFLICT){
                return "版本冲突！";
            }
        }
        if(updateResponse.getResult()== DocWriteResponse.Result.CREATED){
            return "插入成功！";
        }else if(updateResponse.getResult()== DocWriteResponse.Result.UPDATED){
            return "更新成功！";
        }else if(updateResponse.getResult()== DocWriteResponse.Result.NOOP){
            return "无需更新！";
        }
        return "失败！";
    }
}
