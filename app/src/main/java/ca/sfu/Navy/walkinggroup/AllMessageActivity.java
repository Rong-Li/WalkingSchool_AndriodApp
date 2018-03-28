package ca.sfu.Navy.walkinggroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.List;

import ca.sfu.Navy.walkinggroup.adapter.MessageListAdapter;
import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.Message;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import retrofit2.Call;

public class AllMessageActivity extends AppCompatActivity {

    private CheckBox mRadioBtn;
    private ListView mListView;
    private ServerProxy mProxy;
    private MessageListAdapter mMessageListAdapter;
    private boolean isEmergency;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_message);
        init();
    }

    private void init() {
        String token = SavedSharedPreference.getPrefUserToken(AllMessageActivity.this);
        mProxy = ServerProxyBuilder.getProxy(getString(R.string.apikey), token);
        mRadioBtn = findViewById(R.id.radioBtn);
        mListView = findViewById(R.id.message_list);
        mMessageListAdapter = new MessageListAdapter(this);
        mListView.setAdapter(mMessageListAdapter);
        mRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isEmergency = isChecked;
                getMessage();
            }
        });
        getMessage();
    }

    public void getMessage(){
        // Make call
        Call<List<Message>> caller = mProxy.listMessage(isEmergency);
        ServerProxyBuilder.callProxy(AllMessageActivity.this, caller, this::response);
    }

    private void response(List<Message> messages) {
        mMessageListAdapter.updateData(messages);
    }
}
