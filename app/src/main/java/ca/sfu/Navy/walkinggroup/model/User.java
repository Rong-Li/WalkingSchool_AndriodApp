package ca.sfu.Navy.walkinggroup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;

    private List<User> monitoredByUsers = new ArrayList<>();
    private List<User> monitorsUsers = new ArrayList<>();
    private List<Void> memberOfGroups = new ArrayList<>();
    private List<Void> leadsGroups = new ArrayList<>();

    private String href;

    //getters
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public List<User> getMonitoredByUsers() {
        return monitoredByUsers;
    }
    public List<User> getMonitorsUsers() {
        return monitorsUsers;
    }
    public List<Void> getMemberOfGroups() {
        return memberOfGroups;
    }
    public List<Void> getLeadsGroups() {
        return leadsGroups;
    }
    public String getHref() {
        return href;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setMonitoredByUsers(List<User> monitoredByUsers) {
        this.monitoredByUsers = monitoredByUsers;
    }
    public void setMonitorsUsers(List<User> monitorsUsers) {
        this.monitorsUsers = monitorsUsers;
    }
    public void setMemberOfGroups(List<Void> memberOfGroups) {
        this.memberOfGroups = memberOfGroups;
    }
    public void setLeadsGroups(List<Void> leadsGroups) {
        this.leadsGroups = leadsGroups;
    }
    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", monitoredByUsers=" + monitoredByUsers +
                ", monitorsUsers=" + monitorsUsers +
                ", memberOfGroups=" + memberOfGroups +
                '}';
    }
}
