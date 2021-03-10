package com.cdac.egov.mmgsy;

import java.security.acl.NotOwnerException;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import common.Notification;
import common.QMSHelper.NotificationEnum;
import db.DAO.ImageDetailsDAO;
import db.DAO.LoginDetailsDAO;
import db.DAO.ObservationDetailsDAO;
import db.DAO.QmsNotificationDAO;
import db.DAO.ScheduleDetailsDAO;
import db.DTO.LoginDetailsDTO;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ResetActivity extends Activity implements OnClickListener{

	private EditText resetPassword;
	private Button resetButton;
	private Button resetCancelButton;
	private ResetActivity parent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = ResetActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset);
		
		
		 resetPassword  = (EditText) findViewById(R.id.txtResetPasswordId);
		 resetButton = (Button) findViewById(R.id.btnResetId);
		 resetCancelButton = (Button) findViewById(R.id.btnResetCancelId);
		 
		 resetButton.setOnClickListener(this);
		 resetCancelButton.setOnClickListener(this);
		 
	}

	@Override
	public void onClick(View v) {
	
		if(v.getId() == R.id.btnResetId){
			
			if(resetPassword.getText().toString().trim().length() == 0){
				Notification.showErrorMessage(NotificationEnum.EnterPassword, this, parent);
				resetPassword.setText("");
				return;
			}
			
			LoginDetailsDAO loginDAO = new LoginDetailsDAO();
			LoginDetailsDTO loginDetailDTO =  loginDAO.getLoginDetails(null, this);
			
			if(!loginDetailDTO.getPasswordWithoutHash().equalsIgnoreCase(resetPassword.getText().toString().trim())){
				Notification.showErrorMessage(NotificationEnum.incorrectPassword, this, parent);
				resetPassword.setText("");
				return;
			}
			
			ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
			ObservationDetailsDAO observationDetailDAOObj = new ObservationDetailsDAO();
			ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
			
			scheduleDetailsDAOObj.emptyScheduleDetails(this);
			observationDetailDAOObj.emptyObservationDetails(this);
			imageDetailsDAOObj.emptyImageDetails(this);
			QmsNotificationDAO qmsNotificationDAO = new QmsNotificationDAO();
			qmsNotificationDAO.emptyNotificationDetails(this);
			Notification.showErrorMessage(NotificationEnum.resetSuccess, this, parent);
			finish();
				
			
			
			
		}
		
		if(v.getId() == R.id.btnResetCancelId){
			finish();
		}
	}



}
