package myk.project.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import myk.project.Custom.VolleyEngine;
import myk.project.Interfaces.NavigationInterface;

import myk.project.FootballTickets.R;
import com.qintong.library.InsLoadingView;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements NavigationInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        final View view = inflater.inflate(R.layout.fragment_register, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        final InsLoadingView loadingView = (InsLoadingView) view.findViewById(R.id.loading_view);
        loadingView.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText name = (EditText) view.findViewById(R.id.edName);
                EditText email = (EditText) view.findViewById(R.id.edEmail);
                EditText password = (EditText) view.findViewById(R.id.edPassword);
                EditText confirm = (EditText) view.findViewById(R.id.edconfirmPassword);

                if (name.getText().length() == 0 || email.getText().length() == 0 || password.getText().length() == 0 || confirm.getText().length() == 0){
                    Snackbar.make(v,"Please fill all the fields",Snackbar.LENGTH_SHORT).show();
                }else {
                    String tname = name.getText().toString();
                    String temail = email.getText().toString();
                    String tpass = password.getText().toString();
                    String tconfirm = confirm.getText().toString();

                    if (tpass.contentEquals(tconfirm)){
                        HashMap<String, String> params = new HashMap<>();
                        params.put("name", tname);
                        params.put("email", temail);
                        params.put("password",tpass);
                        loadingView.setVisibility(View.VISIBLE);

                        VolleyEngine volleyEngine = new VolleyEngine(getContext());
                        String url = "http://104.236.7.202:3000/users/register";
                        volleyEngine.postContentRegister(url,params,loadingView, RegisterFragment.this);
                    }

                }

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

    @Override
    public void navigateToFragment(DialogFragment fragment) {

    }

    @Override
    public void navigateTo() {
        Fragment login = LoginFragment.newInstance("","");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment,login);
        transaction.addToBackStack(null);
        transaction.commit();
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
    }
}
