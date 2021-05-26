package com.example.proyek.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyek.R;
import com.example.proyek.auth.SignInActivity;
import com.example.proyek.auth.SignUpActivity;
import com.google.android.material.button.MaterialButton;

public class GetStartedActivity extends AppCompatActivity {
    
    //deklarasi variabel
    private Button btnSignIn;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        
        //inisialisasi variabel
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);

        //jika SignIn di klik
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //maka ke activity SignIn
                Intent intent = new Intent(GetStartedActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //jika signUp di klik
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //maka ke activity SignUp
                Intent intent = new Intent(GetStartedActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    
    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}
