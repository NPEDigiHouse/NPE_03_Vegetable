package com.example.proyek.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyek.R;
import com.example.proyek.models.user.UserModel;
import com.example.proyek.activities.GetStartedActivity;
import com.example.proyek.activities.HomeActivity;
import com.example.proyek.admin.AdminActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText etUsernameOrEmail, etPassword;
    private Button btnSignIn, btnGoogleSignIn;
    private TextView tvSignUp, tvForgotPass;
    private GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "GoogleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        etUsernameOrEmail = findViewById(R.id.etUsernameorEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnGoogleSignIn = findViewById(R.id.btnSignInWithGoogle);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvForgotPass = findViewById(R.id.tvForgotPassword);

        createRequest();

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etUsernameOrEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty()) {
                    etUsernameOrEmail.setError("Mohon masukkan email anda !");
                    etUsernameOrEmail.requestFocus();
                } else if (!isUsernameValid(email)) {
                    etUsernameOrEmail.setError("Mohon masukkan email yang valid !");
                    etUsernameOrEmail.requestFocus();
                } else if (password.isEmpty()) {
                    etPassword.setError("Mohon masukkan password anda !");
                    etPassword.requestFocus();
                } else if (password.length() < 6) {
                    etPassword.setError("Password harus lebih dari 6 karakter !");
                    etPassword.requestFocus();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Please fill the empty space !", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("Login").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("as").getValue(String.class).equals("admin")) {
                                            Intent intent = new Intent(SignInActivity.this, AdminActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        } else if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("as").getValue(String.class).equals("user")) {
                                            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            } else {
                                Toast.makeText(SignInActivity.this, "Tolon masukkan email atau password yang terdaftar !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignInActivity.this, "Telah terjadi kesalahan, mohon coba ulang", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = mAuth.getCurrentUser();
////        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//
//        if (user != null){
//            startActivity(new Intent(SignInActivity.this, HomeActivity.class));
//            finish();
////            databaseReference.child("Login").addListenerForSingleValueEvent(new ValueEventListener() {
////                @Override
////                public void onDataChange(@NonNull DataSnapshot snapshot) {
////                    if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("as").getValue(String.class).equals("admin")){
////                        startActivity(new Intent(MainActivity.this, AdminActivity.class));
////                        finish();
////                    }else {
////
////                    }
////                }
////
////                @Override
////                public void onCancelled(@NonNull DatabaseError error) {
////
////                }
////            });
//        }
//    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser mUser = mAuth.getCurrentUser();
                            String role = "user";

                            String username = mUser.getDisplayName();
                            String email = mUser.getEmail();
                            String phone = mUser.getPhoneNumber();
                            UserModel user = new UserModel(username, email, phone, role);

                            FirebaseDatabase.getInstance().getReference("Login").child(FirebaseAuth.getInstance().getCurrentUser()
                                    .getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, "Telah terjadi kesalahan, mohon coba ulang", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    boolean isUsernameValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignInActivity.this, GetStartedActivity.class);
        startActivity(intent);
    }
}