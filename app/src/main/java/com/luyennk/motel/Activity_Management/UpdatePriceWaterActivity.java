package com.luyennk.motel.Activity_Management;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.luyennk.motel.Adapter_Management.AdapterPriceWater;
import com.luyennk.motel.DTOs.UpdatePriceWater;
import com.luyennk.motel.Dialog.AddPriceWaterDialog;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

public class UpdatePriceWaterActivity extends Activity implements View.OnClickListener {

    private static final String TAG="UpdatePriceWaterActivity";

    private ImageView btnBack;
    private FloatingActionButton btnAdd;

    private RecyclerView rvUpdatePriceWater1;
    private AdapterPriceWater adapterPriceWater;
    private List<UpdatePriceWater> updatePriceWaterList;

    private ArrayList<UpdatePriceWater> updatePriceWaters=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_water);
        initView();
        getData();
    }

    private void initView(){
        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        rvUpdatePriceWater1=findViewById(R.id.rvUpdatePriceWater);

        btnBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        LinearLayoutManager llm=new LinearLayoutManager(this);
        rvUpdatePriceWater1.setLayoutManager(llm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent=new Intent(this, ButtomNavigationHomeManagement.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnAdd:
                AddPriceWaterDialog dialog=new AddPriceWaterDialog(this);
                dialog.show();
                break;
        }
    }

    private void getData(){
        DatabaseReference all= FirebaseDatabase.getInstance().getReference().child("UpdatePriceWater");
        all.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    UpdatePriceWater updatePriceWater=item.getValue(UpdatePriceWater.class);
                    updatePriceWaters.add(updatePriceWater);
                }

                updatePriceWaterList=updatePriceWaters;

                for(UpdatePriceWater item:updatePriceWaterList){
                    Log.d(TAG,"TopLimit :"+item.getTopLimit());
                }

                adapterPriceWater=new AdapterPriceWater(updatePriceWaterList,UpdatePriceWaterActivity.this);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(rvUpdatePriceWater1);
                rvUpdatePriceWater1.setAdapter(adapterPriceWater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            showAlertDialog("Xóa hạn mức giá nước","Bạn muốn xóa hạn mức này?",viewHolder);
        }
    };

    private void showAlertDialog(String title, String message, final RecyclerView.ViewHolder viewHolder1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterPriceWater.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = null;

                        int i=0;
                        for (UpdatePriceWater item:updatePriceWaterList){
                            if (i==viewHolder1.getAdapterPosition()){
                                id=item.getId();
                                Log.d(TAG,item.getId());
                            }
                            i++;
                        }
                        removeData(id);

                        updatePriceWaterList.remove(viewHolder1.getAdapterPosition());
                        adapterPriceWater.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void removeData(String id){
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference("UpdatePriceWater").child(id);
        mData.removeValue();
        Toast.makeText(this,"Xóa thành công",Toast.LENGTH_LONG).show();
    }
}
