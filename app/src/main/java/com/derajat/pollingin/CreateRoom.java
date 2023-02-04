package com.derajat.pollingin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.ArrayList;


public class CreateRoom extends AppCompatActivity {
    private LinearLayout parentLinearLayout;
    StringRequest stringRequest;
    Context context;
    public int i = 0;
    private void createRoom(JSONObject jsonData){
//        METHOD POST for creating room
    String url = getString(R.string.voting_api);


        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(CreateRoom.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.

        // Make new json object and put params in it
//            JSONObject jsonParams = new JSONObject();
//            jsonParams.put("data", jsonData);


        // Building a request
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                // Using a variable for the domain is great for testing
                url,
                jsonData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("create room", response.toString());
                        try {

                            String roomKey = response.getString("roomKey");
                            Toast.makeText(CreateRoom.this, "Room created", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(CreateRoom.this,DisplayKeyRoom.class);
                            i.putExtra("roomKey", roomKey);
                            startActivity(i);
                            finish();
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


    public void onAddField(View v) {
        LayoutInflater inflater= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        final View rowView=inflater.inflate(R.layout.field, null);
        i= i+1;
        rowView.setId(i);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);

    }
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }
    public void onCreateRoom(View v) throws JSONException {
        EditText t = (EditText) findViewById(R.id.title);

        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);
        LinearLayout childLL;
        childLL = (LinearLayout) findViewById(R.id.child);
        JSONObject json = new JSONObject();
        json.put("title", t.getText().toString().trim());
        JSONArray array = new JSONArray();



        ArrayList<String> myEditTextList = new ArrayList<String>();
        for( int i = 0; i < parentLinearLayout.getChildCount(); i++ ){

            View view = parentLinearLayout.getChildAt(i);
            EditText edit = (EditText) view.findViewById(R.id.option);

            String text = edit.getText().toString().trim();
            if(text.length()==0){}else{
                Log.e("viewList obj: ", edit.getText().toString());
//                myEditTextList.add(text);
                JSONObject item = new JSONObject();
                item.put("title",text);
                array.put(item);

            }

        }


        json.put("options", array.toString());
        Log.e("simpan json-options", array.toString());
//        Log.e("simpan arrray", myEditTextList.toString());
        Log.e("simpan json",json.toString());
//        createRoom(json);
//        Log.e("simpan json", )


        createRoom(json);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);
//        parentLinearLayout.getChildCount();











    }
}