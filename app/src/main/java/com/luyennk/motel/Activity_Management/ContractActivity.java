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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.Adapter_Management.AdapterContract;
import com.luyennk.motel.DTOs.Contract;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

public class ContractActivity extends Activity implements View.OnClickListener {

    private static final String TAG="ContractActivity";

    private ImageView btnBack;

    private RecyclerView rvListContract;
    private AdapterContract adapterContract;
    private List<Contract> contractList;

    private ArrayList<Contract> contracts=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        initView();
        getData();
    }

    private void initView(){
        btnBack = findViewById(R.id.btnBack);
        rvListContract=findViewById(R.id.rvContract);

        btnBack.setOnClickListener(this);

        LinearLayoutManager llm=new LinearLayoutManager(this);
        rvListContract.setLayoutManager(llm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent = new Intent(ContractActivity.this, ButtonNavigationHomeManagement.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void getData(){
        DatabaseReference all= FirebaseDatabase.getInstance().getReference().child("Contract");
        all.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    Contract contract=item.getValue(Contract.class);
                    contracts.add(contract);
                }

                contractList=contracts;

                for (Contract item:contractList){
                    Log.d(TAG,item.getIdRoom());
                }

                adapterContract=new AdapterContract(contractList,ContractActivity.this);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(rvListContract);
                rvListContract.setAdapter(adapterContract);
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
            showAlertDialog("Xoá hóa đơn","Ban có muốn xóa hóa đơn này không?",viewHolder);
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
                        adapterContract.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = null;

                        int i=0;
                        for (Contract item:contractList){
                            if (i==viewHolder1.getAdapterPosition()){
                                id=item.getIdContract();
                                Log.d(TAG,item.getIdContract());
                            }
                            i++;
                        }
                        removeData(id);

                        contractList.remove(viewHolder1.getAdapterPosition());
                        adapterContract.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void removeData(String id){
        DatabaseReference mData=FirebaseDatabase.getInstance().getReference("Contract").child(id);
        mData.removeValue();
        Toast.makeText(this,"Xóa thành công",Toast.LENGTH_LONG).show();
    }

}
