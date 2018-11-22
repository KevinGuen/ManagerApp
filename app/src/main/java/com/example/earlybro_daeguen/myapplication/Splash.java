package com.example.earlybro_daeguen.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class Splash extends Activity {

    /** 로딩 화면이 떠있는 시간(밀리초단위)  **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** 처음 액티비티가 생성될때 불려진다. */
    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);




        /* SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료한다.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
//                 메뉴액티비티를 실행하고 로딩화면을 죽인다.
                SharedPreferences pref = getSharedPreferences("Autologin", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                String ID = pref.getString("ID","");
                String Password = pref.getString("Password","");



                if(ID != "" && Password !=""){
                    Response.Listener<String> responseListener = new Response.Listener<String>(){

                        @Override
                        public void onResponse(String response){
                            try {

                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success){

                                    String shopID = jsonResponse.getString("shopID");
                                    String shopPassword = jsonResponse.getString("shopPassword");
                                    String shopEmail = jsonResponse.getString("shopEmail");
                                    String shopName = jsonResponse.getString("shopName");
                                    String shopAddress = jsonResponse.getString("shopAddress");
                                    String shopTel = jsonResponse.getString("shopTel");
                                    String managerTel = jsonResponse.getString("managerTel");
                                    String businessNum = jsonResponse.getString("businessNum");
                                    String Latitude = jsonResponse.getString("shopLatitude");
                                    String Longitude = jsonResponse.getString("shopHardness");
                                    String shopInfo = jsonResponse.getString("shopInfo");


                                    Intent intent = new Intent(Splash.this, MainActivity.class);
                                    intent.putExtra("shopID", shopID);
                                    intent.putExtra("shopPassword", shopPassword);
                                    intent.putExtra("shopEmail",shopEmail);
                                    intent.putExtra("shopName", shopName);
                                    intent.putExtra("shopAddress", shopAddress);
                                    intent.putExtra("shopTel", shopTel);
                                    intent.putExtra("managerTel", managerTel);
                                    intent.putExtra("businessNum", businessNum);
                                    intent.putExtra("shopInfo", shopInfo);
                                    intent.putExtra("Latitude", Latitude);
                                    intent.putExtra("Longitude", Longitude);


                                    Splash.this.startActivity(intent);
                                    Splash.this.finish();

                                }

                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(ID, Password, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Splash.this);
                    queue.add(loginRequest);
                }

          else {
                    Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
