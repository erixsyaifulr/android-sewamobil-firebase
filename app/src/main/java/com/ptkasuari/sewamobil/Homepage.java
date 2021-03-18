package com.ptkasuari.sewamobil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Homepage extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        setTitle("");
        toolbar = (Toolbar)findViewById(R.id.toolbarhome);
        setSupportActionBar(toolbar);
        if(toolbar!=null) {
            setSupportActionBar(toolbar);
        }

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    public void logout(View view) {
        auth.signOut();
        startActivity(new Intent(Homepage.this, MainActivity.class));
        finish();
    }
}