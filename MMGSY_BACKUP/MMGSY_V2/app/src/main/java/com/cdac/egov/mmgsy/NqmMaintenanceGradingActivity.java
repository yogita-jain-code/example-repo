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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class NqmMaintenanceGradingActivity extends AppBaseActivity implements OnClickListener, OnCheckedChangeListener {

	private ViewFlipper viewFlipper;
	private Button nextBtn;
	private Button previousBtn;
	
	private RadioGroup nqmMaintenanceOfRoadRestorationRadioGroup;
	private RadioGroup nqmRestorationOfRainCutsRadioGroup;
	private RadioGroup nqmConditionOfPavementRadioGroup;
	private RadioGroup nqmMaintenanceOfDrainsRadioGroup;
	private RadioGroup nqmMaintenanceOfRoadSignsRadioGroup;
	private RadioGroup nqmMaintenanceOfGuardRailsRadioGroup;
	private RadioGroup nqmOverallGradeRadioGroup;
	private Activity parent;
	private ArrayList<String> selectedRadioList;
	private ScheduleDetailsDTO roadDetailsDTO;
	private Button saveBtn;
	private Button cancelBtn;
	private String entryMode;
	private EditText nqmMaintenanceOfRoadRestorationEditText;
	private EditText nqmRestorationOfRainCutsEditText;
	private EditText nqmConditionOfPavementEditText;
	private EditText nqmMaintenanceOfDrainsEditText;
	private EditText nqmMaintenanceOfRoadSignsEditText;
	private EditText nqmMaintenanceOfGuardRailsEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = NqmMaintenanceGradingActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nqm_maintenance_grading);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.nqmMaintenanceGradingFormViewFlipper);
		nextBtn = (Button) findViewById(R.id.nqmMaintenanceGradingFormNextBtnId);
        previousBtn = (Button) findViewById(R.id.nqmMaintenanceGradingFormPrevBtnId);
        saveBtn = (Button) findViewById(R.id.nqmMaintenanceGradingFormSaveBtn);
        cancelBtn = (Button) findViewById(R.id.nqmMaintenanceGradingFormCancelBtn);
       
        nextBtn.setOnClickListener(this);
        previousBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        
        // Set Road Details for Grading forms
        Intent intent = getIntent();
        String  monitorName =  (String) intent.getStringExtra("monitorName");
        entryMode =  (String) intent.getStringExtra("entryMode");
        roadDetailsDTO = (ScheduleDetailsDTO) intent.getSerializableExtra("inpectionEntryRoadDTO");
        
        QMSHelper.setInpsectionFormDetails(parent, monitorName, roadDetailsDTO, this, this);
        
     
        //-------- Radio Button Events
    	nqmMaintenanceOfRoadRestorationRadioGroup  = (RadioGroup) findViewById(R.id.nqmMaintenanceGradingFormMaintenanceOfRoadRestorationRadioGroup);
    	nqmRestorationOfRainCutsRadioGroup = (RadioGroup) findViewById(R.id.nqmMaintenanceGradingFormRestorationOfRainCutsRadioGroup);
    	nqmConditionOfPavementRadioGroup = (RadioGroup) findViewById(R.id.nqmMaintenanceGradingFormConditionOfPavementRadioGroup);
    	nqmMaintenanceOfDrainsRadioGroup = (RadioGroup) findViewById(R.id.nqmMaintenanceGradingFormMaintenanceOfDrainsRadioGroup);
    	nqmMaintenanceOfRoadSignsRadioGroup = (RadioGroup) findViewById(R.id.nqmMaintenanceGradingFormMaintenanceOfRoadSignsRadioGroup);
    	nqmMaintenanceOfGuardRailsRadioGroup = (RadioGroup) findViewById(R.id.nqmMaintenanceGradingFormMaintenanceOfGuardRailsRadioGroup);
    	nqmOverallGradeRadioGroup = (RadioGroup) findViewById(R.id.nqmMaintenanceGradingFormOverallGradeRadioGroup);
    	 
    	nqmMaintenanceOfRoadRestorationRadioGroup.setOnCheckedChangeListener(this);
    	nqmRestorationOfRainCutsRadioGroup.setOnCheckedChangeListener(this);
    	nqmConditionOfPavementRadioGroup.setOnCheckedChangeListener(this);
    	nqmMaintenanceOfDrainsRadioGroup.setOnCheckedChangeListener(this);
    	nqmMaintenanceOfRoadSignsRadioGroup.setOnCheckedChangeListener(this);
    	nqmMaintenanceOfGuardRailsRadioGroup.setOnCheckedChangeListener(this);
    	
    	
    	nqmMaintenanceOfRoadRestorationEditText = (EditText) findViewById(R.id.nqmMaintenanceGradingFormMaintenanceOfRoadRestorationCommentEditText);
    	nqmRestorationOfRainCutsEditText = (EditText) findViewById(R.id.nqmMaintenanceGradingFormRestorationOfRainCutsCommentEditText);
    	nqmConditionOfPavementEditText = (EditText) findViewById(R.id.nqmMaintenanceGradingFormConditionOfPavementCommentEditText);
    	nqmMaintenanceOfDrainsEditText = (EditText) findViewById(R.id.nqmMaintenanceGradingFormMaintenanceOfDrainsCommentEditText);
    	nqmMaintenanceOfRoadSignsEditText = (EditText) findViewById(R.id.nqmMaintenanceGradingFormMaintenanceOfRoadSignsCommentEditText);
    	nqmMaintenanceOfGuardRailsEditText = (EditText) findViewById(R.id.nqmMaintenanceGradingFormMaintenanceOfGuardRailsCommentEditText);
    	
	}

	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nqm_maintenance_grading, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		// Flipper event start 
		if(v == nextBtn)
			viewFlipper.showNext(); 
	       
	    if(v == previousBtn)
	    	   viewFlipper.showPrevious(); 
	    
	    if(v.getId() == R.id.nqmMaintenanceGradingFormSaveBtn){
	    	   saveGradingValues();
		    }
	       
        if(v.getId() == R.id.nqmMaintenanceGradingFormCancelBtn){
    	   finish();
       	}
        
        if(v.getId() == R.id.btnMainMenuLogoutId){
			logOut();
		}
		
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		calculateOverAllGrading();
		
	}
	
	
	public void calculateOverAllGrading(){

		RadioButton S = (RadioButton) findViewById(R.id.nqmMaintenanceGradingFormOverallGradeRadio_S);
		RadioButton SRI = (RadioButton) findViewById(R.id.nqmMaintenanceGradingFormOverallGradeRadio_SRI);
		RadioButton U = (RadioButton) findViewById(R.id.nqmMaintenanceGradingFormOverallGradeRadio_U);
		RadioButton NA = (RadioButton) findViewById(R.id.nqmMaintenanceGradingFormOverallGradeRadio_NA);
		
		RadioButton selectedRadioButton1 = (RadioButton) findViewById(nqmMaintenanceOfRoadRestorationRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton2 = (RadioButton) findViewById(nqmRestorationOfRainCutsRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton3 = (RadioButton) findViewById(nqmConditionOfPavementRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton4 = (RadioButton) findViewById(nqmMaintenanceOfDrainsRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton5 = (RadioButton) findViewById(nqmMaintenanceOfRoadSignsRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton6 = (RadioButton) findViewById(nqmMaintenanceOfGuardRailsRadioGroup.getCheckedRadioButtonId());		

		ArrayList<String> selectvalueArrayList = new ArrayList<String>();
		selectvalueArrayList.add(selectedRadioButton1.getText().toString());
		selectvalueArrayList.add(selectedRadioButton2.getText().toString());
		selectvalueArrayList.add(selectedRadioButton3.getText().toString());
		selectvalueArrayList.add(selectedRadioButton4.getText().toString());
		selectvalueArrayList.add(selectedRadioButton5.getText().toString());
		selectvalueArrayList.add(selectedRadioButton6.getText().toString());
		
		ArrayList<EditText> editTextArrayList = new ArrayList<EditText>();
		editTextArrayList.add(nqmMaintenanceOfRoadRestorationEditText);
		editTextArrayList.add(nqmRestorationOfRainCutsEditText);
		editTextArrayList.add(nqmConditionOfPavementEditText);
		editTextArrayList.add(nqmMaintenanceOfDrainsEditText);
		editTextArrayList.add(nqmMaintenanceOfRoadSignsEditText);
		editTextArrayList.add(nqmMaintenanceOfGuardRailsEditText);
						
		Integer count = 0;
		Integer index = 0;
		for (String string : selectvalueArrayList) {

			if (string.equalsIgnoreCase("NA")) {
				count++;
			}
			
			if(string.equalsIgnoreCase("U")){
				editTextArrayList.get(index).setVisibility(LinearLayout.VISIBLE);
			}else{
				editTextArrayList.get(index).setVisibility(LinearLayout.GONE);
			}
			index++;
		}
		if (selectvalueArrayList.size() == count) {
			NA.setChecked(true);
		
		}else if(selectvalueArrayList.get(1).equalsIgnoreCase("U") || selectvalueArrayList.get(2).equalsIgnoreCase("U") ){
			
			U.setChecked(true);
		
		}else if(selectvalueArrayList.get(0).equalsIgnoreCase("U") || selectvalueArrayList.get(3).equalsIgnoreCase("U") || selectvalueArrayList.get(4).equalsIgnoreCase("U") || selectvalueArrayList.get(5).equalsIgnoreCase("U")){
			
			SRI.setChecked(true);
		
		}else{
			
			S.setChecked(true);
		}
		
	} // calculateOverAllGrading ends here 
	
	
	public void saveGradingValues(){
		
		selectedRadioList = new ArrayList<String>();
		final ArrayList<String> commentEditText = new ArrayList<String>();
		ArrayList<RadioButton> selectedRadioButtonArrayList  = new ArrayList<RadioButton>();
		
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmMaintenanceOfRoadRestorationRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmRestorationOfRainCutsRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmConditionOfPavementRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmMaintenanceOfDrainsRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmMaintenanceOfRoadSignsRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmMaintenanceOfGuardRailsRadioGroup.getCheckedRadioButtonId()));

		ArrayList<EditText> editTextArrayList = new ArrayList<EditText>();
		editTextArrayList.add(nqmMaintenanceOfRoadRestorationEditText);
		editTextArrayList.add(nqmRestorationOfRainCutsEditText);
		editTextArrayList.add(nqmConditionOfPavementEditText);
		editTextArrayList.add(nqmMaintenanceOfDrainsEditText);
		editTextArrayList.add(nqmMaintenanceOfRoadSignsEditText);
		editTextArrayList.add(nqmMaintenanceOfGuardRailsEditText);
		
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(nqmOverallGradeRadioGroup.getCheckedRadioButtonId()));
		Integer index = 0;
		for (RadioButton radioButton : selectedRadioButtonArrayList) {
			selectedRadioList.add(radioButton.getText().toString());
			
			if(radioButton.getText().toString().equalsIgnoreCase("U")){
				if(index < editTextArrayList.size()){
				if(editTextArrayList.get(index).getText().toString().length() == 0){
					Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext() , parent);
					QMSHelper.blink(editTextArrayList.get(index));
					return;
				}else{
					commentEditText.add(editTextArrayList.get(index).getText().toString());
					}
			  }
			}else{
				commentEditText.add("NA");
			}
			index++;
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
