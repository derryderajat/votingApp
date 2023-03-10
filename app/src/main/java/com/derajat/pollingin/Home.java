package com.derajat.pollingin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button onCreate =(Button) findViewById(R.id.create);
        Button onJoin =(Button) findViewById(R.id.join);
        Button onResult =(Button) findViewById(R.id.viewPolling);
        onCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    startActivity(new Intent(Home.this,CreateRoom.class));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("intent error",e.getMessage());
                }

            }
        });
        onJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent intent = new Intent(Home.this, JoinRoom.class);
                    intent.putExtra("result","false");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("intent error",e.getMessage());
                }
            }
        });
        onResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, JoinRoom.class);
                intent.putExtra("result","true");
                startActivity(intent);

            }
        });
    }

}