package com.skypunch.householdsolutions;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceProviderProfile extends AppCompatActivity {

    //Activity related decs
    private String serviceProviderUID , serviceDate , serviceType;
    private Button paymentBtn , addToCartBtn;
    private TextView fnameTV , lnameTV , mnameTV , emailTV;
    //Fire base decs
    private DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference().child("ServiceProvider");
    private DatabaseReference fireAddToCart = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser fireUser;
    private FirebaseAuth fireAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_provider_profile);
        Intent intent = getIntent();
        serviceProviderUID = intent.getExtras().getString("ServiceProviderUID");
        serviceDate = intent.getExtras().getString("ServiceDate");
        serviceType = intent.getExtras().getString("ServiceType");
        Toast.makeText(ServiceProviderProfile.this , serviceProviderUID , Toast.LENGTH_SHORT).show();
        Toast.makeText(ServiceProviderProfile.this , serviceDate , Toast.LENGTH_SHORT).show();
        Toast.makeText(ServiceProviderProfile.this , serviceType , Toast.LENGTH_SHORT).show();

        //Button inits
        paymentBtn = (Button) findViewById(R.id.paymentBtn);
        addToCartBtn = (Button) findViewById(R.id.addToCartBtn);


        //Text view inits
        fnameTV = (TextView) findViewById(R.id.fnameTV);
        lnameTV = (TextView) findViewById(R.id.lnameTV);
        mnameTV = (TextView) findViewById(R.id.mnameTV);
        emailTV = (TextView) findViewById(R.id.emailTV);

        fireDB.child(serviceProviderUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fnameTV.setText(dataSnapshot.child("firstName").getValue(String.class));
                lnameTV.setText(dataSnapshot.child("lastName").getValue(String.class));
                mnameTV.setText(dataSnapshot.child("middleName").getValue(String.class));
                emailTV.setText(dataSnapshot.child("email").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ServiceProviderProfile.this, "Hello", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(ServiceProviderProfile.this, PaymentScreen.class);
                ServiceProviderProfile.this.startActivity(myIntent);
                ServiceProviderProfile.this.finish();
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(24)
            @Override
            public void onClick(View v) {
                try {
                    fireUser = fireAuth.getCurrentUser();
                    Calendar cal = Calendar.getInstance();
                    String[] time = new String[10];
                    time = cal.getTime().toString().split(" ");
                    //Toast.makeText(ServiceProviderProfile.this, time[2]+time[3]+time[4] , Toast.LENGTH_SHORT).show();
                    fireAddToCart.child("UserCart").child(fireUser.getUid()).child(time[3]).child("Date").setValue(serviceDate);
                    fireAddToCart.child("UserCart").child(fireUser.getUid()).child(time[3]).child("ServiceType").setValue(serviceType);
                    fireAddToCart.child("UserCart").child(fireUser.getUid()).child(time[3]).child("UID").setValue(serviceProviderUID);

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
