package com.gmail.programlancer.primo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Date;

public class GpsActivity extends AppCompatActivity {

    private LocationManager locationManager;

    CheckBox checkBoxGps;
    CheckBox checkBoxNet;
    TextView textViewGpsLocation;
    TextView textViewNetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        checkBoxGps = (CheckBox) findViewById(R.id.checkBoxGps);
        checkBoxNet = (CheckBox) findViewById(R.id.checkBoxNet);
        textViewGpsLocation = (TextView) findViewById(R.id.textViewGpsLocation);
        textViewNetLocation = (TextView) findViewById(R.id.textViewNetLocation);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        checkEnabled();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        checkEnabled();

        if (checkBoxGps.isChecked()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 * 10, 10, locationListener);
        }

        if (checkBoxNet.isChecked()) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                    locationListener);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {

            if (ActivityCompat.checkSelfPermission(GpsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(GpsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) checkBoxGps.setChecked(true);
            if (provider.equals(LocationManager.NETWORK_PROVIDER)) checkBoxNet.setChecked(true);
        }
    };

    private void showLocation(Location location) {
        if (location == null)
            return;

        if (location.getProvider().equals(LocationManager.GPS_PROVIDER))
            textViewGpsLocation.setText(formatLocation(location));
        if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER))
            textViewNetLocation.setText(formatLocation(location));

    }

    private String formatLocation(Location location) {
        if (location == null)  return "Is null";
        return String.format(
                "Coordinates: lat = %1$s, lon = %2$s, time = %3$tF %3$tT",
                Location.convert(location.getLatitude(),Location.FORMAT_SECONDS), Location.convert( location.getLongitude(),Location.FORMAT_SECONDS), new Date(
                        location.getTime()));
        /*return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));*/
    }

    private void checkEnabled() {
        checkBoxGps.setChecked(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        checkBoxNet.setChecked(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    public void onClickLocSettingsButton(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }
}
