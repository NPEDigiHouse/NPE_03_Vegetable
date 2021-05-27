package com.example.proyek.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyek.R;
import com.example.proyek.activities.CartActivity;
import com.example.proyek.adapter.FavAdapter;
import com.example.proyek.models.cart.CartModel;
import com.example.proyek.models.fav.FavModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentFav extends Fragment {
    private View view;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private RecyclerView rvFav;
    private FavAdapter adapter;
    private FloatingActionButton fabCart;
    private ConstraintLayout clIfEmptyLayout, clFavContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //kondisi agar FragmentFav mengimplementaskan layout fragment_fav
         view = inflater.inflate(R.layout.fragment_fav, container, false);

         mAuth = FirebaseAuth.getInstance();
         mUser = mAuth.getCurrentUser();
         reference = FirebaseDatabase.getInstance().getReference().child("Login").child(mUser.getUid()).child("favorite");

        clIfEmptyLayout = view.findViewById(R.id.clEmptyFavorite);
        rvFav = view.findViewById(R.id.rvFavorite);
        rvFav.setHasFixedSize(true);

         reference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if (snapshot.exists()) {
                     clIfEmptyLayout.setVisibility(View.GONE);
                     rvFav.setVisibility(View.VISIBLE);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });



        showFavRV();
        adapter.startListening();

        fabCart = view.findViewById(R.id.fabCart);
        fabCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void showFavRV() {
        rvFav.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFav.setItemAnimator(new DefaultItemAnimator());

        FirebaseRecyclerOptions<FavModel> options = new FirebaseRecyclerOptions.Builder<FavModel>()
                .setQuery(reference, FavModel.class).build();

        adapter = new FavAdapter(options);

        rvFav.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
