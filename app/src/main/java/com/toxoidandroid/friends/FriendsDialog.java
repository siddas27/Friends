package com.toxoidandroid.friends;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class FriendsDialog extends DialogFragment{

    private static final String LOG_TAG = FriendsDialog.class.getSimpleName();
    private LayoutInflater mLayoutInflater;
    public static final String DIALOG_TYPE = "command";
    public static final String DELETE_RECORD = "deleteRecord";
    public static final String DELETE_DATABASE = "deleteDatabase";
    public static final String CONFIRM_EXIT = "confirmExit";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mLayoutInflater = getActivity().getLayoutInflater();
        final View view = mLayoutInflater.inflate(R.layout.friend_dialog, null);
        String command = getArguments().getString(DIALOG_TYPE);
        if (command.equals(DELETE_RECORD)){
            final int _id = getArguments().getInt(FriendsContract.Friends._ID);
            String name = getArguments().getString(FriendsContract.Friends.FRIENDS_NAME);
            ((TextView) view.findViewById(R.id.popup_message)).
                    setText("Are you sure you want to delete " + name + " from your friend list?");
            builder.setView(view).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContentResolver mResolver = getActivity().getContentResolver();
                    Uri uri = FriendsContract.Friends.buildFriendsUri(String.valueOf(_id));
                    mResolver.delete(uri, null, null);
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
        }else if (command.equals(DELETE_DATABASE)){
            ((TextView) view.findViewById(R.id.popup_message)).
                    setText("Are you sure you want to delete the whole database?");
            builder.setView(view).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContentResolver mResolver = getActivity().getContentResolver();
                    mResolver.delete(FriendsContract.URI_TABLE, null, null);
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
        } else if (command.equals(CONFIRM_EXIT)){
            ((TextView) view.findViewById(R.id.popup_message)).
                    setText("Are you sure you want to go back and discard changes?");
            builder.setView(view).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
        }else{
            Log.d(LOG_TAG, "Inalid command passed as parameter");
        }
        return builder.create();
    }
}
