package com.example.wateringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.wateringapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding registerBinding;
    private ProgressDialog registerProgress;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private HashMap<String,Object> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());

        registerProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        registerBinding.registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        registerBinding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = registerBinding.registerName.getText().toString();
                String email = registerBinding.registerEmail.getText().toString();
                String password = registerBinding.registerPassword.getText().toString();

                if (!TextUtils.isEmpty(name)||!TextUtils.isEmpty(password)||!TextUtils.isEmpty(email)){
                    registerProgress.setTitle("Kaydediliyor");
                    registerProgress.setMessage("Hesabınız oluşturuluyor..");
                    registerProgress.setCanceledOnTouchOutside(false);
                    registerProgress.show();
                    registerUser(name,password,email);
                }


            }
        });

    }
    private void registerUser(String name, String password, String email) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    mUser = mAuth.getCurrentUser();

                    mData = new HashMap<>();
                    mData.put("KullaniciAdi",name);
                    mData.put("KullaniciE-mail",email);
                    mData.put("KullaniciID",mUser.getUid());


                    registerProgress.dismiss();
                    Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                }
                else
                {
                    registerProgress.dismiss();
                    Toast.makeText(RegisterActivity.this,"Hata "+task.getException(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}