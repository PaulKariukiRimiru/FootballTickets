package myk.project.Delegates;

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


import myk.project.Interfaces.DataRemovalInterface;
import myk.project.Pojo.CartObject;
import myk.project.Pojo.IMainObject;

import myk.project.FootballTickets.R;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

/**
 * Created by Mike on 1/28/2017.
 */

public class CartDelegate extends AdapterDelegate<List<IMainObject>> {

    private final LayoutInflater inflater;
    /**
     * Called to determine whether this AdapterDelegate is the responsible for the given data
     * element.
     *
     * @param items    The data source of the Adapter
     * @param position The position in the datasource
     * @return true, if this item is responsible,  otherwise false
     */
    int number = 1;
    Context context;
    DataRemovalInterface removalInterface;
    public CartDelegate(Context context, DataRemovalInterface removalInterface){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.removalInterface = removalInterface;
    }

    @Override
    protected boolean isForViewType(@NonNull List<IMainObject> items, int position) {
        return items.get(position) instanceof CartObject;
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
        return new CartDelegateViewHolder(inflater.inflate(R.layout.cart_item, parent, false));
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
    protected void onBindViewHolder(@NonNull final List<IMainObject> items, final int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final CartObject cartObject = (CartObject) items.get(position);
        final CartDelegateViewHolder cartDelegateViewHolder = (CartDelegateViewHolder) holder;
        number = 1;
        cartDelegateViewHolder.homeName.setText(cartObject.getHomeTeam());
        cartDelegateViewHolder.awayName.setText(cartObject.getAwayTeam());
        cartDelegateViewHolder.matchTime.setText(cartObject.getTime());
        cartDelegateViewHolder.noOfTickets.setText(String.valueOf(cartObject.getNumberOfTickets()));
        cartDelegateViewHolder.tkprice.setText(String.valueOf(cartObject.getPrice()));
        cartDelegateViewHolder.totalAmount.setText(String.valueOf(cartObject.getNumberOfTickets() * cartObject.getPrice()));

        cartDelegateViewHolder.addTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartObject.setNumberOfTickets(cartObject.getNumberOfTickets()+1);
                cartDelegateViewHolder.noOfTickets.setText(String.valueOf(cartObject.getNumberOfTickets()));
                cartDelegateViewHolder.totalAmount.setText(String.valueOf(cartObject.getNumberOfTickets() * cartObject.getPrice()));
                removalInterface.addPrice(cartObject,cartDelegateViewHolder.getAdapterPosition());
            }
        });
        cartDelegateViewHolder.removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartObject.getNumberOfTickets() == 1) {
                    Log.d("REMOVE ITEM", String.valueOf(cartDelegateViewHolder.getAdapterPosition()));
                    removalInterface.removeObject(cartObject, cartDelegateViewHolder.getAdapterPosition());
                }else {

                    cartObject.setNumberOfTickets(cartObject.getNumberOfTickets()-1);
                    cartDelegateViewHolder.noOfTickets.setText(String.valueOf(cartObject.getNumberOfTickets()));
                    cartDelegateViewHolder.totalAmount.setText(String.valueOf(cartObject.getNumberOfTickets() * cartObject.getPrice()));
                }
            }
        });
    }

    public class CartDelegateViewHolder extends RecyclerView.ViewHolder{
    ImageView homeLogo, awayLogo;
        AppCompatTextView homeName, awayName, matchTime,noOfTickets, tkprice, totalAmount;
        AppCompatButton addTickets, removeFromCart;
        public CartDelegateViewHolder(View itemView) {
            super(itemView);
            homeLogo = (ImageView) itemView.findViewById(R.id.imghome);
            awayLogo = (ImageView) itemView.findViewById(R.id.imgaway);

            homeName = (AppCompatTextView) itemView.findViewById(R.id.tvHomename);
            awayName = (AppCompatTextView) itemView.findViewById(R.id.tvAwayname);
            matchTime = (AppCompatTextView) itemView.findViewById(R.id.tvMatchtime);
            noOfTickets = (AppCompatTextView) itemView.findViewById(R.id.tvNumberoftickets);
            tkprice = (AppCompatTextView) itemView.findViewById(R.id.tvP);
            totalAmount = (AppCompatTextView) itemView.findViewById(R.id.tvTotal);

            addTickets = (AppCompatButton) itemView.findViewById(R.id.btAddtickets);
            removeFromCart = (AppCompatButton) itemView.findViewById(R.id.btRemovefromcart);
        }
    }
}
