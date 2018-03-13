package ca.sfu.Navy.walkinggroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import ca.sfu.Navy.walkinggroup.model.User;

public class MonitorActivity extends AppCompatActivity {

    private User user_loggedin;
    private ServerProxy proxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        // Build the server proxy
        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), null);

    }

    private void getUserLoggedin(){


    }





}
