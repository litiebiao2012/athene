package com.athene.api.client.test.remote.query;

import com.athene.api.client.annotation.ApiField;

import java.io.Serializable;

/**
 * Created by fe on 16/9/9.
 */
public class UserQuery extends BaseQuery implements Serializable {

    @ApiField(description = "用户id",defaultValue = "12345")
    private Long userId;
    @ApiField(description = "用户年龄",defaultValue = "20")
    private int age;
    @ApiField(description = "姓名",defaultValue = "啊啊啊")
    private String name;
    @ApiField(description = "价格",defaultValue = "12.22")
    private double price;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
