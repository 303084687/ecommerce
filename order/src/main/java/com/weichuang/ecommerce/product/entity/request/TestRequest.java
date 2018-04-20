package com.weichuang.ecommerce.product.entity.request;

import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "testRequest")
public class TestRequest {
    @QueryParam("id")
    private int id;

    @QueryParam("name")
    private String name;// 商品名称

    @QueryParam("code")
    private String code;// 商品编码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
