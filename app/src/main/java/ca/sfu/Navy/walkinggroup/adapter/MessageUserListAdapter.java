package ca.sfu.Navy.walkinggroup.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.Message.UserSendMsgActivity;
import ca.sfu.Navy.walkinggroup.model.MessageUser;

public class MessageUserListAdapter extends BaseAdapter {
    private List<MessageUser> messageUsers;
    private LayoutInflater mInflater;
    private Context context;

    public MessageUserListAdapter(Context context) {
        this.messageUsers = new ArrayList<>();
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void updateData(List<MessageUser> messageUsers) {
        this.messageUsers = messageUsers;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messageUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return messageUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_user_leader, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.mUserId = convertView.findViewById(R.id.userId_text);
            viewHolder.mGroupDesc = convertView.findViewById(R.id.group_des);
            viewHolder.group_layout = convertView.findViewById(R.id.group_layout);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        MessageUser messageUser = messageUsers.get(position);
        if (messageUser.isIs_group()) {
            viewHolder.group_layout.setVisibility(View.VISIBLE);
            viewHolder.mGroupDesc.setText(messageUser.getGroup_desc());
        } else
            viewHolder.group_layout.setVisibility(View.GONE);
        viewHolder.mUserId.setText(messageUser.getUser_id() + "");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserSendMsgActivity.class);
                intent.putExtra("user_id",messageUser.getUser_id());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mGroupDesc;
        private TextView mUserId;
        private LinearLayout group_layout;
    }
}
