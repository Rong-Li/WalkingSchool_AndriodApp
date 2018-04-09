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
import ca.sfu.Navy.walkinggroup.model.PermissionRecord;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;

public class RequestListAdapter extends ArrayAdapter<PermissionRecord> {

    private Context mcontext;
    private List<PermissionRecord> requestList = new ArrayList<>();
    private ServerProxy proxy;


    public RequestListAdapter(@NonNull Context context, List<PermissionRecord> list){
        super(context, 0, list);
        mcontext = context;
        requestList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mcontext).inflate(R.layout.request_list, parent, false);
        }

        PermissionRecord currentRequest = requestList.get(position);

        TextView Message = (TextView) listItem.findViewById(R.id.Request_text_message);
        Message.setText(currentRequest.getMessage());


        return listItem;
    }
}
