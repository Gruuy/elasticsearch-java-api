package com.gruuy.elasticsearchjavaapi.entity;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: Gruuy
 * @remark:
 * @date: Create in 15:19 2019/7/25
 */
public class Test2Index {
    private String name;
    private Integer age;
    private String remark;

    /**
     * @param name
     * @param age
     * @param remark
     */
    public Test2Index(String name, Integer age, String remark) {
        this.name = name;
        this.age = age;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
