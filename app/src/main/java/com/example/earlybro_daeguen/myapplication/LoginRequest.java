package com.example.earlybro_daeguen.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;


public class LoginRequest extends StringRequest {
    final static private String URL = "URL";



    private Map<String, String> parameters;

    public LoginRequest(String shopID, String shopPassword, Response.Listener<String> listener){

        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("shopID", shopID);
        parameters.put("shopPassword", shopPassword);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}


