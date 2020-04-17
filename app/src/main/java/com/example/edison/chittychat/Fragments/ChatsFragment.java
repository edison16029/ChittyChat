package com.example.edison.chittychat.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edison.chittychat.Adapter.UserAdapter;
import com.example.edison.chittychat.Model.Chat;
import com.example.edison.chittychat.Model.User;
import com.example.edison.chittychat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment {


    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUser;


    String TAG = "TagChatsFragment";
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    private List<String> usersList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chats, container, false);


        recyclerView =view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat= snapshot.getValue(Chat.class);

                    if(chat.getSender().equals(firebaseUser.getUid())){
                        if(!usersList.contains(chat.getReceiver())) {
//                            Log.d(TAG, "onDataChange: " + "Adding : " + chat.getReceiver() + "for " + chat.getMessage() );
                            usersList.add(chat.getReceiver());
                        }
                    }
                    if(chat.getReceiver().equals(firebaseUser.getUid())) {
                        if (!usersList.contains(chat.getSender())) {
//                            Log.d(TAG, "onDataChange: " + "Adding : " + chat.getSender() + "for " + chat.getMessage());
                            usersList.add(chat.getSender());
                        }
                    }
                }


//                Log.d(TAG, "onDataChange: User List Size= " + usersList.size());
                readChats(usersList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }


    private void readChats(final List<String> usersList){

        mUser = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    if(usersList.contains(user.getId())){
                        mUser.add(user);
                    }
                }

                userAdapter = new UserAdapter(getContext(),mUser);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
