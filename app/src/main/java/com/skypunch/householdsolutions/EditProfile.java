package com.skypunch.householdsolutions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    private final FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    private final FirebaseUser fireUser = fireAuth.getCurrentUser();
    private DatabaseReference fireDB;
    private EditText fnameET , lnameET , mnameET , mobileET , residentET , addressET , cityET , serviceET;
    private Button updateProfileBtn , cancelBtn;

    private String fname , lname , mname , mobile , resident , address , city , service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        if (LastUser.userProfile.equals("SS")) {
            fireDB = FirebaseDatabase.getInstance().getReference().child("ServiceSeeker");
        } else {
            fireDB = FirebaseDatabase.getInstance().getReference().child("ServiceProvider");
        }

        initObjects();
        initEditText();

        updateProfileBtn = (Button) findViewById(R.id.updateProfileBtn);
        cancelBtn = (Button) findViewById(R.id.cancel);

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUser()) {
                    return;
                } else {
                    Toast.makeText(EditProfile.this , "Updating profile" , Toast.LENGTH_SHORT).show();
                    ServiceProviders sp = new ServiceProviders();
                    fireDB.child(fireUser.getUid()).child("firstName").setValue(fname);
                    fireDB.child(fireUser.getUid()).child("lastName").setValue(fname);
                    fireDB.child(fireUser.getUid()).child("middleName").setValue(fname);
                    fireDB.child(fireUser.getUid()).child("mobileNumber").setValue(fname);
                    fireDB.child(fireUser.getUid()).child("residentNumber").setValue(fname);
                    fireDB.child(fireUser.getUid()).child("address").setValue(fname);
                    fireDB.child(fireUser.getUid()).child("city").setValue(fname);
                    if(LastUser.userProfile.equals("SP"))
                        fireDB.child(fireUser.getUid()).child("service").setValue(fname);
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click", "onClick: Clicked");
                Intent myIntent = new Intent(EditProfile.this , OptionsScreen.class);
                EditProfile.this.startActivity(myIntent);
                EditProfile.this.finish();
            }
        });
    }

    public void initObjects(){
        //Edit texts initialize them and set them to empty strings
        fnameET = (EditText) findViewById(R.id.fname);
        lnameET = (EditText) findViewById(R.id.lname);
        mnameET = (EditText) findViewById(R.id.mname);
        mobileET = (EditText) findViewById(R.id.mobile);
        residentET = (EditText) findViewById(R.id.resident);
        addressET = (EditText) findViewById(R.id.address);
        cityET = (EditText) findViewById(R.id.city);
        serviceET = (EditText) findViewById(R.id.service);
    }

    public void initEditText(){
        //Log.d("Details" , spDetails[0]);

        fireDB.child(fireUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                fnameET.setText(dataSnapshot.child("firstName").getValue(String.class));
                lnameET.setText(dataSnapshot.child("lastName").getValue(String.class));
                mnameET.setText(dataSnapshot.child("middleName").getValue(String.class));
                mobileET.setText(dataSnapshot.child("mobileNumber").getValue(String.class));
                residentET.setText(dataSnapshot.child("residentNumber").getValue(String.class));
                addressET.setText(dataSnapshot.child("address").getValue(String.class));
                cityET.setText(dataSnapshot.child("city").getValue(String.class));
                if(LastUser.userProfile.equals("SP"))
                    serviceET.setText(dataSnapshot.child("service").getValue(String.class));
                else
                    serviceET.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public boolean validateUser(){
        if(TextUtils.isEmpty(fnameET.getText().toString().trim())){
            Toast.makeText(EditProfile.this , "Enter First name" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            fname = fnameET.getText().toString();
        }
        if(TextUtils.isEmpty(lnameET.getText().toString().trim())){
            Toast.makeText(EditProfile.this , "Enter Last name" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            lname = lnameET.getText().toString();
        }
        if(TextUtils.isEmpty(mnameET.getText().toString().trim())){
            Toast.makeText(EditProfile.this , "Enter Middle name" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            mname = mnameET.getText().toString();
        }
        if(TextUtils.isEmpty(mobileET.getText().toString().trim())){
            Toast.makeText(EditProfile.this , "Enter Mobile number" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            mobile = mobileET.getText().toString();
        }
        if(TextUtils.isEmpty(residentET.getText().toString().trim())){
            Toast.makeText(EditProfile.this , "Enter Resident phone number" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            resident = residentET.getText().toString();
        }
        if(TextUtils.isEmpty(addressET.getText().toString().trim())){
            Toast.makeText(EditProfile.this , "Enter Address" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            address = addressET.getText().toString();
        }
        if(TextUtils.isEmpty(cityET.getText().toString().trim())){
            Toast.makeText(EditProfile.this , "Enter City" , Toast.LENGTH_SHORT).show();
            return true;
        }else{
            city = cityET.getText().toString();
        }
        if(LastUser.userProfile.equals("SP")) {
            if (TextUtils.isEmpty(serviceET.getText().toString().trim())) {
                Toast.makeText(EditProfile.this, "Enter Service name", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                service = serviceET.getText().toString();
            }
        }
        return false;
    }

    @Override
    public void onBackPressed(){
        Intent myIntent = new Intent(EditProfile.this, OptionsScreen.class);
        EditProfile.this.startActivity(myIntent);
        EditProfile.this.finish();
    }
}
