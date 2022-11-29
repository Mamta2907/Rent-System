package com.example.rent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tenants extends AppCompatActivity {

    FirebaseAuth auth;
    //Button logOutBtn;
    TextView Shop,Rent,Amount;
    FirebaseDatabase database;
    DatabaseReference reference;
    UserInfo userInfo;
    String shopName;
    TenantsData tenantsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenants);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Tenants");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#26A69A")));

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }
//        if (auth.getCurrentUser() == null) {
//            startActivity(new Intent(Tenants.this, register.class));
//        }

        //logOutBtn.findViewById(R.id.logoutBtn);
        userInfo = new UserInfo();
        tenantsData = new TenantsData();

        Shop = findViewById(R.id.tvSN);
        Rent = findViewById(R.id.tvMR);
        Amount = findViewById(R.id.tvPA);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        //Required condition for particular user...
        //Instead of mamta fashion here the details of login user
        //Inside a condition get the current user in string and put inside the child

        reference = database.getReference("tenantsData").child(auth.getUid());
        //reference = database.getReference("tenantsData").child("email");

        //reference = database.getReference("user").child(auth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                  TenantsData tenantsData = snapshot.getValue(TenantsData.class);
                  shopName = tenantsData.getShopName();
                  Shop.setText(shopName);
                  dataInTenants(""+shopName);

            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(Tenants.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            // No user is signed in
            startActivity(new Intent(Tenants.this, register.class));
        } else {
            // User logged in
        }


    }

public void dataInTenants(String SHOP){

    //Toast.makeText(this, "Shop Name"+SHOP, Toast.LENGTH_SHORT).show();
    reference = database.getReference("userInfo").child(SHOP);
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
            UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
            String value2 = userInfo.getOneMonthRent();
            Rent.setText(value2);
            String value3 = userInfo.getPendingAmount();
            Amount.setText(value3);
            //Toast.makeText(Tenants.this, "Data Loaded:"+value2+value3,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(@NonNull  DatabaseError error) {
            Toast.makeText(Tenants.this, "Data are not load", Toast.LENGTH_SHORT).show();
        }
    });
}
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
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
        startActivity(new Intent(Tenants.this,Login.class));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
