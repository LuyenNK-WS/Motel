package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.luyennk.motel.DTOs.Use;
import com.luyennk.motel.R;

public class DetailCustomerDialog extends Dialog {

    private TextView txtFullName;
    private TextView txtAddress;
    private TextView txtPhoneNumber;
    private TextView txtEmail;

    private Button btnDone;

    public DetailCustomerDialog(@NonNull Context context,Use user) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_detail_customer);
        initView();
        pushData(user);
    }

    private void initView(){
        txtFullName=findViewById(R.id.txtFullName);
        txtAddress=findViewById(R.id.txtAddress);
        txtPhoneNumber=findViewById(R.id.txtPhoneNumber);
        txtEmail=findViewById(R.id.txtEmail);
        btnDone = findViewById(R.id.btnDone);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void pushData(Use user){
        txtFullName.setText(user.getFullName());
        txtAddress.setText(user.getAddress());
        txtPhoneNumber.setText(user.getPhoneNumber());
        txtEmail.setText(user.getMail());
    }
}
