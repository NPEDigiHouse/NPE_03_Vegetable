package com.example.proyek.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyek.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    /*  class ini berfungsi sebagai aktifitas jika user lupa password dan ingin mereset passwordnya
     */

    // Variabel
    private TextInputEditText etEmail;
    private Button btnResetPass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Inisialisasi variabel
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etEmail);
        btnResetPass = findViewById(R.id.btnResetPassword);
        // Jika btn diclick
        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();

                // pengecekan input
                if (!isEmailValid(email)) {
                    etEmail.setError("Please input a valid email address !");
                    etEmail.requestFocus();
                } else if (email.isEmpty()) {
                    etEmail.setError("Please input your email address !");
                    etEmail.requestFocus();
                } else {
                    // FirebaseAuth akan mengirimkan email untuk mengubah password
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // cek jika email berhasil dikirim
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "A reset link has been sent to " + email, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });


    }

    // method untuk mengecek apakah email valid
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

}