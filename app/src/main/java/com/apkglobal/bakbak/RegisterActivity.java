package com.apkglobal.bakbak;

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
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    Button back, register;
    EditText username_r, password_r;
    String url = "https://bakbak-a77e9.firebaseio.com/moudisa.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // to initialize the firebase reference
        Firebase.setAndroidContext(this);
        //type cast or initialization
        //convert xml widgets into java objects
        back = findViewById(R.id.back);
        register = findViewById(R.id.register);
        username_r = findViewById(R.id.username_r);
        password_r = findViewById(R.id.password_r);

        //to click on back button to jump to login activity

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to close the current activity
                finish();
            }
        });

        //to click on the register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest sr = new StringRequest(0, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //to create the new file on firebase
                                Firebase firebase = new Firebase("https://bakbak-a77e9.firebaseio.com/moudisa");
                                //to register single user without validation
                                //to chk if user is ist one to register
                                if (response.equals("null"))
                                {
                                    //to pass the register data to the server
                                    firebase.child(username_r.getText().toString())
                                            .child("password").setValue(password_r.getText().toString());
                                    //password here in double quotes is key value
                                }
                                else
                                {
                                    try {
                                        //to break the json data for accessing username
                                        JSONObject jo=new JSONObject(response); //creates json object
                                        if (jo.has(username_r.getText().toString()))
                                        {
                                            //chking if user is already registered
                                            Toast.makeText(RegisterActivity.this,
                                                    "username already exists", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {//user is not registered, nd else will register it
                                            firebase.child(username_r.getText().toString())
                                                    .child("password").setValue(password_r.getText().toString());
                                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
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

                RequestQueue rq = Volley.newRequestQueue(RegisterActivity.this);
                rq.add(sr);

            }
        });
    }
}
