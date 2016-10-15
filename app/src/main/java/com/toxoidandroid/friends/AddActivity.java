package com.toxoidandroid.friends;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends FragmentActivity{

    private final String LOG_TAG = AddActivity.class.getSimpleName();
    private EditText mNameEdit, mEmailEdit, mPhoneEdit;
    private Button save;
    private ContentResolver mContentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mNameEdit = (EditText) findViewById(R.id.editName);
        mPhoneEdit = (EditText) findViewById(R.id.editPhone);
        mEmailEdit = (EditText) findViewById(R.id.editEmail);
        mContentResolver = AddActivity.this.getContentResolver();

        save = (Button) findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    ContentValues cv = new ContentValues();
                    cv.put(FriendsContract.Friends.FRIENDS_NAME, mNameEdit.getText().toString());
                    cv.put(FriendsContract.Friends.FRIENDS_PHONE, mPhoneEdit.getText().toString());
                    cv.put(FriendsContract.Friends.FRIENDS_EMAIL, mEmailEdit.getText().toString());
                    Uri returnedUri = mContentResolver.insert(FriendsContract.URI_TABLE, cv);
                    Log.d(LOG_TAG, "Returned Uri after insert: " + returnedUri.toString());
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please ensure data entered is valid", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean isValid(){
        if (mNameEdit.getText().toString().length() == 0 ||
                mPhoneEdit.getText().toString().length() == 0 ||
                mEmailEdit.getText().toString().length() == 0)
            return false;
        else
            return true;
    }

    public  boolean someDataEntered(){
        if (mNameEdit.getText().toString().length() > 0 ||
                mPhoneEdit.getText().toString().length() > 0 ||
                mEmailEdit.getText().toString().length() > 0)
            return true;
        else
            return false;
    }

    @Override
    public void onBackPressed() {
        if (someDataEntered()) {
            FriendsDialog dialog = new FriendsDialog();
            Bundle args = new Bundle();
            args.putString(FriendsDialog.DIALOG_TYPE, FriendsDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "confirm-exit");
        }else
            super.onBackPressed();
    }
}
