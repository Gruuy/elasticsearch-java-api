package com.gruuy.elasticsearchjavaapi;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ElasticsearchJavaApiApplicationTests {

    @Test
    public void contextLoads() {
        //定义连接端口
        RestClientBuilder restClient=RestClient.builder(new HttpHost("localhost",9200,"http"));
        //定义默认Headers，不用每个连接都带Headers
        Header[] defaultHeaders=new Header[]{new BasicHeader("token","123456")};
        restClient.setDefaultHeaders(defaultHeaders);
        //定义监听器，节点出现故障会收到通知。
        restClient.setFailureListener(new RestClient.FailureListener(){
            @Override
            public void onFailure(Node node) {
                super.onFailure(node);
            }
        });
        //定义节点选择器   这个是跳过data=false，ingest为false的节点
        restClient.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
        //定义默认请求配置回调
        restClient.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback( ) {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                return requestConfigBuilder.setConnectTimeout(5000)         //连接超时（默认为1秒）
                        .setSocketTimeout(10000);                           //套接字超时（默认为30秒）
            }
        });
        //定义HTTP客户端请求配置回调
//        restClient.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback( ) {
//            @Override
//            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                return httpClientBuilder.setProxy(new HttpHost("localhost",8080,"http"));
//            }
//        });
        RestHighLevelClient client=new RestHighLevelClient(restClient);

        GetRequest getRequest=new GetRequest("test2_index","_doc","1");
        GetResponse getResponse=null;
        try {
            getResponse=client.get(getRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace( );
        }
        System.out.println(getResponse.getSourceAsString());
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace( );
        }
    }

}
