package com.cdac.egov.mmgsy;

import common.Constant;
import common.QMSHelper;

import android.os.Bundle;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class StandardMenuActivity extends AppBaseActivity implements OnClickListener{

	private StandardMenuActivity parent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = StandardMenuActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standard_menu);
		registerBaseActivityReceiver();
		
		ImageView btnStdLogout = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnStdLogout.setOnClickListener(this);
		
		Button btnStdModeMenuSchedule = (Button) findViewById(R.id.btnStdModeMenuScheduleId);
		Button btnStdModeTakePhoto = (Button) findViewById(R.id.btnStdModeTakePhotoId);
		Button btnStdModeViewSchedule = (Button) findViewById(R.id.btnStdModeViewScheduleId);
		
		Button btnStdModeInpectionEntry = (Button) findViewById(R.id.btnStdModeInpectionEntryId);
		Button btnStdModeSubmit = (Button) findViewById(R.id.btnStdModeSubmitId);
		Button btnStdViewUploadRecords = (Button) findViewById(R.id.btnStdModeViewUploadRecordsId);
		
		Button btnStdHowToUse = (Button) findViewById(R.id.btnStdModeHowToUseId);
		
		btnStdModeMenuSchedule.setOnClickListener(this);
		btnStdModeTakePhoto.setOnClickListener(this);
		btnStdModeViewSchedule.setOnClickListener(this);
		btnStdModeInpectionEntry.setOnClickListener(this);
		btnStdModeSubmit.setOnClickListener(this);
		btnStdViewUploadRecords.setOnClickListener(this);
		btnStdHowToUse.setOnClickListener(this);
		
		if(Constant.loginMode.equalsIgnoreCase("online")){
			btnStdModeMenuSchedule.setVisibility(LinearLayout.VISIBLE);
			btnStdModeSubmit.setVisibility(LinearLayout.VISIBLE);
		}else{
			btnStdModeMenuSchedule.setVisibility(LinearLayout.GONE);
			btnStdModeSubmit.setVisibility(LinearLayout.GONE);
		}
		
	}
	
	
	
	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }

	@Override
	public void onClick(View v) {

		if(v.getId() == R.id.btnStdModeMenuScheduleId){
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(" Are you sure to download schedule ?")
			       .setCancelable(false)
			       .setTitle("Quality Monitoring System")
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        		Intent intent = new Intent(StandardMenuActivity.this,DownloadScheduleActivity.class);
			    			startActivity(intent);
	 		    			
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
		
		if(v.getId() == R.id.btnStdModeViewScheduleId){
			Intent intent = new Intent(StandardMenuActivity.this,ViewScheduleActivity.class);
			startActivity(intent);
			
		}
		
		if(v.getId() == R.id.btnStdModeTakePhotoId){
			Intent intent = new Intent(StandardMenuActivity.this,TakePhotoActivity.class);
			startActivity(intent);
		}

		if(v.getId() == R.id.btnStdModeInpectionEntryId){
		 	Intent intent = new Intent(StandardMenuActivity.this,InpectionEntryActivity.class);
		 	intent.putExtra("mode", "planned");
			startActivity(intent);
		}

		if(v.getId() == R.id.btnStdModeSubmitId){
			Intent intent = new Intent(StandardMenuActivity.this,PlannedSubmitActivity.class);
			intent.putExtra("mode", "planned");
			startActivity(intent);
		}
		
		if(v.getId() == R.id.btnMainMenuLogoutId){
			logOut();
		}
		
		if(v.getId() == R.id.btnStdModeViewUploadRecordsId){
			Intent intent = new Intent(StandardMenuActivity.this,ViewUploadRecordsActivity.class);
			startActivity(intent);
			
		}
		
		if(v.getId() == R.id.btnStdModeHowToUseId){
			Intent intent = new Intent(StandardMenuActivity.this,HowToUseActivity.class);
			intent.putExtra("mode", "planned");
			finish();
			startActivity(intent);
			
		}
		
	}//onclick ends here

	
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
