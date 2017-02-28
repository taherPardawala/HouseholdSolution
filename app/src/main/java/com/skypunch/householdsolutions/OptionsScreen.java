package com.skypunch.householdsolutions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OptionsScreen extends AppCompatActivity {

    private TextView usernameTV;
    private Button editProfileBtn, logoutBtn , searchBtn;

    private final FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    private final FirebaseUser fireUser = fireAuth.getCurrentUser();
    private DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference().child("UserProfile"), fireDB1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_screen);

        usernameTV = (TextView) findViewById(R.id.userName);
        editProfileBtn = (Button) findViewById(R.id.editProfileBtn);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        searchBtn = (Button) findViewById(R.id.searchBtn);

        fireDB.child(fireUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String userProfile = dataSnapshot.child("userType").getValue(String.class);
                Log.d("DBTEST", "onDataChange: " + userProfile);

                if (userProfile.equals("SS")) {
                    fireDB1 = FirebaseDatabase.getInstance().getReference().child("ServiceSeeker");
                    new LastUser("SS");
                } else {
                    fireDB1 = FirebaseDatabase.getInstance().getReference().child("ServiceProvider");
                    new LastUser("SP");
                }

                fireDB1.child(fireUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.child("firstName").getValue(String.class);
                        usernameTV.setText(String.valueOf("Welcome " + value));
                        if(userProfile.equals("SS")) {
                            ServiceSeeker.userCity = dataSnapshot.child("city").getValue(String.class);
                        }
                        Log.d("DBTEST", "onDataChange: " + userProfile);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(OptionsScreen.this, EditProfile.class);
                OptionsScreen.this.startActivity(myIntent);
                OptionsScreen.this.finish();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireAuth.signOut();
                Intent myIntent = new Intent(OptionsScreen.this, WelcomeScreen.class);
                OptionsScreen.this.startActivity(myIntent);
                OptionsScreen.this.finish();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(OptionsScreen.this, SearchScreen.class);
                OptionsScreen.this.startActivity(myIntent);
                OptionsScreen.this.finish();
            }
        });
    }
}
