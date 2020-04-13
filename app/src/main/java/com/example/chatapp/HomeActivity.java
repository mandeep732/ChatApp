package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.chatapp.HomeFragments.ChatsFragment;
import com.example.chatapp.HomeFragments.UsersFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.appName);


        auth= FirebaseAuth.getInstance();
        FirebaseUser currUser = auth.getCurrentUser();
        if(currUser!=null)
        {
        }

        TabLayout tabLayout = findViewById(R.id.tabLayout_home);
        ViewPager viewPager = findViewById(R.id.viewPager_home);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChatsFragment(),"Chats");
        adapter.addFragment(new UsersFragment(),"Users");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.camera_home_menu:

            break;
            case R.id.profile_home_menu:
                Intent i = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(i);
            break;
            case R.id.settings_home_menu:
            break;
            case R.id.logout_home_menu:
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Confirm Logout")
                        .setMessage("Are you sure you want to Logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
                                progressDialog.setMessage("Logging out...");
                                progressDialog.show();

                                auth.signOut();
                                Intent i = new Intent(HomeActivity.this,LaunchActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                progressDialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

        }
        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> title;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);

            fragments = new ArrayList<>();
            title= new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        void addFragment(Fragment f1,String t1)
        {
            fragments.add(f1);
            title.add(t1);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }
}
