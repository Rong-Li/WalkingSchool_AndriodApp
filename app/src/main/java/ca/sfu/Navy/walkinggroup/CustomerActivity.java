package ca.sfu.Navy.walkinggroup;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import ca.sfu.Navy.walkinggroup.adapter.ColorsListAdapter;
import ca.sfu.Navy.walkinggroup.model.CustomerJson;
import ca.sfu.Navy.walkinggroup.model.MyThemeUtils;
import ca.sfu.Navy.walkinggroup.model.PreferenceUtils;
import ca.sfu.Navy.walkinggroup.model.SavedSharedPreference;

public class CustomerActivity extends AppCompatActivity {
    private int points = 30;
    private int total = 50;

    private LinearLayout group_layout_1,
            group_layout_2,
            group_layout_3,
            group_layout_4,
            group_layout_5;

    private TextView group_point_text_1,
            group_point_text_2,
            group_point_text_3,
            group_point_text_4,
            group_point_text_5;

    private TextView pointText;
    private TextView totalText;

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
        overridePendingTransition(0, 0);
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
        setContentView(R.layout.activity_gamification);
        init();
    }


    private void init() {
        pointText = findViewById(R.id.points);
        totalText = findViewById(R.id.total);
        group_point_text_1 = findViewById(R.id.point_1);
        group_point_text_2 = findViewById(R.id.point_2);
        group_point_text_3 = findViewById(R.id.point_3);
        group_point_text_4 = findViewById(R.id.point_4);
        group_point_text_5 = findViewById(R.id.point_5);

        refreshPoints();
    }

    public void refreshPoints() {
        pointText.setText(points + "");
        totalText.setText(total + "");
    }

    public void group_1(View view) {
        int flag = R.drawable.group_1;
        Gson gson = new Gson();
        int point = Integer.parseInt(group_point_text_1.getText().toString());
        if (points < point) {
            Toast.makeText(this, "Your points is not enough", Toast.LENGTH_SHORT).show();
        } else {
            String json = SavedSharedPreference.getPreUserCus(this);
            CustomerJson customerJson;
            if (json.isEmpty()) {
                customerJson = new CustomerJson();
            } else {
                customerJson = gson.fromJson(json, CustomerJson.class);
            }
            List<CustomerJson.GroupCus> groups = customerJson.getGroupCuses();
            for (CustomerJson.GroupCus group : groups) {
                if (group.getIconName() == flag) {
                    Toast.makeText(this, "You have already bought this icon,change successfully", Toast.LENGTH_SHORT).show();
                    customerJson.setCurrent_grou(group);
                    SavedSharedPreference.setPreUserCus(this, customerJson);
                    return;
                }
            }
            points -= point;
            refreshPoints();
            CustomerJson.GroupCus cus = new CustomerJson.GroupCus(flag);
            groups.add(cus);
            customerJson.setCurrent_grou(cus);
            customerJson.setGroupCuses(groups);
            SavedSharedPreference.setPreUserCus(this, customerJson);
            Toast.makeText(this, "Group icon set successfully!", Toast.LENGTH_SHORT).show();
            System.out.println(SavedSharedPreference.getPreUserCus(this));
        }
    }

    public void group_2(View view) {
        int flag = R.drawable.group_2;
        Gson gson = new Gson();
        int point = Integer.parseInt(group_point_text_2.getText().toString());
        if (points < point) {
            Toast.makeText(this, "Your points is not enough", Toast.LENGTH_SHORT).show();
        } else {
            String json = SavedSharedPreference.getPreUserCus(this);
            CustomerJson customerJson;
            if (json.isEmpty()) {
                customerJson = new CustomerJson();
            } else {
                customerJson = gson.fromJson(json, CustomerJson.class);
            }
            List<CustomerJson.GroupCus> groups = customerJson.getGroupCuses();
            for (CustomerJson.GroupCus group : groups) {
                if (group.getIconName() == flag) {
                    Toast.makeText(this, "You have already bought this icon,change successfully", Toast.LENGTH_SHORT).show();
                    customerJson.setCurrent_grou(group);
                    SavedSharedPreference.setPreUserCus(this, customerJson);
                    return;
                }
            }
            points -= point;
            refreshPoints();
            CustomerJson.GroupCus cus = new CustomerJson.GroupCus(flag);
            groups.add(cus);
            customerJson.setCurrent_grou(cus);
            customerJson.setGroupCuses(groups);
            SavedSharedPreference.setPreUserCus(this, customerJson);
            Toast.makeText(this, "Group icon set successfully!", Toast.LENGTH_SHORT).show();
            System.out.println(SavedSharedPreference.getPreUserCus(this));
        }
    }

    public void group_3(View view) {
        int flag = R.drawable.group_3;
        Gson gson = new Gson();
        int point = Integer.parseInt(group_point_text_3.getText().toString());
        if (points < point) {
            Toast.makeText(this, "Your points is not enough", Toast.LENGTH_SHORT).show();
        } else {
            String json = SavedSharedPreference.getPreUserCus(this);
            CustomerJson customerJson;
            if (json.isEmpty()) {
                customerJson = new CustomerJson();
            } else {
                customerJson = gson.fromJson(json, CustomerJson.class);
            }
            List<CustomerJson.GroupCus> groups = customerJson.getGroupCuses();
            for (CustomerJson.GroupCus group : groups) {
                if (group.getIconName() == flag) {
                    Toast.makeText(this, "You have already bought this icon,change successfully", Toast.LENGTH_SHORT).show();
                    customerJson.setCurrent_grou(group);
                    SavedSharedPreference.setPreUserCus(this, customerJson);
                    return;
                }
            }
            points -= point;
            refreshPoints();
            CustomerJson.GroupCus cus = new CustomerJson.GroupCus(flag);
            groups.add(cus);
            customerJson.setCurrent_grou(cus);
            customerJson.setGroupCuses(groups);
            SavedSharedPreference.setPreUserCus(this, customerJson);
            Toast.makeText(this, "Group icon set successfully!", Toast.LENGTH_SHORT).show();
            System.out.println(SavedSharedPreference.getPreUserCus(this));
        }
    }

    public void group_4(View view) {
        int flag = R.drawable.group_4;
        Gson gson = new Gson();
        int point = Integer.parseInt(group_point_text_4.getText().toString());
        if (points < point) {
            Toast.makeText(this, "Your points is not enough", Toast.LENGTH_SHORT).show();
        } else {
            String json = SavedSharedPreference.getPreUserCus(this);
            CustomerJson customerJson;
            if (json.isEmpty()) {
                customerJson = new CustomerJson();
            } else {
                customerJson = gson.fromJson(json, CustomerJson.class);
            }
            List<CustomerJson.GroupCus> groups = customerJson.getGroupCuses();
            for (CustomerJson.GroupCus group : groups) {
                if (group.getIconName() == flag) {
                    Toast.makeText(this, "You have already bought this icon,change successfully", Toast.LENGTH_SHORT).show();
                    customerJson.setCurrent_grou(group);
                    SavedSharedPreference.setPreUserCus(this, customerJson);
                    return;
                }
            }
            points -= point;
            refreshPoints();
            CustomerJson.GroupCus cus = new CustomerJson.GroupCus(flag);
            groups.add(cus);
            customerJson.setCurrent_grou(cus);
            customerJson.setGroupCuses(groups);
            SavedSharedPreference.setPreUserCus(this, customerJson);
            Toast.makeText(this, "Group icon set successfully!", Toast.LENGTH_SHORT).show();
            System.out.println(SavedSharedPreference.getPreUserCus(this));
        }
    }

    public void group_5(View view) {
        int flag = R.drawable.group_5;
        Gson gson = new Gson();
        int point = Integer.parseInt(group_point_text_5.getText().toString());
        if (points < point) {
            Toast.makeText(this, "Your points is not enough", Toast.LENGTH_SHORT).show();
        } else {
            String json = SavedSharedPreference.getPreUserCus(this);
            CustomerJson customerJson;
            if (json.isEmpty()) {
                customerJson = new CustomerJson();
            } else {
                customerJson = gson.fromJson(json, CustomerJson.class);
            }
            List<CustomerJson.GroupCus> groups = customerJson.getGroupCuses();
            for (CustomerJson.GroupCus group : groups) {
                if (group.getIconName() == flag) {
                    Toast.makeText(this, "You have already bought this icon,change successfully", Toast.LENGTH_SHORT).show();
                    customerJson.setCurrent_grou(group);
                    SavedSharedPreference.setPreUserCus(this, customerJson);
                    return;
                }
            }
            points -= point;
            refreshPoints();
            CustomerJson.GroupCus cus = new CustomerJson.GroupCus(flag);
            groups.add(cus);
            customerJson.setGroupCuses(groups);
            customerJson.setCurrent_grou(cus);
            SavedSharedPreference.setPreUserCus(this, customerJson);
            Toast.makeText(this, "Group icon set successfully!", Toast.LENGTH_SHORT).show();
            System.out.println(SavedSharedPreference.getPreUserCus(this));
        }
    }

    public void changeTheme(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerActivity.this);
        builder.setTitle("设置主题");
        Integer[] res = new Integer[]{R.drawable.red_round, R.drawable.brown_round, R.drawable.blue_round,
                R.drawable.blue_grey_round, R.drawable.yellow_round, R.drawable.deep_purple_round,
                R.drawable.pink_round, R.drawable.green_round};
        List<Integer> list = Arrays.asList(res);
        ColorsListAdapter adapter = new ColorsListAdapter(CustomerActivity.this, list);
        adapter.setCheckItem(MyThemeUtils.getCurrentTheme(CustomerActivity.this).getIntValue());
        GridView gridView = (GridView) LayoutInflater.from(CustomerActivity.this).inflate(R.layout.colors_panel_layout, null);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setCacheColorHint(0);
        gridView.setAdapter(adapter);
        builder.setView(gridView);
        final AlertDialog dialog = builder.show();
        gridView.setOnItemClickListener(
                (parent, view1, position, id) -> {
                    dialog.dismiss();
                    int value = MyThemeUtils.getCurrentTheme(CustomerActivity.this).getIntValue();
                    if (value != position) {
                        PreferenceUtils.getInstance(CustomerActivity.this).saveParam("change_theme_key", position);
                        MyThemeUtils.changTheme(CustomerActivity.this, MyThemeUtils.Theme.mapValueToTheme(position));
                        CustomerActivity.this.setTheme(R.style.DeepOrangeTheme);
                        checkTheme();
                    }
                }
        );
    }

}
