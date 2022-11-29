package com.example.rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class Admin extends AppCompatActivity {
     Button addUser;
     RecyclerView mainUserRecyclerView;
     UserAdapter adapter;
     FirebaseDatabase database;
     ArrayList<UserInfo> userInfoArrayList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shops");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#26A69A")));

        mainUserRecyclerView = findViewById(R.id.mainUserRecyclerView);
        mainUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addUser = findViewById(R.id.addUserBtn);
        database= FirebaseDatabase.getInstance();

       DatabaseReference reference = database.getReference().child("userInfo");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                    userInfoArrayList.add(userInfo);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(Admin.this,"Failed to get data",Toast.LENGTH_SHORT).show();
            }
        });
        adapter = new UserAdapter(Admin.this,userInfoArrayList);
        mainUserRecyclerView.setAdapter(adapter);
    }

    // Action Bar Arrow
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return  true;
    }

      public void setAddUser(View view){
        Intent intent = new Intent(this,user_detail.class);
          startActivity(intent);
      }

}