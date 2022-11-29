package com.example.rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class complex extends AppCompatActivity {

    EditText complexName,complexAdd,totalShop;
    TextView tvComplexName,tvComplexAdd,tvTotalShop;
    Button btnSaveComplex;

    FirebaseDatabase database;
    DatabaseReference reference;
    ComplexData complexData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Add Complex");
        complexData = new ComplexData();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Complex");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#26A69A")));

        //EditText
        complexName = findViewById(R.id.etComplex);
        complexAdd = findViewById(R.id.etComplexAdd);
        totalShop = findViewById(R.id.etTotalShop);

        //TextView
        tvComplexName = findViewById(R.id.tvComplex);
        tvComplexAdd = findViewById(R.id.tvComplexAdd);
        tvTotalShop = findViewById(R.id.tvTotalShop);


        //Intent From Display_Complex class
        String Cname = getIntent().getStringExtra("complex_name");
        complexName.setText(Cname);
        String CAdd = getIntent().getStringExtra("complex_add");
        complexAdd.setText(CAdd);
        String TShop = getIntent().getStringExtra("complex_total_shop");
        totalShop.setText(TShop);


        //Button
        btnSaveComplex = findViewById(R.id.saveBtnComplex);

    }


    public void setBtnSaveComplex(View view){

        String Complex_Name = complexName.getText().toString();
        String Complex_Add = complexAdd.getText().toString();
        String Total_Shop = totalShop.getText().toString();

        if(Complex_Name.isEmpty() || Complex_Add.isEmpty() || Total_Shop.isEmpty()){
            Toast.makeText(this, "Please Entered all data", Toast.LENGTH_SHORT).show();
        }
        else{
            addData(Complex_Name,Complex_Add,Total_Shop);
        }
        startActivity(new Intent(complex.this,MainActivity.class));

    }

    public void addData(String Complex_Name, String Complex_Add, String Total_Shop){
         complexData.setComplexName(Complex_Name);
         complexData.setComplexAdd(Complex_Add);
         complexData.setTotalShop(Total_Shop);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                reference.child(complexData.complexName).setValue(complexData);
                Toast.makeText(complex.this,"Data added ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(complex.this,"Data not added ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Action Bar Arrow
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return  true;
    }

}