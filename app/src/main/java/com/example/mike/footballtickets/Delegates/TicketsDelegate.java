package com.example.mike.footballtickets.Delegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mike.footballtickets.Interfaces.DataRemovalInterface;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.example.mike.footballtickets.Pojo.TicketObject;
import com.example.mike.footballtickets.R;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

/**
 * Created by Mike on 2/15/2017.
 */

public class TicketsDelegate extends AdapterDelegate<List<IMainObject>> {

    private final LayoutInflater inflater;
    private final DataRemovalInterface removalInterface;

    public TicketsDelegate(Context context, DataRemovalInterface removalInterface){
        inflater = LayoutInflater.from(context);
        this.removalInterface = removalInterface;
    }

    @Override
    protected boolean isForViewType(@NonNull List<IMainObject> items, int position) {
        return items.get(position) instanceof TicketObject;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new TicketDelegateViewHolder(inflater.inflate(R.layout.ticket_item,parent,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<IMainObject> items, final int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        final TicketObject ticketObject = (TicketObject) items.get(position);
        TicketDelegateViewHolder delegateViewHolder = (TicketDelegateViewHolder) holder;

        delegateViewHolder.homeTeam.setText(ticketObject.getHomeName());
        delegateViewHolder.awayTeam.setText(ticketObject.getAwayName());
        delegateViewHolder.dateAndTime.setText(ticketObject.getMatchDate());
        delegateViewHolder.noOfTickets.setText(ticketObject.getNoOfSeats());

        delegateViewHolder.deleteTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removalInterface.removeObject(ticketObject,position);
            }
        });
    }

    private class TicketDelegateViewHolder extends RecyclerView.ViewHolder{
        AppCompatTextView homeTeam, awayTeam, dateAndTime, noOfTickets;
        AppCompatButton deleteTicket;
        public TicketDelegateViewHolder(View itemView) {
            super(itemView);
            homeTeam = (AppCompatTextView) itemView.findViewById(R.id.tvHomeTeam);
            awayTeam = (AppCompatTextView) itemView.findViewById(R.id.awayTeam);
            dateAndTime = (AppCompatTextView) itemView.findViewById(R.id.dateAndTime);
            noOfTickets = (AppCompatTextView) itemView.findViewById(R.id.noOfTickets);

            deleteTicket = (AppCompatButton) itemView.findViewById(R.id.deleteButton);
        }
    }
}
