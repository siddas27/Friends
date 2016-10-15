package com.toxoidandroid.friends;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class FriendsContract {

    public static final String CONTENT_AUTHORITY = "com.toxoidandroid.friends";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_FRIENDS = "friends";

    public static final Uri URI_TABLE = Uri.parse(BASE_CONTENT_URI.toString() + "/" + PATH_FRIENDS);

     public static final String[] TOP_LEVEL_PATHS = {
             PATH_FRIENDS
     };

    public static class Friends implements BaseColumns{

        public static final String FRIENDS = "friends";
        public static final String FRIENDS_ID = "_id";
        public static final String FRIENDS_NAME = "friends_name";
        public static final String FRIENDS_PHONE = "friends_phone";
        public static final String FRIENDS_EMAIL = "friends_email";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().
                appendEncodedPath(PATH_FRIENDS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FRIENDS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FRIENDS;

        public static Uri buildFriendsUri(String friendId){
            return CONTENT_URI.buildUpon().appendEncodedPath(friendId).build();
        }

        public static String getFriendId(Uri uri){
            return uri.getPathSegments().get(1);
        }

    }

}
