package com.ayazafzal.i170014_i170161;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class callScreenAdapter extends RecyclerView.Adapter<callScreenAdapter.MyViewHolder>{
    List<userData> lX;
    Context c;
    userData currentUser;

    public callScreenAdapter(Context c,List<userData> lX,userData usX) {
        this.lX = lX;
        this.c = c;
        this.currentUser=usX;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(c).inflate(R.layout.call_row,parent,false);
        //Initializes each row for data?
        return new MyViewHolder(item);
    }
    void start(String reX){
        Intent inGX=new Intent(c,chatMessage.class);
        inGX.putExtra("UserId",currentUser.getEmail());
        inGX.putExtra("Sender",currentUser.getId());
        inGX.putExtra("Reciever", reX);
        c.startActivity(inGX);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.contactNameRV7.setText(lX.get(position).getFirstName()+" "+lX.get(position).getLastName());
        holder.contactNumberRV7.setText(lX.get(position).getPhone());
        Bitmap bitmap = BitmapFactory.decodeFile(lX.get(position).getImage());
        holder.contactPictureRV7.setImageBitmap(bitmap);
        holder.recyclerViewRowFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c,
                        "Call Placed to : "+ lX.get(holder.getAdapterPosition()).getFirstName(),
                        Toast.LENGTH_LONG).show();
                putMesageinDB(lX.get(holder.getAdapterPosition()).getId());
                placeCall(lX.get(holder.getAdapterPosition()).getFirstName()+" "+lX.get(holder.getAdapterPosition()).getLastName(),lX.get(holder.getAdapterPosition()).getImage());
            }
        });
    }

    private void placeCall(String name,String path) {
        Intent inX=new Intent(c,CallingScreen.class);
        inX.putExtra("name",name);
        inX.putExtra("image",path);
        c.startActivity(inX);
    }

    @Override
    public int getItemCount() {
        return this.lX.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout recyclerViewRowFriends;
        TextView contactNumberRV7,contactNameRV7;
        CircleImageView contactPictureRV7;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewRowFriends=itemView.findViewById(R.id.recyclerViewRowFriends);
            contactNumberRV7=itemView.findViewById(R.id.contactNumberRV7);
            contactNameRV7=itemView.findViewById(R.id.contactNameRV7);
            contactPictureRV7=itemView.findViewById(R.id.contactPictureRV7);
        }
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
        cV.put(myDBContracts.Messages._CALL,"1");
        cV.put(myDBContracts.Messages._TEXTMESSAGE,"0");
        cV.put(myDBContracts.Messages._IMAGEMESSAGE,"0");
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
    }
}
