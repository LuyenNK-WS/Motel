package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.luyennk.motel.Activity_Management.ButtonNavigationHomeManagement;
import com.luyennk.motel.DTOs.UpdatePriceElectric;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class AddPriceElectricDialog extends Dialog {

    private static final String TAG="AddPriceElectric";

    private EditText edtLimitTop;
    private EditText edtLimitDown;
    private EditText edtPriceElectric;
    private Button btnSave;
    private Button btnCancel;

    public AddPriceElectricDialog(@NonNull Context context) {
        super(context);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_add_price_electric);

        initView();
    }

    private void initView(){
        edtLimitTop=findViewById(R.id.edtLimitTop);
        edtLimitDown=findViewById(R.id.edtLimitDown);
        edtPriceElectric=findViewById(R.id.edtPriceElectric);
        btnSave=findViewById(R.id.btnSave);
        btnCancel=findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPriceElectric();
                Intent intent=new Intent(getContext(), ButtonNavigationHomeManagement.class);
                startActivity(getContext(),intent,null);

                Toast.makeText(getContext(),"Thêm hạn mức giá điện thành công",Toast.LENGTH_LONG).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void addPriceElectric(){
        final DatabaseReference mData=FirebaseDatabase.getInstance().getReference().child("UpdatePriceElectric");

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UpdatePriceElectric> updatePriceElectrics=new ArrayList<>();

                for (DataSnapshot item:dataSnapshot.getChildren()){
                    updatePriceElectrics.add(item.getValue(UpdatePriceElectric.class));
                }

                int j=0;

                for (int i=0;i<updatePriceElectrics.size()+1;i++){
                    for (UpdatePriceElectric item:updatePriceElectrics){
                        if (i!= Integer.parseInt(item.getId())){
                            j=i;
                        }
                    }
                }

                Log.d(TAG,j+"");

               UpdatePriceElectric updatePriceElectric=new UpdatePriceElectric(j+"",edtLimitTop.getText().toString(),
                       edtLimitDown.getText().toString(),edtPriceElectric.getText().toString());
               mData.child(j+"").setValue(updatePriceElectric);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
