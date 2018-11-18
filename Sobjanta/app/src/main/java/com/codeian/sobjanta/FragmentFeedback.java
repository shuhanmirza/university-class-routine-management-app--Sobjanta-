package com.codeian.sobjanta;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentFeedback.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFeedback#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFeedback extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseRoot;

    private OnFragmentInteractionListener mListener;

    public FragmentFeedback() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFeedback.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFeedback newInstance(String param1, String param2) {
        FragmentFeedback fragment = new FragmentFeedback();
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
        getActivity().setTitle("Feedback");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseRoot = FirebaseDatabase.getInstance().getReference();

        final EditText inputMsg = rootView.findViewById(R.id.msgBody);
        final Button sendBtn = rootView.findViewById(R.id.sendBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("send feedback");

                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss_dd-MMM-yyyy");
                String formattedDate = df.format(date);
                System.out.println(formattedDate);

                DatabaseReference rootDB = FirebaseDatabase.getInstance().getReference();

                DatabaseReference childDB = rootDB.child("feedback").child(formattedDate).child("user");
                childDB.setValue(ActivityMain.curUser.getEmail().toString());

                DatabaseReference childDB1 = rootDB.child("feedback").child(formattedDate).child("msg");
                childDB1.setValue(inputMsg.getText().toString());

                inputMsg.setText("");

                Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);

                Fragment fragment = new FragmentMyRoutine();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flContent, fragment);
                ft.detach(fragment);
                ft.attach(fragment);
                ft.commitAllowingStateLoss();




            }
        });

        return rootView;
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
    }
}
