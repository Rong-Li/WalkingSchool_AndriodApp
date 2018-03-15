package ca.sfu.Navy.walkinggroup.monitor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class AddMonitorActivity extends AppCompatActivity {

    private ServerProxy proxy;
    private ListView listView;
    private UserListAdapter mAdapter;
    List<User> allUsers = new ArrayList<>();
    private User user_loggedin = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monitor);
        // Build the server proxy with token
        String token = SavedSharedPreference.getPrefUserToken(AddMonitorActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        getUserLoggedin();
    }

    private void populateList(){
        // Configure the list view
        listView = (ListView) findViewById(R.id.all_userList2);

        // Build Adapter
        mAdapter = new UserListAdapter(AddMonitorActivity.this, allUsers);
        listView.setAdapter(mAdapter);
        clickCallSetting();

    }

    private void clickCallSetting(){
        listView = (ListView) findViewById(R.id.all_userList2);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View viewclicked, int position, long id) {
                User user = allUsers.get(position);
                long userId = user_loggedin.getId();
                // Make call to add
                Call<List<User>> caller = proxy.addUsertoMonitor(userId, user);
                ServerProxyBuilder.callProxy(AddMonitorActivity.this, caller, returnedUsers -> response(returnedUsers));
                finish();
            }
        });

    }


    private void getAllUser(){
        // Make call to server for all User
        Call<List<User>> caller = proxy.getUsers();
        ServerProxyBuilder.callProxy(AddMonitorActivity.this, caller, returnedUsers -> response(returnedUsers));
    }

    private void response(List<User> returnedUsers){
        allUsers = returnedUsers;
        Log.w("Register Server", "Server replied with user: " + allUsers.toString());
        populateList();
    }

    private void getUserLoggedin(){
        // Encode user email
        String email = SavedSharedPreference.getPrefUserEmail(AddMonitorActivity.this);
        // Make call to retrieve user info
        Call<User> caller = proxy.getUserByEmail(email);
        ServerProxyBuilder.callProxy(AddMonitorActivity.this, caller, returnedUser -> response(returnedUser));
    }

    private void response(User user) {
        Log.w("Register Server", "Server replied with user: " + user.toString());
        user_loggedin = user;
        getAllUser();
    }


    public static Intent makeIntent(Context context){
        return new Intent(context, AddMonitorActivity.class);
    }
}
