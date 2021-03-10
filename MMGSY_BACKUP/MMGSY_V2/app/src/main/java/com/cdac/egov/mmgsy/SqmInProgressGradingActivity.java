package com.cdac.egov.mmgsy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import common.Notification;
import common.QMSHelper;
import common.QMSHelper.NotificationEnum;

import db.DAO.ImageDetailsDAO;
import db.DAO.ObservationDetailsDAO;
import db.DAO.ScheduleDetailsDAO;
import db.DTO.ImageDetailsDTO;
import db.DTO.ScheduleDetailsDTO;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ViewFlipper;


public class SqmInProgressGradingActivity extends AppBaseActivity implements OnClickListener, OnCheckedChangeListener{

	private ViewFlipper viewFlipper;
	private Button nextBtn;
	private Button previousBtn;
	
	private RadioGroup sqmSettingOutRadioGroup;
	private RadioGroup sqmSiteClearanceRadioGroup;
	private RadioGroup sqmQualityArrangementRadioGroup;
	private RadioGroup sqmGeometricsRadioGroup;
	private RadioGroup sqmEarthWorkSubRadioGroup;
	private RadioGroup sqmEarthWorkCuttingRadioGroup;
	private RadioGroup sqmSubbaseRadioGroup;
	private RadioGroup sqmBaseCourseWaterRadioGroup;
	private RadioGroup sqmBituminousRadioGroup;
	private RadioGroup sqmShouldersRadioGroup;
	private RadioGroup sqmCrossDrainageRadioGroup;
	private RadioGroup sqmSideDrainRadioGroup;
	private RadioGroup sqmCCSemiRigidPavementRadioGroup;
	private RadioGroup sqmRoadFurnitureRadioGroup;
	private RadioGroup sqmOverallGrade;
	private Activity parent;
	private ArrayList<String> selectedRadioList;
	private ScheduleDetailsDTO roadDetailsDTO;
	private Button saveBtn;
	private Button cancelBtn;
	private String entryMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = SqmInProgressGradingActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqm_in_progress_grading);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.sqmInProgressGradingFormViewFlipper);
		nextBtn = (Button) findViewById(R.id.sqmInProgressGradingFormNextBtnId);
        previousBtn = (Button) findViewById(R.id.sqmInProgressGradingFormPrevBtnId);
        saveBtn = (Button) findViewById(R.id.sqmInProgressGradingFormSaveBtn);
        cancelBtn = (Button) findViewById(R.id.sqmInProgressGradingFormCancelBtn);
        
        nextBtn.setOnClickListener(this);
        previousBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        
        // Set Road Details for Grading forms
        Intent intent = getIntent();
        String  monitorName =  (String) intent.getStringExtra("monitorName");
        entryMode =  (String) intent.getStringExtra("entryMode");
        roadDetailsDTO = (ScheduleDetailsDTO) intent.getSerializableExtra("inpectionEntryRoadDTO");
        
        QMSHelper.setInpsectionFormDetails(parent, monitorName, roadDetailsDTO, this ,this);
        
        
      //-------- Radio Button Events
        sqmSettingOutRadioGroup  = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmSettingOutRadioGroup);
        sqmSiteClearanceRadioGroup = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmSiteClearanceRadioGroup);
        sqmQualityArrangementRadioGroup = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmQualityArrangementRadioGroup);
        sqmGeometricsRadioGroup  = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmGeometricsRadioGroup);
     	sqmEarthWorkSubRadioGroup = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmEarthWorkSubGradeRadioGroup);
     	sqmEarthWorkCuttingRadioGroup = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmEarthWorkCuttingRadioGroup);
     	sqmSubbaseRadioGroup = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmSubBaseRadioGroup);
     	sqmBaseCourseWaterRadioGroup = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmBaseCourseWaterRadioGroup);
     	sqmBituminousRadioGroup = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmBituminousRadioGroup);
     	sqmShouldersRadioGroup = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmShouldersRadioGroup);
     	sqmCrossDrainageRadioGroup = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmCrossDrainageRadioGroup); 
     	sqmSideDrainRadioGroup = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmSideDrainRadioGroup);
     	sqmCCSemiRigidPavementRadioGroup = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmCCSemiRigidPavementsRadioGroup);
     	sqmRoadFurnitureRadioGroup = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormSqmRoadFurnitureRadioGroup);
     	sqmOverallGrade = (RadioGroup) findViewById(R.id.sqmInProgressGradingFormOverallGradeRadioGroup);
     	
     	sqmSettingOutRadioGroup.setOnCheckedChangeListener(this);
    	sqmSiteClearanceRadioGroup.setOnCheckedChangeListener(this);
    	sqmQualityArrangementRadioGroup.setOnCheckedChangeListener(this);
     	sqmGeometricsRadioGroup.setOnCheckedChangeListener(this);
     	sqmEarthWorkSubRadioGroup.setOnCheckedChangeListener(this);
     	sqmEarthWorkCuttingRadioGroup.setOnCheckedChangeListener(this);
     	sqmSubbaseRadioGroup.setOnCheckedChangeListener(this);
     	sqmBaseCourseWaterRadioGroup.setOnCheckedChangeListener(this);
     	sqmBituminousRadioGroup.setOnCheckedChangeListener(this);
     	sqmShouldersRadioGroup.setOnCheckedChangeListener(this);
     	sqmCrossDrainageRadioGroup.setOnCheckedChangeListener(this);
     	sqmSideDrainRadioGroup.setOnCheckedChangeListener(this);
     	sqmCCSemiRigidPavementRadioGroup.setOnCheckedChangeListener(this);
     	sqmRoadFurnitureRadioGroup.setOnCheckedChangeListener(this);
	}

	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sqm_in_progress_grading, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// Flipper event start 
		if(v == nextBtn)
			viewFlipper.showNext(); 
	       
	    if(v == previousBtn)
	    	   viewFlipper.showPrevious();
	    
	    if(v.getId() == R.id.sqmInProgressGradingFormSaveBtn){
	    	   saveGradingValues();
		    }
	       
	    if(v.getId() == R.id.sqmInProgressGradingFormCancelBtn){
	    	finish();
 		}
	    
	    if(v.getId() == R.id.btnMainMenuLogoutId){
			logOut();
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int isChecked) {
		
		calculateOverAllGrading();
		
	}
	
	
	public void calculateOverAllGrading(){
	 	
		RadioButton S = (RadioButton) findViewById(R.id.sqmInProgressGradingFormOverallGradeRadio_S);
		RadioButton SRI = (RadioButton) findViewById(R.id.sqmInProgressGradingFormOverallGradeRadio_SRI);
		RadioButton U = (RadioButton) findViewById(R.id.sqmInProgressGradingFormOverallGradeRadio_U);
		RadioButton NA = (RadioButton) findViewById(R.id.sqmInProgressGradingFormOverallGradeRadio_NA);
		
		RadioButton selectedRadioButton1 = (RadioButton) findViewById(sqmSettingOutRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton2 = (RadioButton) findViewById(sqmSiteClearanceRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton3 = (RadioButton) findViewById(sqmQualityArrangementRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton4 = (RadioButton) findViewById(sqmGeometricsRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton5 = (RadioButton) findViewById(sqmEarthWorkSubRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton6 = (RadioButton) findViewById(sqmEarthWorkCuttingRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton7 = (RadioButton) findViewById(sqmSubbaseRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton8 = (RadioButton) findViewById(sqmBaseCourseWaterRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton9 = (RadioButton) findViewById(sqmBituminousRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton10 = (RadioButton) findViewById(sqmShouldersRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton11 = (RadioButton) findViewById(sqmCrossDrainageRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton12 = (RadioButton) findViewById(sqmSideDrainRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton13 = (RadioButton) findViewById(sqmCCSemiRigidPavementRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton14 = (RadioButton) findViewById(sqmRoadFurnitureRadioGroup.getCheckedRadioButtonId());
		

		ArrayList<String> selectvalueArrayList = new ArrayList<String>();
		selectvalueArrayList.add(selectedRadioButton1.getText().toString());
		selectvalueArrayList.add(selectedRadioButton2.getText().toString());
		selectvalueArrayList.add(selectedRadioButton3.getText().toString());
		selectvalueArrayList.add(selectedRadioButton4.getText().toString());
		selectvalueArrayList.add(selectedRadioButton5.getText().toString());
		selectvalueArrayList.add(selectedRadioButton6.getText().toString());
		selectvalueArrayList.add(selectedRadioButton7.getText().toString());
		selectvalueArrayList.add(selectedRadioButton8.getText().toString());
		selectvalueArrayList.add(selectedRadioButton9.getText().toString());
		selectvalueArrayList.add(selectedRadioButton10.getText().toString());
		selectvalueArrayList.add(selectedRadioButton11.getText().toString());
		selectvalueArrayList.add(selectedRadioButton12.getText().toString());
		selectvalueArrayList.add(selectedRadioButton13.getText().toString());
		selectvalueArrayList.add(selectedRadioButton14.getText().toString());
				
		Integer count = 0;
		for (String string : selectvalueArrayList) {

			if (string.equalsIgnoreCase("NA")) {
				count++;
			}
		}
		
		if (selectvalueArrayList.size() == count) {
			NA.setChecked(true);
		
		}else if(selectvalueArrayList.get(4).equalsIgnoreCase("U") || selectvalueArrayList.get(5).equalsIgnoreCase("U") || selectvalueArrayList.get(6).equalsIgnoreCase("U") || selectvalueArrayList.get(7).equalsIgnoreCase("U") || selectvalueArrayList.get(8).equalsIgnoreCase("U")){
			
			U.setChecked(true);
		
		}else if(selectvalueArrayList.get(0).equalsIgnoreCase("U") || selectvalueArrayList.get(1).equalsIgnoreCase("U") ||
				 selectvalueArrayList.get(2).equalsIgnoreCase("U") || selectvalueArrayList.get(3).equalsIgnoreCase("U") || selectvalueArrayList.get(9).equalsIgnoreCase("U") 
				|| selectvalueArrayList.get(10).equalsIgnoreCase("U") || selectvalueArrayList.get(11).equalsIgnoreCase("U")){
			
			SRI.setChecked(true);
		
		}else if(selectvalueArrayList.get(0).equalsIgnoreCase("SRI") || selectvalueArrayList.get(1).equalsIgnoreCase("SRI") ||
				 selectvalueArrayList.get(2).equalsIgnoreCase("SRI") || selectvalueArrayList.get(3).equalsIgnoreCase("SRI") || 
				 selectvalueArrayList.get(9).equalsIgnoreCase("SRI") || selectvalueArrayList.get(10).equalsIgnoreCase("SRI") || 
				 selectvalueArrayList.get(11).equalsIgnoreCase("SRI") || 
				 selectvalueArrayList.get(12).equalsIgnoreCase("U") || selectvalueArrayList.get(13).equalsIgnoreCase("U")){
			
			SRI.setChecked(true);
		
		}else{
			
			S.setChecked(true);
		}
	} // calculateOverAllGrading ends here 

	public void saveGradingValues(){
		
		selectedRadioList = new ArrayList<String>();
		ArrayList<RadioButton> selectedRadioButtonArrayList  = new ArrayList<RadioButton>();
		
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmSettingOutRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmSiteClearanceRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmQualityArrangementRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmGeometricsRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmEarthWorkSubRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmEarthWorkCuttingRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmSubbaseRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmBaseCourseWaterRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmBituminousRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmShouldersRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmCrossDrainageRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmSideDrainRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmCCSemiRigidPavementRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmRoadFurnitureRadioGroup.getCheckedRadioButtonId()));
		
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(sqmOverallGrade.getCheckedRadioButtonId()));
		
		for (RadioButton radioButton : selectedRadioButtonArrayList) {
			selectedRadioList.add(radioButton.getText().toString());
		}
		
		selectedRadioList.get(selectedRadioList.size()-1);
		// ------------------- Dialog Box Code ---------------------------------------
		
		if(!QMSHelper.validateChainage(parent, roadDetailsDTO)){
			return;
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(" Overall Grade is : "+selectedRadioList.get(selectedRadioList.size()-1) + "\n Do you want to save?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		          

				private String status;

				public void onClick(DialogInterface dialog, int id) {

						/*Calendar c = Calendar.getInstance();
						SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
						String inspDate = df.format(c.getTime());*/
					

					   // Change on 18-June-2014
				 	    ImageDetailsDAO imageDetailsDAO = new ImageDetailsDAO();
					    ArrayList<ImageDetailsDTO> imageDetailsDTOArrayList =  imageDetailsDAO.getImageDetails(roadDetailsDTO.getUniqueCode(), parent, parent);
					 	//Calendar c = Calendar.getInstance();
						//SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
						String inspDate =  imageDetailsDTOArrayList.get(0).getCaptureDateTime();
						// Change on 18-June-2014 ends here
						
						 //***********************
						   final ArrayList<String> commentEditText = new ArrayList<String>();
					
						float fromChainage = Float.parseFloat(((EditText)  parent.findViewById(R.id.gradingFormFromChainageIdEditText)).getText().toString());
						float toChainage = Float.parseFloat(((EditText) parent.findViewById(R.id.gradingFormToChainageIdEditText)).getText().toString());
						ObservationDetailsDAO observationDetailsDAOObj = new ObservationDetailsDAO();
						long result = observationDetailsDAOObj.setObservationDetails(roadDetailsDTO, inspDate, fromChainage, toChainage, selectedRadioList,commentEditText, parent , parent);
						ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
						if(entryMode.equalsIgnoreCase("unplanned")){
							status = "S";
						}else{
						
							status = "I";
						}
						
					 	scheduleDetailsDAOObj.updateStatus( roadDetailsDTO.getUniqueCode(), status, parent, parent);					
						if(result == 0 || result == -1 ) {
							Notification.showErrorMessage(NotificationEnum.errorObservationDetailSaved, parent, parent);
						}else{
							Notification.showErrorMessage(NotificationEnum.observationDetailSaved, parent, parent);
							finish();
						}
					}
		       		
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	  
		           }
		       });
		
		AlertDialog alert = builder.create();
		alert.show();
		
		// ------------------- Dialog Box Code ends here -----------------------------
	} //method ends
	
	
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
