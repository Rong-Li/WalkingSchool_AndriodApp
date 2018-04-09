package ca.sfu.Navy.walkinggroup.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerJson {

    private List<UserCus> userCuses;
    private List<GroupCus> groupCuses;
    private UserCus current_user;
    private GroupCus current_grou;

    public UserCus getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(UserCus current_user) {
        this.current_user = current_user;
    }

    public GroupCus getCurrent_grou() {
        return current_grou;
    }

    public void setCurrent_grou(GroupCus current_grou) {
        this.current_grou = current_grou;
    }

    public CustomerJson() {
        userCuses = new ArrayList<>();
        groupCuses = new ArrayList<>();
    }

    public List<UserCus> getUserCuses() {
        return userCuses;
    }

    public void setUserCuses(List<UserCus> userCuses) {
        this.userCuses = userCuses;
    }

    public List<GroupCus> getGroupCuses() {
        return groupCuses;
    }

    public void setGroupCuses(List<GroupCus> groupCuses) {
        this.groupCuses = groupCuses;
    }

    public static class UserCus {
        public UserCus(int themeName) {
            this.themeName = themeName;
        }

        private int themeName;

        public int getThemeName() {
            return themeName;
        }

        public void setThemeName(int themeName) {
            this.themeName = themeName;
        }
    }

    public static class GroupCus {
        public GroupCus(int iconName) {
            this.iconName = iconName;
        }

        private int iconName;

        public int getIconName() {
            return iconName;
        }

        public void setIconName(int iconName) {
            this.iconName = iconName;
        }
    }
}
