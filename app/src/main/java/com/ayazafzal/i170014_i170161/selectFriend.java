package com.ayazafzal.i170014_i170161;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class selectFriend extends AppCompatActivity {
    RecyclerView recyclerViewShowContactX;
    String currenntEmail;
    ImageView currentUserImage7;
    TextView currentUserName7,searchButtonScreen7;
    LinearLayout newContact7,newGroup7;
    List<userData> myFriends,myPFriends,allMyFriends;
    RecyclerView recyclerViewScreen7;
    chatSelectAdapter myAdapter;
    String Sender,Reciever;
    int PERMISSIONS_REQUEST_READ_CONTACTS=10;
    userData currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);
        Bundle extras = getIntent().getExtras();
        currenntEmail= extras.getString("email");
        currentUser=getCurrentUser(currenntEmail);
        currentUserImage7=findViewById(R.id.currentUserImage7);
        currentUserImage7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inX=new Intent(selectFriend.this,Home_Five.class);
                inX.putExtra("UserId",currenntEmail);
                startActivity(inX);
            }
        });
        currentUserName7=findViewById(R.id.currentUserName7);
        currentUserName7.setText((currentUser.getFirstName()+" "+currentUser.getLastName()));
        searchButtonScreen7=findViewById(R.id.editButtonScreen7);
        searchButtonScreen7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Try search
            }
        });
        myFriends=getAllfriends(currentUser.getId());
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);


    }
    public List<userData> getFilteredContactList(List<userData> fromPhone, List<userData> fromDB){
        List<userData> finalFriends=new ArrayList<userData>();
        for(userData usX: fromPhone){
            userData idX=checkSimilar(usX.Phone,fromDB);
            if(idX!=null){
                finalFriends.add(idX);
            }
        }
        return finalFriends;
    }
    public userData checkSimilar(String phone,List<userData> fromDB){
        for(userData usX: fromDB){
            if(usX.Phone.contains(phone.replace(" ",""))){
                return usX;
            }
        }
        return null;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(selectFriend.this, "Permission Given !", Toast.LENGTH_SHORT).show();
                myPFriends=getAllContacts(selectFriend.this);
                Log.d("Size", String.valueOf(myPFriends.size()));
                allMyFriends=getFilteredContactList(myPFriends,myFriends);
                recyclerViewScreen7=findViewById(R.id.recyclerViewShowContactX);
                RecyclerView.LayoutManager myManager=new LinearLayoutManager(selectFriend.this);
                recyclerViewScreen7.setLayoutManager(myManager);
                myAdapter=new chatSelectAdapter(selectFriend.this,allMyFriends,currentUser);
                recyclerViewScreen7.setAdapter(myAdapter);
            } else {
                Toast.makeText(selectFriend.this, "Without Permission Contacts won't be synced", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("Range")
    public List<userData> getAllContacts(Context ctx) {
        Log.d("CX","here1");
        List<userData> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                if(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)>0){
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String id = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.d("CX",name);
                        Log.d("CX", phoneNo);
                        contacts.add(new userData(name,phoneNo));
                    }
                    pCur.close();
                }
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();
        return contacts;
    }
    @SuppressLint("Range")
    List<userData> getAllfriends(String cID){
        myDBHelper helper=new myDBHelper(selectFriend.this);
        SQLiteDatabase db=helper.getReadableDatabase();
        String[] projection=new String[]{
                myDBContracts.Friends._ID,
                myDBContracts.Friends._ID1,
                myDBContracts.Friends._ID2
        };
        String selection = myDBContracts.Friends._ID1 + "=?";
        String[] selectionArgs = new String[]{ cID };
        Cursor dataSet=db.query(myDBContracts.Friends.TABLENAME,projection,selection,selectionArgs,null,null,null);
        List<userData> friendsList=new ArrayList<>();
        while(dataSet.moveToNext()){
            friendsList.add(getUserBasedOnID(dataSet.getString(dataSet.getColumnIndex(myDBContracts.Friends._ID2))));
        }
        Log.d("Cursor", String.valueOf(dataSet.getCount()));
        return friendsList;
    }
    @SuppressLint("Range")
    userData getUserBasedOnID(String ID){
        myDBHelper helper=new myDBHelper(selectFriend.this);
        SQLiteDatabase db=helper.getReadableDatabase();
        String[] projection=new String[]{
                myDBContracts.Users._ID,
                myDBContracts.Users._EMAIL,
                myDBContracts.Users._PASSWORD,
                myDBContracts.Users._FIRSTNAME,
                myDBContracts.Users._LASTNAME,
                myDBContracts.Users._GENDER,
                myDBContracts.Users._BIO,
                myDBContracts.Users._STATUS,
                myDBContracts.Users._PHONE,
                myDBContracts.Users._IMAGE
        };
        String selection = myDBContracts.Users._ID + "=?";
        String[] selectionArgs = new String[]{ ID };
        Cursor dataSet=db.query(myDBContracts.Users.TABLENAME,projection,selection,selectionArgs,null,null,null);
        userData userX;
        while(dataSet.moveToNext()){
            @SuppressLint("Range") Bitmap photo= ImageUtility.decodeBase64(dataSet.getBlob(dataSet.getColumnIndex(myDBContracts.Users._IMAGE)).toString());
            userX=new userData(
                    dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._ID)),
                    dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._EMAIL)),
                    dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._PASSWORD)),
                    dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._FIRSTNAME)),
                    dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._LASTNAME)),
                    dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._GENDER)),
                    dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._BIO)),
                    dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._STATUS)),
                    dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._PHONE)),
                    dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._IMAGE))
            );
            return userX;
        }
        return null;
    }
    @SuppressLint("Range")
    userData getCurrentUser(String Email){
        myDBHelper helper=new myDBHelper(selectFriend.this);
        SQLiteDatabase db=helper.getReadableDatabase();
        String sort= myDBContracts.Users._FIRSTNAME+" ASC";
        String[] projection=new String[]{
                myDBContracts.Users._ID,
                myDBContracts.Users._EMAIL,
                myDBContracts.Users._PASSWORD,
                myDBContracts.Users._FIRSTNAME,
                myDBContracts.Users._LASTNAME,
                myDBContracts.Users._GENDER,
                myDBContracts.Users._BIO,
                myDBContracts.Users._STATUS,
                myDBContracts.Users._PHONE,
                myDBContracts.Users._IMAGE
        };
        Cursor dataSet=db.query(myDBContracts.Users.TABLENAME,projection,null,null,null,null,null);
        Log.d("Cursor", String.valueOf(dataSet.getCount()));
        userData userX;
        while(dataSet.moveToNext()){
            if(dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._EMAIL)).equals(Email)){
                Bitmap photo= ImageUtility.decodeBase64(dataSet.getBlob(dataSet.getColumnIndex(myDBContracts.Users._IMAGE)).toString());
                userX=new userData(
                        dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._ID)),
                        dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._EMAIL)),
                        dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._PASSWORD)),
                        dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._FIRSTNAME)),
                        dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._LASTNAME)),
                        dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._GENDER)),
                        dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._BIO)),
                        dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._STATUS)),
                        dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._PHONE)),
                        dataSet.getString(dataSet.getColumnIndex(myDBContracts.Users._IMAGE))
                );
                return userX;
            }
        }
        return null;

    }
}