package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.Activity_Management.ContractActivity;
import com.luyennk.motel.DTOs.Contract;
import com.luyennk.motel.DTOs.Room;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.core.content.ContextCompat.startActivity;

public class EditContractDialog extends Dialog {

    private static final String TAG = "EditContractDialog";

    private Spinner snIDRoom;
    private EditText edtDateOfCheckIn;
    private EditText edtDateOfCheckOut;
    private Button btnEdit;
    private Button btnCancel;
    private String idRoom = null;

    public EditContractDialog(@NonNull Context context, Contract contract) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_edit_contract);
        initView(contract);
        getDataAndPushSpinner();
    }

    private void initView(final Contract contract) {
        snIDRoom = findViewById(R.id.snIDRoom);
        edtDateOfCheckIn = findViewById(R.id.edtDateCheckIn);
        edtDateOfCheckOut = findViewById(R.id.edtDateCheckOut);
        btnEdit = findViewById(R.id.btnEdit);
        btnCancel = findViewById(R.id.btnCancel);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContract(contract);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void getDataAndPushSpinner() {
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference().child("Room");
        final List<String> idRoomList = new ArrayList<>();
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    idRoomList.add(item.getValue(Room.class).getIdRoom());
                    Log.d(TAG, item.getValue(Room.class).getIdRoom());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, idRoomList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                snIDRoom.setAdapter(adapter);
                snIDRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(TAG, idRoomList.get(position));
                        idRoom = idRoomList.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateContract(Contract contract) {
        Map<String, Object> childUpdate = new HashMap<>();

        //Thêm object cần update
        Contract useUpdate = new Contract(contract.getIdContract(), contract.getIdUse(), contract.getNameUse(),contract.getPersonList(), idRoom,
                contract.getServices(),edtDateOfCheckIn.getText().toString(), edtDateOfCheckOut.getText().toString(),contract.getPriceRoom());

        childUpdate.put("Contract/" + contract.getIdContract(), useUpdate);

        //Cập nhật lại DB
        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdate);

        Toast.makeText(getContext(),"Sửa hợp đồng thành công",Toast.LENGTH_LONG).show();

        dismiss();
        Intent intent=new Intent(getContext(), ContractActivity.class);
        startActivity(getContext(),intent,null );
    }
}
