package com.example.alpha.admin_panel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onAddTeacher(View view){
        startActivity(new Intent(ActivityAdmin.this, ActivityAddTeacher.class));
    }


    public void onRemoveTeacher(View view){

        startActivity(new Intent(ActivityAdmin.this, ActivityAddTeacher.class));
    }


    public void onAddCR(View view){

        startActivity(new Intent(ActivityAdmin.this, ActivityAddTeacher.class));
    }


    public void onRemoveCR(View view){

        startActivity(new Intent(ActivityAdmin.this, ActivityAddTeacher.class));
    }


    public void onAddCourse(View view){
        startActivity(new Intent(ActivityAdmin.this, ActivityAddCourse.class));
    }


    public void onRemoveCourse(View view){

        startActivity(new Intent(ActivityAdmin.this, ActivityAddTeacher.class));
    }

    public void onEditCourse(View view){

        startActivity(new Intent(ActivityAdmin.this, ActivityAddTeacher.class));
    }
}
