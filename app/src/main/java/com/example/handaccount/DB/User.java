package com.example.handaccount.DB;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    //昵称
    private String NickName;

}
