package com.example.earlybro_daeguen.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Earlybro_DaeGuen on 2017-11-06.
 */
public class IDrequest extends StringRequest {
    final static private String URL = "URL";



    private Map<String, String> parameters;

    public IDrequest(String shopID, Response.Listener<String> listener){

        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("shopID", shopID);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}