package com.luyennk.motel.Activity_Management;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.firebase.database.FirebaseDatabase;
import com.luyennk.motel.DTOs.Use;
import com.luyennk.motel.R;

import java.util.HashMap;
import java.util.Map;

public class EditAccountActivity extends Activity implements View.OnClickListener {
    private EditText edtFullName;
    private EditText edtIDCard;
    private EditText edtAddress;
    private EditText edtJob;
    private EditText edtEmail;
    private EditText edtUerName;
    private EditText edtPassWord;
    private EditText edtPhoneNumber;

    private Button btnSave;
    private Button btnCancel;

    private Use use=new Use();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        initView();
        getDataSharedPreferences();
    }

    private void initView() {
        edtFullName = findViewById(R.id.edtFullName);
        edtIDCard = findViewById(R.id.edtIDCard);
        edtAddress = findViewById(R.id.edtAddress);
        edtJob = findViewById(R.id.edtJob);
        edtEmail = findViewById(R.id.edtEmail);
        edtUerName = findViewById(R.id.edtNameUse);
        edtPassWord = findViewById(R.id.edtPassWord);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnSave:
                updateDate();
                finish();
                break;
        }
    }

    private void getDataSharedPreferences() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("UserInfor", Context.MODE_PRIVATE);

        String fullName = sharedPreferences.getString("fullName", "Full Name");
        String idCard = sharedPreferences.getString("idCard", "Full Name");
        String address = sharedPreferences.getString("address", "Full Name");
        String job = sharedPreferences.getString("job", "Full Name");
        String email = sharedPreferences.getString("mail", "Full Name");
        String userName = sharedPreferences.getString("nameUse", "Full Name");
        String passWord = sharedPreferences.getString("passWord", "Full Name");
        String phoneNumber = sharedPreferences.getString("phoneNumber", "Full Name");
        String idUse = sharedPreferences.getString("idUse", "Full Name");
        String permission = sharedPreferences.getString("permission", "Full Name");

        use=new Use(idUse,fullName,passWord,userName,address,idCard,phoneNumber,job,email,permission);

        edtFullName.setText(fullName);
        edtIDCard.setText(idCard);
        edtAddress.setText(address);
        edtJob.setText(job);
        edtEmail.setText(email);
        edtUerName.setText(userName);
        edtPassWord.setText(passWord);
        edtPhoneNumber.setText(phoneNumber);
    }

    private void updateDate() {
        Map<String, Object> childUpdate = new HashMap<>();

        //Thêm object cần update
        Use useUpdate = new Use(use.getId(), edtFullName.getText().toString(), edtPassWord.getText().toString(), edtUerName.getText().toString(), edtAddress.getText().toString(),
                edtIDCard.getText().toString(), edtPhoneNumber.getText().toString(), edtJob.getText().toString(), edtEmail.getText().toString(), use.getPermission());
        childUpdate.put("User/" + use.getId(), useUpdate);
        //Cập nhật lại DB
        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdate);
    }
}
