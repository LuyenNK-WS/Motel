package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.Activity_Management.ButtomNavigationHomeManagement;
import com.luyennk.motel.DTOs.UpdatePriceWater;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class AddPriceWaterDialog extends Dialog {

    private EditText edtLimitTop;
    private EditText edtLimitDown;
    private EditText edtPriceWater;
    private Button btnSave;
    private Button btnCancel;

    public AddPriceWaterDialog(@NonNull Context context) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_add_price_water);

        initView();

    }

    private void initView() {
        edtLimitTop = findViewById(R.id.edtLimitTop);
        edtLimitDown = findViewById(R.id.edtLimitDown);
        edtPriceWater = findViewById(R.id.edtPriceWater);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPriceWater();
                Intent intent=new Intent(getContext(), ButtomNavigationHomeManagement.class);
                startActivity(getContext(),intent,null);

                Toast.makeText(getContext(),"Thêm hạn mức giá nước thành công",Toast.LENGTH_LONG).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void addPriceWater() {
        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference().child("UpdatePriceWater");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UpdatePriceWater> updatePriceWaterList = new ArrayList<>();

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    updatePriceWaterList.add(item.getValue(UpdatePriceWater.class));
                }

                int j = 0;
                for (int i = 0; i < updatePriceWaterList.size() + 1; i++) {
                    for (UpdatePriceWater item : updatePriceWaterList) {
                        if (i != Integer.parseInt(item.getId())) {
                            j = i;
                        }
                    }
                }

                UpdatePriceWater updatePriceWater = new UpdatePriceWater(j + "", edtLimitTop.getText().toString(),
                        edtLimitDown.getText().toString(), edtPriceWater.getText().toString());

                mData.child(j+"").setValue(updatePriceWater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
