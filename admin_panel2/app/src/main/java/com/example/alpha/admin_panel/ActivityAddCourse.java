package com.example.alpha.admin_panel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityAddCourse extends AppCompatActivity {


    EditText coursecode, coursetitle, teacher, lastDay, room, friday, saturday, sunday, monday, tuesday, wednesday, thursday;
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
        String course_friday = friday.getText().toString();
        String course_saturday = saturday.getText().toString();
        String course_sunday = sunday.getText().toString();
        String course_monday = monday.getText().toString();
        String course_tuesday = tuesday.getText().toString();
        String course_wednesday = wednesday.getText().toString();
        String course_thursday = thursday.getText().toString();


        Toast.makeText(ActivityAddCourse.this, "Course Info Added", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(ActivityAddCourse.this, ActivityAdmin.class));
    }
}
