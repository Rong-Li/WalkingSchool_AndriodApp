package ca.sfu.Navy.walkinggroup.model;


import java.util.ArrayList;
import java.util.List;

public class PermissionRecord {

    enum PermissionStatus {
        PENDING,
        APPROVED,
        DENIED
    }

    public class Authorizor{
        private List<User> users = new ArrayList<>();
        private PermissionStatus status;
        private User whoApprovedOrDenied;
    }



    private Long id;
    private String action;
    private PermissionStatus status;
    private User userA;
    private User userB;
    private Group groupG;
    private User requestingUser;
    private List<Authorizor> authorizors;
    private String message;
    private String href;

    // getters
    public String getMessage(){
        return message;
    }
    public Long getId(){
        return id;
    }
    public PermissionStatus getStatus(){
        return status;
    }

    // setters
    public void setStatus(String status){
        if(status == "PENDING"){
            this.status = PermissionStatus.PENDING;
        }
        else if(status == "APPROVED"){
            this.status = PermissionStatus.APPROVED;
        }
        else if(status == "DENIED"){
            this.status = PermissionStatus.DENIED;
        }
        else{
            return;
        }
    }



}
