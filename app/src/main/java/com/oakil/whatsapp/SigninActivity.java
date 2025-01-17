package com.oakil.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.oakil.whatsapp.databinding.ActivitySigninBinding;

public class SigninActivity extends AppCompatActivity {
    ActivitySigninBinding binding;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SigninActivity.this);
        progressDialog.setTitle("Login..");
        progressDialog.setMessage("Sign in is processing..");


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        binding.signinButton.setOnClickListener(v->{
            if( !binding.signInEmail.getText().toString().isEmpty() &&  !binding.signInPassword.getText().toString().isEmpty()){
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(binding.signInEmail.getText().toString(), binding.signInPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    progressDialog.dismiss();
                                    startActivity(new Intent(SigninActivity.this, MainActivity.class));

                                }else{
                                    Toast.makeText(SigninActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else{

                Toast.makeText(this, "Enter credential", Toast.LENGTH_SHORT).show();
            }
        });

        binding.goToSignUp.setOnClickListener(v -> {
            startActivity(new Intent(SigninActivity.this, SignupActivity.class));
        });

        if(mAuth.getCurrentUser() != null ){
            startActivity(new Intent(SigninActivity.this, MainActivity.class));
        }

    }
}