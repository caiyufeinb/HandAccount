package com.example.handaccount.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.handaccount.DB.User;
import com.example.handaccount.Dao.UserDao;
import com.example.handaccount.Log.LogUtil;
import com.example.handaccount.R;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 用户的注册界面，通过手机号获取验证码后设置密码，成功后跳转主页面
 */
public class MainActivity extends AppCompatActivity {
    //顶部栏
    @BindView(R.id.topbar_register)
    QMUITopBar mTopBar;
    //注册按钮
    @BindView(R.id.button_register)
    QMUIRoundButton mRegisterButton;
    //电话号码输入框
    @BindView(R.id.edittext_phonenumber)
    EditText userPhone;
    //验证码输入框
    @BindView(R.id.edittext_verifycode)
    EditText verifyCode;
    //获取验证码按钮
    @BindView(R.id.edittext_getverifycode)
    QMUIRoundButton getVerifyButton;
    //密码输入框
    @BindView(R.id.edittext_password)
    EditText mPassword;
    final String TAG = "MainActivity";
    //保存收到的验证码



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);

        ButterKnife.bind(this);
        //LogUtil.w("MainActivity","mTopBar" +"                   " + mTopBar+"                                     11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        initTopBar();
        //UserDao.signUp("18261584843", "caiyufei123", MainActivity.this);

    }
    //初始化顶部栏
    private void initTopBar(){
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.setTitle("注册账号");
    }

    /*
    注册按钮点击事件
     */
    @OnClick({R.id.button_register})
    public void onRegisterClicked(View view) {
        String phone = userPhone.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        String verify = verifyCode.getText().toString().trim();
        if(phone.isEmpty() || password.isEmpty() || verify.isEmpty()){
            Toast.makeText(this, "手机号码，密码或者验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        UserDao.signOrLogin(phone, verify, password, MainActivity.this);
        //记得添加跳转主界面逻辑
    }

    /*
    获取验证码
     */
    @OnClick(R.id.edittext_getverifycode)
    public void onVerifyClicked(View view){
        final String phone = userPhone.getText().toString().trim();
        if(phone.isEmpty()){
            Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
        BmobSMS.requestSMSCode(phone, "", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if(e == null){
                    LogUtil.i(TAG, "短信验证码发送成功" + integer);
                }else {
                    LogUtil.v(TAG, "发送失败" + e.getErrorCode()+ e.getMessage());
                }
            }
        });
    }

}
