package ca.sfu.Navy.walkinggroup.monitor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.adapter.UserListAdapter;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class MonitorActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADDUSER = 301;
    private User user_loggedin = new User();
    private User tmp_user = new User();
    private ServerProxy proxy;
    private ListView listView;
    private UserListAdapter mAdapter;
    private List<User> monitorList = new ArrayList<>();
    private int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        // Build the server proxy with token
        String token = SavedSharedPreference.getPrefUserToken(MonitorActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        getUserLoggedin();
        addMonitorUser();
        addSwapMonitored();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE_ADDUSER:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
        }
    }

    private void updateList(){
        for(User user: monitorList){
            getUserById(user.getId());
        }
    }

    private void getUserById(long id){
        //Make call to retrieve User info
        Call<User> caller = proxy.getUserById(id);
        ServerProxyBuilder.callProxy(MonitorActivity.this, caller, returnedUser -> response2(returnedUser));

    }
    private void response2(User returnedUser){
        tmp_user = returnedUser;
        Log.w("Test receive", "server receive test " + tmp_user.toString());
        updateUserList(position, tmp_user);
        position++;
    }
    private void updateUserList(int position, User user){
        if(position >= monitorList.size()){
            return;
        }
        monitorList.set(position, user);
        populateList();
    }

    private void populateList(){
        // Configure the list view
        listView = (ListView) findViewById(R.id.monitoredBy_userlist);
        // Build Adapter
        mAdapter = new UserListAdapter(MonitorActivity.this, monitorList);
        listView.setAdapter(mAdapter);
        clickCallSetting();
    }

    private void clickCallSetting(){
        listView = (ListView) findViewById(R.id.monitoredBy_userlist);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View viewclick, int position, long id) {
                User user = monitorList.get(position);
                long monitorId = user_loggedin.getId();
                long monitoredId = user.getId();
                // Make call to delete
                Call<Void> caller = proxy.cancelmonitor(monitorId, monitoredId);
                ServerProxyBuilder.callProxy(MonitorActivity.this, caller, returnedNothing -> response(returnedNothing));
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }
    private void response(Void returnedNothing){
        // No content
        getUserLoggedin();
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
        // Create list of item
        monitorList = user_loggedin.getMonitorsUsers();
        updateList();
    }

    private void addMonitorUser(){
        Button button = (Button) findViewById(R.id.add_monitor_user);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddMonitorActivity.makeIntent(MonitorActivity.this);
                startActivityForResult(intent, REQUEST_CODE_ADDUSER);
            }
        });
    }
    private void addSwapMonitored(){
        Button button = (Button) findViewById(R.id.btn_swapMonitored);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MonitoredbyActivity.newIntent(MonitorActivity.this);
                finish();
                startActivity(intent);
            }
        });
    }
    public static Intent newIntent(Context context){
        return new Intent(context, MonitorActivity.class);
    }

}
