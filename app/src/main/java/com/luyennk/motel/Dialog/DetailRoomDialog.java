package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.luyennk.motel.R;

public class DetailRoomDialog extends Dialog {
    private Button btnCancel;
    private TextView txtIDRoom;
    private TextView txtAcreage;
    private TextView txtStatus;
    private TextView txtPrice;

    public DetailRoomDialog(@NonNull Context context, String idRoom,String acreage,String status, String price) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_detail_room);
        initView();

        txtIDRoom.setText("Phòng: "+idRoom);
        txtAcreage.setText("Diện tích: "+acreage);
        txtStatus.setText("Trạng thái: "+status);
        txtPrice.setText("Giá phòng: "+price);
    }

    private void initView(){
        btnCancel=findViewById(R.id.btnCancel);
        txtIDRoom=findViewById(R.id.txtIDRoom);
        txtAcreage=findViewById(R.id.txtAcreage);
        txtStatus=findViewById(R.id.txtStatus);
        txtPrice=findViewById(R.id.txtPriceRoom);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
