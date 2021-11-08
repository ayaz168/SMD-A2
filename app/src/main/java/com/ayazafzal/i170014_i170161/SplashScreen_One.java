package com.ayazafzal.i170014_i170161;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class SplashScreen_One extends AppCompatActivity {
    LinearLayout screenX;
    //SharedPreferences myShare;
    //SharedPreferences.Editor myEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_one);
        /*myShare=getSharedPreferences("com.ayazafzal.i170014_i170161",MODE_PRIVATE);
        myEditor=myShare.edit();
        String Email=myShare.getString("Email","0");
        String Password=myShare.getString("Password","0");
        if(!Email.equals("0")){
            Intent inX=new Intent(SplashScreen_One.this,LogIn_Three.class);
            startActivity(inX);
        }*/
        SharedPreferences prefs = this.getSharedPreferences(
                "com.ayazafzal.i170014_i170161", Context.MODE_PRIVATE);
        String email = "email";
        String ifDefault= prefs.getString(email, "None");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(ifDefault.equals("None")){
                    Intent inX=new Intent(SplashScreen_One.this,SignUp_Two.class);
                    startActivity(inX);
                }else{
                    Intent inX=new Intent(SplashScreen_One.this,Home_Five.class);
                    inX.putExtra("UserId",ifDefault);
                    startActivity(inX);
                }

            }
        }, 5000);
    }

}