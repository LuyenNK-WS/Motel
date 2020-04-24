package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.luyennk.motel.Activity_Management.AddContractActivity;
import com.luyennk.motel.DTOs.Use;
import com.luyennk.motel.R;

import static androidx.core.content.ContextCompat.startActivity;

public class MenuItemCustomerDialog extends Dialog {
    private Button btnEdit;
    private Button btnCancel;
    private Button btnCreateContract;

    public MenuItemCustomerDialog(@NonNull Context context,Use use,String id) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_menu_item_customer);

        initView(use,id);
    }

    private void initView(final Use use, final String id){
        btnEdit=findViewById(R.id.btnEdit);
        btnCancel=findViewById(R.id.btnCancel);
        btnCreateContract=findViewById(R.id.btnCreateContract);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                EditCustomerDialog editCustomerDialog=new EditCustomerDialog(getContext(),use,id);
                editCustomerDialog.show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnCreateContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), AddContractActivity.class);
                intent.putExtra("idUser",use.getId());
                intent.putExtra("nameUser",use.getFullName());
                startActivity(getContext(),intent,null);
            }
        });
    }
}
