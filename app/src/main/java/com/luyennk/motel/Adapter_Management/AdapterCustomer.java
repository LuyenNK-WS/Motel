package com.luyennk.motel.Adapter_Management;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.DTOs.Contract;
import com.luyennk.motel.DTOs.Use;
import com.luyennk.motel.Dialog.DetailCustomerDialog;
import com.luyennk.motel.Dialog.MenuItemCustomerDialog;
import com.luyennk.motel.R;

import java.util.List;

public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.ViewHolder> {
    private static final String TAG="AdapterCustomer";
    private List<Use> useList;
    private Context context;
    private LayoutInflater inflater;

    public AdapterCustomer(List<Use> useList, Context context) {
        this.useList = useList;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customerView=inflater.inflate(R.layout.item_customer,parent,false);
        return new ViewHolder(customerView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Use use=useList.get(position);

        holder.txtNameUse.setText(use.getFullName());

        DatabaseReference mData = FirebaseDatabase.getInstance().getReference().child("Contract");
        mData.addValueEventListener(new ValueEventListener() {
            String idRoom ;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    if (use.getId().equals(item.getValue(Contract.class).getIdUse())){
                        idRoom = null;
                        idRoom = item.getValue(Contract.class).getIdRoom();
                    }
                }
                if (idRoom != null) {
                    holder.txtIDRoom.setText("Phòng: " + idRoom);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.txtAddress.setText(use.getAddress());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"clicked :"+position);
                DetailCustomerDialog dialog=new DetailCustomerDialog(context,use);
                dialog.show();
            }
        });
        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuItemCustomerDialog dialog=new MenuItemCustomerDialog(context,use,use.getId());
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return useList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameUse;
        private TextView txtAddress;
        private TextView txtIDRoom;
        private LinearLayout item;
        private ImageView btnMenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameUse=itemView.findViewById(R.id.txtFullName);
            txtIDRoom=itemView.findViewById(R.id.txtNameRoom);
            txtAddress =itemView.findViewById(R.id.txtAddress);
            item=itemView.findViewById(R.id.itemCustomer);
            btnMenu=itemView.findViewById(R.id.btnMenu);
        }
    }
}