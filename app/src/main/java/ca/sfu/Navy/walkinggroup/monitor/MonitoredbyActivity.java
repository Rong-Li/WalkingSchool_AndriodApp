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

public class MonitoredbyActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADDUSER = 201;
    private User user_loggedin = new User();
    private User tmp_user = new User();
    private ServerProxy proxy;
    private ListView listView;
    private UserListAdapter mAdapter;
    private List<User> monitoredByList = new ArrayList<>();
    private int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoredby);
        // Build the server proxy with token
        String token = SavedSharedPreference.getPrefUserToken(MonitoredbyActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        getUserLoggedin();
        addMonitoredUser();
        addSwapMonitor();
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
        for(User user: monitoredByList){
            getUserById(user.getId());
        }
    }

    private void getUserById(long id){
        //Make call to retrieve User info
        Call<User> caller = proxy.getUserById(id);
        ServerProxyBuilder.callProxy(MonitoredbyActivity.this, caller, returnedUser -> response2(returnedUser));

    }
    private void response2(User returnedUser){
        tmp_user = returnedUser;
        Log.w("Test receive", "server receive test " + tmp_user.toString());
        updateUserList(position, tmp_user);
        position++;
    }
    private void updateUserList(int position, User user){
        if(position >= monitoredByList.size()){
            return;
        }
        monitoredByList.set(position, user);
        populateList();
    }

    private void populateList(){
        // Configure the list view
        listView = (ListView) findViewById(R.id.monitoredBy_userlist);
        // Build Adapter
        mAdapter = new UserListAdapter(MonitoredbyActivity.this, monitoredByList);
        listView.setAdapter(mAdapter);
        clickCallSetting();
    }

    private void clickCallSetting(){
        listView = (ListView) findViewById(R.id.monitoredBy_userlist);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View viewclick, int position, long id) {
                User user = monitoredByList.get(position);
                long monitoredId = user_loggedin.getId();
                long monitorId = user.getId();
                // Make call to delete
                Call<Void> caller = proxy.cancelMonitoredBy(monitoredId, monitorId);
                ServerProxyBuilder.callProxy(MonitoredbyActivity.this, caller, returnedNothing -> response(returnedNothing));
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }
    private void response(Void returnedNothing){
        // No content
    }

    private void getUserLoggedin(){
        String email = SavedSharedPreference.getPrefUserEmail(MonitoredbyActivity.this);
        // Make call to retrieve user info
        Call<User> caller = proxy.getUserByEmail(email);
        ServerProxyBuilder.callProxy(MonitoredbyActivity.this, caller, returnedUser -> response(returnedUser));
    }

    private void response(User user) {
        Log.w("Register Server", "Server replied with user: " + user.toString());
        user_loggedin = user;
        Log.w("Test receive", "server receive test " + user_loggedin.toString());
        // Create list of item
        monitoredByList = user_loggedin.getMonitoredByUsers();
        updateList();
    }

    private void addMonitoredUser(){
        Button button = (Button) findViewById(R.id.add_monitored_user);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddMonitoredbyActivity.makeIntent(MonitoredbyActivity.this);
                startActivityForResult(intent, REQUEST_CODE_ADDUSER);
            }
        });
    }
    private void addSwapMonitor(){
        Button button = (Button) findViewById(R.id.btn_swapMonitor);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MonitorActivity.newIntent(MonitoredbyActivity.this);
                finish();
                startActivity(intent);
            }
        });
    }
    public static Intent newIntent(Context context){
        return new Intent(context, MonitoredbyActivity.class);
    }

}
