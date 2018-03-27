package ca.sfu.Navy.walkinggroup.UserManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class UserCenterActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHECK_EMAIL_CHANGED = 748;
    private ServerProxy proxy;
    private User user_loggedin;
    private long saved_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        // Build the server proxy with token
        String token = SavedSharedPreference.getPrefUserToken(UserCenterActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        getUserLoggedin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CHECK_EMAIL_CHANGED:
                getUserfromID();
                break;
        }
    }

    private void getUserLoggedin(){
        String email = SavedSharedPreference.getPrefUserEmail(UserCenterActivity.this);
        // Make call to retrieve user info
        Call<User> caller = proxy.getUserByEmail(email);
        ServerProxyBuilder.callProxy(UserCenterActivity.this, caller, returnedUser -> response(returnedUser));
    }

    private void response(User user) {
        Log.w("Register Server", "Server replied with user: " + user.toString());
        user_loggedin = user;
        Log.w("Test receive", "server receive test " + user_loggedin.toString());
        saved_id = user.getId();
        EditUserStart();
    }

    private void EditUserStart() {
        Button button = (Button) findViewById(R.id.edit_user_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long userId = saved_id;
                Intent intent = EditUserActivity.makeIntent(UserCenterActivity.this, userId);
                startActivityForResult(intent, REQUEST_CODE_CHECK_EMAIL_CHANGED);
            }
        });
    }

    private void getUserfromID(){
        // Make call with user Id
        Call<User> caller = proxy.getUserById(saved_id);
        ServerProxyBuilder.callProxy(UserCenterActivity.this, caller, returnedUser -> response2(returnedUser));
    }
    private void response2(User user){
        Log.w("Register Server", "Server replied with user: " + user.toString());
        SavedSharedPreference.setUserEmail(UserCenterActivity.this, user.getEmail());
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, UserCenterActivity.class);
    }
}
