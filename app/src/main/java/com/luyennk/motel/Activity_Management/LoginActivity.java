package com.luyennk.motel.Activity_Management;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.Activity_Customer.ButtomNavigationHomeCustomer;
import com.luyennk.motel.DTOs.Use;
import com.luyennk.motel.R;

import java.util.ArrayList;

public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private EditText edtUserName;
    private EditText edtPassWord;
    private Button btnLogin;
    private TextView btnRegister;

    private int INTERNET_PERMISSION_CODE = 1;

    private String nameFile = "HoaDonPhong.txt";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(LoginActivity.this, "Đã bật mạng!", Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission();
        }

        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        try {
            Intent intent = getIntent();
            String userNameIntent = intent.getStringExtra("userName");
            String passWordIntent = intent.getStringExtra("passWord");
            edtUserName.setText(userNameIntent);
            edtPassWord.setText(passWordIntent);
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                checkLogin();
                //test();
                break;
            case R.id.btnRegister:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void checkLogin() {

        final ArrayList<Use> uses = new ArrayList<>();

        DatabaseReference all = FirebaseDatabase.getInstance().getReference().child("User");

        all.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Use use = item.getValue(Use.class);
                    uses.add(use);
                }

                //check login
                int i=0;
                for (Use item : uses) {
                    if (item.getNameUse().equals(edtUserName.getText().toString()) == true) {
                        if (item.getPassWord().equals(edtPassWord.getText().toString()) == true) {
                            if (item.getPermission().equals("1") == true) {
                                createSharedPreferences(item.getId(), item.getFullName(), item.getPassWord(), item.getNameUse(), item.getAddress(),
                                        item.getIdCard(), item.getPhoneNumber(), item.getJob(), item.getMail(), item.getPermission());
                                Intent intent = new Intent(LoginActivity.this, ButtomNavigationHomeManagement.class);
                                startActivity(intent);
                                i++;
                                finish();
                            }else if (item.getPermission().equals("0") == true){
                                createSharedPreferences(item.getId(), item.getFullName(), item.getPassWord(), item.getNameUse(), item.getAddress(),
                                        item.getIdCard(), item.getPhoneNumber(), item.getJob(), item.getMail(), item.getPermission());
                                Intent intent = new Intent(LoginActivity.this, ButtomNavigationHomeCustomer.class);
                                startActivity(intent);
                                i++;
                                finish();
                            }else {
                                Toast.makeText(LoginActivity.this,"Tài khaorn của bạn dã bị khóa, Yêu cầu gặp chủ nhà để mở khóa tài khoản",Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                    }
                }

                if (i==0){
                    Toast.makeText(LoginActivity.this,"Tài khoản hoặc mật khẩu không đúng",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Bạn cần có Internet để sử dụng ứng dụng này!")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == INTERNET_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Dented", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private void test() {
//        Map<String, Object> childUpdates = new HashMap<>();
//
//        // Thêm các object cần sửa kèm thông tin child
//        // vd: Post/Key
//        //childUpdates.put("key", <object_cua_ban>);
//
//        Service service=new Service("1","ABC","12733");
//
//        childUpdates.put("Service/1",service);
//
//        // cập nhập lại
//        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);

        Intent intent = new Intent(LoginActivity.this, ButtomNavigationHomeManagement.class);
        startActivity(intent);
        finish();
    }

    private void createSharedPreferences(String id, String fullName, String passWord, String nameUse, String address, String idCard
            , String phoneNumber, String job, String mail, String permission) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfor", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("idUse", id);
        editor.putString("fullName", fullName);
        editor.putString("passWord", passWord);
        editor.putString("nameUse", nameUse);
        editor.putString("address", address);
        editor.putString("idCard", idCard);
        editor.putString("phoneNumber", phoneNumber);
        editor.putString("job", job);
        editor.putString("mail", mail);
        editor.putString("permission", permission);

        editor.commit();
    }
}
