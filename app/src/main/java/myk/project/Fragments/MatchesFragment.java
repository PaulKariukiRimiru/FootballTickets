package myk.project.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import myk.project.Adapter.MainAdapter;
import myk.project.Interfaces.DataTransferInterface;
import myk.project.Pojo.CartObject;
import myk.project.Pojo.IMainObject;
import myk.project.Pojo.MainMatchObject;
import myk.project.FootballTickets.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MatchesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchesFragment extends Fragment implements DataTransferInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MatchesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchesFragment newInstance(String param1, String param2) {
        MatchesFragment fragment = new MatchesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    List<IMainObject> objects;
    SwipeRefreshLayout swipeRefreshLayout;
    MainAdapter mainAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_matches, container, false);

        objects = getMainObjectList();

        mainAdapter = new MainAdapter(getContext(),objects,this,null, null);
        RecyclerView matchesView = (RecyclerView) view.findViewById(R.id.viewMainmatches);
        matchesView.setAdapter(mainAdapter);
        matchesView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refereshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        if (isLastItemDisplaying(matchesView)){
            objects = getMainObjectList();
            mainAdapter.notifyDataSetChanged();
        }


        return view;
    }
    public void refreshLayout(List<IMainObject> mainObjects){
        objects = mainObjects;
        mainAdapter.notifyDataSetChanged();
    }

    @NonNull
    private List<IMainObject> getMainObjectList() {
        final List<IMainObject> objects = new ArrayList<>();
        String url = "http://104.236.7.202:3000/matches/show";
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject1) {
                try {
                    JSONArray jsonArray = jsonObject1.getJSONArray("matches");
                    for (int i = 0; i < jsonArray.length(); i++){
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.d("Matches response", jsonObject.toString());
                            MainMatchObject mainMatchObject = new MainMatchObject();
                            mainMatchObject.setMatchId(String.valueOf(jsonObject.getInt("id")));
                            mainMatchObject.setHomeName(jsonObject.getString("home_team"));
                            mainMatchObject.setAwayName(jsonObject.getString("away_team"));
                            mainMatchObject.setTime(jsonObject.getString("date"));
                            mainMatchObject.setTicketPrice((int)jsonObject.getDouble("price"));
                            objects.add(mainMatchObject);
                            refreshLayout(objects);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Matches response", e.getLocalizedMessage() );
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(),volleyError.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                Log.d("Volley Error", volleyError.getLocalizedMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
        return objects;
    }

    /**
     * Check whether the last item in RecyclerView is being displayed or not
     *
     * @param recyclerView which you would like to check
     * @return true if last position was Visible and false Otherwise
     */
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        mainMatchObjects = getMainObjectList();
        mainAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    List<IMainObject> mainMatchObjects = new ArrayList<>();


    public void addMatchObjects(IMainObject object1){
        MainMatchObject object = (MainMatchObject) object1;
        if (!mainMatchObjects.contains(object1)){
            CartObject cartObject = new CartObject();
            cartObject.setHomeTeam(object.getHomeName());
            cartObject.setAwayTeam(object.getAwayName());
            cartObject.setLocation(object.getLocation());
            cartObject.setMatchId(object.getMatchId());
            cartObject.setTime(object.getTime());
            cartObject.setPrice(object.getTicketPrice());
            cartObject.setHomeLogo(object.getHomeLogo());
            cartObject.setAwaylogo(object.getAwayLogo());
            cartObject.setNumberOfTickets(1);

            mainMatchObjects.add(object1);
            mListener.addCount();
            mListener.addMatchesCartObject(cartObject);
        }
    }

    @Override
    public void setValues(IMainObject object) {
        addMatchObjects(object);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void addCount();
        void addMatchesCartObject(IMainObject mainObject);
    }
}
