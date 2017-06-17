package com.example.mike.footballtickets.Delegates;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.mike.footballtickets.Fragments.QrGeneratorFragment;
import com.example.mike.footballtickets.Fragments.TicketsPreviewFragment;
import com.example.mike.footballtickets.Interfaces.DataRemovalInterface;
import com.example.mike.footballtickets.Interfaces.NavigationInterface;
import com.example.mike.footballtickets.Pojo.AccountObject;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.example.mike.footballtickets.R;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mike on 3/21/2017.
 */

public class AccountDelegate extends AdapterDelegate<List<IMainObject>> {

    private final Context context;
    private final LayoutInflater inflater;
    private final DataRemovalInterface removalInterface;
    NavigationInterface navigationInterface;

    public AccountDelegate(Context context, DataRemovalInterface removalInterface, NavigationInterface navigationInterface){
        this.context = context;
        this.removalInterface = removalInterface;
        inflater = LayoutInflater.from(context);
        this.navigationInterface = navigationInterface;
    }

    @Override
    protected boolean isForViewType(@NonNull List<IMainObject> items, int position) {
        return items.get(position) instanceof AccountObject;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AccountDelegateViewHolder(inflater.inflate(R.layout.account_item,parent,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<IMainObject> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final AccountObject accountObject  = (AccountObject) items.get(position);
        final AccountDelegateViewHolder holder1 = (AccountDelegateViewHolder) holder;

        holder1.homeName.setText(accountObject.getHomeName());
        holder1.awayName.setText(accountObject.getAwayName());
        holder1.matchTime.setText(accountObject.getTime());

        holder1.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removalInterface.removeObject(accountObject, holder1.getAdapterPosition());
            }
        });

        holder1.rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = removalInterface.getUserId();
                String code = accountObject.getTicketCode();

                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("code", code);

                JSONObject jsonObject = new JSONObject(params);
                DialogFragment fragment = QrGeneratorFragment.newInstance(jsonObject.toString(),accountObject.getHomeName()+" Vs "+accountObject.getAwayName());
                navigationInterface.navigateToFragment(fragment);
            }
        });
    }

    private class AccountDelegateViewHolder extends RecyclerView.ViewHolder {
        ImageView homeLogo, awayLogo;
        AppCompatTextView homeName, awayName, matchTime;
        AppCompatButton delete;
        MaterialRippleLayout rippleView;
        public AccountDelegateViewHolder(View inflate) {
            super(inflate);
            homeLogo = (ImageView) itemView.findViewById(R.id.imghome);
            awayLogo = (ImageView) itemView.findViewById(R.id.imgaway);

            homeName = (AppCompatTextView) itemView.findViewById(R.id.tvHomename);
            awayName = (AppCompatTextView) itemView.findViewById(R.id.tvAwayname);
            matchTime = (AppCompatTextView) itemView.findViewById(R.id.tvMatchtime);

            delete = (AppCompatButton) itemView.findViewById(R.id.btnDelete);
            rippleView = (MaterialRippleLayout) itemView.findViewById(R.id.ripAccountItem);
        }
    }
}
