package com.athene.api.client.test.remote.user.impl;

import com.athene.api.client.annotation.ApiParam;
import com.athene.api.client.test.remote.Result;
import com.athene.api.client.test.remote.dto.UserDto;
import com.athene.api.client.test.remote.query.UserQuery;
import com.athene.api.client.test.remote.user.UserService;

import java.util.List;

/**
 * Created by fe on 16/9/20.
 */
public class UserServiceImpl implements UserService {
    @Override
    public Result<UserDto> queryById(@ApiParam(description = "用户id", defaultValue = "1", required = true) Long userId) {
        return null;
    }

    @Override
    public Result<UserDto> queryByParam(@ApiParam(description = "用户id", defaultValue = "1", required = true) Long userId, @ApiParam(description = "用户名称", defaultValue = "1", required = true) String userName) {
        return null;
    }

    @Override
    public Result<List<UserDto>> queryByIds(@ApiParam(description = "用户id列表", defaultValue = "1|2|3|4|5", required = true) List<Long> userId) {
        return null;
    }

    @Override
    public Result<UserDto> queryAllUser() {
        return null;
    }

    @Override
    public Result<List<UserDto>> queryAllByParam(@ApiParam(description = "用户id列表", required = true) UserQuery userQuery) {
        return null;
    }
}
