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
    
    //deklarasi variabel
    private Toolbar tbShowMore;
    private DatabaseReference reference;
    private RecyclerView rvProduct, rvPacket;
    private ShowMoreAdapter adapter;
    private TextView tvTitle;
    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more);

        //inisialisasi variabel
        tvTitle = findViewById(R.id.tvShowMoreTitle);
        tbShowMore = findViewById(R.id.tbShowMore);
        tbShowMore.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24_primary);
        setSupportActionBar(tbShowMore);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        reference = FirebaseDatabase.getInstance().getReference();

        //cek untuk bagian showMore mana yang di klik
        //dengan memberikan id masing"
        ID = getIntent().getIntExtra("ID", 0);
        switch (ID) {
                //id 301 untuk product
            case 301:
                tvTitle.setText(getIntent().getStringExtra("TITLE"));
                rvProduct = findViewById(R.id.rvShowMore);
                rvProduct.setHasFixedSize(true);
                showProductRV();
                break;
                //id 302 untuk packet
            case 302:
                tvTitle.setText(getIntent().getStringExtra("TITLE"));
                rvPacket = findViewById(R.id.rvShowMore);
                rvPacket.setHasFixedSize(true);
                showPacketRV();
                break;
        }

        adapter.startListening();
    }

    //method untuk menampilkan recyclerview product
    private void showProductRV() {
        //memanggil layoutmanager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //memberikan layoutmanager pada recyclerview
        rvProduct.setLayoutManager(layoutManager);
        rvProduct.setItemAnimator(new DefaultItemAnimator());

        //memanggil firebaserecylceroption untuk terima data dari packet modal
        FirebaseRecyclerOptions<PacketModel> options = new FirebaseRecyclerOptions.Builder<PacketModel>()
                .setQuery(reference.child("Data"), PacketModel.class).build();

        //mengisi adapter dengan options dan data
        adapter = new ShowMoreAdapter(options, "Data");

        //memberikan adapter untuk recyclerview
        rvProduct.setAdapter(adapter);
    }

    //untuk menampilkan recyclerview packet
    private void showPacketRV() {
        //inisialisai variabel
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPacket.setLayoutManager(layoutManager);
        rvPacket.setItemAnimator(new DefaultItemAnimator());

        //memanggil firebaserecycleroptions untuk PacketModal
        FirebaseRecyclerOptions<PacketModel> options = new FirebaseRecyclerOptions.Builder<PacketModel>()
                .setQuery(reference.child("Packet"), PacketModel.class).build();

        //mengisi adapter dengan options, dan packet
        adapter = new ShowMoreAdapter(options, "Packet");

        //memberikan adapter pada recyclervieew
        rvPacket.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return true;
    }

    //agar adapter bekerja saat start
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
