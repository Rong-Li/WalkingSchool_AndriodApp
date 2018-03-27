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
    private int birthYear;
    private int birthMonth;
    private String address;
    private String cellPhone;
    private String homePhone;
    private String grade;
    private String teacherName;
    private String emergencyContactInfo;

    private List<User> monitoredByUsers = new ArrayList<>();
    private List<User> monitorsUsers = new ArrayList<>();
    private List<Group> memberOfGroups = new ArrayList<>();
    private List<Group> leadsGroups = new ArrayList<>();


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
    public int getBirthYear(){
        return birthYear;
    }
    public int getBirthMonth(){
        return birthMonth;
    }
    public String getAddress(){
        return address;
    }
    public String getCellPhone(){
        return cellPhone;
    }
    public String getHomePhone(){
        return homePhone;
    }
    public String getGrade(){
        return grade;
    }
    public String getTeacherName(){
        return teacherName;
    }
    public String getEmergencyContactInfo(){
        return emergencyContactInfo;
    }
    public List<User> getMonitoredByUsers() {
        return monitoredByUsers;
    }
    public List<User> getMonitorsUsers() {
        return monitorsUsers;
    }
    public List<Group> getMemberOfGroups() {
        return  this.memberOfGroups;
    }
    public String getHref() {
        return href;
    }
    public List<Group> getLeadsGroups() {
        return leadsGroups;
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
    public void setBirthYear(int birthYear){
        this.birthYear = birthYear;
    }
    public void setBirthMonth(int birthMonth){
        this.birthMonth = birthMonth;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setCellPhone(String cellPhone){
        this.cellPhone = cellPhone;
    }
    public void setHomePhone(String homePhone){
        this.homePhone = homePhone;
    }
    public void setGrade(String grade){
        this.grade = grade;
    }
    public void setTeacherName(String teacherName){
        this.teacherName = teacherName;
    }
    public void setEmergencyContactInfo(String emergencyContactInfo){
        this.emergencyContactInfo = emergencyContactInfo;
    }
    public void setMonitoredByUsers(List<User> monitoredByUsers) {
        this.monitoredByUsers = monitoredByUsers;
    }
    public void setMonitorsUsers(List<User> monitorsUsers) {
        this.monitorsUsers = monitorsUsers;
    }
    public void setMemberOfGroups(List<Group> memberOfGroups) {
        this.memberOfGroups = memberOfGroups;
    }
    public void setHref(String href) {
        this.href = href;
    }
    public void setLeadsGroups(List<Group> leadsGroups) {
        this.leadsGroups = leadsGroups;
    }

    // List items modifiers
    public void addUsertoMonitor(User user){
        this.monitorsUsers.add(user);
    }
    public void removeUserfromMonitor(int index){
        this.monitorsUsers.remove(index);
    }
    public void addUsertoMonitored(User user){
        this.monitoredByUsers.add(user);
    }
    public void removeUserfromMonitored(int index){ this.monitoredByUsers.remove(index);}



    @Override
    public String toString() {
        return "User{" +
                "id :" + id +
                ", name  :'" + name + '\'' +
                ", email :'" + email + '\'' +
                ", birthYear :" + birthYear +
                ", birthMonth :" + birthMonth +
                ", address :" + address +
                ", cellPhone :" + cellPhone +
                ", homePhone :" + homePhone +
                ", grade :" + grade +
                ", teacherName :" + teacherName +
                ", emergencyContactInfo :" + emergencyContactInfo +
                ", monitoredByUsers=" + monitoredByUsers +
                ", monitorsUsers=" + monitorsUsers +
                ", memberOfGroups=" + memberOfGroups +
                ", leadsGroups=" + leadsGroups +
                ", lastGpsLocation :" +
                '}';
    }
}
