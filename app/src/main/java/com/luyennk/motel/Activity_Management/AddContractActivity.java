package com.luyennk.motel.Activity_Management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.DTOs.Contract;
import com.luyennk.motel.DTOs.Person;
import com.luyennk.motel.DTOs.Room;
import com.luyennk.motel.DTOs.Service;
import com.luyennk.motel.Dialog.AddPersonDialog;
import com.luyennk.motel.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddContractActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "AddContractActivity";

    private ImageView btnBack;
    private EditText edtFullName;
    private Spinner spnNameRoom;
    private Spinner spnNameService;
    private TextView txtNameService;
    private EditText edtDateOfCheckIn;
    private EditText edtDateOfCheckOut;
    private EditText edtPriceRoom;
    private Button btnCreateContract;
    private ImageView btnEdit;
    private ImageView btnEdit1;
    private ImageView btnEdit2;
    private ImageView btnEdit3;
    private ImageButton btnDelete;
    private TextView btnAddPerson;

    private DatabaseReference mData;
    private String idUser;
    private String idRoom;

    final List<String> idService = new ArrayList<>();
    final List<Service> serviceCheck = new ArrayList<>();
    private List<Person> personList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contract);
        initView();
        autoPushData();
        getDataAndPushSpinner();
    }

    private void initView() {
        btnBack = findViewById(R.id.btnBack);
        edtFullName = findViewById(R.id.edtFullName);
        spnNameRoom = findViewById(R.id.spinnerIDRoom);
        spnNameService = findViewById(R.id.spinnerNameService);
        txtNameService = findViewById(R.id.txtNameService);
        edtDateOfCheckIn = findViewById(R.id.edtDateOfCheckIn);
        edtDateOfCheckOut = findViewById(R.id.edtDateOfCheckOut);
        edtPriceRoom = findViewById(R.id.edtPriceRoom);
        btnCreateContract = findViewById(R.id.btnCreateContract);
        btnEdit = findViewById(R.id.btnEdit);
        btnEdit1 = findViewById(R.id.btnEdit1);
        btnEdit2 = findViewById(R.id.btnEdit2);
        btnEdit3 = findViewById(R.id.btnEdit3);
        btnDelete = findViewById(R.id.btnDelete);
        btnAddPerson = findViewById(R.id.btnAddPerson);

        btnBack.setOnClickListener(this);
        btnCreateContract.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnEdit1.setOnClickListener(this);
        btnEdit2.setOnClickListener(this);
        btnEdit3.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnAddPerson.setOnClickListener(this);

        edtFullName.setEnabled(false);
        edtPriceRoom.setEnabled(false);
        edtDateOfCheckOut.setEnabled(false);
        edtDateOfCheckIn.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                Intent intent = new Intent(this, CustomerActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnCreateContract:
                createContract();
                break;
            case R.id.btnEdit:
                edtFullName.setEnabled(true);
                break;
            case R.id.btnEdit1:
                edtDateOfCheckIn.setEnabled(true);
                break;
            case R.id.btnEdit2:
                edtDateOfCheckOut.setEnabled(true);
                break;
            case R.id.btnEdit3:
                edtPriceRoom.setEnabled(true);
                break;
            case R.id.btnDelete:
                deleteService();
                break;
            case R.id.btnAddPerson:
                AddPersonDialog dialog = new AddPersonDialog(this);
                dialog.show();
                break;
        }
    }

    private void createContract() {
        // sửa tình trạng phòng
        DatabaseReference mDataRoom = FirebaseDatabase.getInstance().getReference().child("Room");

        mDataRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String, Object> childUpdate = new HashMap<>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    if (idRoom.equals(item.getValue(Room.class).getIdRoom()) && item.getValue(Room.class).getStatus().equals("Trống")) {
                        Room room = item.getValue(Room.class);
                        Room roomUpdate = new Room(room.getIdRoom(), room.getAcreage(), "Đã cho thuê", room.getPriceRoom());
                        childUpdate.put("Room/" + idRoom, roomUpdate);
                        //Cập nhật lại DB
                        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdate);

                        //create contract
                        final ArrayList<Contract> contractList = new ArrayList<>();

                        mData = FirebaseDatabase.getInstance().getReference().child("Contract");

                        mData.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                personList = AddPersonDialog.personList;
                                Log.d(TAG, "Số lượng person: " + personList.size());
                                final Contract contract = new Contract(idRoom, idUser, edtFullName.getText().toString(), personList, idRoom, serviceCheck, edtDateOfCheckIn.getText().toString(),
                                        edtDateOfCheckOut.getText().toString(), edtPriceRoom.getText().toString());
                                mData.child(idRoom).setValue(contract);

                                Toast.makeText(AddContractActivity.this, "Tạo hợp đồng thành công", Toast.LENGTH_LONG).show();

                                Intent intent=new Intent(AddContractActivity.this,CustomerActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Toast.makeText(AddContractActivity.this, "Phòng này đã có người", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void autoPushData() {
        String dateFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateFormat = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        }

        final String dateCheckIn = dateFormat.substring(6) + "/" + dateFormat.substring(4, 6) + "/" + dateFormat.substring(0, 4);
        String dateCheckOut = dateFormat.substring(6) + "/" + dateFormat.substring(4, 6) + "/" + (Integer.parseInt(dateFormat.substring(0, 4)) + 1);

        Intent intent = getIntent();
        idUser = intent.getStringExtra("idUser");
        edtFullName.setText(intent.getStringExtra("nameUser"));
        edtDateOfCheckIn.setText(dateCheckIn);
        edtDateOfCheckOut.setText(dateCheckOut);
    }

    private void getDataAndPushSpinner() {
        //đẩy dữ liệu vào spinner Room
        DatabaseReference mDataRoom = FirebaseDatabase.getInstance().getReference().child("Room");

        final List<String> idRoomPushSpinnerList = new ArrayList<>();
        final List<Room> roomList = new ArrayList<>();

        mDataRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    roomList.add(item.getValue(Room.class));
                }

                for (Room item : roomList) {
                    idRoomPushSpinnerList.add(item.getIdRoom());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddContractActivity.this, android.R.layout.simple_spinner_item, idRoomPushSpinnerList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnNameRoom.setAdapter(adapter);
                spnNameRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int i = 0;
                        for (Room item : roomList) {
                            if (i == position) {
                                edtPriceRoom.setText(item.getPriceRoom());
                                idRoom = item.getIdRoom();
                            }
                            i++;
                        }
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

        // đẩy dữ liệu vào spinner Service
        DatabaseReference mDataService = FirebaseDatabase.getInstance().getReference().child("Service");
        final List<Service> serviceList = new ArrayList<>();
        final List<String> nameServiceList = new ArrayList<>();

        mDataService.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    serviceList.add(item.getValue(Service.class));
                }

                for (Service item : serviceList) {
                    nameServiceList.add(item.getNameService());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddContractActivity.this, android.R.layout.simple_spinner_item, nameServiceList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnNameService.setAdapter(adapter);
                spnNameService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Thêm dữ liệu vào List idService
                        String name = "";
                        idService.add(serviceList.get(position).getIdService());
                        serviceCheck.add(serviceList.get(position));
                        for (Service item : serviceCheck) name += item.getNameService() + "\n";
                        txtNameService.setText(name);
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

    private void deleteService() {
        serviceCheck.removeAll(serviceCheck);
        txtNameService.setText("");
    }
}
