package com.example.proyek.adapter;

import android.annotation.SuppressLint;
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
import com.example.proyek.models.cart.CartModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CartAdapter extends FirebaseRecyclerAdapter<CartModel, CartAdapter.ViewHolder> {
    private static final int CLASS_ID = 104;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CartAdapter(@NonNull FirebaseRecyclerOptions<CartModel> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull CartAdapter.ViewHolder viewHolder, int i, @NonNull CartModel cartModel) {
        Glide.with(viewHolder.ivProductImg.getContext()).load(cartModel.getUrl()).into(viewHolder.ivProductImg);
        viewHolder.tvTitle.setText(cartModel.getName());
        viewHolder.tvOrderCount.setText(cartModel.getTotal_order());
        viewHolder.tvOrderPrice.setText("Rp. " + cartModel.getTotal_price());

        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String childID = getRef(i).getKey();

                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("CHILD_ID", childID);
                intent.putExtra("CLASS_ID", CLASS_ID);
                v.getContext().startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_cart, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProductImg;
        private TextView tvTitle, tvInstructions, tvEdit, tvOrderCount, tvOrderPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProductImg = itemView.findViewById(R.id.ivProductImg);
            tvTitle = itemView.findViewById(R.id.tvProductOrderTitle);
            tvInstructions = itemView.findViewById(R.id.tvInstructionsOrder);
            tvEdit = itemView.findViewById(R.id.tvEditOrder);
            tvOrderCount = itemView.findViewById(R.id.tvOrderCount);
            tvOrderPrice = itemView.findViewById(R.id.tvOrderPrice);
        }
    }
}
