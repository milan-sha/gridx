package com.example.electricox.USER.ui;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.electricox.ADAPTERS.RequestAdapter;
import com.example.electricox.COMMON.RequestPojo;
import com.example.electricox.COMMON.Utility;
import com.example.electricox.R;
import com.example.electricox.USER.ViewQr;
import com.example.electricox.databinding.FragmentYourBookingBinding;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Your_bookings extends Fragment {

    private FragmentYourBookingBinding binding;
    ListView listView;

    List<RequestPojo> list;


    AlertDialog.Builder builder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentYourBookingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView=root.findViewById(R.id.viewRequestTouser);

        builder=new AlertDialog.Builder(getContext());



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                builder.setMessage("Heloo " + list.get(position).getU_name())
                        .setCancelable(false)
                        .setPositiveButton("Get Qrcode", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String req_id=list.get(position).getBooking_id();
                                getQrcode(req_id);

                            }
                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();

                alert.setTitle("Get Qrcode");
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#1d1d1d"));
                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#94c3ab"));
                        alert.getButton(android.app.AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#ff6600"));

                    }
                });
                alert.show();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

            }
        });

        getUserRequests();



        return root;
    }


    private void getQrcode(String req_id)
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.SERVERUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("failed")) {
                    Gson gson = new Gson();
                    list = Arrays.asList(gson.fromJson(response, RequestPojo[].class));

                    RequestPojo requestPOJO = list.get(0);

                    String Qr=requestPOJO.getQR_code();
                    String QrId=requestPOJO.getBooking_id();

                   Intent in=new Intent(getContext(), ViewQr.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("qrcode", Qr);
                    bundle.putString("qrId", QrId);

                    in.putExtras(bundle);
                    startActivity(in);


                } else {
                    Toast.makeText(getContext(), "Request Pending", Toast.LENGTH_SHORT).show();

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences prefs = getContext().getSharedPreferences("sharedData", MODE_PRIVATE);
                final String reg_id = prefs.getString("login_id", "No_id");
                final String type = prefs.getString("type", "NO-Type");
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", "getQr");
                System.out.println(req_id);
                map.put("id", req_id);


                return map;
            }

        };
        queue.add(request);
    }


    private void getUserRequests()
    {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, Utility.SERVERUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.trim().equals("failed")) {
                    Gson gson = new Gson();
                    list = Arrays.asList(gson.fromJson(response, RequestPojo[].class));
                    RequestAdapter adapter = new RequestAdapter(getActivity(), list);
                    listView.setAdapter(adapter);
                    registerForContextMenu(listView);

                } else {
                    Toast.makeText(getContext(), "NO_DATA", Toast.LENGTH_SHORT).show();

                    listView.setVisibility(View.GONE);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "" + error, Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences prefs = getContext().getSharedPreferences("sharedData", MODE_PRIVATE);
                final String uid = prefs.getString("reg_id", "No_idd");//"No name defined" is the default value.
                final String type = prefs.getString("type", "Notype");
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", "viewUserRequests");
                map.put("id", uid);


                return map;
            }

        };
        queue.add(request);
    }




}