package com.cdac.egov.mmgsy;

import java.util.ArrayList;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import common.Constant;
import common.Notification;
import common.QMSHelper;
import common.QMSHelper.NotificationEnum;

import db.DAO.ConfigDetailsDAO;
import db.DAO.ImageDetailsDAO;
import db.DAO.LoginDetailsDAO;
import db.DAO.ObservationDetailsDAO;
import db.DAO.ScheduleDetailsDAO;
import db.DTO.ConfigDetailsDTO;
import db.DTO.LoginDetailsDTO;
import db.DTO.ObservationDetailsDTO;
import db.DTO.ScheduleDetailsDTO;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class InpectionEntryActivity extends AppBaseActivity implements OnItemSelectedListener, OnClickListener{

	private ArrayList<ScheduleDetailsDTO> scheduleDetailsDTOArrayList, selectedScheduleDTOArrayList;
	private InpectionEntryActivity parent;
	private Spinner roadSpinner;
	private ArrayAdapter roadAdapter;
	private Button inspectionEntryForm;
	private Button inspectionEntryCancel;
	private ArrayList<String> roadList;
	private Integer entryMode;
	private String mode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = InpectionEntryActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inpection_entry);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		Intent intent = getIntent();
		mode = intent.getStringExtra("mode");
		
		String status;
		if(mode.equalsIgnoreCase("unplanned")){
			entryMode = 1;
			status = "I";
		}else{
			entryMode = 0;
			status = "T";
		}
		
		ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
		
		scheduleDetailsDTOArrayList =  scheduleDetailsDAOObj.getAllScheduleDetails(this, parent,entryMode,status);
		ImageDetailsDAO imageDetailsDAO = new ImageDetailsDAO();
		roadList = new ArrayList<String>();
		roadList.add("Select Road");
		selectedScheduleDTOArrayList = new ArrayList<ScheduleDetailsDTO>();
		for (ScheduleDetailsDTO scheduleDetailsDTO : scheduleDetailsDTOArrayList) {
			
			if(imageDetailsDAO.getImageDetailsCount(scheduleDetailsDTO.getUniqueCode(), this, parent) >= Constant.minImage){
				roadList.add(scheduleDetailsDTO.getRoadName());
				selectedScheduleDTOArrayList.add(scheduleDetailsDTO);
			}
			//roadList.add(scheduleDetailsDTO.getRoadName());
		}
		
		roadSpinner = (Spinner) findViewById(R.id.spinnerInspectionEntryRoadNameId);
		roadAdapter = new ArrayAdapter(this,R.layout.spinner_layout, roadList);
		roadSpinner.setAdapter(roadAdapter);
		roadSpinner.setOnItemSelectedListener(this);
		
		inspectionEntryForm = (Button) findViewById(R.id.btnInspectionEntryFormId);
		inspectionEntryForm.setOnClickListener(this);
		inspectionEntryCancel = (Button) findViewById(R.id.btnInspectionEntryCancelId);
		inspectionEntryCancel.setOnClickListener(this);
		
	}
	
	@Override
	public void onBackPressed() {
	}

	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inpection_entry, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
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
		
		if(v.getId() == R.id.btnInspectionEntryFormId){
	
		if(!validateInspectionEntry()){
			return;
		}
		
			
			
			 // form activity call
			 // Based on QmType & RoadStatus - call appropriate activity 
			 // For NQMs - (I)
			 // C - NqmCompletedGradingActivity
			 // P - NqmInProgressGradingActivity
			 // M - NqmMaintenanceGradingActivity
			 // L - NqmLSBGradingActivity
			 // For SQMs - (S)
			 // C - SqmCompletedGradingActivity
			 // P - SqmInProgressGradingActivity
			 // M - SqmMaintenanceGradingActivity
			 // L - SqmLSBGradingActivity
			 		
			
			LoginDetailsDAO loginDetailsDAO = new LoginDetailsDAO();
			LoginDetailsDTO loginDetailsDTO =  loginDetailsDAO.getLoginDetails(null, this);
			
			ScheduleDetailsDTO selectedRoadDetailsDTO = selectedScheduleDTOArrayList.get(roadSpinner.getSelectedItemPosition()-1);
			
			//update by prince paliwal 17/07/2017 (only 3 inspection allow per day) 
			
			//ObservationDetailsDAO observationDetailsDTO = new ObservationDetailsDAO();
			//Boolean valid = observationDetailsDTO.checkInspectionDetailsForPerDayValidation(this,selectedRoadDetailsDTO);
			
			/*if(valid == true)
			{
				Toast.makeText(this, "True", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(this, "false", Toast.LENGTH_SHORT).show();
			}*/
			
			
			//Added By Avinash..If user is SE=S   
			//If user is CE=I
			if(loginDetailsDTO.getQmType().equalsIgnoreCase("I") || loginDetailsDTO.getQmType().equalsIgnoreCase("C")) {
				
				// nqm form call 
				if(selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("C")){
					Intent intent = new Intent(InpectionEntryActivity.this, NqmCompletedGradingActivity.class);
					intent.putExtra("monitorName", loginDetailsDTO.getMonitorName());
					intent.putExtra("entryMode", mode);
					intent.putExtra("inpectionEntryRoadDTO", selectedRoadDetailsDTO);
					startActivity(intent);
					
				}else if(selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("P")){
					Intent intent = new Intent(InpectionEntryActivity.this, NqmInProgressGradingActivity.class);
					intent.putExtra("monitorName", loginDetailsDTO.getMonitorName());
					intent.putExtra("entryMode", mode);
					intent.putExtra("inpectionEntryRoadDTO", selectedRoadDetailsDTO);
					startActivity(intent);
					
					
				}else if(selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("M")){
					Intent intent = new Intent(InpectionEntryActivity.this, NqmMaintenanceGradingActivity.class);
					intent.putExtra("monitorName", loginDetailsDTO.getMonitorName());
					intent.putExtra("entryMode", mode);
					intent.putExtra("inpectionEntryRoadDTO", selectedRoadDetailsDTO);
					startActivity(intent);
					
				}else if(selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("LP")){
				 	Intent intent = new Intent(InpectionEntryActivity.this, SqmLSBInProgressGradingActivity.class);
					intent.putExtra("monitorName", loginDetailsDTO.getMonitorName());
					intent.putExtra("entryMode", mode);
					intent.putExtra("inpectionEntryRoadDTO", selectedRoadDetailsDTO);
					startActivity(intent);
				
				}
				else if(selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("LC") || selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("LM")){
				 	Intent intent = new Intent(InpectionEntryActivity.this, SqmLSBCompletedGradingActivity.class);
					intent.putExtra("monitorName", loginDetailsDTO.getMonitorName());
					intent.putExtra("entryMode", mode);
					intent.putExtra("inpectionEntryRoadDTO", selectedRoadDetailsDTO);
					startActivity(intent);
				
				}
				
				
			}else if(loginDetailsDTO.getQmType().equalsIgnoreCase("S") || loginDetailsDTO.getQmType().equalsIgnoreCase("P")){
				
				// sqm form call
				if(selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("C")){
					Intent intent = new Intent(InpectionEntryActivity.this, SqmCompletedGradingActivity.class);
					intent.putExtra("monitorName", loginDetailsDTO.getMonitorName());
					intent.putExtra("entryMode", mode);
					intent.putExtra("inpectionEntryRoadDTO", selectedRoadDetailsDTO);
					startActivity(intent);
					
				}else if(selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("P")){
					Intent intent = new Intent(InpectionEntryActivity.this, SqmInProgressGradingActivity.class);
					intent.putExtra("monitorName", loginDetailsDTO.getMonitorName());
					intent.putExtra("entryMode", mode);
					intent.putExtra("inpectionEntryRoadDTO", selectedRoadDetailsDTO);
					startActivity(intent);
					
					
				}else if(selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("M")){
					Intent intent = new Intent(InpectionEntryActivity.this, SqmMaintenanceGradingActivity.class);
					intent.putExtra("monitorName", loginDetailsDTO.getMonitorName());
					intent.putExtra("entryMode", mode);
					intent.putExtra("inpectionEntryRoadDTO", selectedRoadDetailsDTO);
					startActivity(intent);
					
				}else if(selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("LP")){
				 	Intent intent = new Intent(InpectionEntryActivity.this, SqmLSBInProgressGradingActivity.class);
					intent.putExtra("monitorName", loginDetailsDTO.getMonitorName());
					intent.putExtra("entryMode", mode);
					intent.putExtra("inpectionEntryRoadDTO", selectedRoadDetailsDTO);
					startActivity(intent);
				
				}
				else if(selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("LC") || selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("LM") || selectedRoadDetailsDTO.getRoadStatus().equalsIgnoreCase("L")){
				 	Intent intent = new Intent(InpectionEntryActivity.this, SqmLSBCompletedGradingActivity.class);
					intent.putExtra("monitorName", loginDetailsDTO.getMonitorName());
					intent.putExtra("entryMode", mode);
					intent.putExtra("inpectionEntryRoadDTO", selectedRoadDetailsDTO);
					startActivity(intent);
				
				}
				
			}
			
			
		}
		
		if(v.getId() == R.id.btnInspectionEntryCancelId){
			finish();
		}
		
	}
	
	
	public Boolean validateInspectionEntry(){
		
		if(roadSpinner.getSelectedItemPosition() == 0){
			Notification.showErrorMessage(NotificationEnum.selectRoad, this, parent);
			QMSHelper.blink(roadSpinner);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	} // validateInspectionEntry
	
	
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

}
