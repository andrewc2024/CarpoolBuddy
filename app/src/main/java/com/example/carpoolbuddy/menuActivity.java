package com.example.carpoolbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.carpoolbuddy.Classes.User;
import com.example.carpoolbuddy.Classes.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;

public class menuActivity extends AppCompatActivity {
    private Button joinFriend;
    private Button addCar;
    private Button joinCar;
    private Button myRides;
    private Button signOut;
    private TextView myId;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        joinFriend = (Button) this.findViewById(R.id.friend);
        addCar = (Button) this.findViewById(R.id.addCar);
        joinCar = (Button) this.findViewById(R.id.joinRide);
        myRides = (Button) this.findViewById(R.id.rides);
        signOut = (Button) this.findViewById(R.id.signOut);
        db = FirebaseFirestore.getInstance();
        myId = this.findViewById(R.id.myId);
        mAuth = FirebaseAuth.getInstance();
        db.collection("users").document(mAuth.getUid()).get();
        db.collection("users").document(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                User x = snapshot.toObject(User.class);
                String mileage = String.valueOf(x.getMileage());
                myId.setText("My ID: "+ mAuth.getUid() + "\r\n" + "Mileage: " + mileage);


            }
        });


        addCar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(menuActivity.this, AddVehicleActivity.class);
                startActivity(intent);
            }
        });
        joinFriend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(menuActivity.this, friend.class);
                startActivity(intent);

            }
        });
        joinCar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(menuActivity.this, VehiclesInfoActivity.class);
                startActivity(intent);

            }
        });
        myRides.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(menuActivity.this, myRides.class);
                startActivity(intent);

            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(menuActivity.this,MainActivity.class);
                startActivity(i);

            }
        });




    }
}