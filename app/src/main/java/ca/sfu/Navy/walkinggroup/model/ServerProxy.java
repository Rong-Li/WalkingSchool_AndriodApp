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
    //API #3 for Login & Logout
    @POST("/login")
    Call<Void> login(@Body User userWithEmailAndPassword);

    //API #4 for Users
    @POST("/users/signup")
    Call<User> createNewUser(@Body User user);

    @GET("/users")
    Call<List<User>> getUsers();

    @GET("/users/{id}")
    Call<User> getUserById(@Path("id") long userId);

    @DELETE("/users/{id}")
    Call<Void> deleteUserbyId(@Path("id") long userId);

    @GET("/users/byEmail")
    Call<User> getUserByEmail(@Query("email") String email);

    @GET("/users/byEmail")
    Call<String> getUserByEmail11(@Query("email") String email);

    @POST("users/{id}")
    Call<User> editUser(@Path("id") long userId, @Body User userInfo);

    @POST("users/{id}/lastGpsLocation")
    Call<GpsLocation> uploadGps(@Path("id") long userId, @Body GpsLocation lastGpsLocation);

    //API #5 for Monitoring
    @GET("/users/{id}/monitorsUsers")
    Call<List<User>> getmonitorsUsers();

    @GET("/users/{id}/monitoredByUsers")
    Call<List<User>> getmonitoredByUsers();

    @POST("/users/{id}/monitorsUsers")
    Call<List<User>> addUsertoMonitor(@Path("id") long userId, @Body User addMonitor);

    @DELETE("/users/{idA}/monitorsUsers/{idB}")
    Call<Void> cancelmonitor(@Path("idA") long monitorId, @Path("idB") long monitoredId);

    @POST("/users/{id}/monitoredByUsers")
    Call<List<User>> addUsertoMonitoredBy(@Path("id") long userId, @Body User addMonitored);

    @DELETE("/users/{idA}/monitoredByUsers/{idB}")
    Call<Void> cancelMonitoredBy(@Path("idA") long monitoredId, @Path("idB") long monitorId);

    //API #6 for Groups
    @GET("/groups")
    Call<List<Group>> listGroups();

    @POST("/groups")
    Call<Group> createNewGroup(@Body Group group);

    @GET("/groups/{id}")
    Call<Group> getGroupDetails(@Path("id") long groupId);

    @POST("/groups/{id}")
    Call<Group> updateGroupDetails(@Path("id") long groupId, @Body Group group);

    @DELETE("/groups/{id}")
    Call<Void> deleteGroups(@Path("id") Long groupID);

    @GET("/groups/{id}/memberUsers")
    Call<Group> getGroupMenberUsers();

    @POST("/groups/{id}/memberUsers")
    Call<Group> addNewGroupMember(@Path("id") long userID, @Body User user);

    @DELETE("/groups/{groupId}/memberUsers/{userId}")
    Call<Void> removeGroupMember(@Path("groupId") long groupID, @Path("userId") long userID);

    @GET("/groups")
    Call<Void> comminicate();

    //API #7 for In-app Messaging
    @GET("/messages")
    Call<List<Message>> listMessage(@Query("is-emergency") boolean isEmergency, @Query("status") String status);

    @GET("/messages")
    Call<List<Message>> listUserMessage(@Query("foruser") long user_id, @Query("is-emergency") boolean isEmergency, @Query("status") String status);

    @GET("/messages")
    Call<List<Message>> listGroupMessage(@Query("togroup") long group_id, @Query("is-emergency") boolean isEmergency, @Query("status") String status);

    @POST("/messages/togroup/{groupId}")
    Call<Message> messageToGroup(@Path("groupId") long groupId, @Body SendMessage sendMessage);

    @POST("/messages/toparentsof/{userId}")
    Call<Message> messageToParents(@Path("userId") long userId, @Body SendMessage sendMessage);

    @GET("/messages/{id}")
    Call<Message> getMessage();

    @DELETE("/messages/{id}")
    Call<Void> deleteMessage();

    @POST("/messages/{messageId}/readby/{userId}")
    Call<MarkResponse> changeReadStatus(@Path("messageId") long messageId, @Path("userId") long userId, @Body Boolean status);

    //API #8 for Permissions
    @GET("/permissions")
    Call<List<PermissionRequest>> getPermission();

    @GET("/permissions/{id}")
    Call<PermissionRequest> getPermissionsById(@Path("id") long permissionId);

    @POST("/permissions/{id}")
    Call<PermissionRequest> approveOrDenyPermissionRequest(
            @Path("id") long permissionId,
            @Body PermissionStatus status
    );

    enum PermissionStatus {
        PENDING,
        APPROVED,
        DENIED
    }

    @GET("/permissions?status=PENDING")
    Call<List<PermissionRecord>> getPendingRequest();

    @POST("/permissions/{Id}")
    Call<PermissionRecord> requestPost(@Path("Id") long requestId, @Body String status);
}
