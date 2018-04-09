package ca.sfu.Navy.walkinggroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ca.sfu.Navy.walkinggroup.adapter.MessageListAdapter;
import ca.sfu.Navy.walkinggroup.model.HandleMsgStatusListener;
import ca.sfu.Navy.walkinggroup.model.MarkResponse;
import ca.sfu.Navy.walkinggroup.model.Message;
import ca.sfu.Navy.walkinggroup.model.MyThemeUtils;
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
    private HandleMsgStatusListener listener;
    private boolean isRead;
    private CheckBox readCheckBtn;
    private MyThemeUtils.Theme currentTheme;

    private void initTheme() {
        MyThemeUtils.Theme theme = MyThemeUtils.getCurrentTheme(this);
        currentTheme = theme;
        MyThemeUtils.changTheme(this, theme);
    }

    public void checkTheme() {
        MyThemeUtils.Theme theme = MyThemeUtils.getCurrentTheme(this);
        if (currentTheme == theme)
            return;
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkTheme();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
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
        readCheckBtn = findViewById(R.id.read_btn);
        readCheckBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRead = isChecked;
                getMessage();
            }
        });
        mRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isEmergency = isChecked;
                getMessage();
            }
        });
        listener = (msgId, userId, status) -> {
            Call<MarkResponse> caller = mProxy.changeReadStatus(msgId, userId, status);
            ServerProxyBuilder.callProxy(AllMessageActivity.this, caller, ans -> {
                Toast.makeText(AllMessageActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            });
        };
        mMessageListAdapter.setListener(listener);
        getMessage();
    }

    public void getMessage() {
        // Make call
        Call<List<Message>> caller = mProxy.listMessage(isEmergency, isRead ? "read" : "unread");
        ServerProxyBuilder.callProxy(AllMessageActivity.this, caller, this::response);
    }

    private void response(List<Message> messages) {
        mMessageListAdapter.updateData(messages);
    }
}
