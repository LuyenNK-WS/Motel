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
import com.luyennk.motel.Adapter_Customer.AdapterService;
import com.luyennk.motel.DTOs.Service;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

public class ServiceCustomerActivity extends Activity implements View.OnClickListener {
    private ImageView btnBack;

    private AdapterService adapterService;
    private RecyclerView rvServiceCustomer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_customer);
        initView();
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent=new Intent(this,ButtomNavigationHomeCustomer.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void initView(){
        btnBack=findViewById(R.id.btnBack);
        rvServiceCustomer=findViewById(R.id.rvService);

        btnBack.setOnClickListener(this);
        LinearLayoutManager llm =new LinearLayoutManager(this);
        rvServiceCustomer.setLayoutManager(llm);
    }

    private void getData(){
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("Service");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Service> serviceList=new ArrayList<>();
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    serviceList.add(item.getValue(Service.class));
                }

                adapterService=new AdapterService(serviceList,ServiceCustomerActivity.this);
                rvServiceCustomer.setAdapter(adapterService);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
