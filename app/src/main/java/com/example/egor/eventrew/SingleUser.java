package com.example.egor.eventrew;

import com.example.egor.eventrew.model.User;

public class SingleUser {
        private SingleUser(){
            throw new AssertionError();
        }

        private static User user = new User();

        public static User getUser() {
            return user;
        }

        public static void setUser(User user) {
            SingleUser.user = user;
        }
}
