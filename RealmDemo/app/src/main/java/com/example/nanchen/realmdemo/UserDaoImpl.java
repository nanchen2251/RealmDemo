package com.example.nanchen.realmdemo;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import io.realm.Realm;
import io.realm.Realm.Transaction;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @author nanchen
 * @date 16-8-8 下午5:49
 */
public class UserDaoImpl implements UserDao {

    private Context context;
    private Realm mRealm;

    public UserDaoImpl(Context context) {
        mRealm = RealmUtils.getInstance(context).getRealm();
    }

    /**
     * 同步插入
     * @param user    需要插入的用户对象
     * @throws SQLException
     */
    @Override
    public void insert(User user) throws SQLException {
        mRealm.beginTransaction();//必须先开启事务
        User user1 = mRealm.copyToRealm(user);//把User对象复制到Realm
        mRealm.commitTransaction();//提交事务
//        mRealm.close();//必须关闭，不然会造成内存泄漏
    }

    /**
     * 返回所有的User对象,并按照名字首字母排序
     * @return  User对象表
     * @throws SQLException
     */
    @Override
    public List<User> getAllUser() throws SQLException {
        List<User> list = null;
        RealmResults<User> results = mRealm.where(User.class).findAll();
        results.sort("name", Sort.DESCENDING);//针对字符串的排序，但目前并不是支持所有字符集
        list = results;
//        mRealm.close();
        return list;
    }

    /**
     * 更新一个User
     * @param user 需要更新的用户类
     * @return 返回更新后的User
     * @throws SQLException
     */
    @Override
    public User updateUser(User user) throws SQLException {
        mRealm.beginTransaction();//开启事务
        User user1 = mRealm.copyToRealmOrUpdate(user);
        mRealm.commitTransaction();//提交事务
//        mRealm.close();//必须关闭事务
        return user1;
    }

    /**
     * @param name1 老名字
     * @param name2 新名字
     * @throws SQLException
     */
    @Override
    public void updateUser(String name1, String name2) throws SQLException {
        mRealm.beginTransaction();//开启事务
        mRealm.where(User.class)
                .equalTo("name",name1)//查询出name为name1的User对象
                .findFirst()
                .setName(name2);//修改查询出的第一个对象的名字
        mRealm.commitTransaction();
//        mRealm.close();
    }

    /**
     * 根据id删除一个User
     * @param id 用户主键
     * @throws SQLException
     */
    @Override
    public void deleteUser(int id) throws SQLException {
        User user = mRealm.where(User.class).equalTo("id",id).findFirst();//删除id列值为id的行
        mRealm.beginTransaction();
        user.deleteFromRealm();//从数据库删除
        mRealm.commitTransaction();
//        mRealm.close();
    }

    /**
     * 异步插入User
     * @param user 需要添加的用户对象
     * @throws SQLException
     */
    @Override
    public void insertUserAsync(final User user) throws SQLException {
        //一个Realm只能在同一个线程访问，在子线程中进行数据库操作必须重新获取realm对象
        mRealm.executeTransaction(new Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.beginTransaction();//开启事务
                User user1 = realm.copyToRealm(user);
                realm.commitTransaction();
                realm.close();//记得关闭事务
            }
        });
//        mRealm.close();//外面也不能忘记关闭事务
    }


    /**
     * 返回第一个指定名字或者年龄的对象
     * @param name1 名字
     * @param age1  年龄
     */
    @Override
    public User findByNameOrAge(String name1,int age1) throws SQLException{
        User user = mRealm.where(User.class)
                .equalTo("name",name1)//相当于where name = name1
                .or()//或，连接查询条件，没有这个方式时会默认是&连接
                .equalTo("age",age1)//相当于where age = age1
                .findFirst();
        //整体相当于select * from (表名) where name = (传入的name) or age = （传入的age）limit 1;
//        mRealm.close();
        return user;
    }

    @Override
    public void deleteAll() throws SQLException {
        mRealm.beginTransaction();
        mRealm.where(User.class).findAll().deleteAllFromRealm();
        mRealm.commitTransaction();
//        mRealm.close();
    }


    @Override
    public void closeRealm() {
        mRealm.close();
    }
}
