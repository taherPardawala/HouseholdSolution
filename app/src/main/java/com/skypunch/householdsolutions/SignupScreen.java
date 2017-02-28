package com.skypunch.householdsolutions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.skypunch.householdsolutions.R.id.login;

public class SignupScreen extends AppCompatActivity {
    private Button cancelBtn , signupBtn , loginBtn;
    //All input fields
    private EditText fnameET , lnameET , mnameET , emailET , passwordET , cpassET , mobileET , residentET , addressET , cityET;
    //Progress dialog used for register time delay
    private ProgressDialog progDial;
    //Firebase related objects
    private FirebaseAuth fireAuth;
    private DatabaseReference fireDB;//used for taking the firebase db object
    private FirebaseUser fireUser;
    //Strings to get edit text data
    private String fname , lname , mname , email , password , cpass , mobile , resident , address , city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        initObjects();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SignupScreen.this, LoginScreen.class);
                //myIntent.putExtra("key", value); //Optional parameters
                SignupScreen.this.startActivity(myIntent);
                SignupScreen.this.finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SignupScreen.this, WelcomeScreen.class);
                //myIntent.putExtra("key", value); //Optional parameters
                SignupScreen.this.startActivity(myIntent);
                SignupScreen.this.finish();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateUser()){
                    return;
                } else {
                    progDial.setMessage("Signing Up..");
                    progDial.show();
                    fireAuth.createUserWithEmailAndPassword(email , password)
                            .addOnCompleteListener(SignupScreen.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        progDial.dismiss();
                                        Toast.makeText(SignupScreen.this , "Signing Up completed Saving info" , Toast.LENGTH_SHORT).show();
                                        fireDB = FirebaseDatabase.getInstance().getReference();
                                        ServiceSeeker ss = new ServiceSeeker(fname , lname , mname , email , password , mobile , resident , address , city);
                                        fireUser = fireAuth.getCurrentUser();
                                        fireDB.child("ServiceSeeker").child(fireUser.getUid()).setValue(ss);
                                        LastUser lu = new LastUser("SS");
                                        fireDB.child("UserProfile").child(fireUser.getUid()).setValue(lu);
                                        Toast.makeText(SignupScreen.this , "Info Saved" , Toast.LENGTH_SHORT).show();
                                        Intent myIntent = new Intent(SignupScreen.this, OptionsScreen.class);
                                        SignupScreen.this.startActivity(myIntent);
                                        SignupScreen.this.finish();
                                    }else{
                                        progDial.dismiss();
                                        Toast.makeText(SignupScreen.this , "Could not complete Registration try again" , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }

    public void initObjects(){
        //Initialize the Firebase objects
        fireAuth = FirebaseAuth.getInstance();
        //fireDB = FirebaseDatabase.getInstance().getReference();

        //Progress dialog object initialization
        progDial = new ProgressDialog(this);

        //Buttons initialization
        signupBtn = (Button) findViewById(R.id.signup);
        cancelBtn = (Button) findViewById(R.id.cancel);
        loginBtn = (Button) findViewById(login);

        //Edit texts initialize them and set them to empty strings
        fnameET = (EditText) findViewById(R.id.fname);
        lnameET = (EditText) findViewById(R.id.lname);
        mnameET = (EditText) findViewById(R.id.mname);
        emailET = (EditText) findViewById(R.id.email);
        passwordET = (EditText) findViewById(R.id.password);
        cpassET = (EditText) findViewById(R.id.cpassword);
        mobileET = (EditText) findViewById(R.id.mobile);
        residentET = (EditText) findViewById(R.id.resident);
        addressET = (EditText) findViewById(R.id.address);
        cityET = (EditText) findViewById(R.id.city);
    }

    public boolean validateUser(){
        if(TextUtils.isEmpty(fnameET.getText().toString().trim())){
            Toast.makeText(SignupScreen.this , "Enter First name" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            fname = fnameET.getText().toString();
        }
        if(TextUtils.isEmpty(lnameET.getText().toString().trim())){
            Toast.makeText(SignupScreen.this , "Enter Last name" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            lname = lnameET.getText().toString();
        }
        if(TextUtils.isEmpty(mnameET.getText().toString().trim())){
            Toast.makeText(SignupScreen.this , "Enter Middle name" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            mname = mnameET.getText().toString();
        }
        if(TextUtils.isEmpty(emailET.getText().toString().trim())){
            Toast.makeText(SignupScreen.this , "Enter Email" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            email = emailET.getText().toString();
        }
        if(TextUtils.isEmpty(passwordET.getText().toString().trim())){
            Toast.makeText(SignupScreen.this , "Enter Password" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            password = passwordET.getText().toString();
        }
        if(TextUtils.isEmpty(cpassET.getText().toString().trim())){
            Toast.makeText(SignupScreen.this , "Enter Password Again" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            cpass = cpassET.getText().toString();
        }
        if(TextUtils.isEmpty(mobileET.getText().toString().trim())){
            Toast.makeText(SignupScreen.this , "Enter Mobile number" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            mobile = mobileET.getText().toString();
        }
        if(TextUtils.isEmpty(residentET.getText().toString().trim())){
            Toast.makeText(SignupScreen.this , "Enter Resident phone number" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            resident = residentET.getText().toString();
        }
        if(TextUtils.isEmpty(addressET.getText().toString().trim())){
            Toast.makeText(SignupScreen.this , "Enter Address" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            address = addressET.getText().toString();
        }
        if(TextUtils.isEmpty(cityET.getText().toString().trim())){
            Toast.makeText(SignupScreen.this , "Enter City" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            city = cityET.getText().toString();
        }
        return false;
    }
}
