package com.example.handaccount.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
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
    @BindView(R.id.rem_inf)
    CheckBox reminf;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        Bmob.initialize(this,"b66bb24bd207440912e6e9925752dbf2");
        mTopBar.setTitle("登录");
        pref = PreferenceManager.getDefaultSharedPreferences(this);//获取SharedPreferences对象
        boolean isRemember = pref.getBoolean("remember", false);//判断是否要保存账号密码
        if (isRemember) {
            //将账号密码设置在文本框中
            String phoneNumber = pref.getString("phone", "");
            String password = pref.getString("password", "");
            userPhone.setText(phoneNumber);
            userPwd.setText(password);
            reminf.setChecked(true);//设置checkedBox为选中状态
        }

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
        editor = pref.edit();
        String phone = userPhone.getText().toString().trim();
        String pwd = userPwd.getText().toString().trim();
        if(phone.isEmpty() || pwd.isEmpty()){
            Toast.makeText(LoginActivity.this, "手机号和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        UserDao.loginByPhone(phone, pwd, LoginActivity.this);
        Toast.makeText(LoginActivity.this, "登录成功1111111", Toast.LENGTH_SHORT).show();
        if(reminf.isChecked()){
            editor.putBoolean("remember", true);
            editor.putString("phone", phone);
            editor.putString("password", pwd);
        }else{
            editor.clear();
        }
        editor.apply();
        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}
