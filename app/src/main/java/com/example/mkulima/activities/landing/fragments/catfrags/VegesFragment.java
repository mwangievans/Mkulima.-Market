package com.example.mkulima.activities.landing.fragments.catfrags;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.mkulima.Model.Product;
import com.example.mkulima.Model.User;
import com.example.mkulima.R;
import com.example.mkulima.activities.credentials.CredentialsActivity;
import com.example.mkulima.activities.detail.DetailActivity;
import com.example.mkulima.activities.landing.adapters.ProductsAdapter;
import com.example.mkulima.activities.post.PostActivity;
import com.example.mkulima.databinding.FragmentVegesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VegesFragment extends Fragment {
    private FragmentVegesBinding binding;
    List<Product> veges = new ArrayList<>();
    List<Product> searchList = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String role;

    private String mParam1;
    private String mParam2;

    public VegesFragment() {
        // Required empty public constructor
    }

    public static VegesFragment newInstance(String param1, String param2) {
        VegesFragment fragment = new VegesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getVeges();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVegesBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProductsAdapter adapter = new ProductsAdapter(requireContext(),veges);
        binding.vegetableRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> startActivity(new Intent(requireContext(), DetailActivity.class).putExtra("product_extra",veges.get(position))));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.category_menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                for (Product product:veges){
                    if (product.getTitle().contains(newText) || product.getDescription().contains(newText) || product.getPrice().contains(newText)){
                        searchList.add(product);
                        ProductsAdapter search = new ProductsAdapter(requireContext(),searchList);
                        binding.vegetableRecycler.setAdapter(search);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sell:checkIfSeller();
            if (role=="Seller")
                startActivity(new Intent(requireContext(), PostActivity.class));
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(requireContext(), CredentialsActivity.class));
                requireActivity().finish();
        }
        return true;
    }

    private void getVeges() {
        FirebaseDatabase.getInstance().getReference("products/veges")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Product product = ds.getValue(Product.class);
                            if (product != null) {
                                veges.add(product);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void checkIfSeller(){
        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    if (user!=null){
                        if (user.getUid().equals(FirebaseAuth.getInstance().getUid())){
                            role = user.getRole();
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