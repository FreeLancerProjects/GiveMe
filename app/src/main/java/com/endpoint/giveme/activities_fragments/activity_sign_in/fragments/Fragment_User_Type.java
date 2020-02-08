package com.endpoint.giveme.activities_fragments.activity_sign_in.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.endpoint.giveme.R;
import com.endpoint.giveme.activities_fragments.activity_sign_in.activity.SignInActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.paperdb.Paper;

public class Fragment_User_Type extends Fragment {
    private RadioButton rb_courier,rb_client;
    private FloatingActionButton fab;
    private int selected_type = 1;
    private SignInActivity activity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_type,container,false);
        initView(view);
        return view;
    }

    public static Fragment_User_Type newInstance()
    {
        return new Fragment_User_Type();
    }
    private void initView(View view) {
        activity = (SignInActivity) getActivity();
        Paper.init(activity);


        rb_client = view.findViewById(R.id.rb_client);
        rb_courier = view.findViewById(R.id.rb_courier);
        fab = view.findViewById(R.id.fab);

        rb_courier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_type = 2;
            }
        });

        rb_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected_type = 1;



            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected_type == 1)
                {
                    activity.DisplayFragmentClientSignUp();
                }else
                    {
                        activity.DisplayFragmentDelegateSignUp();
                    }

            }
        });
    }
}
