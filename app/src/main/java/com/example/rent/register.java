package com.example.rent;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class register extends AppCompatActivity {

    EditText etName, etEmail, etPass, etPhone, etShopName, etComplexName;
    Button registerBtn, userBtn;
    TextView tvRegister;
    CheckBox checkBox;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;
   //ArrayList<String> shopName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Rent System");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#26A69A")));

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        registerBtn = findViewById(R.id.registerBtn);
        tvRegister = findViewById(R.id.tvRegister);
        etShopName = findViewById(R.id.etShopName);
        etComplexName = findViewById(R.id.etComplexName);
        userBtn = findViewById(R.id.userRegisterBtn);

        checkBox = findViewById(R.id.checkBox);
    }

    public void Check(View v) {

        if (checkBox.isChecked()) {
            etShopName.setVisibility(View.VISIBLE);
            etComplexName.setVisibility(View.VISIBLE);
            userBtn.setVisibility(View.VISIBLE);
            registerBtn.setVisibility(View.INVISIBLE);
        } else {
            etShopName.setVisibility(View.INVISIBLE);
            etComplexName.setVisibility(View.INVISIBLE);
            userBtn.setVisibility(View.INVISIBLE);
            registerBtn.setVisibility(View.VISIBLE);
        }
    }

    // Register Button for admin....
    public void setRegisterBtn(View view) {
        progressDialog.show();
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPass.getText().toString();
        String Phone = etPhone.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(Phone)) {
            progressDialog.dismiss();
            Toast.makeText(register.this, "Please Enter valid data", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            etEmail.setError("Enter Valid Email");
            progressDialog.dismiss();
            Toast.makeText(register.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
        } else if (!(Phone.length() == 10)) {
            etPhone.setError("Enter valid mobile number");
            progressDialog.dismiss();
            Toast.makeText(register.this, "Phone Number should be contain 10 digit", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            etPass.setError("Password length should be 6 ");
            progressDialog.dismiss();
            Toast.makeText(register.this, "Password should minimum 6 character long", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        DatabaseReference reference = database.getReference().child("user").child(Objects.requireNonNull(auth.getUid()));
                        User user = new User(auth.getUid(), name, email, Phone);

                        reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    startActivity(new Intent(register.this, MainActivity.class));
                                } else {
                                    Toast.makeText(register.this, "Error in Register", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(register.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //-----------Button for user--------------

    public void setUserRegisterBtn(View view) {
        progressDialog.show();
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPass.getText().toString();
        String Phone = etPhone.getText().toString();
        String shop = etShopName.getText().toString().trim();
        String complex = etComplexName.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(Phone)
                || TextUtils.isEmpty(shop) ) {
            //|| TextUtils.isEmpty(complex)
            progressDialog.dismiss();
            Toast.makeText(register.this, "Please Fill complete details..", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            etEmail.setError("Enter Valid Email");
            progressDialog.dismiss();
            Toast.makeText(register.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
        } else if (!(Phone.length() == 10)) {
            etPhone.setError("Enter valid mobile number");
            progressDialog.dismiss();
            Toast.makeText(register.this, "Phone Number should be contain 10 digit", Toast.LENGTH_SHORT).show();
        } else if (password.length() > 6) {
            etPass.setError("Password length should be 6 ");
            progressDialog.dismiss();
            Toast.makeText(register.this, "Password should minimum 6 character long", Toast.LENGTH_SHORT).show();
        } else {

                String shop1 = shop;
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("userInfo");
                reference1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> shops = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String shopName = dataSnapshot.getKey();
                            shops.add(shopName);
                            Log.d("TAG", String.valueOf(shops));
                            //Toast.makeText(register.this, ""+shops, Toast.LENGTH_SHORT).show();

                            compare(shop1, shops);
                             continue;
                        }

                    }
                        private void compare(String shop1, List<String> shops) {
                           // progressDialog.show();
                            for (String s : shops) {
                                if(s.contains(shop1))
                                {   try {
                                    //Toast.makeText(register.this, "Available:" + shop1, Toast.LENGTH_SHORT).show();
                                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {

                                            DatabaseReference reference = database.getReference().child("tenantsData").child(Objects.requireNonNull(auth.getUid()));
                                            TenantsData tenantsData = new TenantsData(auth.getUid(), name, email, Phone, shop, complex);
                                            reference.setValue(tenantsData).addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    progressDialog.dismiss();

                                                    startActivity(new Intent(register.this, Tenants.class));
                                                    Log.d("Mamta", task.getException().getMessage());

                                                } else {
                                                    //progressDialog.dismiss();
                                                    Toast.makeText(register.this, "Error in Register", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            // progressDialog.dismiss();
                                            // task.getException().getMessage();
                                            Log.d("Mamta", task.getException().getMessage());
                                            //task.getException().getErrorCode();
                                            Toast.makeText(register.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } catch (Exception e) {
                                  // Log.d("try", e.printStackTrace());
                                   e.printStackTrace();
                                }

                                } else {

                                    //Toast.makeText(register.this, "Shop name is not available..", Toast.LENGTH_SHORT).show();
                                    etShopName.setError("Shop Name is Not Available");
                                   progressDialog.setMessage("The shop Name is not available");
                                   progressDialog.dismiss();

                                }
                            }
                        }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }


    public void setTvRegister(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}