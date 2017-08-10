package myk.project.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import myk.project.Adapter.MainAdapter;
import myk.project.Database.DatabaseHelperClass;
import myk.project.Interfaces.DataRemovalInterface;
import myk.project.Pojo.CartList;
import myk.project.Pojo.CartObject;
import myk.project.Pojo.IMainObject;
import myk.project.Pojo.MainMatchObject;
import myk.project.Pojo.SeasonCartObject;

import myk.project.FootballTickets.R;

import java.util.List;

public class CartActivity extends AppCompatActivity implements DataRemovalInterface {

    private List<IMainObject> mainMatchesItem;
    MainAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.viewCart);

        CartList cartList = (CartList) getIntent().getExtras().getSerializable("cartList");
        mainMatchesItem = null;
        if (cartList != null) {
            mainMatchesItem = cartList.getCartobjects();
        }
        adapter = new MainAdapter(this,mainMatchesItem,null,this, null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));


        final DatabaseHelperClass helperClass = new DatabaseHelperClass(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int price = 0;
                int items = 0;
                for (IMainObject iMainObject : mainMatchesItem){
                    if (iMainObject instanceof CartObject){
                        CartObject cartObject = (CartObject) iMainObject;
                        MainMatchObject matchObject = new MainMatchObject();
                        if (cartObject != null){
                            price += cartObject.getNumberOfTickets()*cartObject.getPrice();
                            items += cartObject.getNumberOfTickets();

                            matchObject.setMatchId(cartObject.getMatchId());
                            matchObject.setTicketPrice(cartObject.getPrice());
                        }
                    }else if (iMainObject instanceof SeasonCartObject){
                        SeasonCartObject cartObject = (SeasonCartObject) iMainObject;
                        MainMatchObject matchObject = new MainMatchObject();
                        if (cartObject != null){
                            price += cartObject.getNoOfTickets()*cartObject.getPrice();
                            items += cartObject.getNoOfTickets();

                            matchObject.setMatchId(cartObject.getClubId());
                            matchObject.setTicketPrice(cartObject.getPrice());
                        }
                    }

                }

                CartList cartList1 = new CartList();
                cartList1.setCartobjects(mainMatchesItem);
                Intent intent = new Intent(CartActivity.this, Payments.class);
                intent.putExtra("price",price);
                intent.putExtra("items",items);
                intent.putExtra("list",cartList1);
                startActivity(intent);

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void removeObject(IMainObject object, int position) {
        mainMatchesItem.remove(object);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,mainMatchesItem.size());
    }

    @Override
    public void addPrice(IMainObject object, int position) {
        mainMatchesItem.remove(position);
        mainMatchesItem.add(position,object);
        adapter.notifyDataSetChanged();
    }

    @Override
    public String getUserId() {
        return null;
    }
}
