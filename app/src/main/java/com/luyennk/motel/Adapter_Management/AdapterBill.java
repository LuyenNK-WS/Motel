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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.DTOs.Bill;
import com.luyennk.motel.DTOs.BillDetail;
import com.luyennk.motel.Dialog.DetailBillDialog;
import com.luyennk.motel.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterBill extends RecyclerView.Adapter<AdapterBill.ViewHolder> {

    private static final String TAG="AdapterBill";

    private List<Bill> billList;
    private Context context;
    private LayoutInflater inflater;

    public AdapterBill(List<Bill> billList, Context context) {
        this.billList = billList;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View billView= inflater.inflate(R.layout.item_bill,parent,false);
        return new ViewHolder(billView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Bill bill = billList.get(position);
        final List<BillDetail> billDetail=new ArrayList<>();

        holder.txtIDRoom.setText("Phòng: "+bill.getIdRoom());
        holder.txtDate.setText("Ngày chốt: "+bill.getBillDate());

        DatabaseReference mData= FirebaseDatabase.getInstance().getReference().child("BillDetail");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double total=0;

                for (DataSnapshot item:dataSnapshot.getChildren()){
                    if (item.getValue(BillDetail.class).getIdBill().equals(bill.getIdBill())){
                       total = item.getValue(BillDetail.class).getTotal();
                       billDetail.add(item.getValue(BillDetail.class));
                    }

                }
                NumberFormat currentLocale = NumberFormat.getInstance();
                holder.txtTotalPrice.setText(currentLocale.format(total)+"vnd");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Clicked Bill: "+position);
                Log.d(TAG,"BillDetails Size: "+billDetail.size());
                DetailBillDialog dialog=new DetailBillDialog(context,billDetail.get(0));
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtIDRoom;
        private TextView txtTotalPrice;
        private TextView txtDate;
        private LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIDRoom=itemView.findViewById(R.id.txtNameRoom);
            txtTotalPrice=itemView.findViewById(R.id.txtTotal);
            txtDate=itemView.findViewById(R.id.txtDate);
            item=itemView.findViewById(R.id.itemBill);
        }
    }
}
