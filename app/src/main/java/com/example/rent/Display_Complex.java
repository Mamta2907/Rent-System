package com.example.rent;

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
import android.widget.TextView;

public class Display_Complex extends AppCompatActivity {

     TextView tvComplexName,tvComplexAdd, tvTotalShop;
     String name, add,total_shop;
     Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_complex);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Display Complex");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#26A69A")));


        tvComplexName = findViewById(R.id.tv_complexName);
        tvComplexAdd = findViewById(R.id.tv_complexAdd);
        tvTotalShop = findViewById(R.id.tv_totalShop);

        name = getIntent().getStringExtra("complexName");
        add =  getIntent().getStringExtra("complexAddress");
        total_shop = getIntent().getStringExtra("totalShop");

        tvComplexName.setText(name);
        tvComplexAdd.setText(add);
        tvTotalShop.setText(total_shop);

        btnUpdate = findViewById(R.id.complex_update_btn);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Display_Complex.this,complex.class);
                intent.putExtra("complex_name",String.valueOf(name));
                intent.putExtra("complex_add",String.valueOf(add));
                intent.putExtra("complex_total_shop",String.valueOf(total_shop));
                startActivity(intent);
            }
        });
    }

    // Action Bar Arrow
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return  true;
    }
}