package com.luyennk.motel.Adapter_Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luyennk.motel.DTOs.UpdatePriceWater;
import com.luyennk.motel.R;

import java.text.NumberFormat;
import java.util.List;

public class AdapterPriceWater extends RecyclerView.Adapter<AdapterPriceWater.ViewHolder> {
    private List<UpdatePriceWater> priceWaterList;
    private Context context;
    private LayoutInflater inflater;

    public AdapterPriceWater(List<UpdatePriceWater> priceWaterList, Context context) {
        this.priceWaterList = priceWaterList;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_price_water,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NumberFormat numberFormat=NumberFormat.getInstance();
        UpdatePriceWater updatePriceWater=priceWaterList.get(position);

        holder.txtTopLimit.setText("Hạn mức đầu: "+updatePriceWater.getTopLimit());
        holder.txtLowerLimit.setText("Hạn mức cuối: "+updatePriceWater.getLowerLimitl());
        holder.txtPriceWater.setText("Giá nước: "+ numberFormat.format(Double.parseDouble(updatePriceWater.getPriceWater())) +" vnd/khối");
    }

    @Override
    public int getItemCount() {
        return priceWaterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTopLimit;
        private TextView txtLowerLimit;
        private TextView txtPriceWater;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTopLimit=itemView.findViewById(R.id.txtTopLimit);
            txtLowerLimit=itemView.findViewById(R.id.txtLowerLimit);
            txtPriceWater=itemView.findViewById(R.id.txtPriceWater);
        }
    }
}
