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

public class ComplexAdapter extends RecyclerView.Adapter<ComplexAdapter.viewHolder> {

   Context complex_list;
   ArrayList<ComplexData> complexArrayList;

    public ComplexAdapter(complex_list complex_list, ArrayList<ComplexData> complexArrayList) {
        this.complex_list = complex_list;
        this.complexArrayList = complexArrayList;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(complex_list).inflate(R.layout.complex_list_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplexAdapter.viewHolder holder, int position) {
        ComplexData complexData = complexArrayList.get(position);
       holder.complex_name.setText(complexData.getComplexName());
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(complex_list,Display_Complex.class);
            intent.putExtra("complexName",complexData.getComplexName());
            intent.putExtra("complexAddress",complexData.getComplexAdd());
            intent.putExtra("totalShop",complexData.getTotalShop());
            complex_list.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return complexArrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView complex_name;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            complex_name = itemView.findViewById(R.id.complex_list_item);

        }

    }

}

