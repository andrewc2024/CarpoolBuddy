package com.example.carpoolbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.carpoolbuddy.Classes.User;
import com.example.carpoolbuddy.Classes.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class friend extends AppCompatActivity {

    private Button search;
    private EditText UID;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        search = this.findViewById(R.id.search);
        UID = (EditText) this.findViewById(R.id.friendID);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        back = this.findViewById(R.id.back2);
        String friendName = UID.getText().toString();
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(friend.this, displayFriend.class);
                i.putExtra("cleanName",friendName);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(friend.this, menuActivity.class);
                startActivity(intent);

            }
        });




    }
}