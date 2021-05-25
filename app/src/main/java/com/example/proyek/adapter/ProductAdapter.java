package com.example.proyek.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyek.R;
import com.example.proyek.activities.DetailActivity;
import com.example.proyek.models.product.ProductModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ProductAdapter extends FirebaseRecyclerAdapter<ProductModel, ProductAdapter.ViewHolder> {
    private final int CLASS_CODE = 101;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProductAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull ProductModel productModel) {
        String price = "Rp. " + productModel.getPrice();
        Glide.with(viewHolder.ivProductImg.getContext()).load(productModel.getUrl()).into(viewHolder.ivProductImg);
        viewHolder.tvProductTitle.setText(productModel.getName());
        viewHolder.tvProductPrice.setText(price);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId = getRef(i).getKey();

                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("PRODUCT_ID", productId);
                intent.putExtra("CLASS_CODE", CLASS_CODE);
                v.getContext().startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProductImg;
        private TextView tvProductTitle, tvProductPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProductImg = itemView.findViewById(R.id.ivProductImg);
            tvProductTitle = itemView.findViewById(R.id.tvProductTitle);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}
