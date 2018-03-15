package ca.sfu.Navy.walkinggroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.monitor.MonitorActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLoggedIn();
        MapActivityStart();
        logInActivityStart();
        logOutActivityStart();
        createGroupActivityStart();
        monitorActivityStart();
    }

    private void createGroupActivityStart() {
        Button button = (Button) findViewById(R.id.creatgroup_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateGroupActivity.newIntent(MainActivity.this);
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

    private void logInActivityStart() {
        Button button = (Button) findViewById(R.id.login_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.newIntent(MainActivity.this);
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

    private void monitorActivityStart(){
        Button button = (Button) findViewById(R.id.monitor_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MonitorActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }



}
