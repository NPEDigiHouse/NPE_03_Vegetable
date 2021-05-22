package com.example.proyek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private Button btnOut;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();
        mAuth = FirebaseAuth.getInstance();

        final TextView textViewName = findViewById(R.id.tvName);
        final TextView textViewEmail = findViewById(R.id.tvEmail);

        btnOut = findViewById(R.id.btnOut);
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                Intent a = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(a);
                finish();
            }
        });

        reference.child("Login").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").getValue(String.class);
                textViewEmail.setText(name);
//                textViewName.setText(fullName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ProfileActivity.this, "something wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}