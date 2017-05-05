package uk.tonymerritt.bootcamplocator.fragments;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.tonymerritt.bootcamplocator.R;
import uk.tonymerritt.bootcamplocator.model.Klavar;
import uk.tonymerritt.bootcamplocator.services.DataService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions usermarker;
    private LocationsListFragment mListFragment;

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       mListFragment = (LocationsListFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.container_locations_list);

        if (mListFragment == null) {
            mListFragment = LocationsListFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container_locations_list, mListFragment).commit();

        }

        final EditText postcodeText = (EditText)view.findViewById(R.id.postcode_text);

        postcodeText.setOnKeyListener(new View .OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {

                    //You should make sure this is a valid postcode - check total cunt and characters.
                    String text = postcodeText.getText().toString();
                    int postcode = Integer.parseInt(text);

                    InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(postcodeText.getWindowToken(), 0);

                    showList();

                    updateMapForPostCode(postcode);
                    return  true;

                }

                return false;
            }
    });

        hideList();
        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void setUserMarker(LatLng latLng) {

        if (usermarker == null) {
            usermarker = new MarkerOptions().position(latLng).title("Current Location");
            mMap.addMarker(usermarker);
            Log.v("DONKEY", "Current location: " + latLng.latitude + " Long: " + latLng.longitude);
        }

        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            int postcode = Integer.parseInt(addresses.get(0).getPostalCode());
            updateMapForPostCode(postcode);

        }catch (IOException exception) {
        }

        updateMapForPostCode(92284);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    private void updateMapForPostCode(int postcode) {

        ArrayList<Klavar> locations = DataService.getInstance().getBootcampLocationsWithin10MilesOfPostcode(postcode);

        for (int x = 0; x < locations.size(); x++) {
            Klavar location = locations.get(x);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()));
            marker.title(location.getLocationTitle());
            marker.snippet(location.getLocationAddress());
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
            mMap.addMarker(marker);

        }

    }

    private  void hideList() {
        getActivity().getSupportFragmentManager().beginTransaction().hide(mListFragment).commit();
    }

    private void showList() {
        getActivity().getSupportFragmentManager().beginTransaction().show(mListFragment).commit();
    }

}