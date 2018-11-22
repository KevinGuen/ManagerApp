package com.example.earlybro_daeguen.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SettingActivity extends AppCompatActivity {
    private ActivityManager am = ActivityManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        am.addActivity(this);

        }


        public void logout(View view){

            SharedPreferences Autologin = getSharedPreferences("Autologin", MODE_PRIVATE);
            SharedPreferences.Editor editor = Autologin.edit();
            editor.clear();
            editor.commit();

            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
            SettingActivity.this.startActivity(intent);
            SettingActivity.this.finish();

        }


    public void backMain(View view){
        this.onBackPressed();
    }


}
