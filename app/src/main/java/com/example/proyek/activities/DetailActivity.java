package com.example.proyek.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.proyek.models.cart.CartModel;
import com.example.proyek.models.fav.FavModel;
import com.example.proyek.models.packet.PacketModel;
import com.example.proyek.models.product.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    // class ini untuk menampilkan detail dari produk yang di klik dari recyclerview

    //deklarasi variabel
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference(), mReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Toolbar tbDetail;
    private ImageView ivDetailBan, ivPosterImg;
    private TextView tvTitle, tvPrice, tvPlace, tvSold, tvFee, tvTotalOrder;
    private RatingBar rbReview;
    private TextInputEditText etInstructions;
    private MaterialButton mBtnPlus, mBtnMinus, mBtnCreateOrder, mBtnDeleteOrder;
    private String childID, name, sPrice, url, temp;
    private int totalOrder, price, classID;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // toolbar
        tbDetail = findViewById(R.id.tbDetail);
        tbDetail.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        setSupportActionBar(tbDetail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // mengecek darimana asal intent
        classID = getIntent().getIntExtra("CLASS_ID", 0);
        switch (classID) {
            case 101: // dari ProductAdapter
                childID = getIntent().getStringExtra("CHILD_ID");
                mReference = FirebaseDatabase.getInstance().getReference("Data").child(childID);
                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ProductModel productModel = snapshot.getValue(ProductModel.class);
                        price = Integer.parseInt(productModel.getPrice());

                        url = productModel.getUrl();
                        //untuk menampilkan gambar dengan menggunakan Glide, agar datanya tidak terlalu besar
                        Glide.with(DetailActivity.this).load(productModel.getUrl()).into(ivDetailBan);
                        Glide.with(DetailActivity.this).load(productModel.getUrl()).into(ivPosterImg);
                        tvTitle.setText(productModel.getName());
                        tvPrice.setText("Rp. " + price);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DetailActivity.this, "Fail to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case 102: // dari PacketAdapter
                childID = getIntent().getStringExtra("CHILD_ID");
                mReference = FirebaseDatabase.getInstance().getReference("Packet").child(childID);
                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        PacketModel packetModel = snapshot.getValue(PacketModel.class);
                        price = Integer.parseInt(packetModel.getPrice());

                        url = packetModel.getUrl();
                        Glide.with(DetailActivity.this).load(packetModel.getUrl()).into(ivDetailBan);
                        Glide.with(DetailActivity.this).load(packetModel.getUrl()).into(ivPosterImg);
                        tvTitle.setText(packetModel.getName());
                        tvPrice.setText("Rp. " + price);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DetailActivity.this, "Fail to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case 103:
                childID = getIntent().getStringExtra("CHILD_ID");
                String child = getIntent().getStringExtra("CHILD");
                mReference = FirebaseDatabase.getInstance().getReference(child).child(childID);
                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        PacketModel packetModel = snapshot.getValue(PacketModel.class);
                        price = Integer.parseInt(packetModel.getPrice());

                        url = packetModel.getUrl();
                        Glide.with(DetailActivity.this).load(packetModel.getUrl()).into(ivDetailBan);
                        Glide.with(DetailActivity.this).load(packetModel.getUrl()).into(ivPosterImg);
                        tvTitle.setText(packetModel.getName());
                        tvPrice.setText("Rp. " + price);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DetailActivity.this, "Fail to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case 104: // dari CartAdapter
                childID = getIntent().getStringExtra("CHILD_ID");
                mReference = FirebaseDatabase.getInstance().getReference().child("Login").child(mUser.getUid()).child("cart_temp").child(childID);
                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        CartModel cartModel = snapshot.getValue(CartModel.class);
                        url = cartModel.getUrl();
                        Glide.with(DetailActivity.this).load(cartModel.getUrl()).into(ivDetailBan);
                        Glide.with(DetailActivity.this).load(cartModel.getUrl()).into(ivPosterImg);
                        tvTitle.setText(cartModel.getName());
                        tvPrice.setText("Rp. " + cartModel.getPrice());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DetailActivity.this, "Fail to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case 105:
                childID = getIntent().getStringExtra("CHILD_ID");
                mReference = FirebaseDatabase.getInstance().getReference().child("Login").child(mUser.getUid()).child("favorite").child(childID);
                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        FavModel model = snapshot.getValue(FavModel.class);
                        url = model.getUrl();
                        Glide.with(DetailActivity.this).load(model.getUrl()).into(ivDetailBan);
                        Glide.with(DetailActivity.this).load(model.getUrl()).into(ivPosterImg);
                        tvTitle.setText(model.getName());
                        tvPrice.setText("Rp. " + model.getPrice());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DetailActivity.this, "Fail to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case 0:
                Intent intent = new Intent(DetailActivity.this, HomeActivity.class);
                Toast.makeText(DetailActivity.this, "Error parsing data", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
                break;
        }

        //inisialisasi variabel
        ivDetailBan = findViewById(R.id.ivProductDetailBanner);
        ivPosterImg = findViewById(R.id.ivPosterImg);
        tvTitle = findViewById(R.id.tvProductTitle);
        tvPrice = findViewById(R.id.tvProductPrice);
        tvPlace = findViewById(R.id.tvProductPlace);
        tvSold = findViewById(R.id.tvProductTotalSoldOut);
        tvFee = findViewById(R.id.tvProductOngkir);
        tvTotalOrder = findViewById(R.id.tvTotalOrder);
        mBtnPlus = findViewById(R.id.btnIncreaseOrder);
        mBtnMinus = findViewById(R.id.btnDecreaseOrder);
        mBtnCreateOrder = findViewById(R.id.btnCreateOrder);
        mBtnDeleteOrder = findViewById(R.id.btnDeleteOrder);


        totalOrder = Integer.parseInt(tvTotalOrder.getText().toString());

        // untuk menambah jumlah dari item yang ingin dibeli
        mBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalOrder += 1;

                mBtnCreateOrder.setVisibility(View.VISIBLE);
                mBtnDeleteOrder.setVisibility(View.GONE);

                tvTotalOrder.setText("" + totalOrder);
                mBtnCreateOrder.setText("Add to Cart - Rp. " + totalOrder * price);
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
                    mBtnCreateOrder.setText("Add to Cart - Rp. " + totalOrder * price);
                }
            }
        });

        mBtnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalOrder <= 0) {
                    totalOrder = 0;
                    Toast.makeText(DetailActivity.this, "Please add item", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(DetailActivity.this, CartActivity.class);

                    // mengambil data produk lalu masukkan kedalam hashmap
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", tvTitle.getText().toString());
                    map.put("price", tvPrice.getText().toString());
                    map.put("url", url);
                    map.put("total_order", tvTotalOrder.getText().toString());
                    map.put("total_price", String.valueOf(totalOrder * price));
                    map.put("child_id", childID);

                    // mengambil id dari child Login > user > cart_temp > id produk > child_id
                    reference.child("Login").child(mUser.getUid()).child("cart_temp").child(childID)
                            .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            temp = snapshot.child("child_id").getValue(String.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // mengecek apakah id produk sudah ada atau tidak di dalam database
                    if (childID == temp) { // jika ada
                        reference.child("Login").child(mUser.getUid()).child("cart_temp").child(childID).updateChildren(map, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(DetailActivity.this, "Succes add to cart", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else { // jika tidak ada
                        // memasukkan nilai hashmap ke dalam child Login > user > cart_temp > id produk
                        reference.child("Login").child(mUser.getUid()).child("cart_temp").child(childID).setValue(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(DetailActivity.this, "Succes add to cart", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    startActivity(intent);
                }
            }
        });
        
        mBtnDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mengambil id dari child Login > user > cart_temp > id produk > child_id
                reference.child("Login").child(mUser.getUid()).child("cart_temp").child(childID)
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        temp = snapshot.child("child_id").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // mengecek apakah id produk sudah ada atau tidak di dalam database
                if (childID == temp) { // jika ada
                    reference.child("Login").child(mUser.getUid()).child("cart_temp").child(childID).removeValue();
                } else {
                    Toast.makeText(DetailActivity.this, "You haven\'t add it to your cart yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // jika mengklik item dri toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.addToFav) {
            // mengambil data produk lalu masukkan kedalam hashmap
            Map<String, Object> map = new HashMap<>();
            map.put("name", tvTitle.getText().toString());
            map.put("price", tvPrice.getText().toString());
            map.put("url", url);
            map.put("child_id", childID);

            // mengambil id dari child Login > user > cart_temp > id produk > child_id
            reference.child("Login").child(mUser.getUid()).child("favorite").child(childID)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            temp = snapshot.child("child_id").getValue(String.class);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

            // mengecek apakah id produk sudah ada atau tidak di dalam database
            if (childID == temp) { // jika ada
                reference.child("Login").child(mUser.getUid()).child("favorite").child(childID).updateChildren(map, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(DetailActivity.this, "Succes add to favorite", Toast.LENGTH_SHORT).show();
                    }
                });
            } else { // jika tidak ada
                // memasukkan nilai hashmap ke dalam child Login > user > cart_temp > id produk
                reference.child("Login").child(mUser.getUid()).child("favorite").child(childID).setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(DetailActivity.this, "Succes add to favorite", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        return true;
    }

    // menampilkan icon toolbar
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
