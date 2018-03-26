package ca.sfu.Navy.walkinggroup.Group;

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
import ca.sfu.Navy.walkinggroup.adapter.GroupListAdapter;
import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import retrofit2.Call;

public class UpdateGroupListActivity extends AppCompatActivity {
    private ServerProxy proxy;
    private Group group_list;
    private ListView listView;
    private GroupListAdapter mAdapter;
    List<Group>groupList = new ArrayList<>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_group_list);
        String token = SavedSharedPreference.getPrefUserToken(UpdateGroupListActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        setUpGroupList();
    }

    private void setUpGroupList() {
        Call<List<Group>> caller = proxy.listGroups();
        ServerProxyBuilder.callProxy(UpdateGroupListActivity.this, caller, returnedGroups -> response(returnedGroups));
    }

    private void response(List<Group> groups) {
        Log.w("Register Server", "Server replied with user: " + groups.toString());
        groupList = groups;
        updateList();
    }

    private void updateList(){
        for(Group group: groupList){
            getGroupById(group.getId());
        }
    }

    private void getGroupById(Long id){
        Call<Group> caller = proxy.getGroupDetails(id);
        ServerProxyBuilder.callProxy(UpdateGroupListActivity.this, caller, returnedGroup -> response(returnedGroup));
    }

    private void response(Group group){
        group_list = group;
        Log.w("Test receive", "server receive test " + group_list.toString());
        updateGroupList(index, group_list);
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
        listView = (ListView) findViewById(R.id.group_list);
        // Build Adapter
        mAdapter = new GroupListAdapter(UpdateGroupListActivity.this, groupList);
        listView.setAdapter(mAdapter);
        clickCallBack();
    }

    private void clickCallBack(){
        listView = (ListView) findViewById(R.id.group_list);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Group group = groupList.get(position);
                Long groupId = group.getId();
                Intent intent = UpdateGroupActivity.intent(UpdateGroupListActivity.this, groupId);
                startActivity(intent);
            }
        });
    }

    public static Intent intent(Context context){
        return new Intent(context, UpdateGroupListActivity.class);
    }
}
