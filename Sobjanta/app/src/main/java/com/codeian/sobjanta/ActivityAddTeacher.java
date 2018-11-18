package com.codeian.sobjanta;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.codeian.sobjanta.Models.userInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ActivityAddTeacher extends AppCompatActivity {

    Spinner dropdown;
    private DatabaseReference databaseRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);
        dropdown = findViewById(R.id.teacherSpinner);
        String[] items = ActivityMain.notTeacherUserString.split("_");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

    }


    public void onSubmitTeacher(View view){
        databaseRoot = FirebaseDatabase.getInstance().getReference();

        String userEmail = dropdown.getSelectedItem().toString();
        userInfo teacher = new userInfo();

        for(int i = 0; i < ActivityMain.userList.size() ; i++)
        {
         userInfo temp = ActivityMain.userList.get(i);

         if(temp.getEmail().contains(userEmail) && temp.getEmail().length() == userEmail.length())
         {
             teacher = temp;
             break;
         }
        }

        databaseRoot.child("student").child(teacher.getUid()).child("role").setValue("teacher");
        ActivityMain.userInfoRefresh();

        Toast.makeText(ActivityAddTeacher.this, "Teacher Info Added <" + userEmail +">", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ActivityAddTeacher.this, ActivityAdmin.class));
    }
}