package com.example.proyek.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.proyek.auth.SignInActivity;
import com.example.proyek.R;
import com.example.proyek.adapter.RecyclerAdapter;
import com.example.proyek.models.product.ProductModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    //deklarasi variabel
    Button btnLogout, btnInput;
    private FirebaseAuth mAuth;
    RecyclerAdapter recyclerAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ArrayList<ProductModel> listItem = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //inisialisasi variabel
        recyclerView = findViewById(R.id.rvSayurAdmin);
        //memberikan layout manager ke recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //memanggil FirebaseRecyclerOptions untuk menampilkan data
        FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Data") , ProductModel.class).build();
        recyclerAdapter = new RecyclerAdapter(options);
        recyclerView.setAdapter(recyclerAdapter);

        //inisialisasi mAuth
        mAuth = FirebaseAuth.getInstance();
        
        
        //inisialisasi btnLogout
        btnLogout = findViewById(R.id.btnLogout);
        //dimana jika di klik
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //maka akan signout dan ke SignInActivity
                mAuth.signOut();
                startActivity(new Intent(AdminActivity.this, SignInActivity.class));
                finish();
            }
        });

        //inisialisasi btnInput
        btnInput = findViewById(R.id.btnInput);
        //dimana jika di klik
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //maka DialogFragment akan di panggil, dan tampil
                DialogFragment dialogFragment = new DialogForm();
                dialogFragment.show(getSupportFragmentManager(), "form");
            }
        });

    }

    //onStart agar reccyclerview nya muncul saat app dijalankan
    @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }


}
