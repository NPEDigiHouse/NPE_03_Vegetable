package com.example.proyek.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyek.R;
import com.example.proyek.adapter.PacketAdapter;
import com.example.proyek.adapter.ProductAdapter;
import com.example.proyek.adapter.RvAdapterUser;
import com.example.proyek.models.packet.PacketModel;
import com.example.proyek.models.product.ProductModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentHome extends Fragment {
    private View view;
    private DatabaseReference reference;
    private RecyclerView rvNewProduct, rvPacket;
    private ProductAdapter productAdapter;
    private PacketAdapter packetAdapter;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        reference = FirebaseDatabase.getInstance().getReference();

        rvNewProduct = view.findViewById(R.id.rvNewProduct);
        rvNewProduct.setHasFixedSize(true);


        // Custom Hint Color in Searchview
        TextView searchHint = searchView.findViewById(R.id.svHome);
        searchHint.setHint("Hari ini kamu mau beli apa?");
        searchHint.setHintTextColor(Color.BLACK);

        rvPacket = view.findViewById(R.id.rvPacket);
        rvPacket.setHasFixedSize(true);

        showNewProductRV();
        showPacketRV();

        return view;
    }

    public void showNewProductRV() {
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvNewProduct.setLayoutManager(horizontalLayoutManager);

        rvNewProduct.setItemAnimator(new DefaultItemAnimator());

        FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>().setLifecycleOwner(getActivity())
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Data"), ProductModel.class).build();

        productAdapter = new ProductAdapter(options);

        rvNewProduct.setAdapter(productAdapter);
    }

    private void showPacketRV() {
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvPacket.setLayoutManager(horizontalLayoutManager);

        rvPacket.setItemAnimator(new DefaultItemAnimator());

        FirebaseRecyclerOptions<PacketModel> options = new FirebaseRecyclerOptions.Builder<PacketModel>().setLifecycleOwner(getActivity())
                .setQuery(reference.child("Packet"), PacketModel.class).build();

        packetAdapter = new PacketAdapter(options);

        rvPacket.setAdapter(packetAdapter);
    }
}
