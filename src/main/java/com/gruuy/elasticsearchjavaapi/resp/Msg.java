package com.gruuy.elasticsearchjavaapi.resp;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 17:36 2019/7/24
 */
@Component
public class Msg extends HashMap {

    public Msg fail(){
        this.put("result","fail");
        return this;
    }
    public Msg success(){
        this.put("result","success");
        return this;
    }
    public Msg code(Integer code){
        this.put("code",code);
        return this;
    }
    public Msg message(Object message){
        this.put("resp",message);
        return this;
    }
}
