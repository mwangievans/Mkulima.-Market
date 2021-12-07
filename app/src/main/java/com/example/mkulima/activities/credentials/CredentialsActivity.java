package com.example.mkulima.activities.credentials;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mkulima.Model.FragObject;
import com.example.mkulima.activities.credentials.adapters.CredentialsAdapter;
import com.example.mkulima.activities.credentials.fragments.LoginFragment;
import com.example.mkulima.activities.credentials.fragments.SignUpFragment;
import com.example.mkulima.databinding.ActivityCredentialsBinding;

import java.util.ArrayList;
import java.util.List;

public class CredentialsActivity extends AppCompatActivity {
    private ActivityCredentialsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCredentialsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<FragObject> fragObjectList = new ArrayList<>();
        fragObjectList.add(new FragObject("Sign up", new SignUpFragment()));
        fragObjectList.add(new FragObject("Login", new LoginFragment()));
        CredentialsAdapter adapter = new CredentialsAdapter(this.getSupportFragmentManager());
        adapter.setList(fragObjectList);
        binding.credentialsPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.credentialsPager,true);

    }
}

