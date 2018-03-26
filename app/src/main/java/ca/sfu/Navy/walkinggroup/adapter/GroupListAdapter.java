package ca.sfu.Navy.walkinggroup.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.sfu.Navy.walkinggroup.GroupSendMsgActivity;
import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.model.Group;

public class GroupListAdapter extends BaseAdapter {
    private ArrayList<Group> groups;
    private LayoutInflater mInflater;
    private Context context;

    public GroupListAdapter(Context context) {
        this.groups = new ArrayList<>();
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public void updateData(ArrayList<Group> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_group_list_item, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.mDescText = convertView.findViewById(R.id.group_des);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mDescText.setText(groups.get(position).getGroupDescription());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GroupSendMsgActivity.class);
                intent.putExtra("group_id",groups.get(position).getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mDescText;
    }
}
