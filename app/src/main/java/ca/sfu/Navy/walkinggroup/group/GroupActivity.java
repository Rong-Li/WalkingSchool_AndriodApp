package ca.sfu.Navy.walkinggroup.group;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;

public class GroupActivity extends AppCompatActivity {
    private ServerProxy proxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        proxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), null);

        setupCreateNewGroupbtn();

    }

    private setupCreateNewGroupbtn(){
        Button btn = new Button(findViewById())
    }

    public static Intent newIntent(Context context){
        return new Intent(context, GroupActivity.class);
    }
}
