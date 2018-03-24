package ca.sfu.Navy.walkinggroup.Group;

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
import ca.sfu.Navy.walkinggroup.adapter.GroupListAdapter;
import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class ListGroupAsParentActivity extends AppCompatActivity {
    private ServerProxy proxy;
    private User user_id;
    private Group temp_group;
    private ListView listView;
    private GroupListAdapter mAdapter;
    private int index = 0;
    private List<User> monitorList = new ArrayList<>();
    private List<Group> groupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group_as_parent);
        setContentView(R.layout.activity_list_group_as_member);
        String token = SavedSharedPreference.getPrefUserToken(ListGroupAsParentActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        asLeaderButton();
        asMemberButton();
        getUserId();
    }

    private void asLeaderButton(){
        Button button = (Button) findViewById(R.id.leader_list_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ListGroupAsLeaderActivity.intent(ListGroupAsParentActivity.this);
                finish();
                startActivity(intent);
            }
        });
    }

    private void asMemberButton(){
        Button button = (Button) findViewById(R.id.member_list_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ListGroupAsMemberActivity.intent(ListGroupAsParentActivity.this);
                finish();
                startActivity(intent);
            }
        });
    }

    private void getUserId(){
        String email = SavedSharedPreference.getPrefUserEmail(ListGroupAsParentActivity.this);
        // Make call to retrieve user info
        Call<User> caller = proxy.getUserByEmail(email);
        ServerProxyBuilder.callProxy(ListGroupAsParentActivity.this, caller, returnedUser -> response(returnedUser));
    }

    private void response(User user) {
        Log.w("Register Server", "Server replied with user: " + user.toString());
        user_id = user;
        Log.w("Test receive", "server receive test " + user_id.toString());
        // Create list of item
        monitorList = user_id.getMonitorsUsers();
        updateList();
    }

    private void updateList(){
        for(User user: monitorList){
            getGroupByUserId(user.getId());
        }
    }

    private void getGroupByUserId(Long userId) {
        Call<User> caller = proxy.getUserById(userId);
        ServerProxyBuilder.callProxy(ListGroupAsParentActivity.this, caller, returnedUser -> responses(returnedUser));
    }

    private void responses(User user) {
        Log.w("Register Server", "Server replied with user: " + user.toString());
        user_id = user;
        Log.w("Test receive", "server receive test " + user_id.toString());
        // Create list of item
        monitorList = user_id.getMonitorsUsers();
        updateListagain();
    }

    private void updateListagain() {
        for(Group group: groupList){
            getGroupById(group.getId());
        }
    }

    private void getGroupById(Long id) {
        Call<Group> caller = proxy.getGroupDetails(id);
        ServerProxyBuilder.callProxy(ListGroupAsParentActivity.this, caller, returnedGroup -> response(returnedGroup));
    }

    private void response(Group returnedGroup){
        temp_group = returnedGroup;
        Log.w("Test receive", "server receive test " + temp_group.toString());
        updateGroupList(index, temp_group);
        index++;
    }

    private void updateGroupList(int position, Group group){
        if(position >= groupList.size()){
            return;
        }
        groupList.set(position, group);
        populateList();
    }

    private void populateList(){
        // Configure the list view
        listView = (ListView) findViewById(R.id.group_leader_list);
        // Build Adapter
        mAdapter = new GroupListAdapter(ListGroupAsParentActivity.this, groupList);
        listView.setAdapter(mAdapter);
        clickCallBack();
    }

    private void clickCallBack(){
        listView = (ListView) findViewById(R.id.group_leader_list);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Group group = groupList.get(position);
                Long groupId = group.getId();
                String groupDescription = group.getGroupDescription();
                Intent intent = MemberListActivity.intent(ListGroupAsParentActivity.this, groupId, groupDescription);
                startActivity(intent);
            }
        });
    }

    public static Intent intent(Context context){
        return new Intent(context, ListGroupAsParentActivity.class);
    }
}
