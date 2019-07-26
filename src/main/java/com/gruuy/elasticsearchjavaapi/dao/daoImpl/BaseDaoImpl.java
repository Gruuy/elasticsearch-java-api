package com.gruuy.elasticsearchjavaapi.dao.daoImpl;

import com.alibaba.fastjson.JSONObject;
import com.gruuy.elasticsearchjavaapi.dao.BaseDao;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexAction;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 11:58 2019/7/25
 */
@Service
public class BaseDaoImpl implements BaseDao {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public String createIndex() {
        //创建索引的连接
        CreateIndexRequest createIndexRequest=new CreateIndexRequest("create_index");
        //指定settings  就是在kibana上面写的那些语句  转成代码而已
        createIndexRequest.settings(Settings.builder( ).put("number_of_shards",2).put("number_of_replicas",1));
        //mapping的内容（字段定义）
        createIndexRequest.mapping("{"+"\"properties\": {\n" +
                "      \"title\":{\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"ik_smart\"\n" +
                "      },\n" +
                "      \"message\":{\n" +
                "        \"type\": \"text\"\n" +
                "      },\n" +
                "      \"date\":{\n" +
                "        \"type\": \"date\"\n" +
                "      }\n" +
                "    }}", XContentType.JSON);
        CreateIndexResponse createIndexResponse;
        try {
            //干！
            createIndexResponse=restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace( );
            return "插入失败！";
        }
        //返回创建成功的索引名
        return createIndexResponse.index();
    }

    @Override
    public String deleteIndex() {
        //删除索引的连接
        DeleteIndexRequest deleteIndexRequest=new DeleteIndexRequest("create_index");
        //删除的返回值。。我也不知道是咩
        AcknowledgedResponse acknowledgedResponse = null;
        try {
            acknowledgedResponse=restHighLevelClient.indices().delete(deleteIndexRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace( );
            return "删除失败！";
        }catch (ElasticsearchException ex){
            //没有找到对应索引的时候会报错！
            if(ex.status()== RestStatus.NOT_FOUND){
                return "索引不存在！";
            }
        }
        if(acknowledgedResponse.isAcknowledged()) {
            return "删除成功！";
        }
        return "删除失败！";
    }


}
