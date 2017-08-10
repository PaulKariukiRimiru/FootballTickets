package myk.project.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.balysv.materialripple.MaterialRippleLayout;
import myk.project.Adapter.MainAdapter;
import myk.project.Database.DatabaseHelperClass;
import myk.project.Fragments.ReportFragment;
import myk.project.Interfaces.DataTransferInterface;
import myk.project.Pojo.IMainObject;
import myk.project.Pojo.TeamObject;
import myk.project.Pojo.TicketBoughtItem;

import myk.project.FootballTickets.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class TeamMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DataTransferInterface, ZBarScannerView.ResultHandler, ReportFragment.OnFragmentInteractionListener {
    TextView opponent,tickets,sold,ammount;
    private ZBarScannerView mScannerView;
    private static final String TAG = "Result";

    int id, amount;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id = getIntent().getIntExtra("id", 0);
        amount = getIntent().getIntExtra("ammount", 0);

        name = getIntent().getStringExtra("name");

         opponent = (TextView) findViewById(R.id.tvOpponent);
         tickets = (TextView) findViewById(R.id.tvtickets);
         sold  = (TextView) findViewById(R.id.tvsold);
         ammount = (TextView) findViewById(R.id.tvammount);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MaterialRippleLayout rippleView = (MaterialRippleLayout) findViewById(R.id.ripMainItem);

        rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScannerView = new ZBarScannerView(getApplicationContext());    // Programmatically initialize the scanner view
                setContentView(mScannerView);
                mScannerView.setResultHandler(TeamMainActivity.this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                        } else
                            mScannerView.startCamera();
                    }
                }
                mScannerView.startCamera();

            }
        });


        String url = "http://104.236.7.202:3000/matches/show/"+id;
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject1) {
                try {
                    JSONArray jsonArray = jsonObject1.getJSONArray("matches");
                    for (int i = 0; i < jsonArray.length(); i++){
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.d("Matches response", jsonObject.toString());
                            TeamObject teamObject = new TeamObject();
                            teamObject.setOpponent(jsonObject.getString(""));
                            teamObject.setTickets(jsonObject.getString(""));
                            teamObject.setSold(jsonObject.getString(""));
                            int price=300;
                            teamObject.setAmmount(String.valueOf(price * Integer.parseInt(teamObject.getSold())));
                            objects.add(teamObject);
                            refreshLayout(objects);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Matches response", e.getLocalizedMessage() );
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(TeamMainActivity.this,volleyError.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                Log.d("Volley Error", volleyError.getLocalizedMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        if (!objects.isEmpty()){

        }

        mainAdapter = new MainAdapter(this,objects,this,null, null);
        RecyclerView matchesView = (RecyclerView) findViewById(R.id.viewMainmatches);
        matchesView.setAdapter(mainAdapter);
        matchesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    }
    List<IMainObject> objects = new ArrayList<>();
    MainAdapter mainAdapter;

    public void refreshLayout(List<IMainObject> mainObjects){
        objects = mainObjects;
        mainAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
        startActivity(new Intent(TeamMainActivity.this,TeamMainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.team_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            // Handle the camera action
            startActivity(new Intent(TeamMainActivity.this, TeamAccount.class));
        }else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setValues(IMainObject object) {
        TeamObject teamObject = (TeamObject) object;
        opponent.setText(teamObject.getOpponent());
        tickets.setText(teamObject.getTickets());
        sold.setText(teamObject.getSold());
        ammount.setText(teamObject.getAmmount());
    }

    String idr, code;
    @Override
    public void handleResult(Result result) {
        // Do something with the result here
        Log.v(TAG, result.getContents()); // Prints scan results
        Log.v(TAG, result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        String url = "http://104.236.7.202:3000/tickets/verify";

        try {
            JSONObject jsonObject = new JSONObject(result.getContents());
            idr = jsonObject.getString("id");
            code = jsonObject.getString("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<TicketBoughtItem> ticketBoughtItems = new ArrayList<>();




        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("error")){
                        DatabaseHelperClass helperClass = new DatabaseHelperClass(TeamMainActivity.this);
                        TicketBoughtItem boughtItem = new TicketBoughtItem();
                        Log.d("TEamActivity", code);
                        boughtItem.setTicketcode(code);
                        boughtItem.setUserId(idr);
                        if (helperClass.verifyTicket(boughtItem)){
                            if (helperClass.insertTicket(boughtItem)) {
                                Log.d("Scannerinserted",boughtItem.getUserId());
                                DialogFragment dialogFragment = ReportFragment.newInstance(0, "");
                                FragmentManager manager = getSupportFragmentManager();
                                dialogFragment.show(manager, "1");
                            }

                        }else {
                            DialogFragment dialogFragment = ReportFragment.newInstance(1,"");
                            FragmentManager manager = getSupportFragmentManager();
                            dialogFragment.show(manager,"1");
                        }
                    }else {
                        DialogFragment dialogFragment = ReportFragment.newInstance(1,"");
                        FragmentManager manager = getSupportFragmentManager();
                        dialogFragment.show(manager,"1");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mScannerView.resumeCameraPreview(TeamMainActivity.this);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", idr);
                params.put("code", code);
                return params;
            }
        };

      RequestQueue requestQueue = Volley.newRequestQueue(this);
       requestQueue.add(request);


        mScannerView.stopCamera();
    }

    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_CAMERA_REQUEST_CODE) {
            return;
        }
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Permission granted",Toast.LENGTH_SHORT).show();
            mScannerView.startCamera();
            return;
        }
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage("Permission denied")
                .setPositiveButton(android.R.string.ok, listener)
                .show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void startCamera() {
        mScannerView.setResultHandler(TeamMainActivity.this);
        mScannerView.startCamera();
    }
}
