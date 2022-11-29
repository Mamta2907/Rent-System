package com.example.rent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button adminBtn, complexBtn;
    //CheckBox checkBox;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        getSupportActionBar().setTitle("Rent System");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#26A69A")));

        auth = FirebaseAuth.getInstance();
        //checkBox = findViewById(R.id.checkBox);
        boolean isChecked = this.getIntent().getBooleanExtra("checkBoxValue", false);

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, register.class));
        }

       // Toast.makeText(this, "" + auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();


        adminBtn = findViewById(R.id.adminBtn);
        complexBtn = findViewById(R.id.complexbtn);


    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            // No user is signed in
            startActivity(new Intent(MainActivity.this, register.class));
        } else {
            // User logged in
        }
    }


    public void complex(View view) {
        startActivity(new Intent(this, complex_list.class));
    }

    public void admin(View view) {
        Intent intent = new Intent(this, Admin.class);
        startActivity(intent);
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itemLog) {
            logoutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logoutUser() {
       auth.signOut();
       startActivity(new Intent(MainActivity.this,Login.class));
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}


