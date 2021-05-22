package com.example.proyek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView photo, btnTambah, btnKurang;
    TextView tvName, tvHarga;
    int harga = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = findViewById(R.id.tvJudul);
        tvHarga = findViewById(R.id.tvHarga);
        photo = findViewById(R.id.photo);
        btnTambah = findViewById(R.id.btnTambahItem);
        btnKurang = findViewById(R.id.btnKurangItem);

        btnTambah.setOnClickListener(this);
        btnKurang.setOnClickListener(this);

//        VegetablesSetGet vegetablesSetGet = getIntent().getParcelableExtra("vegetables_data");
//        SeasoningSetGet seasoningSetGet = getIntent().getParcelableExtra("seasoning_data");
//        FruitsSetGet fruitsSetGet = getIntent().getParcelableExtra("fruits_data");

//        if (vegetablesSetGet != null){
//            tvName.setText(vegetablesSetGet.getName());
//            harga = vegetablesSetGet.getHarga();
//            tvHarga.setText(String.valueOf(harga));
//
//            photo.setImageResource(vegetablesSetGet.getPhoto());
//        }else if (fruitsSetGet != null){
//            tvName.setText(fruitsSetGet.getName());
//            harga = vegetablesSetGet.getHarga();
//            tvHarga.setText(String.valueOf(harga));
//            photo.setImageResource(fruitsSetGet.getPhoto());
//        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnKurangItem:

            case R.id.btnTambahItem:

        }
    }
}