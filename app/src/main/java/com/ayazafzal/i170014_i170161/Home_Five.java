package com.ayazafzal.i170014_i170161;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Home_Five extends AppCompatActivity {
    RecyclerView recycleView;
    newUserAdapter myAdapter;
    List<userData> myFriends;
    List<userData> listX=new ArrayList();
    ImageView callScreen5,cameraScreen5,chatScreen5,contactsScreen5,writeNewMessgeScreen5,searchScreen5;
    int CAMERA_REQUEST = 1888;
    int MY_CAMERA_PERMISSION_CODE = 100;
    userData currentUser;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_five);
        Bundle extras = getIntent().getExtras();
        value = extras.getString("UserId");
        SharedPreferences prefs = this.getSharedPreferences(
                "com.ayazafzal.i170014_i170161", Context.MODE_PRIVATE);
        String email = "email";
        prefs.edit().putString(email, value).apply();

        currentUser=getCurrentUser(value);
        callScreen5=findViewById(R.id.callScreen5);
        cameraScreen5=findViewById(R.id.cameraScreen5);
        chatScreen5=findViewById(R.id.chatScreen5);
        contactsScreen5=findViewById(R.id.contactsScreen5);
        searchScreen5=findViewById(R.id.searchScreen5);
        searchScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home_Five.this,
                        "No fearch functionality yet.",
                        Toast.LENGTH_LONG).show();
            }
        });
        callScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inX=new Intent(Home_Five.this,callScreen.class);
                inX.putExtra("email",currentUser.getEmail());
                startActivity(inX);
            }
        });
        cameraScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
            }
        });
        contactsScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inX=new Intent(Home_Five.this,ContactScreen_Seven.class);
                inX.putExtra("email",currentUser.getEmail());
                startActivity(inX);
            }
        });
        writeNewMessgeScreen5=findViewById(R.id.writeNewMessgeScreen5);
        writeNewMessgeScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inGX=new Intent(Home_Five.this,selectFriend.class);
                inGX.putExtra("email",currentUser.getEmail());
                startActivity(inGX);
            }
        });
        listX.clear();
        getDbData();
        Log.d("User1", String.valueOf(listX.size()));
        myFriends=getAllfriends(currentUser.getId());
        recycleView=findViewById(R.id.recyclerViewScreen5);
        myAdapter=new newUserAdapter(this,myFriends,currentUser);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(myAdapter);


    }
    @SuppressLint("Range")
    List<userData> getAllfriends(String cID){
        myDBHelper helper=new myDBHelper(Home_Five.this);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getApplicationContext(), photo);
            String imagePath = getFilePath2(tempUri);
            Intent inX=new Intent(Home_Five.this,SendImage_Four.class);
            inX.putExtra("Image",imagePath);
            inX.putExtra("email",this.value);
            startActivity(inX);
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private String getFilePath2(Uri selectedImage) {
        String imagePath;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imagePath = cursor.getString(columnIndex);
        cursor.close();

        return imagePath;

    }
    @SuppressLint("Range")
    void getDbData(){
        myDBHelper helper=new myDBHelper(Home_Five.this);
        SQLiteDatabase db=helper.getReadableDatabase();
        //db.execSQL("DROP TABLE IF EXISTS "+myDBContracts.Users.TABLENAME);
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
        while(dataSet.moveToNext()){
            Bitmap photo= ImageUtility.decodeBase64(dataSet.getBlob(dataSet.getColumnIndex(myDBContracts.Users._IMAGE)).toString());
            userData userX=new userData(
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
            listX.add(userX);
            userX.DisplayUser();
            Log.d("Read ID: ",userX.getId());
            Log.d("Read ID: ",userX.getEmail());
            Log.d("Read ID: ",userX.getFirstName());
            Log.d("Read ID: ",userX.getPhone());

        }
    }
    @SuppressLint("Range")
    userData getUserBasedOnID(String ID){
        myDBHelper helper=new myDBHelper(Home_Five.this);
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
        myDBHelper helper=new myDBHelper(Home_Five.this);
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
//    @Override
//    protected void onResume(){
//        super.onResume();
//        getDbData();
//
//    }
}