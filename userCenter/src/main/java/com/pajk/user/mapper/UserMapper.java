package com.pajk.user.mapper;

import com.pajk.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据访问接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 根据ID查询用户
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    User getUserById(@Param("id") Long id);
    
    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByUsername(@Param("username") String username);
    
    /**
     * 插入用户
     * 
     * @param user 用户信息
     * @return 影响行数
     */
    int insertUser(User user);
    
    /**
     * 更新用户
     * 
     * @param user 用户信息
     * @return 影响行数
     */
    int updateUser(User user);
    
    /**
     * 删除用户
     * 
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteUser(@Param("id") Long id);
    
    /**
     * 查询所有用户
     * 
     * @return 用户列表
     */
    List<User> getAllUsers();
} 