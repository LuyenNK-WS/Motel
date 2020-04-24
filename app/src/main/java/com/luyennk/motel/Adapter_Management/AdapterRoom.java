package com.luyennk.motel.Adapter_Management;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luyennk.motel.DTOs.Room;
import com.luyennk.motel.Dialog.DetailRoomDialog;
import com.luyennk.motel.R;

import java.text.NumberFormat;
import java.util.List;

public class AdapterRoom extends RecyclerView.Adapter<AdapterRoom.ViewHolder> {

    private static final String TAG="AdapterRoom";

    private List<Room> roomList;
    private Context context;
    private LayoutInflater inflater;

    public AdapterRoom(Context context, List<Room> roomList) {
        this.roomList = roomList;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View roomView=inflater.inflate(R.layout.item_room,parent,false);
        return new ViewHolder(roomView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        NumberFormat numberFormat=NumberFormat.getInstance();
        final Room room=roomList.get(position);

        holder.txtNameRoom.setText("Phòng: "+room.getIdRoom());
        holder.txtStatus.setText("Tình trạng: "+room.getStatus());
        holder.txtPriceRoom.setText("Giá phòng: "+numberFormat.format(Double.parseDouble(room.getPriceRoom()))+" vnd");

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Click");
                DetailRoomDialog dialog=new DetailRoomDialog(context,room.getIdRoom(),room.getAcreage(),room.getStatus(),room.getPriceRoom());
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNameRoom;
        private TextView txtStatus;
        private TextView txtPriceRoom;
        private LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameRoom=itemView.findViewById(R.id.txtNameRoom);
            txtPriceRoom=itemView.findViewById(R.id.txtPriceRoom);
            txtStatus=itemView.findViewById(R.id.txtStatus);
            item=itemView.findViewById(R.id.itemRoom);
        }
    }
}
