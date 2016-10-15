package com.toxoidandroid.friends;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FriendsSearchListLoader extends AsyncTaskLoader<List<Friend>> {

    private static final String LOG_TAG = FriendsSearchListLoader.class.getSimpleName();
    private List<Friend> mFriends;
    private ContentResolver mContentResolver;
    private Cursor mCursor;
    private String mFilterText;
    private Uri mUri;

    public FriendsSearchListLoader(Context context, Uri uri, ContentResolver contentResolver, String filterText) {
        super(context);
        mContentResolver = contentResolver;
        mUri = uri;
        mFilterText = filterText;
    }

    @Override
    public List<Friend> loadInBackground() {
        String[] projections = {FriendsContract.Friends._ID,
                FriendsContract.Friends.FRIENDS_NAME,
                FriendsContract.Friends.FRIENDS_PHONE,
                FriendsContract.Friends.FRIENDS_EMAIL};
        List<Friend> entries = new ArrayList<>();

        String selection = FriendsContract.Friends.FRIENDS_NAME + " LIKE '%" + mFilterText + "%'";

        mCursor = mContentResolver.query(mUri, projections, selection, null, null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {

                    int _id = mCursor.getInt(mCursor.getColumnIndex(FriendsContract.Friends._ID));
                    String name = mCursor.getString(mCursor.getColumnIndex(FriendsContract.Friends.FRIENDS_NAME));
                    String phone = mCursor.getString(mCursor.getColumnIndex(FriendsContract.Friends.FRIENDS_PHONE));
                    String email = mCursor.getString(mCursor.getColumnIndex(FriendsContract.Friends.FRIENDS_EMAIL));

                    Friend friend = new Friend(_id, name, phone, email);
                    entries.add(friend);

                } while (mCursor.moveToNext());
            }
        }
        return entries;
    }

    @Override
    public void deliverResult(List<Friend> data) {
        if (isReset()){
            if (data != null)
                mCursor.close();
        }
        List<Friend> oldFriendList = mFriends;
        if (mFriends == null || mFriends.size() == 0){
            Log.d(LOG_TAG, " No Data returned");
        }
        mFriends = data;
        if (isStarted()){
            super.deliverResult(data);
        }
        if (oldFriendList != null && oldFriendList != data){
            mCursor.close();
        }

    }

    @Override
    protected void onStartLoading() {
        if (mFriends != null) {
            deliverResult(mFriends);
        }

        if (takeContentChanged() || mFriends == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if (mCursor != null)
            mCursor.close();
        mFriends = null;
    }

    @Override
    public void onCanceled(List<Friend> data) {
        super.onCanceled(data);
        if (mCursor != null)
            mCursor.close();
    }

    @Override
    public void forceLoad() {
        super.forceLoad();
    }
}
