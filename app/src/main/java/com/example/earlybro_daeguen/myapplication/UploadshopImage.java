package com.example.earlybro_daeguen.myapplication;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Earlybro_DaeGuen on 2017-12-28.
 */

public class UploadshopImage extends StringRequest {
    final static private String URL = "URL";

    private Map<String, String> parameters;

    public UploadshopImage(String Dir, String filename, String Image, Response.Listener<String> listener){

        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("dir", Dir);
        parameters.put("filename", filename);
        parameters.put("image", Image);

    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
