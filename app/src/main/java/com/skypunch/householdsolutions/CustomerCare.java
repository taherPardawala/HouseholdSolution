package com.skypunch.householdsolutions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomerCare extends AppCompatActivity {
    private Button sendBtn;
    private EditText messageET , subjectET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_care);

        sendBtn = (Button) findViewById(R.id.sendET);
        messageET = (EditText) findViewById(R.id.messageET);
        subjectET = (EditText) findViewById(R.id.subjectET);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(messageET.getText().toString())){
                    Toast.makeText(CustomerCare.this, "Enter Message", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(subjectET.getText().toString())){
                    Toast.makeText(CustomerCare.this, "Enter Subject", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.putExtra(Intent.EXTRA_EMAIL, "taher.work@outlook.com");
                    myIntent.putExtra(Intent.EXTRA_SUBJECT, subjectET.getText().toString());
                    myIntent.putExtra(Intent.EXTRA_TEXT, messageET.getText().toString());
                    myIntent.setType("message/rfc822");
                    try{
                        startActivity(Intent.createChooser(myIntent,"Choose an email client"));
                    }
                    catch (android.content.ActivityNotFoundException ane){
                        Toast.makeText(CustomerCare.this, "No email application found ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent myIntent = new Intent(CustomerCare.this, OptionsScreen.class);
        CustomerCare.this.startActivity(myIntent);
        CustomerCare.this.finish();
    }
}
