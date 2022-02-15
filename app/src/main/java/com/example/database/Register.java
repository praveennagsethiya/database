package com.example.database;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    EditText mFullName,mEmail,mMobileNo,mPassword,mConfirmPassword;
    Button mRegisteredBtn;
    Button mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_register);

        mFullName=findViewById (R.id.fullName);
        mEmail=findViewById (R.id.Email);
        mMobileNo=findViewById (R.id.phone);
        mPassword=findViewById (R.id.password);
        //mConfirmPassword=findViewById (R.id.ConfirmPassword);
        mFullName=findViewById (R.id.fullName);
        mRegisteredBtn=findViewById (R.id.registerBtn);
        mLoginBtn=findViewById (R.id.createText);

        fAuth=FirebaseAuth.getInstance ();
        progressBar=findViewById (R.id.progressBar);


        if(fAuth.getCurrentUser ()!=null) {

            startActivity (new Intent (getApplicationContext (),MainActivity.class));

            finish ();
        }


        mRegisteredBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                String email=mEmail.getText ().toString ().trim ();
                String password=mPassword.getText ().toString ().trim ();

                if(TextUtils.isEmpty (email)){
                    mEmail.setError ("Email Is Required");
                    return;
                }
                if(TextUtils.isEmpty (password)){
                    mPassword.setError ("Password Is Required");
                    return;
                }
                if (password.length ()<6){
                    mPassword.setError ("Password Must be 6 Charector Long");
                    return;
                }

                progressBar.setVisibility (view.VISIBLE);
                // register the user in firebase

                fAuth.createUserWithEmailAndPassword (email,password).addOnCompleteListener (new OnCompleteListener <AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task <AuthResult> task) {
                     if(task.isSuccessful ()){
                         Toast.makeText (Register.this,"user created",Toast.LENGTH_SHORT).show ();

                         startActivity (new Intent (getApplicationContext (),MainActivity.class));

                     }
                     else {
                         Toast.makeText (Register.this,"Error",Toast.LENGTH_SHORT).show ();
                         progressBar.setVisibility (view.GONE);
                     }
                    }
                });
            }
        });


        mLoginBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                startActivity (new Intent (getApplicationContext (), Login.class));

            }
            });


    }

}