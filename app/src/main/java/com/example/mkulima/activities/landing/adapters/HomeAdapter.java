package com.example.mkulima.activities.landing.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mkulima.Model.Product;
import com.example.mkulima.R;
import com.example.mkulima.databinding.ProductItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private List<Product> products;
    private OnItemClick listener;

    public interface OnItemClick {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClick listener) {
        this.listener = listener;
    }

    public HomeAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.product_item,
                                parent,
                                false),
                listener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        private ProductItemBinding binding;

        public HomeViewHolder(View view, OnItemClick listener) {
            super(view);
            binding = ProductItemBinding.bind(view);
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(pos);
                }
            });
        }

        public void bind(Product product) {
            binding.productTitle.setText(product.getTitle());
            binding.productDescription.setText(product.getDescription());
            Picasso.get().load(product.getImageUrl()).into(binding.productImage);
        }
    }
}
