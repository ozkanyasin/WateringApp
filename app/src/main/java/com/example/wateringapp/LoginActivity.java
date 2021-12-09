package com.example.wateringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.wateringapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding loginBinding;
    private ProgressDialog loginProgress;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        loginBinding.registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        loginBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginBinding.editTextEmail.getText().toString();
                String password = loginBinding.editTextPassword.getText().toString();
                if (!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
                    loginProgress.setTitle("Oturum Açılıyor");
                    loginProgress.setMessage("Lütfen bekleyiniz..");
                    loginProgress.setCanceledOnTouchOutside(false);
                    loginProgress.show();
                    loginUser(email,password);
                }
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    loginProgress.dismiss();
                    Toast.makeText(LoginActivity.this,"Giriş Başarılı",Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                }
                else {
                    loginProgress.dismiss();
                    Toast.makeText(LoginActivity.this,"Giriş yapılamadı: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}