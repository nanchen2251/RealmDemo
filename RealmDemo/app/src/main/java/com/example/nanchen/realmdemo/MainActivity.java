package com.example.nanchen.realmdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDao = new UserDaoImpl(this);
        try {
            userDao.deleteAll();//先删除所有，以免demo出现主键已经存在的异常
            User user = new User();
            user.setId(10);
            user.setName("小刺猬");
            user.setAge(22);
            user.setHasGrilFriend(true);
            userDao.insert(user);

            Log.d("flag","插入小刺猬----"+userDao.getAllUser().toString());

            for (int i = 0; i < 5; i++) {
                userDao.insert(new User(i,"南尘"+i,20+i));
            }
            Log.d("flag","插入5个对象----"+userDao.getAllUser().toString());
            Log.d("flag","查询1----"+userDao.findByNameOrAge("南尘1",20));
            Log.d("flag","查询2----"+userDao.findByNameOrAge("南尘1",23));
            userDao.updateUser("南尘1","nanchen");
            Log.d("flag","更新1----"+userDao.findByNameOrAge("南尘1",23));
            userDao.deleteUser(0);//删除0
            Log.d("flag","删除后查看----"+userDao.getAllUser().toString());


            //统一关闭事务
            userDao.closeRealm();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
