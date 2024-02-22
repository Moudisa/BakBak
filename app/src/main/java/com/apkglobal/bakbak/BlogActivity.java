package com.apkglobal.bakbak;

import android.app.Activity;
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

public class BlogActivity extends AppCompatActivity {
    EditText name, email, blog;
    Button send;
    String url = "https://bakbak-a77e9.firebaseio.com/blog.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        //to initialize the firebase
        Firebase.setAndroidContext(this);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        blog= findViewById(R.id.blog);
        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest sr = new StringRequest(0, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // to send blog to firebase to server
                        Firebase firebase = new Firebase("https://bakbak-a77e9.firebaseio.com/blog");
                        firebase.child(name.getText().toString()).child(blog.getText().
                                toString()).child("email").setValue(email.getText().toString());
                        Toast.makeText(BlogActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jo1 = new JSONObject(response);
                            if(jo1.equals(name.getText().toString()))
                            {
                                //Toast.makeText(LoginActivity.this, username_l.getText().toString(), Toast.LENGTH_SHORT).show();
                                JSONObject jo2 = jo1.getJSONObject(name.getText().toString());      //no need to initialize memory bcz job comes inside jo
                                if(jo2.getString("password").equals(email.getText().toString()))
                                {
                                    Toast.makeText(BlogActivity.this, email.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(BlogActivity.this, "Incorrect Email", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(BlogActivity.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
                RequestQueue rq = Volley.newRequestQueue(BlogActivity.this);
                rq.add(sr);

            }
        });

    }
}
