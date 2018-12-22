package com.example.egor.eventrew;

public final class Settings {
    private static String serverAddress = /*"http://10.0.2.2:8080"*/ "http://192.168.43.208:8080";
    public static Integer userId;
    public static String token;

    public static final class ServiceURL {
        public static final String LOGIN = "/authorization";
        public static final String REGISTER = "/registration";
        public static final String GET_EVENTS = "/geteventsbyamount/100";
        public static final String GET_MY_EVENTS = "/getmyevents";
        public static final String ADD_EVENT = "/addevent";
        public static final String EVENT_REGISTER = "/eventregister/";
        public static final String LOG_OUT = "/logout";
    }

    public static String getServerAddress() {
        return serverAddress;
    }
}
