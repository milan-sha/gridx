package com.example.electricox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.electricox.ADMIN.AdminHome;
import com.example.electricox.CHARGINGSTATION.Charging_Station;
import com.example.electricox.CHATBOT.chatbot;
import com.example.electricox.COMMON.Utility;
import com.example.electricox.USER.User_Nav;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView signUp, chatbot;
    EditText LoginUserName, LoginPassWord;
    Button login;

    AlertDialog.Builder builder;
    String EMAIL, PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = findViewById(R.id.signUp);
        login = findViewById(R.id.loginButton);
        LoginUserName = findViewById(R.id.login_username);
        LoginPassWord = findViewById(R.id.login_password);
        chatbot = findViewById(R.id.chat);

        checkPermissions();

        signUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterMenu.class)));
        chatbot.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), chatbot.class)));

        login.setOnClickListener(v -> validateLogin());
    }

    private void checkPermissions() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        };
        if (!hasPermission(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    private void validateLogin() {
        EMAIL = LoginUserName.getText().toString().trim();
        PASSWORD = LoginPassWord.getText().toString().trim();

        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        if (EMAIL.isEmpty()) {
            showToast("Please enter your email");
            LoginUserName.requestFocus();
        } else if (!EMAIL.matches(emailPattern)) {
            showToast("Invalid email format. Example: user@example.com");
            LoginUserName.requestFocus();
        } else if (PASSWORD.isEmpty()) {
            showToast("Please enter your password");
            LoginPassWord.requestFocus();
        }  else {
            login_volley();
        }
    }

    private void login_volley() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.SERVERUrl, response -> {
            if (!response.trim().equals("failed")) {
                String[] resArr = response.trim().split("#");

                SharedPreferences.Editor editor = getSharedPreferences("sharedData", MODE_PRIVATE).edit();
                editor.putString("reg_id", resArr[0]);
                editor.putString("type", resArr[1]);
                editor.apply();

                navigateToHome(resArr[1]);
            } else {
                showToast("Login Failed! Invalid credentials.");
            }
        }, error -> {
            showToast("Error: " + error);
            Log.i("Login Error", error.toString());
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("key", "login");
                params.put("email", EMAIL);
                params.put("pass", PASSWORD);
                return params;
            }
        };
        queue.add(request);
    }

    private void navigateToHome(String userType) {
        Intent intent;
        switch (userType) {
            case "ADMIN":
                intent = new Intent(LoginActivity.this, AdminHome.class);
                break;
            case "CHARGING STATION":
                intent = new Intent(LoginActivity.this, Charging_Station.class);
                break;
            case "USER":
                intent = new Intent(LoginActivity.this, User_Nav.class);
                break;
            default:
                showToast("Unknown user type. Contact support.");
                return;
        }
        showToast("Login Successful!");
        startActivity(intent);
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Exit!! Are You Sure?")
                .setCancelable(false)
                .setPositiveButton("YES", (dialog, id) -> finishAffinity())
                .setNegativeButton("NO", (dialog, id) -> dialog.cancel());

        AlertDialog alert = builder.create();
        alert.setTitle("Exit");
        alert.show();
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    public static boolean hasPermission(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}