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
    //deklarasi firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        //memanggil bottomnavigationview
        BottomNavigationView bottomNavigationItemView = findViewById(R.id.bottom_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(navListener);

        //mengatur agar fragment yang tampil awal adalah FragmentHome
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FragmentHome())
                    .commit();
        }

        mAuth = FirebaseAuth.getInstance();


    }

    // mengatur untuk saat app dibuka dan user login agar ke activity GetStartedActivity terlebih dahulu
    @Override
    public void onStart() {
        super.onStart();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //mengambil user yang login
                user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    //mengarahkan ke activity GetStartedActivity
                    Intent intent = new Intent(HomeActivity.this, GetStartedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        mAuth.addAuthStateListener(mAuthStateListener);
    }

    //mengatur untuk bagian BottomNavigationView
    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        
        //kondisi untuk cek item/icon bottomnav mana yang di klik
        //jika di klik salah satunya, maka akan mengampilkan fragment yang seusai
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
