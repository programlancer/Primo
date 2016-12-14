package com.gmail.programlancer.primo;

import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private TextView textViewReceived;
    protected static LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewReceived=(TextView)findViewById(R.id.textViewReceived);
        textViewReceived.setText("No messages received.");

        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    private void onClickRightButton(View view) {
        Intent intent=new Intent(this,ConnectActivity.class);
        startActivity(intent);
    }

    public void onClickLeftButton(View view) {
        Intent intent=new Intent(this,GpsActivity.class);
        startActivity(intent);
    }
}
