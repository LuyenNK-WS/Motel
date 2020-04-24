package com.luyennk.motel.FragmentManagement;

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

import com.luyennk.motel.Activity_Management.BillActivity;
import com.luyennk.motel.Activity_Management.ChartActivity;
import com.luyennk.motel.Activity_Management.ContractActivity;
import com.luyennk.motel.Activity_Management.CustomerActivity;
import com.luyennk.motel.Activity_Management.NotificationActivity;
import com.luyennk.motel.Activity_Management.RoomActivity;
import com.luyennk.motel.Activity_Management.ServiceActivity;
import com.luyennk.motel.Activity_Management.UpdatePriceElectricActivity;
import com.luyennk.motel.Activity_Management.UpdatePriceWaterActivity;
import com.luyennk.motel.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private LinearLayout btnBill;
    private LinearLayout btnContract;
    private LinearLayout btnCustomer;
    private LinearLayout btnService;
    private LinearLayout btnNotification;
    private LinearLayout btnRoom;
    private LinearLayout btnElectric;
    private LinearLayout btnWater;

    private ImageView btnMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView(){
        btnBill= getView().findViewById(R.id.btnBill);
        btnContract=getView().findViewById(R.id.btnContract);
        btnCustomer=getView().findViewById(R.id.btnCustomer);
        btnService=getView().findViewById(R.id.btnService);
        btnNotification=getView().findViewById(R.id.btnNotification);
        btnRoom=getView().findViewById(R.id.btnRoom);
        btnElectric=getView().findViewById(R.id.btnPriceElectric);
        btnWater=getView().findViewById(R.id.btnPriceWater);
        btnMenu=getView().findViewById(R.id.btnMenu);

        btnBill.setOnClickListener(this);
        btnContract.setOnClickListener(this);
        btnCustomer.setOnClickListener(this);
        btnService.setOnClickListener(this);
        btnNotification.setOnClickListener(this);
        btnRoom.setOnClickListener(this);
        btnElectric.setOnClickListener(this);
        btnWater.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBill:
                Intent intent=new Intent(getActivity(), BillActivity.class);
                startActivity(intent);
                break;
            case R.id.btnContract:
                Intent intent1=new Intent(getActivity(), ContractActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnCustomer:
                Intent intent2=new Intent(getActivity(), CustomerActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnService:
                Intent intent3=new Intent(getActivity(), ServiceActivity.class);
                startActivity(intent3);
                break;
            case R.id.btnNotification:
                Intent intent4=new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent4);
                break;
            case R.id.btnRoom:
                Intent intent5=new Intent(getActivity(), RoomActivity.class);
                startActivity(intent5);
                break;
            case R.id.btnPriceElectric:
                Intent intent6=new Intent(getActivity(), UpdatePriceElectricActivity.class);
                startActivity(intent6);
                break;
            case R.id.btnPriceWater:
                Intent intent7=new Intent(getActivity(), UpdatePriceWaterActivity.class);
                startActivity(intent7);
                break;
            case R.id.btnMenu:
                Intent intent8=new Intent(getActivity(), ChartActivity.class);
                startActivity(intent8);
                break;
        }
    }
}
