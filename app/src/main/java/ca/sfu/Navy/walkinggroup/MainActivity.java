package ca.sfu.Navy.walkinggroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.sfu.Navy.walkinggroup.Group.ManageGroupActivity;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UserCenterStart();
        MapActivityStart();
        logOutActivityStart();
        manageGroupActivityStart();
    }

    private void manageGroupActivityStart() {
        Button button = (Button) findViewById(R.id.managegroup_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ManageGroupActivity.intent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLoggedIn();
    }

    private void checkLoggedIn(){
        if(SavedSharedPreference.getPrefUserEmail(MainActivity.this).length() == 0)
        {
            // call Login Activity
            Intent intent = LoginActivity.newIntent(MainActivity.this);
            startActivity(intent);
        }
        else
        {
            // Stay at the current activity.
            // Assume the JWT authorization token of the user is still valid
        }
    }

    private void UserCenterStart(){
        Button button = (Button) findViewById(R.id.userCenter_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ca.sfu.Navy.walkinggroup.UserManager.UserCenterActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void MapActivityStart() {
        Button button = (Button) findViewById(R.id.MapButtonID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MapsActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void logOutActivityStart(){
        Button button = (Button) findViewById(R.id.logout_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavedSharedPreference.clearUserLogged(MainActivity.this);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }





}
