package com.luyennk.motel.Adapter_Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luyennk.motel.DTOs.UpdatePriceElectric;
import com.luyennk.motel.R;

import java.text.NumberFormat;
import java.util.List;

public class AdapterPriceElectric extends RecyclerView.Adapter<AdapterPriceElectric.ViewHolder> {
    private List<UpdatePriceElectric> priceElectrics;
    private Context context;
    private LayoutInflater inflater;

    public AdapterPriceElectric(List<UpdatePriceElectric> updatePriceElectrics, Context context) {
        this.priceElectrics = updatePriceElectrics;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_price_electric,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NumberFormat numberFormat=NumberFormat.getInstance();
        UpdatePriceElectric priceElectric=priceElectrics.get(position);

        holder.txtTopLimit.setText("Hạn mức đầu: "+priceElectric.getTopLimit());
        holder.txtLowerLimit.setText("Hạn mức cuối: "+priceElectric.getLowerLimitl());
        holder.txtPriceElectric.setText("Giá điện: "+numberFormat.format(Double.parseDouble(priceElectric.getPriceElectric()))+" vnd/kwh");
    }

    @Override
    public int getItemCount() {
        return priceElectrics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTopLimit;
        private TextView txtLowerLimit;
        private TextView txtPriceElectric;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTopLimit=itemView.findViewById(R.id.txtTopLimit);
            txtLowerLimit=itemView.findViewById(R.id.txtLowerLimit);
            txtPriceElectric=itemView.findViewById(R.id.txtPriceElectric);
        }
    }
}
