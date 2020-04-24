package com.luyennk.motel.Adapter_Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luyennk.motel.DTOs.Service;
import com.luyennk.motel.R;

import java.text.NumberFormat;
import java.util.List;

public class AdapterService extends RecyclerView.Adapter<AdapterService.ViewHolder> {
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
        View view = inflater.inflate(R.layout.item_service,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service=serviceList.get(position);
        NumberFormat currentLocale = NumberFormat.getInstance();
        holder.txtNameService.setText("Tên dịch vụ: "+service.getNameService());
        holder.txtPriceService.setText("Giá: "+currentLocale.format(Double.parseDouble(service.getPriceService())) +" vnd");
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameService;
        private TextView txtPriceService;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameService=itemView.findViewById(R.id.txtNameService);
            txtPriceService=itemView.findViewById(R.id.txtPriceService);
        }
    }
}
