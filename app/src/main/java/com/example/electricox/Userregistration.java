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

public class Userregistration extends AppCompatActivity {

    EditText userNameEditText, userAddressEditText, userEmailEditText,
            userPhoneEditText, userPasswordEditText;

    String USERNAME, USERADDRESS, USEREMAIL, USERPHONE, USERPASSWORD;
    MaterialButton signUpButton;
    TextView login_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregistration);

        userNameEditText = findViewById(R.id.user_name);
        userAddressEditText = findViewById(R.id.user_address);
        userEmailEditText = findViewById(R.id.user_email);
        userPhoneEditText = findViewById(R.id.user_phone);
        userPasswordEditText = findViewById(R.id.user_password);
        login_back = findViewById(R.id.login_back);
        signUpButton = findViewById(R.id.user_signUp);

        login_back.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

        signUpButton.setOnClickListener(v -> validateUser());
    }

    private void validateUser() {
        USERNAME = userNameEditText.getText().toString().trim();
        USERADDRESS = userAddressEditText.getText().toString().trim();
        USEREMAIL = userEmailEditText.getText().toString().trim();
        USERPHONE = userPhoneEditText.getText().toString().trim();
        USERPASSWORD = userPasswordEditText.getText().toString().trim();

        // Regular expressions
        String namePattern = "^[a-zA-Z ]{3,}$";  // At least 3 characters, only alphabets and spaces
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        String phonePattern = "^[0-9]{10}$"; // Exactly 10 digits
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"; // At least 8 characters, 1 uppercase, 1 lowercase, 1 number, 1 special character

        String[] allowedDomains = {"gmail.com", "yahoo.com", "outlook.com", "hotmail.com"};

        if (USERNAME.isEmpty() || !USERNAME.matches(namePattern)) {
            showToast("Enter a valid name (at least 3 letters, no special characters)");
            userNameEditText.requestFocus();
        } else if (USERADDRESS.isEmpty() || USERADDRESS.length() < 5) {
            showToast("Enter a valid address (at least 5 characters)");
            userAddressEditText.requestFocus();
        } else if (USEREMAIL.isEmpty() || !USEREMAIL.matches(emailPattern)) {
            showToast("Enter a valid email");
            userEmailEditText.requestFocus();
        } else {
            boolean domainValid = false;
            for (String domain : allowedDomains) {
                if (USEREMAIL.endsWith("@" + domain)) {
                    domainValid = true;
                    break;
                }
            }
            if (!domainValid) {
                showToast("Enter an email with a valid domain (gmail, yahoo, outlook, hotmail)");
                userEmailEditText.requestFocus();
            } else if (USERPHONE.isEmpty() || !USERPHONE.matches(phonePattern)) {
                showToast("Enter a valid 10-digit phone number");
                userPhoneEditText.requestFocus();
            } else if (USERPASSWORD.isEmpty() || !USERPASSWORD.matches(passwordPattern)) {
                showToast("Password must be at least 8 characters with 1 uppercase, 1 lowercase, 1 number, and 1 special character");
                userPasswordEditText.requestFocus();
            } else {
                UserRegistrationVolley();
            }
        }
    }

    private void UserRegistrationVolley() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.SERVERUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("Already Exist")) {
                    showToast("User already exists");
                } else if (!response.trim().equals("failed")) {
                    showToast("Registration successful!");
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    showToast("Registration failed");
                }
            }
        }, error -> {
            showToast("Error: " + error);
            Log.i("Error", error.toString());
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("key", "userRegister");
                map.put("name", USERNAME);
                map.put("address", USERADDRESS);
                map.put("phone", USERPHONE);
                map.put("email", USEREMAIL);
                map.put("pass", USERPASSWORD);
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
