package com.codeian.sobjanta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codeian.sobjanta.Models.userInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.BindView;

public class ActivitySignUp extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    boolean radioFlg = false;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_reg) EditText _regText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_security) EditText _securityText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    @BindView(R.id.userType) RadioGroup _userGroup;
    @BindView(R.id.input_reg_container) TextInputLayout _regContainer;
    @BindView(R.id.input_security_container) TextInputLayout _securityContainer;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("security").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ActivityMain.security = dataSnapshot.getValue().toString();
                System.out.println("#####################"+ActivityMain.security);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        _regContainer.setHint("Registration Number");
        _securityContainer.setVisibility(View.GONE);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });

        _userGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                   String val = checkedRadioButton.getText().toString();

                    Log.d("VAL",val);
                    // Changes the textview's text to "Checked: example radiobutton text
                    if( val.equals("Teacher")){
                        radioFlg =true;
                        _regContainer.setHint("Teacher Code");
                        _securityContainer.setVisibility(View.VISIBLE);

                    }else{
                        radioFlg = false;
                        _regContainer.setHint("Registration Number");
                        _securityContainer.setVisibility(View.GONE);
                    }

                }
            }
        });

    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(ActivitySignUp.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();
        final String reg = _regText.getText().toString();

        // TODO: Implement your own signup logic here.

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            // On complete call either onSignupSuccess or onSignupFailed
                                            // depending on success
                                            onSignupSuccess();
                                            // onSignupFailed();
                                            userInfo studentInfo;

                                            if (radioFlg)
                                                studentInfo = new userInfo(name,reg,email,"!teacher",ActivityMain.courseString);
                                            else
                                                studentInfo = new userInfo(name,reg,email,"student","");

                                            FirebaseUser user = firebaseAuth.getCurrentUser();
                                            databaseReference.child("student").child(user.getUid()).setValue(studentInfo);
                                            progressDialog.dismiss();
                                        }
                                    }, 3000);
                        }
                        else {
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            // On complete call either onSignupSuccess or onSignupFailed
                                            // depending on success
                                            //onSignupSuccess();
                                             onSignupFailed();
                                            progressDialog.dismiss();
                                        }
                                    }, 3000);
                        }

                    }
                });

;
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
        ActivityMain.flg = 1;
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reg = _regText.getText().toString();
        String sec = _securityText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if ((reg.isEmpty() || reg.length() < 10) && radioFlg == false) {
            _regText.setError("at least 10 digits");
            valid = false;
        }
        else if ( reg.isEmpty() && radioFlg == true) {
            _regText.setError("This field is must");
            valid = false;
        } else {
            _regText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (radioFlg) {
            if (sec.contains(ActivityMain.security) && sec.length() == ActivityMain.security.length()) {
                _securityText.setError(null);
            } else {
                _securityText.setError("Security Code does not match");
                valid = false;
            }
        }


        return valid;
    }
}

