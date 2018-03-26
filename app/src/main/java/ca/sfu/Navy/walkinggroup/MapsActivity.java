package ca.sfu.Navy.walkinggroup;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationListener;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        /*GoogleMap.OnMarkerClickListener,*/ com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private ServerProxy proxy;
    private List<Group> groups = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        String token = SavedSharedPreference.getPrefUserToken(MapsActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);


        // 1
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)//provides callbacks that are triggered when
                                                // the client is connected (onConnected())
                                                // or temporarily disconnected (onConnectionSuspended()) from the service
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

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

    //



    @Override
    protected void onStart() {
        super.onStart();
        // 2
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 3
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /////enable zoom in zoom out; enable
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
        //mMap.setInfoWindowAdapter(new CustomWindowAdapter(MapsActivity.this));


    }

    //checks if the app has been granted the ACCESS_FINE_LOCATION permission.//////******main purpose
    //If it hasn’t, then request it from the user.
    //it is called by onconnected() function, and onconnected() is called if the clients is connected!!!!!!*********

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

            Function_Click();
            Function_callProxy();

            mMap.setMyLocationEnabled(true);
            LocationAvailability locationAvailability =
                    LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
            if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
                // 3
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                // 4
                if (mLastLocation != null) {
                    LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation
                            .getLongitude());
                    placeMarkerOnMap(currentLocation);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
                }
            }
    }//END of SetUpMap!!!!!!!!!!!!!!!!!!!!!!!!!!!


    private void Function_Click(){
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                View mWindow;
//                mWindow = LayoutInflater.from(MapsActivity.this).inflate(R.layout.custom_info_window, null);
//                //Toast.makeText(getApplicationContext(),"The Marker is Clicked!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
//                Button button = (Button) mWindow.findViewById(R.id.YESbtnID);
//                button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                //Toast.makeText(getApplicationContext(),"The Marker is Clicked!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
//                Log.i("MyApp","****************************");
//                }
//            });
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                MessageFragment dialog = new MessageFragment();
                dialog.show(manager, "MEssgaDialog");

                Log.i("MyApp","showed the dialog");
                return false;
            }
        });
    }

    private void Function_callProxy(){
        Call<List<Group>> caller = proxy.listGroups();
        ServerProxyBuilder.callProxy(MapsActivity.this, caller, this::response);
    }


    private void response(List<Group> returnedGroups) {
        Log.w("Register Server", "*********************** " + returnedGroups.toString());
        for(int i =0; i < returnedGroups.size(); i++)
        {
            Group temp = returnedGroups.get(i);
            groups.add(temp);
        }
        Log.w("Register Server", "*********************** " + returnedGroups.toString());
    }


    @Override
    public void onLocationChanged(Location location) {

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setUpMap();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        Toast.makeText(getApplicationContext(),"The Marker is Clicked!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
//        return false;
//    }



    //function for placing a marker on the map based on its latitude and longtitude
    protected void placeMarkerOnMap(LatLng location) {
        // 1
        MarkerOptions markerOptions = new MarkerOptions().position(location);
        // 2
        mMap.addMarker(markerOptions);
    }
    public static Intent newIntent(Context context){
        return new Intent(context, MapsActivity.class);
    }
}

