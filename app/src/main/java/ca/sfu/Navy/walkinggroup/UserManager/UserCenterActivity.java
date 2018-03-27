package ca.sfu.Navy.walkinggroup.UserManager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ca.sfu.Navy.walkinggroup.R;

public class UserCenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
    }



    public static Intent makeIntent(Context context){
        return new Intent(context, UserCenterActivity.class);
    }
}
