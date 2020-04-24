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
import com.luyennk.motel.Adapter_Management.AdapterPriceElectric;
import com.luyennk.motel.DTOs.UpdatePriceElectric;
import com.luyennk.motel.Dialog.AddPriceElectricDialog;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class UpdatePriceElectricActivity extends Activity implements View.OnClickListener {

    private static final String TAG="UpdatePriceElectricActivity";

    private ImageView btnBack;
    private FloatingActionButton btnAdd;

    private RecyclerView rvUpdatePriceElectric;
    private AdapterPriceElectric adapterPriceElectric;
    private List<UpdatePriceElectric> updatePriceElectricList;

    private ArrayList<UpdatePriceElectric> updatePriceElectrics=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_electric);
        initView();
        getData();
    }

    private void initView(){
        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        rvUpdatePriceElectric=findViewById(R.id.rvPriceElectric);

        btnBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        LinearLayoutManager llm=new LinearLayoutManager(this);
        rvUpdatePriceElectric.setLayoutManager(llm);
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
                AddPriceElectricDialog dialog=new AddPriceElectricDialog(this);
                dialog.show();
                break;
        }
    }

    private void getData(){
        DatabaseReference all= FirebaseDatabase.getInstance().getReference().child("UpdatePriceElectric");
        all.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    UpdatePriceElectric updatePriceElectric =item.getValue(UpdatePriceElectric.class);
                    updatePriceElectrics.add(updatePriceElectric);
                }

                updatePriceElectricList=updatePriceElectrics;

                for(UpdatePriceElectric item:updatePriceElectricList){
                    Log.d(TAG,"TopLimit :"+item.getTopLimit());
                }

                adapterPriceElectric=new AdapterPriceElectric(updatePriceElectricList,UpdatePriceElectricActivity.this);
                new ItemTouchHelper(iteSimpleCallback).attachToRecyclerView(rvUpdatePriceElectric);
                rvUpdatePriceElectric.setAdapter(new ScaleInAnimationAdapter( adapterPriceElectric));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ItemTouchHelper.SimpleCallback iteSimpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            showAlertDialog("Xóa hạn mức giá điện","Bạn muốn xóa hạn mức này?",viewHolder);
        }
    };

    private void removeData(String id){
        DatabaseReference mData=FirebaseDatabase.getInstance().getReference("UpdatePriceElectric").child(id);
        mData.removeValue();
        Toast.makeText(this,"Xóa thành công",Toast.LENGTH_LONG).show();
    }

    private void showAlertDialog(String title, String message, final RecyclerView.ViewHolder viewHolder1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterPriceElectric.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = null;

                        int i=0;
                        for (UpdatePriceElectric item:updatePriceElectricList){
                            if (i==viewHolder1.getAdapterPosition()){
                                id=item.getId();
                                Log.d(TAG,item.getId());
                            }
                            i++;
                        }
                        removeData(id);

                        updatePriceElectricList.remove(viewHolder1.getAdapterPosition());
                        adapterPriceElectric.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
