package com.example.bungee;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

public class fragments_requests extends Fragment {
    private FragmentStateAdapter swipeAdapter;
    public ViewPager2 vp;
    public TabLayout tb;
    String email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_requests, container, false);
        vp = v.findViewById(R.id.vp2);
        swipeAdapter = new fragments_requests.ScreenSlidePageAdapter(this);
        vp.setAdapter(swipeAdapter);

        tb = v.findViewById(R.id.tabs);

        tb.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tb.getTabAt(position).select();
            }
        });
        return v;
    }
    private class ScreenSlidePageAdapter extends FragmentStateAdapter {
        public ScreenSlidePageAdapter(fragments_requests ac) {
            super(ac);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new fragment_sellers();
                case 1:
                    return new fragment_riders();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}