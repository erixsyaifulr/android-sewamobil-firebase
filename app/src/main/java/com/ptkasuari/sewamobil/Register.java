package com.ptkasuari.sewamobil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Register extends AppCompatActivity {

    EditText etnama,etemail,etnohp,etpassword;
    private FirebaseAuth auth;
    private ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        PD = new ProgressDialog(this);
        PD.setMessage("Registrasi...");
        PD.setCancelable(false);
        PD.setCanceledOnTouchOutside(false);

        auth = FirebaseAuth.getInstance();
        etnama=findViewById(R.id.etnama);
        etemail=findViewById(R.id.etemail);
        etnohp=findViewById(R.id.etnohp);
        etpassword=findViewById(R.id.etpassword);

        Button bntlogin = findViewById(R.id.btnLogRegister);
        bntlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button bntregistrasi = findViewById(R.id.btregistrasi);
        bntregistrasi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String nama=etnama.getText().toString().trim();
                        final String nohp=etnohp.getText().toString().trim();
                        final String email=etemail.getText().toString().trim().toLowerCase();
                        final String password=etpassword.getText().toString().trim().toLowerCase();
                        final String waktu = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        if(!nama.equals("")&&!nohp.equals("")&&!email.equals("")&&!password.equals("")){

                                PD.show();
                                auth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                //jika gagal register do something
                                                if (!task.isSuccessful()) {
                                                    Toast.makeText(Register.this,
                                                            "Register gagal karena " + task.getException().getMessage(),
                                                            Toast.LENGTH_LONG).show();
                                                } else {

                                                    DatabaseReference userref = FirebaseDatabase.getInstance().getReference("User");
                                                    PaketUser pktall = new PaketUser(nama, nohp, email, password,"user", waktu);
                                                    userref.child(encodeUserEmail(encodeUserEmail(email))).setValue(pktall);

                                                    etnama.setText("");
                                                    etnohp.setText("");
                                                    etemail.setText("");
                                                    etpassword.setText("");
                                                    auth.signOut();
                                                    showalert();
                                                }
                                                PD.dismiss();
                                            }
                                        });


                        }
                        else {
                            Toast.makeText(Register.this,"Lengkapi data diatas!",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    AlertDialog alertDialog;
    void showalert(){
        alertDialog=new AlertDialog.Builder(Register.this)
                .setMessage("Registrasi berhasil, Silakan kembali ke halaman Login")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }
}