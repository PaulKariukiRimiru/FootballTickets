package myk.project.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import myk.project.FootballTickets.R;

import org.json.JSONException;
import org.json.JSONObject;

public class TeamAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView ammount = (TextView) findViewById(R.id.textView31);
        TextView tname = (TextView) findViewById(R.id.tvname);
        TextView temail = (TextView) findViewById(R.id.tvemail);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int id = preferences.getInt("id",0);
        String name = preferences.getString("user","Team one");
        tname.setText(name);
        temail.setText(name+"@footballticket.com");
        String url = "http://104.236.7.202:3000/teams/earnings/"+id;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("Account Team", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    ammount.setText(String.valueOf(jsonObject.getInt("total")));
                } catch (JSONException e) {
                    e.printStackTrace();
                    ammount.setText("0");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ammount.setText("0");
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

}
