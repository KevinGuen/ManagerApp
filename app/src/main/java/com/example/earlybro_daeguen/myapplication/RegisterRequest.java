package com.example.earlybro_daeguen.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class RegisterRequest extends StringRequest {
    final static private String URL = "URL";

    private Map<String, String> parameters;

    public RegisterRequest(String shopID, String shopPassword, String shopEmail, String shopName, String shopAddress, String shopTel, String managerTel, String businessNum, String businessImg, String shopImg, String shopLatitude, String shopHardness, String shopInfo , Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("shopID", shopID);
        parameters.put("shopPassword", shopPassword);
        parameters.put("shopEmail", shopEmail);
        parameters.put("shopName", shopName);
        parameters.put("shopAddress", shopAddress);
        parameters.put("shopTel", shopTel);
        parameters.put("managerTel", managerTel);
        parameters.put("businessNum", businessNum);
        parameters.put("businessImg", businessImg);
        parameters.put("shopImg", shopImg);
        parameters.put("shopLatitude", shopLatitude);
        parameters.put("shopHardness", shopHardness);
        parameters.put("shopInfo", shopInfo);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
