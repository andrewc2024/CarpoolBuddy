package com.example.carpoolbuddy;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.carpoolbuddy.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Button signIn;
    private Button signUp;
    private EditText email;
    private EditText name;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Spinner userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn = (Button) this.findViewById(R.id.signIn);
        email = (EditText)  this.findViewById(R.id.Email);
        name = (EditText)  this.findViewById(R.id.Name);
        userType = (Spinner) this.findViewById(R.id.userType);
        signUp = (Button) this.findViewById(R.id.signUp);
        password = (EditText) this.findViewById(R.id.password);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.userTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
//        double distance=startPoint.distanceTo(endPoint);


        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                email.getText().clear();
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                name.getText().clear();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String userName = name.getText().toString();
                String userEmail = email.getText().toString();
                String userTypeData = userType.getSelectedItem().toString();
                String userPassword = password.getText().toString();
                if(userEmail.equals("") && userName.equals("")&&userTypeData.equals("")&&userPassword.equals("")){
                    Context context = getApplicationContext();
                    CharSequence text = "please fill in all required parameters";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else {
                    if (!userEmail.contains("cis.edu.hk")) {
                        Context context = getApplicationContext();
                        CharSequence text = "email does not belong to our school organization";
                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                        toast.show();
                    }else{
                        try {
                            mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Sign In", "Successfully signed in");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent intent = new Intent(MainActivity.this, addLocation.class);
                                        startActivity(intent);
                                    } else {
                                        Log.w("Sign in", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Sign in failed:", Toast.LENGTH_LONG).show();
                                        updateUI(null);
                                    }
                                }
                            });
                        }catch (Exception exception){
                            Context context = getApplicationContext();
                            CharSequence text = "user with this email already exists";
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }



                    }
                }

                }

        });
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString();
                String userEmail = email.getText().toString();
                String userTypeData = userType.getSelectedItem().toString();
                String userPassword = password.getText().toString();
                User x = new User(userTypeData, userName, userEmail, userPassword, 0, 0, 0);
                if (!userEmail.contains("cis.edu.hk") && userEmail != null) {
                    Context context = getApplicationContext();
                    CharSequence text = "email does not belong to our school organization";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    try {
                        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("SIGN UP", "createUserWithEmail:success");
                                    db.collection("users").document(mAuth.getUid()).set(x);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    Log.w("Sign up", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
                    } catch (Exception exception) {
                        Context context = getApplicationContext();
                        CharSequence text = "sign up failed";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                }
            }
        });



}
    public void updateUI(FirebaseUser currentUser){
        if(currentUser != null){
            String userName = name.getText().toString();
            String userEmail = email.getText().toString();
            String userTypeData = userType.getSelectedItem().toString();
            String userPassword = password.getText().toString();
            User x = new User(userTypeData,userName,userEmail,userPassword,0,0,0);
            db.collection("users").document(mAuth.getUid()).set(x);
            Intent intent = new Intent(this, addLocation.class);
            startActivity(intent);
        }

    }

}




