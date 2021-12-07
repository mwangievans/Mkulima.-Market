package com.example.mkulima.activities.landing;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mkulima.R;
import com.example.mkulima.activities.landing.fragments.CategoryFragment;
import com.example.mkulima.activities.landing.fragments.FavoritesFragment;
import com.example.mkulima.activities.landing.fragments.HomeFragment;
import com.example.mkulima.activities.landing.fragments.ProfileFragment;
import com.example.mkulima.databinding.ActivityLandingBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Landing extends AppCompatActivity {
    private ActivityLandingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_container,
                        new HomeFragment(),
                        "Home Frag")
                .commit();
        binding.bottomNav.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_container,
                                new HomeFragment(),
                                "Home Frag")
                        .commit();
            } else if (item.getItemId() == R.id.category) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_container,
                                new CategoryFragment(),
                                "Category Frag")
                        .commit();
            } else if (item.getItemId() == R.id.favorites) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_container,
                                new FavoritesFragment(),
                                "Favourites Frag")
                        .commit();
            } else if (item.getItemId() == R.id.profile) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frag_container,
                                new ProfileFragment(),
                                "Profile Frag")
                        .commit();
            }
            return true;
        });
    }
}