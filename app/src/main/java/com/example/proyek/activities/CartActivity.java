package com.example.proyek.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyek.R;
import com.example.proyek.adapter.CartAdapter;
import com.example.proyek.models.cart.CartModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    private DatabaseReference reference;
    private RecyclerView rvCart;
    private CartAdapter adapter;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private TextView tvSubtotal, tvTotalPrice;
    private MaterialButton mBtnCreateOrder, mBtnOrderTrue, mBtnOrderFalse;
    private int totalCost;
    private Dialog dialogBox;
    private ConstraintLayout clIfEmptyLayout, clCartContainer;
    private boolean temp;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // toolbar
        Toolbar tbCart = findViewById(R.id.tbCart);
        tbCart.setNavigationIcon(R.drawable.ic_baseline_close_24);
        setSupportActionBar(tbCart);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Login").child(mUser.getUid()).child("cart_temp");
        tvSubtotal = findViewById(R.id.tvSubTotal);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        mBtnCreateOrder = findViewById(R.id.btnCreateOrder);
        clCartContainer = findViewById(R.id.clCartContainer);
        clIfEmptyLayout = findViewById(R.id.clEmptyCart);
        
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot != null) {
                    clCartContainer.setVisibility(View.VISIBLE);
                    clIfEmptyLayout.setVisibility(View.GONE);

                    rvCart = findViewById(R.id.rvProductOrderPreview);
                    rvCart.setHasFixedSize(true);
                    showCartRV();
//                    adapter.startListening();
                    
                } else if (snapshot == null) {
                    clCartContainer.setVisibility(View.GONE);
                    clIfEmptyLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        totalCost = 0;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Log.d("Detail", data.child("total_price").getValue().toString());

                    CartModel model = data.getValue(CartModel.class);

                    String totalCost = data.child("total_price").getValue().toString();
                    Log.d("Detail", totalCost);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tvSubtotal.setText("Rp. " + totalCost);

        totalCost += 10000;
        tvTotalPrice.setText("Rp. " + totalCost);

        mBtnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBox = new Dialog(CartActivity.this);
                dialogBox.setContentView(R.layout.dialog_box_confirm_order);
                dialogBox.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                dialogBox.setCancelable(true);
                dialogBox.getWindow().getAttributes().windowAnimations = R.style.DialogBoxAnimation;

                mBtnOrderTrue = dialogBox.findViewById(R.id.btnOrderTrue);
                mBtnOrderFalse = dialogBox.findViewById(R.id.btnOrderFalse);

                mBtnOrderFalse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBox.dismiss();
                    }
                });

                mBtnOrderTrue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        temp = false;
                        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CartActivity.this, "Thank you", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(CartActivity.this, "Error, please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        
                        dialogBox.dismiss();
                        clCartContainer.setVisibility(View.GONE);
                        clIfEmptyLayout.setVisibility(View.VISIBLE);
                    }
                });

                dialogBox.show();
            }
        });

    }

    //untuk menampilkan recyclerview cart
    private void showCartRV() {
        //inisialisai variabel
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvCart.setLayoutManager(layoutManager);
        rvCart.setItemAnimator(new DefaultItemAnimator());

        // memanggil firebaserecycleroptions untuk CartModel
        FirebaseRecyclerOptions<CartModel> options = new FirebaseRecyclerOptions.Builder<CartModel>().setLifecycleOwner(this)
                .setQuery(reference, CartModel.class).build();

        //mengisi adapter dengan options, dan packet
        adapter = new CartAdapter(options);

        //memberikan adapter pada recyclervieew
        rvCart.setAdapter(adapter);
//        adapter.startListening();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return true;
    }

    //agar adapter bekerja saat start

}