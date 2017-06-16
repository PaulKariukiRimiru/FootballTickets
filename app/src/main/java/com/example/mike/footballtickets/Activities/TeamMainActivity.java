package com.example.mike.footballtickets.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.mike.footballtickets.Adapter.MainAdapter;
import com.example.mike.footballtickets.Fragments.ReportFragment;
import com.example.mike.footballtickets.Interfaces.DataTransferInterface;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.example.mike.footballtickets.Pojo.MainMatchObject;
import com.example.mike.footballtickets.Pojo.TeamObject;
import com.example.mike.footballtickets.Pojo.TicketObject;
import com.example.mike.footballtickets.R;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class TeamMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DataTransferInterface, ZBarScannerView.ResultHandler, ReportFragment.OnFragmentInteractionListener {
    TextView opponent,tickets,sold,ammount;
    private ZBarScannerView mScannerView;
    private static final String TAG = "Result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                    } else
                        mScannerView.startCamera();
                }else {
                    mScannerView.startCamera();
                }
            }
        });


        List<IMainObject> objects = new ArrayList<>();
        for (int i=1; i <=20; i++){
            TeamObject matchObject = new TeamObject();
            matchObject.setAmmount("Ksh. 20000");
            matchObject.setOpponent("Thika Unt");
            matchObject.setSold("200 tickets");
            matchObject.setTickets("500 seats");

            objects.add(matchObject);
        }

        TeamObject teamObject = (TeamObject) objects.get(0);
        opponent.setText(teamObject.getOpponent());
        tickets.setText(teamObject.getTickets());
        sold.setText(teamObject.getSold());
        ammount.setText(teamObject.getAmmount());

        MainAdapter mainAdapter = new MainAdapter(this,objects,this,null, null);
        RecyclerView matchesView = (RecyclerView) findViewById(R.id.viewMainmatches);
        matchesView.setAdapter(mainAdapter);
        matchesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));



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

    @Override
    public void handleResult(Result result) {
        // Do something with the result here
        Log.v(TAG, result.getContents()); // Prints scan results
        Log.v(TAG, result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        DialogFragment fragment = ReportFragment.newInstance("","");
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.show(fragmentManager,"Report");
        // If you would like to resume scanning, call this method below:
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
