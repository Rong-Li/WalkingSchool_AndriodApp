package ca.sfu.Navy.walkinggroup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.model.Group;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;

public class GroupListAdapter extends ArrayAdapter<Group> {
    private Context temp_context;
    private List<Group> groupList = new ArrayList<>();
    private String groupDescription;
    private ServerProxy proxy;

    public GroupListAdapter(@NonNull Context context, List<Group> list){
        super(context, 0, list);
        temp_context = context;
        groupList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(temp_context).inflate(R.layout.group_list, parent, false);
        }

        Group currentGroup = groupList.get(position);

        TextView groupDescription = (TextView) listItem.findViewById(R.id.group_description_txt);
        groupDescription.setText(currentGroup.getGroupDescription());

        return listItem;
    }
}
