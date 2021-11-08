package com.ayazafzal.i170014_i170161;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class callScreen extends AppCompatActivity {
    userData currentUser;
    List<userData> friends;
    CircleImageView currentUserImage7;
    TextView currentUserName7;
    RecyclerView recyclerViewScreen7;
    ImageView chatScreen5;
    RecyclerView recyclerViewScreenCall;
    callScreenAdapter myAdapter;
    ImageView cameraScreen5,contactsScreen5;
    int CAMERA_REQUEST = 1888;
    int MY_CAMERA_PERMISSION_CODE = 100;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_screen);
        Bundle extras = getIntent().getExtras();
        value = extras.getString("email");
        currentUser=getCurrentUser(value);
        friends=getAllfriends(currentUser.getId());
        currentUserImage7=findViewById(R.id.currentUserImage7);
        Bitmap bitmap = BitmapFactory.decodeFile(currentUser.getImage());
        currentUserImage7.setImageBitmap(bitmap);
        currentUserName7=findViewById(R.id.currentUserName7);
        currentUserName7.setText(currentUser.getFirstName()+" "+currentUser.getLastName());
        recyclerViewScreen7=findViewById(R.id.recyclerViewScreenCall);
        RecyclerView.LayoutManager myManager=new LinearLayoutManager(callScreen.this);
        recyclerViewScreen7.setLayoutManager(myManager);
        myAdapter=new callScreenAdapter(callScreen.this,friends,currentUser);
        recyclerViewScreen7.setAdapter(myAdapter);
        chatScreen5=findViewById(R.id.chatScreen5);
        chatScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inX=new Intent(callScreen.this, Home_Five.class);
                inX.putExtra("UserId",currentUser.getEmail());
                startActivity(inX);
            }
        });
        cameraScreen5=findViewById(R.id.cameraScreen5);
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
        contactsScreen5=findViewById(R.id.contactsScreen5);
        contactsScreen5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inX=new Intent(callScreen.this,ContactScreen_Seven.class);
                inX.putExtra("email",value);
                startActivity(inX);
            }
        });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getApplicationContext(), photo);
            String imagePath = getFilePath2(tempUri);
            Intent inX=new Intent(callScreen.this,SendImage_Four.class);
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
    @SuppressLint("Range")
    userData getUserBasedOnID(String ID){
        myDBHelper helper=new myDBHelper(callScreen.this);
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
    List<userData> getAllfriends(String cID){
        myDBHelper helper=new myDBHelper(callScreen.this);
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
    userData getCurrentUser(String Email){
        myDBHelper helper=new myDBHelper(callScreen.this);
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