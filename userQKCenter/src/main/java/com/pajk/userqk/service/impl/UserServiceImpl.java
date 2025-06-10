package com.pajk.userqk.service.impl;

import com.mycompany.aigw.sdk.tool.annotation.Tool;
import com.mycompany.aigw.sdk.tool.annotation.ToolParam;
import com.pajk.userqk.entity.User;
import com.pajk.userqk.mapper.UserMapper;
import com.pajk.userqk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户服务实现类
 */
@Component
@DubboService(version = "1.0.0")
@Slf4j
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    @Tool(description = "根据ID查询用户")
    public User getUserById(@ToolParam(description = "用户ID")  Long id) {
        log.info("根据ID查询用户：{}", id);
        return userMapper.getUserById(id);
    }
    
    @Override
    @Tool(description = "根据名查询用户")
    public User getUserByUsername(@ToolParam(description = "用户名")  String username) {
        log.info("根据用户名查询用户：{}", username);
        return userMapper.getUserByUsername(username);
    }
    
    @Override
    @Tool(description = "创建新用户")
    public Long createUser(@ToolParam(description = "用户对象") User user) {
        log.info("创建用户：{}", user.getUsername());
        userMapper.insertUser(user);
        return user.getId();
    }
    
    @Override
    @Tool(description = "更新用户信息")
    public boolean updateUser(@ToolParam(description = "用户对象") User user) {
        log.info("更新用户：{}", user.getId());
        return userMapper.updateUser(user) > 0;
    }
    
    @Override
    @Tool(description = "删除用户信息")
    public boolean deleteUser(Long id) {
        log.info("删除用户：{}", id);
        return userMapper.deleteUser(id) > 0;
    }
} 