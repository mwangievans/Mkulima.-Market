package com.example.mkulima.activities.landing.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mkulima.Model.CategoryFrag;

import java.util.List;

public class CategoryAdapter extends FragmentStatePagerAdapter {
    private List<CategoryFrag> categoryFragList;
    public CategoryAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    public void setList(List<CategoryFrag> categoryFragList){
        this.categoryFragList = categoryFragList;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryFragList.get(position).getTitle();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return categoryFragList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return categoryFragList.size();
    }
}
