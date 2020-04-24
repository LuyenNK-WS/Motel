package com.luyennk.motel.Adapter_Management;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luyennk.motel.DTOs.Contract;
import com.luyennk.motel.Dialog.DetailContractDialog;
import com.luyennk.motel.Dialog.EditContractDialog;
import com.luyennk.motel.R;

import java.util.List;

public class AdapterContract extends RecyclerView.Adapter<AdapterContract.ViewHolder>  {

    private static final String TAG="AdapterContract";

    private List<Contract> contractList;
    private Context context;
    private LayoutInflater inflater;

    public AdapterContract(List<Contract> contractList, Context context) {
        this.contractList = contractList;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contractView=inflater.inflate(R.layout.item_contract,parent,false);
        return new ViewHolder(contractView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Contract contract=contractList.get(position);

        holder.txtIDRoom.setText("Phòng: "+contract.getIdRoom());
        holder.txtNameCustomer.setText("Họ tên: "+contract.getNameUse());
        holder.txtDeadline.setText("Hạn hợp đồng: "+contract.getDateCheckOut());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Clicked edit:"+position);

                EditContractDialog dialog=new EditContractDialog(context,contract);
                dialog.show();
            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Clicked Contract:"+position);
                DetailContractDialog dialog=new DetailContractDialog(context,contract.getIdRoom()
                        ,contract.getNameUse(),contract.getPersonList(),contract.getServices(),contract.getDateCheckIn(),contract.getDateCheckOut(),contract.getPriceRoom());
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contractList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtIDRoom;
        private TextView txtNameCustomer;
        private TextView txtDeadline;
        private ImageButton btnEdit;
        private LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIDRoom=itemView.findViewById(R.id.txtIDRoom);
            txtNameCustomer=itemView.findViewById(R.id.txtNameCustomer);
            txtDeadline=itemView.findViewById(R.id.txtDeadline);
            btnEdit=itemView.findViewById(R.id.btnEdit);
            item=itemView.findViewById(R.id.itemContract);
        }
    }
}
