package ca.sfu.Navy.walkinggroup.Message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.adapter.MessageUserListAdapter;
import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.MessageUser;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class ListUserMessageActivity extends AppCompatActivity {

    private ListView mUserList;
    private ServerProxy mProxy;
    private MessageUserListAdapter mAdapter;
    private ArrayList<MessageUser> messageUsers;
    private long mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_user_list);
        init();
    }

    private void init() {
        this.messageUsers = new ArrayList<>();
        mId = SavedSharedPreference.getPreUserId(this);
        String token = SavedSharedPreference.getPrefUserToken(ListUserMessageActivity.this);
        mProxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);
        mUserList = findViewById(R.id.user_list);
        mAdapter = new MessageUserListAdapter(this);
        mUserList.setAdapter(mAdapter);
        listLeader();
    }

    public void listLeader() {
        // Make call
        Call<List<Group>> caller = mProxy.listGroups();
        ServerProxyBuilder.callProxy(ListUserMessageActivity.this, caller, this::response);
    }

    private void response(List<Group> groups) {
        messageUsers = new ArrayList<>();
        MessageUser user;
        for (Group group : groups) {
            if (group.getLeader().getId().equals(mId))
                continue;
            long leader_id = group.getLeader().getId();
            String group_desc = group.getGroupDescription();
            user = new MessageUser(group_desc, leader_id, true);
            messageUsers.add(user);
        }
        listUser();
    }

    public void listUser() {
        // Make call
        Call<User> caller = mProxy.getUserByEmail(SavedSharedPreference.getPrefUserEmail(this));
        ServerProxyBuilder.callProxy(ListUserMessageActivity.this, caller, this::handleData);
    }

    private void handleData(User user) {
        MessageUser messageUser;
        for (User u : user.getMonitoredByUsers()) {
            long user_id = u.getId();
            messageUser = new MessageUser("", user_id, false);
            messageUsers.add(messageUser);
        }
        mAdapter.updateData(messageUsers);
    }
}
