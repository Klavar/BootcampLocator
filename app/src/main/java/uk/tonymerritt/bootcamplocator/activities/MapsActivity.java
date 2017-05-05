package uk.tonymerritt.bootcamplocator.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
//import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;

import uk.tonymerritt.bootcamplocator.R;
import uk.tonymerritt.bootcamplocator.fragments.MainFragment;

public class MapsActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

final int PERMISION_LOCATION = 111;
private GoogleApiClient mGoogleApiClient;
private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }
        mainFragment = (MainFragment)getSupportFragmentManager().findFragmentById(R.id.container_main);

        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_main, mainFragment)
                    .commit();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISION_LOCATION);
            Log.v("DONKEY", "Requesting permissions");
        }else {
            Log.v("DONKEY", "Starting services from on conected");
            startLocationServices();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("DONKy", "Long:" + location.getLongitude() + " - Lat " + location.getLatitude());
        mainFragment.setUserMarker(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case  PERMISION_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {                 startLocationServices();
                    Log.v("DONKy", "Permission Granted - Starting Services");
                }else {
                    // Show a dialog. i.e, Cannot run location, denied permission.
                    Log.v("DONKEY", "Permission not granted");

                }
            }
        }
    }


    public void startLocationServices() {
        Log.v("DONKEY", "Starting Location Services Called");

        try {
            LocationRequest req = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, req, this);
Log.v("DONKEY", "Requesting location updates");
        }catch (SecurityException exeption) {
            // Show dialog to user, we cant get locaation
            Log.v("DONKEY", exeption.toString());
        }
    }

}