package ca.sfu.Navy.walkinggroup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;

/**
 * Created by lirongl on 2018-03-13.
 */

public class MessageFragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.custom_info_window,null);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Log.i("MyApp","YOU CLICK THE YESYESYESYES");
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
