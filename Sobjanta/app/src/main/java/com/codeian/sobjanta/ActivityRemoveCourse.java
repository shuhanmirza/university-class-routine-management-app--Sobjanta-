package com.codeian.sobjanta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.codeian.sobjanta.Models.courseInfo;
import com.codeian.sobjanta.Models.userInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityRemoveCourse extends AppCompatActivity {
    Spinner dropdown;
    private DatabaseReference databaseRoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_course);

        dropdown = findViewById(R.id.courseSpinner);
        String[] items = ActivityMain.courseString.split("_");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }
    public void onSubmitCourse(View view){
        databaseRoot = FirebaseDatabase.getInstance().getReference();

        String code = dropdown.getSelectedItem().toString();

        databaseRoot.child("course").child(code).setValue(null);
        ActivityMain.courseInfoRefresh();

        Toast.makeText(ActivityRemoveCourse.this, "Course Info Removed <" + code +">", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ActivityRemoveCourse.this, ActivityAdmin.class));
    }
}
