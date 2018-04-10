package ca.sfu.Navy.walkinggroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import ca.sfu.Navy.walkinggroup.Group.ManageGroupActivity;
import ca.sfu.Navy.walkinggroup.adapter.LeaderBoardAdapter;
import ca.sfu.Navy.walkinggroup.model.MyThemeUtils;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    private ServerProxy proxy;

    private MyThemeUtils.Theme currentTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(R.layout.activity_main);
        UserCenterStart();
        MapActivityStart();
        logOutActivityStart();
        manageGroupActivityStart();
        leaderBoardActivityStart();
    }

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

    public void list_group(View view) {
        startActivity(new Intent(this, ListGroupActivity.class));
    }

    public void all_message(View view) {
        startActivity(new Intent(this, AllMessageActivity.class));
    }

    private void leaderBoardActivityStart() {
        Button button = (Button) findViewById(R.id.leaderboard_button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LeaderboardActivity.intent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void manageGroupActivityStart() {
        Button button = (Button) findViewById(R.id.managegroup_btn);
        button.setOnClickListener(new OnClickListener() {
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
        checkTheme();
    }

    private void checkLoggedIn() {
        if (SavedSharedPreference.getPrefUserEmail(MainActivity.this).length() == 0) {
            // call Login Activity
            Intent intent = LoginActivity.newIntent(MainActivity.this);
            startActivity(intent);
        } else {
            // Stay at the current activity.
            // Assume the JWT authorization token of the user is still valid
            String token = SavedSharedPreference.getPrefUserToken(MainActivity.this);
            proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);
            String email_user = SavedSharedPreference.getPrefUserEmail(MainActivity.this);
            Call<User> caller = proxy.getUserByEmail(email_user);
            ServerProxyBuilder.callProxy(MainActivity.this, caller, this::response);
        }
    }

    private void response(User user) {
        Log.w("Login Test", ": " + user.toString());
        Long userId = user.getId();
        SavedSharedPreference.setPrefUserId(MainActivity.this, userId);
    }

    private void UserCenterStart() {
        Button button = findViewById(R.id.userCenter_btn);
        button.setOnClickListener(v -> {
            Intent intent = ca.sfu.Navy.walkinggroup.UserManager.UserCenterActivity.makeIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    private void MapActivityStart() {
        Button button = findViewById(R.id.MapButtonID);
        button.setOnClickListener(view -> {
            Intent intent = MapsActivity.newIntent(MainActivity.this);
            startActivity(intent);
        });
    }

    private void logOutActivityStart() {
        Button button = findViewById(R.id.logout_btn);
        button.setOnClickListener(view -> {
            SavedSharedPreference.clearUserLogged(MainActivity.this);
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });
    }

    public void user_message(View view) {
        startActivity(new Intent(this, ListUserMessageActivity.class));
    }

    public void gamification(View view) {
        startActivity(new Intent(this, CustomerActivity.class));
    }
}
