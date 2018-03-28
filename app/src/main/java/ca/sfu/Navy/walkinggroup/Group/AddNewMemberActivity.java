package ca.sfu.Navy.walkinggroup.Group;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class AddNewMemberActivity extends AppCompatActivity {
    private static final String id = "groupId";
    private ServerProxy proxy;
    private User user_temp = new User();
    EditText email_edit;
    private Long groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);
        String token = SavedSharedPreference.getPrefUserToken(AddNewMemberActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        extractDataFromIntent();
        setupEditText();
        setUpAddButton();
        setUpCancelButton();
    }

    private void getUserId() {
        String email = email_edit.getText().toString();

        Call<User> caller = proxy.getUserByEmail(email);
        ServerProxyBuilder.callProxy(AddNewMemberActivity.this, caller, returnedUser -> response(returnedUser));
    }

    private void response(User user){
        Log.w("Register Server", "Server replied with user: " + user.toString());
        user_temp = user;
    }

    private void setUpAddButton() {
        Button button = (Button) findViewById(R.id.add_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Group> caller = proxy.addNewGroupMember(groupId, user_temp);
                ServerProxyBuilder.callProxy(AddNewMemberActivity.this, caller, returnedGroup -> response(returnedGroup));
            }
        });
    }

    private void response(Group group){
        Log.w("Register Server", "Server replied with: " + group.toString());
        Toast.makeText(getApplicationContext(),
                "Add new Member Successfully",
                Toast.LENGTH_LONG)
                .show();
        finish();
    }

    private void setUpCancelButton() {
        Button button = (Button) findViewById(R.id.cancel_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupEditText(){
        email_edit = (EditText) findViewById(R.id.edit_email);
        
        email_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(checkEmail(s.toString()) == true){
                    getUserId();
                }
            }
        });
    }

    private static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        groupId = intent.getLongExtra(id, 0);
    }

    public static Intent intent(Context context, Long groupId){
        Intent intent = new Intent(context, AddNewMemberActivity.class);
        intent.putExtra(id, groupId);
        return intent;
    }
}
