package com.example.nanchen.realmdemo;

import java.sql.SQLException;
import java.util.List;

/**
 * 操作数据库的接口Dao
 *
 * @author  nanchen
 * @date   2016-08-08  17:23:18
 *
 */
public interface UserDao {

    /**
     * 插入一个用户
     * @param user    需要插入的用户对象
     * @throws SQLException
     */
    void insert(User user) throws SQLException;

    /**
     * 获得所有的用户列表
     * @return 用户列表
     * @throws SQLException
     */
    List<User> getAllUser() throws SQLException;

    /**
     * 更新一个用户
     * @param user 需要更新的用户类
     * @return      更新后的对象
     * @throws SQLException
     */
    User updateUser(User user) throws SQLException;

    /**
     * 根据姓名修改新姓名
     * @param name1 老名字
     * @param name2 新名字
     * @throws SQLException
     */
    void updateUser(String name1,String name2) throws SQLException;

    /**
     * 根据id删除用户
     * @param id 用户主键
     * @throws SQLException
     */
    void deleteUser(int id) throws SQLException;

    /**
     * 异步添加用户
     * @param user 需要添加的用户对象
     * @throws SQLException
     */
    void insertUserAsync(User user) throws SQLException;

    /**
     * 按名字或者年龄查找第一个User
     */
    User findByNameOrAge(String name1,int age1) throws SQLException;

    /**
     * 清楚所有
     * @throws SQLException
     */
    void deleteAll() throws SQLException;

    /**
     * 关闭事务
     */
    void closeRealm();
}
