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
import com.example.proyek.models.fav.FavModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class FavAdapter extends FirebaseRecyclerAdapter<FavModel, FavAdapter.ViewHolder> {
    private static final int CLASS_ID = 105;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FavAdapter(@NonNull FirebaseRecyclerOptions<FavModel> options) {
        super(options);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull FavAdapter.ViewHolder viewHolder, int i, @NonNull FavModel favModel) {
        Glide.with(viewHolder.ivPoster.getContext()).load(favModel.getUrl()).into(viewHolder.ivPoster);
        viewHolder.tvTitle.setText(favModel.getName());
        viewHolder.tvPrice.setText("Rp. " + favModel.getPrice());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getRef(i).getKey();

                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("CLASS_ID", CLASS_ID);
                intent.putExtra("CHILD_ID", id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_favorite, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPoster;
        private TextView tvTitle, tvPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.ivPosterFav);
            tvTitle = itemView.findViewById(R.id.tvTitleFav);
            tvPrice = itemView.findViewById(R.id.tvPriceFav);
        }
    }

}
