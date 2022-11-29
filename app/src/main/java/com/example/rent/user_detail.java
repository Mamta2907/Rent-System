package com.example.rent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class user_detail extends AppCompatActivity {
 //implements AdapterView.OnItemSelectedListener
     EditText etShopName,etMonthlyRent,etComplexName,etRentPaid,etDefaultMonth,etPendingAmount;
     TextView tvShopName,tvMonthlyRent, tvComplexName,tvRentPaid,tvDefaultMonth,tvPendingAmount;
     Button btnSave;
     Spinner spinner;
     String text;
     FirebaseAuth auth;
     FirebaseDatabase database;
     DatabaseReference reference;
     UserInfo userInfo;
    //List<String> C = new ArrayList<>();
    //String[] C;
     ArrayList<String> complexlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

       ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Shop/Tenants ");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#26A69A")));

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("userInfo");
        userInfo = new UserInfo();

        //  EditText
        etShopName = findViewById(R.id.etShopName);
        etMonthlyRent = findViewById(R.id.etRent);
        etComplexName = findViewById(R.id.editComplexName);
        etRentPaid = findViewById(R.id.etRentPaid);
        etDefaultMonth = findViewById(R.id.etDefaultMonth);
        etPendingAmount = findViewById(R.id.etPendingAmount);

        // TextView
         tvShopName = findViewById(R.id.tvShopName);
         tvMonthlyRent = findViewById(R.id.tvRentMonth);
         tvComplexName = findViewById(R.id.tvComplexName);
         tvRentPaid = findViewById(R.id.tvRentUpto);
         tvDefaultMonth = findViewById(R.id.tvDefaultMonth);
         tvPendingAmount =  findViewById(R.id.tvPendingAmount);

      //Intent From Display_User class
        String Sname = getIntent().getStringExtra("name");
        etShopName.setText(Sname);
        String rent = getIntent().getStringExtra("Rent");
        etMonthlyRent.setText(rent);
        String complex = getIntent().getStringExtra("Complex");
        etComplexName.setText(complex);
        String paidRent = getIntent().getStringExtra("PaidRent");
        etRentPaid.setText(paidRent);
        String defaultMonth = getIntent().getStringExtra("DMonth");
        etDefaultMonth.setText(defaultMonth);
        String pendingRent = getIntent().getStringExtra("PendingRent");
        etPendingAmount.setText(pendingRent);


         //Button
        btnSave = findViewById(R.id.saveBtn);

        //Spinner
        spinner = findViewById(R.id.spinner);
        complexlist = new ArrayList<>();



        DatabaseReference databaseReference = database.getReference().child("Add Complex");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                 //List<String> complexlist = new ArrayList<>();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String complexName = dataSnapshot.getKey();
                    complexlist.add(complexName);
                   // C = complex;
                    //Toast.makeText(user_detail.this, "" +complexlist, Toast.LENGTH_SHORT).show();
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(user_detail.this,android.R.layout.simple_spinner_item, complexlist);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
                //spinner.setOnItemSelectedListener(this);
                //dataAdapter.notifyDataSetChanged();

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                     text = adapterView.getSelectedItem().toString();
                                     //text = parent.getItemAtPosition(position).toString();
                                    //Toast.makeText(adapterView.getContext(),text, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(user_detail.this, "Add Shop not Enter", Toast.LENGTH_SHORT).show();
            }
        });


    }





//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//             //text = parent.getSelectedItem().toString();
//             text = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//        Toast.makeText(this, "onNothing Selected", Toast.LENGTH_SHORT).show();
//    }

  // Action Bar Arrow
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return  true;
    }

    // save Button
    public void setBtnSave(View view){
        //etComplexName.setText(text);
        //Toast.makeText(this, "btnSave"+text, Toast.LENGTH_SHORT).show();
      String shopName = etShopName.getText().toString().trim();
      String rent = etMonthlyRent.getText().toString();
      String complexName = text;
      //String complexName = etComplexName.getText().toString().trim();
      String rentPaid = etRentPaid.getText().toString();
      String defaultMonth = etDefaultMonth.getText().toString();
      String pendingAmount = etPendingAmount.getText().toString();

      if(TextUtils.isEmpty(shopName) || TextUtils.isEmpty(rent) || TextUtils.isEmpty(complexName) ||
              TextUtils.isEmpty(rentPaid) || TextUtils.isEmpty(defaultMonth) || TextUtils.isEmpty(pendingAmount)){

          Toast.makeText(user_detail.this,"Please add some data",Toast.LENGTH_SHORT).show();
      }
      else{
          addDataToFirebase(shopName,rent,complexName,rentPaid,defaultMonth,pendingAmount);
      }
      startActivity(new Intent(user_detail.this,MainActivity.class));
    }


    public void addDataToFirebase(String shopName, String rent, String complexName, String rentPaid, String defaultMonth, String pendingAmount) {

        userInfo.setShopName(shopName);
        userInfo.setOneMonthRent(rent);
        userInfo.setComplexName(complexName);
        userInfo.setPaidAmount(rentPaid);
        userInfo.setDefaultMonth(defaultMonth);
        userInfo.setPendingAmount(pendingAmount);

       reference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull  DataSnapshot snapshot) {
               reference.child(userInfo.shopName).setValue(userInfo);
               //reference.setValue(userInfo);
               Toast.makeText(user_detail.this,"Data added ",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onCancelled(@NonNull  DatabaseError error) {
               Toast.makeText(user_detail.this,"Fail to add data ",Toast.LENGTH_SHORT).show();
           }
       });
    }
}