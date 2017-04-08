package com.skypunch.householdsolutions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TrendingScreen extends AppCompatActivity {
    private ListView offerList , serviceList;
    private String[] services = {"Cook" , "Maid" , "Gardener"} , offers = {"15% Off on Maid services in mumbai" , "5% off for new users on Cooks"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trending_screen);

        offerList = (ListView) findViewById(R.id.offersList);
        serviceList = (ListView) findViewById(R.id.servicesList);

        offerList.setAdapter(new ArrayAdapter<>(TrendingScreen.this, R.layout.activity_listview, offers));
        serviceList.setAdapter(new ArrayAdapter<>(TrendingScreen.this, R.layout.activity_listview, services));


        // TODO: 07/04/17 module will access the database to get the trending services and offers and then update the list and display it
    }
}
