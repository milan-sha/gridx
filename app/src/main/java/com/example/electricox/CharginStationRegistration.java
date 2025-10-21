package com.example.electricox;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.electricox.COMMON.Utility;
import com.google.android.material.button.MaterialButton;
import java.util.HashMap;
import java.util.Map;

public class CharginStationRegistration extends AppCompatActivity {

    EditText csNameEditText, csAddressEditText, csEmailEditText,
            csPhoneEditText, csPasswordEditText;

    String CSNAME, CSADDRESS, CSEMAIL, CSPHONE, CSPASSWORD;
    MaterialButton CSsignUpButton;
    TextView CSlogin_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargin_station_registration);

        csNameEditText = findViewById(R.id.cs_name);
        csAddressEditText = findViewById(R.id.cs_address);
        csEmailEditText = findViewById(R.id.cs_email);
        csPhoneEditText = findViewById(R.id.cs_phone);
        csPasswordEditText = findViewById(R.id.cs_password);
        CSlogin_back = findViewById(R.id.login_back);
        CSsignUpButton = findViewById(R.id.cs_signUp);

        CSlogin_back.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

        CSsignUpButton.setOnClickListener(v -> validateCs());
    }

    private void validateCs() {
        CSNAME = csNameEditText.getText().toString().trim();
        CSADDRESS = csAddressEditText.getText().toString().trim();
        CSEMAIL = csEmailEditText.getText().toString().trim();
        CSPHONE = csPhoneEditText.getText().toString().trim();
        CSPASSWORD = csPasswordEditText.getText().toString().trim();

        // Regular expressions
        String namePattern = "^[a-zA-Z ]{3,}$"; // At least 3 characters, only alphabets and spaces
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        String phonePattern = "^[0-9]{10}$"; // Exactly 10 digits
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"; // At least 8 characters, 1 uppercase, 1 lowercase, 1 number, 1 special character

        String[] allowedDomains = {"gmail.com", "yahoo.com", "outlook.com", "hotmail.com"};

        if (CSNAME.isEmpty() || !CSNAME.matches(namePattern)) {
            showToast("Enter a valid name (at least 3 letters, no special characters)");
            csNameEditText.requestFocus();
        } else if (CSADDRESS.isEmpty() || CSADDRESS.length() < 5) {
            showToast("Enter a valid address (at least 5 characters)");
            csAddressEditText.requestFocus();
        } else if (CSEMAIL.isEmpty() || !CSEMAIL.matches(emailPattern)) {
            showToast("Enter a valid email");
            csEmailEditText.requestFocus();
        } else {
            boolean domainValid = false;
            for (String domain : allowedDomains) {
                if (CSEMAIL.endsWith("@" + domain)) {
                    domainValid = true;
                    break;
                }
            }
            if (!domainValid) {
                showToast("Enter an email with a valid domain (gmail, yahoo, outlook, hotmail)");
                csEmailEditText.requestFocus();
            } else if (CSPHONE.isEmpty() || !CSPHONE.matches(phonePattern)) {
                showToast("Enter a valid 10-digit phone number");
                csPhoneEditText.requestFocus();
            } else if (CSPASSWORD.isEmpty() || !CSPASSWORD.matches(passwordPattern)) {
                showToast("Password must be at least 8 characters with 1 uppercase, 1 lowercase, 1 number, and 1 special character");
                csPasswordEditText.requestFocus();
            } else {
                CSRegistrationVolley();
            }
        }
    }

    private void CSRegistrationVolley() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.SERVERUrl, response -> {
            if (response.trim().equals("Already Exist")) {
                showToast("Charging station already exists");
            } else if (!response.trim().equals("failed")) {
                showToast("Registration successful!");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            } else {
                showToast("Registration failed");
            }
        }, error -> {
            showToast("Error: " + error);
            Log.i("Error", error.toString());
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("key", "CSRegister");
                map.put("name", CSNAME);
                map.put("address", CSADDRESS);
                map.put("phone", CSPHONE);
                map.put("email", CSEMAIL);
                map.put("pass", CSPASSWORD);
                return map;
            }
        };
        queue.add(request);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}
