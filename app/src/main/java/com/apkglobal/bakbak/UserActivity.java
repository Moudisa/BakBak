package com.apkglobal.bakbak;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class UserActivity extends AppCompatActivity {
    ListView listView;
    String url = "https://bakbak-a77e9.firebaseio.com/moudisa.json";
    ArrayList<String> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        listView = findViewById(R.id.listview);

        fetchUser();

        //to click on the user for chat
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                UserDetails.chatwith = arrayList.get(i);
                Intent intent = new Intent(UserActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchUser() {
        StringRequest sr = new StringRequest(0, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    //to call all objects inside a block with iteration
                    Iterator iterator = jo.keys();
                    String key = "";
                    //to get all data
                    while(iterator.hasNext())
                    {
                        //to pass all data inside key with iteration
                        key = iterator.next().toString();
                        if(!key.equals(UserDetails.username))
                        {
                            arrayList.add(key);
                        }
                    }

                    //to add the arraylist into listview. This is done by adapter class
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UserActivity.this, android.R.layout.simple_list_item_1,arrayList);
                    listView.setAdapter(arrayAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue rq = Volley.newRequestQueue(UserActivity.this);
        rq.add(sr);

    }
}
