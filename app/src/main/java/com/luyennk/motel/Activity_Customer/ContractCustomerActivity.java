package com.luyennk.motel.Activity_Customer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.DTOs.Contract;
import com.luyennk.motel.DTOs.Use;
import com.luyennk.motel.R;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContractCustomerActivity extends Activity implements View.OnClickListener {
    private static final String TAG="ContractCustomerActivity";
    private ImageView btnBack;
    private TextView txtLine1;
    private TextView txtLine2;
    private TextView txtLine3;
    private TextView txtLine4;
    private TextView txtLine5;
    private TextView txtLine6;
    private TextView txtLine7;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_customer);
        initView();
        getData();
    }

    private void initView(){
        btnBack=findViewById(R.id.btnBack);
        txtLine1=findViewById(R.id.txtLine1);
        txtLine2=findViewById(R.id.txtLine2);
        txtLine3=findViewById(R.id.txtLine3);
        txtLine4=findViewById(R.id.txtLine4);
        txtLine5=findViewById(R.id.txtLine5);
        txtLine6=findViewById(R.id.txtLine6);
        txtLine7=findViewById(R.id.txtLine7);

        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent=new Intent(this, ButtomNavigationHomeCustomer.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void getData(){
        DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("Contract");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            Contract contract=new Contract();
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NumberFormat currentLocale = NumberFormat.getInstance();
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    if (getDataPreference().equals(item.getValue(Contract.class).getIdUse())){
                        contract=item.getValue(Contract.class);
                    }
                    Log.d(TAG,"id User: "+item.getValue(Contract.class).getIdUse());
                    Log.d(TAG,"id User Share Preferences: "+getDataPreference());
                }
                String dateFormat = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    dateFormat = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
                }

                txtLine1.setText("Hà Nội, ngày "+dateFormat.substring(6)+" tháng "+dateFormat.substring(4, 6)+" năm "+dateFormat.substring(0, 4));
                txtLine2.setText("Hôm nay, ngày "+dateFormat.substring(6)+" tháng "+dateFormat.substring(4, 6)+" năm "+dateFormat.substring(0, 4)+", các Bên gồm:");

                getUserAddContract(contract.getIdUse());

                txtLine6.setText("ngày "+dateFormat.substring(6)+" tháng "+dateFormat.substring(4, 6)+" năm "+dateFormat.substring(0, 4));
                txtLine7.setText("  5.1. Tiền Thuê nhà đối với Diện Tích Thuê nêu tại mục 1.1\n" +
                        "  Điều 1 là:"+ currentLocale.format(Double.valueOf(contract.getPriceRoom())) +"VNĐ/tháng ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getDataPreference(){
        SharedPreferences sharedPreferences=getSharedPreferences("UserInfor",MODE_PRIVATE);

       return sharedPreferences.getString("idUse","id user");
    }

    private void getUserAddContract(final String idUser){
        DatabaseReference mData=FirebaseDatabase.getInstance().getReference().child("User");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    if (idUser.equals(item.getValue(Use.class).getId())){
                        txtLine3.setText("BÊN THUÊ (Bên B) :"+item.getValue(Use.class).getFullName());
                        txtLine4.setText("CMND số: "+item.getValue(Use.class).getIdCard());
                        txtLine5.setText("Nơi ĐKTT:"+item.getValue(Use.class).getAddress());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
