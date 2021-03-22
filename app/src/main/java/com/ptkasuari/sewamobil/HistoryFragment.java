package com.ptkasuari.sewamobil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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

    Toolbar toolbar;
    List<PaketBooking> pktbooking;
    ListView listhistory;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        toolbar = view.findViewById(R.id.toolbar_booking);
        listhistory = view.findViewById(R.id.list_history);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        getActivity().setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView toolbarText = view.findViewById(R.id.tvtoolbar_history);
        if(toolbarText!=null && toolbar!=null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        cetaklist(getActivity(),listhistory);

        return view;
    }

    static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }


    public void cetaklist(final Activity context, final ListView listView) {
        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Ambil Data ...");
        mProgressDialog.show();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Booking").
                child(encodeUserEmail(encodeUserEmail(user.getEmail())));
        pktbooking=new ArrayList<>();
        mRef.orderByChild("waktu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pktbooking.clear();
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    PaketBooking pkt=childSnapShot.getValue(PaketBooking.class);

                        pktbooking.add(pkt);

                }
                listHistory listadapter = new listHistory(context, pktbooking);
                listView.setAdapter(listadapter);
                mProgressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public class listHistory extends ArrayAdapter<PaketBooking> {
        private Activity context;
        List<PaketBooking> pkt;

        public listHistory(@NonNull Activity context, List<PaketBooking> pkt) {
            super(context, R.layout.list_history, pkt);
            this.context = context;
            this.pkt = pkt;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            final View listViewItem = inflater.inflate(R.layout.list_history, null, true);

            TextView tvkode = (TextView) listViewItem.findViewById(R.id.tvlist_kode);
            TextView tvasal = (TextView) listViewItem.findViewById(R.id.tvlist_asal);
            TextView tvtujuan = (TextView) listViewItem.findViewById(R.id.tvlist_tujuan);
            TextView tvtanggal = (TextView) listViewItem.findViewById(R.id.tvlist_tanggal);
            Button btnstatus = listViewItem.findViewById(R.id.btnlist_status);

            final PaketBooking pk = pkt.get(position);
            tvkode.setText("KODE BOOKING "+pk.getKode());
            tvasal.setText(pk.getAsal());
            tvtujuan.setText(pk.getTujuan());
            tvtanggal.setText("Tanggal berangkat : "+pk.getTanggal_berangkat());
            btnstatus.setText(pk.getStatus());
            if(pk.getStatus().equals("Booked")){
                btnstatus.setBackgroundResource(R.drawable.login_btn_bg);
            }



            return listViewItem;
        }

    }
}