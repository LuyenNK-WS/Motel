package com.luyennk.motel.Activity_Management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.luyennk.motel.Adapter_Management.AdapterNotification;
import com.luyennk.motel.DTOs.Notification;
import com.luyennk.motel.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends Activity implements View.OnClickListener {
    private ImageView btnBack;
    private ImageView btnSend;
    private EditText edtTextMessage;

    private RecyclerView rvNotification;
    private AdapterNotification adapterNotification;
    private List<Notification> notificationList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
        getData();
    }

    private void initView(){
        btnBack=findViewById(R.id.btnBack);
        btnSend=findViewById(R.id.btnSend);
        edtTextMessage=findViewById(R.id.edtSend);
        rvNotification=findViewById(R.id.rvNotification);

        btnBack.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        LinearLayoutManager llm=new LinearLayoutManager(this);
        rvNotification.setLayoutManager(llm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent = new Intent(NotificationActivity.this, ButtonNavigationHomeManagement.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnSend:
                sendNotification();
                break;
        }
    }

    private void sendNotification(){
        String dateFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateFormat = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        }
        String date = dateFormat.substring(6) + "/" + dateFormat.substring(4, 6) + "/" + dateFormat.substring(0, 4);
        Notification notification=new Notification(date,edtTextMessage.getText().toString());
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference().child("Notification");
        mData.child(mData.push().getKey()).setValue(notification);
    }

    private void getData(){
        DatabaseReference all= FirebaseDatabase.getInstance().getReference().child("Notification");
        all.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    Notification notification=item.getValue(Notification.class);
                    notificationList.add(notification);
                }
                adapterNotification=new AdapterNotification(notificationList,NotificationActivity.this);
                rvNotification.setAdapter(adapterNotification);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
