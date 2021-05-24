package com.example.proyek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.proyek.adapter.RvAdapterUser;
import com.example.proyek.settergetter.RvItemSetGet;
import com.example.proyek.models.product.ProductModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AfterClickMenu extends AppCompatActivity {

    private TextView tvJudul;
    private RecyclerView rvSayur;
    RvAdapterUser recyclerAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ArrayList<ProductModel> listItem = new ArrayList<>();
    RecyclerView recyclerView;

    String Judul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_click_menu);

        tvJudul = findViewById(R.id.tvName);

        rvSayur = findViewById(R.id.rvSayur);
        RvItemSetGet rvItemSetGet = getIntent().getParcelableExtra("rv_item");

        Judul = rvItemSetGet.getName();
        tvJudul.setText(Judul);
//        tvHarga.setText(rvItemSetGet.get);
        showRecyclerView();

//        String name = getIntent().getStringExtra("judul");
//        tvJudul.setText(name);


    }

    private void showRecyclerView() {
        rvSayur.setLayoutManager(new LinearLayoutManager(AfterClickMenu.this));
        if (Judul.equals("Sayur")){
//            list.addAll(VegetableData.getListData());
//            ListAdapterVegetable listAdapterVegetable = new ListAdapterVegetable(list);
//            rvSayur.setAdapter(listAdapterVegetable);
//            listAdapterVegetable.setOnItemClickCallback(new ListAdapterVegetable.OnItemClickCallback() {
//                @Override
//                public void onItemClicked(VegetablesSetGet data) {
//                    Intent i = new Intent(AfterClickMenu.this, Detail.class);
//                    i.putExtra("vegetables_data", data);
//                    startActivity(i);
//                }
//            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvSayur.setLayoutManager(layoutManager);
            rvSayur.setItemAnimator(new DefaultItemAnimator());

            FirebaseRecyclerOptions<ProductModel> options = new FirebaseRecyclerOptions.Builder<ProductModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Data") , ProductModel.class).build();
            recyclerAdapter = new RvAdapterUser(options);
            rvSayur.setAdapter(recyclerAdapter);
        }else if (Judul.equals("Bumbu")){
//            list2.addAll(SeasoningData.getListData());
//            ListAdapterSeasoning listAdapterSeasoning = new ListAdapterSeasoning(list2);
//            rvSayur.setAdapter(listAdapterSeasoning);
//
//            listAdapterSeasoning.setOnItemClickCallback(new ListAdapterSeasoning.OnItemClickCallback() {
//                @Override
//                public void onItemClicked(SeasoningSetGet data) {
//                    Intent a = new Intent(AfterClickMenu.this, Detail.class);
//                    a.putExtra("seasoning_data", data);
//                    startActivity(a);
//                }
//            });
        }else{
//            list3.addAll(FruitsData.getListData());
//            ListAdapterFruits listAdapterFruits = new ListAdapterFruits(list3);
//            rvSayur.setAdapter(listAdapterFruits);
//
//            listAdapterFruits.setOnItemClickCallback(new ListAdapterFruits.OnItemClickCallback() {
//                @Override
//                public void onItemClicked(FruitsSetGet data) {
//                    Intent b = new Intent(AfterClickMenu.this, Detail.class);
//                    b.putExtra("fruits_data", data);
//                    startActivity(b);
//                }
//            });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapter.startListening();

    }
}