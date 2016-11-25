package com.example.wholovesyellow.ics115_labatory.Model;

import java.util.ArrayList;

/**
 * Created by User on 11/23/2016.
 */

public class Model {
    private static String token;
    private static int userType;
    private static ArrayList<String> requestList;
    private static String username;
    private static String position;
    private static String fullname;
    private static int userId;


    public Model(){}

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Model.token = token;
    }

    public static int getUserType() {
        return userType;
    }

    public static void setUserType(int userType) {
        Model.userType = userType;
    }

    public static void setRequestList(ArrayList<String> requestList) {
        Model.requestList = requestList;
    }

    public static ArrayList<String> getRequestList() {
        return requestList;
    }

    public static String getFullname() {
        return fullname;
    }

    public static void setFullname(String fullname) {
        Model.fullname = fullname;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Model.username = username;
    }

    public static String getPosition() {
        return position;
    }

    public static void setPosition(String position) {
        Model.position = position;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        Model.userId = userId;
    }


}
