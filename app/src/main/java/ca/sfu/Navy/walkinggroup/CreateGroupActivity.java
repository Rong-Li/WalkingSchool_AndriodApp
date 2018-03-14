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
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import ca.sfu.Navy.walkinggroup.model.UserInfoStore;
import retrofit2.Call;

public class CreateGroupActivity extends AppCompatActivity {
    private ServerProxy proxy;
    private UserInfoStore userInfoStore;
    private EditText groupDescription;
    private EditText emailaddress;
    private long leader = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), null);

        setupCreateNewGroupbtn();
        setupEditText();
    }
    private void setupEditText(){
        groupDescription = (EditText) findViewById(R.id.groupdescription_txt);
    }

    private void setupCreateNewGroupbtn() {
        Button btn = findViewById(R.id.creatgroup_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build new user
                String desctiption = groupDescription.getText().toString();
                String email = emailaddress.getText().toString();

                // Register for token received:
                ServerProxyBuilder.setOnTokenReceiveCallback( token -> onReceiveToken(token));

                Call<User> caller = proxy.getUserByEmail(email);
                ServerProxyBuilder.callProxy(CreateGroupActivity.this, caller, returnedUser -> response(returnedUser));

                // Make call
                Call<Void> callergroup = proxy.creatNewGroup(leader, desctiption);
                ServerProxyBuilder.callProxy(CreateGroupActivity.this, callergroup, returnedNothing -> response(returnedNothing));
            }
        });
    }



    private void onReceiveToken(String token) {
        // Replace the current proxy with one that uses the token!
        Log.w("Server Test", "   --> NOW HAVE TOKEN: " + token);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);
    }

    private void response(Void returnedNothing) {
        Log.w("Server Test", "Server replied to Create New Group request (no content was expected).");
    }

    private void response(User user) {
        Log.w("Server Test", "Server replied with user: " + user.toString());
        leader = user.getId();
    }

    public static Intent newIntent(Context context){
        return new Intent(context, CreateGroupActivity.class);
    }
}
