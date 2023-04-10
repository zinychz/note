package com.izi.note.common;

public class Constants {

    private Constants() {
    }

    public static class URL {

        private URL() {
        }

        public static final String NOTES_CONTROLLER_ROOT_URL = "/";
        public static final String NOTE = "/note";
        public static final String NOTE_ID = "/note/{id}";
        public static final String LIKE = "/like";
        public static final String DISLIKE = "/dislike";
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/register";
    }

    public static class PathVariable {

        private PathVariable() {
        }

        public static final String ID = "id";
    }

    public static class ROLE {

        private ROLE() {
        }

        public static final String ROLE_ANONYMOUS = "ANONYMOUS";
        public static final String ROLE_USER = "USER";
    }
}
