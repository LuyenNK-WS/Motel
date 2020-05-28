package com.luyennk.motel.Activity_Management;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luyennk.motel.DTOs.BillDetail;
import com.luyennk.motel.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {
    private static final String TAG = "ChartActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        getData();
    }

    private void getData() {
        String dateFormat = null;
        final List<Double> totalBill = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateFormat = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        }
        String year = dateFormat.substring(0, 4);

        for (int i=1;i<=12;i++) {
            String row = String.format("%02d",i);
            DatabaseReference mData = FirebaseDatabase.getInstance().getReference("BillDetail").child(year + "/" + row);
            mData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    BarChart chart = findViewById(R.id.barchart);
                    Double totalMonth = 0.0 ;
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        totalMonth += item.getValue(BillDetail.class).getTotal();
                        Log.d(TAG,"abc: "+totalMonth);
                    }
                    totalBill.add(totalMonth);

                    List<BarEntry> NoOfEmp = new ArrayList();
                    int i=200;
                    for (Double item : totalBill){
                        NoOfEmp.add(new BarEntry(Float.parseFloat(i+200+""), Float.parseFloat(item+"")));
                    }
//
//                    NoOfEmp.add(new BarEntry(945f, 0));
//                    NoOfEmp.add(new BarEntry(1040f, (float) 2.5));
//                    NoOfEmp.add(new BarEntry(1133f, 2));
//                    NoOfEmp.add(new BarEntry(1240f, 3));
//                    NoOfEmp.add(new BarEntry(1350f, (float) 4.6));
//                    NoOfEmp.add(new BarEntry(1460f, 5));
//                    NoOfEmp.add(new BarEntry(1501f, 6));
//                    NoOfEmp.add(new BarEntry(1645f, (float) 7.2));
//                    NoOfEmp.add(new BarEntry(1578f, 8));
//                    NoOfEmp.add(new BarEntry(1695f, 9));

                    BarDataSet dataSet = new BarDataSet(NoOfEmp, "Các tháng trong năm(1-12)");
                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    BarData barData = new BarData(dataSet);
                    barData.setBarWidth(20f);

                    chart.setVisibility(View.VISIBLE);
                    chart.animateY(5000);
                    chart.setData(barData);
                    chart.setFitBars(true);

                    Description description = new Description();
                    description.setText("vnd");
                    chart.setDescription(description);
                    chart.invalidate();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
