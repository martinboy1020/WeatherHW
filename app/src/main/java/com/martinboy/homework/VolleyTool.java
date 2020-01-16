package com.martinboy.homework;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VolleyTool {

    private static String TAG = VolleyTool.class.getSimpleName();
    private Context mCtx;
    private String url;
    private VolleyToolCallBack mCallBack;

    public void runNetApi(Context context, String url, VolleyToolMethod method, VolleyToolCallBack callBack) {
        mCtx = context;
        mCallBack = callBack;
        this.url = url;
        useStringRequest(method);
    }

    private void useStringRequest(VolleyToolMethod method) {

        int netMethod = -1;

        if (method.ordinal() == 0) {
            netMethod = Request.Method.POST;
        } else if (method.ordinal() == 1) {
            netMethod = Request.Method.GET;
        }

        if (netMethod == -1)
            return;

        RequestQueue mQueue = Volley.newRequestQueue(mCtx);

        StringRequest stringRequest =
                new StringRequest(netMethod, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i(TAG, response);
                                if (mCallBack != null) {
                                    mCallBack.callBackStringResponse(response);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, error.getMessage(), error);
                        if (mCallBack != null) {
                            mCallBack.callBackStringResponseError(error.getMessage());
                        }
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        Map<String, String> map = new HashMap<>();
                        map.put("Authorization", Constant.WEATHER_API_AUTH);

                        return map;
                    }
                };

        mQueue.add(stringRequest);

    }

    public enum VolleyToolMethod {
        METHOD_POST,
        METHOD_GET,
        METHOD_PUSH,
        METHOD_DELETE
    }

    public interface VolleyToolCallBack {
        void callBackStringResponse(String response);

        void callBackStringResponseError(String error);
    }

}
