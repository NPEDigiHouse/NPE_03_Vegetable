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
import com.example.proyek.models.packet.PacketModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ShowMoreAdapter extends FirebaseRecyclerAdapter<PacketModel, ShowMoreAdapter.ViewHolder> {
    private final int CLASS_ID = 103;
    private String child;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ShowMoreAdapter(@NonNull FirebaseRecyclerOptions<PacketModel> options, String child) {
        super(options);
        this.child = child;
    }

    @Override
    protected void onBindViewHolder(@NonNull ShowMoreAdapter.ViewHolder viewHolder, int i, @NonNull PacketModel packetModel) {
        String price = "Rp. " + packetModel.getPrice();
        Glide.with(viewHolder.ivProductImg.getContext()).load(packetModel.getUrl()).into(viewHolder.ivProductImg);
        viewHolder.tvProductTitle.setText(packetModel.getName());
        viewHolder.tvProductPrice.setText(price);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String packetId = getRef(i).getKey();

                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("CLASS_ID", CLASS_ID);
                intent.putExtra("CHILD_ID", packetId);
                intent.putExtra("CHILD", child);
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ShowMoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_show_more, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProductImg;
        private TextView tvProductTitle, tvProductPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProductImg = itemView.findViewById(R.id.ivPosterProduct);
            tvProductTitle = itemView.findViewById(R.id.tvTitleProduct);
            tvProductPrice = itemView.findViewById(R.id.tvPriceProduct);
        }
    }
}
