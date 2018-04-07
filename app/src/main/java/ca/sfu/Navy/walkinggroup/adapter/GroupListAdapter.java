package ca.sfu.Navy.walkinggroup.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import ca.sfu.Navy.walkinggroup.GroupSendMsgActivity;
import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.model.CustomerJson;
import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;

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
            viewHolder.group_icon = convertView.findViewById(R.id.group_icon);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Gson gson = new Gson();
        String json = SavedSharedPreference.getPreUserCus(context);
        CustomerJson customerJson;
        if (json.isEmpty()) {
            customerJson = new CustomerJson();
        } else {
            customerJson = gson.fromJson(json, CustomerJson.class);
        }
        if (customerJson.getGroupCuses().size() > 0) {
            viewHolder.group_icon.setImageResource(customerJson.getCurrent_grou().getIconName());
        }
        viewHolder.mDescText.setText(groups.get(position).getGroupDescription());
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GroupSendMsgActivity.class);
            intent.putExtra("group_id", groups.get(position).getId());
            context.startActivity(intent);
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mDescText;
        private ImageView group_icon;
    }
}
