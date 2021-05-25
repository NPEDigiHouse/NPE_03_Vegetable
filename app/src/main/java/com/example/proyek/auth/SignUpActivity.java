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
    /* class ini berfungsi untuk aktifitas menambah akun (Register/SignUp)
    *
    * */

    // variabel
    private FirebaseAuth mAuth;
    private TextInputEditText etUsername, etEmail, etPassword, etPhone;
    private Button btnSignUp;
    private TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // inisialisasi variabel
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
                // mengambil data String dari edit text
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String phone = etPhone.getText().toString();

                // pengecekan input
                if (username.isEmpty()) {
                    etUsername.setError("Please input your username");
                    etUsername.requestFocus();
                } else if (email.isEmpty()) {
                    etEmail.setError("Please input your email address");
                    etEmail.requestFocus();
                } else if (!isUsernameValid(email)) {
                    etEmail.setError("Please input a valid email address");
                    etEmail.requestFocus();
                } else if (password.isEmpty()) {
                    etPassword.setError("Please input your password");
                    etPassword.requestFocus();
                } else if (password.length() < 6) {
                    etPassword.setError("Your password must be more than 6 characters");
                    etPassword.requestFocus();
                } else if (phone.isEmpty()) {
                    etPhone.setError("Please input your phone number");
                    etPhone.requestFocus();
                } else if (username.isEmpty() && email.isEmpty() && password.isEmpty() && phone.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill the empty space !", Toast.LENGTH_SHORT).show();
                } else if (!(username.isEmpty() && email.isEmpty() && password.isEmpty() && phone.isEmpty())) {
                    // change value of ifAdmin to "admin" if u want add admin user
                    String role = "user";
                    // memerintahkan FirebaseAuth untuk membuat user baru dgn email dan password
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // cek jika user baru berhasil dibuat
                            if (task.isSuccessful()) {
                                // model user
                                UserModel user = new UserModel(username, email, phone, role);
                                // memanggil FirebaseDatabase untuk menyimpan data dari user baru berdasarkan model dari UserModel
                                FirebaseDatabase.getInstance().getReference("Login").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                                            Toast.makeText(SignUpActivity.this, "Sign up complete, please sign in", Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(SignUpActivity.this, "Sign up fail, please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(SignUpActivity.this,"Sign up fail, please try again !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // memindahkan activity ke SignInActivity
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // mengecek jika email itu valid
    boolean isUsernameValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // jika tombol back ditekan
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignUpActivity.this, GetStartedActivity.class);
        startActivity(intent);
    }
}