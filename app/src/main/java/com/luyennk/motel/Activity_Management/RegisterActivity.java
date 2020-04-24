package com.luyennk.motel.Activity_Management;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.DTOs.Use;
import com.luyennk.motel.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";

    private EditText edtUserName;
    private EditText edtPassword;
    private EditText edtReviewPassword;
    private EditText edtFullName;
    private EditText edtAddress;
    private EditText edtIDCard;
    private EditText edtPhoneNumber;
    private EditText edtJob;
    private EditText edtMail;
    private Button btnRegisterUser;
    private ImageView btnBack;

    private DatabaseReference mDatabase;

    private Boolean checked = false;
    private int i = 0;

    final long delay=1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initview();
    }

    private void initview() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassWord);
        edtReviewPassword = findViewById(R.id.edtReviewPassWord);
        edtFullName = findViewById(R.id.edtNameUseCreate);
        edtAddress = findViewById(R.id.edtAddress);
        edtIDCard = findViewById(R.id.edtIDCard);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtJob = findViewById(R.id.edtJob);
        edtMail = findViewById(R.id.edtMail);
        btnRegisterUser = findViewById(R.id.btnRegisterUser);
        btnBack = findViewById(R.id.btnBack);

//        edtUserName.setOnClickListener(this);
//        edtPassword.setOnClickListener(this);
//        edtReviewPassword.setOnClickListener(this);
//        edtFullName.setOnClickListener(this);
//        edtAddress.setOnClickListener(this);
//        edtIDCard.setOnClickListener(this);
//        edtPhoneNumber.setOnClickListener(this);
//        edtJob.setOnClickListener(this);
//        edtMail.setOnClickListener(this);
        btnRegisterUser.setOnClickListener(this);
        btnBack.setOnClickListener(this);

    }

    //create use
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerUse() {
        LocalDate date1 = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date1 = LocalDate.now();
        }
        final String dateFormat = date1.format(DateTimeFormatter.BASIC_ISO_DATE);

        if (edtUserName.getText().toString().equals("") || edtFullName.getText().toString().equals("") ||
                edtPassword.getText().toString().equals("") || edtUserName.getText().toString().equals("") ||
                edtAddress.getText().toString().equals("") || edtIDCard.getText().toString().equals("") ||
                edtPhoneNumber.getText().toString().equals("") || edtJob.getText().toString().equals("") ||
                edtMail.getText().toString().equals("")) {
            Toast.makeText(this, "Bạn cần nhập đủ thông tin", Toast.LENGTH_SHORT).show();

        } else {
            if (edtPassword.getText().toString().equals(edtReviewPassword.getText().toString())) {
                mDatabase = FirebaseDatabase.getInstance().getReference("User");

                final String userNameCheck = edtUserName.getText().toString();
                checkUser(userNameCheck);
                Log.d(TAG, "check : " + checked.toString() + edtUserName.getText().toString());

                if (checked == true) {
                    Log.d(TAG, dateFormat);
                    String userID = edtUserName.getText().toString() + dateFormat;
                    Use use = new Use(edtUserName.getText().toString() + dateFormat, edtFullName.getText().toString(),
                            edtPassword.getText().toString(), edtUserName.getText().toString(), edtAddress.getText().toString(),
                            edtIDCard.getText().toString(), edtPhoneNumber.getText().toString(), edtJob.getText().toString(),
                            edtMail.getText().toString(), "0");

                    mDatabase.child(userID).setValue(use);

                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("userName", edtUserName.getText().toString());
                    intent.putExtra("passWord", edtPassword.getText().toString());
                    startActivity(intent);

                } else {
                    Toast.makeText(RegisterActivity.this, "Tài khoản này đã tồn tại, Yêu cầu người dùng sử dụng tài khoản khác!", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(this, "Mật khẩu không giống nhau , nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkUser(final String userName) {
        final ArrayList<Use> uses = new ArrayList<>();
        //DatabaseReference all = FirebaseDatabase.getInstance().getReference().child("User"); // lay toan bo data
        Query allPost = FirebaseDatabase.getInstance().getReference().child("User");
        i=0;
        allPost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Use use = item.getValue(Use.class);
                    uses.add(use);
                }

                for (Use item : uses) {

                    if (item.getNameUse().equals(userName)) {
                        i++;
                    }
                }

                Log.d(TAG, "So luong: " + i);

                if (i != 0) {
                    checked = false;
                } else {
                    checked = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegisterUser:
                registerUse();
                break;
            case R.id.btnBack:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    //add Use
//    private void createUse(){
//        mDatabase = FirebaseDatabase.getInstance().getReference("User");
//
//        String userID = edtUserName.getText().toString();
//        Use use = new Use(edtUserName.getText().toString() , edtFullName.getText().toString(),
//                edtPassword.getText().toString(), edtUserName.getText().toString(), edtAddress.getText().toString(),
//                edtIDCard.getText().toString(), edtPhoneNumber.getText().toString(), edtJob.getText().toString(),
//                edtMail.getText().toString(), "0");
//
//        mDatabase.child(userID).setValue(use, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                if (databaseError != null){
//                    Toast.makeText(RegisterActivity.this,"Error",Toast.LENGTH_LONG).show();
//                }else {
//                    Toast.makeText(RegisterActivity.this,"Success",Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//    }
}
