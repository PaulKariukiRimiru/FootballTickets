package com.example.mike.footballtickets.Delegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.mike.footballtickets.Activities.MainActivity;
import com.example.mike.footballtickets.Interfaces.DataTransferInterface;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.example.mike.footballtickets.Pojo.MainMatchObject;
import com.example.mike.footballtickets.R;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 1/28/2017.
 */

public class MatchesDelegate extends AdapterDelegate<List<IMainObject>> {
    private final LayoutInflater inflater;
    DataTransferInterface transferInterface;
    /**
     * Called to determine whether this AdapterDelegate is the responsible for the given data
     * element.
     *
     * @param items    The data source of the Adapter
     * @param position The position in the datasource
     * @return true, if this item is responsible,  otherwise false
     */
    private Context context;
    private MainActivity mainActivity = new MainActivity();
    private List<MainMatchObject> mainMatchObjects = new ArrayList<>();

    public MatchesDelegate(Context context, DataTransferInterface dataTransferInterface){
        this.context = context;
        this.transferInterface = dataTransferInterface;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected boolean isForViewType(@NonNull List<IMainObject> items, int position) {
        return items.get(position) instanceof MainMatchObject;
    }

    /**
     * Creates the  {@link RecyclerView.ViewHolder} for the given data source item
     *
     * @param parent The ViewGroup parent of the given datasource
     * @return The new instantiated {@link RecyclerView.ViewHolder}
     */
    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new MatchesDelegateViewHolder(inflater.inflate(R.layout.matches_item,parent,false));
    }

    /**
     * Called to bind the {@link RecyclerView.ViewHolder} to the item of the datas source set
     *
     * @param items    The data source
     * @param position The position in the datasource
     * @param holder   The {@link RecyclerView.ViewHolder} to bind
     * @param payloads A non-null list of merged payloads. Can be empty list if requires full update.
     */
    @Override
    protected void onBindViewHolder(@NonNull List<IMainObject> items, final int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final MainMatchObject matchObject = (MainMatchObject) items.get(position);
        MatchesDelegateViewHolder matchesDelegateViewHolder = (MatchesDelegateViewHolder) holder;

        matchesDelegateViewHolder.homeName.setText(matchObject.getHomeName());
        matchesDelegateViewHolder.awayName.setText(matchObject.getAwayName());
        matchesDelegateViewHolder.matchTime.setText(matchObject.getTime());

        matchesDelegateViewHolder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SELECTED ITEM AT: ", String.valueOf(position));
                if (mainMatchObjects.contains(matchObject)){

                    //transferInterface.setValues(matchObject);
                }else {
                    transferInterface.setValues(matchObject);
                    mainMatchObjects.add(matchObject);
                }
            }
        });

        matchesDelegateViewHolder.rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SELECTED ITEM AT: ", String.valueOf(position));
                if (mainMatchObjects.contains(matchObject)){
                    Log.d("item duplicate ", String.valueOf(position));
                    transferInterface.setValues(matchObject);
                }else {
                    Log.d("item not duplicate ", String.valueOf(position));
                    transferInterface.setValues(matchObject);
                    mainMatchObjects.add(matchObject);
                }
            }
        });

    }

    public class MatchesDelegateViewHolder extends RecyclerView.ViewHolder{
    ImageView homeLogo, awayLogo;
        AppCompatTextView homeName, awayName, matchTime;
        AppCompatButton addToCart;
        MaterialRippleLayout rippleView;
        public MatchesDelegateViewHolder(View itemView) {
            super(itemView);
            homeLogo = (ImageView) itemView.findViewById(R.id.imghome);
            awayLogo = (ImageView) itemView.findViewById(R.id.imgaway);

            homeName = (AppCompatTextView) itemView.findViewById(R.id.tvHomename);
            awayName = (AppCompatTextView) itemView.findViewById(R.id.tvAwayname);
            matchTime = (AppCompatTextView) itemView.findViewById(R.id.tvMatchtime);

            addToCart = (AppCompatButton) itemView.findViewById(R.id.btnAddtocart);
            rippleView = (MaterialRippleLayout) itemView.findViewById(R.id.ripMainItem);
        }
    }
}
