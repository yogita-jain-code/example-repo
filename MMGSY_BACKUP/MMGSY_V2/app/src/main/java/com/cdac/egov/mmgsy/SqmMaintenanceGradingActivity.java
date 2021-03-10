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

public class SqmMaintenanceGradingActivity extends AppBaseActivity implements OnClickListener, OnCheckedChangeListener {

	private ViewFlipper viewFlipper;
	private Button nextBtn;
	private Button previousBtn;
	private RadioGroup sqmMaintenanceOfRoadRestorationRadioGroup;
	private RadioGroup sqmRestorationOfRainCutsRadioGroup;
	private RadioGroup sqmConditionOfPavementRadioGroup;
	private RadioGroup sqmMaintenanceOfDrainsRadioGroup;
	private RadioGroup sqmMaintenanceOfRoadSignsRadioGroup;
	private RadioGroup sqmMaintenanceOfGuardRailsRadioGroup;
	private RadioGroup sqmOverallGradeRadioGroup;
	private Activity parent;
	private Button saveBtn;
	private Button cancelBtn;
	private ScheduleDetailsDTO roadDetailsDTO;
	private ArrayList<String> selectedRadioList;
	private String entryMode;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = SqmMaintenanceGradingActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqm_maintenance_grading);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.sqmMaintenanceGradingFormViewFlipper);
		nextBtn = (Button) findViewById(R.id.sqmMaintenanceGradingFormNextBtnId);
        previousBtn = (Button) findViewById(R.id.sqmMaintenanceGradingFormPrevBtnId);
        saveBtn = (Button) findViewById(R.id.sqmMaintenanceGradingFormSaveBtn);
        cancelBtn = (Button) findViewById(R.id.sqmMaintenanceGradingFormCancelBtn);
        
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
    	sqmMaintenanceOfRoadRestorationRadioGroup  = (RadioGroup) findViewById(R.id.sqmMaintenanceGradingFormMaintenanceOfRoadRestorationRadioGroup);
    	sqmRestorationOfRainCutsRadioGroup = (RadioGroup) findViewById(R.id.sqmMaintenanceGradingFormRestorationOfRainCutsRadioGroup);
    	sqmConditionOfPavementRadioGroup = (RadioGroup) findViewById(R.id.sqmMaintenanceGradingFormConditionOfPavementRadioGroup);
    	sqmMaintenanceOfDrainsRadioGroup = (RadioGroup) findViewById(R.id.sqmMaintenanceGradingFormMaintenanceOfDrainsRadioGroup);
    	sqmMaintenanceOfRoadSignsRadioGroup = (RadioGroup) findViewById(R.id.sqmMaintenanceGradingFormMaintenanceOfRoadSignsRadioGroup);
    	sqmMaintenanceOfGuardRailsRadioGroup = (RadioGroup) findViewById(R.id.sqmMaintenanceGradingFormMaintenanceOfGuardRailsRadioGroup);
    	sqmOverallGradeRadioGroup = (RadioGroup) findViewById(R.id.sqmMaintenanceGradingFormOverallGradeRadioGroup);
    	
    	sqmMaintenanceOfRoadRestorationRadioGroup.setOnCheckedChangeListener(this);
    	sqmRestorationOfRainCutsRadioGroup.setOnCheckedChangeListener(this);
    	sqmConditionOfPavementRadioGroup.setOnCheckedChangeListener(this);
    	sqmMaintenanceOfDrainsRadioGroup.setOnCheckedChangeListener(this);
    	sqmMaintenanceOfRoadSignsRadioGroup.setOnCheckedChangeListener(this);
    	sqmMaintenanceOfGuardRailsRadioGroup.setOnCheckedChangeListener(this);
	}

	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sqm_maintenance_grading, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// Flipper event start 
		if(v == nextBtn)
			viewFlipper.showNext(); 
	       
	    if(v == previousBtn)
	    	   viewFlipper.showPrevious();
		
	    if(v.getId() == R.id.sqmMaintenanceGradingFormSaveBtn){
	    	   saveGradingValues();
		    }
	       
	    if(v.getId() == R.id.sqmMaintenanceGradingFormCancelBtn){
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

		RadioButton S = (RadioButton) findViewById(R.id.sqmMaintenanceGradingFormOverallGradeRadio_S);
		RadioButton SRI = (RadioButton) findViewById(R.id.sqmMaintenanceGradingFormOverallGradeRadio_SRI);
		RadioButton U = (RadioButton) findViewById(R.id.sqmMaintenanceGradingFormOverallGradeRadio_U);
		RadioButton NA = (RadioButton) findViewById(R.id.sqmMaintenanceGradingFormOverallGradeRadio_NA);
		
		RadioButton selectedRadioButton1 = (RadioButton) findViewById(sqmMaintenanceOfRoadRestorationRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton2 = (RadioButton) findViewById(sqmRestorationOfRainCutsRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton3 = (RadioButton) findViewById(sqmConditionOfPavementRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton4 = (RadioButton) findViewById(sqmMaintenanceOfDrainsRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton5 = (RadioButton) findViewById(sqmMaintenanceOfRoadSignsRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton6 = (RadioButton) findViewById(sqmMaintenanceOfGuardRailsRadioGroup.getCheckedRadioButtonId());		

		ArrayList<String> selectvalueArrayList = new ArrayList<String>();
		selectvalueArrayList.add(selectedRadioButton1.getText().toString());
		selectvalueArrayList.add(selectedRadioButton2.getText().toString());
		selectvalueArrayList.add(selectedRadioButton3.getText().toString());
		selectvalueArrayList.add(selectedRadioButton4.getText().toString());
		selectvalueArrayList.add(selectedRadioButton5.getText().toString());
		selectvalueArrayList.add(selectedRadioButton6.getText().toString());
						
		Integer count = 0;
		for (String string : selectvalueArrayList) {

			if (string.equalsIgnoreCase("NA")) {
				count++;
			}
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
		ArrayList<RadioButton> selectedRadioButtonArrayList  = new ArrayList<RadioButton>();
		
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmMaintenanceOfRoadRestorationRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmRestorationOfRainCutsRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmConditionOfPavementRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmMaintenanceOfDrainsRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmMaintenanceOfRoadSignsRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmMaintenanceOfGuardRailsRadioGroup.getCheckedRadioButtonId()));

		selectedRadioButtonArrayList.add( (RadioButton) findViewById(sqmOverallGradeRadioGroup.getCheckedRadioButtonId()));
		
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
