package ca.sfu.Navy.walkinggroup;

import android.Manifest;
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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import ca.sfu.Navy.walkinggroup.model.GpsLocation;

import retrofit2.Call;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        /*GoogleMap.OnMarkerClickListener,*/ com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    private Location mLastLocation;
    private ServerProxy proxy;
    private List<Group> List_groups = new ArrayList<>();
    private List<LatLng> List_startingLocations = new ArrayList<>();
    private User user_login = new User();
    private LatLng marker_clicked;
    private long groupID;
    private boolean check = false;
    private boolean paused = false;
    private Button btn;
    private LatLng Destination = new LatLng(49.287586, -123.113560);
    private int tool = 0;
    private Button parentActivity_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.parentMap);
        mapFragment.getMapAsync(this);
        String token = SavedSharedPreference.getPrefUserToken(MapsActivity.this);
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

        locationRequest = new LocationRequest();
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(15 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        btn = findViewById(R.id.bottonID);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseButtonClicked();
            }
        });

        parentActivity_btn = findViewById(R.id.ParentButtonID);
        parentActivity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ParentActivity.newIntent(MapsActivity.this);
                startActivity(intent);
            }
        });
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
    protected void onResume() {
        super.onResume();
        Resume();
    }

    private void Resume() {
        if (mGoogleApiClient.isConnected()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        Pause();
    }

    private PendingResult<Status> Pause() {
        Log.i("MyApp","PAUSE PAUSE PAUSE PAUSE PAUSE PAUSE PAUSE");

        return LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
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




        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);

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

        //
        Function_callProxy();
        Function_Click();
        //Join Group preparation
        //Get current user info


    }//END of SetUpMap!!!!!!!!!!!!!!!!!!!!!!!!!!!

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        finish();
    }


    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 100) {
//            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                return;
//            } else {
//                finish();
//            }
//        }
//    }

    private void Function_Click(){

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker_clicked = marker.getPosition();
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
        ServerProxyBuilder.callProxy(MapsActivity.this, caller, returnedGroups -> response(returnedGroups));
    }

    private void Function_getUserInfo() {
        String email = SavedSharedPreference.getPrefUserEmail(MapsActivity.this);
        // Make call to retrieve user info
        Call<User> caller = proxy.getUserByEmail(email);
        ServerProxyBuilder.callProxy(MapsActivity.this, caller, returnedUser -> response(returnedUser));
    }

    private void response(User user){
        Log.w("Register Server", "Server replied with user: " + user.toString());
        user_login = user;
    }

    private void response(List<Group> returnedGroups) {
//        Log.i("Register Server", "*********************** " + returnedGroups.toString());
        List_groups = returnedGroups;
        Response_updateList();
        Response_showGroups();
    }

    private void Response_updateList(){
        double ini_Lat;
        double ini_Lng;
        LatLng coord;
        for(Group group: List_groups){
            if(group.getRouteLatArray().isEmpty() || group.getRouteLngArray().isEmpty()){
                coord = new LatLng(0.0, 0.0);
                List_startingLocations.add(coord);
            }
            else{
                ini_Lat = (double) group.getRouteLatArray().get(0);
                ini_Lng = (double) group.getRouteLngArray().get(0);
                Log.i("MyApp","！！！！！！$$$$$$$$$$$！！！！！" + ini_Lat);
                Log.i("MyApp","！！！！！$$$$$$$$$$$！！！！！" + ini_Lng);

                coord = new LatLng(ini_Lat, ini_Lng);
                List_startingLocations.add(coord);
            }

        }
    }

    private void Response_showGroups(){
        for (int i = 0; i < List_startingLocations.size(); i++){
            placeMarkerOnMap(List_startingLocations.get(i));

        }
    }

//    public static void joinGroup(){
//        public static Intent newIntent(Context context){
//    }

    public long getGroupIDByLocation(LatLng location){
        //long temp = -1;

        for (int i = 0; i < List_startingLocations.size(); i++){
            if (List_startingLocations.get(i).latitude == location.latitude && List_startingLocations.get(i).longitude == location.longitude){
                return List_groups.get(i).getId();
            }
        }
        return -1;
    }

    public User getCurrentUser(){
        return user_login;
    }
    public LatLng getMarkerLocation(){
        return marker_clicked;
    }
    public void join_group(){
        groupID = getGroupIDByLocation(marker_clicked);
        Call<Group> caller = proxy.addNewGroupMember(groupID, user_login);
        ServerProxyBuilder.callProxy(MapsActivity.this, caller, returnedGroup -> response(returnedGroup));
    }
    private void response(Group group){
        Log.i("MyApp","SUCCESSFULLY JOIN THE GROUP!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Toast.makeText(getApplicationContext(),
                "SUCCESSFULLY JOIN THE GROUP!!!!!!!!!!!!!!!!!!!!!!!!!!",
                Toast.LENGTH_LONG)
                .show();
    }

    public void pauseButtonClicked(){
        if(paused == false){
            Pause();
            paused = true;
            Toast.makeText(getApplicationContext(),
                    "Paused tracking",
                    Toast.LENGTH_SHORT)
                    .show();
            btn.setText("Resume tracking Servive");
        }else{
            Resume();;
            paused = false;
            Toast.makeText(getApplicationContext(),
                    "Resumed tracking",
                    Toast.LENGTH_SHORT)
                    .show();
            btn.setText("Pause tracking Servive");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getApplicationContext(),
                "Changed location!!!!!!!!!!!!!!!!!!!!!!!!!!" + location,
                Toast.LENGTH_LONG)
                .show();

        double Location_Lat = location.getLatitude();
        double Location_Lng = location.getLongitude();
        double Destination_Lat = Destination.latitude;
        double Destination_Lng = Destination.longitude;

        Location_Lat = roundThreeDecimals(Location_Lat);
        Destination_Lat = roundThreeDecimals(Destination_Lat);

        Location_Lng = roundThreeDecimals(Location_Lng);
        Destination_Lng = roundThreeDecimals(Destination_Lng);

        if (Location_Lat == Destination_Lat && Location_Lng == Destination_Lng){

            Log.i("MyApp","Arrived Arrived!!!!!!!!!!!!!!!!!!!!!!!!!!");


            tool++;

        }
        if(tool == 21){ //purpose of waiting for 10 minutes, i.e 20 round of 30secs
//            EndTime = Calendar.getInstance().getTime();
//            int temp = EndTime.getMinutes() + 1;
//            EndTime.setMinutes(temp);
            Pause();
            tool = 0;
        }
        if(user_login.getId() != null){

            Double a = location.getLatitude();
            Double b = location.getLongitude();
            Date c = Calendar.getInstance().getTime();
            GpsLocation temp = new GpsLocation();
            temp.setLat(a);
            temp.setLng(b);
            temp.setTimestamp(c);
            Log.i("MyApp","&&&&" + a);
            Log.i("MyApp","&&&&" + b);
            Long userID = user_login.getId();
            Log.i("MyApp","END OF SETTING USERRR");
            Log.i("MyApp","STARTS ENVOCING CALLPROXY");

            Call<GpsLocation> caller = proxy.uploadGps(userID, temp);
            ServerProxyBuilder.callProxy(MapsActivity.this, caller, returnedLocation -> response2(returnedLocation));
        }
    }

    private void response2(GpsLocation location) {
        Log.i("MyApp","@@@@@@@@@@@@@@@@@@@@@@@@%%%%%%%%%%%%%%%" + location.getLat());
        Log.i("MyApp","@@@@@@@@@@@@@@@@@@@@@@@@%%%%%%%%%%%%%%%" + location.getLng());

    }

    private double roundThreeDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.###");
        return Double.valueOf(twoDForm.format(d));
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

