package com.example.proyek.admin;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.proyek.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DialogForm extends DialogFragment {

    String name, harga, urlImage;
    EditText etName, etHarga, etUrlImage;
    Button btnSave;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public DialogForm() {
        this.name = name;
        this.harga = harga;
        this.urlImage = urlImage;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_form, container, false);

        etName = view.findViewById(R.id.etName);
        etHarga = view.findViewById(R.id.etHarga);
        etUrlImage = view.findViewById(R.id.etUrlImage);
        btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String harga = etHarga.getText().toString();
                String url = etUrlImage.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("name", etName.getText().toString());
                map.put("price", etHarga.getText().toString());
                map.put("url", etUrlImage.getText().toString());
                if (name.isEmpty()){
                    etName.setError("tolong isi nama");
                    etName.requestFocus();
                    return;
                }
                if (harga.isEmpty()){
                    etHarga.setError("tolong isi harga");
                    etHarga.requestFocus();
                    return;
                }
                if (url.isEmpty()){
                    etUrlImage.setError("tolong isi url");
                    etUrlImage.requestFocus();
                    return;
                }

                databaseReference.child("Packet").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(view.getContext(), "succes input data", Toast.LENGTH_LONG).show();

                        dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "failure input data", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;
    }
}
