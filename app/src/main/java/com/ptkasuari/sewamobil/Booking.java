package com.ptkasuari.sewamobil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Booking extends AppCompatActivity {

    Toolbar toolbar;
    EditText etasal,etnama,ettujuan,etharga,ettanggal;
    FirebaseAuth auth;
    FirebaseUser user;
    CardView booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        etasal=findViewById(R.id.etasal_booking);
        etnama=findViewById(R.id.etnama_booking);
        ettujuan=findViewById(R.id.ettujuan_booking);
        ettanggal=findViewById(R.id.ettanggal_booking);
        etharga=findViewById(R.id.etharga_booking);
        booking=findViewById(R.id.cardbooking_detail);

        etasal.setText(getIntent().getStringExtra("asal"));
        ettujuan.setText(getIntent().getStringExtra("tujuan"));
        etharga.setText(getIntent().getStringExtra("harga"));

        toolbar = findViewById(R.id.toolbar_booking);
        setTitle("");
        setTitleColor(R.color.white);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarText = findViewById(R.id.tvtoolbar);
        if(toolbarText!=null && toolbar!=null) {
            setSupportActionBar(toolbar);
        }

        setDateTimeField();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("User").child(encodeUserEmail(encodeUserEmail(user.getEmail()))).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nama = dataSnapshot.child("nama").getValue(String.class);
                etnama.setText(nama);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ettanggal.getText().toString().equals("")){
                    Toast.makeText(Booking.this,"Anda belum pilih tanggal !",Toast.LENGTH_SHORT).show();
                }
                else{
                    String kode=getRandomString(7);
                    final String waktu = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    DatabaseReference userref = FirebaseDatabase.getInstance().getReference("Booking");
                    PaketBooking pktall = new PaketBooking(encodeUserEmail(encodeUserEmail(user.getEmail())),
                            kode, etnama.getText().toString(), etasal.getText().toString(),ettujuan.getText().toString(),
                            ettanggal.getText().toString(),etharga.getText().toString(),waktu,"Booked");
                    userref.child(encodeUserEmail(encodeUserEmail(user.getEmail()))).child(kode).setValue(pktall);

                    showalert();
                }


            }
        });


    }

    AlertDialog alertDialog;
    void showalert(){
        alertDialog=new AlertDialog.Builder(Booking.this)
                .setMessage("Booking Sukses !\nSilahkan untuk melihat kode booking anda di halaman History")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ettanggal.setText("");

                    }
                })
                .show();
    }


    private static final String ALLOWED_CHARACTERS ="0123456789QWERTYUIOPASDFGHJKLZXCVBNM";

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    private DatePickerDialog dpTanggal;
    String sTanggal;
    Calendar newCalendar = Calendar.getInstance();

    private void setDateTimeField() {
        ettanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpTanggal.show();
            }
        });

        dpTanggal = new DatePickerDialog(Booking.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei",
                        "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
                sTanggal = dayOfMonth + " " + bulan[monthOfYear] + " " + year;
                ettanggal.setText(sTanggal);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        Calendar a = Calendar.getInstance();
        dpTanggal.getDatePicker().setMinDate(a.getTimeInMillis());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}