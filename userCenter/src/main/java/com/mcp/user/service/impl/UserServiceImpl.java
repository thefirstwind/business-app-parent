package com.mcp.user.service.impl;

import com.mcp.user.entity.User;
import com.mcp.user.mapper.UserMapper;
import com.mcp.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户服务实现类
 */
@Component
@DubboService(version = "1.0.0", group = "mcp")
@Slf4j
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User getUserById(Long id) {
        log.info("根据ID查询用户：{}", id);
        return userMapper.getUserById(id);
    }
    
    @Override
    public User getUserByUsername(String username) {
        log.info("根据用户名查询用户：{}", username);
        return userMapper.getUserByUsername(username);
    }
    
    @Override
    public Long createUser(User user) {
        log.info("创建用户：{}", user.getUsername());
        userMapper.insertUser(user);
        return user.getId();
    }
    
    @Override
    public boolean updateUser(User user) {
        log.info("更新用户：{}", user.getId());
        return userMapper.updateUser(user) > 0;
    }
    
    @Override
    public boolean deleteUser(Long id) {
        log.info("删除用户：{}", id);
        return userMapper.deleteUser(id) > 0;
    }
} 