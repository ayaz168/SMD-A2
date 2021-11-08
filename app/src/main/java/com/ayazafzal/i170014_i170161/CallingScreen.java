package com.ayazafzal.i170014_i170161;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallingScreen extends AppCompatActivity {
    String imagePath;
    CircleImageView personPic;
    ImageView goBack;
    String image,name;
    TextView contactName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling_screen);
        goBack=findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle extras = getIntent().getExtras();
        image = extras.getString("image");
        name = extras.getString("name");
        contactName=findViewById(R.id.contactName);
        Log.d("name",name);
        contactName.setText(name);
        Bitmap bitmap = BitmapFactory.decodeFile(image);
        personPic=findViewById(R.id.contactPictureRV8);
        personPic.setImageBitmap(bitmap);


    }
}