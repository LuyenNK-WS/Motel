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
import com.luyennk.motel.Adapter_Customer.AdapterPriceWater;
import com.luyennk.motel.DTOs.UpdatePriceWater;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

public class PriceWaterCustomerActivity extends Activity implements View.OnClickListener {
    private ImageView btnBack;

    private AdapterPriceWater adapterPriceWater;
    private RecyclerView rvPriceWater;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_water_customer);
        initView();
        getData();
    }

    private void initView(){
        btnBack=findViewById(R.id.btnBack);
        rvPriceWater=findViewById(R.id.rvUpdatePriceWater);

        btnBack.setOnClickListener(this);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        rvPriceWater.setLayoutManager(llm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent=new Intent(this, ButtonNavigationHomeCustomer.class);
                startActivity(intent);
                break;
        }
    }

    private void getData(){
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("UpdatePriceWater");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UpdatePriceWater> updatePriceWaters=new ArrayList<>();
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    updatePriceWaters.add(item.getValue(UpdatePriceWater.class));
                }

                adapterPriceWater=new AdapterPriceWater(updatePriceWaters,PriceWaterCustomerActivity.this);
                rvPriceWater.setAdapter(adapterPriceWater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
