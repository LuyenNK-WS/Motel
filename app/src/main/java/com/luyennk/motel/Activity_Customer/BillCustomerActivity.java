package com.luyennk.motel.Activity_Customer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.Adapter_Customer.AdapterBill;
import com.luyennk.motel.DTOs.Bill;
import com.luyennk.motel.DTOs.Contract;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

public class BillCustomerActivity extends Activity implements View.OnClickListener {
    private static final String TAG="BillCustomerActivity";

    private ImageView btnBack;

    private RecyclerView rvListBillCustomer;
    private AdapterBill adapterBillCustomer;

    private List<Bill> bills=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_customer);
        initView();
        getData();
    }

    private void initView(){
        btnBack=findViewById(R.id.btnBack);

        rvListBillCustomer=findViewById(R.id.rvBill);

        btnBack.setOnClickListener(this);

        LinearLayoutManager llm =new LinearLayoutManager(this);
        rvListBillCustomer.setLayoutManager(llm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent=new Intent(this, ButtomNavigationHomeCustomer.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void getDataBill(final String idRoom){
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("Bill");

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    if (idRoom.equals(item.getValue(Bill.class).getIdRoom())) {
                        Bill bill = item.getValue(Bill.class);
                        bills.add(bill);
                    }
                }
                adapterBillCustomer=new AdapterBill(bills,BillCustomerActivity.this);
                rvListBillCustomer.setAdapter(adapterBillCustomer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getData(){
        DatabaseReference mData=FirebaseDatabase.getInstance().getReference().child("Contract");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    getDataBill(item.getValue(Contract.class).getIdRoom());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
