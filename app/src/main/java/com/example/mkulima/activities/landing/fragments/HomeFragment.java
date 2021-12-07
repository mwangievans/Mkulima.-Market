package com.example.mkulima.activities.landing.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mkulima.Model.Product;
import com.example.mkulima.activities.detail.DetailActivity;
import com.example.mkulima.activities.landing.adapters.HomeAdapter;
import com.example.mkulima.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private List<Product> meat = new ArrayList<>();
    private List<Product> veges = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMeat();
        getVeges();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeAdapter meatAdapter = new HomeAdapter(meat);
        binding.meatRecycler.setAdapter(meatAdapter);
        meatAdapter.setOnItemClickListener(position -> startActivity(new Intent(requireContext(), DetailActivity.class).putExtra("product_extra",meat.get(position))));
        HomeAdapter vegesAdapter = new HomeAdapter(veges);
        binding.vegesRecycler.setAdapter(vegesAdapter);
        vegesAdapter.setOnItemClickListener(position -> startActivity(new Intent(requireContext(), DetailActivity.class).putExtra("product_extra",veges.get(position))));
    }

    private void getVeges() {
        String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(System.currentTimeMillis());
        FirebaseDatabase.getInstance().getReference("products/veges")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Product product = ds.getValue(Product.class);
                            if (product != null) {
                                if (product.getDateAdded().equals(today)) {
                                    veges.add(product);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void getMeat() {
        String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(System.currentTimeMillis());
        FirebaseDatabase.getInstance().getReference("products/animal_products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Product product = ds.getValue(Product.class);
                            if (product != null) {
                                if (product.getDateAdded().equals(today)) {
                                    meat.add(product);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}