package ca.sfu.Navy.walkinggroup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.User;

/**
 * Created by lirongl on 2018-03-13.
 */

public class MessageFragment extends AppCompatDialogFragment {

    private LatLng location;
    private long groupID;
    private User user;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.custom_info_window,null);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Log.i("MyApp","YOU CLICK THE YESYESYESYES");
                        MapsActivity activity = (MapsActivity) getActivity();
                        location = activity.getMarkerLocation();
                        groupID = activity.getGroupIDByLocation(location);
                        user = activity.getCurrentUser();




                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Log.i("MyApp","YOU CLICK THE NONONONONONO");
                        break;
//                    case DialogInterface.BUTTON_NEUTRAL:
//                        Log.i("MyApp","YOU CLICK THE CANCELCANCELCANCEL");
//                        break
                }
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("Question")
                .setView(v)
                .setPositiveButton("join", listener)
                .setNegativeButton("NO", listener)

                .create();
    }
}
