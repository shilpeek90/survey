package com.example.canesurvey;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.canesurvey.Comman.CommanData;


public class BlankFragment extends Fragment {

    public String Tag = "DashBoard";
    TextView mImei,mOpratorcode,mOpratorName;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_blank, container, false);

        mImei=view.findViewById(R.id.cimei);
        mOpratorcode=view.findViewById(R.id.copcode);
        mOpratorName=view.findViewById(R.id.copname);

        DisplayOpratorDetials();

        return view;
    }

    private void DisplayOpratorDetials() {
        if(CommanData.oprator==null)
        {
            CommanData.oprator= CommanData.conn.oprator.getOprator();
        }
        mImei.setText("IMEI NO :"+CommanData.oprator.getImei());
        mOpratorcode.setText("COde :"+CommanData.oprator.getCode());
        mOpratorName.setText("Name :"+CommanData.oprator.getName());
    }

    // TODO: Rename method, update argument and hook method into UI event

}
