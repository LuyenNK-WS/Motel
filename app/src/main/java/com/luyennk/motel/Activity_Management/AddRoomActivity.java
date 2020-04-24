package com.luyennk.motel.Activity_Management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.DTOs.Room;
import com.luyennk.motel.R;

public class AddRoomActivity extends Activity implements View.OnClickListener {
    private ImageView btnBack;
    private EditText edtNameRoom;
    private EditText edtAcreage;
    private EditText edtPriceRoom;
    private EditText edtStatus;

    private Button btnCreateRoom;

    private static final String TAG="AddRoomActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        initView();
    }

    private void initView(){
        btnBack=findViewById(R.id.btnBack);
        btnCreateRoom=findViewById(R.id.btnCreateRoom);
        edtAcreage=findViewById(R.id.edtAcreage);
        edtNameRoom=findViewById(R.id.edtNameRoom);
        edtPriceRoom=findViewById(R.id.edtPriceRoom);
        edtStatus=findViewById(R.id.edtStatus);

        btnBack.setOnClickListener(this);
        btnCreateRoom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent=new Intent(this,RoomActivity.class);
                startActivity(intent);
                break;
            case R.id.btnCreateRoom:
                createRoom();
                break;
        }
    }

    private void createRoom(){
        final DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("Room");

        mData.addListenerForSingleValueEvent(new ValueEventListener() {

            int i=0;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    if (item.getValue(Room.class).getIdRoom().equals(edtNameRoom.getText().toString())){
                        i++;
                    }
                }

                if (i==0){
                    Room room=new Room(edtNameRoom.getText().toString(),edtAcreage.getText().toString(),edtStatus.getText().toString(),edtPriceRoom.getText().toString());
                    String id=edtNameRoom.getText().toString();
                    mData.child(id).setValue(room);
                    Toast.makeText(AddRoomActivity.this,"Thêm phòng thành công",Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(AddRoomActivity.this,RoomActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(AddRoomActivity.this,"Phòng này đã tồn tại",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
