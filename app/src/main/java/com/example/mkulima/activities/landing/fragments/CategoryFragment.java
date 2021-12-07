package com.example.mkulima.activities.landing.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mkulima.Model.CategoryFrag;
import com.example.mkulima.activities.landing.adapters.CategoryAdapter;
import com.example.mkulima.activities.landing.fragments.catfrags.MeatFragment;
import com.example.mkulima.activities.landing.fragments.catfrags.VegesFragment;
import com.example.mkulima.databinding.FragmentCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private FragmentCategoryBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<CategoryFrag> frags = new ArrayList<>();
        frags.add(new CategoryFrag("Animal Products",new MeatFragment()));
        frags.add(new CategoryFrag("Vegetables",new VegesFragment()));
        CategoryAdapter adapter = new CategoryAdapter(getChildFragmentManager());
        adapter.setList(frags);
        binding.categoryPager.setAdapter(adapter);
        binding.categoryTab.setupWithViewPager(binding.categoryPager,true);
    }
}