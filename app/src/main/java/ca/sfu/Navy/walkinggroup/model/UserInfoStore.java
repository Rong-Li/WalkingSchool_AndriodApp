package ca.sfu.Navy.walkinggroup.model;

public class UserInfoStore {
    private static Long id;
    private String name;
    private String email;
    private String token;

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getToken(){
        return token;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {
        this.email = email;
    }
    public void setToken(String token){
        this.token = token;
    }
}
