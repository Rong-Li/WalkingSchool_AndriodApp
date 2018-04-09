package ca.sfu.Navy.walkinggroup.UserManager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.adapter.RequestListAdapter;
import ca.sfu.Navy.walkinggroup.model.PermissionRecord;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;
import ca.sfu.Navy.walkinggroup.monitor.MonitorActivity;
import retrofit2.Call;

public class RequestActivity extends AppCompatActivity {

    private ServerProxy proxy;
    private List<PermissionRecord> requestList = new ArrayList<>();
    private User user_loggedin = new User();
    private ListView listView;
    private RequestListAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        // Build the server proxy with token
        String token = SavedSharedPreference.getPrefUserToken(RequestActivity.this);
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);
        getListFromServer();

    }


    private void getListFromServer(){
        // Make call to retrieve List of requests
        Call<List<PermissionRecord>> caller = proxy.getPendingRequest();
        ServerProxyBuilder.callProxy(RequestActivity.this, caller, returnedRequests -> response(returnedRequests));
    }

    private void response(List<PermissionRecord> returnedRequest){
        requestList = returnedRequest;
        populateList();
    }

    private void populateList(){
        // Configure ListView
        listView = (ListView) findViewById(R.id.Request_list);
        // Configure Adapter
        mAdapter = new RequestListAdapter(RequestActivity.this, requestList, proxy);
        listView.setAdapter(mAdapter);
    }



    public static Intent startIntent(Context context){
        return new Intent(context, RequestActivity.class);
    }
}
