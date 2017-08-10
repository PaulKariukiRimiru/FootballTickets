package myk.project.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import myk.project.Fragments.LoginFragment;
import myk.project.Fragments.RegisterFragment;
import myk.project.Fragments.WelcomeFragment;

import myk.project.FootballTickets.R;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, WelcomeFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        Fragment register = WelcomeFragment.newInstance("","");
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.add(R.id.fragment,register);
        transaction1.addToBackStack(null);
        transaction1.commit();



    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}