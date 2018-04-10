package ca.sfu.Navy.walkinggroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.adapter.LeaderBoardAdapter;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import retrofit2.Call;

public class LeaderboardActivity extends AppCompatActivity {
    private ServerProxy proxy;
    private LeaderBoardAdapter mAdapter;
    private ListView listView;
    private int index = 0;
    private List<User> userList = new ArrayList<>();
    private List<User> sortedUserList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        String token = SavedSharedPreference.getPrefUserToken(LeaderboardActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        getUserList();
    }

    private void getUserList() {
        Call<List<User>> caller = proxy.getUsers();
        ServerProxyBuilder.callProxy(LeaderboardActivity.this, caller, returnedUsers -> response(returnedUsers));
    }

    private void response(List<User> returnedUsers) {
        userList = returnedUsers;
        Log.w("Register Server", "Server replied with user: " + userList.toString());
        listToSort();
    }

    private void listToSort() {
        for(int i = 0; i < userList.size(); i ++){
            User max = new User();
            for(User user: userList){
                if(user.getTotalPointsEarned() >= max.getTotalPointsEarned()){
                    if(i > 0 && user.getTotalPointsEarned() < sortedUserList.get(i-1).getTotalPointsEarned()) {
                        max = user;
                    }
                    else if(i == 0) {
                        max = user;
                    }
                }
            }
            sortedUserList.add(max);
        }
        Log.w("Register Server", "Server replied with user: " + userList.toString());
        populateList();
    }

    private void populateList() {
        listView = (ListView) findViewById(R.id.leaderboard_list);
        mAdapter = new LeaderBoardAdapter(LeaderboardActivity.this, sortedUserList);
        listView.setAdapter(mAdapter);
    }

    public static Intent intent(Context context){
        return new Intent(context, LeaderboardActivity.class);
    }
}
