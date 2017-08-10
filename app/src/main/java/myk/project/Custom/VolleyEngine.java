package myk.project.Custom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import myk.project.Activities.HomeActivity;
import myk.project.Activities.TeamMainActivity;
import myk.project.Interfaces.NavigationInterface;
import myk.project.Pojo.IMainObject;

import com.qintong.library.InsLoadingView;

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

    public void postContentLogin(String url, final HashMap<String, String> params, final InsLoadingView loadingView, final Fragment fragment){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Login response", jsonObject.toString());
                    if (jsonObject.getBoolean("error")){
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        loadingView.setVisibility(View.INVISIBLE);
                    }else {
                        Toast.makeText(context,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        loadingView.setVisibility(View.INVISIBLE);
                        if (jsonObject.isNull("team_id")){
                            Log.d("Login response", jsonObject.toString());
                            String name = jsonObject.getString("name");
                            int id = jsonObject.getInt("id");

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = preferences.edit();
                            if (preferences.contains("id")){
                                editor.remove("id");
                                editor.putInt("id", jsonObject.getInt("id"));
                                if (preferences.contains("amount")){
                                    editor.remove("amount");
                                    editor.putInt("amount", jsonObject.getInt("amount"));
                                    if (preferences.contains("user")){
                                        editor.remove("user");
                                        editor.putString("user", jsonObject.getString("name"));
                                    }
                                }
                                editor.apply();
                            }else {
                                editor.putInt("id", jsonObject.getInt("id"));
                                editor.putInt("amount", jsonObject.getInt("amount"));
                                editor.apply();
                            }

                            loadingView.setVisibility(View.GONE);

                            fragment.getActivity().finish();

                            Intent intent = new Intent(context, HomeActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("id", id);

                            context.startActivity(intent);
                        }else {
                            String name = jsonObject.getString("name");
                            int id = jsonObject.getInt("team_id");
                            int ammount = jsonObject.getInt("amount");

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = preferences.edit();
                            if (preferences.contains("id")){
                                editor.remove("id");
                                editor.putInt("id", jsonObject.getInt("team_id"));
                                if (preferences.contains("amount")){
                                    editor.remove("amount");
                                    editor.putInt("amount", jsonObject.getInt("amount"));
                                    if (preferences.contains("user")){
                                        editor.remove("user");
                                        editor.putString("user", jsonObject.getString("name"));
                                    }
                                }
                                editor.apply();
                            }else {
                                editor.putInt("id", jsonObject.getInt("team_id"));
                                editor.putInt("amount", jsonObject.getInt("amount"));
                                editor.apply();
                            }
                            fragment.getActivity().finish();
                            loadingView.setVisibility(View.GONE);
                            Intent intent = new Intent(context, TeamMainActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("id", id);
                            intent.putExtra("ammount", ammount);
                            context.startActivity(intent);
                        }
                    }
                } catch (JSONException e) {

                    Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    loadingView.setVisibility(View.GONE);
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                loadingView.setVisibility(View.GONE);
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

    public void postContentRegister(String url, final HashMap<String, String> params, final InsLoadingView loadingView, final NavigationInterface navigationInterface){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("error")){
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        loadingView.setVisibility(View.INVISIBLE);
                    }else {
                        Toast.makeText(context,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        loadingView.setVisibility(View.INVISIBLE);
                        navigationInterface.navigateTo();
                    }
                } catch (JSONException e) {

                    Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    loadingView.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
