package com.athene.api.client.test.remote.dto;

import com.athene.api.client.annotation.ApiField;

import java.util.Date;

/**
 * Created by fe on 16/9/19.
 */
public class OrderDto {
    @ApiField(description = "优惠券名称",defaultValue = "20160606210000005")
    private String orderNum;
    @ApiField(description = "创建时间",defaultValue = "2011-01-01 10:00:00")
    private Date createTime;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
