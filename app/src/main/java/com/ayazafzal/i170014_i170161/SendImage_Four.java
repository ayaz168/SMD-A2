package com.ayazafzal.i170014_i170161;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SendImage_Four extends AppCompatActivity {
    ImageView justCaptured;
    RecyclerView showContactScreen4;
    sendImageAdapter myAdapter;
    userData currentUser;
    List<userData> myFriends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image_four);
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("Image");
        String value2 = extras.getString("email");
        justCaptured=findViewById(R.id.justCaptured);
        Bitmap bitmap = BitmapFactory.decodeFile(value);
        justCaptured.setImageBitmap(bitmap);
        currentUser=getCurrentUser(value2);
        Log.d("ix",currentUser.getEmail());
        myFriends=getAllfriends(currentUser.getId());
        Log.d("ix",myFriends.get(0).getEmail());
        showContactScreen4=findViewById(R.id.showContactScreen4);
        myAdapter=new sendImageAdapter(this,myFriends,currentUser,value);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        showContactScreen4.setLayoutManager(layoutManager);
        showContactScreen4.setAdapter(myAdapter);

    }
    @SuppressLint("Range")
    List<userData> getAllfriends(String cID){
        myDBHelper helper=new myDBHelper(SendImage_Four.this);
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
        myDBHelper helper=new myDBHelper(SendImage_Four.this);
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
        myDBHelper helper=new myDBHelper(SendImage_Four.this);
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