package com.example.proyek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

//    private FirebaseAuth mAuth;
//    private EditText editTextUsername, editTextEmail, editTextPassword;
//    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        mAuth = FirebaseAuth.getInstance();
//
//        editTextUsername = findViewById(R.id.usernameInput);
//        editTextEmail = findViewById(R.id.emailInput);
//        editTextPassword = findViewById(R.id.passwordInput);
//        btnRegister = findViewById(R.id.buttonRegister);
//
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email = editTextEmail.getText().toString();
//                String password = editTextPassword.getText().toString();
//                String fullName = editTextUsername.getText().toString();
//
//
//                if (fullName.isEmpty()) {
//                    editTextUsername.setError("name is required");
//                    editTextUsername.requestFocus();
//                    return;
//                }
//                if (email.isEmpty()) {
//                    editTextEmail.setError("email is required");
//                    editTextEmail.requestFocus();
//
//                    return;
//                }
//                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                    editTextEmail.setError("please provide valid email");
//                    return;
//                }
//
//                if (password.isEmpty()) {
//                    editTextPassword.setError("Password is required");
//                    editTextPassword.requestFocus();
//                    return;
//                }
//                if (password.length() < 6) {
//                    editTextPassword.setError("password must be 6 character");
//                    editTextPassword.requestFocus();
//                    return;
//                }
//
//                //change value of ifAdmin to "admin" if u want add admin user
//                String ifAdmin = "user";
//                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            User user = new User(fullName, email, ifAdmin);
//                            FirebaseDatabase.getInstance().getReference("Login").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        startActivity(new Intent(Register.this, MainActivity.class));
//                                        Toast.makeText(Register.this, "user has been registered successfully", Toast.LENGTH_LONG).show();
//                                        finish();
//
//                                    } else {
//                                        Toast.makeText(Register.this, "failed register", Toast.LENGTH_LONG).show();
//
//                                    }
//                                }
//                            });
//                        } else {
//                            Toast.makeText(Register.this, "failed register", Toast.LENGTH_LONG).show();
//
//                        }
//                    }
//                });
//            }
//        });
    }
}