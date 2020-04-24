package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.Activity_Management.ButtomNavigationHomeManagement;
import com.luyennk.motel.DTOs.Service;
import com.luyennk.motel.R;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class AddServiceDialog extends Dialog {
    private EditText edtNameService;
    private EditText edtPriceService;
    private Button btnSave;
    private Button btnCancel;

    private DatabaseReference mDatabase;

    public AddServiceDialog(@NonNull Context context) {
        super(context);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.dialog_add_service);

        initView();

    }

    private void initView(){
        edtNameService=findViewById(R.id.edtNameService);
        edtPriceService=findViewById(R.id.edtPriceService);
        btnSave=findViewById(R.id.btnSave);
        btnCancel=findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                addService();
                Intent intent=new Intent(getContext(), ButtomNavigationHomeManagement.class);
                startActivity(getContext(),intent,null);

                Toast.makeText(getContext(),"Thêm dịch vụ thành công",Toast.LENGTH_LONG).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addService(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Service");

        final ArrayList<Service> services=new ArrayList<>();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    Service service= item.getValue(Service.class);
                    services.add(service);
                }

                int j=0;

                for (int i=0;i<services.size()+1;i++) {
                    for (Service item : services) {
                        if (i!=Integer.parseInt(item.getIdService())){
                            j=i;
                        }
                    }
                }

                Service service=new Service(String.valueOf(j),edtNameService.getText().toString(),edtPriceService.getText().toString());
                mDatabase.child(j+"").setValue(service);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
