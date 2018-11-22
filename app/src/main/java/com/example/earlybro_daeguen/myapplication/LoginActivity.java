package com.example.earlybro_daeguen.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;



public class LoginActivity extends AppCompatActivity {

    private long backPrssedTime = 0;
    private ActivityManager am = ActivityManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        am.addActivity(this);

}


public void Login(View view){

 EditText idText = (EditText)findViewById(R.id.lgidText);
 EditText passwordText = (EditText)findViewById(R.id.passwordText);

 String shopID = idText.getText().toString();
 String shopPassword = passwordText.getText().toString();
idText.setText("");
passwordText.setText("");

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



              SharedPreferences pref = getSharedPreferences("Autologin", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("ID", shopID);
                editor.putString("Password", shopPassword);
                editor.commit();


                 Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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



                LoginActivity.this.startActivity(intent);
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("로그인 실패").setNegativeButton("다시시도", null).create().show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
};

LoginRequest loginRequest = new LoginRequest(shopID, shopPassword, responseListener);
RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
queue.add(loginRequest);

}

public void GoRegister(View view){

Intent resgisterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
LoginActivity.this.startActivity(resgisterIntent);

}

@Override
    public void onBackPressed(){
    long tempTime = System.currentTimeMillis();
    long intervalTime = tempTime - backPrssedTime;

    if (0 <= intervalTime && 2000 >= intervalTime) {

    am.finishAllActivity();

        }
    else
        {
    backPrssedTime = tempTime;
    Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 누를시 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

    }

    public void sendEmail(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.activity_alertlayout, null);
        builder.setTitle("아이디/비밀번호찾기");
        builder.setView(dialoglayout);

        final EditText checkBusiness = (EditText) dialoglayout.findViewById(R.id.alertText);
        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String checkbusiness = checkBusiness.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"가입시 이메일로 회원정보가 발송되었습니다.",Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                };
                Businessrequest businessrequest = new Businessrequest(checkbusiness, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(businessrequest);

                dialog.dismiss();
            }
        });



        builder.show();


    }


}
