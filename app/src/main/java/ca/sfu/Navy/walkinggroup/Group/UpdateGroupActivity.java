package ca.sfu.Navy.walkinggroup.Group;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import retrofit2.Call;

public class UpdateGroupActivity extends AppCompatActivity {
    private static final String gid = "groupId";
    private ServerProxy proxy;
    private Group group_temp;
    private Long groupId;
    List<Double>groupLat = new ArrayList<>();
    List<Double>groupLng = new ArrayList<>();
    List<Double>groupLat_temp = new ArrayList<>();
    List<Double>groupLng_temp = new ArrayList<>();
    EditText description;
    EditText latitude;
    EditText longitude;
    private Double lat_temp;
    private Double lng_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_group);
        String token = SavedSharedPreference.getPrefUserToken(UpdateGroupActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);

        extractDataFromIntent();
        setUpEditText();
        getGroupDetail();
        setUpUpdateButton();
        setUpDeleteButton();
    }

    private void setUpEditText() {
        description = (EditText) findViewById(R.id.group_description_txt);
        latitude = (EditText) findViewById(R.id.group_latitude_txt);
        longitude = (EditText) findViewById(R.id.group_longitude_txt);

        Call<Void> caller = proxy.comminicate();
        ServerProxyBuilder.callProxy(UpdateGroupActivity.this, caller, returnedNothing -> responses(returnedNothing));
    }

    private void responses(Void returnedNothing) {
        groupLat_temp = group_temp.getRouteLatArray();
        groupLng_temp = group_temp.getRouteLngArray();
        description.setText("" + group_temp.getGroupDescription());
        latitude.setText("" + groupLat_temp.get(0));
        longitude.setText("" + groupLng_temp.get(0));
    }

    private void setUpUpdateButton() {
        Button button = (Button) findViewById(R.id.update_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat_edit =latitude.getText().toString();
                String lng_edit = longitude.getText().toString();
                String description_edit = description.getText().toString();
                if(!lat_edit.isEmpty() && !lng_edit.isEmpty()){
                    try{
                        lat_temp = Double.parseDouble(lat_edit);
                        lng_temp = Double.parseDouble(lng_edit);
                    }catch (Exception e){
                        latitude.setText("" + 0);
                        longitude.setText("" + 0);
                    }
                }
                groupLat.add(lat_temp);
                groupLng.add(lng_temp);

                group_temp.setId(groupId);
                group_temp.setRouteLatArray(groupLat);
                group_temp.setRouteLngArray(groupLng);
                group_temp.setGroupDescription(description_edit);

                Call<Group> caller = proxy.updateGroupDetails(groupId, group_temp);
                ServerProxyBuilder.callProxy(UpdateGroupActivity.this, caller, returnedGroup -> responses(returnedGroup));
            }
        });
    }

    private void responses(Group group){
        Log.w("Test receive", "server receive test " + group.toString());
        Intent intent = UpdateGroupListActivity.intent(UpdateGroupActivity.this);
        finish();
        startActivity(intent);
    }

    private void setUpDeleteButton() {
        Button button = (Button) findViewById(R.id.delete_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> caller = proxy.deleteGroups(groupId);
                ServerProxyBuilder.callProxy(UpdateGroupActivity.this, caller, returnedNothing -> response(returnedNothing));
            }
        });
    }

    private void response(Void returnedNothing) {
        Intent intent = UpdateGroupListActivity.intent(UpdateGroupActivity.this);
        finish();
        startActivity(intent);
    }

    private void getGroupDetail() {
        Call<Group> caller = proxy.getGroupDetails(groupId);
        ServerProxyBuilder.callProxy(UpdateGroupActivity.this, caller, returnedGroup -> response(returnedGroup));
    }

    private void response(Group group) {
        Log.w("Test receive", "server receive test " + group.toString());
        group_temp = group;
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        groupId = intent.getLongExtra(gid, 0);
    }

    public static Intent intent(Context context, Long groupId){
        Intent intent = new Intent(context, UpdateGroupActivity.class);
        intent.putExtra(gid, groupId);
        return intent;
    }
}
