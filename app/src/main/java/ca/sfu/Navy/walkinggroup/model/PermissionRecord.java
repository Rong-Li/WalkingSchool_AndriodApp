package ca.sfu.Navy.walkinggroup.model;


public class PermissionRecord {

    enum PermissionStatus {
        PENDING,
        APPROVED,
        DENIED
    }

    private String message;
    private Long id;
    private PermissionStatus status;

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
