package com.example.pollingin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CreateRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        Button onCreate =(Button) findViewById(R.id.createRoomButton);

        onCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(CreateRoom.this,DisplayKeyRoom.class));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("intent error",e.getMessage());
                }
            }
        });

    }
}