package com.example.handaccount.Dao;

import android.content.Context;
import android.widget.Toast;

import com.example.handaccount.DB.User;
import com.example.handaccount.Log.LogUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

public class UserDao {

    /*
    账号密码注册
     */
    public static void signUp(String userPhone, String password, final Context context) {
        final User user = new User();
        user.setUsername("" + System.currentTimeMillis());
        user.setMobilePhoneNumber(userPhone);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User gameUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(context, "创建成功", Toast.LENGTH_SHORT).show();
                    LogUtil.v("UserDao", "创建成功");
                } else {
                    Toast.makeText(context, "创建失败" + e.getMessage() + "111", Toast.LENGTH_SHORT).show();
                    LogUtil.v("UserDao", "创建失败");
                }
            }
        });
    }
    /*
    一键注册登录
     */
    public static void signOrLogin(String phone, String code, String pwd, final Context context){
        User user = new User();
        user.setMobilePhoneNumber(phone);
        user.setUsername(phone);
        user.setPassword(pwd);
        user.signOrLogin(code, new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e == null){
                    Toast.makeText(context, "一键登录注册成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "一键登录注册失败" + e.getMessage() + e.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /*
    手机号密码登录
     */
    public static void loginByPhone(String phone, String pwd, final Context context){
        BmobUser.loginByAccount(phone, pwd, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(user != null){
                    //Toast.makeText(context, "手机号密码登录注册成功", Toast.LENGTH_SHORT).show();
                    LogUtil.v("UserDao", "手机号密码登录注册成功");
                }else{
                    //Toast.makeText(context, "手机号密码登录注册失败" + e.getMessage() + e.getErrorCode(), Toast.LENGTH_SHORT).show();
                    LogUtil.v("UserDao", "手机号密码登录注册失败");
                }
            }
        });
    }

}
