package ca.sfu.Navy.walkinggroup.model;


public class MessageUser {
    private String group_desc;
    private Long user_id;
    private boolean is_group;

    public MessageUser(String group_desc, Long user_id, boolean is_group) {
        this.group_desc = group_desc;
        this.user_id = user_id;
        this.is_group = is_group;
    }

    public MessageUser() {
    }

    public String getGroup_desc() {
        return group_desc;
    }

    public void setGroup_desc(String group_desc) {
        this.group_desc = group_desc;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public boolean isIs_group() {
        return is_group;
    }

    public void setIs_group(boolean is_group) {
        this.is_group = is_group;
    }
}
