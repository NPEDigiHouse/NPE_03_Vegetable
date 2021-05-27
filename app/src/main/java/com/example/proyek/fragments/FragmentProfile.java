package com.example.proyek.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.proyek.R;
import com.example.proyek.activities.EditProfileActivity;
import com.example.proyek.models.user.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentProfile extends Fragment {
    // class untuk fragment profil

    // variabel
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Toolbar tbProfile;
    private CircleImageView civProfileImg;
    private TextView tvName, tvEmail;
    private String userID;
    private View view;
    private Dialog dialogBox;
    private MaterialButton mBtnSignOutTrue, mBtnSignOutFalse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inisialisasi variabel
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        // sebagai referensi FirebaseDatabase untuk mengambil data user
        reference = FirebaseDatabase.getInstance().getReference("Login").child(mAuth.getUid());

        // toolbar
        tbProfile = view.findViewById(R.id.tbProfile);
        tbProfile.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                // jika mengclick icon settings
                if (id == R.id.profileSettings) {
                    Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                    startActivity(intent);
                } else if (id == R.id.signOut) { // jika mengclick icon signout
                    // menampilkan dialogbox
                    dialogBox = new Dialog(getActivity());
                    dialogBox.setContentView(R.layout.dialog_box_sign_out);
                    dialogBox.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialogBox.setCancelable(true);
                    dialogBox.getWindow().getAttributes().windowAnimations = R.style.DialogBoxAnimation;

                    // inisialisasi button
                    mBtnSignOutFalse = dialogBox.findViewById(R.id.btnSignOutFalse);
                    mBtnSignOutTrue = dialogBox.findViewById(R.id.btnSignOutTrue);

                    //kondisi untuk menghilangkan dialogbox
                    mBtnSignOutFalse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBox.dismiss();
                        }
                    });

                    //kondisi untuk signOut dan menghilangkan dialogbox
                    mBtnSignOutTrue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mAuth.signOut();
                            dialogBox.dismiss();
                        }
                    });

                    dialogBox.show();
                }
                return true;
            }
        });

        //inisialisasi variabel
        civProfileImg = view.findViewById(R.id.civImageProfile);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);

        // membaca data dari FirebaseDatabase berdasarkan child Uid atau data dari user
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user = snapshot.getValue(UserModel.class);

                tvName.setText(user.getUsername());
                tvEmail.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Fail to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }
}
