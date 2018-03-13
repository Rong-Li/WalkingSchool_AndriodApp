package ca.sfu.Navy.walkinggroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class MonitorActivity extends AppCompatActivity {

    private User user_loggedin;
    private ServerProxy proxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        // Build the server proxy with token
        String token = SavedSharedPreference.getPrefUserToken(MonitorActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        getUserLoggedin();
    }

    private void getUserLoggedin(){
        // Encode user email
        String email = SavedSharedPreference.getPrefUserEmail(MonitorActivity.this);
        String encode_email = email.substring(0, email.indexOf('@')) + "%40" + email.substring(email.indexOf('@') +1, email.length());

        // Make call to retrieve user info
        Call<User> caller = proxy.getUserByEmail(encode_email);
        ServerProxyBuilder.callProxy(MonitorActivity.this, caller, returnedUser -> response(returnedUser));

    }

    private void response(User user) {
        Log.w("Register Server", "Server replied with user: " + user.toString());
        user_loggedin.setId(user.getId());
        user_loggedin.setEmail(user.getEmail());
        user_loggedin.setPassword(user.getPassword());
        user_loggedin.setName(user.getName());
        user_loggedin.setHref(user.getHref());
        user_loggedin.setMonitorsUsers(user.getMonitorsUsers());
        user_loggedin.setMonitoredByUsers(user.getMonitoredByUsers());
        user_loggedin.setMemberOfGroups(user.getMemberOfGroups());

    }










    public static Intent newIntent(Context context){
        return new Intent(context, MonitorActivity.class);
    }

}
