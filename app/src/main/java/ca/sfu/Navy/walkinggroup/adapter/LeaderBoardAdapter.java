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
import ca.sfu.Navy.walkinggroup.model.User;

public class LeaderBoardAdapter  extends ArrayAdapter<User> {
    private Context mcontext;
    private List<User> userList = new ArrayList<>();


    public LeaderBoardAdapter(@NonNull Context context, List<User> list){
        super(context, 0, list);
        mcontext = context;
        userList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(mcontext).inflate(R.layout.leaderboard_list, parent, false);
        }

        User currentUser = userList.get(position);

        TextView userName = (TextView) listItem.findViewById(R.id.user_name_txt);
        userName.setText(currentUser.getName());

        TextView userPoints = (TextView) listItem.findViewById(R.id.user_points_txt);
        userPoints.setText("" + currentUser.getTotalPointsEarned());


        return listItem;
    }


}
