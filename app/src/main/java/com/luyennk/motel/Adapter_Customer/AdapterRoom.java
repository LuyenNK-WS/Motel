package com.luyennk.motel.Adapter_Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luyennk.motel.DTOs.Room;
import com.luyennk.motel.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterRoom extends RecyclerView.Adapter<AdapterRoom.ViewHolder> {
    private List<Room> roomList=new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public AdapterRoom(List<Room> roomList,Context context) {
        this.roomList = roomList;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_room,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NumberFormat numberFormat=NumberFormat.getInstance();
        Room room=roomList.get(position);
        holder.txtNameRoom.setText("Phòng: "+room.getIdRoom());
        holder.txtPriceRoom.setText("Giá phòng: "+numberFormat.format(Double.parseDouble(room.getPriceRoom())) +" vnd");
        holder.txtStatus.setText("Tình trang: "+room.getStatus());
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameRoom;
        private TextView txtPriceRoom;
        private TextView txtStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameRoom=itemView.findViewById(R.id.txtNameRoom);
            txtPriceRoom=itemView.findViewById(R.id.txtPriceRoom);
            txtStatus=itemView.findViewById(R.id.txtStatus);
        }
    }
}
