package com.ayazafzal.i170014_i170161;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn_Three extends AppCompatActivity {
    EditText newEmailScreenThree,newPasswordScreenThree;
    TextView forgotPasswordScreenThree,registerNowThree;
    Button logInScreenThree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_three);
        newEmailScreenThree=findViewById(R.id.newEmailScreenThree);
        newPasswordScreenThree=findViewById(R.id.newPasswordScreenThree);
        forgotPasswordScreenThree=findViewById(R.id.forgotPasswordScreenThree);
        forgotPasswordScreenThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LogIn_Three.this,
                        "Please Contact Admin.",
                        Toast.LENGTH_LONG).show();
            }
        });
        logInScreenThree=findViewById(R.id.logInScreenThree);
        logInScreenThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=newEmailScreenThree.getText().toString();
                String pass=newPasswordScreenThree.getText().toString();
                userData usX=getCurrentUser(email);
                if(usX.getPass().equals(pass)){
                    Intent inXX=new Intent(LogIn_Three.this,Home_Five.class);
                    inXX.putExtra("UserId",email);
                    startActivity(inXX);
                }
                else{
                    Toast.makeText(LogIn_Three.this,
                            "Invalid Credentials, try again..",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        registerNowThree=findViewById(R.id.registerNowThree);
        registerNowThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inX=new Intent(LogIn_Three.this,SignUp_Two.class);
                startActivity(inX);
            }
        });

    }
    @SuppressLint("Range")
    userData getCurrentUser(String Email){
        myDBHelper helper=new myDBHelper(LogIn_Three.this);
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
        return new userData("-1","dummy","dummy","dummy","dummy","dummy","dummy","dummy","dummy","dummy");

    }
}