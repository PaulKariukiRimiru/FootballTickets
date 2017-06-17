package com.example.mike.footballtickets.Activities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mike.footballtickets.Fragments.AddMoneyFragment;
import com.example.mike.footballtickets.R;

public class WalletActivity extends AppCompatActivity implements AddMoneyFragment.OnFragmentInteractionListener{
    TextView textView;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id = getIntent().getIntExtra("id", 0);
        int ammount = getIntent().getIntExtra("ammount", 0);
        final String uname = getIntent().getStringExtra("name");
        if (id == 0){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            id = preferences.getInt("id", 0);
        }
        if (ammount == 0){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            ammount = preferences.getInt("amount", 0);
        }

        Log.d("id", String.valueOf(id));

        textView= (TextView) findViewById(R.id.textView35);
        textView.setText("Ksh. "+ammount);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment fragment = AddMoneyFragment.newInstance(String.valueOf(id), uname);
                FragmentManager manager = getSupportFragmentManager();
                fragment.show(manager, "Add ammount");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void setNewValue(String newAmmount) {
        textView.setText("Ksh. "+newAmmount);
    }
}
