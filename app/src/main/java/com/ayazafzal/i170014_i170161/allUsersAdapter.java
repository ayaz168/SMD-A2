package com.ayazafzal.i170014_i170161;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class allUsersAdapter extends RecyclerView.Adapter<allUsersAdapter.MyViewHolder>{
    List<userData> lX;
    Context c;
    userData currentUser;

    public allUsersAdapter(Context c, List<userData> lX,userData currentuser) {
        this.c = c;
        this.lX = lX;
        this.currentUser=currentuser;
    }
    @NonNull
    @Override
    public allUsersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(c).inflate(R.layout.show_user_row,parent,false);
        //Initializes each row for data?
        return new allUsersAdapter.MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull allUsersAdapter.MyViewHolder holder, int position) {
        holder.contactNameRV8.setText(lX.get(holder.getAdapterPosition()).getFirstName()+" "+ lX.get(position).getLastName());
        holder.contactNumberRV8.setText(lX.get(holder.getAdapterPosition()).getPhone());
        Bitmap bitmap = BitmapFactory.decodeFile(lX.get(holder.getAdapterPosition()).getImage());
        holder.contactPictureRV8.setImageBitmap(bitmap);
        holder.recyclerViewRowAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c,
                        "Contact Long Pressed "+lX.get(holder.getAdapterPosition()).getFirstName()+" "+ lX.get(holder.getAdapterPosition()).getLastName(),
                        Toast.LENGTH_LONG).show();
                if( ! currentUser.getId().equals(lX.get(holder.getAdapterPosition()).getId())){ // So user can't add himself as a friend.
                    addFriend(currentUser.getId(), lX.get(holder.getAdapterPosition()).getId());
                }else {
                    Toast.makeText(c,
                            "Contact Long Pressed "+lX.get(holder.getAdapterPosition()).getFirstName()+" "+ lX.get(holder.getAdapterPosition()).getLastName(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        holder.recyclerViewRowAllUsers.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(c,
                        "Contact Long Pressed "+lX.get(holder.getAdapterPosition()).getFirstName()+" "+ lX.get(holder.getAdapterPosition()).getLastName(),
                        Toast.LENGTH_LONG).show();
                if( ! currentUser.getId().equals(lX.get(holder.getAdapterPosition()).getId())){
                    addFriend(currentUser.getId(), lX.get(holder.getAdapterPosition()).getId());
                }else {
                    Toast.makeText(c,
                            "Contact Long Pressed "+lX.get(holder.getAdapterPosition()).getFirstName()+" "+ lX.get(holder.getAdapterPosition()).getLastName(),
                            Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }
    public void addFriend(String idA,String idB){
        myDBHelper helper=new myDBHelper(c);
        SQLiteDatabase database=helper.getWritableDatabase();
        ContentValues cV=new ContentValues();
        cV.put(myDBContracts.Friends._ID1,idA);
        cV.put(myDBContracts.Friends._ID2,idB);
        double res=database.insert(myDBContracts.Friends.TABLENAME,null,cV);
        database.close();
        helper.close();
        if (res>0){
            Toast.makeText(c,
                    "Friends Added",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(c,
                    "Error",
                    Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public int getItemCount() {
        return this.lX.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recyclerViewRowAllUsers;
        TextView contactNameRV8,contactNumberRV8;
        CircleImageView contactPictureRV8;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewRowAllUsers=itemView.findViewById(R.id.recyclerViewRowAllUsers);
            contactNameRV8=itemView.findViewById(R.id.contactNameRV8);
            contactNumberRV8=itemView.findViewById(R.id.contactNumberRV8);
            contactPictureRV8=itemView.findViewById(R.id.contactPictureRV8);
        }

    }
}
