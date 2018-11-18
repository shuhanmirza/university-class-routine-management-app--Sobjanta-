package com.codeian.sobjanta;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.codeian.sobjanta.Helpers.ClassScheduleAdapter;
import com.codeian.sobjanta.Models.EnrolledDataModel;
import com.codeian.sobjanta.Models.ScheduleDataModel;
import com.codeian.sobjanta.Models.courseInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMyRoutine.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMyRoutine#newInstance} factory method  to
 * create an instance of this fragment.
 */
public class FragmentMyRoutine extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList myClassData;
    ListView listView;
    ClassScheduleAdapter adapter;

    public FragmentMyRoutine() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMyRoutine.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMyRoutine newInstance(String param1, String param2) {
        FragmentMyRoutine fragment = new FragmentMyRoutine();
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

        getActivity().setTitle("Class Schedule");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_my_routine, container, false);

        listView = rootView.findViewById(R.id.listView);
        TextView dateView = rootView.findViewById(R.id.dateView);
        TextView weekView = rootView.findViewById(R.id.weekView);
        ImageButton leftBtn = rootView.findViewById(R.id.dateLeft);
        ImageButton rightBtn = rootView.findViewById(R.id.dateRight);


        rightBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             ActivityMain.curDate = ActivityMain.dateIncrementer(ActivityMain.curDate,1);
             ActivityMain.curDay = ActivityMain.getDay(ActivityMain.curDate);
             ActivityMain.flg = 1;
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityMain.curDate = ActivityMain.dateIncrementer(ActivityMain.curDate,-1);
                ActivityMain.curDay = ActivityMain.getDay(ActivityMain.curDate);
                ActivityMain.flg = 1;
            }
        });

        weekView.setText(ActivityMain.curDay);
        dateView.setText(ActivityMain.curDate);

       // myClassData = loadJSONFromAsset();
        myClassData = loadRoutine();
        listView = rootView.findViewById(R.id.listView);
        adapter = new ClassScheduleAdapter(myClassData, getContext());

        listView.setAdapter(adapter);

        return rootView;
    }

    public ArrayList<ScheduleDataModel> loadJSONFromAsset() {
        ArrayList<ScheduleDataModel> locList = new ArrayList<>();
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("classSchedule.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray("schedule");

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                ScheduleDataModel location = new ScheduleDataModel();

                location.setCode(jo_inside.getString("course_code"));
                location.setName(jo_inside.getString("course_name"));
                location.setTime(jo_inside.getString("time"));
                location.setRoom(jo_inside.getString("room"));
                location.setTeacher(jo_inside.getString("teacher"));
                location.setCancelled(jo_inside.getBoolean("enabled"));

                //Add your values in your `ArrayList` as below:
                locList.add(location);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ActivityMain.ulalaFlg = 2;
        return locList;
    }

    public ArrayList<ScheduleDataModel> loadRoutine() {
        ArrayList<ScheduleDataModel> locList = new ArrayList<>();
        ArrayList<courseInfo> courseList = ActivityMain.courseList;

        for (int i = 0; i < courseList.size(); i++) {
            courseInfo temp = courseList.get(i);

            if (!checkDay(temp)) continue;
            if (!checkEnrollment(temp)) continue;

            ScheduleDataModel location = new ScheduleDataModel();

            location.setCode(temp.getCode());
            location.setTeacher(temp.getTitle());
            location.setTime(temp.getSchedule(ActivityMain.curDay.toLowerCase().substring(0, 3)));
            location.setRoom(temp.getRoom());
            location.setName(temp.getTeacher());
            location.setCancelled(false);

            String title = temp.getCode();
            String msg = "Time: " + location.getTime() + "\n Room:" + location.getRoom();

            if (ActivityMain.curDate.contains(((ActivityMain)getActivity()).getDate("dd-MMM-yyyy"))) {
//                try {
//                    ((ActivityMain) getActivity()).selfNotify(title, msg);
//                } catch (Exception e) {
//                    System.out.println("old notify exception");
//                }

                try {
                    ((ActivityMain) getActivity()).showNotification(getContext(), title, msg, getActivity().getIntent());
                } catch (Exception e) {
                    System.out.println("NEW notify exception");
                }

            }

                //Add your values in your `ArrayList` as below:
                locList.add(location);

        }
        return locList;
    }

    private boolean checkDay(courseInfo c) {
        String day = ActivityMain.curDay;
        String date = ActivityMain.curDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            Date strDate = sdf.parse(date);
            Date lastDate = sdf.parse(c.getLastDay());
            if (lastDate.before(strDate)) {
                System.out.println("date passed");
                return false;
            }

            day = day.toLowerCase().substring(0,3);
            //System.out.println(day);

            if ( c.getSchedule(day).length() == 0)
                return false;
        }catch (Exception e)
        {
            System.out.println(e);
        }

        return true;
    }

    private boolean checkEnrollment(courseInfo c)
    {
        try{
            if(ActivityMain.curUser.getCourses().contains(c.getCode()))
            {
                return true;
            }
        }catch (Exception e)
        {
            System.out.println("user not logged in");
            System.out.println(e);
            Intent intent = new Intent(getContext(), ActivityLogin.class);
            startActivity(intent);
        }
        return false;
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
