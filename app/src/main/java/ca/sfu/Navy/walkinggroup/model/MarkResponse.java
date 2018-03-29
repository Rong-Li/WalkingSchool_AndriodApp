package ca.sfu.Navy.walkinggroup.model;

import java.util.List;

public class MarkResponse {

    /**
     * id : 3
     * name : Mr. Unique
     * email : unique12@sfu.ca
     * birthYear : 2005
     * birthMonth : 12
     * address : #1 big house way.
     * cellPhone : +1.778.098.7765
     * homePhone : (604) 123-4567
     * grade : Kindergarten
     * teacherName : Mr. Big
     * emergencyContactInfo : Mom!
     * monitoredByUsers : []
     * monitorsUsers : []
     * memberOfGroups : []
     * leadsGroups : []
     * lastGpsLocation : {"lat":null,"lng":null,"timestamp":null}
     * unreadMessages : [{"id":20,"href":"/messages/20"},{"id":23,"href":"/messages/23"},{"id":24,"href":"/messages/24"}]
     * readMessages : [{"id":22,"href":"/messages/22"}]
     * href : /users/3
     */

    private int id;
    private String name;
    private String email;
    private int birthYear;
    private int birthMonth;
    private String address;
    private String cellPhone;
    private String homePhone;
    private String grade;
    private String teacherName;
    private String emergencyContactInfo;
    /**
     * lat : null
     * lng : null
     * timestamp : null
     */

    private LastGpsLocationBean lastGpsLocation;
    private String href;
    private List<?> monitoredByUsers;
    private List<?> monitorsUsers;
    private List<?> memberOfGroups;
    private List<?> leadsGroups;
    /**
     * id : 20
     * href : /messages/20
     */

    private List<UnreadMessagesBean> unreadMessages;
    /**
     * id : 22
     * href : /messages/22
     */

    private List<ReadMessagesBean> readMessages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getEmergencyContactInfo() {
        return emergencyContactInfo;
    }

    public void setEmergencyContactInfo(String emergencyContactInfo) {
        this.emergencyContactInfo = emergencyContactInfo;
    }

    public LastGpsLocationBean getLastGpsLocation() {
        return lastGpsLocation;
    }

    public void setLastGpsLocation(LastGpsLocationBean lastGpsLocation) {
        this.lastGpsLocation = lastGpsLocation;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<?> getMonitoredByUsers() {
        return monitoredByUsers;
    }

    public void setMonitoredByUsers(List<?> monitoredByUsers) {
        this.monitoredByUsers = monitoredByUsers;
    }

    public List<?> getMonitorsUsers() {
        return monitorsUsers;
    }

    public void setMonitorsUsers(List<?> monitorsUsers) {
        this.monitorsUsers = monitorsUsers;
    }

    public List<?> getMemberOfGroups() {
        return memberOfGroups;
    }

    public void setMemberOfGroups(List<?> memberOfGroups) {
        this.memberOfGroups = memberOfGroups;
    }

    public List<?> getLeadsGroups() {
        return leadsGroups;
    }

    public void setLeadsGroups(List<?> leadsGroups) {
        this.leadsGroups = leadsGroups;
    }

    public List<UnreadMessagesBean> getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(List<UnreadMessagesBean> unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public List<ReadMessagesBean> getReadMessages() {
        return readMessages;
    }

    public void setReadMessages(List<ReadMessagesBean> readMessages) {
        this.readMessages = readMessages;
    }

    public static class LastGpsLocationBean {
        private Object lat;
        private Object lng;
        private Object timestamp;

        public Object getLat() {
            return lat;
        }

        public void setLat(Object lat) {
            this.lat = lat;
        }

        public Object getLng() {
            return lng;
        }

        public void setLng(Object lng) {
            this.lng = lng;
        }

        public Object getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Object timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class UnreadMessagesBean {
        private int id;
        private String href;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }

    public static class ReadMessagesBean {
        private int id;
        private String href;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }

    @Override
    public String toString() {
        return "MarkResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthYear=" + birthYear +
                ", birthMonth=" + birthMonth +
                ", address='" + address + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", grade='" + grade + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", emergencyContactInfo='" + emergencyContactInfo + '\'' +
                ", lastGpsLocation=" + lastGpsLocation +
                ", href='" + href + '\'' +
                ", monitoredByUsers=" + monitoredByUsers +
                ", monitorsUsers=" + monitorsUsers +
                ", memberOfGroups=" + memberOfGroups +
                ", leadsGroups=" + leadsGroups +
                ", unreadMessages=" + unreadMessages +
                ", readMessages=" + readMessages +
                '}';
    }
}
