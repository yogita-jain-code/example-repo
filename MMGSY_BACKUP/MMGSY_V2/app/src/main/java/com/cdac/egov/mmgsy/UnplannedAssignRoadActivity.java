package com.cdac.egov.mmgsy;

import java.util.ArrayList;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import webServices.DownloadScheduleWebServices;
import webServices.DownloadUnplannedScheduleWebServices;

import common.Notification;
import common.QMSHelper;
import common.QMSHelper.NotificationEnum;

import db.DAO.LoginDetailsDAO;
import db.DAO.ScheduleDetailsDAO;
import db.DTO.LoginDetailsDTO;
import db.DTO.ScheduleDetailsDTO;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class UnplannedAssignRoadActivity extends AppBaseActivity implements OnClickListener, OnItemSelectedListener {

	private Spinner generatedIdSpinner;
	private ArrayAdapter generatedIdAdapter;
	private ArrayList<ScheduleDetailsDTO> scheduleDetailsDTOArrayList;
	private UnplannedAssignRoadActivity parent;
	private EditText packageIdEditText;
	private Button btnGetRoads;
	private Button btnGetRoadsCancel;
	private TextView monthYearOfInspection;
	private Integer monthOfInspection;
	private Integer yearOfInspection;
	private ProgressDialog dialog;
	public ArrayList<ScheduleDetailsDTO> scheduleDetailsDTOArrayListOfRoadsInPackage;
	private String selectedGeneratedId;
	private Integer qmCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = UnplannedAssignRoadActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unplanned_assign_road);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);

		btnGetRoads = (Button) findViewById(R.id.btnGetRoadsId);
		btnGetRoads.setOnClickListener(this);
		
		btnGetRoadsCancel = (Button) findViewById(R.id.btnGetRoadsCancelId);
		btnGetRoadsCancel.setOnClickListener(this);
		
		monthYearOfInspection = (TextView) findViewById(R.id.monthYearOfInspectionId);
		
		ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
		scheduleDetailsDTOArrayList = scheduleDetailsDAOObj.getAllFinalizedGeneratedIds(this, parent);

		ArrayList<String> generatedIdList = new ArrayList<String>();
		generatedIdList.add("Select Generated Id");
		for (ScheduleDetailsDTO scheduleDetailsDTO : scheduleDetailsDTOArrayList) {
			generatedIdList.add(scheduleDetailsDTO.getUniqueCode());
		}
		generatedIdSpinner = (Spinner) findViewById(R.id.spinnerGeneratedsId);
		generatedIdAdapter = new ArrayAdapter(this,R.layout.spinner_layout, generatedIdList);
		generatedIdSpinner.setAdapter(generatedIdAdapter);
		//generatedIdSpinner.setSelection(selelectedLastRoad);
		generatedIdSpinner.setOnItemSelectedListener(this);
		
		packageIdEditText = (EditText) findViewById(R.id.packageEditTextId);
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
		getMenuInflater().inflate(R.menu.unplanned_assign_road, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.btnMainMenuLogoutId){
			logOut();
		}
		
		if(v.getId() == R.id.btnGetRoadsId)
		{
			if(!validateGetRoads()){
				return;
			}
			
			// here make service call, get schedule details & store temporarily in array list,
			// on selection of road, map it with Id & update it in schedule details table.
			dialog = ProgressDialog.show(UnplannedAssignRoadActivity.this, "", 
	                "Schedule Loading. Please wait...", true);
			 
			 new DownloadFilesTask().execute();
			
		}
		
		if(v.getId() == R.id.btnGetRoadsCancelId)
		{
			finish();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long  arg3) {
		int localIndex = i-1;
		
		if(adapterView.getId() == R.id.spinnerGeneratedsId)
		{
			if(i != 0)
			{
				ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
				selectedGeneratedId = generatedIdSpinner.getSelectedItem().toString();
				ScheduleDetailsDTO scheduleDetailsDTOObj = scheduleDetailsDAOObj.getScheduleDetails(selectedGeneratedId, this, parent);
				if(scheduleDetailsDTOObj != null)
				{
					LoginDetailsDAO loginDetail = new LoginDetailsDAO();
					LoginDetailsDTO loginDetailDTO = loginDetail.getLoginDetails(null, this);
					qmCode = loginDetailDTO.getQmCode();
					monthOfInspection = scheduleDetailsDTOObj.getScheduleMonth();
					yearOfInspection = scheduleDetailsDTOObj.getScheduleYear();
					monthYearOfInspection.setVisibility(RelativeLayout.VISIBLE);
					monthYearOfInspection.setText("Month / Year : "+ monthOfInspection + " / " + yearOfInspection);
				}
				else
				{
					//return error;
					//Notification.showErrorMessage(NotificationEnum.plzGenerateId, this, parent);
				}
			}
			else
			{
				monthYearOfInspection.setVisibility(RelativeLayout.GONE);
				monthYearOfInspection.setText("Month / Year : ");
			}
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	public Boolean validateGetRoads(){
		
		if(generatedIdSpinner.getSelectedItemPosition() == 0){
			Notification.showErrorMessage(NotificationEnum.plzSelectGeneratedId, this, parent);
			QMSHelper.blink(generatedIdSpinner);
			return Boolean.FALSE;
		}
		
		if(packageIdEditText.getText().toString().length()==0){
			Notification.showErrorMessage(NotificationEnum.emptyPackageId, this, parent);
			QMSHelper.blink(packageIdEditText);
			return Boolean.FALSE;
		}
		
		if(packageIdEditText.getText().toString().length() > 15){
			Notification.showErrorMessage(NotificationEnum.exceedPackageId, this, parent);
			QMSHelper.blink(packageIdEditText);
			return Boolean.FALSE;
		}
		
		String SpChar[] = {"~","!","@,","#","$","%","^","&","*","\\",":",";","`","(",")","{","}","[","]","<",">","?","+","="};
		for(int i = 0; i<SpChar.length;i++){
		if(packageIdEditText.getText().toString().contains(SpChar[i])){
			Notification.showErrorMessage(NotificationEnum.specialCharPackageId, this, parent);
			QMSHelper.blink(packageIdEditText);
			return Boolean.FALSE;
			
			}
		}
		return Boolean.TRUE;
	}//validate Ends here


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
	}//logout ends here
	
	
	private class DownloadFilesTask extends AsyncTask<Context, String, String> {

		
		
		@Override
		protected String doInBackground(Context... context) {
			
			DownloadUnplannedScheduleWebServices downloadUnplannedScheduleWebServices = new DownloadUnplannedScheduleWebServices();
			scheduleDetailsDTOArrayListOfRoadsInPackage = new ArrayList<ScheduleDetailsDTO>();
			scheduleDetailsDTOArrayListOfRoadsInPackage = downloadUnplannedScheduleWebServices.downloadUnplannedSchedule(packageIdEditText.getText().toString(), monthOfInspection.toString(), yearOfInspection.toString(),qmCode.toString() , UnplannedAssignRoadActivity.this, parent);
			dialog.dismiss();
			finish();
			
			Intent intent = new Intent(UnplannedAssignRoadActivity.this, ViewRoadsToAssignActivity.class);
			intent.putExtra("selectedGeneratedId", selectedGeneratedId);
			intent.putExtra("scheduleDetailsDTOArrayListOfRoadsInPackage", scheduleDetailsDTOArrayListOfRoadsInPackage);
			startActivity(intent);
			
			return null;
		}
	}

}
