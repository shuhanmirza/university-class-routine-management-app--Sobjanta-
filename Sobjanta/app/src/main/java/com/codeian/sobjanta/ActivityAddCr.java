package com.codeian.sobjanta;

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

public class ActivityAddCr extends AppCompatActivity {

    Spinner dropdown;
    private DatabaseReference databaseRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cr);

        dropdown = findViewById(R.id.crSpinner);
        String[] items = ActivityMain.studentUserString.split("_");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    public void onSubmitCr(View view){
        databaseRoot = FirebaseDatabase.getInstance().getReference();

        String userEmail = dropdown.getSelectedItem().toString();
        userInfo cr = new userInfo();

        for(int i = 0; i < ActivityMain.userList.size() ; i++)
        {
            userInfo temp = ActivityMain.userList.get(i);

            if(temp.getEmail().contains(userEmail) && temp.getEmail().length() == userEmail.length())
            {
                cr = temp;
                break;
            }
        }

        databaseRoot.child("student").child(cr.getUid()).child("role").setValue("cr");
        ActivityMain.userInfoRefresh();

        Toast.makeText(ActivityAddCr.this, "CR Info Added <" + userEmail +">", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ActivityAddCr.this, ActivityAdmin.class));
    }
}
