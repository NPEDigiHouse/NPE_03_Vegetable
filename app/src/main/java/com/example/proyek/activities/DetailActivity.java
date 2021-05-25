package com.example.proyek.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyek.R;
import com.example.proyek.models.product.ProductModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private DatabaseReference reference;
    private Toolbar tbDetail;
    private ImageView ivProductDetailBan, ivPosterImg;
    private TextView tvProductTitle, tvProductPrice, tvProductPlace, tvProductSold, tvProductFee, tvTotalOrder;
    private RatingBar rbProductReview;
    private TextInputEditText etInstructions;
    private MaterialButton mBtnPlus, mBtnMinus, mBtnCreateOrder, mBtnDeleteOrder;
    private String productID;
    private int totalOrder, productPrice;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tbDetail = findViewById(R.id.tbDetail);
        tbDetail.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        setSupportActionBar(tbDetail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        productID = getIntent().getStringExtra("PRODUCT_ID");

        ivProductDetailBan = findViewById(R.id.ivProductDetailBanner);
        ivPosterImg = findViewById(R.id.ivPosterImg);
        tvProductTitle = findViewById(R.id.tvProductTitle);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductPlace = findViewById(R.id.tvProductPlace);
        tvProductSold = findViewById(R.id.tvProductTotalSoldOut);
        tvProductFee = findViewById(R.id.tvProductOngkir);
        tvTotalOrder = findViewById(R.id.tvTotalOrder);
        mBtnPlus = findViewById(R.id.btnIncreaseOrder);
        mBtnMinus = findViewById(R.id.btnDecreaseOrder);
        mBtnCreateOrder = findViewById(R.id.btnCreateOrder);
        mBtnDeleteOrder = findViewById(R.id.btnDeleteOrder);

        reference = FirebaseDatabase.getInstance().getReference("Data").child(productID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductModel productModel = snapshot.getValue(ProductModel.class);
                productPrice = Integer.parseInt(productModel.getPrice());

                Glide.with(DetailActivity.this).load(productModel.getUrl()).into(ivProductDetailBan);
                Glide.with(DetailActivity.this).load(productModel.getUrl()).into(ivPosterImg);
                tvProductTitle.setText(productModel.getName());
                tvProductPrice.setText("Rp. " + productPrice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailActivity.this, "Fail to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });

        totalOrder = Integer.parseInt(tvTotalOrder.getText().toString());

        mBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalOrder += 1;

                mBtnCreateOrder.setVisibility(View.VISIBLE);
                mBtnDeleteOrder.setVisibility(View.GONE);

                tvTotalOrder.setText("" + totalOrder);
                mBtnCreateOrder.setText("Add to Cart - Rp. " + totalOrder * productPrice);
            }
        });

        mBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalOrder -= 1;
                if (totalOrder < 0) {
                    totalOrder = 0;

                    mBtnCreateOrder.setVisibility(View.GONE);
                    mBtnDeleteOrder.setVisibility(View.VISIBLE);
                } else {
                    mBtnCreateOrder.setVisibility(View.VISIBLE);
                    mBtnDeleteOrder.setVisibility(View.GONE);

                    tvTotalOrder.setText("" + totalOrder);
                    mBtnCreateOrder.setText("Add to Cart - Rp. " + totalOrder * productPrice);
                }
            }
        });
    }

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