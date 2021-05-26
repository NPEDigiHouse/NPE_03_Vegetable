package com.example.proyek.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyek.R;
import com.example.proyek.models.user.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    // class ini untuk mengubah data dari profile

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Toolbar tbProfileEdit;
    private ImageView ivProfilePst;
    private MaterialButton mBtnChangeProfilePoster, mBtnChangeProfileImg;
    private CircleImageView civProfileImg;
    private TextInputEditText etUsername, etEmail, etPhone, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // FirebaseAuth dan Database
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Login").child(mAuth.getUid());

        // toolbar
        tbProfileEdit = findViewById(R.id.tbProfileEdit);
        tbProfileEdit.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        setSupportActionBar(tbProfileEdit);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ivProfilePst = findViewById(R.id.ivPosterProfile);
        mBtnChangeProfilePoster = findViewById(R.id.btnChangeProfilePoster);
        civProfileImg = findViewById(R.id.civImageProfile);
        mBtnChangeProfileImg = findViewById(R.id.btnChangeProfileImage);
        etUsername = findViewById(R.id.etUsernameEdit);
        etEmail = findViewById(R.id.etEmailEdit);
        etPhone = findViewById(R.id.etPhoneEdit);
        etPassword = findViewById(R.id.etPasswordEdit);

        // retrieve data from Firebase Database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user = snapshot.getValue(UserModel.class);

                etUsername.setText(user.getUsername());
                etEmail.setText(user.getEmail());
                etPhone.setText(user.getPhoneNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileActivity.this, "Fail to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.saveChanges) { // untuk menyimpan perubahan dari edit text ke FirebaseDatabase
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();
            String password = etPassword.getText().toString();

            if (password.length() < 6) {
                etPassword.setError("Your password must be more than 6 characters");
                etPassword.requestFocus();
            }

            Map<String, Object> newDataMap = new HashMap<>();
            newDataMap.put("username", username);
            newDataMap.put("email", email);
            newDataMap.put("phoneNumber", phone);

            reference.updateChildren(newDataMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(EditProfileActivity.this, "Successfully edit profile", Toast.LENGTH_LONG).show();
                }
            });

            user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(EditProfileActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
//            user.updateEmail(email);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);

        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();

            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources()
                        .getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
        }

        return true;
    }
}