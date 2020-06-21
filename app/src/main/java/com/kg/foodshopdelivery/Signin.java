package com.kg.foodshopdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kg.foodshopdelivery.Common.Common;
import com.kg.foodshopdelivery.model.User;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;

public class Signin extends AppCompatActivity {
    private EditText editPhone,editPassword;
    private Button btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        editPhone=(MaterialEditText)findViewById(R.id.edtPhone);
        editPassword=(MaterialEditText)findViewById(R.id.edtPassword);
        btnSignIn=(FButton)findViewById(R.id.btnSignIn);

        //user Init
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        final DatabaseReference table_user= database.getReference("users");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog =  new ProgressDialog(Signin.this);
                mDialog.setMessage("Please waiting ....");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //если нет юзера в базе
                        if (dataSnapshot.child(editPhone.getText().toString()).exists()) {
                            //информация юзера
                            mDialog.dismiss();
                            User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(editPassword.getText().toString())) {
                                Intent homeIntent =new Intent(Signin.this,Home.class);
                                Common.currentUser= user;
                                startActivity(homeIntent);
                                finish();
                            } else {
                                Toast.makeText(Signin.this, "Wrong password", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            mDialog.dismiss();
                            Toast.makeText(Signin.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
