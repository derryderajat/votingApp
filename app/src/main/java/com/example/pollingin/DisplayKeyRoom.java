package com.example.pollingin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayKeyRoom extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_key_room);
        Intent intent = getIntent();
        String roomKey = intent.getStringExtra("roomKey");
        Log.e("roomKey", roomKey);

        tv = findViewById(R.id.textView2);
        try {
            tv.setText(roomKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Button btn = (Button) findViewById(R.id.btnCopy);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("text copied", roomKey);
                    Toast.makeText(DisplayKeyRoom.this, "text copied", Toast.LENGTH_SHORT).show();
                    clipboard.setPrimaryClip(clip);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onJoin(View view) {

    }
}