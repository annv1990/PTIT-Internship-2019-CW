package com.example.intership2019;

import android.os.Bundle;

import android.view.MenuItem;

import com.example.intership2019.Fragment.CurrentFragment;
import com.example.intership2019.Fragment.DesignPatternsFragment;
import com.example.intership2019.Fragment.ForecastFragment;
import com.example.intership2019.Fragment.MovieListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new CurrentFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_current:
                    fragment = new CurrentFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_forecast:
                    fragment = new ForecastFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_movie:
                    fragment = new MovieListFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_test1:
                    fragment = new DesignPatternsFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;

        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

}
