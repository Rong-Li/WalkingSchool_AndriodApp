package ca.sfu.Navy.walkinggroup.model;

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

    @GET("/users/{id}")
    Call<User> getUserById(@Path("id") long userId);

    @GET("/users/byEmail")
    Call<User> getUserByEmail(@Query("email") String email);

    @POST("/users/{id}/monitorsUsers")
    Call<List<User>> addUsertoMonitor(@Path("id") long userId, @Body User addUserModel);

    @DELETE("/users/{idA}/monitorsUsers/{idB}")
    Call<Void> cancelmonitor(@Path("idA") long monitorId, @Path("idB") long monitoredId);

/**
    @GET("/users/{id}/monitorsUsers")
    Call<User> getMonitorsUsers(@Path("id") Long userId);

    @GET("/users/{id}/monitoredByUsers")
    Call<User> getMonitorsByUsers(@Path("id") Long userId);

    @POST("/users/{id}/monitorsUsers")
    Call<User> monitorsOthers(@Body User user);

    @DELETE("/users/{idA}/monitorsUsers/{idB}")
    Call<User> deleteMonitors();

    @GET("/groups")
    Call<List<User>> getGroups();

    @POST("/groups")
    Call<User> creatNewGroup(@Path("Leader") Long userIdr, @Query("groupDescription") String groupDescription);

    @GET("/groups/{id}")
    Call<User> getGroupDetails();

    @POST("/groups/{id}")
    Call<User> updateGroupDetails();

    @DELETE("/groups/{id}")
    Call<User> deleteGroups();

    @GET("/groups/{id}/memberUsers")
    Call<List<User>> getGroupMenberUsers();

    @POST("/groups/{id}/memberUsers")
    Call<User> addNewGroupMember(@Path("id") Long userId);

    @DELETE("/groups/{groupId}/memberUsers/{userId}")
    Call<User> removeGroupMember(@Path("groupId") Long groupIDr, @Path("userId") Long userIDr);
*/
}
