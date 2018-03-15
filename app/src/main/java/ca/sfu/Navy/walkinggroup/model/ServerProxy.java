package ca.sfu.Navy.walkinggroup.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerProxy {
    @POST("/users/signup")
    Call<User> createNewUser(@Body User user);

    @POST("/login")
    Call<Void> login(@Body User userWithEmailAndPassword);

    @GET("/users")
    Call<List<User>> getUsers();

    @GET("/users")
    Call<User> getUserDetails();

    @GET("/users/{id}")
    Call<User> getUserById(@Path("id") Long userId);

    @GET("/users/byEmail")
    Call<User> getUserByEmail(@Query("email") String email);

     @GET("/users/{id}/monitorsUsers")
    Call<List<User>> getMonitorsUsers(@Path("id") Long userId);

    @GET("/users/{id}/monitoredByUsers")
    Call<List<User>> getMonitorsByUsers(@Path("id") Long userId);

    @POST("/users/{id}/monitorsUsers")
    Call<User> monitorsOthers(@Body User user);

    @DELETE("/users/{idA}/monitorsUsers/{idB}")
    Call<User> deleteMonitors();

    @GET("/groups")
    Call<Group> getGroups();

    @POST("/groups")
    Call<Group> createNewGroup(@Body Group group);

    @GET("/groups/{id}")
    Call<Group> getGroupDetails(@Path("id") Long groupID);

    @POST("/groups/{id}")
    Call<Group> updateGroupDetails(@Path("id") Long groupID);

    @DELETE("/groups/{id}")
    Call<Group> deleteGroups(@Path("id") Long groupID);

    @GET("/groups/{id}/memberUsers")
    Call<Group> getGroupMenberUsers();

    @POST("/groups/{id}/memberUsers")
    Call<Group> addNewGroupMember(@Path("id") Long userID);

    @DELETE("/groups/{groupId}/memberUsers/{userId}")
    Call<Group> removeGroupMember(@Path("groupId") Long groupID, @Path("userId") Long userID);
}
