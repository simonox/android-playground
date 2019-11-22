package com.example.oochs.babiesfirstprogram;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = (EditText) findViewById(R.id.name_input);
        okButton = (Button) findViewById(R.id.the_button);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputField.getText().toString();

                Snackbar.make(view, "Hello " + name, Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent launchActivityIntent = new Intent(MainActivity.this, SoBadImNotTheMainActivity.class);
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


}
