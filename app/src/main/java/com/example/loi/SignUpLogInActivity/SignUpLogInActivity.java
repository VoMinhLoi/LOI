package com.example.loi.SignUpLogInActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.loi.Adapter.SignUpLogInAdapter;
import com.example.loi.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SignUpLogInActivity extends AppCompatActivity {
    private SignUpLogInAdapter mAdapter;
    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;
    private int[] mTabTitbles = new int[]{R.string.tab_signup_title, R.string.tab_signin_title};
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);
        mAdapter = new SignUpLogInAdapter(this);
        mViewPager2 = findViewById(R.id.viewPager2);
        mViewPager2.setAdapter(mAdapter);
        new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(SignUpLogInActivity.this.getResources().getString(mTabTitbles[position]));
            }
        });
    }
}