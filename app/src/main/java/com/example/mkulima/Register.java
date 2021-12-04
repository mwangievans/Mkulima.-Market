package com.example.mkulima;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity
{
    private Button RegisterButton;
    private EditText InputFirstName, InputLastName, InputEmail, InputNewPassword, InputConfirmPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterButton = (Button) findViewById(R.id.register_btn);
        InputFirstName = (EditText) findViewById(R.id.register_firstName);
        InputLastName = (EditText) findViewById(R.id.register_lastName);
        InputEmail = (EditText) findViewById(R.id.register_email);
        InputNewPassword = (EditText) findViewById(R.id.register_newPassword);
        InputConfirmPassword = (EditText) findViewById(R.id.register_password);
        loadingBar = new ProgressDialog(this);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
                

            }
        });
    }

    private void CreateAccount() 
    {
        String firstName = InputFirstName.getText().toString();
        String lastName = InputLastName.getText().toString();
        String email = InputEmail.getText().toString();
        String newPassword = InputNewPassword.getText().toString();
        String password = InputConfirmPassword.getText().toString();
        
        if (TextUtils.isEmpty(firstName)) 
        {
            Toast.makeText(this, "Please write your first name...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(lastName))
        {
            Toast.makeText(this, "Please write your last name...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your first email...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(newPassword))
        {
            Toast.makeText(this, "Please create a new password...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please confirm your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
          loadingBar.setTitle("Create Account");
          loadingBar.setMessage("Please wait...");
          loadingBar.setCanceledOnTouchOutside(false);
          loadingBar.show();
          
                ValidateEmail(firstName, lastName, email, newPassword, password);
        }
        }

    private void ValidateEmail(String firstName, String lastName, String email, String newPassword, String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (!(snapshot.child("Users").child(email).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("firstName", firstName);
                    userdataMap.put("lastName", lastName);
                    userdataMap.put("email", email);
                    userdataMap.put("newPassword", newPassword);
                    userdataMap.put("password", password);

                    RootRef.child("Users").child(email).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                               if (task.isSuccessful())
                               {
                                   Toast.makeText(Register.this,"Congratulations your account has been created. ", Toast.LENGTH_SHORT).show();
                                   loadingBar.dismiss();

                                   Intent intent = new Intent(Register.this, Login.class);
                                   startActivity(intent);
                               }
                               else
                               {
                                   loadingBar.dismiss();
                                   Toast.makeText(Register.this,"Network Error: Please try again... ", Toast.LENGTH_SHORT).show();
                               }


                            }
                        });

                }
                else
                {
                    Toast.makeText(Register.this,"This " + email + " already exists ", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Register.this,"Please try again using another email ", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

