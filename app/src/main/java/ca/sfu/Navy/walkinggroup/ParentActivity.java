package ca.sfu.Navy.walkinggroup;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.sfu.Navy.walkinggroup.adapter.CustomWindowAdapter;
import ca.sfu.Navy.walkinggroup.model.MyThemeUtils;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import ca.sfu.Navy.walkinggroup.model.GpsLocation;

import retrofit2.Call;


public class ParentActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        /*GoogleMap.OnMarkerClickListener,*/ com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private Location mLastLocation;
    private ServerProxy proxy;
    private User user_login = new User();
    private LatLng marker_clicked;
    private Button btn;
    private LatLng Destination = new LatLng(49.287586, -123.113560);
    private int tool = 0;
    private Date EndTime;
    private List<User> List_children = new ArrayList<>();
    private List<GpsLocation> List_childrenLocations = new ArrayList<>();
    private GpsLocation default_location = new GpsLocation();
    private User user_onPurpose = new User();
    private int index = 0;
    private User user_clicked = new User();

    private MyThemeUtils.Theme currentTheme;

    private void initTheme() {
        MyThemeUtils.Theme theme = MyThemeUtils.getCurrentTheme(this);
        currentTheme = theme;
        MyThemeUtils.changTheme(this, theme);
    }

    public void checkTheme() {
        MyThemeUtils.Theme theme = MyThemeUtils.getCurrentTheme(this);
        if (currentTheme == theme)
            return;
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkTheme();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(R.layout.activity_parent);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.parentMap);
        mapFragment.getMapAsync(this);
        String token = SavedSharedPreference.getPrefUserToken(ParentActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        Function_getUserInfo();
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

        default_location.setLat(0.0);
        default_location.setLng(0.0);

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
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);


    }

    //checks if the app has been granted the ACCESS_FINE_LOCATION permission.//////******main purpose
    //If it hasn’t, then request it from the user.
    //it is called by onconnected() function, and onconnected() is called if the clients is connected!!!!!!*********
    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;

        }


        LocationAvailability locationAvailability =
                LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
            // 3
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            // 4
            if (mLastLocation != null) {
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation
                        .getLongitude());
                //placeMarkerOnMap(currentLocation);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
            }
        }
        mMap.setMyLocationEnabled(true);
        //mMap.setInfoWindowAdapter(new CustomWindowAdapter(ParentActivity.this));
        //
        Function_Click();
        //Join Group preparation
        //Get current user info


    }//END of SetUpMap!!!!!!!!!!!!!!!!!!!!!!!!!!!



    private void Function_Click(){

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker_clicked = marker.getPosition();
                for (int i = 0; i < List_children.size(); i++){
                    if (List_children.get(i).getLastGpsLocation().getLat() == marker_clicked.latitude && List_children.get(i).getLastGpsLocation().getLng() == marker_clicked.longitude){
                        user_clicked = List_children.get(i);
                    }
                }
                if (user_clicked.getName() == null){
                    Toast.makeText(getApplicationContext(),
                            "Didn't find the child",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                else{
//                    TextView text1 = findViewById(R.id.textViewView1);
//                    text1.setText(user_clicked.getName());
                    TextView text2 = findViewById(R.id.textViewView2);
                    text2.setText(user_clicked.getLastGpsLocation().getTimestamp().toString());

                }

                return false;
            }
        });
    }


    public User getUserClicked_forFragment(){
        return user_clicked;
    }
    private void Function_getUserInfo() {
        String email = SavedSharedPreference.getPrefUserEmail(ParentActivity.this);
        // Make call to retrieve user info
        Call<User> caller = proxy.getUserByEmail(email);
        ServerProxyBuilder.callProxy(ParentActivity.this, caller, returnedUser -> response(returnedUser));
    }

    private void response(User user){
        Log.w("Register Server", "Server replied with user: " + user.getEmail());
        Log.w("Register Server", "Server replied with user: " + user.getMonitorsUsers());
        Log.w("Register Server", "Server replied with user: " + user.getMonitoredByUsers());
        List_children = user.getMonitorsUsers();

        response_updateList();
    }

    private void response_updateList(){
        for(User child: List_children) {
            updateUserDetail(child.getId());
        }
    }


    private void updateUserDetail(long id){
        Call<User> caller = proxy.getUserById(id);
        ServerProxyBuilder.callProxy(ParentActivity.this, caller, returnedUser -> response2(returnedUser));
    }
    private void response2(User returnedUser) {
        List_children.set(index, returnedUser);
        LatLng temp = new LatLng(returnedUser.getLastGpsLocation().getLat(), returnedUser.getLastGpsLocation().getLng());
        placeMarkerOnMap(temp, returnedUser.getName());
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



    //function for placing a marker on the map based on its latitude and longtitude
    protected void placeMarkerOnMap(LatLng location, String name) {
        // 1
        MarkerOptions markerOptions = new MarkerOptions().position(location);
        // 2
        mMap.addMarker(markerOptions.title(name));
    }
    public static Intent newIntent(Context context){
        return new Intent(context, ParentActivity.class);
    }
}

