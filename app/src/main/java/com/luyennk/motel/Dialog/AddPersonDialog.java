package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.luyennk.motel.DTOs.Person;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

public class AddPersonDialog extends Dialog implements View.OnClickListener {
    private static final String TAG="AddPersonDialog";

    private EditText edtFullName;
    private EditText edtIdCard;
    private EditText edtPhoneNumber;
    private EditText edtJob;

    private Button btnAddPerson;
    private Button btnDone;
    private Button btnCancel;

    private TextView txtNamePerson;

    public static List<Person> personList=new ArrayList<com.luyennk.motel.DTOs.Person>();

    public AddPersonDialog(@NonNull Context context) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_add_person);
        initView();
    }

    private void initView(){
        edtFullName=findViewById(R.id.edtFullName);
        edtIdCard=findViewById(R.id.edtIdCard);
        edtPhoneNumber=findViewById(R.id.edtPhoneNumber);
        edtJob=findViewById(R.id.edtJob);

        btnAddPerson=findViewById(R.id.btnAddPerson);
        btnDone=findViewById(R.id.btnDone);
        btnCancel=findViewById(R.id.btnCancel);

        txtNamePerson=findViewById(R.id.txtNamePerson);

        btnAddPerson.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddPerson:
                addPerson();
                edtFullName.setText("");
                edtPhoneNumber.setText("");
                edtIdCard.setText("");
                edtJob.setText("");
                break;
            case R.id.btnDone:
                dismiss();
                break;
            case R.id.btnCancel:
                personList.removeAll(personList);
                dismiss();
                break;
        }
    }

    private void addPerson(){
        if (!edtFullName.getText().toString().equals("")){
            String name = "";
            personList.add(new com.luyennk.motel.DTOs.Person(edtFullName.getText().toString(),edtIdCard.getText().toString(),edtPhoneNumber.getText().toString(),edtJob.getText().toString()));
            for (Person item: personList){
                name+=item.getFullName()+"\n";
            }
            txtNamePerson.setText(name);
        }else {
            Toast.makeText(getContext(),"Bạn cần nhập tên người ở cùng",Toast.LENGTH_LONG).show();
        }

    }
}
