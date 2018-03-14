package ca.sfu.Navy.walkinggroup.group;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class GroupActivity extends AppCompatActivity {
    private ServerProxy proxy;
    private EditText groupDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), null);

        setupCreateNewGroupbtn();
        setupEditText();
    }

    private void setupEditText(){
        groupDescription = (EditText) findViewById(R.id.groupdescription_txt);
    }

    private void setupCreateNewGroupbtn() {
        Button btn = findViewById(R.id.btnLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build new user
                Group group = new Group();
                Long leader;
                String desctiption = groupDescription.getText().toString();


                // Register for token received:
                ServerProxyBuilder.setOnTokenReceiveCallback( token -> onReceiveToken(token));

                Call<User> caller = proxy.getUserByEmail(SavedSharedPreference.getUserEmail());
                ServerProxyBuilder.callProxy(GroupActivity.this, caller, returnedUser -> response(returnedUser));

                // Make call
                Call<Group> callerg = proxy.creatNewGroup(leader, desctiption);
                ServerProxyBuilder.callProxy(GroupActivity.this, caller, returnedNothing -> response(returnedNothing));
            }
        });
    }



    private void onReceiveToken(String token) {
        // Replace the current proxy with one that uses the token!
        Log.w(TAG, "   --> NOW HAVE TOKEN: " + token);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);
    }

    private void response(Void returnedNothing) {
        startActivity(i);
        Log.w(TAG, "Server replied to login request (no content was expected).");
    }

    private void response(User user) {
        Log.w(TAG, "Server replied with user: " + user.toString());
        leader = user.getId();
    }

    public static Intent newIntent(Context context){
        return new Intent(context, GroupActivity.class);
    }
}
