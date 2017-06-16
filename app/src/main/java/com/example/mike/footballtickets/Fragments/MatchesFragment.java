package com.example.mike.footballtickets.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mike.footballtickets.Activities.CartActivity;
import com.example.mike.footballtickets.Activities.MainActivity;
import com.example.mike.footballtickets.Adapter.MainAdapter;
import com.example.mike.footballtickets.Interfaces.DataTransferInterface;
import com.example.mike.footballtickets.Pojo.CartList;
import com.example.mike.footballtickets.Pojo.CartObject;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.example.mike.footballtickets.Pojo.MainMatchObject;
import com.example.mike.footballtickets.R;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        List<IMainObject> objects = new ArrayList<>();
        for (int i=1; i <=20; i++){
            MainMatchObject matchObject = new MainMatchObject();
            matchObject.setHomeName("Gor Mahia");
            matchObject.setAwayName("Thika Untd");
            matchObject.setTime("Sat,21/1/2017");
            matchObject.setLocation("Nyayo stadium");
            matchObject.setTicketPrice(100);
            matchObject.setHomeLogo("not uploaded");
            matchObject.setAwayLogo("not uploaded");
            matchObject.setMatchId("001");

            objects.add(matchObject);
        }
        MainAdapter mainAdapter = new MainAdapter(getContext(),objects,this,null, null);
        RecyclerView matchesView = (RecyclerView) view.findViewById(R.id.viewMainmatches);
        matchesView.setAdapter(mainAdapter);
        matchesView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));



        return view;
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
//    public List<IMainObject> getMatchObjectsToCartObjects(){
//        List<IMainObject> cartObjectList = new ArrayList<>();
//        if (mainMatchObjects != null){
//            for (MainMatchObject object:mainMatchObjects){
//                CartObject cartObject = new CartObject();
//                cartObject.setHomeTeam(object.getHomeName());
//                cartObject.setAwayTeam(object.getAwayName());
//                cartObject.setLocation(object.getLocation());
//                cartObject.setMatchId(object.getMatchId());
//                cartObject.setTime(object.getTime());
//                cartObject.setPrice(object.getTicketPrice());
//                cartObject.setHomeLogo(object.getHomeLogo());
//                cartObject.setAwaylogo(object.getAwayLogo());
//                cartObject.setNumberOfTickets(1);
//                cartObjectList.add(cartObject);
//            }
//        }
//        return cartObjectList;
//    }

    public void addMatchObjects(IMainObject object1){
        if (!mainMatchObjects.contains(object1)){
            MainMatchObject object = (MainMatchObject) object1;
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

            mainMatchObjects.add(cartObject);
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
