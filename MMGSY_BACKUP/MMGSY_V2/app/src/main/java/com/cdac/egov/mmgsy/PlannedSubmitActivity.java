package com.cdac.egov.mmgsy;

import java.util.ArrayList;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import common.Constant;
import common.Notification;
import common.QMSHelper;
import common.QMSHelper.NotificationEnum;

import webServices.DownloadScheduleWebServices;
import webServices.UploadInspectionDetails;

import db.DAO.ImageDetailsDAO;
import db.DAO.LoginDetailsDAO;
import db.DAO.ScheduleDetailsDAO;
import db.DTO.ImageDetailsDTO;
import db.DTO.LoginDetailsDTO;
import db.DTO.ScheduleDetailsDTO;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class PlannedSubmitActivity extends AppBaseActivity  implements OnItemSelectedListener, OnClickListener{

	private PlannedSubmitActivity parent;
	private Spinner roadSpinner;
	private ArrayAdapter roadAdapter;
	private ArrayList<String> roadList;
	private ArrayList<ScheduleDetailsDTO> selectedScheduleDTOArrayList;
	private Button plannedUploadButton;
	private Button plannedCancelButton;
	private String uniqueCode;
	private String mode;
	private String status;
	private int entryMode;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = PlannedSubmitActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_planned_submit);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		roadList = new ArrayList<String>();
		roadList.add("Select Road");
		
		Intent intent = getIntent();
		mode = intent.getStringExtra("mode");
		if(mode.equalsIgnoreCase("unplanned")){
			status = "S";
			entryMode = 1;
		}else{
		
			status = "I";
			entryMode = 0;
		}
		
		selectedScheduleDTOArrayList = new ArrayList<ScheduleDetailsDTO>();
		ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
		//selectedScheduleDTOArrayList = scheduleDetailsDAOObj.getUnuploadedRecords(this, parent);
		selectedScheduleDTOArrayList = scheduleDetailsDAOObj.getAllScheduleDetails(this, parent, entryMode, status);
		for (ScheduleDetailsDTO scheduleDetailsDTO : selectedScheduleDTOArrayList) {
			roadList.add(scheduleDetailsDTO.getRoadName());
		}
		
		roadSpinner = (Spinner) findViewById(R.id.submitSpinnerRoadNameId);
		roadAdapter = new ArrayAdapter(this,R.layout.spinner_layout, roadList);
		roadSpinner.setAdapter(roadAdapter);
		roadSpinner.setOnItemSelectedListener(this);
		
		plannedUploadButton = (Button) findViewById(R.id.btnPlannedUploadId);
		plannedCancelButton = (Button) findViewById(R.id.btnPlannedUploadCancelId);
		plannedUploadButton.setOnClickListener(this);
		plannedCancelButton.setOnClickListener(this);
		
	}

	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long  arg3) {
	
		if(i == 0){
			return;
		}
		uniqueCode = selectedScheduleDTOArrayList.get(i-1).getUniqueCode();
		ImageDetailsDAO imageDetailsDAO = new ImageDetailsDAO();
		ArrayList<ImageDetailsDTO> imageDetailsDTOArrayList = new ArrayList<ImageDetailsDTO>();
		imageDetailsDTOArrayList = imageDetailsDAO.getImageDetails(uniqueCode, this, parent); 
		
		Intent intent = new Intent(PlannedSubmitActivity.this,ImageGalleryActivity.class);
		intent.putExtra("mode", mode);
		intent.putExtra("imageDetailsDTOArrayList", imageDetailsDTOArrayList);
		startActivity(intent);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.btnMainMenuLogoutId){
			logOut();
		}
		
	
		if(v.getId() == R.id.btnPlannedUploadId){
			
			if(roadSpinner.getSelectedItemPosition() == 0){
				Notification.showErrorMessage(NotificationEnum.selectRoad, this, parent);
				QMSHelper.blink(roadSpinner);
				return ;
			}
			
			   ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
			   ArrayList<ImageDetailsDTO> imageDetailsDTOArrayList = imageDetailsDAOObj.getImageDetails(uniqueCode,this,parent);
			   
			   Integer attachedImageCount = 0;
				 for (ImageDetailsDTO imageDetailsDTO : imageDetailsDTOArrayList) {
					if(imageDetailsDTO.getIsSelected() == 1){
						attachedImageCount++;
						
					}
					}		
		if(attachedImageCount >= Constant.minImage){
 	 
	 dialog = ProgressDialog.show(PlannedSubmitActivity.this, "", 
             "Uploading Starting. Please wait...", true);
		 
		 new UploadStartTask().execute();
		}
		else{
			Toast.makeText(PlannedSubmitActivity.this, "Please select minimum "+ Constant.minImage + " Images", Toast.LENGTH_LONG).show();	
		}
	}
		if(v.getId() == R.id.btnPlannedUploadCancelId){
			finish();
		}
		
	}
	
	@Override
	public void onBackPressed() {
	}
	
	public void logOut()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(parent);
		builder.setMessage("Are you sure to logout?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		          
				public void onClick(DialogInterface dialog, int id) {
						closeAllActivities();
					}
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           }
		       });
		
		AlertDialog alert = builder.create();
		alert.show();
	}

	
	private class UploadStartTask extends AsyncTask<Context, String, String> {
		
		
		@Override
		protected String doInBackground(Context... context) {
			//android.os.Debug.waitForDebugger();
			UploadInspectionDetails uploadInspectionDetailsObj = new UploadInspectionDetails();
			if(uploadInspectionDetailsObj.setInspectionDetails(uniqueCode, PlannedSubmitActivity.this, parent) == 1){
				ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
			 	scheduleDetailsDAOObj.updateStatus( uniqueCode, "U", parent, parent);
				Notification.showErrorMessage(NotificationEnum.uploadingStarted, PlannedSubmitActivity.this, parent);
			}
			dialog.dismiss();
			finish();
			return null;
		}
		
		
	}
	
}
