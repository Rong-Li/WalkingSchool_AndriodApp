package ca.sfu.Navy.walkinggroup.Group;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class CreateGroupActivity extends AppCompatActivity {
    private static final String latt = "latitude";
    private static final String lngg = "longitude";
    private ServerProxy proxy;
    private User user_login = new User();
    private List<Double>groupLat = new ArrayList<>();
    private List<Double>groupLng = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        String token = SavedSharedPreference.getPrefUserToken(CreateGroupActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        extractDataFromIntent();
        getUserID();
        setupCreateNewGroupbtn();
    }

    private void setupCreateNewGroupbtn() {
        Button btn = (Button) findViewById(R.id.creatgroup_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build new group
                EditText groupDescription = findViewById(R.id.groupdescription_txt);
                String description = groupDescription.getText().toString();
                Group group = new Group();
                Long id = user_login.getId();
                String href = user_login.getHref();
                group.setGroupDescription(description);
                User leader = new User();
                leader.setId(id);
                leader.setHref(href);
                group.setLeader(leader);
                group.setRouteLatArray(groupLat);
                group.setRouteLngArray(groupLng);

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
        user_login = user;
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        groupLat.add(intent.getDoubleExtra(latt, 0));
        groupLng.add(intent.getDoubleExtra(lngg, 0));
    }

    public static Intent newIntent(Context context){
        return new Intent(context, CreateGroupActivity.class);
    }

    public static Intent intent(Context context, Double lat, Double lng){
        Intent intent = new Intent(context, MemberInfo.class);
        intent.putExtra(latt, lat);
        intent.putExtra(lngg, lng);
        return intent;
    }
}
