package com.example.mike.footballtickets.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.mike.footballtickets.Adapter.MainAdapter;
import com.example.mike.footballtickets.Database.DatabaseHelperClass;
import com.example.mike.footballtickets.Fragments.QrGeneratorFragment;
import com.example.mike.footballtickets.Fragments.TicketsPreviewFragment;
import com.example.mike.footballtickets.Interfaces.DataRemovalInterface;
import com.example.mike.footballtickets.Interfaces.NavigationInterface;
import com.example.mike.footballtickets.Pojo.AccountObject;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.example.mike.footballtickets.R;

import java.util.ArrayList;
import java.util.List;

public class Account extends AppCompatActivity implements DataRemovalInterface, QrGeneratorFragment.OnFragmentInteractionListener, NavigationInterface {
    List<IMainObject> listObjects;
    private MainAdapter adapter;
    DatabaseHelperClass databaseHelperClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_account);


        RecyclerView accountTickets = (RecyclerView) findViewById(R.id.viewAccount);
        listObjects = new ArrayList<>();
        databaseHelperClass = new DatabaseHelperClass(this);
        for (AccountObject accountObject : databaseHelperClass.getAllTickets()) {
            Log.d("RETRIEVEING DATA",accountObject.getMatchId());
            IMainObject iMainObject = accountObject;
            listObjects.add(iMainObject);
        }
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void navigateToFragment(DialogFragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        fragment.show(manager,"Cart");
    }
}
