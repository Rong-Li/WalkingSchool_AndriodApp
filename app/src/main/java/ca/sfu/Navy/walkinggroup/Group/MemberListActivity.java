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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.adapter.GroupListAdapter;
import ca.sfu.Navy.walkinggroup.adapter.UserListAdapter;
import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class MemberListActivity extends AppCompatActivity {
    private static final String id = "groupId";
    private static final String description = "group description";
    private ServerProxy proxy;
    private User temp_user;
    private ListView listView;
    private UserListAdapter mAdapter;
    private Long groupId;
    private String groupDescription;
    private int index = 0;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        String token = SavedSharedPreference.getPrefUserToken(MemberListActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        extractDataFromIntent();
        getGroupMemberId();
        TextView groupName = (TextView) findViewById(R.id.group_description_txt);
        groupName.setText(groupDescription);
        setUpAddMemberButton();
    }

    private void getGroupMemberId() {
        Call<Group> caller = proxy.getGroupDetails(groupId);
        ServerProxyBuilder.callProxy(MemberListActivity.this, caller, returnedGroup -> response(returnedGroup));
    }

    private void response(Group group) {
        Log.w("Test receive", "server receive test " + group.toString());
        userList = group.getMemberUsers();
        updateList();
    }

    private void updateList(){
        for(User user: userList){
            getUserById(user.getId());
        }
    }

    private void getUserById(Long id) {
        Call<User> caller = proxy.getUserById(id);
        ServerProxyBuilder.callProxy(MemberListActivity.this, caller, returnedUser -> response(returnedUser));
    }

    private void response(User user) {
        Log.w("Test receive", "server receive test " + user.toString());
        temp_user = user;
        updateUserList(index, temp_user);
        index++;
    }

    private void updateUserList(int index, User user) {
        if(index >= userList.size()){
            return;
        }
        userList.set(index, user);
        populateList();
    }

    private void populateList() {
        // Configure the list view
        listView = (ListView) findViewById(R.id.group_member_list);
        // Build Adapter
        mAdapter = new UserListAdapter(MemberListActivity.this, userList);
        listView.setAdapter(mAdapter);
        clickCallBack();
    }

    private void clickCallBack() {
        listView = (ListView) findViewById(R.id.group_member_list);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                User user = userList.get(position);
                Long userId = temp_user.getId();
                Intent intent = MemberInfo.intent(MemberListActivity.this, userId, groupId);
                startActivity(intent);
            }
        });
    }

    private void setUpAddMemberButton() {
        Button button = (Button) findViewById(R.id.add_member_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddNewMemberActivity.intent(MemberListActivity.this, groupId);
                startActivity(intent);
            }
        });
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        groupId = intent.getLongExtra(id, 0);
        groupDescription = intent.getStringExtra(description);
    }

    public static Intent intent(Context context, Long groupId, String groupDescription){
        Intent intent = new Intent(context, MemberListActivity.class);
        intent.putExtra(id, groupId);
        intent.putExtra(description, groupDescription);
        return intent;
    }
}
