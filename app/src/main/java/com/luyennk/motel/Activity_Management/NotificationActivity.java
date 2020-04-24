package com.luyennk.motel.Activity_Management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.luyennk.motel.R;

public class NotificationActivity extends Activity implements View.OnClickListener {

    private ImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initview();
    }

    private void initview(){
        btnBack=findViewById(R.id.btnBack);

        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                Intent intent = new Intent(NotificationActivity.this, ButtomNavigationHomeManagement.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
