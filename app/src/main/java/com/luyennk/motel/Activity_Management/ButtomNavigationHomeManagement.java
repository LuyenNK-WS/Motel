package com.luyennk.motel.Activity_Management;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.luyennk.motel.FragmentManagement.AccountFragment;
import com.luyennk.motel.FragmentManagement.ElectricWaterFragment;
import com.luyennk.motel.FragmentManagement.HomeFragment;
import com.luyennk.motel.FragmentManagement.NotificationAppFragment;
import com.luyennk.motel.R;

public class ButtomNavigationHomeManagement extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_home_management);
        initView();
    }

    private void initView(){
        bottomNavigationView=findViewById(R.id.bnvHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());

    }

    private void loadFragment(Fragment frg){
        if (frg!=null){
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content,frg).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment=null;

        switch (menuItem.getItemId()){
            case R.id.nav_Home:
                fragment =new HomeFragment();
                break;
            case R.id.nav_Electric_Water:
                fragment=new ElectricWaterFragment();
                break;
            case R.id.nav_Account:
                fragment=new AccountFragment();
                break;
            case R.id.nav_Notification_App:
                fragment=new NotificationAppFragment();
                break;
            default:
                return false;
        }

        loadFragment(fragment);
        return false;
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
