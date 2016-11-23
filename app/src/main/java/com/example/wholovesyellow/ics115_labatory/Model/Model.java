package com.example.wholovesyellow.ics115_labatory.Model;

import java.util.ArrayList;

/**
 * Created by User on 11/23/2016.
 */

public class Model {
    private static String token;
    private static int userType;
    private static ArrayList<String> requestList;

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

    public static ArrayList<String> getRequestList() {
        return requestList;
    }

    public static void setRequestList(ArrayList<String> requestList) {
        Model.requestList = requestList;
    }


}
