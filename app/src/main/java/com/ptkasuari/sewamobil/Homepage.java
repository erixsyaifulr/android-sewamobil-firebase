package com.ptkasuari.sewamobil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Homepage extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    Toolbar toolbar;

    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

//        setTitle("");
//        toolbar = (Toolbar)findViewById(R.id.toolbarhome);
//        setSupportActionBar(toolbar);
//        if(toolbar!=null) {
//            setSupportActionBar(toolbar);
//        }

        bottomNavigation = findViewById(R.id.bottom_nav);
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_list));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_user));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment= null;
                switch (item.getId()){
                    case 1:
                        fragment=new HistoryFragment();
                        break;
                    case 2:
                        fragment=new HomeFragment();
                        break;
                    case 3:
                        fragment=new ProfilFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });

        bottomNavigation.show(2, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(getApplicationContext(), "You cliCKED : "+item.getId(),Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(getApplicationContext(), "You Reselec : "+item.getId(),Toast.LENGTH_SHORT).show();
            }
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }


    public void logout(View view) {
        auth.signOut();
        startActivity(new Intent(Homepage.this, MainActivity.class));
        finish();
    }
}