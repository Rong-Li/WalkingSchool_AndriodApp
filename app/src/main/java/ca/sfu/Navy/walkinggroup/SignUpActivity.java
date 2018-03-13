package ca.sfu.Navy.walkinggroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class SignUpActivity extends AppCompatActivity {

    private EditText name_edit;
    private EditText email_edit;
    private EditText pw_edit;
    private ServerProxy proxy;
    private long UserID = 0;

    public static Intent makeRegisterIntent(Context context){
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Build the server proxy
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), null);

        setupEditText();
        setupRegisterButton();

    }

    private void setupEditText(){
        name_edit = (EditText) findViewById(R.id.signup_edit_name);
        email_edit = (EditText) findViewById(R.id.signup_edit_email);
        pw_edit = (EditText) findViewById(R.id.signup_edit_pw);
    }

    private void setupRegisterButton(){
        Button register = (Button) findViewById(R.id.signup_btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build new User
                User user = new User();
                String name = name_edit.getText().toString();
                String email = email_edit.getText().toString();
                String pw = pw_edit.getText().toString();
                user.setEmail(email);
                user.setName(name);
                user.setPassword(pw);

                // Make call
                Call<User> caller = proxy.createNewUser(user);
                ServerProxyBuilder.callProxy(SignUpActivity.this, caller, returnedUser -> response(returnedUser));

                finish();
            }
        });
    }

    private void response(User user) {
        Log.w("Register Server", "Server replied with user: " + user.toString());
        UserID = user.getId();
    }


}
