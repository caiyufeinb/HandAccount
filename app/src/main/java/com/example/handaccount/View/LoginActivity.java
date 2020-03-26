package com.example.handaccount.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.handaccount.Dao.UserDao;
import com.example.handaccount.R;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;

/**
 * 该界面用于初始的用户登录界面
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.topbar_login)
    QMUITopBar mTopBar;
    @BindView(R.id.user_phonenumber)
    EditText userPhone;
    @BindView(R.id.user_password)
    EditText userPwd;
    @BindView(R.id.jump_register)
    QMUIRoundButton jumpRegister;
    @BindView(R.id.button_login)
    QMUIRoundButton buttonLogin;
    @BindView(R.id.forget_pwd)
    QMUIRoundButton forget_pwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        Bmob.initialize(this,"b66bb24bd207440912e6e9925752dbf2");
        mTopBar.setTitle("登录");
    }
    @OnClick(R.id.jump_register)
    public void onClick(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.forget_pwd)
    public void onForgetClick(){
        Intent intent = new Intent(LoginActivity.this, ForgetPwdActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_login)
    public void onLoginClick(){
        String phone = userPhone.getText().toString().trim();
        String pwd = userPwd.getText().toString().trim();
        if(phone.isEmpty() || pwd.isEmpty()){
            Toast.makeText(LoginActivity.this, "手机号和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        UserDao.loginByPhone(phone, pwd, LoginActivity.this);
        Toast.makeText(LoginActivity.this, "登录成功1111111", Toast.LENGTH_SHORT).show();
    }
}
