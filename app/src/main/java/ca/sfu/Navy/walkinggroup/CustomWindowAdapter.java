package ca.sfu.Navy.walkinggroup;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by lirongl on 2018-03-13.
 */

public class CustomWindowAdapter implements GoogleMap.InfoWindowAdapter {


    private final View mWindow;
    private Context mContext;

    public CustomWindowAdapter(Context mContext) {
        this.mContext = mContext;
        this.mWindow = LayoutInflater.from(mContext).inflate(R.layout.custom_info_window, null);
    }


    private void rendowWindowText(Marker marker, View view){
//        Button button = (Button) findViewById(R.id.YESbtnID);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("MyApp","****************************");
//            }
//        });
        Log.i("MyApp","****************************");
    }
    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}