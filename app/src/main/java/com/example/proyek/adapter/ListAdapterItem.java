package com.example.proyek.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyek.R;
import com.example.proyek.settergetter.RvItemSetGet;

import java.util.ArrayList;

public class ListAdapterItem extends RecyclerView.Adapter<ListAdapterItem.ListViewHolder> {

    private ArrayList<RvItemSetGet> listItem;

    public ListAdapterItem(ArrayList<RvItemSetGet> list){
        this.listItem = list;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_default, parent, false);
        return  new ListViewHolder(view);
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        RvItemSetGet rvItemSetGet = listItem.get(position);
        holder.tvName.setText(rvItemSetGet.getName());
        holder.imgPhoto.setImageResource(rvItemSetGet.getPhoto());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listItem.get(holder.getBindingAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvName;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.ivProductImgDefault);
            tvName = itemView.findViewById(R.id.tvProductTitleDefault);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(RvItemSetGet data);
    }
}
