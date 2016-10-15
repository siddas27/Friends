package com.toxoidandroid.friends;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditActivity extends FragmentActivity {

    private final String LOG_TAG = EditActivity.class.getSimpleName();
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
        mContentResolver = EditActivity.this.getContentResolver();

        Intent intent = getIntent();
        final String _id = intent.getStringExtra(FriendsContract.Friends._ID);
        String name = intent.getStringExtra(FriendsContract.Friends.FRIENDS_NAME);
        String phone = intent.getStringExtra(FriendsContract.Friends.FRIENDS_PHONE);
        String email = intent.getStringExtra(FriendsContract.Friends.FRIENDS_EMAIL);

        mNameEdit.setText(name);
        mPhoneEdit.setText(phone);
        mEmailEdit.setText(email);

        save = (Button) findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    ContentValues cv = new ContentValues();
                    cv.put(FriendsContract.Friends.FRIENDS_NAME, mNameEdit.getText().toString());
                    cv.put(FriendsContract.Friends.FRIENDS_PHONE, mPhoneEdit.getText().toString());
                    cv.put(FriendsContract.Friends.FRIENDS_EMAIL, mEmailEdit.getText().toString());
                    Uri uri = FriendsContract.Friends.buildFriendsUri(_id);
                    int updatedRecords = mContentResolver.update(uri, cv, null, null);
                    Log.d(LOG_TAG, "Records updated: " + updatedRecords);
                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please ensure data entered is not empty and valid", Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
            FriendsDialog dialog = new FriendsDialog();
            Bundle args = new Bundle();
            args.putString(FriendsDialog.DIALOG_TYPE, FriendsDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "confirm-exit");
    }

}
