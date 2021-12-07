package com.example.mkulima.activities.landing.fragments.catfrags;

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
import com.example.mkulima.activities.landing.adapters.ProductsAdapter;
import com.example.mkulima.databinding.FragmentMeatBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MeatFragment extends Fragment {
    private FragmentMeatBinding binding;
    List<Product> meat = new ArrayList<>();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MeatFragment() {
        // Required empty public constructor
    }

    public static MeatFragment newInstance(String param1, String param2) {
        MeatFragment fragment = new MeatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getMeat();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMeatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProductsAdapter adapter = new ProductsAdapter(requireContext(),meat);
        binding.animalProductsRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> startActivity(new Intent(requireContext(), DetailActivity.class).putExtra("product_extra",meat.get(position))));
    }

    private void getMeat() {
        FirebaseDatabase.getInstance().getReference("products/animal_products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Product product = ds.getValue(Product.class);
                            if (product != null) {
                                meat.add(product);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}