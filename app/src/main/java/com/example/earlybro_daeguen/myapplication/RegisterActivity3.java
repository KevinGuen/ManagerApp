package com.example.earlybro_daeguen.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterActivity3 extends AppCompatActivity {
    String Image="";
    Bitmap bitmap;
    String shopID,shopPassword,shopEmail,managerTel,shopName,shopTel,shopAddress,businessImg,businessNum, businessfilename, businessImgPath, shopLatitude, shopHardness, shopInfo;
    private ActivityManager am = ActivityManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        am.addActivity(this);
        setContentView(R.layout.activity_register3);

        Intent intent = getIntent();
        shopID = intent.getStringExtra("shopID");
        shopPassword = intent.getStringExtra("shopPassword");
        shopEmail = intent.getStringExtra("shopEmail");
        shopName = intent.getStringExtra("shopName");
        managerTel = intent.getStringExtra("managerTel");
        shopAddress = intent.getStringExtra("shopAddress");
        businessNum = intent.getStringExtra("shopBusinessNum");
        businessfilename = intent.getStringExtra("filename");
        shopLatitude = intent.getStringExtra("shopLatitude");
        shopHardness = intent.getStringExtra("shopHardness");

        businessImgPath = "shopImage/" + shopID + "/" + businessfilename + ".jpg";
    }

    public void LoadImg2(View view){

            Intent intent2 = new Intent(Intent.ACTION_PICK);
            intent2.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent2.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent2,   100);

    }


    public void Registerdata(View view) {
        EditText shopT = (EditText)findViewById(R.id.shopTelText);
        EditText shopInfoText = (EditText)findViewById(R.id.shopInfoText);
        shopTel = shopT.getText().toString();
        shopInfo = shopInfoText.getText().toString();

        String Time = String.valueOf(System.currentTimeMillis());
        String filename = shopID  + Time.substring(Time.length()-6, Time.length());
        String Path2 = "shopImage/" + shopID + "/" + filename + ".jpg";

        CheckInput TestID = new CheckInput();

        if (TestID.CheckID(shopID)&&TestID.CheckshopTel(shopTel)&&TestID.CheckEmail(shopEmail)&&TestID.CheckmanagerTel(managerTel)) {

            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity3.this);
                            builder.setMessage("회원등록이 완료되었습니다.").setPositiveButton("확인", null).create().show();

                            Intent intent = new Intent(RegisterActivity3.this, LoginActivity.class);
                            RegisterActivity3.this.startActivity(intent);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity3.this);
                            builder.setMessage("회원등록이 실패하였습니다.").setNegativeButton("확인", null).create().show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            RegisterRequest registerRequest = new RegisterRequest(shopID, shopPassword, shopEmail, shopName, shopAddress, shopTel, managerTel,businessNum, businessImgPath, Path2, shopLatitude, shopHardness, shopInfo, responseListener);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity3.this);
            queue.add(registerRequest);

            Response.Listener<String> responseListener2 = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonResponse = new JSONObject(response);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            UploadshopImage uploadImage = new UploadshopImage(shopID, filename, Image, responseListener2);
            RequestQueue queue2 = Volley.newRequestQueue(RegisterActivity3.this);
            queue2.add(uploadImage);

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            Uri imgUri = data.getData();
            ImageView shopImage = (ImageView)findViewById(R.id.shopImage);

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                int width = 250;
                int height = 125;
                bitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);
                shopImage.setImageBitmap(bitmap);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] imageBytes = outputStream.toByteArray();
                Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
