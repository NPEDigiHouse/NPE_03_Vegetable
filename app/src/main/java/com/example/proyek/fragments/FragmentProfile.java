package com.example.proyek.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.proyek.R;

public class FragmentProfile extends Fragment {
//    private Button btnOut;
//    private FirebaseUser user;
//    private DatabaseReference reference;
//    private String userID;
//    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

//        user = FirebaseAuth.getInstance().getCurrentUser();
//        reference = FirebaseDatabase.getInstance().getReference("User");
//        userID = user.getUid();
//        mAuth = FirebaseAuth.getInstance();
//
//        final TextView textViewName = view.findViewById(R.id.tvName);
//        final TextView textViewEmail = view.findViewById(R.id.tvEmail);
//
//        btnOut = view.findViewById(R.id.btnOut);
//        btnOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuth.signOut();
//                Intent a = new Intent(getActivity(), MainActivity.class);
//                a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(a);
//                getActivity().finish();
//            }
//        });
//
//        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User userProfile = snapshot.getValue(User.class);
//                if (userProfile != null){
//                    String fullName = userProfile.fullName;
//                    String email = userProfile.email;
//
//                    textViewEmail.setText(email);
//                    textViewName.setText(fullName);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//                Toast.makeText(getActivity(), "something wrong", Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
