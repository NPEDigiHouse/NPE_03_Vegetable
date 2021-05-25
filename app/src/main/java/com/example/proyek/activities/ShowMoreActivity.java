package com.example.proyek.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyek.R;
import com.example.proyek.adapter.PacketAdapter;
import com.example.proyek.adapter.ProductAdapter;
import com.example.proyek.adapter.RvAdapterUser;
import com.example.proyek.adapter.ShowMoreAdapter;
import com.example.proyek.models.packet.PacketModel;
import com.example.proyek.settergetter.RvItemSetGet;
import com.example.proyek.models.product.ProductModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class ShowMoreActivity extends AppCompatActivity {
    private Toolbar tbShowMore;
    private DatabaseReference reference;
    private RecyclerView rvProduct, rvPacket;
    private ShowMoreAdapter adapter;
    private RvAdapterUser rvAdapterUser;
    private TextView tvTitle;
    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more);

        tvTitle = findViewById(R.id.tvShowMoreTitle);
        tbShowMore = findViewById(R.id.tbShowMore);
        tbShowMore.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24_primary);
        setSupportActionBar(tbShowMore);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        reference = FirebaseDatabase.getInstance().getReference();

        ID = getIntent().getIntExtra("ID", 0);
        switch (ID) {
            case 301:
                tvTitle.setText(getIntent().getStringExtra("TITLE"));
                rvProduct = findViewById(R.id.rvShowMore);
                rvProduct.setHasFixedSize(true);
                showProductRV();
                break;
            case 302:
                tvTitle.setText(getIntent().getStringExtra("TITLE"));
                rvPacket = findViewById(R.id.rvShowMore);
                rvPacket.setHasFixedSize(true);
                showPacketRV();
                break;
        }

        adapter.startListening();
    }

    private void showProductRV() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvProduct.setLayoutManager(layoutManager);
        rvProduct.setItemAnimator(new DefaultItemAnimator());

        FirebaseRecyclerOptions<PacketModel> options = new FirebaseRecyclerOptions.Builder<PacketModel>()
                .setQuery(reference.child("Data"), PacketModel.class).build();

        adapter = new ShowMoreAdapter(options, "Data");

        rvProduct.setAdapter(adapter);
    }

    private void showPacketRV() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPacket.setLayoutManager(layoutManager);
        rvPacket.setItemAnimator(new DefaultItemAnimator());

        FirebaseRecyclerOptions<PacketModel> options = new FirebaseRecyclerOptions.Builder<PacketModel>()
                .setQuery(reference.child("Packet"), PacketModel.class).build();

        adapter = new ShowMoreAdapter(options, "Packet");

        rvPacket.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // untuk FirebaseRecyclerOptions
        adapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }
}