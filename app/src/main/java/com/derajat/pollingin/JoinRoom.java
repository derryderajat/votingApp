package com.derajat.pollingin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_room);
        EditText et = (EditText) findViewById(R.id.roomKey);
        Button go = (Button) findViewById(R.id.joinBTN);
        Intent lastIntent = getIntent();
        String stat = lastIntent.getStringExtra("result");
        Log.e("polling status", stat);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String keyRoom = et.getText().toString().trim();
                if(stat.equals("true")){
                    Intent i = new Intent(JoinRoom.this,ResultPolling.class);
//                        i.putExtra("data",response);
                    i.putExtra("roomKey",keyRoom);
                    startActivity(i);
                }
                if(stat.equals("false")) {
                    Intent i = new Intent(JoinRoom.this,Polling.class);
//                        i.putExtra("data",response);
                    i.putExtra("roomKey",keyRoom);
                    startActivity(i);
                }
               


            }
        });

    }


}