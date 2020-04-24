package com.luyennk.motel.Adapter_Management;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luyennk.motel.DTOs.ElectricWater;
import com.luyennk.motel.DTOs.Room;
import com.luyennk.motel.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterUpdateNumberElectricWater extends RecyclerView.Adapter<AdapterUpdateNumberElectricWater.ViewHolder> {

    private static final String TAG="AdapterUpdateNumberElectricWater";

    private List<Room> roomList;
    private Context context;
    private LayoutInflater inflater;

    public List<ElectricWater> electricWaterList=new ArrayList<>();

    public AdapterUpdateNumberElectricWater(List<Room> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_electric_water,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Room room=roomList.get(position);
        holder.txtNameRoom.setText("Phòng: "+room.getIdRoom());
        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                if (holder.edtNumberElectric.getText().toString().equals("") || holder.edtNumberWater.getText().toString().equals("")){
                    Toast.makeText(context,"Bạn cần nhập đủ thông tin",Toast.LENGTH_LONG).show();
                }else {
                    Log.d(TAG, "Phòng: " + room.getIdRoom() + "Số điện: " + holder.edtNumberElectric.getText().toString());
                    electricWaterList.add(new ElectricWater(holder.edtNumberElectric.getText().toString(),holder.edtNumberWater.getText().toString()));
                    holder.edtNumberWater.setEnabled(false);
                    holder.edtNumberElectric.setEnabled(false);
                    holder.btnSave.setEnabled(false);
                    holder.txtSave.setTextColor(300);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameRoom;
        private LinearLayout item;
        private LinearLayout btnSave;
        private EditText edtNumberElectric;
        private EditText edtNumberWater;
        private TextView txtSave;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameRoom=itemView.findViewById(R.id.txtNameRoom);
            item=itemView.findViewById(R.id.itemUpdateNumberElectricWater);
            btnSave=itemView.findViewById(R.id.btnSave);
            edtNumberElectric=itemView.findViewById(R.id.edtNumberElectric);
            edtNumberWater=itemView.findViewById(R.id.edtNumberWater);
            txtSave=itemView.findViewById(R.id.txtSave);
        }
    }
}
