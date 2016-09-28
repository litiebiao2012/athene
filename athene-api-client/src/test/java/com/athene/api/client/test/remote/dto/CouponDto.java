package com.athene.api.client.test.remote.dto;

import com.athene.api.client.annotation.ApiField;

import java.math.BigDecimal;

/**
 * Created by fe on 16/9/19.
 */
public class CouponDto {
    @ApiField(description = "优惠券名称",defaultValue = "情人节限定优惠券")
    private String couponName;
    @ApiField(description = "优惠券价格",defaultValue = "12.22")
    private BigDecimal couponPrice;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }
}
