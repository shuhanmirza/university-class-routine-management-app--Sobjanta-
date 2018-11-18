package com.codeian.sobjanta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        this.setTitle("Admin Panel");
    }


    public void onAddTeacher(View view){
        startActivity(new Intent(ActivityAdmin.this, ActivityAddTeacher.class));
    }


    public void onRemoveTeacher(View view){

        startActivity(new Intent(ActivityAdmin.this, ActivityRemoveTeacher.class));
    }


    public void onAddCR(View view){

        startActivity(new Intent(ActivityAdmin.this, ActivityAddCr.class));
    }


    public void onRemoveCR(View view){

        startActivity(new Intent(ActivityAdmin.this, ActivityRemoveCr.class));
    }


    public void onAddCourse(View view){
        startActivity(new Intent(ActivityAdmin.this, ActivityAddCourse.class));
    }


    public void onRemoveCourse(View view){

        startActivity(new Intent(ActivityAdmin.this, ActivityRemoveCourse.class));
    }

    public void onEditCourse(View view){

        startActivity(new Intent(ActivityAdmin.this, ActivityAddTeacher.class));
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ActivityAdmin.this, ActivityMain.class));
    }
}
