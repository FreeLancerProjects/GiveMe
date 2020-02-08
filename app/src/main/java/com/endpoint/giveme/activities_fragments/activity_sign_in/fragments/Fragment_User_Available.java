package com.endpoint.giveme.activities_fragments.activity_sign_in.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.endpoint.giveme.R;
import com.endpoint.giveme.activities_fragments.activity_sign_in.activity.SignInActivity;

import java.util.Locale;

import io.paperdb.Paper;

public class Fragment_User_Available extends Fragment {
    private static String TAG = "TAG";
    private LinearLayout ll_back;
    private TextView tvState;
    private ImageView arrow;
    private SignInActivity activity;
    private String lang;
    private int state_available;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_available,container,false);
        initView(view);
        return view;
    }

    public static Fragment_User_Available newInstance(int state_available)
    {
        Fragment_User_Available fragment_user_available = new Fragment_User_Available();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG,state_available);
        fragment_user_available.setArguments(bundle);
        return fragment_user_available;
    }
    private void initView(View view) {
        activity = (SignInActivity) getActivity();
        Paper.init(activity);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());

        tvState = view.findViewById(R.id.tvState);
        arrow = view.findViewById(R.id.arrow);
        ll_back = view.findViewById(R.id.ll_back);

        if (lang.equals("ar"))
        {
            arrow.setRotation(180.0f);
        }



        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            state_available = bundle.getInt(TAG);

            if (state_available==1)
            {
                tvState.setText(getString(R.string.user_dele));
            }else if (state_available==2)
            {
                tvState.setText(getString(R.string.user_bloked));

            }
        }


        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.Back();
            }
        });


    }


}
