package com.luyennk.motel.Activity_Management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.Adapter_Management.AdapterCustomer;
import com.luyennk.motel.DTOs.Use;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends Activity implements View.OnClickListener {

    private static final String TAG="CustomerActivity";

    private ImageView btnBack;
    private TextView txtTotalPeople;
    private RecyclerView rvCustomer;
    private List<Use> useList;
    private AdapterCustomer adapterCustomer;

    private int i=0;

    private ArrayList<Use> uses=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        initView();
        getData();
    }

    private void initView(){
        btnBack=findViewById(R.id.btnBack);
        rvCustomer=findViewById(R.id.rvCustomer);
        txtTotalPeople=findViewById(R.id.txtTotalPeople);

        btnBack.setOnClickListener(this);

        LinearLayoutManager llm=new LinearLayoutManager(this);
        rvCustomer.setLayoutManager(llm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent = new Intent(CustomerActivity.this, ButtomNavigationHomeManagement.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void getData(){
        Query all = FirebaseDatabase.getInstance().getReference().child("User").orderByChild("permission").equalTo("0");
        all.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    Use use=item.getValue(Use.class);
                    uses.add(use);
                    i++;
                }

                Log.d(TAG,"số lương khách hàng: "+i);
                txtTotalPeople.setText("Tổng cộng: "+i+" người");
                useList=uses;

                adapterCustomer=new AdapterCustomer(useList,CustomerActivity.this);
                rvCustomer.setAdapter(adapterCustomer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
