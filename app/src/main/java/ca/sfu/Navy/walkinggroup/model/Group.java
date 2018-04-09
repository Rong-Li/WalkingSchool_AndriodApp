package ca.sfu.Navy.walkinggroup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {
    private Long id;
    private String groupDescription;
    private User leader = new User();

    private List<User> memberUsers = new ArrayList<>();
    private List<Double> routeLatArray = new ArrayList<>();
    private List<Double> routeLngArray = new ArrayList<>();
    private String href;
    /**
     * customJson : null
     */

    private Object customJson;

    //getters
    public Long getId() {
        return id;
    }
    public String getGroupDescription() {
        return groupDescription;
    }
    public User getLeader() {
        return leader;
    }
    public List<User> getMemberUsers() {
        return memberUsers;
    }
    public List<Double> getRouteLatArray() {
        return routeLatArray;
    }
    public List<Double> getRouteLngArray() {
        return routeLngArray;
    }
    public String getHref() {
        return href;
    }




    //setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public void setLeader(User leader){
        this.leader = leader;
    }
    public void setRouteLatArray(List<Double> routeLatArray) {
        this.routeLatArray = routeLatArray;
    }
    public void setRouteLngArray(List<Double> routeLngArray) {
        this.routeLngArray = routeLngArray;
    }
    public void setHref(String href) {
        this.href = href;
    }
    public void setMemberUsers(List<User> memberUsers){
        this.memberUsers = memberUsers;
    }
    
    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupDescription='" + groupDescription + '\'' +
                ", leader=" + leader +
                ", memberUsers=" + memberUsers +
                ", routeLatArray=" + routeLatArray +
                ", routeLngArray=" + routeLngArray +
                ", href='" + href + '\'' +
                ", customJson=" + customJson +
                '}';
    }

    public Object getCustomJson() {
        return customJson;
    }

    public void setCustomJson(Object customJson) {
        this.customJson = customJson;
    }
}
