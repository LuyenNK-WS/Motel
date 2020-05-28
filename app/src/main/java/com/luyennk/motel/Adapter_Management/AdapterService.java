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

import com.luyennk.motel.DTOs.Service;
import com.luyennk.motel.R;

import java.text.NumberFormat;
import java.util.List;

public class AdapterService extends RecyclerView.Adapter<AdapterService.ViewHolder> {

    private static final String TAG="AdapterService";

    private List<Service> serviceList;
    private Context context;
    private LayoutInflater inflater;

    public AdapterService(List<Service> serviceList, Context context) {
        this.serviceList = serviceList;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View serviceView=inflater.inflate(R.layout.item_service,parent,false);
        return new ViewHolder(serviceView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        NumberFormat numberFormat=NumberFormat.getInstance();
        Service service= serviceList.get(position);

        holder.txtNameService.setText("Tên dịch vụ: "+service.getNameService());
        holder.txtPriceService.setText("Giá dịch vụ: "+numberFormat.format(Double.parseDouble(service.getPriceService()))+" vnd/tháng");

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"clicked :"+position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNameService;
        private TextView txtPriceService;
        private LinearLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameService=itemView.findViewById(R.id.txtNameService);
            txtPriceService=itemView.findViewById(R.id.txtPriceService);
            item=itemView.findViewById(R.id.itemService);
        }
    }
}
