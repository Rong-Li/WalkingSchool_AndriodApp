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
import ca.sfu.Navy.walkinggroup.monitor.AddMonitorActivity;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity {
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

    private void setupEditText() {
        email_edit = findViewById(R.id.login_edit_email);
        pw_edit = findViewById(R.id.login_edit_pw);
    }

    private void setupSignupButton() {
        Button signup = findViewById(R.id.login_btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SignUpActivity.makeRegisterIntent(LoginActivity.this);
                startActivity(intent);
            }
        });
    }

    private void setupLoginButton() {
        Button login = findViewById(R.id.login_btn_login);
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
                ServerProxyBuilder.setOnTokenReceiveCallback(token -> onReceiveToken(token));

                // Make call
                Call<Void> caller = proxy.login(user);
                ServerProxyBuilder.callProxy(LoginActivity.this, caller, returnedNothing -> response(returnedNothing));
            }
        });
    }

    private void onReceiveToken(String token) {
        // Replace the current proxy with one that uses the token!
        Log.w("Login Server", "   --> NOW HAVE TOKEN: " + token);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);
        SavedSharedPreference.setPrefUserToken(LoginActivity.this, token);
    }

    private void response(Void returnedNothing) {
        Log.w("Login Server", "Server replied to login request (no content was expected).");
        String email = email_edit.getText().toString();
        String pw = pw_edit.getText().toString();
        SavedSharedPreference.setUserEmail(LoginActivity.this, email);
        SavedSharedPreference.setPrefUserPw(LoginActivity.this, pw);
        Call<User> caller = proxy.getUserByEmail(email);
        ServerProxyBuilder.callProxy(LoginActivity.this, caller, returnedUser -> getUser(returnedUser));
    }

    private void getUser(User returnedUser) {
        SavedSharedPreference.setPrefUserId(this, returnedUser.getId());
        SavedSharedPreference.setPrefUserPw(this, returnedUser.getPassword());
        SavedSharedPreference.setUserEmail(this, returnedUser.getEmail());
        finish();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}
