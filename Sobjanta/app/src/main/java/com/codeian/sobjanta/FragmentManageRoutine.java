package com.codeian.sobjanta;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codeian.sobjanta.Models.courseInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentManageRoutine.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentManageRoutine#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentManageRoutine extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseRoot;

    public FragmentManageRoutine() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentManageRoutine.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentManageRoutine newInstance(String param1, String param2) {
        FragmentManageRoutine fragment = new FragmentManageRoutine();
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


        getActivity().setTitle("Manage Schedule");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =   inflater.inflate(R.layout.fragment_manage_routine, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseRoot = FirebaseDatabase.getInstance().getReference();

        final TextView warnView = rootView.findViewById(R.id.warnView);
//        final EditText inputTitle = rootView.findViewById(R.id.msgTitle);
        final EditText inputMsg = rootView.findViewById(R.id.msgBody);
        final Button sendBtn = rootView.findViewById(R.id.sendBtn);
        final Spinner dropdown = rootView.findViewById(R.id.spinner1);


        //create a list of items for the spinner.
        String[] items = new String[ActivityMain.courseList.size()];//create an adapter to describe how the items are displayed, adapters are used in several places in android.//There are multiple variations of this, but this is the basic variant.

        for(int i = 0; i < ActivityMain.courseList.size(); i++)
        {
            courseInfo c = ActivityMain.courseList.get(i);
            items[i] = c.getCode().toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseRoot.child("student").child(user.getUid()).child("role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                //System.out.println(s);
                if (s.contains("student") == true)
                {
                    warnView.setText("You are not class representative.");
                    warnView.setVisibility(TextView.VISIBLE);

                    inputMsg.setVisibility(View.GONE);
//                    inputTitle.setVisibility(View.GONE);
                    dropdown.setVisibility(View.GONE);
                    sendBtn.setVisibility(View.GONE);
                }
                else
                {
                    sendBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            System.out.println("send Notification");

                            Calendar calendar = Calendar.getInstance();
                            Date date = calendar.getTime();
                            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss_dd-MMM-yyyy");
                            String formattedDate = df.format(date);
                            System.out.println(formattedDate);

                            DatabaseReference rootDB = FirebaseDatabase.getInstance().getReference();

                            DatabaseReference childDB = rootDB.child("notification").child(formattedDate).child("title");
                            childDB.setValue(dropdown.getSelectedItem().toString());

                            DatabaseReference childDB1 = rootDB.child("notification").child(formattedDate).child("msg");
                            childDB1.setValue(inputMsg.getText().toString());

                            inputMsg.setText("");
//                            inputTitle.setText("");

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


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        // Inflate the layout for this fragment
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
