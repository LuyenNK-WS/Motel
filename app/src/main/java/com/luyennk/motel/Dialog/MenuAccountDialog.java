package com.luyennk.motel.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.luyennk.motel.Activity_Management.EditAccountActivity;
import com.luyennk.motel.Activity_Management.LoginActivity;
import com.luyennk.motel.R;

import static androidx.core.content.ContextCompat.startActivity;

public class MenuAccountDialog extends Dialog{

    private Button btnEdit;
    private Button btnLogOut;
    private Button btnCancel;
    private ImageView imgAvatar;
    private TextView txtNameAccount;

    public MenuAccountDialog(@NonNull final Context context) {
        super(context);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        getWindow().getAttributes().windowAnimations= R.style.DialogAnimation;
        setContentView(R.layout.dialog_menu_account);

        initView();

        getDataSharedPreferences();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), EditAccountActivity.class);
                startActivity(getContext(),intent,null);
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,LoginActivity.class);
                startActivity(context,intent,null);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initView(){
        btnEdit=findViewById(R.id.btnEdit);
        btnLogOut=findViewById(R.id.btnLogOut);
        btnCancel=findViewById(R.id.btnCancel);

        imgAvatar=findViewById(R.id.imgAvatar);
        txtNameAccount=findViewById(R.id.txtNameAccountInMenu);
    }

    private void getDataSharedPreferences(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserInfor", Context.MODE_PRIVATE);

        String fullName=sharedPreferences.getString("fullName","Full Name");

        txtNameAccount.setText(fullName);
    }
}
