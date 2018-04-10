package ca.sfu.Navy.walkinggroup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.sfu.Navy.walkinggroup.R;
import ca.sfu.Navy.walkinggroup.UserManager.RequestActivity;
import ca.sfu.Navy.walkinggroup.model.PermissionRecord;
import ca.sfu.Navy.walkinggroup.model.ServerProxy;
import ca.sfu.Navy.walkinggroup.model.ServerProxyBuilder;
import retrofit2.Call;

public class RequestListAdapter extends ArrayAdapter<PermissionRecord> {

    private Context mcontext;
    private List<PermissionRecord> requestList = new ArrayList<>();
    private ServerProxy proxy;
    private RequestListAdapter adapter;


    public RequestListAdapter(@NonNull Context context, List<PermissionRecord> list, ServerProxy serverproxy){
        super(context, 0, list);
        mcontext = context;
        requestList = list;
        proxy = serverproxy;
        adapter = this;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mcontext).inflate(R.layout.request_list, parent, false);
        }

        PermissionRecord currentRequest = requestList.get(position);
        Long permissionId = currentRequest.getId();

        TextView Message = (TextView) listItem.findViewById(R.id.Request_text_message);
        Message.setText(currentRequest.getMessage());

        Button approve_btn = (Button) listItem.findViewById(R.id.Request_btn_approve);
        approve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Approve the request
                Call<PermissionRecord> caller = proxy.requestPost(permissionId, "APPROVED");
                ServerProxyBuilder.callProxy(getContext(), caller, returnedRequest -> response(returnedRequest));
                requestList.remove(currentRequest);
                adapter.notifyDataSetChanged();
            }
        });

        Button deny_btn = (Button) listItem.findViewById(R.id.Request_btn_deny);
        deny_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Deny the request
                Call<PermissionRecord> caller = proxy.requestPost(permissionId, "DENIED");
                ServerProxyBuilder.callProxy(getContext(), caller, returnedRequest -> response(returnedRequest));
                requestList.remove(currentRequest);
                adapter.notifyDataSetChanged();
            }
        });

        return listItem;
    }

    private void response(PermissionRecord request){
        // No idea what to do here
    }
}
