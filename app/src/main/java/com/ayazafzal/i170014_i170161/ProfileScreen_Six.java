package com.ayazafzal.i170014_i170161;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;

public class ProfileScreen_Six extends AppCompatActivity {
    String Email,Pass,FirstName,LastName,Gender,Bio,Phone;
    EditText newFirstName,newLastName,newAccountGender,newAccountBio,newAccountPhone;
    Button takeImageCamera,pickImageGallery;
    int CAMERA_REQUEST = 1888;
    int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int PICK_FROM_GALLERY = 1;
//    SharedPreferences myShare;
//    SharedPreferences.Editor myEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen_six);
        //refresSQLLITE();
        Intent inX=getIntent();
        Email=inX.getStringExtra("Email");
        Pass=inX.getStringExtra("Password");
        newFirstName=findViewById(R.id.newFirstName);
        newLastName=findViewById(R.id.newLastName);
        newAccountGender=findViewById(R.id.newAccountGender);
        newAccountBio=findViewById(R.id.newAccountBio);
        takeImageCamera=findViewById(R.id.takeImageCamera);
        pickImageGallery=findViewById(R.id.pickImageGallery);
        newAccountPhone=findViewById(R.id.newAccountPhone);
        takeImageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveConstData()) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            }
        });
        pickImageGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveConstData()){
                    if (ActivityCompat.checkSelfPermission(ProfileScreen_Six.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ProfileScreen_Six.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                    }
                }
            }
        });

    }
    private boolean saveConstData(){
        FirstName=newFirstName.getText().toString();
        LastName=newLastName.getText().toString();
        Gender=newAccountGender.getText().toString();
        Bio=newAccountBio.getText().toString();
        Phone=newAccountPhone.getText().toString();
        if(!Phone.isEmpty()){
            return true;
        }
        return false;
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
        }else if(requestCode == PICK_FROM_GALLERY){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getFilePath(Intent data) {
        String imagePath;
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imagePath = cursor.getString(columnIndex);
        cursor.close();

        return imagePath;

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
    private boolean addUser(com.ayazafzal.i170014_i170161.userData Obj){
        myDBHelper helper=new myDBHelper(ProfileScreen_Six.this);
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues cV=new ContentValues();
        cV.put(myDBContracts.Users._EMAIL,Obj.getEmail());
        cV.put(myDBContracts.Users._PASSWORD,Obj.getPass());
        cV.put(myDBContracts.Users._FIRSTNAME,Obj.getFirstName());
        cV.put(myDBContracts.Users._LASTNAME,Obj.getLastName());
        cV.put(myDBContracts.Users._GENDER,Obj.getGender());
        cV.put(myDBContracts.Users._BIO,Obj.getBio());
        cV.put(myDBContracts.Users._STATUS,Obj.getStatus());
        cV.put(myDBContracts.Users._PHONE,Obj.getPhone());
        Log.d("Image",Obj.getImage());
        cV.put(myDBContracts.Users._IMAGE,Obj.getImage());
        double res=database.insert(myDBContracts.Users.TABLENAME,null,cV);
        database.close();
        helper.close();
        finish();
        if (res>0){
            Toast.makeText(ProfileScreen_Six.this,
                    "Data Added",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            Toast.makeText(ProfileScreen_Six.this,
                    "Error",
                    Toast.LENGTH_LONG).show();
            return false;

        }

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri tempUri = getImageUri(getApplicationContext(), photo);
            String imagePath = getFilePath2(tempUri);
            userData usX=new userData("tempID",Email,Pass,FirstName,LastName,Gender,Bio,"1",Phone,imagePath);
            if(addUser(usX)){
                /*myShare=getSharedPreferences("com.ayazafzal.i170014_i170161",MODE_PRIVATE);
                myEditor=myShare.edit();
                myEditor.putString("email",Email);
                myEditor.putString("email",Pass);
                myEditor.commit();
                myEditor.apply();*/
                Intent inX=new Intent(ProfileScreen_Six.this, Home_Five.class);
                inX.putExtra("UserId",usX.getEmail());
                startActivity(inX);
            }else{
                Toast.makeText(ProfileScreen_Six.this,
                        "Error Occurred",
                        Toast.LENGTH_LONG).show();
            }
            //showImage.setImageBitmap(photo);
        }else if (requestCode == PICK_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            String imagePath = getFilePath(data);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            userData usX=new userData("tempID",Email,Pass,FirstName,LastName,Gender,Bio,"1",Phone,imagePath);
            //showImage.setImageBitmap(bitmap);
            if(addUser(usX)){
                Intent inX=new Intent(ProfileScreen_Six.this, Home_Five.class);
                inX.putExtra("UserId",usX.getEmail());
                startActivity(inX);
            }else{
                Toast.makeText(ProfileScreen_Six.this,
                        "Error Occurred",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    public void refresSQLLITE(){
        myDBHelper helper=new myDBHelper(ProfileScreen_Six.this);
        SQLiteDatabase db=helper.getReadableDatabase();
        String CREATE_USERS_TABLE="CREATE TABLE "+
                myDBContracts.Users.TABLENAME+"("+
                myDBContracts.Users._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                myDBContracts.Users._EMAIL+ " TEXT NOT NULL, "+
                myDBContracts.Users._PASSWORD+ " TEXT NOT NULL, "+
                myDBContracts.Users._FIRSTNAME+ " TEXT NOT NULL, "+
                myDBContracts.Users._LASTNAME+ " TEXT NOT NULL, "+
                myDBContracts.Users._GENDER+ " TEXT NOT NULL, "+
                myDBContracts.Users._BIO+ " TEXT NOT NULL, "+
                myDBContracts.Users._STATUS+ " TEXT NOT NULL, "+
                myDBContracts.Users._PHONE+ " TEXT NOT NULL, "+
                myDBContracts.Users._IMAGE+ " TEXT);";
        String DELETE_USERS_TABLE="DROP TABLE IF EXISTS "+ com.ayazafzal.i170014_i170161.myDBContracts.Users.TABLENAME;
        String CREATE_FRIENDS_TABLE="CREATE TABLE "+
                myDBContracts.Friends.TABLENAME+"("+
                myDBContracts.Friends._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                myDBContracts.Friends._ID1+ " TEXT NOT NULL, "+
                myDBContracts.Friends._ID2+ " TEXT NOT NULL);";
        String DELETE_FRIENDS_TABLE="DROP TABLE IF EXISTS "+ com.ayazafzal.i170014_i170161.myDBContracts.Friends.TABLENAME;
        String CREATE_MESSAGES_TABLE="CREATE TABLE "+
                myDBContracts.Messages.TABLENAME+"("+
                myDBContracts.Messages._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                myDBContracts.Messages._SENDER+ " TEXT NOT NULL, "+
                myDBContracts.Messages._RECEIVER+ " TEXT NOT NULL, "+
                myDBContracts.Messages._HOUR+ " TEXT NOT NULL, "+
                myDBContracts.Messages._MINUTE+ " TEXT NOT NULL, "+
                myDBContracts.Messages._CALL+ " TEXT, "+
                myDBContracts.Messages._TEXTMESSAGE+ " TEXT, "+
                myDBContracts.Messages._IMAGEMESSAGE+ " TEXT, "+
                myDBContracts.Messages._SCREENSHOT+ " TEXT);";
        String DELETE_MESSAGES_TABLE="DROP TABLE IF EXISTS "+ myDBContracts.Messages.TABLENAME;
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_FRIENDS_TABLE);
        db.execSQL(CREATE_MESSAGES_TABLE);
//        db.execSQL(DELETE_USERS_TABLE);
//        db.execSQL(DELETE_FRIENDS_TABLE);
//        db.execSQL(DELETE_MESSAGES_TABLE);
        Log.d("refreshDB","All Theables Deleted");
        Log.d("refreshDB","All Tyrables Deleted");
        Log.d("refreshDB","All Tuiables Deleted");
    }

}