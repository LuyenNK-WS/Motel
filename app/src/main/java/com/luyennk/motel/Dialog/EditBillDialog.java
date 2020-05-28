package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.FirebaseDatabase;
import com.luyennk.motel.DTOs.Bill;
import com.luyennk.motel.DTOs.BillDetail;
import com.luyennk.motel.R;

import java.util.HashMap;
import java.util.Map;

public class EditBillDialog extends Dialog {
    private TextView txtIDRoom;
    private EditText edtLiabilities;
    private RadioButton ckPay;
    private RadioButton ckLiabilities;
    private Button btnSave;
    private Button btnCancel;

    public EditBillDialog(@NonNull Context context, BillDetail billDetail, Bill bill) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_edit_bill);
        initView(billDetail, bill);
        checkCheckBox();
    }

    private void initView(final BillDetail billDetail, final Bill bill) {
        txtIDRoom = findViewById(R.id.txtIDRoom);
        edtLiabilities = findViewById(R.id.edtLiabilities);
        ckPay = findViewById(R.id.ckPayFull);
        ckLiabilities = findViewById(R.id.ckLiabilities);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePay(billDetail, bill);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        txtIDRoom.setText("Phòng: " + bill.getIdRoom());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ckLiabilities.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ckLiabilities.isChecked()) {
                    edtLiabilities.setEnabled(true);
                } else {
                    edtLiabilities.setEnabled(false);
                }
            }
        });
    }

    private void checkCheckBox() {
        if (ckLiabilities.isChecked()) {
            edtLiabilities.setEnabled(true);
        } else {
            edtLiabilities.setEnabled(false);
        }
    }

    private void savePay(BillDetail billDetail, Bill bill) {
        Map<String, Object> childUpdate = new HashMap<>();
        if (ckPay.isChecked() == true) {
            billDetail.setLiabilities(0);
            billDetail.setPay(billDetail.getTotal());
        } else {
            Double liabilities = billDetail.getTotal() - Double.parseDouble(edtLiabilities.getText().toString());
            billDetail.setLiabilities(liabilities);
            billDetail.setPay(Double.parseDouble(edtLiabilities.getText().toString()));
        }

        String date = bill.getBillDate();
        final String year = date.substring(6);
        final String month = date.substring(3,5);
        childUpdate.put("BillDetail/" + year + "/" + month + "/" + billDetail.getIdBill(), billDetail);
        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdate);
        Toast.makeText(getContext(), "Cập nhật thanh toán hóa đơn thành công", Toast.LENGTH_LONG).show();
        dismiss();
    }
}