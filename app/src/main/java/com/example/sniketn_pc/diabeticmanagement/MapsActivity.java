package com.example.sniketn_pc.diabeticmanagement;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Address> addressList = null;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Check the network provider enable and track my location
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude
                    double latitude =location.getLatitude();
                    //get the longitude
                    double longitude =location.getLongitude();
                    //instantiate the class LatLng
                    LatLng latLng = new LatLng(latitude,longitude);
                    //instantiate the class Geocoder
                    Geocoder geocoder = new Geocoder((getApplicationContext()));
                    try {
                        List<Address> addressList= geocoder.getFromLocation(latitude,longitude, 1);
                        String str = addressList.get(0).getAddressLine(0)+", ";
                        str += addressList.get(0).getLocality()+",";
                        str += addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        }
        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new  LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude
                    double latitude =location.getLatitude();
                    //get the longitude
                    double longitude =location.getLongitude();
                    //instantiate the class LatLng
                    LatLng latLng = new LatLng(latitude,longitude);
                    //instantiate the class Geocoder
                    Geocoder geocoder = new Geocoder((getApplicationContext()));
                    try {
                        List<Address> addressList= geocoder.getFromLocation(latitude,longitude, 1);
                        String str = addressList.get(0).getLocality()+",";
                        str += addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }

    }

    public void onSearch(View view)
    {
        EditText location_tf =(EditText)findViewById(R.id.Esearch);
        String location =location_tf.getText().toString();

        if(location!=null || location.equals(""))
        {
            Geocoder geocoder =new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.2f));
        }
    }

    public void onMenu(View view)
    {
        //return menu activity
        Intent display = new Intent(getBaseContext(),MenuOption.class);
        startActivity(display);
        finish();
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

        // Add a marker and move the camera
        LatLng medicenter = new LatLng(51.888315, -8.514894);
        LatLng glasheenMedical = new LatLng(51.884077, -8.48815);
        LatLng clinicDermatology = new LatLng(51.897532, -8.526910);
        LatLng waterlandsMedical = new LatLng(51.913206, -8.474039);
        LatLng weltonMed = new LatLng(51.878843, -8.503908);
        LatLng cladyMedical = new LatLng(51.880115, -8.517984);
        LatLng drDanMackenna = new LatLng(51.880539, -8.510088);
        LatLng citySouth = new LatLng(51.892364, -8.482622);


        mMap.addMarker(new MarkerOptions().position(medicenter).title("Medicenter"));
        mMap.addMarker(new MarkerOptions().position(glasheenMedical).title("Glasheen Medical center"));
        mMap.addMarker(new MarkerOptions().position(clinicDermatology).title("Clinic Dermatology"));
        mMap.addMarker(new MarkerOptions().position(waterlandsMedical).title("Waterlands Medical center"));
        mMap.addMarker(new MarkerOptions().position(weltonMed).title("welton Medical center"));
        mMap.addMarker(new MarkerOptions().position(cladyMedical).title("Clady Medical center"));
        mMap.addMarker(new MarkerOptions().position(drDanMackenna).title("Dr Dan Mackenna"));
        mMap.addMarker(new MarkerOptions().position(citySouth).title("City South"));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Medicenter));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(medicenter,10.2f));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

}
