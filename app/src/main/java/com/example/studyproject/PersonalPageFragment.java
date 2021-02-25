package com.example.studyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PersonalPageFragment extends Fragment {
    Button bt_personal;
    Button bt_profileEdit;
    EditText personalName, personalNick;
    TextView tv_pname, tv_pnick;
    String myname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        //personalName = (EditText)view.findViewById(R.id.et_pname);
        personalNick = (EditText)view.findViewById(R.id.et_pnick);
        bt_personal = (Button)view.findViewById(R.id.bt_save);
        tv_pname = (TextView) view.findViewById(R.id.tv_pname);
        tv_pnick = (TextView) view.findViewById(R.id.tv_pnick);
        bt_profileEdit = view.findViewById(R.id.profileEdit);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            myname = user.getDisplayName();
            // Check if user's email is verified
        }

        tv_pname.setText("이름: "+myname);

        bt_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalNick.setText(personalNick.getText());
            }
        });
        bt_profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ProfileEditActivity.class));
            }
        });

        return view;
    }

}
