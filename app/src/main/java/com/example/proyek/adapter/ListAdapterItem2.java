package com.example.proyek.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyek.R;
import com.example.proyek.settergetter.RvItemSetGet2;

import java.util.ArrayList;

public class ListAdapterItem2 extends RecyclerView.Adapter<ListAdapterItem2.ListViewHolder>{
    private ArrayList<RvItemSetGet2> listItem;
    public ListAdapterItem2(ArrayList<RvItemSetGet2> list){
        this.listItem = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design, parent, false);
        return  new ListAdapterItem2.ListViewHolder(view);
    }

    private ListAdapterItem2.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(ListAdapterItem2.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        RvItemSetGet2 rvItemSetGet = listItem.get(position);
        holder.tvName.setText(rvItemSetGet.getName());
        holder.imgPhoto.setImageResource(rvItemSetGet.getPhoto());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listItem.get(holder.getAdapterPosition()));
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
            imgPhoto = itemView.findViewById(R.id.ivProductImg);
            tvName = itemView.findViewById(R.id.tvProductTitle);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(RvItemSetGet2 data);
    }
}
