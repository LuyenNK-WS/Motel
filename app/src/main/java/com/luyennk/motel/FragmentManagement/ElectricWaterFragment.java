package com.luyennk.motel.FragmentManagement;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.Adapter_Management.AdapterUpdateNumberElectricWater;
import com.luyennk.motel.DTOs.Bill;
import com.luyennk.motel.DTOs.BillDetail;
import com.luyennk.motel.DTOs.Contract;
import com.luyennk.motel.DTOs.ElectricWater;
import com.luyennk.motel.DTOs.Room;
import com.luyennk.motel.DTOs.Service;
import com.luyennk.motel.DTOs.UpdatePriceElectric;
import com.luyennk.motel.DTOs.UpdatePriceWater;
import com.luyennk.motel.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ElectricWaterFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ElectricWaterFragment";
    private TextView btnCreateBill;
    private AdapterUpdateNumberElectricWater adapterUpdateNumberElectricWater;
    private RecyclerView rvUpdateNumberElectricWater;
    final List<Room> rooms = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_electric_water, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView();
        getData();
    }

    private void initView() {
        rvUpdateNumberElectricWater = getView().findViewById(R.id.rvElectricWater);
        btnCreateBill = getView().findViewById(R.id.btnCreateBill);

        btnCreateBill.setOnClickListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvUpdateNumberElectricWater.setLayoutManager(llm);
    }

    private void getData() {
        DatabaseReference all = FirebaseDatabase.getInstance().getReference().child("Room");

        all.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    rooms.add(item.getValue(Room.class));
                    Log.d(TAG, "idRoom:" + item.getValue(Room.class).getIdRoom());
                }
                adapterUpdateNumberElectricWater = new AdapterUpdateNumberElectricWater(rooms, getActivity());
                rvUpdateNumberElectricWater.setAdapter(adapterUpdateNumberElectricWater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateBill:
                addDataInElectricWater();
                break;
        }
    }

    private void addDataInElectricWater() {
        //create ElectricWater
        String dateFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateFormat = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        }

        final String date = dateFormat.substring(0, 4) + "-" + dateFormat.substring(4, 6) + "-" + dateFormat.substring(6);


        List<ElectricWater> electricWaters = adapterUpdateNumberElectricWater.electricWaterList;
        int i = 0;
        try {
            for (final ElectricWater item : electricWaters) {
                //lấy dữ liệu ElectricWater tháng trước

                DatabaseReference mDataElectricWaterLast=FirebaseDatabase.getInstance().getReference().child("ElectricWater").child(rooms.get(i).getIdRoom());

                mDataElectricWaterLast.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int i=0;
                        int numberElectricLast=0;
                        int numberWaterLast=0;
                        for (DataSnapshot item: dataSnapshot.getChildren()){
                            Log.d(TAG,"Electric water : "+ item.getValue(ElectricWater.class).getNumberElectric());
                            numberElectricLast=Integer.parseInt(item.getValue(ElectricWater.class).getNumberElectric());
                            numberWaterLast=Integer.parseInt(item.getValue(ElectricWater.class).getNumberWater());

                            Log.d(TAG,"test Number Electric: "+numberElectricLast);
                        }

                        DatabaseReference mData = FirebaseDatabase.getInstance().getReference("ElectricWater").child(rooms.get(i).getIdRoom());

                        //create Bill
                        createBill(rooms.get(i).getIdRoom(), item, numberElectricLast,numberWaterLast);

                        //create ElectricWater
                        mData.child(date).setValue(item);
                        i++;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
            Toast.makeText(getContext(), "Tạo hóa đơn thành công", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private void createBill(final String idRoom, final ElectricWater electricWater, final int electricLast, final int waterLast) {
        String dateFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateFormat = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        }

        final String date = dateFormat.substring(6) + "/" + dateFormat.substring(4, 6) + "/" + dateFormat.substring(0, 4);
        final String year = dateFormat.substring(0, 4);
        final String month = dateFormat.substring(4, 6);
        Log.d(TAG, date);

        DatabaseReference mDataBill = FirebaseDatabase.getInstance().getReference().child("Bill");
        try {
            //create bill
            mDataBill.child(idRoom + dateFormat).setValue(new Bill(idRoom + dateFormat, idRoom, date));

            // lấy dữ liệu từ Contract
            DatabaseReference mDataContract = FirebaseDatabase.getInstance().getReference().child("Contract");

            final String finalDateFormat = dateFormat;
            mDataContract.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Double total = 0.0;
                    String priceRoom = "0";
                    // lấy dữ giá phòng trong hợp đồng
                    List<Service> serviceList = new ArrayList<>();
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        if (item.getValue(Contract.class).getIdRoom().equals(idRoom)) {
                            serviceList = item.getValue(Contract.class).getServices();
                            priceRoom=item.getValue(Contract.class).getPriceRoom();
                        }
                    }

                    total+=Double.parseDouble(priceRoom);

                    // lấy giá service
                    for (Service itemService : serviceList) {
                        total += Double.parseDouble(itemService.getPriceService());
                    }

                    // tính tiền điện nước
                    int electricityConsumption=Integer.parseInt(electricWater.getNumberElectric()) - electricLast;
                    int waterConsumption=Integer.parseInt(electricWater.getNumberWater()) - waterLast;

                    Log.d(TAG,"waterConsumption: "+waterConsumption);

                    getPriceElectric(electricityConsumption);
                    getPriceWater(waterConsumption);

                    int priceElectric=getDataSharedPreferences("PriceElectric");
                    int priWater=getDataSharedPreferences("PriceWater");

                    total+=electricityConsumption*priceElectric+waterConsumption*priWater;

                    //create Bill Detail
                    DatabaseReference mDataDetailBill = FirebaseDatabase.getInstance().getReference().child("BillDetail");
                    mDataDetailBill.child(year +"/"+month+"/"+idRoom + finalDateFormat).setValue(new BillDetail(idRoom + finalDateFormat,
                            serviceList,electricLast,waterLast, Integer.parseInt(electricWater.getNumberElectric()),
                            Integer.parseInt(electricWater.getNumberWater()), total, total, 0.0));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private int getPriceElectric(final int number){
        DatabaseReference mDataElectric=FirebaseDatabase.getInstance().getReference().child("UpdatePriceElectric");
        mDataElectric.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int priceElectric=0;
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    if (number>Integer.parseInt(item.getValue(UpdatePriceElectric.class).getTopLimit()) && number<Integer.parseInt(item.getValue(UpdatePriceElectric.class).getLowerLimitl())){
                        priceElectric =Integer.parseInt(item.getValue(UpdatePriceElectric.class).getPriceElectric());
                        Log.d(TAG,"Test giá điện: "+priceElectric);
                    }
                }
                createSharedPreferences(priceElectric,"PriceElectric");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return 1;
    }

    private void getPriceWater(final int number){
        DatabaseReference mDataElectric=FirebaseDatabase.getInstance().getReference().child("UpdatePriceWater");
        mDataElectric.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int priceWater=0;
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    if (number>Integer.parseInt(item.getValue(UpdatePriceWater.class).getTopLimit()) && number<Integer.parseInt(item.getValue(UpdatePriceWater.class).getLowerLimitl())){
                        priceWater =Integer.parseInt(item.getValue(UpdatePriceWater.class).getPriceWater());
                        Log.d(TAG,"Test giá nước: "+priceWater);
                    }

                    Log.d(TAG,"Test giá nước: "+number);
                }
                createSharedPreferences(priceWater,"PriceWater");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createSharedPreferences(int price,String namePrice) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(namePrice, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("price", price);
        editor.commit();
    }

    private int getDataSharedPreferences(String namePreferences){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(namePreferences, Context.MODE_PRIVATE);
        int price=sharedPreferences.getInt("price",0);
        return price;
    }
}