package com.example.transportlogin;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class UserSession {
    SharedPreferences usersession;
    SharedPreferences.Editor editor;
    Context context;


    private static final String is_verify = "IsVerify";

    public static final String Key_Number = "mobilenumber";

    public UserSession(Context context){
      usersession = context.getSharedPreferences("sharemobile",Context.MODE_PRIVATE);
      editor = usersession.edit();
    }
    public void createloginSession(String mobilenumber){
      editor.putBoolean(is_verify,true);
      editor.putString(Key_Number,mobilenumber);
      editor.commit();

    }
    public HashMap<String,String> getuserdatafromsession(){
        HashMap<String,String> userdata  = new HashMap<String, String>();
        userdata.put(Key_Number,usersession.getString(Key_Number,null));
        return userdata;
    }
    public boolean checkverfied(){
        if (usersession.getBoolean(is_verify,true)){
            return true;
        }else {
            return false;
        }

    }
}
