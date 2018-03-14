package ca.sfu.Navy.walkinggroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.adapter.UserListAdapter;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class MonitorActivity extends AppCompatActivity {

    private User user_loggedin = new User();
    private ServerProxy proxy;
    private ListView listView;
    private UserListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        // Build the server proxy with token
        String token = SavedSharedPreference.getPrefUserToken(MonitorActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        getUserLoggedin();
        addMonitorUser();
    }

    private void populateList(){

        // Create list of item
        List<User> monitorList = new ArrayList<>();
        monitorList = user_loggedin.getMonitorsUsers();
        // Configure the list view
        listView = (ListView) findViewById(R.id.monitor_userlist);

        // Build Adapter
        mAdapter = new UserListAdapter(MonitorActivity.this, monitorList);

        listView.setAdapter(mAdapter);
    }

    private void addMonitorUser(){
        Button button = (Button) findViewById(R.id.add_monitor_user);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddMonitorActivity.makeIntent(MonitorActivity.this);
                startActivity(intent);
            }
        });
    }

    private void getUserLoggedin(){
        String email = SavedSharedPreference.getPrefUserEmail(MonitorActivity.this);

        // Make call to retrieve user info
        Call<User> caller = proxy.getUserByEmail(email);
        ServerProxyBuilder.callProxy(MonitorActivity.this, caller, returnedUser -> response(returnedUser));

    }

    private void response(User user) {
        Log.w("Register Server", "Server replied with user: " + user.toString());
        user_loggedin = user;
        Log.w("Test receive", "server receive test " + user_loggedin.toString());
        populateList();

    }

    public static Intent newIntent(Context context){
        return new Intent(context, MonitorActivity.class);
    }

}
