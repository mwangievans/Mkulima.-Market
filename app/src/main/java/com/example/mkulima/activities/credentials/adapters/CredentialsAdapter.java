package com.example.mkulima.activities.credentials.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mkulima.Model.FragObject;

import java.util.List;

public class CredentialsAdapter extends FragmentStatePagerAdapter {
    private List<FragObject> fragObjectList;

    public CredentialsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    public void setList(List<FragObject> fragObjectList){
        this.fragObjectList = fragObjectList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragObjectList.get(position).getFragment();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public int getCount() {
        return fragObjectList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragObjectList.get(position).getTitle();
    }
}
