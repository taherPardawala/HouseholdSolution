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

public class LoginScreen extends AppCompatActivity {

    private EditText emailET , passwordET;
    private Button login;
    private FirebaseAuth fireAuth;
    private String email , password;
    private ProgressDialog progDiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        fireAuth = FirebaseAuth.getInstance();

        emailET = (EditText) findViewById(R.id.email);

        passwordET = (EditText) findViewById(R.id.password);


        login = (Button) findViewById(R.id.loginBtn);

        progDiag = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(emailET.getText().toString().trim())){
                    Toast.makeText(LoginScreen.this , "Enter Email ID" , Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    email = emailET.getText().toString().trim();
                }
                if (TextUtils.isEmpty(passwordET.getText().toString().trim())){
                    Toast.makeText(LoginScreen.this , "Enter Password" , Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    password = passwordET.getText().toString().trim();
                }

                progDiag.setMessage("Logining In");
                progDiag.show();
                fireAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progDiag.dismiss();
                        if (task.isSuccessful()){
                            Intent myIntent = new Intent(LoginScreen.this,OptionsScreen.class);
                            LoginScreen.this.startActivity(myIntent);
                            LoginScreen.this.finish();
                        } else {
                            Toast.makeText(LoginScreen.this , "Could not log in" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
