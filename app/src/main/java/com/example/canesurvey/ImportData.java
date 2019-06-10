package com.example.canesurvey;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.canesurvey.Async.GetAllGrowerTask;
import com.example.canesurvey.Async.GetAllIrrigationTask;
import com.example.canesurvey.Async.GetAllPlantationTask;
import com.example.canesurvey.Async.GetAllVillageTask;
import com.example.canesurvey.Async.GetAllvarietyTask;
import com.example.canesurvey.Comman.CommanData;
import com.example.canesurvey.Repository.ITaskComplete;

public class ImportData extends Fragment implements View.OnClickListener, ITaskComplete {

    private View pageview;
    public String TAG="IMPORT EXPORT DATA";
    AlertDialog dialog;
    View clickedButton;
    // controls object
    Button mImportAll,mImportVariety,mImportVillage,mImportgrower,mImportirrigation,mImportPlantation,mExportSurvey,mExportgrowerupdation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pageview= inflater.inflate(R.layout.fragment_import_data, container, false);

        GetControls(pageview);
        SetClickListener();

        return pageview;
    }

    private void SetClickListener() {
        mImportAll.setOnClickListener(this);
        mImportVariety.setOnClickListener(this);
        mImportVillage.setOnClickListener(this);
        mImportgrower.setOnClickListener(this);
        mImportirrigation.setOnClickListener(this);
        mImportPlantation.setOnClickListener(this);
        mExportSurvey.setOnClickListener(this);
        mExportgrowerupdation.setOnClickListener(this);
    }

    private void GetControls(View view) {
        mImportAll=view.findViewById(R.id.cImportall);
        mImportVariety=view.findViewById(R.id.cimportvariety);
        mImportVillage=view.findViewById(R.id.cimportvillage);
        mImportgrower=view.findViewById(R.id.cimportgrower);
        mImportirrigation=view.findViewById(R.id.cimportirrigation);
        mImportPlantation=view.findViewById(R.id.cimportplantation);
        mExportSurvey=view.findViewById(R.id.cexportsurvey);
        mExportgrowerupdation=view.findViewById(R.id.cexportgrowerupdation);
    }

    @Override
    public void onClick(View view) {
        //Log.i("checking",view.getTransitionName());
        clickedButton=view;
        CommanData.ShowWaitDialog(this.getContext(),this);
        if(view==mImportAll)
        {

        }
        else if(view==mImportVariety)
        {

            GetAllvarietyTask task=new GetAllvarietyTask(this);
            task.execute();
        } else if(view==mImportVillage)
        {

            GetAllVillageTask task=new GetAllVillageTask(this);
            task.execute();
        } else if(view==mImportirrigation)
        {

            GetAllIrrigationTask task=new GetAllIrrigationTask(this);
            task.execute();
        } else if(view==mImportPlantation)
        {

            GetAllPlantationTask task=new GetAllPlantationTask(this);
            task.execute();
        } else if(view==mImportgrower)
        {

            GetAllGrowerTask task=new GetAllGrowerTask(this);
            task.execute();
        }

    }

    @Override
    public <E> void OnTaskComplete(boolean success, E returnobj) {

        CommanData.CloseWaitDialog();
        Toast.makeText(this.getContext(),returnobj.toString(),Toast.LENGTH_SHORT).show();

        if(clickedButton==mImportVariety)
        {
        }
    }
}
