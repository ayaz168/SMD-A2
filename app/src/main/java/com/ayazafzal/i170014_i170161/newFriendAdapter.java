package com.ayazafzal.i170014_i170161;

import android.content.Context;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class newFriendAdapter extends RecyclerView.Adapter<newFriendAdapter.MyViewHolder>{
    List<userData> lX;
    Context c;

    public newFriendAdapter(Context c,List<userData> lX) {
        this.lX = lX;
        this.c = c;
    }

    @NonNull
    @Override
    public newFriendAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(c).inflate(R.layout.show_friends_7_rv,parent,false);
        //Initializes each row for data?
        return new newFriendAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull newFriendAdapter.MyViewHolder holder, int position) {
        holder.contactNameRV7.setText(lX.get(position).getFirstName()+" "+ lX.get(position).getLastName());
        holder.contactNumberRV7.setText(lX.get(position).getPhone());
        Bitmap bitmap = BitmapFactory.decodeFile(lX.get(position).getImage());
        holder.contactPictureRV7.setImageBitmap(bitmap);
        holder.recyclerViewRowFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c,
                        "Contact Presses",
                        Toast.LENGTH_LONG).show();
            }
        });
        holder.recyclerViewRowFriends.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(c,
                        "Contact Long Presses",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.lX.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView contactNameRV7,contactNumberRV7;
        CircleImageView contactPictureRV7;
        LinearLayout recyclerViewRowFriends;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contactNameRV7=itemView.findViewById(R.id.contactNameRV7);
            contactNumberRV7=itemView.findViewById(R.id.contactNumberRV7);
            contactPictureRV7=itemView.findViewById(R.id.contactPictureRV7);
            recyclerViewRowFriends=itemView.findViewById(R.id.recyclerViewRowFriends);
        }
    }
}
