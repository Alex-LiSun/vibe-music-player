package com.example.vibemusicplayer.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vibemusicplayer.R;

public class ContactFragment extends Fragment {

    private Button phone;
    private Button message;
    private TextView number1;
    private TextView number2;

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        phone = view.findViewById(R.id.button_call);
        message = view.findViewById(R.id.button_send);
        number1 = view.findViewById(R.id.info1);
        number2 = view.findViewById(R.id.info2);

        // 拨打电话
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = number1.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });

        // 发送短信
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = number2.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + number));
                intent.putExtra("sms_body", "反馈内容：");
                startActivity(intent);
            }
        });

        return view;
    }

}