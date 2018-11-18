package com.codeian.sobjanta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codeian.sobjanta.Helpers.EnrolledCourseAdapter;
import com.codeian.sobjanta.Models.EnrolledDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMyCourses.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMyCourses#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMyCourses extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList myCourseData;
    ListView listView;
    EnrolledCourseAdapter adapter;

    public FragmentMyCourses() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMyCourses.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMyCourses newInstance(String param1, String param2) {
        FragmentMyCourses fragment = new FragmentMyCourses();
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

        getActivity().setTitle("Enrolled Courses");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_course, container, false);

        myCourseData = loadEnrolledCourse();

        listView = rootView.findViewById(R.id.listView);
        adapter = new EnrolledCourseAdapter(myCourseData, getContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                EnrolledDataModel dataModel= (EnrolledDataModel) myCourseData.get(position);
                myCourseData.remove(dataModel);

                String temp = dataModel.getName();

                ActivityMain.curUser.courseList.remove(temp);

                temp = "";
                for (int i = 0; i < ActivityMain.curUser.courseList.size() ; i++)
                {
                    if (temp.length() == 0) {
                        temp = temp + ActivityMain.curUser.courseList.get(i) ;

                    }else {
                        temp = temp + "_"+ ActivityMain.curUser.courseList.get(i);
                    }
                }
                DatabaseReference rootDB = FirebaseDatabase.getInstance().getReference();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference childDB = rootDB.child("student").child(user.getUid()).child("courses");
                childDB.setValue(temp);

                Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(100);

                adapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    public ArrayList <EnrolledDataModel> loadEnrolledCourse(){
        ArrayList<EnrolledDataModel> locList = new ArrayList<>();

        String []parts = ActivityMain.curUser.getCourses().split("_");
        ActivityMain.curUser.courseList = new ArrayList<String>();

        for(int i =  0; i < parts.length ; i++)
        {
            EnrolledDataModel location = new EnrolledDataModel();
            location.setName(parts[i]);
            locList.add(location);
            ActivityMain.curUser.courseList.add(parts[i]);
        }

        return locList;
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
