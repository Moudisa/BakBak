package com.apkglobal.bakbak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText username_l, password_l;
    Button new_user, login;
    String url = "https://bakbak-a77e9.firebaseio.com/moudisa.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_l = findViewById(R.id.username_l);
        password_l = findViewById(R.id.password_l);
        new_user = findViewById(R.id.new_user);
        login = findViewById(R.id.login);

        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                //to store the username and password in UserDetails class
                UserDetails.username = username_l.getText().toString();
                UserDetails.password = password_l.getText().toString();

                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest sr = new StringRequest(0, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                        if(response.equals("null"))
                            Toast.makeText(LoginActivity.this, "User not found",
                                    Toast.LENGTH_SHORT).show();
                        else {
                            try {
                                JSONObject jo = new JSONObject(response);

                                //if(jo.equals(username_l.getText().toString()))
                                if (jo.has(username_l.getText().toString()))
                                {
                                    //Toast.makeText(LoginActivity.this, username_l.getText().toString(), Toast.LENGTH_SHORT).show();
                                    JSONObject job = jo.getJSONObject(username_l.getText().toString());      //no need to initialize memory bcz job comes inside jo
                                    if (job.getString("password").equals(password_l.getText().toString())) {
                                        Intent intent = new Intent(LoginActivity.this, UserActivity.class);

                                        //to store the username and password in UserDetails class
                                        UserDetails.username = username_l.getText().toString();
                                        UserDetails.password = password_l.getText().toString();
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                RequestQueue rq = Volley.newRequestQueue(LoginActivity.this);
                rq.add(sr);
            }
        });
    }
}
