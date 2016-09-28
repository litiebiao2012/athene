package com.athene.api.client.test.remote.order;

import com.athene.api.client.test.remote.Result;

/**
 * Created by fe on 16/9/20.
 */
public interface OrderService {

    public Result<String> queryOrderNumByOrderId(String orderId);
}
