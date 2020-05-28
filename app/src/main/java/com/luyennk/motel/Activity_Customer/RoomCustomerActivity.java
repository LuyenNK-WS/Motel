package com.luyennk.motel.Activity_Customer;

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
import com.luyennk.motel.Adapter_Customer.AdapterRoom;
import com.luyennk.motel.DTOs.Room;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

public class RoomCustomerActivity extends Activity implements View.OnClickListener {
    private ImageView btnBack;
    private TextView txtTotalRoom;

    private AdapterRoom adapterRoom;
    private RecyclerView rvRoom;
    private List<Room> roomList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_customer);
        initView();
        getData();
    }

    private void initView(){
        btnBack=findViewById(R.id.btnBack);
        rvRoom=findViewById(R.id.rvRoom);
        txtTotalRoom=findViewById(R.id.txtTotalRoom);

        btnBack.setOnClickListener(this);

        LinearLayoutManager llm =new LinearLayoutManager(this);
        rvRoom.setLayoutManager(llm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent=new Intent(this, ButtonNavigationHomeCustomer.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void getData(){
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("Room");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            int i=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    roomList.add(item.getValue(Room.class));
                    i++;
                }

                txtTotalRoom.setText("Tổng cộng: "+i+" phòng");
                adapterRoom=new AdapterRoom(roomList,RoomCustomerActivity.this);
                rvRoom.setAdapter(adapterRoom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
