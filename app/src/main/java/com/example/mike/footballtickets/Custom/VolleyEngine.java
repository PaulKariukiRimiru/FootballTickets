package com.example.mike.footballtickets.Custom;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.qintong.library.InsLoadingView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mike on 6/14/17.
 */

public class VolleyEngine {
    private final Context context;

    public VolleyEngine(Context context){
        this.context = context;
    }

    public List<IMainObject> getJsonContent(String url, int requestType,final InsLoadingView loadingView){
        switch (requestType){
            case 0:
                return getMatches(url,loadingView);
            case 1:
                return getTickets(url,loadingView);
            default:
                return null;
        }

    }

    private List<IMainObject> getTickets(String url, final InsLoadingView loadingView) {
        List<IMainObject> mainObjects = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingView.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingView.setVisibility(View.INVISIBLE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return mainObjects;
    }

    private List<IMainObject> getMatches(String url, final InsLoadingView loadingView) {
        List<IMainObject> mainObjects = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingView.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingView.setVisibility(View.INVISIBLE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return mainObjects;
    }

    public void postContent(String url, final HashMap<String, String> params, final InsLoadingView loadingView){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                loadingView.setVisibility(View.INVISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingView.setVisibility(View.INVISIBLE);
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                return params;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(context);
        requestQueue1.add(request);
    }

}
