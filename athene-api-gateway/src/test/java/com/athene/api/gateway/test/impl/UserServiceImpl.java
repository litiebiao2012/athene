package com.athene.api.gateway.test.impl;

import com.athene.api.client.annotation.ApiParam;
import com.athene.api.gateway.test.Result;
import com.athene.api.gateway.test.UserService;
import com.athene.api.gateway.test.dto.UserDto;
import com.athene.api.gateway.test.query.UserQuery;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by fe on 16/9/9.
 */
public class UserServiceImpl implements UserService {
    public Result<UserDto> queryById(Long userId) {
        System.out.println("userId : " + userId);
        Result<UserDto> result = new Result<UserDto>();
        result.setCode("200");
        result.setSuccess(true);
        UserDto userDto = new UserDto();
        userDto.setAge(1);
        userDto.setName("李铁彪");
        result.setData(userDto);
        return result;
    }

    public Result<UserDto> queryByParam(@ApiParam(description = "用户id", defaultValue = "1", required = true) Long userId, @ApiParam(description = "用户名称", defaultValue = "1", required = true) String userName) {
        return new Result<UserDto>();
    }

    public Result<List<UserDto>> queryByIds(@ApiParam(description = "用户id列表", defaultValue = "1,2,3,4,5", required = true) List<Long> userId) {
        return new Result<List<UserDto>>();
    }

    public Result<UserDto> queryAllUser() {
        return new Result<UserDto>();
    }

    public Result<List<UserDto>> queryAllUserById(Long userId) {
        return null;
    }

    public Result<UserDto> queryByParam(UserQuery userQuery) {
        return null;
    }

    public Result<String> getUserName() {
        return null;
    }

    public Result<Integer> getAge() {
        return null;
    }

    public void saveByParam(UserDto userDto) {

    }

    public void save(Map<String, Object> paramMap) {

    }

    public void batchSave(List<UserDto> userDtoList) {

    }

    public Result<List<UserDto>> queryAllByParam(UserQuery userQuery) {
        Result<List<UserDto>> result = new Result<List<UserDto>>();
        result.setCode("200");
        result.setSuccess(true);

        List<UserDto> userDtoList = new LinkedList<UserDto>();
        UserDto userDto = new UserDto();
        userDto.setAge(1);
        userDto.setName("李铁彪");
        userDtoList.add(userDto);
        result.setData(userDtoList);
        return result;
    }
}
