package com.example.proyek.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyek.R;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

//    private ImageView photo, btnTambah, btnKurang;
//    TextView tvName, tvHarga;
//    int harga = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar tbDetail = findViewById(R.id.tbDetail);
        tbDetail.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        setSupportActionBar(tbDetail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

//        tvName = findViewById(R.id.tvJudul);
//        tvHarga = findViewById(R.id.tvHarga);
//        photo = findViewById(R.id.photo);
//        btnTambah = findViewById(R.id.btnTambahItem);
//        btnKurang = findViewById(R.id.btnKurangItem);
//
//        btnTambah.setOnClickListener(this);
//        btnKurang.setOnClickListener(this);

//        VegetablesSetGet vegetablesSetGet = getIntent().getParcelableExtra("vegetables_data");
//        SeasoningSetGet seasoningSetGet = getIntent().getParcelableExtra("seasoning_data");
//        FruitsSetGet fruitsSetGet = getIntent().getParcelableExtra("fruits_data");

//        if (vegetablesSetGet != null){
//            tvName.setText(vegetablesSetGet.getName());
//            harga = vegetablesSetGet.getHarga();
//            tvHarga.setText(String.valueOf(harga));
//            photo.setImageResource(vegetablesSetGet.getPhoto());
//        }else if (fruitsSetGet != null){
//            tvName.setText(fruitsSetGet.getName());
//            harga = vegetablesSetGet.getHarga();
//            tvHarga.setText(String.valueOf(harga));
//            photo.setImageResource(fruitsSetGet.getPhoto());
//        }
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btnKurangItem:
//
//            case R.id.btnTambahItem:
//
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        // else if (item.getItemId() == R.id.addToFav) {Do something here!}
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);

        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();

            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources()
                        .getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
        }

        return true;
    }
}