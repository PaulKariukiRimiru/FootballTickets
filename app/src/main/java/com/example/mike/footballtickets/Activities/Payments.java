package com.example.mike.footballtickets.Activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mike.footballtickets.Database.DatabaseHelperClass;
import com.example.mike.footballtickets.Fragments.PaymentFragment;
import com.example.mike.footballtickets.Fragments.ThankYouFragment;
import com.example.mike.footballtickets.Fragments.WelcomeFragment;
import com.example.mike.footballtickets.Pojo.CartList;
import com.example.mike.footballtickets.Pojo.CartObject;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.example.mike.footballtickets.Pojo.MainMatchObject;
import com.example.mike.footballtickets.R;

import java.util.ArrayList;
import java.util.List;

public class Payments extends AppCompatActivity implements PaymentFragment.OnFragmentInteractionListener, ThankYouFragment.OnFragmentInteractionListener {

    List<IMainObject> mainObjectList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        int price = getIntent().getIntExtra("price",0);
        int items = getIntent().getIntExtra("items", 0);
        CartList cartList = (CartList) getIntent().getSerializableExtra("list");
        mainObjectList = cartList.getCartobjects();

        Fragment register = PaymentFragment.newInstance(String.valueOf(items),String.valueOf(price));
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.add(R.id.fragment,register);
        transaction1.addToBackStack(null);
        transaction1.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void pay() {
        DatabaseHelperClass helperClass = new DatabaseHelperClass(this);
        for (IMainObject iMainObject : mainObjectList){
            CartObject cartObject = (CartObject) iMainObject;
            MainMatchObject matchObject = new MainMatchObject();
            if (cartObject != null){
                matchObject.setHomeName(cartObject.getHomeTeam());
                matchObject.setAwayName(cartObject.getAwayTeam());
                matchObject.setLocation(cartObject.getLocation());
                matchObject.setMatchId(cartObject.getMatchId());
                matchObject.setTime(cartObject.getTime());
                matchObject.setTicketPrice(cartObject.getPrice());
                matchObject.setHomeLogo(cartObject.getHomeLogo());
                matchObject.setAwayLogo(cartObject.getAwaylogo());
            }
            helperClass.insertTicket(matchObject);
        }

    }
}
