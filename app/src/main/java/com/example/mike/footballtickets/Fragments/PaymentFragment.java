package com.example.mike.footballtickets.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mike.footballtickets.Activities.HomeActivity;
import com.example.mike.footballtickets.Pojo.CartList;
import com.example.mike.footballtickets.Pojo.CartObject;
import com.example.mike.footballtickets.Pojo.IMainObject;
import com.example.mike.footballtickets.Pojo.SeasonCartObject;
import com.example.mike.footballtickets.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PaymentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Serializable serializable;

    private OnFragmentInteractionListener mListener;

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param noOfTickets Parameter 1.
     * @param totalPrice Parameter 2.
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(String noOfTickets, String totalPrice, Serializable listObject) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, noOfTickets);
        args.putString(ARG_PARAM2, totalPrice);
        args.putSerializable("list", listObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            serializable = getArguments().getSerializable("list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        String formatedDate = dateFormat.format(calendar.getTime());

        final CartList cartList = (CartList) serializable;

        TextView number = (TextView) view.findViewById(R.id.textView6);
        TextView total = (TextView) view.findViewById(R.id.textView7);
        TextView date = (TextView) view.findViewById(R.id.textView);

        Button cancel = (Button) view.findViewById(R.id.button3);
        Button pay = (Button) view.findViewById(R.id.button4);

        date.setText(formatedDate);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HomeActivity.class));
            }
        });



        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mListener.pay();
                List<IMainObject> mainObjectList = cartList.getCartobjects();

                for (IMainObject mainObject : mainObjectList){
                    String url = "";
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    if (mainObject instanceof CartObject){
                        final boolean[] success = {false};
                        CartObject cartObject = (CartObject) mainObject;
                        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                success[0] = true;

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params = new HashMap<>();

                                return super.getParams();
                            }
                        };
                        requestQueue.add(stringRequest);
                        if (!success[0]){
                            break;
                        }
                    }else if (mainObject instanceof SeasonCartObject){
                        final SeasonCartObject cartObject = (SeasonCartObject) mainObject;
                        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params = new HashMap<>();
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                params.put("team_id", cartObject.getClubId());
                                params.put("user_id", String.valueOf(preferences.getInt("id",1)));
                                params.put("price", String.valueOf(cartObject.getPrice()));
                                params.put("seasonal_ticket", "True");
                                return super.getParams();
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }


                Toast.makeText(getContext(), "Transaction complete",Toast.LENGTH_SHORT).show();

                Fragment register = ThankYouFragment.newInstance("","");
                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                transaction1.replace(R.id.fragment,register);
                transaction1.addToBackStack(null);
                transaction1.commit();
            }
        });

        number.setText(mParam1);
        total.setText("Ksh. "+mParam2);
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
        void pay();
    }
}
