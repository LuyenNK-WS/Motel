package com.luyennk.motel.Activity_Customer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.luyennk.motel.FragmentCustomer.AccountFragment;
import com.luyennk.motel.FragmentCustomer.HomeFragment;
import com.luyennk.motel.FragmentManagement.NotificationAppFragment;
import com.luyennk.motel.R;

public class ButtomNavigationHomeCustomer extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_home_customer);
        initView();
    }

    private void initView(){
        bottomNavigationView=findViewById(R.id.bnvHomeCustomer);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment frg=null;

        switch (menuItem.getItemId()){
            case R.id.nav_Home:
                frg=new HomeFragment();
                break;
            case R.id.nav_Account:
                frg=new AccountFragment();
                break;
            case R.id.nav_Notification_App:
                frg=new NotificationAppFragment();
                break;
            default:
                return false;
        }
        loadFragment(frg);
        return false;
    }

    private void loadFragment(Fragment fg){
        if (fg != null){
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content,fg).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences=getSharedPreferences("UserInfor",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("UserInfor");
        editor.apply();
    }
}
