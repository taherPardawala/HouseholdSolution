package com.skypunch.householdsolutions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PaymentScreen extends AppCompatActivity {
    private Button payBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_screen);

        payBtn = (Button) findViewById(R.id.paymentBtn);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentScreen.this, "Payment Complete ", Toast.LENGTH_SHORT).show();
                Toast.makeText(PaymentScreen.this, "Service Provider Hired", Toast.LENGTH_SHORT).show();
            }
        });

        // TODO: 07/04/17 Get user cards if available or if not available take input validate all fields and then make payment
        // and update the firebase database and assign service provider to the current user
    }
}
