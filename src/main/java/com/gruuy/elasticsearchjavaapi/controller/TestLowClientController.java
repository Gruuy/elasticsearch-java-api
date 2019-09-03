package com.gruuy.elasticsearchjavaapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.gruuy.elasticsearchjavaapi.resp.Msg;
import com.gruuy.elasticsearchjavaapi.service.TestLowClientService;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 15:20 2019/9/3
 */
@RestController
@RequestMapping("/LowClient/test2Index")
public class TestLowClientController {
    @Autowired
    private TestLowClientService testLowClientService;
    
    /** 搜索 */
    @PostMapping("/search")
    public Msg search(){
        try {
            return new Msg().success().message(JSONObject.parseObject(EntityUtils.toString(testLowClientService.search())));
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return new Msg().fail();
    }

    /** 插入 */
    @PostMapping("/insert")
    public Msg insert(){
        return new Msg().success().message(testLowClientService.insert());
    }

    /** 更新 */
    @PostMapping("/update")
    public Msg update(@RequestBody Map<String,Object> map){
        return new Msg().success().message(testLowClientService.update(map.get("id").toString()));
    }

    /** 删除 */
    @PostMapping("/delete")
    public Msg delete(@RequestBody Map<String,Object> map){
        return new Msg().success().message(testLowClientService.delete(map.get("id").toString()));
    }

}
