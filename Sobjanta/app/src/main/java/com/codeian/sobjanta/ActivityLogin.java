package com.codeian.sobjanta;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.LayoutInflater;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityLogin extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private FirebaseAuth firebaseAuth;

    int radioFlg = 0;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    //@BindView(R.id.userType) RadioGroup _userGroup;
    @BindView(R.id.userIdentity) TextInputLayout _userIdentity;


//    private static final String REG_REGEX = "\\d{10}";
//    private static final String PHONE_REGEX = "\\d{10}";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null)
        {
            /// start profile activity
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), ActivitySignUp.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });


        // This overrides the radiogroup onCheckListener
//        _userGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//        {
//            public void onCheckedChanged(RadioGroup group, int checkedId)
//            {
//                // This will get the radiobutton that has changed in its check state
//                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
//                // This puts the value (true/false) into the variable
//                boolean isChecked = checkedRadioButton.isChecked();
//                // If the radiobutton that has changed in check state is now checked...
//                if (isChecked)
//                {
//                    String val = checkedRadioButton.getText().toString();
//
//                    Log.d("VAL",val);
//                    // Changes the textview's text to "Checked: example radiobutton text
//                    if( val.equals("Teacher")){
//                        //_userIdentity.setHint("Email");
//
//
//                    }else{
//                        //_userIdentity.setHint("Registration No.");
//                    }
//
//                }
//            }
//        });


    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(ActivityLogin.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            // On complete call either onLoginSuccess or onLoginFailed
                                            onLoginSuccess();
                                            // onLoginFailed();
                                            progressDialog.dismiss();
                                        }
                                    }, 3000);
                        }
                        else{
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            // On complete call either onLoginSuccess or onLoginFailed
                                            //onLoginSuccess();
                                            onLoginFailed();
                                            progressDialog.dismiss();
                                        }
                                    }, 3000);
                        }

                    }
                });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View vi = inflater.inflate(R.layout.nav_header_activity_main, null); //log.xml is your file.
//        final TextView tv = vi.findViewById(R.id.studentName);
//        System.out.println("##################################");
//        System.out.println(tv.getText());

        finish();
        Intent intent = new Intent(this, ActivityMain.class);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


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

        return valid;
    }

}

//        //add
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference mChild = mDatabase.child("ulala");
//        mChild.setValue("asdasd");

    ///get
//    FirebaseUser user = firebaseAuth.getCurrentUser();
//
//    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child(user.getUid()).child("email").addValueEventListener(new ValueEventListener() {
//@Override
//public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//        String s = dataSnapshot.getValue(String.class);
//        System.out.println(s);
//        }
//
//@Override
//public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//        });

////get multipple
// Map<String, String> map = datasnapshot.getValue(Map.class);
//String name = map.get("key");
//Log.v("E_VALUE","key : "+ name);

//
//