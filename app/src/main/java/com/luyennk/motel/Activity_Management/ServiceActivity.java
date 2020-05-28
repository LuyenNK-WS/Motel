package com.luyennk.motel.Activity_Management;

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
import com.luyennk.motel.Adapter_Management.AdapterService;
import com.luyennk.motel.DTOs.Service;
import com.luyennk.motel.Dialog.AddServiceDialog;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class ServiceActivity extends Activity implements View.OnClickListener {

    private static final String TAG="ServiceActivity";

    private ImageView btnBack;
    private FloatingActionButton btnAdd;

    private RecyclerView rvService;
    private AdapterService adapterService;
    private List<Service> serviceList;

    private ArrayList<Service> services=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        initView();

        getData();
    }

    private void initView(){
        btnBack=findViewById(R.id.btnBack);
        btnAdd=findViewById(R.id.btnAdd);
        rvService=findViewById(R.id.rvService);

        btnBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        LinearLayoutManager llm=new LinearLayoutManager(this);
        rvService.setLayoutManager(llm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent = new Intent(ServiceActivity.this, ButtonNavigationHomeManagement.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnAdd:
                AddServiceDialog dialog=new AddServiceDialog(this);
                dialog.show();
                break;
        }
    }

    private void getData(){
        DatabaseReference all= FirebaseDatabase.getInstance().getReference().child("Service");
        all.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    Service service=item.getValue(Service.class);
                    services.add(service);

                }

                serviceList=services;

                for (Service item:serviceList){
                    Log.d(TAG,"idService: "+item.getIdService());
                }

                adapterService=new AdapterService(serviceList,ServiceActivity.this);
                new ItemTouchHelper(itemSimpleCallback).attachToRecyclerView(rvService);
                rvService.setAdapter(new ScaleInAnimationAdapter(adapterService));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ItemTouchHelper.SimpleCallback itemSimpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            showAlertDialog("Xóa dịch vụ","Bạn có muốn xóa dịch vụ?",viewHolder);
        }
    };

    private void removeData(String id){
        DatabaseReference mData=FirebaseDatabase.getInstance().getReference("Service").child(id);
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
                        adapterService.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = null;

                        int i=0;
                        for (Service item:serviceList){
                            if (i==viewHolder1.getAdapterPosition()){
                                id=item.getIdService();
                                Log.d(TAG,item.getIdService());
                            }
                            i++;
                        }
                        removeData(id);

                        serviceList.remove(viewHolder1.getAdapterPosition());
                        adapterService.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
