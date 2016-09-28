package com.athene.api.client.test.remote.user;

import com.athene.api.client.test.remote.Result;

/**
 * Created by fe on 16/9/20.
 */
public interface UserRoleService {

    public Result<Long> queryUserRoleByRoleId(Long roleId);
}
