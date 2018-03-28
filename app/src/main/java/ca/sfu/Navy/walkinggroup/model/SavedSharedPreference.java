package ca.sfu.Navy.walkinggroup.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SavedSharedPreference {
    static final String PREF_USER_EMAIL = "User email";
    static final String PREF_USER_PW = "User Password";
    static final String PREF_USER_TOKEN = "User Authorization Token";
    static final String PREF_USER_ID = "User User ID";

    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    // Save user email for log in
    public static void setUserEmail(Context context, String userEmail)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_EMAIL, userEmail);
        editor.commit();
    }

    // Save user pw for log in
    public static void setPrefUserPw(Context context, String userPW){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_PW, userPW);
        editor.commit();
    }

    // Save user token when logged in
    public static void setPrefUserToken(Context context, String userToken){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_TOKEN, userToken);
        editor.commit();
    }

    public static void setPrefUserId(Context context, Long userId){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(PREF_USER_ID, userId);
        editor.commit();
    }

    public static String getPrefUserEmail(Context context)
    {
        return getSharedPreferences(context).getString(PREF_USER_EMAIL, "");
    }

    public static String getPrefUserPw(Context context){
        return getSharedPreferences(context).getString(PREF_USER_PW, "");
    }

    public static String getPrefUserToken(Context context){
        return getSharedPreferences(context).getString(PREF_USER_TOKEN, "");
    }

    public static Long getPrefUserId(Context context){
        return getSharedPreferences(context).getLong(PREF_USER_ID, 0);
    }

    // This function clears the saved user info to log the user out
    public static void clearUserLogged(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.commit();
    }

}
