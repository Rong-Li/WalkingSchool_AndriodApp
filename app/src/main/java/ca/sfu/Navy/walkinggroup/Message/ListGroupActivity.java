package ca.sfu.Navy.walkinggroup.Message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.adapter.GroupListAdapter;
import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import retrofit2.Call;

public class ListGroupActivity extends AppCompatActivity {

    private ListView mGroupList;
    private ServerProxy mProxy;
    private Long mId;
    private GroupListAdapter mGroupListAdapter;
    private ArrayList<Group> groups;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        init();
    }

    private void init() {
        this.groups = new ArrayList<>();
        String token = SavedSharedPreference.getPrefUserToken(ListGroupActivity.this);
        mProxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);
        mGroupList = findViewById(R.id.group_list);
        mId = SavedSharedPreference.getPrefUserId(this);
        mGroupListAdapter = new GroupListAdapter(this);
        mGroupList.setAdapter(mGroupListAdapter);

        // Make call
        Call<List<Group>> caller = mProxy.listGroups();
        ServerProxyBuilder.callProxy(ListGroupActivity.this, caller, this::response);
    }

    private void response(List<Group> groups) {
        this.groups = new ArrayList<>();
        for (Group group : groups) {
            if (!group.getLeader().getId().equals(mId))
                continue;
            this.groups.add(group);
        }
        Log.e("test",this.groups.size()+"");
        mGroupListAdapter.updateData(this.groups);
    }
}
