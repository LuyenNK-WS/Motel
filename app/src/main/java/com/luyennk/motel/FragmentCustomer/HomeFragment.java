package com.luyennk.motel.FragmentCustomer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.Activity_Customer.BillCustomerActivity;
import com.luyennk.motel.Activity_Customer.ContractCustomerActivity;
import com.luyennk.motel.Activity_Customer.PriceElectricCustomerActivity;
import com.luyennk.motel.Activity_Customer.PriceWaterCustomerActivity;
import com.luyennk.motel.Activity_Customer.RoomCustomerActivity;
import com.luyennk.motel.Activity_Customer.ServiceCustomerActivity;
import com.luyennk.motel.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private LinearLayout btnBill;
    private LinearLayout btnContract;
    private LinearLayout btnService;
    private LinearLayout btnNotification;
    private LinearLayout btnRoom;
    private LinearLayout btnElectric;
    private LinearLayout btnWater;

    private ImageView imgWarning;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_customer,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView();
        imgWarning.setEnabled(false);
        checkWarning();
    }

    private void initView(){
        btnBill=getView().findViewById(R.id.btnBill);
        btnContract=getView().findViewById(R.id.btnContract);
        btnService=getView().findViewById(R.id.btnService);
        btnNotification=getView().findViewById(R.id.btnNotification);
        btnRoom=getView().findViewById(R.id.btnRoom);
        btnElectric=getView().findViewById(R.id.btnPriceElectric);
        btnWater=getView().findViewById(R.id.btnPriceWater);
        imgWarning=getView().findViewById(R.id.imgWarning);

        btnBill.setOnClickListener(this);
        btnContract.setOnClickListener(this);
        btnService.setOnClickListener(this);
        btnNotification.setOnClickListener(this);
        btnRoom.setOnClickListener(this);
        btnElectric.setOnClickListener(this);
        btnWater.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBill:
                Intent intent=new Intent(getActivity(), BillCustomerActivity.class);
                startActivity(intent);
                break;
            case R.id.btnContract:
                Intent intent1=new Intent(getActivity(), ContractCustomerActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnService:
                Intent intent2=new Intent(getActivity(), ServiceCustomerActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnNotification:
                break;
            case R.id.btnRoom:
                Intent intent3=new Intent(getActivity(), RoomCustomerActivity.class);
                startActivity(intent3);
                break;
            case R.id.btnPriceElectric:
                Intent intent4=new Intent(getActivity(), PriceElectricCustomerActivity.class);
                startActivity(intent4);
                break;
            case R.id.btnPriceWater:
                Intent intent5=new Intent(getActivity(), PriceWaterCustomerActivity.class);
                startActivity(intent5);
                break;
            default:
                break;
        }
    }

    private void checkWarning(){
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("Notification");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imgWarning.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
