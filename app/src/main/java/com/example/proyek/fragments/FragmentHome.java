package com.example.proyek.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyek.activities.CartActivity;
import com.example.proyek.models.ImageSliderData;
import com.example.proyek.R;
import com.example.proyek.activities.ShowMoreActivity;
import com.example.proyek.adapter.ImageSliderAdapter;
import com.example.proyek.adapter.PacketAdapter;
import com.example.proyek.adapter.ProductAdapter;
import com.example.proyek.models.packet.PacketModel;
import com.example.proyek.models.product.ProductModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class FragmentHome extends Fragment {
    //deklarasi variabel
    private View view;
    private DatabaseReference reference;
    private RecyclerView rvProduct, rvPacket;
    private ProductAdapter productAdapter;
    private PacketAdapter packetAdapter;
    private TextView tvShowMoreProduct, tvShowMorePacket;
    private FloatingActionButton fabCart;

    @Nullable
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inisialisasi variabel
        view = inflater.inflate(R.layout.fragment_home, container, false);

        reference = FirebaseDatabase.getInstance().getReference();

        rvProduct = view.findViewById(R.id.rvNewProduct);
        rvProduct.setHasFixedSize(true);

        rvPacket = view.findViewById(R.id.rvPacket);
        rvPacket.setHasFixedSize(true);

        tvShowMoreProduct = view.findViewById(R.id.tvnewProductShowMore);
        tvShowMorePacket = view.findViewById(R.id.tvPacketShowMore);

        //jika tvShowMore di klik
        tvShowMoreProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int ID = 301;
                Intent intent = new Intent(getActivity(), ShowMoreActivity.class);
                intent.putExtra("ID", ID);
                intent.putExtra("TITLE", "Our Product List");
                startActivity(intent);
            }
        });

        tvShowMorePacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int ID = 302;
                Intent intent = new Intent(getActivity(), ShowMoreActivity.class);
                intent.putExtra("ID", ID);
                intent.putExtra("TITLE", "Our Packet List");
                startActivity(intent);
            }
        });

        showProductRV();
        showPacketRV();

        // code untuk carousel
        ArrayList<ImageSliderData> sliderData = new ArrayList<>();
        SliderView sliderView = view.findViewById(R.id.imageSliderAds);

        sliderData.add(new ImageSliderData(R.drawable.banner_ad_1));
        sliderData.add(new ImageSliderData(R.drawable.banner_ad_2));
        sliderData.add(new ImageSliderData(R.drawable.banner_ad_3));

        ImageSliderAdapter adapter = new ImageSliderAdapter(getActivity(), sliderData);

        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(7);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

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

    private void showProductRV() {
        // mengatur layoutmanager untuk produk
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvProduct.setLayoutManager(horizontalLayoutManager);

        // mengambil data untuk recyclerview dari FirebaseDatabase
        FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                .setQuery(reference.child("Data"), ProductModel.class).build();

        // inisialsasi object productAdapter
        productAdapter = new ProductAdapter(options);

        // mengatur adapter
        rvProduct.setAdapter(productAdapter);
    }

    private void showPacketRV() {
        // sama sperti showProductRV()
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvPacket.setLayoutManager(horizontalLayoutManager);

        FirebaseRecyclerOptions<PacketModel> options = new FirebaseRecyclerOptions.Builder<PacketModel>()
                .setQuery(reference.child("Packet"), PacketModel.class).build();

        packetAdapter = new PacketAdapter(options);

        rvPacket.setAdapter(packetAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        // untuk FirebaseRecyclerOptions
        productAdapter.startListening();
        packetAdapter.startListening();
    }
}
