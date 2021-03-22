package com.ptkasuari.sewamobil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    FirebaseAuth auth;
    FirebaseUser user;
    Spinner spinner;
    TextView tvharga,tvasal,tvhargaintent;
    CardView booking;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        tvharga = view.findViewById(R.id.tv_harga);
        tvhargaintent = view.findViewById(R.id.tvharga_intent);
        tvasal = view.findViewById(R.id.tvasal);
        spinner = view.findViewById(R.id.spinner);
        booking = view.findViewById(R.id.cardbooking);


        String[] values = new String[]{"--Pilih Tujuan--","Sorong", "Sausapor", "Miyah"};
        String[] harga_string = new String[]{"-","Rp. 2.000.000", "Rp. 1.500.000", "Rp. 2.000.000"};
        String[] harga = new String[]{"2000000", "1500000", "2000000"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Pilih Tujuan");
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tvharga.setText(harga_string[position]);
                    tvhargaintent.setText(harga[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvharga.getText().toString().equals("-")){
                    Toast.makeText(getActivity(),"Anda belum pilih tujuan !",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), Booking.class);
                    intent.putExtra("asal",tvasal.getText().toString());
                    intent.putExtra("harga",tvharga.getText().toString());
                    intent.putExtra("hargareal",tvhargaintent.getText().toString());
                    intent.putExtra("tujuan",spinner.getSelectedItem().toString());
                    startActivity(intent);
                }

            }
        });


        return view;
    }
}