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
import myk.project.Pojo.TeamObject;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 6/16/17.
 */

public class TeamDelegate extends AdapterDelegate<List<IMainObject>> {
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
    private List<TeamObject> mainMatchObjects = new ArrayList<>();

    public TeamDelegate(Context context, DataTransferInterface dataTransferInterface){
        this.context = context;
        this.transferInterface = dataTransferInterface;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected boolean isForViewType(@NonNull List<IMainObject> items, int position) {
        return items.get(position) instanceof TeamObject;
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
        return new TeamDelegate.MatchesDelegateViewHolder(inflater.inflate(R.layout.teamhome_item,parent,false));
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
        final TeamObject matchObject = (TeamObject) items.get(position);
        TeamDelegate.MatchesDelegateViewHolder matchesDelegateViewHolder = (TeamDelegate.MatchesDelegateViewHolder) holder;

        matchesDelegateViewHolder.opponent.setText(matchObject.getOpponent());
        matchesDelegateViewHolder.ammount.setText(matchObject.getAmmount());
        matchesDelegateViewHolder.tickets.setText(matchObject.getTickets());
        matchesDelegateViewHolder.sold.setText(matchObject.getSold());

        matchesDelegateViewHolder.rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SELECTED ITEM AT: ", String.valueOf(position));
                    transferInterface.setValues(matchObject);
            }
        });

    }

    public class MatchesDelegateViewHolder extends RecyclerView.ViewHolder{
        TextView opponent,tickets,sold,ammount;
        MaterialRippleLayout rippleView;
        public MatchesDelegateViewHolder(View itemView) {
            super(itemView);
            opponent = (TextView) itemView.findViewById(R.id.textView18);
            tickets = (TextView) itemView.findViewById(R.id.textView24);
            sold  = (TextView) itemView.findViewById(R.id.textView23);
            ammount = (TextView) itemView.findViewById(R.id.textView25);
            rippleView = (MaterialRippleLayout) itemView.findViewById(R.id.ripMainItem);
        }
    }
}