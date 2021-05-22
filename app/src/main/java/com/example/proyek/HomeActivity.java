package com.example.proyek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.proyek.fragments.FragmentFav;
import com.example.proyek.fragments.FragmentHistory;
import com.example.proyek.fragments.FragmentHome;
import com.example.proyek.fragments.FragmentInbox;
import com.example.proyek.fragments.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

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

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    };
}