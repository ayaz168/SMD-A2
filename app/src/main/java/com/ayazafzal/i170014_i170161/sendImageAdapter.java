package com.ayazafzal.i170014_i170161;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class sendImageAdapter extends RecyclerView.Adapter<sendImageAdapter.MyViewHolder>{
    Context c;
    List<userData> lX;
    String Path;
    userData currentUser;

    public sendImageAdapter(Context c, List<userData> lx, userData currentUser,String path) {
        this.c = c;
        this.lX = lx;
        this.currentUser = currentUser;
        this.Path=path;
    }

    @NonNull
    @Override
    public sendImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(c).inflate(R.layout.send_image_row,parent,false);
        //Initializes each row for data?
        return new sendImageAdapter.MyViewHolder(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull sendImageAdapter.MyViewHolder holder, int position) {
        Bitmap bitmap = BitmapFactory.decodeFile(lX.get(position).getImage());
        holder.contactPictureRV7.setImageBitmap(bitmap);
        Log.d("ix",lX.get(holder.getAdapterPosition()).getFirstName()+" "+lX.get(holder.getAdapterPosition()).getLastName());
        holder.contactNameRV7.setText(lX.get(holder.getAdapterPosition()).getFirstName()+" "+lX.get(holder.getAdapterPosition()).getLastName());
        holder.contactClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putMesageinDB(lX.get(holder.getAdapterPosition()).getId());
            }
        });
    }

    public void putMesageinDB(String recV) {
        //messageData msX=new messageData()
        myDBHelper helper=new myDBHelper(c);
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues cV=new ContentValues();
        Date currentTime = Calendar.getInstance().getTime();
        int hour=currentTime.getHours();
        int min = currentTime.getMinutes();
        cV.put(myDBContracts.Messages._SENDER,currentUser.getId());
        cV.put(myDBContracts.Messages._RECEIVER,recV);
        cV.put(myDBContracts.Messages._HOUR,String.valueOf(hour));
        cV.put(myDBContracts.Messages._MINUTE,String.valueOf(min));
        cV.put(myDBContracts.Messages._CALL,"0");
        cV.put(myDBContracts.Messages._TEXTMESSAGE,"0");
        cV.put(myDBContracts.Messages._IMAGEMESSAGE,this.Path);
        cV.put(myDBContracts.Messages._SCREENSHOT,"0");


        double res=database.insert(myDBContracts.Messages.TABLENAME,null,cV);
        if (res>0){
            Toast.makeText(c,
                    "Message Sent",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(c,
                    "Message Not Sent, Error",
                    Toast.LENGTH_LONG).show();

        }
        database.close();
        helper.close();
        Intent inGX=new Intent(c,Home_Five.class);
        inGX.putExtra("UserId",currentUser.getEmail());
        c.startActivity(inGX);

    }

    @Override
    public int getItemCount() {
        return this.lX.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView contactPictureRV7;
        TextView contactNameRV7;
        LinearLayout contactClick;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contactPictureRV7=itemView.findViewById(R.id.contactPictureRV7);
            contactNameRV7=itemView.findViewById(R.id.contactNameRV7);
            contactClick=itemView.findViewById(R.id.contactClick);
        }
    }
}
