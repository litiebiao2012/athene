package com.athene.api.gateway.test.query;

import com.athene.api.client.annotation.ApiField;

/**
 * Created by fe on 16/9/9.
 */
public class BaseQuery {
    @ApiField(description = "页数",defaultValue = "1")
    private int page;
    @ApiField(description = "每页大小",defaultValue = "20")
    private int pageSize;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
