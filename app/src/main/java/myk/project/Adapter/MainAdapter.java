package myk.project.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import myk.project.Delegates.AccountDelegate;
import myk.project.Delegates.CartDelegate;
import myk.project.Delegates.MatchesDelegate;
import myk.project.Delegates.SeasonCartDelegate;
import myk.project.Delegates.SeasonDelegate;
import myk.project.Delegates.TeamDelegate;
import myk.project.Delegates.TicketsDelegate;
import myk.project.Interfaces.DataRemovalInterface;
import myk.project.Interfaces.DataTransferInterface;
import myk.project.Interfaces.NavigationInterface;
import myk.project.Pojo.IMainObject;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.List;

/**
 * Created by Mike on 1/28/2017.
 */

public class MainAdapter extends RecyclerView.Adapter {

    private AdapterDelegatesManager<List<IMainObject>> manager;
    private List<IMainObject> mainObjects;

    public MainAdapter(Context context, List<IMainObject> mainObjects, DataTransferInterface dataTransferInterface, DataRemovalInterface dataRemovalInterface, NavigationInterface navigationInterface){
        this.mainObjects = mainObjects;
        manager = new AdapterDelegatesManager<>();
            manager.addDelegate(new CartDelegate(context, dataRemovalInterface));
            manager.addDelegate(new TicketsDelegate(context,dataRemovalInterface));
            manager.addDelegate(new AccountDelegate(context,dataRemovalInterface, navigationInterface));
            manager.addDelegate(new MatchesDelegate(context, dataTransferInterface));
            manager.addDelegate(new TeamDelegate(context, dataTransferInterface));
            manager.addDelegate(new SeasonDelegate(context,dataTransferInterface));
            manager.addDelegate(new SeasonCartDelegate(context, dataRemovalInterface));

    }

    @Override
    public int getItemViewType(int position) {
        return manager.getItemViewType(mainObjects,position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return manager.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        manager.onBindViewHolder(mainObjects,position,holder);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        manager.onBindViewHolder(mainObjects,position,holder,payloads);
    }
    @Override
    public int getItemCount() {
        return mainObjects.size();
    }
}
