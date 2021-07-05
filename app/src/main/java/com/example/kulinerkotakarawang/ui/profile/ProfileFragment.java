package com.example.kulinerkotakarawang.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kulinerkotakarawang.Login;
import com.example.kulinerkotakarawang.R;
import com.example.kulinerkotakarawang.Registrasi;
import com.example.kulinerkotakarawang.model.LapakModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ProfileFragment extends Fragment {

    private Button regist, login;
    private LinearLayout before, after;
    private LapakModel lm = new LapakModel();
    private ImageView profile;
    private TextView lapakname, address;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        _init(root);
        _prep();

        return root;
    }

    private void _init(View root){
        regist = root.findViewById(R.id.regist);
        login = root.findViewById(R.id.login);
        before = root.findViewById(R.id.before);
        after = root.findViewById(R.id.after);
        profile = root.findViewById(R.id.imgprofile);
        lapakname = root.findViewById(R.id.lapakname);
        address = root.findViewById(R.id.address);
    }

    private void _prep(){
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Registrasi.class));
                getActivity().finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });

        if (TextUtils.isEmpty(lm.getNohp())){
            before.setVisibility(View.VISIBLE);
            after.setVisibility(View.GONE);
        } else {
            before.setVisibility(View.GONE);
            after.setVisibility(View.VISIBLE);
            lapakname.setText(lm.getNama());
            address.setText(lm.getAlamat());
            if (!TextUtils.isEmpty(lm.getGambar())){
                Glide.with(getActivity())
                        .load(lm.getGambar())
                        .into(profile);
            }
        }
    }
}
