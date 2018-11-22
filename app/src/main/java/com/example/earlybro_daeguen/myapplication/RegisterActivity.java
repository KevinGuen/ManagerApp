package com.example.earlybro_daeguen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private ActivityManager am = ActivityManager.getInstance();
    private int checkClick = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        am.addActivity(this);
        setContentView(R.layout.activity_register);
    }

    public void CheckID_existbt(View view){

        CheckID_exist();

    }

    public void Senddata(View view){

        EditText IDText = (EditText) findViewById(R.id.IDText);
        EditText passwordText = (EditText) findViewById(R.id.PasswordText);
        EditText emailText = (EditText) findViewById(R.id.emailText);
        EditText managerTelText = (EditText) findViewById(R.id.managerTelText);

        String shopID = IDText.getText().toString();
        String shopPassword = passwordText.getText().toString();
        String shopEmail = emailText.getText().toString();
        String managerTel = managerTelText.getText().toString();

        CheckInput checkInput = new CheckInput();

        if(checkInput.CheckID(shopID) && checkInput.CheckEmail(shopEmail) && checkInput.CheckmanagerTel(managerTel)&&checkClick==1) {

            Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
            intent.putExtra("shopID", shopID);
            intent.putExtra("shopPassword", shopPassword);
            intent.putExtra("shopEmail", shopEmail);
            intent.putExtra("managerTel", managerTel);

            RegisterActivity.this.startActivity(intent);
        }
        else{

            if (!checkInput.CheckID(shopID)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("아이디는 알파벳 숫자만 사용가능 합니다.").setNegativeButton("확인", null).create().show();
            }

            if (!checkInput.CheckNull(shopID)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("아이디를 입력해야 합니다.").setNegativeButton("확인", null).create().show();
            }

            if (!checkInput.CheckEmail(shopEmail)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("이메일 주소가 올바르지 않습니다.").setNegativeButton("확인", null).create().show();
            }
            if (!checkInput.CheckNull(shopPassword)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("비밀번호를 입력하지 않았습니다.").setNegativeButton("확인", null).create().show();
            }

            if (!checkInput.CheckmanagerTel(managerTel)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("핸드폰 번호가 올바르지 않습니다.").setNegativeButton("확인", null).create().show();
            }
            if (!checkInput.CheckNull(managerTel)){
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("핸드폰 번호를 입력하지 않았습니다.").setNegativeButton("확인", null).create().show();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("중복확인을 하지 않았습니다.").setNegativeButton("확인", null).create().show();
        }
    }

    public void CheckID_exist(){
        final EditText IDText = (EditText)findViewById(R.id.IDText);
        final String shopID = IDText.getText().toString();
        final CheckInput checkInput = new CheckInput();
        Response.Listener<String> responseListener = new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    if(checkInput.CheckID(shopID)){
                    if(shopID.length()>0) {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("아이디가 사용 가능합니다").setNegativeButton("확인", null).create().show();
                            IDText.setEnabled(false);
                            checkClick = 1;
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("아이디가 중복 됩니다.").setNegativeButton("확인", null).create().show();
                            checkClick = 0;
                        }
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("아이디를 입력해야 합니다.").setNegativeButton("확인", null).create().show();
                            }

                        }
                        else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("아이디는 알파벳 숫자만 사용가능 합니다.").setNegativeButton("확인", null).create().show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        IDrequest idrequest = new IDrequest(shopID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(idrequest);


    }


}
