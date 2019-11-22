package com.example.oochs.babiesfirstprogram;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    Button okButton;
    TextView greeting;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    BroadcastReceiver dataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("downloadedPage");
            Log.wtf("DATA", data);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        inputField = (EditText) findViewById(R.id.name_input);
        okButton = (Button) findViewById(R.id.the_button);
        greeting = (TextView) findViewById(R.id.greeting);

        Intent intent = new Intent(MainActivity.this, MyIntentService.class);
        intent.putExtra("url", "http://www.example.com/");
        startService(intent);


        IntentFilter filter = new IntentFilter("com.example.oochs.babiesfirstprogram.SEND_DATA");
        registerReceiver(dataReceiver, filter);

        if (sharedPreferences.getString("USERNAME", ""). isEmpty()) {
            greeting.setVisibility(View.GONE);
            Log.wtf("Main preference", "USERNAME is empty");
        } else {
            greeting.setVisibility(View.VISIBLE);
            String name = sharedPreferences.getString("USERNAME", "");
            greeting.setText("Welcome back " + name);
            inputField.setText(name);
            Log.wtf("Main preference", name );

        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = inputField.getText().toString();
                editor.putString("USERNAME", name).apply();
                Snackbar.make(view, "Hello " + name, Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent launchActivityIntent = new Intent(MainActivity.this, SoBadImNotTheMainActivity.class);
                                launchActivityIntent.putExtra("name", name);
                                startActivity(launchActivityIntent);
                            }
                        }).show();
                }

        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        inputField.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        inputField.setText("Welcome back");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dataReceiver);
    }
}
