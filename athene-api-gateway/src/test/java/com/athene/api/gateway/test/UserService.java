package com.athene.api.gateway.test;

import com.athene.api.client.annotation.ApiGroup;
import com.athene.api.client.annotation.ApiMethod;
import com.athene.api.client.annotation.ApiParam;
import com.athene.api.gateway.test.dto.UserDto;
import com.athene.api.gateway.test.query.UserQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fe on 16/9/9.
 */

@Resource
@ApiGroup(
        description = "用户服务",
        owner = "fe",
        needAuth = false,
        version = "1.0.0",
        group = "test"
)
public interface UserService {

    @ApiMethod(description = "查询用户根据id")
    public Result<UserDto> queryById(@ApiParam(description = "用户id",defaultValue = "1",required = true) Long userId);

    @ApiMethod(description = "查询用户根据请求参数")
    public Result<UserDto> queryByParam(@ApiParam(description = "用户id",defaultValue = "1",required = true) Long userId,
                                        @ApiParam(description = "用户名称",defaultValue = "1",required = true) String userName);
    @ApiMethod(description = "根据用户id批量查询")
    public Result<List<UserDto>> queryByIds(@ApiParam(description = "用户id列表",defaultValue = "1|2|3|4|5",required = true) List<Long> userId);


    @ApiMethod(description = "查询所有用户")
    public Result<UserDto> queryAllUser();


//    @ApiMethod(description = "查询所有用户根据id")
//    public Result<List<UserDto>> queryAllUserById(Long userId);

//    public Result<UserDto> queryByParam(UserQuery userQuery);
//
     @ApiMethod(description = "根据query查询用户信息")
     public Result<List<UserDto>> queryAllByParam(@ApiParam(description = "用户id列表",required = true) UserQuery userQuery);
//
//    public Result<String> getUserName();
//
//    public Result<Integer> getAge();
//
//    public void saveByParam(UserDto userDto);
//
//    public void save(Map<String,Object> paramMap);
//
//    public void batchSave(List<UserDto> userDtoList);

}
