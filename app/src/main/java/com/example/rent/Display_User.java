package com.example.rent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Display_User extends AppCompatActivity {

    TextView tvName,tvRent,tvComplex,tvPaidRent,tvDefaultMonth,tvPendingRent;
     String Sname, rent, complex, paidRent, defaultMonth, pendingRent;
     Button updateBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_user);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Display User");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#26A69A")));
        

         Sname = getIntent().getStringExtra("Name");
         rent = getIntent().getStringExtra("Rent");
         complex = getIntent().getStringExtra("Complex");
         paidRent = getIntent().getStringExtra("PaidRent");
         defaultMonth = getIntent().getStringExtra("DMonth");
         pendingRent = getIntent().getStringExtra("PendingRent");


        tvName = findViewById(R.id.tvName);
        tvName.setText(Sname);

        tvRent = findViewById(R.id.tvRent);
        tvRent.setText(rent);

        tvComplex = findViewById(R.id.tvComplex);
        tvComplex.setText(complex);

        tvPaidRent = findViewById(R.id.tvPaidRent);
        tvPaidRent.setText(paidRent);

        tvDefaultMonth = findViewById(R.id.tvDefault);
        tvDefaultMonth.setText(defaultMonth);

        tvPendingRent = findViewById(R.id.tvPending);
        tvPendingRent.setText(pendingRent);

        updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Display_User.this,user_detail.class);
                intent.putExtra("name", String.valueOf(Sname));
                intent.putExtra("Rent",String.valueOf(rent));
                intent.putExtra("Complex",String.valueOf(complex));
                intent.putExtra("PaidRent",String.valueOf(paidRent));
                intent.putExtra("DMonth",String.valueOf(defaultMonth));
                intent.putExtra("PendingRent",String.valueOf(pendingRent));
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