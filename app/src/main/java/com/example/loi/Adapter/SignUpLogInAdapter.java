package com.example.loi.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.loi.Fragment.LogInFragment;
import com.example.loi.Fragment.SignUpFragment;

public class SignUpLogInAdapter extends FragmentStateAdapter {
    public SignUpLogInAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            return SignUpFragment.newInstance();
        }
        return new LogInFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
