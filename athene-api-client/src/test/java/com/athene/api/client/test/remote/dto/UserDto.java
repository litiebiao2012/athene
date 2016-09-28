package com.athene.api.client.test.remote.dto;

import com.athene.api.client.annotation.ApiField;

import java.util.Date;
import java.util.List;

/**
 * Created by fe on 16/9/28.
 */
public class UserDto {
    @ApiField(description = "用户id",defaultValue = "123456")
    private Long userId;
    @ApiField(description = "用户名称",defaultValue = "张三")
    private String name;
    @ApiField(description = "用户年龄",defaultValue = "12")
    private int age;
    @ApiField(description = "创建时间",defaultValue = "2012-12-12 10:00:00")
    private Date createTime;
    @ApiField(description = "性别",defaultValue = "m")
    private char sex;
    @ApiField(description = "是否vip",defaultValue = "true")
    private boolean isVip;
    @ApiField(description = "权限",defaultValue = "1")
    private byte security;
    @ApiField(description = "积分",defaultValue = "12")
    private double score;
    @ApiField(description = "价格",defaultValue = "123")
    private float price;
    @ApiField(description = "是充满",defaultValue = "333")
    private short isFull;
    @ApiField(description = "优惠券列表",defaultValue = "")
    private List<CouponDto> couponList;
    @ApiField(description = "订单",defaultValue = "")
    private OrderDto order;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public byte getSecurity() {
        return security;
    }

    public void setSecurity(byte security) {
        this.security = security;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public short getIsFull() {
        return isFull;
    }

    public void setIsFull(short isFull) {
        this.isFull = isFull;
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    public List<CouponDto> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponDto> couponList) {
        this.couponList = couponList;
    }
}
