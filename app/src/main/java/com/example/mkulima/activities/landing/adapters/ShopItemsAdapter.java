package com.example.mkulima.activities.landing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mkulima.Model.Product;
import com.example.mkulima.Model.ShopItem;
import com.example.mkulima.R;
import com.example.mkulima.databinding.ShoppingItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopItemsAdapter extends RecyclerView.Adapter<ShopItemsAdapter.ShopItemViewHolder> {
    private List<ShopItem> products;
    private OnItemClick listener;

    public interface OnItemClick{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClick listener){
        this.listener = listener;
    }

    public ShopItemsAdapter(Context context, List<ShopItem> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ShopItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShopItemViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.shopping_item,
                        parent,
                        false
                ),
                listener
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ShopItemViewHolder extends RecyclerView.ViewHolder{
        private final ShoppingItemBinding binding;
        public ShopItemViewHolder(View view,OnItemClick listener){
            super(view);
            binding = ShoppingItemBinding.bind(view);
            binding.getRoot().setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos!=RecyclerView.NO_POSITION){
                    listener.onItemClick(pos);
                }
            });
        }
        public void bind(ShopItem product){
            binding.titleTv.setText(product.getTitle());
            binding.descriptionTv.setText(product.getDescription());
            binding.priceTv.setText(product.getPrice());
            Picasso.get().load(product.getImageUrl()).into(binding.shoppingProductItem);
        }
    }
}
