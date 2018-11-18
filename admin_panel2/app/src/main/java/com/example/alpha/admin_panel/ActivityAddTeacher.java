package com.example.alpha.admin_panel;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityAddTeacher extends AppCompatActivity {


    EditText emailet, passwordet, nameet, codeet ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        emailet = (EditText) findViewById(R.id.teacher_email);
        passwordet = (EditText) findViewById(R.id.teacher_password);
        nameet = (EditText) findViewById(R.id.teacher_name);
        codeet = (EditText) findViewById(R.id.teacher_code);

    }


    public void onSubmitTeacher(View view){
        String teacher_email = emailet.getText().toString();
        String teacher_password = passwordet.getText().toString();
        String teacher_name = nameet.getText().toString();
        String teacher_code = codeet.getText().toString();

        Toast.makeText(ActivityAddTeacher.this, "Teacher Info Added", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(ActivityAddTeacher.this, ActivityAdmin.class));


    }
}
