package myk.project.Activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import myk.project.Fragments.PaymentFragment;
import myk.project.Fragments.ThankYouFragment;
import myk.project.Pojo.CartList;
import myk.project.Pojo.IMainObject;

import myk.project.FootballTickets.R;

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

        Fragment register = PaymentFragment.newInstance(String.valueOf(items),String.valueOf(price), cartList);
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

    }
}
