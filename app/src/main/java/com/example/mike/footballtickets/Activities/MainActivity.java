package com.example.mike.footballtickets.Activities;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.mike.footballtickets.Adapter.MainAdapter;
import com.example.mike.footballtickets.Fragments.TicketsPreviewFragment;
import com.example.mike.footballtickets.Interfaces.DataTransferInterface;
import com.example.mike.footballtickets.Pojo.CartList;
import com.example.mike.footballtickets.Pojo.CartObject;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.example.mike.footballtickets.Pojo.MainMatchObject;
import com.example.mike.footballtickets.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DataTransferInterface, TicketsPreviewFragment.OnFragmentInteractionListener {
    List<CartObject> cartObjectList = new ArrayList<CartObject>();
    List<MainMatchObject> mainMatchObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<IMainObject> objects = new ArrayList<>();
        for (int i=1; i <=20; i++){
            MainMatchObject matchObject = new MainMatchObject();
            matchObject.setHomeName("Gor Mahia");
            matchObject.setAwayName("Thika Untd");
            matchObject.setTime("Sat,21/1/2017");
            matchObject.setLocation("Nyayo stadium");
            matchObject.setTicketPrice(100);
            matchObject.setHomeLogo("not uploaded");
            matchObject.setAwayLogo("not uploaded");
            matchObject.setMatchId("001");

            objects.add(matchObject);
        }
        MainAdapter mainAdapter = new MainAdapter(this,objects,this,null, null);
        RecyclerView matchesView = (RecyclerView) findViewById(R.id.viewMainmatches);
        matchesView.setAdapter(mainAdapter);
        matchesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMatchObjectsToCartObjects();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void getMatchObjectsToCartObjects(){
        List<IMainObject> cartObjectList = new ArrayList<>();
        CartList cartList = new CartList();
        if (mainMatchObjects != null){
            for (MainMatchObject object:mainMatchObjects){
                CartObject cartObject = new CartObject();
                cartObject.setHomeTeam(object.getHomeName());
                cartObject.setAwayTeam(object.getAwayName());
                cartObject.setLocation(object.getLocation());
                cartObject.setMatchId(object.getMatchId());
                cartObject.setTime(object.getTime());
                cartObject.setPrice(object.getTicketPrice());
                cartObject.setHomeLogo(object.getHomeLogo());
                cartObject.setAwaylogo(object.getAwayLogo());
                cartObject.setNumberOfTickets(1);
                cartObjectList.add(cartObject);
            }
        }
        cartList.setCartobjects(cartObjectList);
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        intent.putExtra("cartList",cartList);
        startActivity(intent);
    }

    public void addMatchObjects(IMainObject object){
        MainMatchObject object1 = (MainMatchObject) object;
        if (!mainMatchObjects.contains(object1)){
            mainMatchObjects.add(object1);
            addCount();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cartItems) {
            FragmentManager manager = getSupportFragmentManager();
            TicketsPreviewFragment cartFragment = TicketsPreviewFragment.newInstance();
            cartFragment.show(manager,"Cart");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.nav_account) {
          intent = new Intent(this, Account.class);
          startActivity(intent);
        } else if (id == R.id.nav_login) {
            intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
//        } else if (id == R.id.nav_settings) {
//            intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
//        }
       else if (id == R.id.nav_about) {
           intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
       else if (id == R.id.nav_scan) {
          intent = new Intent(this, QrScannerAcitvity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setValues(IMainObject object) {

        addMatchObjects(object);
    }

    private int count = 0;


    public void addCount() {
        this.count += 1;
        MenuItem itemCart = menu.findItem(R.id.action_cartItems);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        int badgeCount = this.count;
        if (badgeCount > 0) {
            ActionItemBadge.update(this, itemCart, icon , ActionItemBadge.BadgeStyles.GREY, badgeCount);
        } else {
            badgeCount = 0;
            ActionItemBadge.update(this, itemCart, icon , ActionItemBadge.BadgeStyles.GREY, badgeCount);
        }
    }

    private void removeCount(){
        this.count -= 1;
        MenuItem itemCart = menu.findItem(R.id.action_cartItems);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        int badgeCount = this.count;
        if (badgeCount > 0) {
            ActionItemBadge.update(this, itemCart, icon , ActionItemBadge.BadgeStyles.GREY, badgeCount);
        } else if (badgeCount <= 0){
            badgeCount = 0;
            ActionItemBadge.update(this, itemCart, icon , ActionItemBadge.BadgeStyles.GREY, badgeCount);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public List<IMainObject> getCartObjects() {

        List<IMainObject> cartObjectList = new ArrayList<>();
        if (mainMatchObjects != null){
            for (MainMatchObject object:mainMatchObjects){
                CartObject cartObject = new CartObject();
                cartObject.setHomeTeam(object.getHomeName());
                cartObject.setAwayTeam(object.getAwayName());
                cartObject.setLocation(object.getLocation());
                cartObject.setMatchId(object.getMatchId());
                cartObject.setTime(object.getTime());
                cartObject.setPrice(object.getTicketPrice());
                cartObject.setHomeLogo(object.getHomeLogo());
                cartObject.setAwaylogo(object.getAwayLogo());
                cartObject.setNumberOfTickets(1);
                cartObjectList.add(cartObject);
            }
        }
        return cartObjectList;
    }

    @Override
    public void removeObject(int position) {

        mainMatchObjects.remove(position);
        removeCount();
    }
}
