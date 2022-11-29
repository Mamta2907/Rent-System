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

public class complex_list extends AppCompatActivity {
    Button addComplexBtn;
    RecyclerView complexRecyclerView;
    FirebaseDatabase database;
    ArrayList<ComplexData> complexArrayList  = new ArrayList<>();
    ComplexAdapter complexAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_list);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Complex ");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#26A69A")));


        addComplexBtn = findViewById(R.id.ComplexBtn);
        addComplexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(complex_list.this,complex.class));
            }
        });

        complexRecyclerView = findViewById(R.id.complexRecyclerView);
        complexRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Add Complex");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ComplexData complexData = dataSnapshot.getValue(ComplexData.class);
                    complexArrayList.add(complexData);
                }
                complexAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(complex_list.this,"Failed to get data",Toast.LENGTH_SHORT).show();
            }
        });
            complexAdapter = new ComplexAdapter(complex_list.this,complexArrayList);
            complexRecyclerView.setAdapter(complexAdapter);
    }

    // Action Bar Arrow
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return  true;
    }
}