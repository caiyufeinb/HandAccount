package com.example.handaccount.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.handaccount.Log.LogUtil;
import com.example.handaccount.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPwdActivity extends AppCompatActivity {
    public static String TAG = "ForgetPwdActivity";
    @BindView(R.id.confirm_password)
    EditText confirmPwd;
    @BindView(R.id.new_password)
    EditText newPwd;
    @BindView(R.id.forget_phonenumber)
    EditText userPhone;
    @BindView(R.id.forget_getverifycode)
    QMUIRoundButton forgetGetVerify;
    @BindView(R.id.forget_verifycode)
    EditText forgetVerify;
    @BindView(R.id.button_change)
    QMUIRoundButton buttonChange;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pwd);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.forget_getverifycode)
    public void onClick(){
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

    @OnClick(R.id.button_change)
    public void onForgetClick(){
        String newPassword = newPwd.getText().toString().trim();
        String confrim = confirmPwd.getText().toString().trim();
        String phone = userPhone.getText().toString().trim();
        String forget = forgetVerify.getText().toString().trim();
        if(phone.isEmpty() || newPassword.isEmpty() || forget.isEmpty() || confrim.isEmpty()){
            Toast.makeText(ForgetPwdActivity.this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!confrim.equals(newPassword)){
            Toast.makeText(ForgetPwdActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        BmobUser.resetPasswordBySMSCode(forget, newPassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    Toast.makeText(ForgetPwdActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgetPwdActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ForgetPwdActivity.this, "密码修改失败" + e.getErrorCode() + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
