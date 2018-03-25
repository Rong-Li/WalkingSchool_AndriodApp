package ca.sfu.Navy.walkinggroup.Group;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class MemberInfo extends AppCompatActivity {
    private static final String id = "userId";
    private static final String gid = "groupId";
    private ServerProxy proxy;
    private User user_info;
    private Long userId;
    private Long groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);
        String token = SavedSharedPreference.getPrefUserToken(MemberInfo.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        extractDataFromIntent();
        getUserInfo(userId);
        setUpOkButton();
        setUpRemoveButton();
    }

    private void setUserInfo(User user_info) {
        TextView userName = (TextView) findViewById(R.id.user_name_txt);
        TextView userBirth = (TextView) findViewById(R.id.user_birth_month_txt);
        TextView userAddress = (TextView) findViewById(R.id.user_address_txt);
        TextView userCellPhone = (TextView) findViewById(R.id.user_cellphone_txt);
        TextView userHomePhone = (TextView) findViewById(R.id.user_home_phone_txt);
        TextView userGrade = (TextView) findViewById(R.id.user_grade_txt);
        TextView userTeacher = (TextView) findViewById(R.id.user_teacher_txt);
        userName.setText(user_info.getName());
        userBirth.setText("Year: " + user_info.getBirthYear() + ", " + "Month: " + user_info.getBirthMonth());
        userAddress.setText(user_info.getAddress());
        userCellPhone.setText(user_info.getCellPhone());
        userHomePhone.setText(user_info.getHomePhone());
        userGrade.setText(user_info.getGrade());
        userTeacher.setText(user_info.getTeacherName());
    }

    private void setUpOkButton() {
        Button button = (Button) findViewById(R.id.ok_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpRemoveButton() {
        Button button = (Button) findViewById(R.id.remove_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> caller = proxy.removeGroupMember(groupId, userId);
                ServerProxyBuilder.callProxy(MemberInfo.this, caller, returnedNothing -> response(returnedNothing));
            }
        });
    }

    private void response(Void returnedNothing) {
        finish();
    }

    private void getUserInfo(Long userId) {
        Call<User> caller = proxy.getUserById(userId);
        ServerProxyBuilder.callProxy(MemberInfo.this, caller, returnedUser -> response(returnedUser));
    }

    private void response(User user) {
        Log.w("Test receive", "server receive test " + user.toString());
        user_info = user;
        setUserInfo(user_info);
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        userId = intent.getLongExtra(id, 0);
        groupId = intent.getLongExtra(gid, 0);
    }

    public static Intent intent(Context context, Long userId, Long groupId) {
        Intent intent = new Intent(context, MemberInfo.class);
        intent.putExtra(id, userId);
        intent.putExtra(gid, groupId);
        return intent;
    }
}
