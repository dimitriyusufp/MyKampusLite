package com.example.mykampuslite.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mykampuslite.adapter.ViewPagerAdapter;
import com.example.mykampuslite.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Mahasiswa");
            } else {
                tab.setText("Dosen");
            }
        }).attach();
    }
}