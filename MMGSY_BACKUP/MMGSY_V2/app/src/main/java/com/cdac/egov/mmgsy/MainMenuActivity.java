package com.cdac.egov.mmgsy;

import common.Constant;
import common.QMSHelper;

import android.os.Bundle;

import com.cdac.egov.mmgsy.MainActivity.CheckLoginTask;
//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainMenuActivity extends AppBaseActivity implements OnClickListener{

	private MainMenuActivity parent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		parent = MainMenuActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		
		Button btnMainMenuStdMode = (Button) findViewById(R.id.btnMainMenuStdModeId);
		Button btnMainMenuUnPlannedMode = (Button) findViewById(R.id.btnMainMenuUnPlannedModeId);
		Button btnMainMenuResetModeId = (Button) findViewById(R.id.btnMainMenuResetModeId);
		Button btnMainMenuMessageInboxModeId = (Button) findViewById(R.id.btnMainMenuMessageInboxModeId);
		
		//Button btnMainMenuLogoutId = (Button) findViewById(R.id.btnMainMenuLogoutId);
		
		btnMainMenuStdMode.setOnClickListener(this);
		btnMainMenuUnPlannedMode.setOnClickListener(this);
		btnMainMenuResetModeId.setOnClickListener(this);
		btnMainMenuLogoutId.setOnClickListener(this);
		btnMainMenuMessageInboxModeId.setOnClickListener(this);
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
	public void onClick(View v) {
		
		if(v.getId() == R.id.btnMainMenuStdModeId){
			Intent intent = new Intent(MainMenuActivity.this,StandardMenuActivity.class);
			startActivity(intent);

		}
		
		if(v.getId() == R.id.btnMainMenuUnPlannedModeId){
			Intent intent = new Intent(MainMenuActivity.this,UnplannedMenuActivity.class);
			startActivity(intent);
		}

		if(v.getId() == R.id.btnMainMenuResetModeId){
			Intent intent = new Intent(MainMenuActivity.this,ResetActivity.class);
			startActivity(intent);
		}
		
		if(v.getId() == R.id.btnMainMenuMessageInboxModeId){
			Intent intent = new Intent(MainMenuActivity.this,MessageBoxActivity.class);
			startActivity(intent);
		}
		
		if(v.getId() == R.id.btnMainMenuLogoutId){
			
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



}
