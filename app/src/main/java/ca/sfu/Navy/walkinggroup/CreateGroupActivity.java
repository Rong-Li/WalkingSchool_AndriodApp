package ca.sfu.Navy.walkinggroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class CreateGroupActivity extends AppCompatActivity {
    private ServerProxy proxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), SavedSharedPreference.getPrefUserToken(CreateGroupActivity.this));

        setupCreateNewGroupbtn();
    }

    private void setupCreateNewGroupbtn() {
        Button btn = findViewById(R.id.creatgroup_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build new user
                Group group = new Group();

                EditText groupDescription = findViewById(R.id.groupdescription_txt);
                String desctiption = groupDescription.getText().toString();

                group.setGroupDescription(desctiption);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", SavedSharedPreference.getPrefUserId(CreateGroupActivity.this));

                JSONObject leader = new JSONObject(map);
                group.setLeader(leader);

                // Make call
                Call<Group> caller = proxy.createNewGroup(group);
                ServerProxyBuilder.callProxy(CreateGroupActivity.this, caller, returnedGroup -> response(returnedGroup));
            }
        });
    }

    private void onReceiveToken(String token) {
        // Replace the current proxy with one that uses the token!
        Log.w("Server Test", "   --> NOW HAVE TOKEN: " + token);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);
    }

    private void response(Group group) {
        Log.w("Server Test", "Server replied to Create New Group request:" + group.toString());
    }

    private void response(User user) {
        Log.w("Server Test", "Server replied with user: " + user.toString());
    }

    public static Intent newIntent(Context context){
        return new Intent(context, CreateGroupActivity.class);
    }
}
