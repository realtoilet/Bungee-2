package com.example.bungee;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class account_views extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;

    private FragmentStateAdapter swipeAdapter;
    public ViewPager2 vp;
    DBClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_views);

        // Change status bar color if API level is Lollipop or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.darkblue));
        }

        vp = findViewById(R.id.vp2);
        swipeAdapter = new ScreenSlidePageAdapter(this);
        vp.setAdapter(swipeAdapter);
        db = new DBClass(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        }, 1000);
    }

    private class ScreenSlidePageAdapter extends FragmentStateAdapter {
        public ScreenSlidePageAdapter(account_views ac) {
            super(ac);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new fragment_login();
                case 1:
                    return new fragment_signin();
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
