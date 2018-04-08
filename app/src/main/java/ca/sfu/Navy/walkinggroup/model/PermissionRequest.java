package ca.sfu.Navy.walkinggroup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionRequest {
    protected Long id;
    protected String href;
    private String action;
    private ServerProxy.PermissionStatus status;
    private User userA;
    private User userB;
    private Group groupG;
    private User requestingUser;
    private Set<Authorizor> authorizors;
    private String message;


    public static class Authorizor {
        private Long id;
        private Set<User> users;
        private ServerProxy.PermissionStatus status;
        private User whoApprovedOrDenied;
    }
}
