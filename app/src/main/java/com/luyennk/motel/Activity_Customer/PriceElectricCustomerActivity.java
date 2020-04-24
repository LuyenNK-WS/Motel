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
import com.luyennk.motel.Adapter_Customer.AdapterPriceElectric;
import com.luyennk.motel.DTOs.UpdatePriceElectric;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

public class PriceElectricCustomerActivity extends Activity implements View.OnClickListener {
    private ImageView btnBack;

    private List<UpdatePriceElectric> updatePriceElectrics=new ArrayList<>();
    private AdapterPriceElectric adapterPriceElectric;
    private RecyclerView rvPriceElectric;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_electric_customer);
        initView();
        getData();
    }

    private void initView(){
        btnBack=findViewById(R.id.btnBack);
        rvPriceElectric=findViewById(R.id.rvPriceElectric);

        btnBack.setOnClickListener(this);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        rvPriceElectric.setLayoutManager(llm);
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

    private void getData(){
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("UpdatePriceElectric");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    updatePriceElectrics.add(item.getValue(UpdatePriceElectric.class));
                }
                adapterPriceElectric= new com.luyennk.motel.Adapter_Customer.AdapterPriceElectric(updatePriceElectrics,PriceElectricCustomerActivity.this);
                rvPriceElectric.setAdapter(adapterPriceElectric);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
