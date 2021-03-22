package com.ptkasuari.sewamobil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog PD;
    private FirebaseAuth auth;
    EditText etemail,etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(false);
        PD.setCanceledOnTouchOutside(false);

        auth = FirebaseAuth.getInstance();

        etemail=findViewById(R.id.etlogin_email);
        etpassword=findViewById(R.id.etlogin_password);

        Button btnregistrasi = findViewById(R.id.btnRegLogin);
        btnregistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });

        Button btnlogin = findViewById(R.id.btnlogin1);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etemail.getText().toString().trim().toLowerCase();
                String password = etpassword.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        PD.show();
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                                            alertDialog.setTitle("Kesalahan");
                                            alertDialog.setMessage("Email Belum Terdaftar/Password salah !");
                                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                        }
                                                    });
                                            alertDialog.show();
                                        } else {
                                                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("User");
                                                            dR.child(encodeUserEmail(encodeUserEmail(email))).child("status").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    String status = (String)dataSnapshot.getValue();

                                                                    Intent intent = new Intent(MainActivity.this, Homepage.class);
                                                                    if(status.equals("admin")) {
                                                                        intent.putExtra("status","admin");
                                                                    } else {
                                                                        intent.putExtra("status","user");
                                                                    }
                                                                    startActivity(intent);
                                                                    finish();
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });

                                        }

                                    }
                                });
                        PD.dismiss();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                "Isi Email Yang Benar!", Toast.LENGTH_LONG)
                                .show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Lengkapi Email/Pasword!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });
    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    @Override
    protected void onResume() {
        if (auth.getCurrentUser() != null) {
            FirebaseUser acct = FirebaseAuth.getInstance().getCurrentUser();
            final String email=acct.getEmail();
                        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("User");
                        dR.child(encodeUserEmail(email)).child("status").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                String status = (String)dataSnapshot.getValue();
                                Intent intent = new Intent(MainActivity.this, Homepage.class);
                                if(status.equals("admin")) {
                                    intent.putExtra("status","admin");
                                } else {
                                    intent.putExtra("status","user");
                                }
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
        }
        super.onResume();
    }
}