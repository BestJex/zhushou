package com.yuntao.zhushou.model.query;

import com.fasterxml.jackson.databind.deser.Deserializers;

/**
 * Created by shan on 2016/3/27.
 */
public class AppQuery  extends BaseQuery{

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}