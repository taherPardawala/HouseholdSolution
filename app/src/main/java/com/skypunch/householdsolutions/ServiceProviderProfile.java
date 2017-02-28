package com.skypunch.householdsolutions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ServiceProviderProfile extends AppCompatActivity {

    private String serviceProviderUID;
    private Button paymentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_provider_profile);
        Intent intent = getIntent();
        serviceProviderUID = intent.getExtras().getString("ServiceProviderUID");
        Toast.makeText(ServiceProviderProfile.this , serviceProviderUID , Toast.LENGTH_SHORT).show();

        paymentBtn = (Button) findViewById(R.id.paymentBtn);
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ServiceProviderProfile.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
