package com.ycjt.ycim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.ycjt.ycim.R;
import com.hyphenate.easeui.chat.base.ChatHelper;
import com.hyphenate.easeui.chat.interfaces.OnCallBackListener;

public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button logoutButton = (Button) getView().findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ChatHelper.getInstance().logout(false, new OnCallBackListener() {

                    @Override
                    public void onSuccess(Object o) {
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }

                    @Override
                    public void onError(Object o, String error) {

                    }
                });
            }
        });
    }
}
