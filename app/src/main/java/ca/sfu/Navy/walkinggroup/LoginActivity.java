package ca.sfu.Navy.walkinggroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import ca.sfu.Navy.walkinggroup.model.UserInfoStore;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {
    private UserInfoStore userInfoStore;
    private ServerProxy proxy;
    private EditText email_edit;
    private EditText pw_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Build the server proxy
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), null);

        setupEditText();
        setupSignupButton();
        setupLoginButton();

    }

    private void setupEditText(){
        email_edit = (EditText) findViewById(R.id.login_edit_email);
        pw_edit = (EditText) findViewById(R.id.login_edit_pw);
    }

    private void setupSignupButton(){
        Button signup = (Button) findViewById(R.id.login_btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SignUpActivity.makeRegisterIntent(LoginActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setupLoginButton(){
        Button login = (Button) findViewById(R.id.login_btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build new user
                User user = new User();
                String email = email_edit.getText().toString();
                String pw = pw_edit.getText().toString();
                user.setEmail(email);
                user.setPassword(pw);

                // Receive token
                ServerProxyBuilder.setOnTokenReceiveCallback( token -> onReceiveToken(token));

                // Make call
                Call<User> caller = proxy.login(user);
                ServerProxyBuilder.callProxy(LoginActivity.this, caller, returnedUser -> response(returnedUser));

            }
        });
    }

    private void onReceiveToken(String token) {
        // Replace the current proxy with one that uses the token!
        Log.w("Login Server", "   --> NOW HAVE TOKEN: " + token);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);
        userInfoStore.setToken(token);
    }

    private void response(User user) {
        Log.w("Login Server", "Server replied to login request (no content was expected).");
        String email = email_edit.getText().toString();
        String pw = pw_edit.getText().toString();
        long id = user.getId();
        String name = user.getName();
        String theEmail = user.getEmail();
        userInfoStore.setId(id);
        userInfoStore.setName(name);
        userInfoStore.setEmail(theEmail);
        SavedSharedPreference.setUserEmail(LoginActivity.this,email);
        SavedSharedPreference.setPrefUserPw(LoginActivity.this, pw);

        finish();
    }

    public static Intent newIntent(Context context){
        return new Intent(context, LoginActivity.class);
    }
}
