package ca.sfu.Navy.walkinggroup.model;

public interface HandleMsgStatusListener {
    void read(long msgId, long userId, boolean status);
}
