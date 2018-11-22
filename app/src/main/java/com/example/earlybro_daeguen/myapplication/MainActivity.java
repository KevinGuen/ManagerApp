package com.example.earlybro_daeguen.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    Boolean lastitemVisibleflag = false;
    private ActivityManager am = ActivityManager.getInstance();
    private GoogleMap mMap;

    String shopID,shopPassword,shopEmail,shopName,shopAddress,shopTel,managerTel,businessNum, shopInfo, Latitude, Longitude;
    int btflag=1;
    int uploadflag;
    Bitmap bitmap;
    ArrayList<Bitmap> getBitmaps = new ArrayList<Bitmap>();
    private long backPrssedTime = 0;
    ImageGridAdapter imageGridA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        am.addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        shopID = intent.getStringExtra("shopID");
        shopPassword = intent.getStringExtra("shopPassword");
        shopEmail = intent.getStringExtra("shopEmail");
        shopName = intent.getStringExtra("shopName");
        shopAddress = intent.getStringExtra("shopAddress");
        shopTel = intent.getStringExtra("shopTel");
        shopTel = addDot(shopTel);
        managerTel = intent.getStringExtra("managerTel");
        managerTel = addDot(managerTel);
        businessNum = intent.getStringExtra("businessNum");
        shopInfo = intent.getStringExtra("shopInfo");
        Latitude = intent.getStringExtra("Latitude");
        Longitude = intent.getStringExtra("Longitude");

        Response.Listener<String> responseListener2 = new Response.Listener<String>(){

            @Override
            public void onResponse(String response){
                ImageView UpshopImage = (ImageView)findViewById(R.id.shopImage);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String shopImage = jsonResponse.getString("Image");
                    byte[] decodedString = Base64.decode(shopImage, Base64.DEFAULT);
                    Bitmap shopImageBmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    UpshopImage.setImageBitmap(shopImageBmp);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        DownloadshopImage downloadshopImage = new DownloadshopImage(shopID,  responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(this);
        queue2.add(downloadshopImage);


        TextView shopTelView = (TextView) findViewById(R.id.shopTelText2);
        shopTelView.setText(shopTel);
        TextView shopNameView = (TextView) findViewById(R.id.shopNameText);
        shopNameView.setText(shopName);
        TextView infoBusinessNum = (TextView) findViewById(R.id.infoBusiness);
        infoBusinessNum.setText(businessNum);
        TextView shopAddressTv = (TextView) findViewById(R.id.shopAddress);
        shopAddressTv.setText(shopAddress);
        TextView shopInfoView = (TextView) findViewById(R.id.salutation);
        shopInfoView.setText(shopInfo);



        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response){

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int Size = jsonResponse.getInt("size");


                    if (Size > 0) {
                        for (int i = 1; i <= Size; i++) {

                            String Key = "Image" + String.valueOf(i);
                            String tempGet = jsonResponse.getString(Key);
                            byte[] decodedString = Base64.decode(tempGet, Base64.DEFAULT);
                            Bitmap tmpbit = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            //tmpbit = Bitmap.createScaledBitmap( tmpbit,300,300,false);
                            addeventView(tmpbit, i);
/*
                            getBitmaps.add(tmpbit);
                            ImageView downtestVw = (ImageView)findViewById(R.id.testDown);
                            downtestVw.setImageBitmap(ImgArray[5]);
*/
                        }
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        DownloadeventImage downloadeventImage = new DownloadeventImage(shopID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(downloadeventImage);





    }

    public void addeventView(Bitmap bitmap, int idNum){

        ImageView iv = new ImageView(this);
        LinearLayout horizontalScrollview = (LinearLayout)findViewById(R.id.eventView);
        LinearLayout.LayoutParams Iparms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(Iparms);
        iv.setImageBitmap(bitmap);
        iv.setId(idNum);
        horizontalScrollview.addView(iv);

    }

    public String addDot(String needDot){
        if(needDot.length() == 10){
            needDot = needDot.substring(0,3) + "." + needDot.substring(3,6) + "." + needDot.substring(6,needDot.length());
        }
        else if(needDot.length() == 11){
            needDot = needDot.substring(0,3) + "." + needDot.substring(3,7) + "." + needDot.substring(7,needDot.length());
        }
        return needDot;
    }


    public void ViewControl (View view) {

        Button shopbt = (Button)findViewById(R.id.shopInfobt);
        Button eventbt = (Button)findViewById(R.id.eventbt);
        Button toefingbt = (Button)findViewById(R.id.toefingbt);
        Button myinfobt = (Button)findViewById(R.id.myinfobt);
        LinearLayout shopView = (LinearLayout)findViewById(R.id.shopInfoView);
        LinearLayout eventView = (LinearLayout)findViewById(R.id.eventView);
        LinearLayout toefingView = (LinearLayout)findViewById(R.id.toefingView);
        LinearLayout myinfoView = (LinearLayout)findViewById(R.id.myinfoView);
        LinearLayout modifylayout = (LinearLayout)findViewById(R.id.modifylayout);
        ScrollView scrollView = (ScrollView)findViewById(R.id.Scroll);
        Button modifybt = (Button)findViewById(R.id.modifybt);
        Button mdSuccess = (Button)findViewById(R.id.mdsuccess);
        Button uploaditembt = (Button)findViewById(R.id.uploaditem);

        if(view.getId() == R.id.shopInfobt){

            shopbt.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.bt));
            setBt(eventbt,toefingbt,myinfobt);
            shopbt.setTextColor(getResources().getColor(R.color.red));

            btflag = 1;

            scrollView.setVisibility(View.VISIBLE);
            shopView.setVisibility(View.VISIBLE);
            setGone(myinfoView,eventView,toefingView,modifylayout,uploaditembt);
            modifybt.setVisibility(View.VISIBLE);

            controlEdit(0);


        }

        else if(view.getId() == R.id.eventbt){

            eventbt.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.bt));
            setBt(shopbt,toefingbt,myinfobt);
            eventbt.setTextColor(getResources().getColor(R.color.red));

            btflag = 2;
            scrollView.setVisibility(View.VISIBLE);
            eventView.setVisibility(View.VISIBLE);
            setGone(shopView,toefingView,myinfoView,modifylayout,uploaditembt);
            modifybt.setVisibility(View.VISIBLE);
        }

        else if(view.getId() == R.id.toefingbt){

            toefingbt.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.bt));
            toefingbt.setTextColor(getResources().getColor(R.color.red));
            setBt(shopbt,eventbt,myinfobt);

            setGone(eventView,shopView,myinfoView,modifylayout,uploaditembt);
            scrollView.setVisibility(View.GONE);
            toefingView.setVisibility(View.VISIBLE);
            modifybt.setVisibility(View.VISIBLE);
            btflag = 3;
            DownImage();

        }

        else if(view.getId() == R.id.myinfobt){

            TextView infoEmail = (TextView)findViewById(R.id.infoEmail);
            TextView infoMobile = (TextView)findViewById(R.id.infoMobile);
            TextView infoName = (TextView)findViewById(R.id.infoName);

            myinfobt.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.bt));
            myinfobt.setTextColor(getResources().getColor(R.color.red));
            setBt(shopbt,eventbt,toefingbt);

            infoEmail.setText(shopEmail);
            infoMobile.setText(managerTel);
            infoName.setText(shopName);
            modifybt.setVisibility(View.VISIBLE);

            btflag = 4;
            scrollView.setVisibility(View.VISIBLE);
            myinfoView.setVisibility(View.VISIBLE);
            setGone(eventView,shopView,toefingView,modifylayout,uploaditembt);

        }
    }

    public void reject (View view){
    }

    public void setGone(LinearLayout linearLayout1,LinearLayout linearLayout2,LinearLayout linearLayout3, LinearLayout linearLayout4, Button modify2 ){

        linearLayout1.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.GONE);
        linearLayout4.setVisibility(View.GONE);
        modify2.setVisibility(View.GONE);

    }

    public void setBt(Button button1, Button button2, Button button3){

        button1.setBackgroundResource(R.color.white);
        button1.setTextColor(getResources().getColor(R.color.gray));
        button2.setBackgroundResource(R.color.white);
        button2.setTextColor(getResources().getColor(R.color.gray));
        button3.setBackgroundResource(R.color.white);
        button3.setTextColor(getResources().getColor(R.color.gray));

    }

    public void controlEdit (int visibleflag){

        EditText shopT = (EditText) findViewById(R.id.shopTelText2);
        EditText dayT = (EditText) findViewById(R.id.dayText);
        EditText dayT2 = (EditText) findViewById(R.id.dayText2);
        EditText Time = (EditText) findViewById(R.id.Timetext);
        EditText hollyday = (EditText) findViewById(R.id.hollyday);
        EditText shopAD = (EditText) findViewById(R.id.shopAddress);
        EditText item = (EditText) findViewById(R.id.item);
        EditText price = (EditText) findViewById(R.id.price);

        if(visibleflag == 1) {

            shopT.setEnabled(true);
            dayT.setEnabled(true);
            dayT2.setEnabled(true);
            Time.setEnabled(true);
            hollyday.setEnabled(true);
            shopAD.setEnabled(true);
            item.setEnabled(true);
            price.setEnabled(true);

        }
        else if(visibleflag == 0){

            shopT.setEnabled(false);
            dayT.setEnabled(false);
            dayT2.setEnabled(false);
            Time.setEnabled(false);
            hollyday.setEnabled(false);
            shopAD.setEnabled(false);
            item.setEnabled(false);
            price.setEnabled(false);

        }

    }

    public void modify(View view){

        LinearLayout modifylayout = (LinearLayout)findViewById(R.id.modifylayout);
        Button modifybt = (Button)findViewById(R.id.modifybt);
        Button uploaditembt = (Button)findViewById(R.id.uploaditem);
        switch (btflag){

            case 1 :

                controlEdit(1);
                modifybt.setVisibility(View.GONE);
                modifylayout.setVisibility(View.VISIBLE);
                uploaditembt.setVisibility(View.GONE);
                break;


            case 2:

                uploaditembt.setVisibility(View.VISIBLE);
                modifybt.setVisibility(View.GONE);
                modifylayout.setVisibility(View.GONE);
                break;

            case 3:

                uploaditembt.setVisibility(View.VISIBLE);
                modifybt.setVisibility(View.GONE);
                modifylayout.setVisibility(View.GONE);
                break;


            case 4 :

                EditText infoName = (EditText) findViewById(R.id.infoName);
                EditText infoMobile = (EditText) findViewById(R.id.infoMobile);
                EditText infoEmail = (EditText) findViewById(R.id.infoEmail);
                EditText infoBusiness = (EditText) findViewById(R.id.infoBusiness);

                infoName.setEnabled(true);
                infoMobile.setEnabled(true);
                infoEmail.setEnabled(true);

                uploaditembt.setVisibility(View.GONE);
                modifybt.setVisibility(View.GONE);
                modifylayout.setVisibility(View.VISIBLE);
                break;

        }

    }

    public void DownImage(){

        getBitmaps.clear();

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int Size = jsonResponse.getInt("size");


                    if (Size > 0) {
                        for (int i = 1; i <= Size; i++) {

                            String Key = "Image" + String.valueOf(i);
                            String tempGet = jsonResponse.getString(Key);
                            byte[] decodedString = Base64.decode(tempGet, Base64.DEFAULT);
                            Bitmap tmpbit = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            tmpbit = Bitmap.createScaledBitmap( tmpbit,300,300,false);
                            getBitmaps.add(tmpbit);
                            /*Toast.makeText(g,String.valueOf(getBitmaps.get(i-1)),Toast.LENGTH_LONG).show();
                            ImageView downtestVw = (ImageView)findViewById(R.id.testDown);
                            downtestVw.setImageBitmap(ImgArray[5]);*/

                        }
                    }

                            imageGridA = new ImageGridAdapter(MainActivity.this,getBitmaps);
                            final GridView gridView = (GridView) findViewById(R.id.Viewgd);
                            gridView.setAdapter(imageGridA);

//                    gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//                        @Override
//                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                            lastitemVisibleflag = (totalItemCount > 0 ) && (firstVisibleItem + visibleItemCount) >= totalItemCount;
//
//                        }
//                        @Override
//                        public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//                            if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleflag){
//
//                                Response.Listener<String> responseListener = new Response.Listener<String>(){
//
//                                    @Override
//                                    public void onResponse(String response){
//
//                                        try {
//                                            JSONObject jsonResponse = new JSONObject(response);
//                                            int Size = jsonResponse.getInt("size");
//
//
//                                            if (Size > 0) {
//                                                for (int i = 1; i <= Size; i++) {
//
//                                                    String Key = "Image" + String.valueOf(i);
//                                                    String tempGet = jsonResponse.getString(Key);
//                                                    byte[] decodedString = Base64.decode(tempGet, Base64.DEFAULT);
//                                                    Bitmap tmpbit = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                                                    tmpbit = Bitmap.createScaledBitmap( tmpbit,300,300,false);
//                                                    imageGridA.addItem(tmpbit);
//
//                            /*Toast.makeText(g,String.valueOf(getBitmaps.get(i-1)),Toast.LENGTH_LONG).show();
//                            ImageView downtestVw = (ImageView)findViewById(R.id.testDown);
//                            downtestVw.setImageBitmap(ImgArray[5]);*/
//
//                                                }
//                                            }
//
//                                            imageGridA = new ImageGridAdapter(MainActivity.this,getBitmaps);
//                                            final GridView gridView = (GridView) findViewById(R.id.Viewgd);
//                                            gridView.setAdapter(imageGridA);
//
//                                        }
//                                        catch (Exception e){
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                };
//                                DownloadImage downloadImage = new DownloadImage(shopID, responseListener);
//                                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//                                queue.add(downloadImage);
//
//                            }
//
//                        }
//
//                    });

                }
                catch (Exception e){
                    e.printStackTrace();
                }




            }
        };
        DownloadImage downloadImage = new DownloadImage(shopID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(downloadImage);

        //return encodeImg;
    }

    public void modifycc (View view){

        LinearLayout modifylayout = (LinearLayout)findViewById(R.id.modifylayout);
        Button modifybt = (Button)findViewById(R.id.modifybt);
        Button uploaditembt = (Button)findViewById(R.id.uploaditem);


        if(btflag == 1) {

            Button modifyccbt = (Button) findViewById(R.id.modifyccbt);

            controlEdit(0);
            modifybt.setVisibility(View.VISIBLE);
            modifylayout.setVisibility(View.GONE);
            uploaditembt.setVisibility(View.GONE);

        }

        else if(btflag == 4) {
            EditText infoName = (EditText) findViewById(R.id.infoName);
            EditText infoMobile = (EditText) findViewById(R.id.infoMobile);
            EditText infoEmail = (EditText) findViewById(R.id.infoEmail);

            infoName.setEnabled(false);
            infoMobile.setEnabled(false);
            infoEmail.setEnabled(false);

            uploaditembt.setVisibility(View.GONE);
            modifybt.setVisibility(View.VISIBLE);
            modifylayout.setVisibility(View.GONE);
        }
    }

    public void modifysucess (View view){

        LinearLayout modifylayout = (LinearLayout) findViewById(R.id.modifylayout);
        Button modifybt = (Button) findViewById(R.id.modifybt);
        Button uploaditembt = (Button)findViewById(R.id.uploaditem);
        EditText shopT = (EditText) findViewById(R.id.shopTelText2);
        EditText shopAD = (EditText) findViewById(R.id.shopAddress);

        shopTel = shopT.getText().toString();
        shopAddress = shopAD.getText().toString();

        switch(btflag) {

            case 1 :

            controlEdit(0);
                modifybt.setVisibility(View.VISIBLE);
                modifylayout.setVisibility(View.GONE);

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                modifyShop modifyshop = new modifyShop(shopID, shopTel, shopAddress, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(modifyshop);

            break;

            case 4 :

                EditText infoName = (EditText) findViewById(R.id.infoName);
                EditText infoMobile = (EditText) findViewById(R.id.infoMobile);
                EditText infoEmail = (EditText) findViewById(R.id.infoEmail);
                EditText infoBusiness = (EditText) findViewById(R.id.infoBusiness);

                infoName.setEnabled(false);
                infoMobile.setEnabled(false);
                infoEmail.setEnabled(false);
                infoBusiness.setEnabled(false);

                uploaditembt.setVisibility(View.GONE);
                modifybt.setVisibility(View.VISIBLE);
                modifylayout.setVisibility(View.GONE);
                break;

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

//
        double lat = Double.valueOf(Latitude);
        double lng = Double.valueOf(Longitude);

//        LatLng position = new LatLng(lat, lng);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 18));

    }


    public void goSetting(View view){

        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        MainActivity.this.startActivity(intent);

    }

    public void goMap(View view){

        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("Adress",shopAddress);

        MainActivity.this.startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int width=0, height=0;
        if (resultCode == Activity.RESULT_OK) {

            Uri imgUri = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);

                if(btflag == 2) {
                    width = (int) 1080;
                    height = (int) 482;

                    bitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    byte[] imageBytes = outputStream.toByteArray();
                    String uploadeventImg = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    UploadEvent upeventimg = new UploadEvent(shopID, uploadeventImg, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(upeventimg);

                }
                else{
                    width = (int)(bitmap.getWidth()*0.25);
                    height = (int)(bitmap.getHeight()*0.25);

                    bitmap = Bitmap.createScaledBitmap(bitmap,width,height,true);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    byte[] imageBytes = outputStream.toByteArray();
                    String uploadNailImg = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    UploadItem upnailimg = new UploadItem(shopID, uploadNailImg, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(upnailimg);
                }
//                Toast.makeText(this.getApplicationContext(),width,Toast.LENGTH_LONG).show();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void uploaditem(View view){

        Intent intent2 = new Intent(Intent.ACTION_PICK);
        intent2.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent2.setType(MediaStore.Images.Media.CONTENT_TYPE);


            startActivityForResult(intent2,   100);


        Button modifybt = (Button)findViewById(R.id.modifybt);
        Button uploaditembt = (Button)findViewById(R.id.uploaditem);
        modifybt.setVisibility(View.VISIBLE);
        uploaditembt.setVisibility(View.GONE);

    }


    public void CreateExtra(String addExtraText){

        LinearLayout extraView = (LinearLayout) findViewById(R.id.ExtraView);
        int childCount = extraView.getChildCount();
        String Rid = "Extrainfo" + String.valueOf(childCount - 1);
        int lid = this.getResources().getIdentifier(Rid, "id", this.getPackageName());

        LinearLayout LatestExtrainfo = (LinearLayout) findViewById(lid);


        Toast.makeText(getApplicationContext(), String.valueOf(LatestExtrainfo.getChildCount()),Toast.LENGTH_LONG).show();
        if (LatestExtrainfo.getChildCount() > 4) {

            LinearLayout nextLinear = new LinearLayout(this);
            nextLinear.setOrientation(LinearLayout.HORIZONTAL);
            nextLinear.setWeightSum((float) 1.0);
            String newId = "Extrainfo" + String.valueOf(childCount);
            int newidnum = this.getResources().getIdentifier(newId, "id", this.getPackageName());
            nextLinear.setId(newidnum);
            LinearLayout.LayoutParams paramLinear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int marginValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics());  // dp to fx
            paramLinear.setMargins(0, 0, 0, marginValue);

            extraView.addView(nextLinear,paramLinear);

            Toast.makeText(getApplicationContext(),Rid,Toast.LENGTH_LONG).show();
        }

        else{

            TextView insertText = new TextView(this);
            insertText.setText(addExtraText);
            insertText.setBackgroundResource(R.color.gray);
            insertText.setTextColor(getResources().getColor(R.color.textgray));
            insertText.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams paramText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float)0.25);
            LatestExtrainfo.addView(insertText,paramText);

        }
    }




}