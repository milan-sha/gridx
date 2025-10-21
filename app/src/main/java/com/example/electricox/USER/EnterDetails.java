package com.example.electricox.USER;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.electricox.COMMON.Slot;
import com.example.electricox.COMMON.Utility;
import com.example.electricox.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class EnterDetails extends AppCompatActivity {


    Button uploadButton;
    TextInputLayout uploadDrivingLicence;
    String Slot , price , bunk_id, owner_id;
    TextView SlotNumber , Todayprice;
    TextView parkingTimeEditText, selectedTimeTextView;

    String selectedTime;
    String slotStatus;

    String SLOTNUMBER, VEHICLETYPE, PARKTIME, DRIVINGLICENS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);


        uploadDrivingLicence = findViewById(R.id.uploadDrivingLicence);
        uploadButton = findViewById(R.id.uploadButton);
        SlotNumber = findViewById(R.id.sloteNumber);

        parkingTimeEditText = findViewById(R.id.parkingTimeEditText);
        selectedTimeTextView = findViewById(R.id.selectedTimeTextView);

        Intent in = getIntent();
        Slot = in.getExtras().getString("slotNumber");
        bunk_id=in.getExtras().getString(("bunk_id"));
        owner_id=in.getExtras().getString(("owner_id"));


        SlotNumber.setText("Your Slot is : " + Slot);
        //Todayprice.setText(price + "/-");





        parkingTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        EnterDetails.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String amPm;
                                if (hourOfDay >= 12) {
                                    amPm = "PM";
                                    if (hourOfDay > 12) {
                                        hourOfDay -= 12;
                                    }
                                } else {
                                    amPm = "AM";
                                }

                                // Handle the selected time with AM/PM
                                selectedTime = String.format("%02d:%02d %s", hourOfDay, minute, amPm);
                                selectedTimeTextView.setText(selectedTime);
                                selectedTimeTextView.setVisibility(View.VISIBLE);
                            }
                        },
                        0,
                        0,
                        false
                );

                timePickerDialog.show();
            }
        });


        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SLOTNUMBER = Slot;
                DRIVINGLICENS = uploadDrivingLicence.getEditText().getText().toString().trim();
                PARKTIME = selectedTime;

                // Regular expression for vehicle number validation
                String vehicleNumberPattern = "^[A-Z]{2}[0-9]{2}[A-Z][0-9]{4}$";

                if (DRIVINGLICENS.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter Vehicle Number", Toast.LENGTH_SHORT).show();
                } else if (!DRIVINGLICENS.matches(vehicleNumberPattern)) {
                    Toast.makeText(getApplicationContext(), "Enter a valid vehicle Number (e.g., KL12A4568)", Toast.LENGTH_SHORT).show();
                } else if (PARKTIME.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Select time", Toast.LENGTH_SHORT).show();
                } else {
                    addParkingDetails();
                }

            }
        });



    }


    private void addParkingDetails() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.SERVERUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("failed")) {
                    Toast.makeText(getApplicationContext(), "Success ..!", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(EnterDetails.this,Slot.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("slotNumber", SLOTNUMBER);
//                    bundle.putString("slotStatus", slotStatus);
//                    in.putExtras(bundle);
                    startActivity(in);
                } else {
                    Toast.makeText(getApplicationContext(), " Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("sharedData", MODE_PRIVATE);
                final String uid = prefs.getString("reg_id", "No_idd");//"No name defined" is the default value.
                final String type = prefs.getString("type", "Notype");
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", "addParkingRequest");
                map.put("login_id", uid);
                map.put("bunk_id", bunk_id);
                map.put("SLOTNUMBER", SLOTNUMBER);
                map.put("DRIVINGLICENS", DRIVINGLICENS);
                map.put("PARKTIME", PARKTIME);
                map.put("owner_id", owner_id);
                return map;
            }
        };
        queue.add(request);
    }

    private void getParkingDetails() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.SERVERUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("failed")) {
                    String DATA = response;
                    String resArr[] = DATA.trim().split("#");
                    slotStatus = resArr[0];
                    Intent in = new Intent(EnterDetails.this,Slot.class);
      //              Bundle bundle = new Bundle();
       //             bundle.putString("slotNumber", SLOTNUMBER);
        //            bundle.putString("slotStatus", slotStatus);
        //            in.putExtras(bundle);
                    startActivity(in);
                } else {
                    Toast.makeText(getApplicationContext(), " Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "my error :" + error, Toast.LENGTH_LONG).show();
                Log.i("My error", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("sharedData", MODE_PRIVATE);
                final String uid = prefs.getString("reg_id", "No_idd");//"No name defined" is the default value.
                final String type = prefs.getString("type", "Notype");
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", "getParkingRequest");
                map.put("login_id", uid);
                map.put("SLOTNUMBER", Slot);
                map.put("bunk_id", bunk_id);
                return map;
            }
        };
        queue.add(request);
    }
}