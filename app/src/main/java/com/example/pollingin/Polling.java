package com.example.pollingin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Polling extends AppCompatActivity {
    RadioGroup mRgAllButtons;
    TextView titleView;
    String roomKey;
    String idSelected ,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polling);
        idSelected="";
        try {
            roomKey= intent.getStringExtra("roomKey");


            onJoinRoom(roomKey);
            mRgAllButtons = findViewById(R.id.radiogroup);
            mRgAllButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    RadioButton radioButton = radioGroup.findViewById(checkedId);

                    // on below line we are setting text
                    // for our status text view.
                    idSelected = radioButton.getHint().toString();
//                    Toast.makeText(Polling.this, radioButton.getHint(),Toast.LENGTH_SHORT).show();
                }
            });
            EditText et = (EditText) findViewById(R.id.name);

            Button btn = (Button) findViewById(R.id.vote);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name = et.getText().toString().trim();

                    JSONObject json = new JSONObject();
                    try {
                        json.put("name",name);
                        json.put("idSelected",idSelected);
                        json.put("roomKey",roomKey);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(name.length()>0 && idSelected.length()>0){
                        onVoteRoom(json);

                    }else{
                        Toast.makeText(Polling.this, "name and option is required for voting",Toast.LENGTH_LONG).show();
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("polling act ", e.getMessage());
        }

    }
    private void onVoteRoom(JSONObject jsonData){
//        METHOD POST for creating room
        String url = getString(R.string.voting_api);


        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Polling.this);

        // Building a request
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                // Using a variable for the domain is great for testing
                url,
                jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("vote respon", response.toString());
                        try {

                            Toast.makeText(Polling.this, response.getString("message").toString(), Toast.LENGTH_SHORT).show();
                            if(!response.getString("message").equals("user has been voted")){
                                Intent i = new Intent(Polling.this,ResultPolling.class);
                                Toast.makeText(Polling.this, "vote success",Toast.LENGTH_LONG).show();
                                i.putExtra("roomKey", roomKey);
                                startActivity(i);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        // Handle the response

                    }
                },

                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Handle the error

                    }
                });


        queue.add(request);

    }
    public void onJoinRoom(String roomKey) {
        String url = getString(R.string.voting_api)+"?roomKey="+roomKey;
        Log.e("join room: api ", url);
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mRgAllButtons = findViewById(R.id.radiogroup);
                        titleView = findViewById(R.id.title);
                        mRgAllButtons.setOrientation(LinearLayout.VERTICAL);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String title = jsonObject.getString("title");
                            titleView.setText(title);
                            JSONArray optionsJSON = new JSONArray(jsonObject.getString("options"));
                            for (int i = 0; i < optionsJSON.length(); i++) {
                                JSONObject item = optionsJSON.getJSONObject(i);
                                RadioButton rdbtn = new RadioButton(Polling.this);
//                                rdbtn.setId();
                                rdbtn.setHint(item.getString("_id"));
                                rdbtn.setText(item.getString("title"));
//                                rdbtn.setOnClickListener(this);
                                mRgAllButtons.addView(rdbtn);
                            }


                            Log.e("join room: respon", response.toString());
                        } catch (JSONException e) {
                            Log.e("join room: respon gagal", response.toString());
                            startActivity(new Intent(Polling.this,JoinRoom.class));
                            Toast.makeText(Polling.this, "Room doesn't exist",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Polling.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}