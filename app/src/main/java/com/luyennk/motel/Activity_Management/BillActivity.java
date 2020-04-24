package com.luyennk.motel.Activity_Management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.Adapter_Management.AdapterBill;
import com.luyennk.motel.DTOs.Bill;
import com.luyennk.motel.R;

import java.util.ArrayList;

public class BillActivity extends Activity implements View.OnClickListener {

    private static final String TAG="BillActivity";

    private ImageView btnBack;
    private TextView txtTotal;

    private RecyclerView rvListBill;
    private AdapterBill adapterBill;

    private ArrayList<Bill> bills=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        initView();
        getData();
    }

    private void initView(){
        btnBack=findViewById(R.id.btnBack);
        txtTotal=findViewById(R.id.txtTotal);
        rvListBill=findViewById(R.id.rvBill);

        btnBack.setOnClickListener(this);

        LinearLayoutManager llm=new LinearLayoutManager(this);
        rvListBill.setLayoutManager(llm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent = new Intent(BillActivity.this, ButtomNavigationHomeManagement.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void getData(){
        DatabaseReference all= FirebaseDatabase.getInstance().getReference().child("Bill");
        all.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    Bill bill=item.getValue(Bill.class);
                    bills.add(bill);
                }
                adapterBill=new AdapterBill(bills,BillActivity.this);
                rvListBill.setAdapter(adapterBill);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
