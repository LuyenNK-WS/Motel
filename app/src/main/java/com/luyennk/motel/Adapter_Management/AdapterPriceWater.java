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

import com.luyennk.motel.DTOs.UpdatePriceWater;
import com.luyennk.motel.R;

import java.text.NumberFormat;
import java.util.List;

public class AdapterPriceWater extends RecyclerView.Adapter<AdapterPriceWater.ViewHolder>{

    private static final String TAG="AdapterPriceWater";

    private List<UpdatePriceWater> updatePriceElectricList;
    private Context context;
    private LayoutInflater inflater;

    public AdapterPriceWater(List<UpdatePriceWater> updatePriceWaters, Context context) {
        this.updatePriceElectricList = updatePriceWaters;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View updatePriceElectricView=inflater.inflate(R.layout.item_price_water,parent,false);
        return new ViewHolder(updatePriceElectricView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        NumberFormat numberFormat=NumberFormat.getInstance();
        UpdatePriceWater updatePriceWater=updatePriceElectricList.get(position);

        holder.txtTopLimit.setText("Hạn mức trên: "+updatePriceWater.getTopLimit());
        holder.txtLowerLimit.setText("Hạn mức dưới: "+updatePriceWater.getLowerLimitl());
        holder.txtPriceWater.setText("Giá nước: "+numberFormat.format(Double.parseDouble(updatePriceWater.getPriceWater()))+" vnd/khối");

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"clicked :"+position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return updatePriceElectricList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTopLimit;
        private TextView txtLowerLimit;
        private TextView txtPriceWater;
        private LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTopLimit=itemView.findViewById(R.id.txtTopLimit);
            txtLowerLimit=itemView.findViewById(R.id.txtLowerLimit);
            txtPriceWater=itemView.findViewById(R.id.txtPriceWater);
            item=itemView.findViewById(R.id.itemPriceWater);
        }
    }
}
