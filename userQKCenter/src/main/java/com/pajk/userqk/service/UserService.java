package com.pajk.userqk.service;

import com.pajk.userqk.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {

    String sayHello();
    /**
     * 根据ID查询用户
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    User getUserById(Long id);
    
    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByUsername(String username);
    
    /**
     * 创建用户
     * 
     * @param user 用户信息
     * @return 用户ID
     */
    Long createUser(User user);
    
    /**
     * 更新用户
     * 
     * @param user 用户信息
     * @return 是否成功
     */
    boolean updateUser(User user);
    
    /**
     * 删除用户
     * 
     * @param id 用户ID
     * @return 是否成功
     */
    boolean deleteUser(Long id);
} 