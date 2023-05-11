package com.matrix_maeny.bluetoothchat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.S)
            public void run(){
                try{
                    SharedPreferences pref = getSharedPreferences("MyPref", 0); // 0 - for private mode
                    String amount = pref.getString("amount","");
                    String uid = pref.getString("uid","");
                    if(amount == "" || amount == null){
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("amount","500");
                        editor.apply();
                    }
                    if(uid == "" || uid == null){
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("uid",(String)timeStamp);
                        editor.apply();
                    }
                    sleep(2500);
                }catch (Exception ignored){

                }finally {
                    startActivity(new Intent(SplashScreenActivity.this,MainActivity2.class));
                    finish();
                }
            }
        }.start();
    }
}