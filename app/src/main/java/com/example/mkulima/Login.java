package com.example.mkulima;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mkulima.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class  Login extends AppCompatActivity {
    private EditText InputEmail, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    private String parentDbname = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        LoginButton = (Button) findViewById(R.id.log_in_btn);
        InputEmail = (EditText) findViewById(R.id.log_in_email);
        InputPassword = (EditText) findViewById(R.id.log_in_password);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                LoginUser();

            }
        });

    }

    private void LoginUser()
    {
        String email = InputEmail.getText().toString();
        String Password = InputPassword.getText().toString();


        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please input your email...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Password))
        {
            Toast.makeText(this, "Please input your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Authenticating");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();



            AllowAccessToAccount(email, Password);
        }


    }

    private void AllowAccessToAccount(String email, String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.child(parentDbname).child(email).exists())
                {
                    Users userData = snapshot.child(parentDbname).child(email).getValue(Users.class);

                    if (userData.getEmail().equals(email))
                    {
                        if (userData.getPassword().equals(password))
                        {
                            Toast.makeText(Login.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(Login.this, Home.class);
                            startActivity(intent);
                        }
                    }

                }
                else
                {
                    Toast.makeText(Login.this, "Account with this " + email + " does not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Login.this, "Please create an account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}