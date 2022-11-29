package com.example.rent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {
    Context  admin;
   ArrayList<UserInfo> userInfoArrayList;
    public UserAdapter(Admin admin, ArrayList<UserInfo> userInfoArrayList) {

        this.admin = admin;
        this.userInfoArrayList = userInfoArrayList;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(admin).inflate(R.layout.user_list_item,parent,false);
        return new viewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull  UserAdapter.viewHolder holder, int position) {
        UserInfo userInfo = userInfoArrayList.get(position);
        holder.user_name.setText(userInfo.getShopName());
        holder.pending_amount.setText(userInfo.getPendingAmount());
        holder.itemView.setOnClickListener(v -> {

           Intent intent = new Intent(admin,Display_User.class);
            //Toast.makeText(admin, ""+userInfo.getOneMonthRent(), Toast.LENGTH_SHORT).show();
            intent.putExtra("Name",userInfo.getShopName());
            intent.putExtra("Rent",userInfo.getOneMonthRent());
            intent.putExtra("Complex",userInfo.getComplexName());
            intent.putExtra("PaidRent",userInfo.getPaidAmount());
            intent.putExtra("DMonth",userInfo.getDefaultMonth());
            intent.putExtra("PendingRent",userInfo.getPendingAmount());
            admin.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userInfoArrayList.size();
    }

   public static class viewHolder extends RecyclerView.ViewHolder  {
        TextView user_name;
        TextView pending_amount;
        public viewHolder(@NonNull  View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.tvUserName);
            pending_amount = itemView.findViewById(R.id.tv_PendingAmount);
        }
    }
}