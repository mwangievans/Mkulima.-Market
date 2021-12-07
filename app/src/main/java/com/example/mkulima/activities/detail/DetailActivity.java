package com.example.mkulima.activities.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mkulima.Model.Product;
import com.example.mkulima.activities.cart.CartActivity;
import com.example.mkulima.databinding.ActivityDetailBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    private ActivityDetailBinding binding;
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        Product product = intent.getParcelableExtra("product_extra");
        if (product != null) {
            binding.titleTvDetail.setText(product.getTitle());
            binding.productDescriptionDetail.setText(product.getDescription());
            binding.amountTv.setText("1");
            Picasso.get().load(product.getImageUrl()).into(binding.productImageDetail);
            binding.priceTvDetail.setText(String.format(Locale.getDefault(), "Price: %s", product.getPrice()));
            number = Integer.parseInt(binding.amountTv.getText().toString());
        }
        binding.add.setOnClickListener(v -> addOne());
        binding.reduce.setOnClickListener(v -> removeOne());
        binding.addToCartBtn.setOnClickListener(v -> {
            Map<String,Product > cart = new HashMap<>();
            assert product != null;
            cart.put(product.getTitle(),product);
            FirebaseDatabase.getInstance().getReference("carts/"+ FirebaseAuth.getInstance().getUid()+"/"+System.currentTimeMillis()).setValue(cart)
                    .addOnCompleteListener(task -> {
                        if (task.isComplete() && task.isSuccessful()){
                            Log.i(TAG, "onComplete: product uploaded");
                        }
                    });
        });
        binding.viewCartBtn.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        binding.checkOutBtn.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));

    }

    private void addOne() {
        binding.amountTv.setText(String.valueOf(number += 1));
    }

    private void removeOne() {
        if (number == 1) {
            Snackbar.make(binding.getRoot(), "Cannot go below 1", Snackbar.LENGTH_LONG).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show();
        } else {
            binding.amountTv.setText(number -= 1);
        }
    }
}