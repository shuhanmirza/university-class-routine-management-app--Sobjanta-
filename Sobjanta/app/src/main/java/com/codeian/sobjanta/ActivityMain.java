package com.codeian.sobjanta;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.codeian.sobjanta.Models.courseInfo;
import com.codeian.sobjanta.Models.userInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , FragmentMyRoutine.OnFragmentInteractionListener,
        FragmentMyCourses.OnFragmentInteractionListener, FragmentAvailableCourse.OnFragmentInteractionListener,
        FragmentManageRoutine.OnFragmentInteractionListener,FragmentFeedback.OnFragmentInteractionListener,
        FragmentContact.OnFragmentInteractionListener,FragmentAnnouncement.OnFragmentInteractionListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    int notificationNumber = 0;
    public static userInfo curUser = new userInfo();
    static public ArrayList<courseInfo> courseList = new ArrayList<courseInfo>();
    static public ArrayList<userInfo> userList = new ArrayList<userInfo>();
    static public String curDay = "";
    static public String curDate = "";
    static public int flg = 1;
    public static String security = "";
    static String courseString = "";
    static String allUserString = "";
    static String teacherUserString = "";
    static String notTeacherUserString = "";
    static String studentUserString = "";
    static String crUserString = "";
    static int ulalaFlg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        courseList = new ArrayList<courseInfo>();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_routine);

        if(ulalaFlg == 0)
        {
            ulalaFlg = 1;
            Toast.makeText(ActivityMain.this, "Wait for the vibration. System is loading...",
                    Toast.LENGTH_LONG).show();
        }


        /////
        View header = navigationView.getHeaderView(0);
        final TextView _name = header.findViewById(R.id.studentName);
        final TextView _reg = header.findViewById(R.id.studentReg);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null ) {
                    curUser.setUid(user.getUid());
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("student").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Map <String, String> map = (Map<String, String>) dataSnapshot.getValue();
                            curUser.setEmail(map.get("email"));
                            curUser.setName(map.get("name"));
                            curUser.setReg(map.get("reg"));
                            curUser.setRole(map.get("role"));
                            curUser.setCourses(map.get("courses"));

                            _reg.setText(curUser.getReg());
                            _name.setText(curUser.getName());

                            System.out.println(courseList.size());

                            if(flg == 1)
                            {
                               try{
                                   displaySelectedScreen(R.id.nav_routine);
                               }catch (Exception e)
                               {
                                   System.out.println(e);
                                   Intent intent = getIntent();
                                   finish();
                                   startActivity(intent);
                               }
                                flg = 0;

                                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                vibe.vibrate(100);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    handler.postDelayed(this, 1000);

                }
            }
        };
        handler.postDelayed(runnable, 1000);

        databaseReference.child("notification").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String formatteddate = dataSnapshot.getKey().toString();

                getNotification(formatteddate);

                //System.out.println("##################################"+formatteddate);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        databaseReference.child("security").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 security = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userInfoRefresh();
        courseInfoRefresh();

        curDate = getDate("dd-MMM-yyyy");
        Calendar calendar = Calendar.getInstance();
        curDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        System.out.println(getDate("HH:mm:ss"));

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            finish();
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
        }

    }

    public static void courseInfoRefresh()
    {
        courseString = "";
        courseList = new ArrayList<courseInfo>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("course").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final courseInfo course = new courseInfo();
                String courseCode;
                Map <String, String> map = (Map<String, String>) dataSnapshot.getValue();

                courseCode = dataSnapshot.getKey().toString();
                System.out.println(courseCode);

                course.setCode(courseCode);
                course.setTitle(map.get("title"));
                course.setTeacher(map.get("teacher"));
                course.setLastDay(map.get("lastDay"));
                course.setRoom(map.get("room"));
                course.setFri(map.get("fri"));
                course.setSat(map.get("sat"));
                course.setSun(map.get("sun"));
                course.setMon(map.get("mon"));
                course.setTue(map.get("tue"));
                course.setWed(map.get("wed"));
                course.setThu(map.get("thu"));

                courseList.add(course);

                if(courseString.length() == 0)
                    courseString = courseCode;
                else
                    courseString = courseString + "_" +courseCode;

                ActivityMain.flg = 1;
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //courseInfoRefresh();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //courseInfoRefresh();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void userInfoRefresh()
    {
        allUserString = "";
        teacherUserString = "";
        notTeacherUserString = "";
        studentUserString = "";
        crUserString = "";
        userList = new ArrayList<userInfo>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("student").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final userInfo user = new userInfo();
                String uid;
                Map <String, String> map = (Map<String, String>) dataSnapshot.getValue();

                uid = dataSnapshot.getKey().toString();
                //System.out.println(courseCode);

                user.setUid(uid);
                user.setCourses(map.get("courses"));
                user.setEmail(map.get("email"));
                user.setName(map.get("name"));
                user.setReg(map.get("reg"));
                user.setRole(map.get("role"));

                userList.add(user);

                if(user.getRole().contains("!teacher"))
                {
                    if(notTeacherUserString.length() == 0)
                        notTeacherUserString = user.getEmail();
                    else
                        notTeacherUserString = notTeacherUserString + "_" +user.getEmail();
                }
                else if(user.getRole().contains("teacher"))
                {
                    if(teacherUserString.length() == 0)
                        teacherUserString = user.getEmail();
                    else
                        teacherUserString = teacherUserString + "_" +user.getEmail();
                }

                else if(user.getRole().contains("cr"))
                {
                    if(crUserString.length() == 0)
                        crUserString = user.getEmail();
                    else
                        crUserString = crUserString + "_" +user.getEmail();
                }
                else if(user.getRole().contains("student"))
                {
                    if(studentUserString.length() == 0)
                        studentUserString = user.getEmail();
                    else
                        studentUserString = studentUserString + "_" +user.getEmail();
                }


                if(allUserString.length() == 0)
                    allUserString = user.getEmail();
                else
                    allUserString = allUserString + "_" +user.getEmail();


                //if(curUser.getName() != null)displaySelectedScreen(R.id.nav_routine);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //userInfoRefresh();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //userInfoRefresh();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //userInfoRefresh();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = notificationNumber++;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }

    public void selfNotify(String title,String msg){

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_notify_error)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(msg);
        notificationBuilder.setDefaults(
                Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationNumber++,notificationBuilder.build());

    }

    public void getNotification(String ketNotification) {
        String[] parts = ketNotification.split("_");
        String time = parts[0];
        String date = parts[1];

        String todayDate = getDate("dd-MMM-yyyy");

        if(date.contains(todayDate) && todayDate.length() == date.length())
        {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("notification").child(ketNotification).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Map <String, String> map = (Map<String, String>) dataSnapshot.getValue();
                    String title = map.get("title");
                    String msg = map.get("msg");

                    try{
                        selfNotify(title,msg);
                    }catch (Exception e)
                    {
                        System.out.println("old notify exception");
                    }

                    try{
                        showNotification(ActivityMain.this,title,msg,getIntent());
                    }catch (Exception e)
                    {
                        System.out.println("NEW notify exception");
                    }

                    //System.out.println("#################################");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    public static String dateIncrementer(String date, int i)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar c = Calendar.getInstance();
        try{
            c.setTime(sdf.parse(date));
            c.add(Calendar.DATE, i);  // number of days to add
            date = sdf.format(c.getTime());
        }catch (Exception e)
        {
            System.out.println(e);
        }

        return date;
    }

    public static String getDay(String date){

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar c = Calendar.getInstance();
        String day = "";
        try{
            c.setTime(sdf.parse(date));
            day = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        }catch (Exception e)
        {
            System.out.println(e);
        }

        return day;
    }

    public String getDate(String pattern)
    {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        String formattedDate = df.format(date);

        return formattedDate;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        displaySelectedScreen(id);
        return true;
    }

    public void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_routine:
                fragment = new FragmentMyRoutine();
                break;
            case R.id.nav_my_course:
                fragment = new FragmentMyCourses();
                break;
            case R.id.nav_available_course:
                fragment = new FragmentAvailableCourse();
                break;
            case R.id.nav_announcement:
                if(curUser.getRole().contains("student")) {
                    Toast.makeText(ActivityMain.this, "Hey, You are not Authorized",
                            Toast.LENGTH_LONG).show();

                 break;
                }
                else if(curUser.getRole().contains("!teacher")) {
                    Toast.makeText(ActivityMain.this, "You are not Validated. Please contact Admin",
                            Toast.LENGTH_LONG).show();

                    break;
                }
                fragment = new FragmentAnnouncement();
                break;

                case R.id.nav_edit_routine:
                if(curUser.getRole().contains("student")) {
                    Toast.makeText(ActivityMain.this, "Hey, You are not CR",
                            Toast.LENGTH_LONG).show();

                    break;
                }
                else if(curUser.getRole().contains("!teacher")) {
                    Toast.makeText(ActivityMain.this, "You are not Validated. Please contact Admin",
                            Toast.LENGTH_LONG).show();

                    break;
                }
                fragment = new FragmentManageRoutine();
                break;
            case R.id.nav_logout:

                Intent intent = getIntent();
                finish();
                startActivity(intent);

                FirebaseAuth.getInstance().signOut();
                Intent intentNew = new Intent(this, ActivityLogin.class);
                startActivity(intentNew);
                break;

            case R.id.nav_admin:
                if(curUser.getRole().contains("admin") == false) {
                    Toast.makeText(ActivityMain.this, "Hey, You are not ADMIN",
                            Toast.LENGTH_LONG).show();
                    break;
                }

                finish();
                Intent intentNew1 = new Intent(this, ActivityAdmin.class);
                startActivity(intentNew1);
                break;

            case R.id.nav_feedback:
                fragment = new FragmentFeedback();
                break;

            case R.id.nav_contact:
                fragment = new FragmentContact();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContent, fragment);
            ft.commitAllowingStateLoss( );
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void setToolBar(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSaveInstanceState( Bundle outState ) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

}
