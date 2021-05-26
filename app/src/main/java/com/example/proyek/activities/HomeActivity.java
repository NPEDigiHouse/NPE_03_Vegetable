package com.example.proyek.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.proyek.R;
import com.example.proyek.fragments.FragmentFav;
import com.example.proyek.fragments.FragmentHistory;
import com.example.proyek.fragments.FragmentHome;
import com.example.proyek.fragments.FragmentInbox;
import com.example.proyek.fragments.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationItemView = findViewById(R.id.bottom_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FragmentHome())
                    .commit();
        }

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onStart() {
        super.onStart();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(HomeActivity.this, GetStartedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.nav_profile:
                selectedFragment = new FragmentProfile();
                break;
            case R.id.nav_inbox:
                selectedFragment = new FragmentInbox();
                break;
            case R.id.nav_history:
                selectedFragment = new FragmentHistory();
                break;
            case R.id.nav_fav:
                selectedFragment = new FragmentFav();
                break;
            case R.id.nav_home:
                selectedFragment = new FragmentHome();
                break;
        }

        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    };

}