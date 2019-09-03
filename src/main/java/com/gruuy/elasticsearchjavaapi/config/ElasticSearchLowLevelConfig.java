package com.gruuy.elasticsearchjavaapi.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.NodeSelector;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Gruuy
 * @remark: 低级客户端配置
 * @date: Create in 14:51 2019/9/3
 */
@Configuration
public class ElasticSearchLowLevelConfig {
    Logger logger= LoggerFactory.getLogger(ElasticSearchLowLevelConfig.class);

    @Bean
    public RestClient restClient(RestClientBuilder restClientBuilder){
        return restClientBuilder.build();
    }

    @Bean
    @SuppressWarnings("all")
    public RestClientBuilder getBuilder(){
        RestClientBuilder builder=RestClient.builder(new HttpHost("localhost",9200,"http"));
        //设置请求超时10s
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback( ) {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
                return builder.setSocketTimeout(10000);
            }
        });
        //定义节点选择器   这个是跳过data=false，ingest为false的节点
        builder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);
        //定义监听器，节点出现故障会收到通知。
        builder.setFailureListener(new RestClient.FailureListener(){
            @Override
            public void onFailure(Node node) {
                logger.info("Fail!"+node.getName()+","+node.getHost()+"is Error!");
                super.onFailure(node);
            }
        });
        return builder;
    }
}
