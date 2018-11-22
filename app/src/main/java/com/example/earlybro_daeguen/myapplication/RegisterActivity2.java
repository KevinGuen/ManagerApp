package com.example.earlybro_daeguen.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class RegisterActivity2 extends AppCompatActivity {
    String Image="";
    Bitmap bitmap;
    String shopID,shopPassword,shopEmail,managerTel,shopName;
    private ActivityManager am = ActivityManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        am.addActivity(this);
        setContentView(R.layout.activity_register2);
        Intent intent = getIntent();
        shopID = intent.getStringExtra("shopID");
        shopPassword = intent.getStringExtra("shopPassword");
        shopEmail = intent.getStringExtra("shopEmail");
        managerTel = intent.getStringExtra("managerTel");

    }

    public void LoadImg (View view){

    Intent intent2 = new Intent(Intent.ACTION_PICK);
    intent2.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    intent2.setType(MediaStore.Images.Media.CONTENT_TYPE);
    startActivityForResult(intent2, 1);
    Button imgLoadbt = (Button)findViewById(R.id.BusinessImgLoad);
    imgLoadbt.setClickable(false);

    }

    public void Gosearch(View view){

        Intent intent = new Intent(RegisterActivity2.this, SearchAdress.class);
        RegisterActivity2.this.startActivityForResult(intent,11);

    }

    public void Senddata2(View view) {



        EditText nameText = (EditText) findViewById(R.id.shopName);
        EditText addressText = (EditText) findViewById(R.id.address);
        EditText businessNum = (EditText) findViewById(R.id.businessNum);

        String shopBusinessNum = businessNum.getText().toString();
        shopName = nameText.getText().toString();
        String Time = String.valueOf(System.currentTimeMillis());
        String filename = shopID  + Time.substring(Time.length()-6, Time.length());
        String shopAddress = addressText.getText().toString();

        CheckInput TestID = new CheckInput();

     /*   Response.Listener<String> responseListener = new Response.Listener<String>(){  네이버 API 사용

            @Override
            public void onResponse(String response){

                try {

                    JSONObject jsonResponse = new JSONObject(response);
                    String Latitude = jsonResponse.getString("test");
                    Latitude = Latitude.replace("}" , "");
                    Latitude = Latitude.replace(" " , "");
                    Latitude = Latitude.replace("\n" , "");
                    Latitude = Latitude.replace("]" , "");
                    int indexPoint = Latitude.indexOf("point");

                    Latitude =  Latitude.substring(indexPoint+8, Latitude.length());

                    Toast.makeText(getApplicationContext(), Latitude, Toast.LENGTH_LONG).show();

                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        };

        AddressToPoint addressToPoint = new AddressToPoint (shopAddress, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(addressToPoint);*/



        if (!TestID.CheckNull(shopName)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity2.this);
            builder.setMessage("가게이름이 올바르지 않습니다.").setNegativeButton("확인", null).create().show();
        }

        String latitude="";
        String longitude="";

        Geocoder geocoder = new Geocoder(this);

        try {
            List<Address> listAddress = geocoder.getFromLocationName(shopAddress,5);
            if(listAddress.size() > 0){

                Address address = listAddress.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();


                latitude = String.valueOf(lat);
                longitude = String.valueOf(lng);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        UploadbusinessImage uploadImage = new UploadbusinessImage(shopID, filename, Image, responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(RegisterActivity2.this);
        queue2.add(uploadImage);

        Intent intent = new Intent(RegisterActivity2.this, RegisterActivity3.class);
        intent.putExtra("shopID", shopID);
        intent.putExtra("shopPassword", shopPassword);
        intent.putExtra("shopEmail", shopEmail);
        intent.putExtra("shopName", shopName);
        intent.putExtra("managerTel", managerTel);
        intent.putExtra("shopAddress", shopAddress);
        intent.putExtra("shopBusinessNum", shopBusinessNum);
        intent.putExtra("filename", filename);
        intent.putExtra("shopLatitude",latitude);
        intent.putExtra("shopHardness",longitude);


        RegisterActivity2.this.startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            Uri imgUri = data.getData();
            ImageView Bussiness = (ImageView)findViewById(R.id.BusinessRegi);
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                int width =  (int)(bitmap.getWidth()*0.25);
                int height = (int)(bitmap.getHeight()*0.25);
                bitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);
                Bussiness.setImageBitmap(bitmap);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] imageBytes = outputStream.toByteArray();
                Image = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == 11 ){
            EditText addressText = (EditText) findViewById(R.id.address);
            String address = data.getStringExtra("address");
            String subAddress = address.substring(8,address.length());
            addressText.setText(subAddress);
        }
        }

}
