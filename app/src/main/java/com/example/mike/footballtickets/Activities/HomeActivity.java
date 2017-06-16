package com.example.mike.footballtickets.Activities;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.mike.footballtickets.Fragments.MatchesFragment;
import com.example.mike.footballtickets.Fragments.SeasonalFragment;
import com.example.mike.footballtickets.Fragments.TicketsPreviewFragment;
import com.example.mike.footballtickets.Pojo.CartList;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.example.mike.footballtickets.Pojo.MainMatchObject;
import com.example.mike.footballtickets.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements MatchesFragment.OnFragmentInteractionListener, SeasonalFragment.OnFragmentInteractionListener, TicketsPreviewFragment.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        Fragment matches = MatchesFragment.newInstance("","");
        Fragment season = SeasonalFragment.newInstance("","");

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(matches);
        fragmentList.add(season);

        List<String> titles = new ArrayList<>();
        titles.add("Upcoming Matches");
        titles.add("Seasonal Tickets");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragmentList, titles);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMatchObjectsToCartObjects();
            }
        });
    }


    public void getMatchObjectsToCartObjects(){

        CartList cartList = new CartList();
        cartList.setCartobjects(mainObjectList);

        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra("cartList",cartList);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public List<IMainObject> getCartObjects() {
        return mainObjectList;
    }

    @Override
    public void removeObject(int position) {
        removeCount();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList;
        private final List<String> titles;

        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titles) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragmentList.size();
        }
    }

    private int count = 0;
    Menu menu;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void addCount() {
        addCounter();
    }

    List<IMainObject> mainObjectList = new ArrayList<>();
    @Override
    public void addMatchesCartObject(IMainObject mainObject) {
        mainObjectList.add(mainObject);
    }


    public void addCounter() {
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

}
