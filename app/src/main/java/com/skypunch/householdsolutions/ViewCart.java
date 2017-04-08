package com.skypunch.householdsolutions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewCart extends AppCompatActivity {
    //firebase declarations
    private DatabaseReference fireCart = FirebaseDatabase.getInstance().getReference().child("UserCart");
    private DatabaseReference fireProvider = FirebaseDatabase.getInstance().getReference().child("ServiceProvider");
    private FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    private FirebaseUser fireUser = fireAuth.getCurrentUser();
    private ListView cartList;
    private ArrayList<String> userKeys = new ArrayList<>();
    private ArrayList<String> userNames = new ArrayList<>();
    /*
    private String[] cartItems = {"8-04-2017 Cook : Huzefa",
                                  "8-04-2017 Gardener : Taher",
                                  "8-04-2017 Maid : Chinmay",
                                  "8-04-2017 Cleaners : Omkar",
                                  "8-04-2017 Maid : Anjali",
                                  "8-04-2017 Gardener : Huzefa",
                                  "8-04-2017 Cook : Anjali",
                                  "8-04-2017 Maid : Huzefa"};
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_cart);

        cartList = (ListView) findViewById(R.id.cartList);
        //cartList.setAdapter(new ArrayAdapter<>(ViewCart.this, R.layout.activity_listview, cartItems));

        // TODO: 07/04/17 This module connects to the firebase database and then pulls the list of cartb items for the given user  

        fireCart.child(fireUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot cartItem : dataSnapshot.getChildren()){
                    Toast.makeText(ViewCart.this, cartItem.child("UID").getValue().toString() , Toast.LENGTH_SHORT).show();
                    userKeys.add(cartItem.child("UID").getValue().toString());
                }
                for(int i=0;i<userKeys.size();i++)
                {
                    fireProvider.child(userKeys.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            userNames.add(dataSnapshot.child("firstName").getValue().toString());
                            Toast.makeText(ViewCart.this, dataSnapshot.child("firstName").getValue().toString(), Toast.LENGTH_SHORT).show();
                            cartList.setAdapter(new ArrayAdapter<>(ViewCart.this, R.layout.activity_listview, userNames));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent myIntent = new Intent(ViewCart.this, OptionsScreen.class);
        ViewCart.this.startActivity(myIntent);
        ViewCart.this.finish();
    }
}


