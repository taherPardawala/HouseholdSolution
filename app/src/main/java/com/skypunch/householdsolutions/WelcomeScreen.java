package com.skypunch.householdsolutions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeScreen extends AppCompatActivity {
    private Button login , register , signup;
    private FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        fireAuth = FirebaseAuth.getInstance();
        if(fireAuth.getCurrentUser() != null){
            Intent myIntent = new Intent(WelcomeScreen.this , OptionsScreen.class);
            WelcomeScreen.this.startActivity(myIntent);
            WelcomeScreen.this.finish();
        }

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        signup = (Button) findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(WelcomeScreen.this, LoginScreen.class);
                //myIntent.putExtra("key", value); //Optional parameters
                WelcomeScreen.this.startActivity(myIntent);
                WelcomeScreen.this.finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(WelcomeScreen.this, RegisterScreen.class);
                //myIntent.putExtra("key", value); //Optional parameters
                WelcomeScreen.this.startActivity(myIntent);
                WelcomeScreen.this.finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(WelcomeScreen.this, SignupScreen.class);
                //myIntent.putExtra("key", value); //Optional parameters
                WelcomeScreen.this.startActivity(myIntent);
                WelcomeScreen.this.finish();
            }
        });
    }
}
