package com.example.proyek.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyek.R;
import com.example.proyek.models.user.UserModel;
import com.example.proyek.activities.GetStartedActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText etUsername, etEmail, etPassword, etPhone;
    private Button btnSignUp;
    private TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.tvSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String phone = etPhone.getText().toString();

                if (username.isEmpty()) {
                    etUsername.setError("Mohon masukkan username anda");
                    etUsername.requestFocus();
                } else if (email.isEmpty()) {
                    etEmail.setError("Mohon masukkan email anda !");
                    etEmail.requestFocus();
                } else if (!isUsernameValid(email)) {
                    etEmail.setError("Mohon masukkan email yang valid !");
                    etEmail.requestFocus();
                } else if (password.isEmpty()) {
                    etPassword.setError("Mohon masukkan password anda !");
                    etPassword.requestFocus();
                } else if (password.length() < 6) {
                    etPassword.setError("Password harus lebih dari 6 karakter !");
                    etPassword.requestFocus();
                } else if (phone.isEmpty()) {
                    etPhone.setError("Mohon masukkan nomor telefon anda !");
                    etPhone.requestFocus();
                } else if (username.isEmpty() && email.isEmpty() && password.isEmpty() && phone.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Mohon isi tempat yang kosong", Toast.LENGTH_SHORT).show();
                } else if (!(username.isEmpty() && email.isEmpty() && password.isEmpty() && phone.isEmpty())) {
                    // change value of ifAdmin to "admin" if u want add admin user
                    String role = "user";
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                UserModel user = new UserModel(username, email, phone, role);
                                FirebaseDatabase.getInstance().getReference("Login").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                            Toast.makeText(SignUpActivity.this, "Sign Up berhasil, silahkan login", Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(SignUpActivity.this, "Sign Up gagal, mohon coba lagi", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(SignUpActivity.this,"Sign up gagal, coba lagi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    boolean isUsernameValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignUpActivity.this, GetStartedActivity.class);
        startActivity(intent);
    }
}