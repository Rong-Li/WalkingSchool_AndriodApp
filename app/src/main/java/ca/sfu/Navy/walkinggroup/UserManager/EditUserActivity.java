package ca.sfu.Navy.walkinggroup.UserManager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import ca.sfu.Navy.walkinggroup.monitor.MonitorActivity;
import retrofit2.Call;

public class EditUserActivity extends AppCompatActivity {

    private static final String EDIT_USER_ID_TO_EDIT = "Edit User Activity - User id to Edit";
    private ServerProxy proxy;
    private User user_edited;
    private long userId;
    // Current user info textview
    private TextView current_user_name;
    private TextView current_user_email;
    private TextView current_user_birthYear;
    private TextView current_user_birthMonth;
    private TextView current_user_address;
    private TextView current_user_cellPhone;
    private TextView current_user_homePhone;
    private TextView current_user_grade;
    private TextView current_user_teacherName;
    private TextView current_user_emergencyContact;

    // User info edit text list
    private EditText edit_user_name;
    private EditText edit_user_email;
    private EditText edit_user_birthYear;
    private EditText edit_user_birthMonth;
    private EditText edit_user_address;
    private EditText edit_user_cellPhone;
    private EditText edit_user_homePhone;
    private EditText edit_user_grade;
    private EditText edit_user_teacherName;
    private EditText edit_user_emergencyContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        // Build the server proxy with token
        String token = SavedSharedPreference.getPrefUserToken(EditUserActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);
        // Call in serial
        getUserFromIntent();
    }

    private void getUserFromIntent(){
        Intent intent = getIntent();
        userId = intent.getLongExtra(EDIT_USER_ID_TO_EDIT, 0);
        // Make call to retrieve user info
        Call<User> caller = proxy.getUserById(userId);
        ServerProxyBuilder.callProxy(EditUserActivity.this, caller, returnedUser -> response(returnedUser));

    }

    private void response(User user) {
        Log.w("Register Server", "Server replied with user: " + user.toString());
        user_edited = user;
        Log.w("Test receive", "server receive test " + user_edited.toString());
        // Call in serial
        setupText();
    }

    private void setupText(){
        // Setup textView
        current_user_name = (TextView) findViewById(R.id.current_user_name);
        current_user_email = (TextView) findViewById(R.id.current_user_email);
        current_user_birthYear = (TextView) findViewById(R.id.current_user_birthYear);
        current_user_birthMonth = (TextView) findViewById(R.id.current_user_birthMonth);
        current_user_address = (TextView) findViewById(R.id.current_user_address);
        current_user_cellPhone = (TextView) findViewById(R.id.current_user_cellPhone);
        current_user_homePhone = (TextView) findViewById(R.id.current_user_homePhone);
        current_user_grade = (TextView) findViewById(R.id.current_user_grade);
        current_user_teacherName = (TextView) findViewById(R.id.current_user_teacherName);
        current_user_emergencyContact= (TextView) findViewById(R.id.current_user_emergencyContactInfo);
        // Enter user information into text
        current_user_name.setText(getString(R.string.user_name_is, user_edited.getName()));
        current_user_email.setText(getString(R.string.user_email_is, user_edited.getEmail()));
        current_user_birthYear.setText(getString(R.string.user_birthYear_is, user_edited.getBirthYear()));
        current_user_birthMonth.setText(getString(R.string.user_birthMonth_is,user_edited.getBirthMonth()));
        current_user_address.setText(getString(R.string.user_address_is, user_edited.getAddress()));
        current_user_cellPhone.setText(getString(R.string.user_cellPhone_is, user_edited.getCellPhone()));
        current_user_homePhone.setText(getString(R.string.user_homePhone_is, user_edited.getHomePhone()));
        current_user_grade.setText(getString(R.string.user_grade_is, user_edited.getGrade()));
        current_user_teacherName.setText(getString(R.string.user_teacherName_is, user_edited.getTeacherName()));
        current_user_emergencyContact.setText(getString(R.string.user_emergencyContactInfo_is, user_edited.getEmergencyContactInfo()));
        // Call in serial
        setupEdit();
    }

    private void setupEdit(){
        // setup EditText private field
        edit_user_name = (EditText) findViewById(R.id.edit_user_name);
        edit_user_email = (EditText) findViewById(R.id.edit_user_email);
        edit_user_birthYear = (EditText) findViewById(R.id.edit_user_birthYear);
        edit_user_birthMonth = (EditText) findViewById(R.id.edit_user_birthMonth);
        edit_user_address = (EditText) findViewById(R.id.edit_user_address);
        edit_user_cellPhone = (EditText) findViewById(R.id.edit_user_cellPhone);
        edit_user_homePhone = (EditText) findViewById(R.id.edit_user_homePhone);
        edit_user_grade = (EditText) findViewById(R.id.edit_user_grade);
        edit_user_teacherName = (EditText) findViewById(R.id.edit_user_teacherName);
        edit_user_emergencyContact = (EditText) findViewById(R.id.edit_user_emergencyContactInfo);
        // Call in serial
        setupSubmit();
    }

    private void setupSubmit(){
        Button button = (Button) findViewById(R.id.edit_submit_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new user
                User user_submit = user_edited;

                // check name
                if(TextUtils.isEmpty(edit_user_name.getText())){
                    // do nothing
                }
                else{
                    String new_name = edit_user_name.getText().toString();
                    user_submit.setName(new_name);
                }

                // check email
                if(TextUtils.isEmpty(edit_user_email.getText())){
                    // do nothing
                }
                else{
                    String new_email = edit_user_email.getText().toString();
                    user_submit.setEmail(new_email);
                }

                // check birthYear
                if(TextUtils.isEmpty(edit_user_birthYear.getText())){
                    // do nothing
                }
                else{
                    String new_birthYear = edit_user_birthYear.getText().toString();
                    int new_intBY = Integer.parseInt(new_birthYear);
                    user_submit.setBirthYear(new_intBY);
                }

                // check birthMonth
                if(TextUtils.isEmpty(edit_user_birthMonth.getText())){
                    // do nothing
                }
                else{
                    String new_birthMonth = edit_user_birthMonth.getText().toString();
                    int new_intBM = Integer.parseInt(new_birthMonth);
                    user_submit.setBirthMonth(new_intBM);
                }

                // check address
                if(TextUtils.isEmpty(edit_user_address.getText())){
                    // do nothing
                }
                else{
                    String new_address = edit_user_address.getText().toString();
                    user_submit.setAddress(new_address);
                }

                // check cellPhone
                if(TextUtils.isEmpty(edit_user_cellPhone.getText())){
                    // do nothing
                }
                else{
                    String new_cellPhone = edit_user_cellPhone.getText().toString();
                    user_submit.setCellPhone(new_cellPhone);
                }

                // check homePhone
                if(TextUtils.isEmpty(edit_user_homePhone.getText())){
                    // do nothing
                }
                else{
                    String new_homePhone = edit_user_homePhone.getText().toString();
                    user_submit.setHomePhone(new_homePhone);
                }

                // check grade
                if(TextUtils.isEmpty(edit_user_grade.getText())){
                    // do nothing
                }
                else{
                    String new_grade = edit_user_grade.getText().toString();
                    user_submit.setGrade(new_grade);
                }

                // check teacherName
                if(TextUtils.isEmpty(edit_user_teacherName.getText())){
                    // do nothing
                }
                else{
                    String new_teacherName = edit_user_teacherName.getText().toString();
                    user_submit.setTeacherName(new_teacherName);
                }

                // check emergencyContactInfo
                if(TextUtils.isEmpty(edit_user_emergencyContact.getText())){
                    // do nothing
                }
                else{
                    String new_emergencyContact = edit_user_emergencyContact.getText().toString();
                    user_submit.setEmergencyContactInfo(new_emergencyContact);
                }

                // User submit finalized, then make call
                Call<User> caller = proxy.editUser(userId, user_submit);
                ServerProxyBuilder.callProxy(EditUserActivity.this, caller, returnedUser -> response2(returnedUser));
            }
        });

    }

    private void response2(User user){
        Log.w("Register Server", "Server replied with user: " + user.toString());
        finish();
    }

    public static Intent makeIntent(Context context, long userId){
        Intent intent = new Intent(context, EditUserActivity.class);
        intent.putExtra(EDIT_USER_ID_TO_EDIT, userId);
        return intent;
    }
}
