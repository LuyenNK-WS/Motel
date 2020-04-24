package com.luyennk.motel.FragmentCustomer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.luyennk.motel.Dialog.MenuAccountDialog;
import com.luyennk.motel.R;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private static final String TAG="AccountFragment";

    private TextView txtFullName;
    private TextView txtPhoneNumber;
    private TextView txtEmail;

    private ImageView btnMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_account_customer, container, false);

        txtFullName = RootView.findViewById(R.id.txtNameAccount);
        txtPhoneNumber =RootView.findViewById(R.id.txtPhoneNumBer);
        txtEmail = RootView.findViewById(R.id.txtEmail);

        return RootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView();
        getDataInPreferences();
    }

    private void initView(){
        txtFullName=getActivity().findViewById(R.id.txtNameAccount);
        txtPhoneNumber=getActivity().findViewById(R.id.txtPhoneNumBer);
        txtEmail=getActivity().findViewById(R.id.txtEmail);

        btnMenu = getView().findViewById(R.id.btnMenu);

        btnMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMenu:
                MenuAccountDialog dialog=new MenuAccountDialog(getActivity());
                dialog.show();
                break;
        }
    }

    private void getDataInPreferences(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("UserInfor", Context.MODE_PRIVATE);

        String fullName=sharedPreferences.getString("fullName","Full name");
        String phoneNumber=sharedPreferences.getString("phoneNumber","Phone number");
        String mail=sharedPreferences.getString("mail","Mail");

        txtFullName.setText(fullName);
        txtPhoneNumber.setText(phoneNumber);
        txtEmail.setText(mail);
    }
}
