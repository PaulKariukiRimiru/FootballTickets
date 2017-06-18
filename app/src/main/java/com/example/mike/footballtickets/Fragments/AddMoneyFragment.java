package com.example.mike.footballtickets.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mike.footballtickets.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddMoneyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddMoneyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMoneyFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddMoneyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMoneyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMoneyFragment newInstance(String param1, String param2) {
        AddMoneyFragment fragment = new AddMoneyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog =  super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
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
        View view = inflater.inflate(R.layout.fragment_add_money, container, false);
        final EditText edamount = (EditText) view.findViewById(R.id.ammount);
        Button button = (Button) view.findViewById(R.id.button);

        Log.d("param", mParam1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://104.236.7.202:3000/users/wallet/topup";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        if (!s.isEmpty()) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                if (jsonObject.getBoolean("error")) {
                                    Log.d("Error occured: ", jsonObject.getString("message"));
                                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                } else {
                                    mListener.setNewValue(String.valueOf(jsonObject.getInt("new_amount")));
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    if (preferences.contains("amount")){
                                        editor.remove("amount");
                                        editor.putInt("amount",jsonObject.getInt("new_amount"));
                                        editor.apply();
                                    }else {
                                        editor.putInt("amount",jsonObject.getInt("new_amount"));
                                        editor.apply();
                                    }
                                    dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("Error occured: ", e.getLocalizedMessage());
                                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("amount", edamount.getText().toString());
                        params.put("id", mParam1);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(request);
            }
        });
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
        void setNewValue(String newAmmount);
    }
}
