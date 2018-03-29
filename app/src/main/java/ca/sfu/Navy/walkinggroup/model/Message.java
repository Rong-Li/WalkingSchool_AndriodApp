package ca.sfu.Navy.walkinggroup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    /**
     * id : 18
     * timestamp : 1521442825000
     * text : Yo yo yo
     * fromUser : {"id":2,"href":"/users/2"}
     * toGroup : {"id":3,"href":"/groups/3"}
     * emergency : false
     * href : /messages/18
     */

    private int id;
    private long timestamp;
    private String text;
    /**
     * id : 2
     * href : /users/2
     */

    private FromUserBean fromUser;
    /**
     * id : 3
     * href : /groups/3
     */

    private ToGroupBean toGroup;
    private boolean emergency;
    private String href;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FromUserBean getFromUser() {
        return fromUser;
    }

    public void setFromUser(FromUserBean fromUser) {
        this.fromUser = fromUser;
    }

    public ToGroupBean getToGroup() {
        return toGroup;
    }

    public void setToGroup(ToGroupBean toGroup) {
        this.toGroup = toGroup;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public static class FromUserBean {
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

    public static class ToGroupBean {
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
        return "Message{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", text='" + text + '\'' +
                ", fromUser=" + fromUser +
                ", toGroup=" + toGroup +
                ", emergency=" + emergency +
                ", href='" + href + '\'' +
                '}';
    }
}
