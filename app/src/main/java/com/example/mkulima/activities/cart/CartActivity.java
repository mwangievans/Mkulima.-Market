package com.example.mkulima.activities.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mkulima.Model.ShopItem;
import com.example.mkulima.activities.landing.adapters.ShopItemsAdapter;
import com.example.mkulima.databinding.ActivityCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private List<ShopItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseDatabase.getInstance().getReference("cart/" + FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ShopItem item = ds.getValue(ShopItem.class);
                            if (item != null) {
                                items.add(item);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        ShopItemsAdapter adapter = new ShopItemsAdapter(this, items);
        binding.cartRecycler.setHasFixedSize(true);
        binding.cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRecycler.setAdapter(adapter);
    }
}