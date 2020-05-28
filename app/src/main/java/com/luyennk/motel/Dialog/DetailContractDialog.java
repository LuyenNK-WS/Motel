package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.luyennk.motel.DTOs.Person;
import com.luyennk.motel.DTOs.Service;
import com.luyennk.motel.R;

import java.text.NumberFormat;
import java.util.List;

public class DetailContractDialog extends Dialog {
    private TextView txtIDRoom;
    private TextView txtNameUser;
    private TextView txtPersonWithLive;
    private TextView txtNameService;
    private TextView txtDateOfCheckIn;
    private TextView txtDateOfCheckOut;
    private TextView txtPriceRoom;
    private Button btnCancel;

    public DetailContractDialog(@NonNull Context context, String idRoom, String nameUser, List<Person> personList, List<Service> serviceList, String dateCheckIn, String dateCheckOut,String priceRoom) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_detail_contract);
        initView();

        NumberFormat numberFormat=NumberFormat.getInstance();

        txtIDRoom.setText("Phòng: "+idRoom);
        txtNameUser.setText("KH: "+nameUser);
        String person="";
        if (personList.size() != 0) {
            for (Person item : personList) {
                person += item.getFullName() + "\n";
            }
        }
        txtPersonWithLive.setText(person);

        String service="";
        for (Service item:serviceList){
            service+=item.getNameService()+" :"+numberFormat.format(Double.parseDouble(item.getPriceService()))+"vnd/thang\n";
        }
        txtNameService.setText(service);
        txtDateOfCheckIn.setText("Ngày thuê: "+dateCheckIn);
        txtDateOfCheckOut.setText("Ngày trả: "+dateCheckOut);
        txtPriceRoom.setText("Giá phòng: "+numberFormat.format(Double.parseDouble(priceRoom))+"vnd/thang");
    }

    private void initView(){
        txtIDRoom=findViewById(R.id.txtIDRoom);
        txtNameUser=findViewById(R.id.txtNameUser);
        txtPersonWithLive=findViewById(R.id.txtPersonLiveWith);
        txtNameService=findViewById(R.id.txtNameService);
        txtDateOfCheckIn=findViewById(R.id.txtDateOfCheckIn);
        txtDateOfCheckOut=findViewById(R.id.txtDateOfCheckOut);
        txtPriceRoom=findViewById(R.id.txtPriceRoom);
        btnCancel=findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
