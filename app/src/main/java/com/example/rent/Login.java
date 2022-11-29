package com.example.rent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Login extends AppCompatActivity {

    EditText etEmail, etPass, etShopName;
    Button adminLoginBtn,tenantsLoginBtn;
    TextView tvLogin, tvForgotPass;
    CheckBox checkBox;
    FirebaseAuth auth;
    TenantsData tenantsData;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Rent System");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#26A69A")));

        if(Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_bar));
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etEmailLogin);
        etPass = findViewById(R.id.etPassLogin);
        etShopName = findViewById(R.id.etLoginShopName);
        adminLoginBtn = findViewById(R.id.loginBtn);
        tenantsLoginBtn = findViewById(R.id.tenants_loginBtn);
        checkBox = findViewById(R.id.checkBoxLogin);
        tvLogin = findViewById(R.id.tvLogin);
        tvForgotPass = findViewById(R.id.forgotPassword);


        // from login to register page
        tvLogin.setOnClickListener(view -> startActivity(new Intent(Login.this, register.class)));
        tvForgotPass.setOnClickListener(view -> showRecoverPasswordDialog());
    }


    //CheckBox
    public void setCheckBox(View v){

        if(checkBox.isChecked()){
            tenantsLoginBtn.setVisibility(View.VISIBLE);
            etShopName.setVisibility(View.VISIBLE);
            adminLoginBtn.setVisibility(View.INVISIBLE);
        }
        else
        {
            tenantsLoginBtn.setVisibility(View.INVISIBLE);
            etShopName.setVisibility(View.INVISIBLE);
            adminLoginBtn.setVisibility(View.VISIBLE);
        }
    }


// Forgot Password and Email Recover
    ProgressDialog loadingBar;
    private void showRecoverPasswordDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout = new LinearLayout(this);
        final EditText emailet = new EditText(this);

        //Email from which you registered...
        emailet.setHint("Enter Email for recover");
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10, 10, 10, 10);
        builder.setView(linearLayout);

        //click on the forgot password button
        builder.setPositiveButton("Recover mail",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = emailet.getText().toString().trim();
                beginRecovery(email);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void beginRecovery(String email) {
        loadingBar = new ProgressDialog(this);
        loadingBar.setMessage("Sending Email...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                loadingBar.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Email Send..!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Login.this, "Error in email send..", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(Login.this, "Error failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Admin Login Button
    public void adminLoginBtn(View view){
        progressDialog.show();
        String email = etEmail.getText().toString().trim();
        String password = etPass.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {   progressDialog.dismiss();
            Toast.makeText(Login.this,"Please Enter Valid Data",Toast.LENGTH_SHORT).show();
        }
        else if(!email.matches(emailPattern)){
            progressDialog.dismiss();
            etEmail.setError("Invalid Email");
            Toast.makeText(Login.this,"Invalid Email",Toast.LENGTH_SHORT).show();
        }
        else if(password.length() < 6){
            progressDialog.dismiss();
            etPass.setError("Password Length should be 6 ");
            //Toast.makeText(Login.this,"Please Enter Valid Password",Toast.LENGTH_SHORT).show();
        }
        else{

            String email1 = email;
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull  DataSnapshot snapshot) {
                    List<String> emailList = new ArrayList<>();

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String emails = dataSnapshot.child("email").getValue(String.class);
                        emailList.add(emails);
                        Log.d("Email",emails);

                        compare(email1,emailList);
                        continue;

                    }
                }

                private void compare(String email1, List<String> emailList){
                    for(String s: emailList){
                        if(s.contains(email)){

                            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {

                            if(task.isSuccessful()){

                                     progressDialog.dismiss();
                                      //Toast.makeText(Login.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                                      startActivity(new Intent(Login.this,MainActivity.class));

                                    }
                                 else{
                                      progressDialog.dismiss();
                                     Toast.makeText(Login.this,"Error in login",Toast.LENGTH_SHORT).show();
                                 }

                             });

                        }
                        else{
                            progressDialog.dismiss();
                            etEmail.setError("This Email is not register as a Admin..");
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull  DatabaseError error) {
                    Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    // Tenants Login Button
    public void tenantsLoginBtn(View v){
        progressDialog.show();
        String email = etEmail.getText().toString().trim();
        String password = etPass.getText().toString();
        String shop = etShopName.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(shop) )

        {
            progressDialog.dismiss();
            Toast.makeText(Login.this,"Please Enter Valid Data",Toast.LENGTH_SHORT).show();
        }
        else if(!email.matches(emailPattern)){
            progressDialog.dismiss();
            etEmail.setError("Invalid Email");
            //Toast.makeText(Login.this,"Invalid Email",Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<6){
            progressDialog.dismiss();
            etPass.setError("Password Length should be 6");
            //Toast.makeText(Login.this,"Please Enter Valid Password",Toast.LENGTH_SHORT).show();
        }
        else {

            String email1 = email;
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tenantsData");
            //DatabaseReference reference1 = reference.child("email");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull  DataSnapshot snapshot) {
                    List<String> emailList = new ArrayList<>();

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String emails = dataSnapshot.child("email").getValue(String.class);
                        //String emails = dataSnapshot.getKey();
                        emailList.add(emails);
                        //Log.d("Email",emails);
                        //Toast.makeText(Login.this, ""+emails, Toast.LENGTH_SHORT).show();
                        compare(email1, emailList);

                        continue;

                    }
                }

                private void  compare(String email1, List<String> emaillist){
                    for(String s : emaillist){
                        if(s.contains(email1)){
                            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                   // Toast.makeText(Login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, Tenants.class));
                                   // break;

                                    //Log.d("Task",task.getException().getMessage());

                                }

                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(Login.this, "Error in login, Or Internet Connection Check", Toast.LENGTH_SHORT).show();
                                    //Log.d("Task",task.getException().getMessage());
                                }

                            });
                        }
                        else{
                            progressDialog.dismiss();
                            etEmail.setError("This Email is not register as a Tenants, Please Register as Tenants First");
                            //Toast.makeText(Login.this, "Error in login", Toast.LENGTH_SHORT).show();
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull  DatabaseError error) {
                    Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
         }
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//              finish();
//        }
//    }
}

