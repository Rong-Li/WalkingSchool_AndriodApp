package ca.sfu.Navy.walkinggroup.Group;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.sfu.Navy.walkinggroup.R;

public class ManageGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_group);

        createGroupActivityButton();
        listGroupActivityButton();
        updateGroupActivityButton();
        changeLeaderActivityButton();
    }

    private void changeLeaderActivityButton() {
        Button button = findViewById(R.id.change_leader_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ChangeLeaderListActivity.intent(ManageGroupActivity.this);
                startActivity(intent);
            }
        });
    }

    private void createGroupActivityButton() {
        Button button = findViewById(R.id.creatgroup_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateGroupActivity.newIntent(ManageGroupActivity.this);
                startActivity(intent);
            }
        });
    }

    private void listGroupActivityButton(){
        Button button = (Button) findViewById(R.id.list_groups_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ListGroupAsLeaderActivity.intent(ManageGroupActivity.this);
                startActivity(intent);
            }
        });
    }

    private void updateGroupActivityButton(){
        Button button = (Button) findViewById(R.id.update_group_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UpdateGroupListActivity.intent(ManageGroupActivity.this);
                startActivity(intent);
            }
        });
    }

    public static Intent intent(Context context){
        return new Intent(context, ManageGroupActivity.class);
    }
}
