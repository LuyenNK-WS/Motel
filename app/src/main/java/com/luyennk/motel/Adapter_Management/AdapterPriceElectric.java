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

import com.luyennk.motel.DTOs.UpdatePriceElectric;
import com.luyennk.motel.R;

import java.text.NumberFormat;
import java.util.List;

public class AdapterPriceElectric extends RecyclerView.Adapter<AdapterPriceElectric.ViewHolder>{

    private static final String TAG="AdapterPriceElectric";

    private List<UpdatePriceElectric> updatePriceElectricList;
    private Context context;
    private LayoutInflater inflater;

    public AdapterPriceElectric(List<UpdatePriceElectric> updatePriceElectrics, Context context) {
        this.updatePriceElectricList = updatePriceElectrics;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View updatePriceElectricView=inflater.inflate(R.layout.item_price_electric,parent,false);
        return new ViewHolder(updatePriceElectricView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        NumberFormat numberFormat=NumberFormat.getInstance();
        UpdatePriceElectric updatePriceElectric =updatePriceElectricList.get(position);

        holder.txtTopLimit.setText("Hạn mức trên: "+ updatePriceElectric.getTopLimit());
        holder.txtLowerLimit.setText("Hạn mức dưới: "+ updatePriceElectric.getLowerLimitl());
        holder.txtPriceElectric.setText("Giá điện: "+ numberFormat.format(Double.parseDouble(updatePriceElectric.getPriceElectric()))+" vnd/kwh(số)");

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
        private TextView txtPriceElectric;
        private LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTopLimit=itemView.findViewById(R.id.txtTopLimit);
            txtLowerLimit=itemView.findViewById(R.id.txtLowerLimit);
            txtPriceElectric=itemView.findViewById(R.id.txtPriceElectric);
            item=itemView.findViewById(R.id.itemPriceElectric);
        }
    }
}
