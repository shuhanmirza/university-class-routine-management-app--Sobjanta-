package com.codeian.sobjanta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityAddCourse extends AppCompatActivity {


    EditText coursecode, coursetitle, teacher, lastDay, room, friday, saturday, sunday, monday, tuesday, wednesday, thursday;
    private DatabaseReference databaseRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        coursecode = (EditText) findViewById(R.id.course_code);
        coursetitle = (EditText) findViewById(R.id.course_title);
        teacher = (EditText) findViewById(R.id.course_teacher);
        lastDay = (EditText) findViewById(R.id.course_lastday);
        room = (EditText) findViewById(R.id.course_room);
        friday = (EditText) findViewById(R.id.course_friday);
        saturday = (EditText) findViewById(R.id.course_saturday);
        sunday = (EditText) findViewById(R.id.course_sunday);
        monday = (EditText) findViewById(R.id.course_monday);
        tuesday = (EditText) findViewById(R.id.course_tuesday);
        wednesday = (EditText) findViewById(R.id.course_wednesday);
        thursday = (EditText) findViewById(R.id.course_thursday);

    }



    public void onSubmitCourse(View view){

        String course_code = coursecode.getText().toString();
        String course_title = coursetitle.getText().toString();
        String course_teacher = teacher.getText().toString();
        String course_room = room.getText().toString();
        String course_lastday = lastDay.getText().toString();
        String course_friday = friday.getText().toString();
        String course_saturday = saturday.getText().toString();
        String course_sunday = sunday.getText().toString();
        String course_monday = monday.getText().toString();
        String course_tuesday = tuesday.getText().toString();
        String course_wednesday = wednesday.getText().toString();
        String course_thursday = thursday.getText().toString();

        databaseRoot = FirebaseDatabase.getInstance().getReference();

        databaseRoot.child("course").child(course_code).child("title").setValue(course_title);
        databaseRoot.child("course").child(course_code).child("teacher").setValue(course_teacher);
        databaseRoot.child("course").child(course_code).child("room").setValue(course_room);
        databaseRoot.child("course").child(course_code).child("lastDay").setValue(course_lastday);
        databaseRoot.child("course").child(course_code).child("sun").setValue(course_sunday);
        databaseRoot.child("course").child(course_code).child("mon").setValue(course_monday);
        databaseRoot.child("course").child(course_code).child("tue").setValue(course_tuesday);
        databaseRoot.child("course").child(course_code).child("wed").setValue(course_wednesday);
        databaseRoot.child("course").child(course_code).child("thu").setValue(course_thursday);
        databaseRoot.child("course").child(course_code).child("fri").setValue(course_friday);
        databaseRoot.child("course").child(course_code).child("sat").setValue(course_saturday);

        Toast.makeText(ActivityAddCourse.this, "Course Info Added", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(ActivityAddCourse.this, ActivityAdmin.class));
    }
}
