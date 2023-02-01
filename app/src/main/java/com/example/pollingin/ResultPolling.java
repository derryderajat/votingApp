package com.example.pollingin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultPolling extends AppCompatActivity {
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;
    ArrayList countArrayList;
    ArrayList<String> titleArrayList;
    Integer lengthList;
    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, 4));
        barEntriesArrayList.add(new BarEntry(2f, 6));
        barEntriesArrayList.add(new BarEntry(3f, 8));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_polling);
        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart);
        countArrayList = new ArrayList<>();

        try {
            String roomKey = intent.getStringExtra("roomKey");
            getBarEntries();
            onJoinRoom(roomKey);
            // creating a new bar data set.
//            barDataSet = new BarDataSet(barEntriesArrayList, "Geeks for Geeks");
//
//            barData = new BarData(barDataSet);
//
//            // below line is to set data
//            // to our bar chart.
//            barChart.setData(barData);
//
//            // adding color to our bar data set.
//            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//
//            // setting text color.
//            barDataSet.setValueTextColor(Color.BLACK);
//
//            // setting text size
//            barDataSet.setValueTextSize(16f);
//            barChart.getDescription().setEnabled(false);

            Log.e("result bardata", barEntriesArrayList.toString());

        } catch (Exception e) {

            Log.e("result polling", e.getMessage());

        }

    }

    public void onJoinRoom(String roomKey) {
        String url = getString(R.string.voting_api)+"?roomKey="+roomKey;
//        Log.e("join room: api ", url);
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       Log.e("result polling", response.toString());
                        try {
                            JSONObject dataAPI = new JSONObject(response);
                            JSONArray options = new JSONArray(dataAPI.getString("options"));
                            titleArrayList = new ArrayList<>();

                            lengthList = options.length();
                            for (int i=0;i<options.length();i++){
                                JSONObject item = options.getJSONObject(i);
                                Integer count = item.getInt("count");
                                String title = item.getString("title");
                                countArrayList.add(new BarEntry(Float.parseFloat(String.valueOf(i)),(float) count));
                                titleArrayList.add(title);

                            }
                            Log.e("result bardata ~ API", countArrayList.toString());
                            BarDataSet dataSet = new BarDataSet(countArrayList, dataAPI.getString("title"));
                            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            BarData data = new BarData( dataSet);

                            barChart.setData(data);
//                            Legend legend = barChart.getLegend();
//                            LegendEntry[] legendEntries = new LegendEntry[lengthList];
//                            for (int i=0;i<legendEntries.length; i++){
//                                LegendEntry entry = new LegendEntry();
//                                entry.formColor = barDataSet.getColor(i);
//                                entry.label = "get";
//                                legendEntries[i] = entry;
//                            }
//                            legend.setCustom(legendEntries);

/////////////////////
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("result Polling Error response", error.getMessage());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}




