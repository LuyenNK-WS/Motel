package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.luyennk.motel.DTOs.BillDetail;
import com.luyennk.motel.DTOs.Service;
import com.luyennk.motel.R;

import java.text.NumberFormat;

public class DetailBillDialog extends Dialog{
    private TextView txtNameAndPriceService;
    private TextView txtNumberElectricLast;
    private TextView txtNumberWaterLast;
    private TextView txtNumberElectricNow;
    private TextView txtNumberWaterNow;
    private TextView txtTotal;
    private TextView txtPay;
    private TextView txtLiability;

    private Button btnDone;


    public DetailBillDialog(@NonNull Context context,BillDetail billDetail) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_detail_bill);
        initView();
        pushData(billDetail);
    }

    private void initView(){
        txtNameAndPriceService=findViewById(R.id.txtNameAndPriceService);
        txtNumberElectricLast=findViewById(R.id.txtNumberElectricLast);
        txtNumberWaterLast=findViewById(R.id.txtNumberWaterLast);
        txtNumberElectricNow=findViewById(R.id.txtNumberElectricNow);
        txtNumberWaterNow=findViewById(R.id.txtNumberWaterNow);
        txtTotal=findViewById(R.id.txtTotal);
        txtPay=findViewById(R.id.txtPay);
        txtLiability=findViewById(R.id.txtLiability);

        btnDone=findViewById(R.id.btnDone);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void pushData(BillDetail billDetail){
        NumberFormat currentLocale = NumberFormat.getInstance();
        String service="";
        for (Service item: billDetail.getIdService()){
            service+=item.getNameService()+": "+currentLocale.format(Double.valueOf(item.getPriceService()))+"vnd\n";
        }

        txtNameAndPriceService.setText(service);
        txtNumberElectricLast.setText("Số điện tháng trước: "+billDetail.getNumberElectricLast());
        txtNumberWaterLast.setText("Số nước tháng trước: "+billDetail.getNumberWaterLast());
        txtNumberElectricNow.setText("Số điện tháng này: "+billDetail.getNumberElectricNow());
        txtNumberWaterNow.setText("Số nước tháng này: "+billDetail.getNumberWaterNow());
        txtTotal.setText("Tổng tiền: "+currentLocale.format(billDetail.getTotal())+" vnd");
        txtPay.setText("Thanh toán: "+currentLocale.format(billDetail.getPay())+" vnd");
        txtLiability.setText("Còn nợ: "+currentLocale.format(billDetail.getLiabilities())+" vnd");
    }
}
