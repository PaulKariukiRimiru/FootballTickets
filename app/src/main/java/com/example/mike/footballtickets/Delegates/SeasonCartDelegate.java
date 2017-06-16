package com.example.mike.footballtickets.Delegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mike.footballtickets.Fragments.SeasonalFragment;
import com.example.mike.footballtickets.Interfaces.DataRemovalInterface;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.example.mike.footballtickets.Pojo.SeasonCartObject;
import com.example.mike.footballtickets.R;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

/**
 * Created by mike on 6/16/17.
 */

public class SeasonCartDelegate extends AdapterDelegate<List<IMainObject>>{

    private final DataRemovalInterface removalInterface;
    private final Context context;
    private final LayoutInflater inflater;

    public SeasonCartDelegate(Context context, DataRemovalInterface removalInterface){
        this.context = context;
        this.removalInterface = removalInterface;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected boolean isForViewType(@NonNull List<IMainObject> iMainObjects, int i) {
        return iMainObjects.get(i) instanceof SeasonCartObject;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new SeasonCartDelegateViewHolder(inflater.inflate(R.layout.season_cartitem, viewGroup, false));
    }
    int num = 1;

    @Override
    protected void onBindViewHolder(@NonNull List<IMainObject> iMainObjects, int i, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull List<Object> list) {
        final SeasonCartObject cartObject = (SeasonCartObject) iMainObjects.get(i);
        final SeasonCartDelegateViewHolder viewHolder1 = (SeasonCartDelegateViewHolder) viewHolder;

        viewHolder1.homeName.setText(cartObject.getClubname());
        viewHolder1.noOfTickets.setText(cartObject.getNoOfTickets()+" tickets");
        viewHolder1.tkprice.setText("Ksh. "+cartObject.getPrice());
        viewHolder1.totalAmount.setText("Ksh. "+cartObject.getPrice()*cartObject.getNoOfTickets());

        viewHolder1.addTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartObject.setNoOfTickets(cartObject.getNoOfTickets()+1);
                viewHolder1.noOfTickets.setText(String.valueOf(cartObject.getNoOfTickets()));
                viewHolder1.totalAmount.setText(String.valueOf(cartObject.getNoOfTickets() * cartObject.getPrice()));
                removalInterface.addPrice(cartObject,viewHolder1.getAdapterPosition());
            }
        });

        viewHolder1.removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartObject.getNoOfTickets() == 1) {
                    Log.d("REMOVE ITEM", String.valueOf(viewHolder1.getAdapterPosition()));
                    removalInterface.removeObject(cartObject, viewHolder1.getAdapterPosition());
                }else {

                    cartObject.setNoOfTickets(cartObject.getNoOfTickets()-1);
                    viewHolder1.noOfTickets.setText(String.valueOf(cartObject.getNoOfTickets()));
                    viewHolder1.totalAmount.setText(String.valueOf(cartObject.getNoOfTickets() * cartObject.getPrice()));
                }
            }
        });
    }

    private class SeasonCartDelegateViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView homeName,noOfTickets, tkprice, totalAmount;
        AppCompatButton addTickets, removeFromCart;
        public SeasonCartDelegateViewHolder(View itemView) {
            super(itemView);

            homeName = (AppCompatTextView) itemView.findViewById(R.id.tvHomename);
            noOfTickets = (AppCompatTextView) itemView.findViewById(R.id.tvNumberoftickets);
            tkprice = (AppCompatTextView) itemView.findViewById(R.id.tvP);
            totalAmount = (AppCompatTextView) itemView.findViewById(R.id.tvTotal);

            addTickets = (AppCompatButton) itemView.findViewById(R.id.btAddtickets);
            removeFromCart = (AppCompatButton) itemView.findViewById(R.id.btRemovefromcart);
        }
    }

}
