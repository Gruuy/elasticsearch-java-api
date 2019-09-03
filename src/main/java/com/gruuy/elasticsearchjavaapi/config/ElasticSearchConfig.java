package com.gruuy.elasticsearchjavaapi.config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Gruuy
 * @remark: ElasticSearch配置
 * @date: Create in 17:14 2019/7/24
 */
@Configuration
public class ElasticSearchConfig {
    Logger logger= LoggerFactory.getLogger(ElasticSearchConfig.class);
    /** 端口号 */
    private final Integer port=9200;
    /** 地址 */
    private final String hostName="localhost";
    /** 协议名称 */
    private final String scheme="http";
    /** 默认Token */
    private final String token="token";
    /** 默认token的值 */
    private final String value="123456";
    /** 连接超时时间 连接超时（默认为1秒）*/
    private final int connectOutTime=5000;
    /** 套接字超时时间 套接字超时（默认为30秒） */
    private final int socketTimeout=10000;

    @Bean
    public RestHighLevelClient client(RestClientBuilder restClientBuilder){
        return new RestHighLevelClient(restClientBuilder);
    }
    @Bean
    @SuppressWarnings("all")
    public RestClientBuilder restClientBuilder(){
        RestClientBuilder builder= RestClient.builder(new HttpHost(hostName,port,scheme));
        //定义默认Headers，不用每个连接都带Headers
        Header[] defaultHeaders=new Header[]{new BasicHeader(token,value)};
        builder.setDefaultHeaders(defaultHeaders);
        //定义监听器，节点出现故障会收到通知。
        builder.setFailureListener(new RestClient.FailureListener(){
            @Override
            public void onFailure(Node node) {
                logger.info("Fail!"+node.getName()+","+node.getHost()+"is Error!");
                super.onFailure(node);
            }
        });
        //定义节点选择器   这个是跳过data=false，ingest为false的节点
        builder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
        //定义默认请求配置回调
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback( ) {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                return requestConfigBuilder.setConnectTimeout(connectOutTime)
                        .setSocketTimeout(socketTimeout);
            }
        });
//        定义HTTP客户端请求配置回调
//        restClient.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback( ) {
//            @Override
//            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
//                return httpClientBuilder.setProxy(new HttpHost("localhost",8080,"http"));
//            }
//        });

        return builder;
    }
}
