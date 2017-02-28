package com.skypunch.householdsolutions;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class SearchScreen extends AppCompatActivity {
    //Firebase declarations
    private FirebaseAuth fireAuth = FirebaseAuth.getInstance();
    private FirebaseUser fireUSer = fireAuth.getCurrentUser();
    private DatabaseReference fireDB = FirebaseDatabase.getInstance().getReference().child("ServiceProvider");
    private DatabaseReference fireHiredProviders = FirebaseDatabase.getInstance().getReference().child("HiredProviders");
    //Spinner and list declaration
    private Spinner spinner;
    private ListView resultLV;
    //Date picker declarations
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    //Validation variables
    private boolean isDateSelected = false;
    private String serviceType = "" , selectedProvider = "" , selectedDate="";
    //Array adapter and string declaration for the result list view
    private ArrayAdapter resultListAdapter;
    private ArrayList<String> serviceProviderResultArrayList = new ArrayList<>();
    private ArrayList<String> serviceProviderUIDArrayList = new ArrayList<>();
    //Counter to reset the array list for new search
    private int searchCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);

        //List view initialization of object
        resultLV = (ListView) findViewById(R.id.resultListView);
        //initialization of date picker objects
        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        //Spinner object initialization and value initialization
        spinner = (Spinner) findViewById(R.id.serviceSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.services_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //set listener to get the selected item/service in the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchScreen.this , parent.getItemAtPosition(position).toString() , Toast.LENGTH_SHORT).show();
                serviceType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Used for the date picker
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
        selectedDate = String.valueOf(day) + "-" + String.valueOf(month)+ "-" + String.valueOf(year);
        Log.d("DBTEST", "showDate: " + selectedDate);
        isDateSelected = true;
    }
    //Called when the search button is clicked
    public void onClickSearchBtn(View view){
        if (!isDateSelected) {
            return;
        } else {
            //this should empty the result if there are any previous results from the list view
            resultLV.setAdapter(new ArrayAdapter<>(SearchScreen.this, R.layout.activity_listview, new String[0]));
            if(searchCounter>0) {
                serviceProviderResultArrayList.clear();
                serviceProviderUIDArrayList.clear();
            }
            //order children by their city and service so that its easy to order them and retrieve them from the databse
            fireDB.orderByChild("service_city").equalTo(ServiceSeeker.userCity+"_"+serviceType).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    searchCounter++;
                    Log.d("DBTEST", "onDataChange: " + dataSnapshot.toString());
                    //Loop to iterate through all the children in the retrieved result
                    /*the loop variable i is used to keep a track of how many results were retrieved and is then used to initialize the service result
                    * array with the help of the temp result array which on .length gives 50 and not how many elements are in it at the moment which is a drag this shows the result as of now */
                    int i=0;
                    for (DataSnapshot userIDs : dataSnapshot.getChildren()) {
                        serviceProviderResultArrayList.add(userIDs.child("firstName").getValue(String.class));
                        serviceProviderUIDArrayList.add(userIDs.getKey());
                        Log.d("DBTEST", "onDataChange: " + serviceProviderResultArrayList.get(i));
                        Log.d("DBTEST", "onDataChange: " + userIDs.getKey());
                        i++;
                    }
                    if(i==0) {
                        resultLV.setAdapter(new ArrayAdapter<>(SearchScreen.this, R.layout.activity_listview, new String[0]));
                        Toast.makeText(SearchScreen.this , "No results were found",Toast.LENGTH_SHORT).show();
                    } else {

                        fireHiredProviders.child(serviceType).child(selectedDate).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int k=0;
                                Log.d("DBTEST", "onDataChange: " + dataSnapshot.toString());
                                for (DataSnapshot hiredProviders : dataSnapshot.getChildren()) {
                                    for(k=0;k<serviceProviderResultArrayList.size();k++) {
                                        if (hiredProviders.getKey().equals(serviceProviderUIDArrayList.get(k))) {
                                            serviceProviderResultArrayList.remove(k);
                                            serviceProviderUIDArrayList.remove(k);
                                        }
                                    }
                                }
                                if (serviceProviderResultArrayList.size() > 0) {
                                    resultListAdapter = new ArrayAdapter<>(SearchScreen.this, R.layout.activity_listview, serviceProviderResultArrayList);
                                    resultLV.setAdapter(resultListAdapter);
                                } else {
                                    Toast.makeText(SearchScreen.this, "No Service Providers available for the given day ", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        resultLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(SearchScreen.this, parent.getItemAtPosition(position).toString() ,Toast.LENGTH_SHORT).show();
                                Log.d("DBTEST", "onItemClick: " +parent.getItemAtPosition(position).toString());
                                selectedProvider = parent.getItemAtPosition(position).toString();
                                Log.d("DBTEST", "onItemClick: " + String.valueOf(position));
                                Intent myIntent = new Intent(SearchScreen.this , ServiceProviderProfile.class);
                                myIntent.putExtra("ServiceProviderUID", serviceProviderUIDArrayList.get(position));
                                SearchScreen.this.startActivity(myIntent);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
