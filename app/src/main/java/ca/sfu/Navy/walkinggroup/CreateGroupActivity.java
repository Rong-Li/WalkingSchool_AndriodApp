package ca.sfu.Navy.walkinggroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class CreateGroupActivity extends AppCompatActivity {
    private ServerProxy proxy;
    private User user_login = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        String token = SavedSharedPreference.getPrefUserToken(CreateGroupActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        getUserID();
        setupCreateNewGroupbtn();
    }

    private void setupCreateNewGroupbtn() {
        Button btn = findViewById(R.id.creatgroup_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build new group
                EditText groupDescription = findViewById(R.id.groupdescription_txt);
                String description = groupDescription.getText().toString();
                Group group = new Group();
                long id = user_login.getId();
                String href = user_login.getHref();
                group.setGroupDescription(description);
                User leader = new User();
                leader.setId(id);
                leader.setHref(href);
                group.setLeader(leader);

                // Make call
                Call<Group> caller = proxy.createNewGroup(group);
                ServerProxyBuilder.callProxy(CreateGroupActivity.this, caller, returnedGroup -> response(returnedGroup));
            }
        });
    }

    private void response(Group group) {
        Log.w("Server Test", "Server replied to Create New Group request:" + group.toString());
        try{
            Thread.currentThread().sleep(500);
        }catch(Exception e){}
        finish();
    }

    private void getUserID(){
        // Encode user email
        String email = SavedSharedPreference.getPrefUserEmail(CreateGroupActivity.this);
        // Make call to retrieve user info
        Call<User> caller = proxy.getUserByEmail(email);
        ServerProxyBuilder.callProxy(CreateGroupActivity.this, caller, returnedUser -> response(returnedUser));
    }

    private void response(User user){
        Log.w("Register Server", "Server replied with user: " + user.toString());
        SavedSharedPreference.setPrefUserId(CreateGroupActivity.this, user.getId());
        user_login = user;
    }

    public static Intent newIntent(Context context){
        return new Intent(context, CreateGroupActivity.class);
    }
}
