package com.luyennk.motel.Activity_Management;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.Adapter_Management.AdapterRoom;
import com.luyennk.motel.DTOs.Room;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends Activity implements View.OnClickListener {

    private static final String TAG="RoomActivity";

    private ImageView btnBack;
    private FloatingActionButton btnAdd;
    private TextView txtTotalRoom;

    private RecyclerView rvListRoom;
    private AdapterRoom adapter;
    private List<Room> roomList;

    private ArrayList<Room> rooms = new ArrayList<>();

    private int i=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_room);
        initView();

        getData();
    }

    private void initView(){
        btnBack = findViewById(R.id.btnBack);
        btnAdd=findViewById(R.id.btnAdd);
        txtTotalRoom=findViewById(R.id.txtTotal);
        rvListRoom=findViewById(R.id.rvRoom);

        btnBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        LinearLayoutManager llm=new LinearLayoutManager(this);
        rvListRoom.setLayoutManager(llm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent =new Intent(this, ButtonNavigationHomeManagement.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnAdd:
                Intent intent1=new Intent(this,AddRoomActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void getData(){
        DatabaseReference all = FirebaseDatabase.getInstance().getReference().child("Room");

        all.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Room room = item.getValue(Room.class);
                    rooms.add(room);
                    i++;
                }
                roomList=rooms;

                for (Room item:roomList){
                    Log.d(TAG,"idRoom: "+item.getIdRoom());
                }

                txtTotalRoom.setText("Tông số phòng : "+i+" phòng");

                adapter= new AdapterRoom(RoomActivity.this,roomList);
                new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rvListRoom);
                rvListRoom.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ItemTouchHelper.SimpleCallback itemTouchHelper=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            showAlterDialog("Xóa","Bạn có muốn xóa phòng này",viewHolder);
        }
    };

    private void showAlterDialog(String title, String message, final RecyclerView.ViewHolder viewHolder){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = null;

                        int i=0;
                        for (Room item:roomList){
                            if (i==viewHolder.getAdapterPosition()){
                                id=item.getIdRoom();
                                Log.d(TAG,item.getIdRoom());
                            }
                            i++;
                        }
                        removeData(id);

                        roomList.remove(viewHolder.getAdapterPosition());
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void removeData(String id){
        DatabaseReference mData=FirebaseDatabase.getInstance().getReference("Room").child(id);
        mData.removeValue();
        Toast.makeText(this,"Xóa thành công",Toast.LENGTH_LONG).show();
    }

}
