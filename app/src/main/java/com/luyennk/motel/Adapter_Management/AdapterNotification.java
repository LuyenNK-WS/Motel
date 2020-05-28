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

import com.luyennk.motel.DTOs.Notification;
import com.luyennk.motel.R;

import java.util.List;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ViewHolder> {
    private static final String TAG="AdapterNotification";
    private List<Notification> notifications;
    private Context context;
    private LayoutInflater inflater;

    public AdapterNotification(List<Notification> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.item_notification,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification=notifications.get(position);
        holder.txtNotification.setText(notification.getNotification());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNotification;
        private LinearLayout item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNotification=itemView.findViewById(R.id.txtNotification);
            item=itemView.findViewById(R.id.item);
        }
    }
}
