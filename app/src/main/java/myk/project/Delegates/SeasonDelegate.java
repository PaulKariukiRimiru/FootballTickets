package myk.project.Delegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import myk.project.FootballTickets.R;
import myk.project.Interfaces.DataTransferInterface;
import myk.project.Pojo.IMainObject;
import myk.project.Pojo.SeasonObject;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 6/16/17.
 */

public class SeasonDelegate extends AdapterDelegate<List<IMainObject>> {

    private final LayoutInflater inflater;
    private final Context context;
    private final DataTransferInterface dataTransferInterface;

    public SeasonDelegate(Context context, DataTransferInterface dataTransferInterface){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.dataTransferInterface = dataTransferInterface;
    }

    @Override
    protected boolean isForViewType(@NonNull List<IMainObject> iMainObjects, int i) {
        return iMainObjects.get(i) instanceof SeasonObject;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new SeasonViewHolder(inflater.inflate(R.layout.season_item,viewGroup, false));
    }

    List<IMainObject> matchObjectList = new ArrayList<>();

    @Override
    protected void onBindViewHolder(@NonNull List<IMainObject> iMainObjects, final int i, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull List<Object> list) {
        final SeasonObject seasonObject = (SeasonObject) iMainObjects.get(i);
        SeasonViewHolder viewHolder1 = (SeasonViewHolder) viewHolder;

        viewHolder1.name.setText(seasonObject.getClubname());
        viewHolder1.location.setText(seasonObject.getClubLocation());
        viewHolder1.stadium.setText(seasonObject.getClubStadium());

        viewHolder1.rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SELECTED ITEM AT: ", String.valueOf(i));
                if (matchObjectList.contains(seasonObject)){
                    dataTransferInterface.setValues(seasonObject);
                }else {
                    dataTransferInterface.setValues(seasonObject);
                    matchObjectList.add(seasonObject);
                }
            }
        });
    }

    private class SeasonViewHolder extends RecyclerView.ViewHolder{
        TextView name, stadium, location;
        MaterialRippleLayout rippleView;


        public SeasonViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView15);
            stadium = (TextView) itemView.findViewById(R.id.textView32);
            location = (TextView) itemView.findViewById(R.id.textView33);

            rippleView = (MaterialRippleLayout) itemView.findViewById(R.id.ripMainItem);

        }
    }
}
