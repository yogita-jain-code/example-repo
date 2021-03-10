package com.cdac.egov.mmgsy;

import java.util.ArrayList;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import common.Constant;
import common.Notification;
import common.QMSHelper.NotificationEnum;

import db.DAO.ImageDetailsDAO;
import db.DAO.ScheduleDetailsDAO;
import db.DTO.ScheduleDetailsDTO;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import common.QMSHelper;

public class UnplannedMenuActivity extends AppBaseActivity implements OnClickListener {

	private UnplannedMenuActivity parent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = UnplannedMenuActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unplanned_menu);
		registerBaseActivityReceiver();
		
		ImageView btnUnplannedLogout = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnUnplannedLogout.setOnClickListener(this);
		
		Button btnUnplannedModeMenuGenerateId = (Button) findViewById(R.id.btnUnplannedModeMenuGenerateIdId);
		Button btnUnplannedModeTakePhoto = (Button) findViewById(R.id.btnUnplannedModeTakePhotoId);
		Button btnUnplannedModeFinalize = (Button) findViewById(R.id.btnUnplannedModeFinalizeId);
		Button btnUnplannedModeAssignRoad = (Button) findViewById(R.id.btnUnplannedModeAssignRoadId);
		Button btnUnplannedModeInpectionEntry = (Button) findViewById(R.id.btnUnplannedModeInpectionEntryId);
		Button btnUnplannedModeSubmit = (Button) findViewById(R.id.btnUnplannedModeSubmitId);
		
		Button btnUnplannedModeHowToUse = (Button) findViewById(R.id.btnUnplannedModeHowToUse);
		
		btnUnplannedModeMenuGenerateId.setOnClickListener(this);
		btnUnplannedModeTakePhoto.setOnClickListener(this);
		btnUnplannedModeFinalize.setOnClickListener(this);
		btnUnplannedModeAssignRoad.setOnClickListener(this);
		btnUnplannedModeInpectionEntry.setOnClickListener(this);
		btnUnplannedModeSubmit.setOnClickListener(this);
		btnUnplannedModeHowToUse.setOnClickListener(this);
		
		if(Constant.loginMode.equalsIgnoreCase("online")){
			btnUnplannedModeAssignRoad.setVisibility(LinearLayout.VISIBLE);
			btnUnplannedModeInpectionEntry.setVisibility(LinearLayout.VISIBLE);
			btnUnplannedModeSubmit.setVisibility(LinearLayout.VISIBLE);
		}else{
			btnUnplannedModeAssignRoad.setVisibility(LinearLayout.GONE);
			btnUnplannedModeInpectionEntry.setVisibility(LinearLayout.GONE);
			btnUnplannedModeSubmit.setVisibility(LinearLayout.GONE);
		}
		
	}

	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.unplanned_menu, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnUnplannedModeMenuGenerateIdId){
			generateId();
		}
		
		if(v.getId() == R.id.btnUnplannedModeTakePhotoId){
			ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
			ScheduleDetailsDTO scheduleDetailsDTOObj = scheduleDetailsDAOObj.getGeneratedIds(UnplannedMenuActivity.this, parent);
			
			if(scheduleDetailsDTOObj == null)
			{
				Notification.showErrorMessage(NotificationEnum.plzGenerateId, UnplannedMenuActivity.this, parent);
				return;
			}
			Intent intent = new Intent(UnplannedMenuActivity.this,UnplannedTakePhotoActivity.class);
			startActivity(intent);
		}
		
		if(v.getId() == R.id.btnUnplannedModeFinalizeId){
			finalizeEntry();
		}
		
		if(v.getId() == R.id.btnUnplannedModeAssignRoadId){
			
			ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
			ArrayList<ScheduleDetailsDTO>  scheduleDetailsDTOArrayList = scheduleDetailsDAOObj.getAllFinalizedGeneratedIds(this, parent);
			if(scheduleDetailsDTOArrayList.isEmpty())
			{
				Notification.showErrorMessage(NotificationEnum.noFinalizedEnryToMap, UnplannedMenuActivity.this, parent);
				return;
			}
			
			Intent intent = new Intent(UnplannedMenuActivity.this,UnplannedAssignRoadActivity.class);
			startActivity(intent);
		}
		
		if(v.getId() == R.id.btnUnplannedModeInpectionEntryId){
			Intent intent = new Intent(UnplannedMenuActivity.this,InpectionEntryActivity.class);
			intent.putExtra("mode", "unplanned");
			startActivity(intent);
		}
		
		if(v.getId() == R.id.btnUnplannedModeSubmitId){
			Intent intent = new Intent(UnplannedMenuActivity.this,PlannedSubmitActivity.class);
			intent.putExtra("mode", "unplanned");
			startActivity(intent);
		}
		
		if(v.getId() == R.id.btnUnplannedModeHowToUse){
			Intent intent = new Intent(UnplannedMenuActivity.this,HowToUseActivity.class);
			intent.putExtra("mode", "unplanned");
			finish();
			startActivity(intent);
		}
		
		if(v.getId() == R.id.btnMainMenuLogoutId){
			logOut();
		}
	}//onclick ends here
	
	public void generateId()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(" Are you sure to generate new id?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        		
			           //call method from DAO to generate Id
		        	   ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
		        	   int generatedId = scheduleDetailsDAOObj.generateUniqueId(UnplannedMenuActivity.this, parent);
		        	   
		        	   if(generatedId > 0)
		        	   {
			        	   //NotificationEnum can not be used due to dynamic value of generatedId
			        	   Toast toast = Toast.makeText(parent, "New Generated Id : " + generatedId, Toast.LENGTH_LONG);
			       		   toast.setGravity(Gravity.CENTER, 0, 0);
			       		   toast.show();
		        	   }else if(generatedId == -1)
		        	   {
		        		   Notification.showErrorMessage(NotificationEnum.finalizePrevEntry, UnplannedMenuActivity.this, parent);
		        	   }
		        	   else
		        	   {
		        		   //Display Error while generating Id
			        	   Notification.showErrorMessage(NotificationEnum.errorGeneratingNewId, UnplannedMenuActivity.this, parent);
		        	   }
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		
			AlertDialog alert = builder.create();
			alert.show();
	}
	
	
	public void finalizeEntry()
	{
		//Check image count, min photo requirement should be fulfilled before finalization.
		ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO(); 
		Integer imageCnt = imageDetailsDAOObj.getImageCountForGeneratedId(UnplannedMenuActivity.this, parent);
		if(imageCnt == -1)
		{
			Notification.showErrorMessage(NotificationEnum.noEntryToFinalize, UnplannedMenuActivity.this, parent);
			return;
			
		}else if(imageCnt < Constant.minImage)
		{
		   //NotificationEnum can not be used due to dynamic value of minImages
     	   Toast toast = Toast.makeText(parent, "Minimum " + Constant.minImage + " photos required to finalize entry", Toast.LENGTH_LONG);
		   toast.setGravity(Gravity.CENTER, 0, 0);
		   toast.show();
		   return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("If entry is finalized, you can't take photos for it. Are you sure to finalize? ")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        		
			        	   //call method from DAO to generate Id
		        	   	ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO(); 
			   			long finalizeCnt = scheduleDetailsDAOObj.finalizeEntryWithGeneratedId(UnplannedMenuActivity.this, parent);
			   			if(finalizeCnt == 0)
			   			{
			   				Notification.showErrorMessage(NotificationEnum.noEntryToFinalize, UnplannedMenuActivity.this, parent);
			   			}
			   			else if(finalizeCnt == -1)
			   			{
			   				Notification.showErrorMessage(NotificationEnum.errorInFinalize, UnplannedMenuActivity.this, parent);
			   			}
			   			else
			   			{
			   				Notification.showErrorMessage(NotificationEnum.entryFinalized, UnplannedMenuActivity.this, parent);
			   			}
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		
			AlertDialog alert = builder.create();
			alert.show();
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

}
