package ca.sfu.Navy.walkinggroup.model;


public class SendMessage {
    /**
     * text : My awesome message here
     * emergency : false
     */

    private String text;
    private boolean emergency;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }
}
