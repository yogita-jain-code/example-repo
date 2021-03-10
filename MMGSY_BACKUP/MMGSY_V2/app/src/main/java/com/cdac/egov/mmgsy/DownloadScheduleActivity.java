package com.cdac.egov.mmgsy;

import db.DAO.LoginDetailsDAO;
import db.DTO.LoginDetailsDTO;
import webServices.DownloadScheduleWebServices;
import android.os.AsyncTask;
import android.os.Bundle;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Menu;

public class DownloadScheduleActivity extends AppBaseActivity {

	private ProgressDialog dialog;
	private static DownloadScheduleActivity parent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = DownloadScheduleActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_schedule);
		registerBaseActivityReceiver();
		
		 dialog = ProgressDialog.show(DownloadScheduleActivity.this, "", 
	                "Schedule Loading. Please wait...", true);
			 
			 new DownloadFilesTask().execute();
	}

	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }
	
	private class DownloadFilesTask extends AsyncTask<Context, String, String> {
		
		@Override
		protected String doInBackground(Context... context) {
			//android.os.Debug.waitForDebugger();
			LoginDetailsDAO loginDetailsDAOObj = new LoginDetailsDAO();
			LoginDetailsDTO loginDetailsDTOObj = loginDetailsDAOObj.getLoginDetails(null, DownloadScheduleActivity.this);
			
			if(loginDetailsDTOObj.getUserId() != null){
				DownloadScheduleWebServices downloadScheduleWebServices = new DownloadScheduleWebServices();
				downloadScheduleWebServices.downloadSchedule(loginDetailsDTOObj.getUserName(), DownloadScheduleActivity.this, parent);
			}
			dialog.dismiss();
			finish();
			return null;
		}
		
		
	}

}



