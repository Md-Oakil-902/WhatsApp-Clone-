package com.oakil.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.oakil.whatsapp.Models.Users;
import com.oakil.whatsapp.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ActivitySignupBinding binding;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Creating Account..");
        progressDialog.setMessage("Account creating in processing");

        binding.signUp.setOnClickListener(v -> {
            if (!binding.textPersonName.getText().toString().isEmpty() &&
                    !binding.textEmail.getText().toString().isEmpty() &&
                    !binding.editTextNumberPassword.getText().toString().isEmpty()) {
                progressDialog.show();


                mAuth.createUserWithEmailAndPassword(binding.textEmail.getText().toString(), binding.editTextNumberPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Users users = new Users(binding.textPersonName.getText().toString(), binding.textEmail.getText().toString(), binding.editTextNumberPassword.getText().toString());

                                    String id = task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(users);


                                    Toast.makeText(SignupActivity.this, "Sign up Successfull", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));

                                } else {
                                    Toast.makeText(SignupActivity.this, "Sign up Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            } else {
                Toast.makeText(SignupActivity.this, "Enter credential ", Toast.LENGTH_SHORT).show();
            }

        });

        binding.goToSignIN.setOnClickListener(v->{
            startActivity(new Intent(SignupActivity.this, SigninActivity.class));
        });


    }
}




