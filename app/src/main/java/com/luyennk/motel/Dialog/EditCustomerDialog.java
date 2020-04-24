package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.FirebaseDatabase;
import com.luyennk.motel.Activity_Management.CustomerActivity;
import com.luyennk.motel.DTOs.Use;
import com.luyennk.motel.R;

import java.util.HashMap;
import java.util.Map;

import static androidx.core.content.ContextCompat.startActivity;

public class EditCustomerDialog extends Dialog implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "EditCustomerDialog";

    private Spinner spinner;
    private Button btnSaveChange;
    private Button btnCancel;
    private String permission;

    public EditCustomerDialog(@NonNull Context context,Use use,String id) {
        super(context);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_edit_customer);

        initView(use,id);

        pushDataInSpinner();
    }

    private void initView(final Use use, final String id) {
        spinner = findViewById(R.id.spinnerPermission);
        btnSaveChange = findViewById(R.id.btnSaveChange);
        btnCancel = findViewById(R.id.btnCancel);

        btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCustomer(use,id);
                Toast.makeText(getContext(),"Sửa quyền thành công!",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getContext(), CustomerActivity.class);
                startActivity(getContext(),intent,null);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void pushDataInSpinner(){
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_peermission,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position==0 || position==2){
            permission="0";
        }else if (position==1){
            permission="1";
        }else{
            permission="2";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateCustomer(Use use,String id){
        Map<String,Object> childUpdate=new HashMap<>();

        //Thêm object cần update
        Use useUpdate=new Use(id,use.getFullName(),use.getPassWord(),use.getNameUse(),use.getAddress(),
                use.getIdCard(),use.getPhoneNumber(),use.getJob(),use.getMail(),permission);

        childUpdate.put("User/"+id,useUpdate);

        //Cập nhật lại DB
        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdate);
    }
}
