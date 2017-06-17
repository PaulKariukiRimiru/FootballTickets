package com.example.mike.footballtickets.Activities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mike.footballtickets.Adapter.MainAdapter;
import com.example.mike.footballtickets.Database.DatabaseHelperClass;
import com.example.mike.footballtickets.Fragments.QrGeneratorFragment;
import com.example.mike.footballtickets.Fragments.TicketsPreviewFragment;
import com.example.mike.footballtickets.Interfaces.DataRemovalInterface;
import com.example.mike.footballtickets.Interfaces.NavigationInterface;
import com.example.mike.footballtickets.Pojo.AccountObject;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.example.mike.footballtickets.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account extends AppCompatActivity implements DataRemovalInterface, QrGeneratorFragment.OnFragmentInteractionListener, NavigationInterface {
    List<IMainObject> listObjects;
    private MainAdapter adapter;
    DatabaseHelperClass databaseHelperClass;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_account);
        id = getIntent().getIntExtra("id", 0);
        int ammount = getIntent().getIntExtra("ammount", 0);
        final String uname = getIntent().getStringExtra("name");
        if (id == 0){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            id = preferences.getInt("id", 0);
        }

        RecyclerView accountTickets = (RecyclerView) findViewById(R.id.viewAccount);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });


        listObjects = new ArrayList<>();
        String url = "http://192.168.88.141:3000/tickets/show/"+id;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("tickets");
                    Log.d("Account" , jsonArray.toString());
                    Log.d("Account response", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        AccountObject accountObject = new AccountObject();
                        accountObject.setHomeName(jsonObject1.getString("home_team"));
                        accountObject.setAwayName(jsonObject1.getString("away_team"));
                        accountObject.setLocation(jsonObject1.getString("location"));
                        accountObject.setTime(jsonObject1.getString("date"));
                        accountObject.setTicketCode(jsonObject1.getString("code"));
                        listObjects.add(accountObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Account.this, e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

        adapter = new MainAdapter(this,listObjects,null,this,this);
        accountTickets.setAdapter(adapter);
        accountTickets.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void removeObject(IMainObject object, int position) {
        listObjects.remove(object);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,listObjects.size());

        AccountObject object1 = (AccountObject) object;
        databaseHelperClass.deleteContact(object1.getMatchId());
    }

    @Override
    public void addPrice(IMainObject object, int position) {

    }

    @Override
    public String getUserId() {
        return String.valueOf(id);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void navigateToFragment(DialogFragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        fragment.show(manager,"Cart");
    }

    @Override
    public void navigateTo() {

    }
}
