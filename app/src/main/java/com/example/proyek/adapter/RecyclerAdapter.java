package com.example.proyek.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyek.R;
import com.example.proyek.models.product.ProductModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class RecyclerAdapter extends FirebaseRecyclerAdapter<ProductModel, RecyclerAdapter.myViewHolder> {

    public RecyclerAdapter(@NonNull FirebaseRecyclerOptions<ProductModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, final int position, @NonNull ProductModel sayur) {

        myViewHolder.tvName.setText(sayur.getName());
        myViewHolder.tvHarga.setText(sayur.getPrice());
        Glide.with(myViewHolder.img.getContext()).load(sayur.getUrl()).into(myViewHolder.img);

        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myViewHolder.img.getContext());
                builder.setTitle("Delete");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Data").child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
        myViewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(myViewHolder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1100)
                        .create();
                View myview = dialogPlus.getHolderView();
                final EditText url = myview.findViewById(R.id.etUrlImage);
                final EditText name = myview.findViewById(R.id.etName);
                final EditText harga = myview.findViewById(R.id.etHarga);
                Button update = myview.findViewById(R.id.btnUpdate);

                url.setText(sayur.getUrl());
                name.setText(sayur.getName());
                harga.setText(sayur.getPrice());
                dialogPlus.show();
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("url", url.getText().toString());
                        map.put("name", name.getText().toString());
                        map.put("harga", harga.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Data").child(getRef(position).getKey()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialogPlus.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogPlus.dismiss();
                            }
                        });
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_list, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView tvName, tvHarga;
        Button btnEdit, btnDelete;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvJudulSayur);
            tvHarga = itemView.findViewById(R.id.tvHargaSayur);
            img = itemView.findViewById(R.id.photoSayur);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

}
