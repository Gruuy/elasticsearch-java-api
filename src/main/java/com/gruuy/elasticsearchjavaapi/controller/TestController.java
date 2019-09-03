package com.gruuy.elasticsearchjavaapi.controller;

import com.gruuy.elasticsearchjavaapi.resp.Msg;
import com.gruuy.elasticsearchjavaapi.service.TestService;
import com.gruuy.elasticsearchjavaapi.util.ConstUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Gruuy
 * @remark: test
 * @date: Create in 17:35 2019/7/24
 */
@RestController
@RequestMapping("/test2Index")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private Msg msg;

    /** 测试get */
    @RequestMapping("/test")
    public Msg test(){
        return msg.success().code(ConstUtil.SUCCESS_CODE).message(testService.get1());
    }
    /** 测试insert */
    @PostMapping("/insert")
    public Msg insert(){
        return msg.success().code(ConstUtil.SUCCESS_CODE).message(testService.insert());
    }
    /** 测试创建索引 */
    @PostMapping("/createIndex")
    public Msg createIndex(){
        return msg.success().code(ConstUtil.SUCCESS_CODE).message(testService.createIndex());
    }
    /** 测试删除索引 */
    @PostMapping("/deleteIndex")
    public Msg deleteIndex(){
        return msg.success().code(ConstUtil.SUCCESS_CODE).message(testService.deleteIndex());
    }
    /** 测试关闭索引 */
    @PostMapping("/closeIndex")
    public Msg closeIndex(){
        return msg.success().code(ConstUtil.SUCCESS_CODE).message(testService.closeIndex());
    }
    @PostMapping("/openIndex")
    public Msg openIndex(){
        return msg.success().code(ConstUtil.SUCCESS_CODE).message(testService.openIndex());
    }

    @GetMapping("/getIndex")
    public Msg getIndex(){
        return msg.success().code(ConstUtil.SUCCESS_CODE).message(testService.getIndex());
    }
    @PostMapping("/update")
    public Msg update(){
        return msg.success().code(ConstUtil.SUCCESS_CODE).message(testService.update());
    }
    @PostMapping("/delete")
    public Msg delete() {
        return msg.success( ).code(ConstUtil.SUCCESS_CODE).message(testService.delete( ));
    }
}
