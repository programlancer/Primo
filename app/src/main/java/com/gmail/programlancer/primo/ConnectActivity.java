package com.gmail.programlancer.primo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConnectActivity extends AppCompatActivity {

    String passData = null;
    boolean working;
    private TextView textViewResponse;
    private EditText textServerIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        textViewResponse = (TextView) findViewById(R.id.textViewResponse);
        textServerIP = (EditText) findViewById(R.id.editTextServerIP);
    }

    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }

    }

    public void onClickTestConnect(View view) {

        if (isOnline()) {

            Toast.makeText(this, "Connected to Internet !", Toast.LENGTH_LONG).show();

            working = true;

            new Thread() {
                @Override
                public void run() {
                    passData = HttpManager.getData();

                    working = false;
                }
            }.start();

            while (working) {
            }

            textViewResponse.setText(passData);

        } else Toast.makeText(this, "Not connected to Internet !", Toast.LENGTH_LONG).show();

        //textViewResponse.setText("Response");
    }

}
