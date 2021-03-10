package com.cdac.egov.mmgsy;


import java.util.ArrayList;

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
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ViewFlipper;

public class NqmLSBCompletedGradingActivity extends AppBaseActivity implements OnClickListener, OnCheckedChangeListener {

	private ViewFlipper viewFlipper;
	private Button nextBtn;
	private Button previousBtn;
	private RadioGroup nqmLSBQualityArrangementRadioGroup;
	private RadioGroup nqmLSBFoundationStageIRadioGroup;
	private RadioGroup nqmLSBSubstructurestageIIRadioGroup;
	private RadioGroup nqmLSBSuperstructurestageIIIRadioGroup;
	private RadioGroup nqmLSBContractionjointRadioGroup;
	private RadioGroup nqmLSBContractionjointSpacingRadioGroup;
	private RadioGroup nqmLSBContractionjointCrossSectionRadioGroup;
	private RadioGroup nqmLSBResultofloadtestRadioGroup;
	private RadioGroup nqmLSBApproachesstageIIandIIIRadioGroup;
	private RadioGroup nqmLSBContractionjointCrossSectionEmbankmentRadioGroup;
	private RadioGroup nqmLSBContractionjointCrossSectionSubbaseRadioGroup;
	private RadioGroup nqmLSBContractionjointCrossSectionBasecourseRadioGroup;
	private RadioGroup nqmLSBContractionjointCrossSectionWearingcourseRadioGroup;
	private RadioGroup nqmOverallGradeRadioGroup;
	private Activity parent;
	private Button saveBtn;
	private Button cancelBtn;
	private ScheduleDetailsDTO roadDetailsDTO;
	private ArrayList<String> selectedRadioList;
	private String entryMode;
	
	/*Changes done by Mahavir Tirkey*/
	String pattern = "^[0-9a-zA-Z ]*$";
	private TextView nqmMaintenanceOfRoadRestorationCommentEditText;
	private TextView nqmRestorationOfRainCutsCommentEditText;
	private TextView nqmConditionOfPavementCommentEditText;
	private TextView nqmMaintenanceOfDrainsCommentEditText;
	private TextView nqmMaintenanceOfRoadSignsCommentEditText;
	private TextView nqmMaintenanceOfGuardRailsCommentEditText;
	private TextView nqmOverallGradeCommentEditText;
	private TextView gradingFromChainageIdEditText;
	private TextView gradingToChainageIdEditText;
	/*End*/
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = NqmLSBCompletedGradingActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nqm_completed_grading);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.nqmLSBCompletedGradingFormViewFlipper);
		nextBtn = (Button) findViewById(R.id.nqmLSBCompletedGradingFormNextBtnId);
        previousBtn = (Button) findViewById(R.id.nqmLSBCompletedGradingFormPrevBtnId);
        saveBtn = (Button) findViewById(R.id.nqmLSBCompletedGradingFormSaveBtn);
        cancelBtn = (Button) findViewById(R.id.nqmLSBCompletedGradingFormCancelBtn);
        
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
        nqmLSBQualityArrangementRadioGroup  = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormQualityArrangementRadioGroup);
        nqmLSBFoundationStageIRadioGroup  = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormFoundationStageIRadioGroup);
        nqmLSBSubstructurestageIIRadioGroup = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormSubstructurestageIIRadioGroup);
        nqmLSBSuperstructurestageIIIRadioGroup = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormSuperstructurestageIIIRadioGroup);
        nqmLSBContractionjointRadioGroup = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormContractionjointRadioGroup);
        nqmLSBContractionjointSpacingRadioGroup = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormContractionjointSpacingRadioGroup);
        nqmLSBContractionjointCrossSectionRadioGroup = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormContractionjointCrossSectionRadioGroup);
        nqmLSBResultofloadtestRadioGroup = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormResultofloadtestRadioGroup);
        nqmLSBApproachesstageIIandIIIRadioGroup = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormApproachesstageIIandIIIRadioGroup);
        nqmLSBContractionjointCrossSectionEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormContractionjointCrossSectionEmbankmentRadioGroup);
        nqmLSBContractionjointCrossSectionSubbaseRadioGroup = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormContractionjointCrossSectionSubbaseRadioGroup);
        nqmLSBContractionjointCrossSectionBasecourseRadioGroup = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormContractionjointCrossSectionBasecourseRadioGroup);
        nqmLSBContractionjointCrossSectionWearingcourseRadioGroup = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormContractionjointCrossSectionWearingcourseRadioGroup);
    	nqmOverallGradeRadioGroup = (RadioGroup) findViewById(R.id.nqmLSBCompletedGradingFormOverallGradeRadioGroup);
    	
    	nqmLSBQualityArrangementRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBFoundationStageIRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBSubstructurestageIIRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBSuperstructurestageIIIRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointSpacingRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointCrossSectionRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBResultofloadtestRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBApproachesstageIIandIIIRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointCrossSectionEmbankmentRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointCrossSectionSubbaseRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointCrossSectionBasecourseRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointCrossSectionWearingcourseRadioGroup.setOnCheckedChangeListener(this);
    	
    	gradingFromChainageIdEditText = (TextView)findViewById(R.id.gradingFormFromChainageIdEditText);
    	gradingFromChainageIdEditText.setVisibility(View.GONE);
    	gradingToChainageIdEditText = (TextView)findViewById(R.id.gradingFormToChainageIdEditText);
    	gradingToChainageIdEditText.setVisibility(View.GONE);
    	/*Changes Done by Mahavir Tirkey*/
    	/*nqmMaintenanceOfRoadRestorationCommentEditText = (TextView)findViewById(R.id.nqmMaintenanceGradingFormMaintenanceOfRoadRestorationCommentEditText);
    	nqmMaintenanceOfRoadRestorationCommentEditText.setVisibility(View.GONE);
    	nqmRestorationOfRainCutsCommentEditText = (TextView)findViewById(R.id.nqmMaintenanceGradingFormRestorationOfRainCutsCommentEditText);
    	nqmRestorationOfRainCutsCommentEditText.setVisibility(View.GONE);
    	nqmConditionOfPavementCommentEditText = (TextView)findViewById(R.id.nqmMaintenanceGradingFormConditionOfPavementCommentEditText);
    	nqmConditionOfPavementCommentEditText.setVisibility(View.GONE);
    	nqmMaintenanceOfDrainsCommentEditText = (TextView)findViewById(R.id.nqmMaintenanceGradingFormMaintenanceOfDrainsCommentEditText);
    	nqmMaintenanceOfDrainsCommentEditText.setVisibility(View.GONE);
    	nqmMaintenanceOfRoadSignsCommentEditText = (TextView)findViewById(R.id.nqmMaintenanceGradingFormMaintenanceOfRoadSignsCommentEditText);
    	nqmMaintenanceOfRoadSignsCommentEditText.setVisibility(View.GONE);
    	nqmMaintenanceOfGuardRailsCommentEditText = (TextView)findViewById(R.id.nqmMaintenanceGradingFormMaintenanceOfGuardRailsCommentEditText);
    	nqmMaintenanceOfGuardRailsCommentEditText.setVisibility(View.GONE);
     	nqmOverallGradeCommentEditText = (TextView)findViewById(R.id.nqmMaintenanceGradingFormOverallGradeCommentEditText);
     	nqmOverallGradeCommentEditText.setVisibility(View.GONE);*/
     	/*End*/
    	
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
		if(v == nextBtn){
			/*Changes Done by Mahavir Tirkey*/
			/*if(validateuserComment()){
			    viewFlipper.showNext(); 
			}else{
				return;
			}*/
			/*End*/
			 viewFlipper.showNext(); 
		} 
	    if(v == previousBtn){
	    	/*Changes Done by Mahavir Tirkey*/
	    	/*if(validateuserComment()){
	    	   viewFlipper.showPrevious();
	    	}else{
	    		return;
	    	}*/
	    	/*End*/
	    	 viewFlipper.showPrevious();
	    } 	   
		
	    if(v.getId() == R.id.nqmLSBCompletedGradingFormSaveBtn){
	    	   saveGradingValues();
		    }
	       
	    if(v.getId() == R.id.nqmLSBCompletedGradingFormCancelBtn){
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

		RadioButton S = (RadioButton) findViewById(R.id.nqmLSBCompletedGradingFormOverallGradeRadio_S);
		RadioButton SRI = (RadioButton) findViewById(R.id.nqmLSBCompletedGradingFormOverallGradeRadio_SRI);
		RadioButton U = (RadioButton) findViewById(R.id.nqmLSBCompletedGradingFormOverallGradeRadio_U);
		RadioButton NA = (RadioButton) findViewById(R.id.nqmLSBCompletedGradingFormOverallGradeRadio_NA);
		
		RadioButton Contractionjoint_S = (RadioButton) findViewById(R.id.nqmLSBCompletedGradingFormContractionjointRadio_S);
		RadioButton Contractionjoint_SRI = (RadioButton) findViewById(R.id.nqmLSBCompletedGradingFormContractionjointRadio_SRI);
		RadioButton Contractionjoint_U = (RadioButton) findViewById(R.id.nqmLSBCompletedGradingFormContractionjointRadio_U);
		RadioButton Contractionjoint_NA = (RadioButton) findViewById(R.id.nqmLSBCompletedGradingFormContractionjointRadio_NA);
		
		RadioButton ApproachesstageIIandIII_S = (RadioButton) findViewById(R.id.nqmLSBCompletedGradingFormApproachesstageIIandIIIRadio_S);
		RadioButton ApproachesstageIIandIII_SRI = (RadioButton) findViewById(R.id.nqmLSBCompletedGradingFormApproachesstageIIandIIIRadio_SRI);
		RadioButton ApproachesstageIIandIII_U = (RadioButton) findViewById(R.id.nqmLSBCompletedGradingFormApproachesstageIIandIIIRadio_U);
		RadioButton ApproachesstageIIandIII_NA = (RadioButton) findViewById(R.id.nqmLSBCompletedGradingFormApproachesstageIIandIIIRadio_NA);
	
		RadioButton selectedRadioButton1 = (RadioButton) findViewById(nqmLSBQualityArrangementRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton2 = (RadioButton) findViewById(nqmLSBFoundationStageIRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton3 = (RadioButton) findViewById(nqmLSBSubstructurestageIIRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton4 = (RadioButton) findViewById(nqmLSBSuperstructurestageIIIRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton5 = (RadioButton) findViewById(nqmLSBContractionjointRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton6 = (RadioButton) findViewById(nqmLSBContractionjointSpacingRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton7 = (RadioButton) findViewById(nqmLSBContractionjointCrossSectionRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton8 = (RadioButton) findViewById(nqmLSBResultofloadtestRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton9 = (RadioButton) findViewById(nqmLSBApproachesstageIIandIIIRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton10 = (RadioButton) findViewById(nqmLSBContractionjointCrossSectionEmbankmentRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton11 = (RadioButton) findViewById(nqmLSBContractionjointCrossSectionSubbaseRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton12 = (RadioButton) findViewById(nqmLSBContractionjointCrossSectionBasecourseRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton13 = (RadioButton) findViewById(nqmLSBContractionjointCrossSectionWearingcourseRadioGroup.getCheckedRadioButtonId());

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
						
		Integer count = 0;
		for (String string : selectvalueArrayList) {

			if (string.equalsIgnoreCase("NA")) {
				count++;
			}
		}
		
		/*Changes Done by Mahavir Tirkey*/
		//activateDeactivateUserComment(selectvalueArrayList);
		/*END*/
		
		if(selectvalueArrayList.get(5).equalsIgnoreCase("U") || selectvalueArrayList.get(6).equalsIgnoreCase("U")){
			Contractionjoint_U.setChecked(true);
		}else if(selectvalueArrayList.get(5).equalsIgnoreCase("SRI") || selectvalueArrayList.get(6).equalsIgnoreCase("SRI")){
			Contractionjoint_SRI.setChecked(true);
		}else if(selectvalueArrayList.get(5).equalsIgnoreCase("NA") || selectvalueArrayList.get(6).equalsIgnoreCase("NA")){
			Contractionjoint_NA.setChecked(true);
		}else{
			Contractionjoint_S.setChecked(true);
		}
		
		if(selectvalueArrayList.get(9).equalsIgnoreCase("U") || selectvalueArrayList.get(10).equalsIgnoreCase("U")||selectvalueArrayList.get(11).equalsIgnoreCase("U") || selectvalueArrayList.get(12).equalsIgnoreCase("U")){
			ApproachesstageIIandIII_U.setChecked(true);
		}else if(selectvalueArrayList.get(9).equalsIgnoreCase("SRI") || selectvalueArrayList.get(10).equalsIgnoreCase("SRI")||selectvalueArrayList.get(11).equalsIgnoreCase("SRI") || selectvalueArrayList.get(12).equalsIgnoreCase("SRI")){
			ApproachesstageIIandIII_SRI.setChecked(true);
		}else if(selectvalueArrayList.get(9).equalsIgnoreCase("NA") || selectvalueArrayList.get(10).equalsIgnoreCase("NA")||selectvalueArrayList.get(11).equalsIgnoreCase("NA") || selectvalueArrayList.get(12).equalsIgnoreCase("NA")){
			ApproachesstageIIandIII_NA.setChecked(true);
		}else{
			ApproachesstageIIandIII_S.setChecked(true);
		}
		
		
		if (selectvalueArrayList.size() == count) {
			NA.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//nqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//nqmOverallGradeCommentEditText.setText("");
			/*End*/
		
		}else if(selectvalueArrayList.get(1).equalsIgnoreCase("U") || selectvalueArrayList.get(2).equalsIgnoreCase("U")|| selectvalueArrayList.get(3).equalsIgnoreCase("U") ){
			
			U.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//nqmOverallGradeCommentEditText.setVisibility(View.VISIBLE);
			/*End*/
		
		}else if((selectvalueArrayList.get(1).equalsIgnoreCase("S") && selectvalueArrayList.get(2).equalsIgnoreCase("S") && selectvalueArrayList.get(3).equalsIgnoreCase("S")) && (selectvalueArrayList.get(0).equalsIgnoreCase("U") || selectvalueArrayList.get(5).equalsIgnoreCase("U") || selectvalueArrayList.get(6).equalsIgnoreCase("U") || selectvalueArrayList.get(7).equalsIgnoreCase("U")|| selectvalueArrayList.get(9).equalsIgnoreCase("U")|| selectvalueArrayList.get(10).equalsIgnoreCase("U")|| selectvalueArrayList.get(11).equalsIgnoreCase("U")|| selectvalueArrayList.get(12).equalsIgnoreCase("U"))){
			
			SRI.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//nqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//nqmOverallGradeCommentEditText.setText("");
			/*End*/
		
		}else if((selectvalueArrayList.get(1).equalsIgnoreCase("S") && selectvalueArrayList.get(2).equalsIgnoreCase("S") && selectvalueArrayList.get(3).equalsIgnoreCase("S")) && (selectvalueArrayList.get(0).equalsIgnoreCase("SRI") || selectvalueArrayList.get(5).equalsIgnoreCase("SRI") || selectvalueArrayList.get(6).equalsIgnoreCase("SRI") || selectvalueArrayList.get(7).equalsIgnoreCase("SRI")|| selectvalueArrayList.get(9).equalsIgnoreCase("SRI")|| selectvalueArrayList.get(10).equalsIgnoreCase("SRI")|| selectvalueArrayList.get(11).equalsIgnoreCase("SRI")|| selectvalueArrayList.get(12).equalsIgnoreCase("SRI"))){
			
			SRI.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//nqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//nqmOverallGradeCommentEditText.setText("");
			/*End*/
		
		}else if((selectvalueArrayList.get(1).equalsIgnoreCase("S") && selectvalueArrayList.get(2).equalsIgnoreCase("S") && selectvalueArrayList.get(3).equalsIgnoreCase("S")) && (selectvalueArrayList.get(0).equalsIgnoreCase("S") || selectvalueArrayList.get(5).equalsIgnoreCase("S") || selectvalueArrayList.get(6).equalsIgnoreCase("S") || selectvalueArrayList.get(7).equalsIgnoreCase("S")|| selectvalueArrayList.get(9).equalsIgnoreCase("S")|| selectvalueArrayList.get(10).equalsIgnoreCase("S")|| selectvalueArrayList.get(11).equalsIgnoreCase("S")|| selectvalueArrayList.get(12).equalsIgnoreCase("S"))){
			
			S.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//nqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//nqmOverallGradeCommentEditText.setText("");
			/*End*/
		
		}else if((selectvalueArrayList.get(1).equalsIgnoreCase("NA") && selectvalueArrayList.get(2).equalsIgnoreCase("NA") && selectvalueArrayList.get(3).equalsIgnoreCase("NA")) && (selectvalueArrayList.get(0).equalsIgnoreCase("NA") || selectvalueArrayList.get(5).equalsIgnoreCase("NA") || selectvalueArrayList.get(6).equalsIgnoreCase("NA") || selectvalueArrayList.get(7).equalsIgnoreCase("NA")|| selectvalueArrayList.get(9).equalsIgnoreCase("NA")|| selectvalueArrayList.get(10).equalsIgnoreCase("NA")|| selectvalueArrayList.get(11).equalsIgnoreCase("NA")|| selectvalueArrayList.get(12).equalsIgnoreCase("NA"))){
			
			NA.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//nqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//nqmOverallGradeCommentEditText.setText("");
			/*End*/
		
		}else{
			
			S.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//nqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//nqmOverallGradeCommentEditText.setText("");
			/*End*/
		}

		
	} // calculateOverAllGrading ends here 

public void saveGradingValues(){
		
		selectedRadioList = new ArrayList<String>();
		ArrayList<RadioButton> selectedRadioButtonArrayList  = new ArrayList<RadioButton>();
		
		nqmLSBQualityArrangementRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBFoundationStageIRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBSubstructurestageIIRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBSuperstructurestageIIIRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointSpacingRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointCrossSectionRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBResultofloadtestRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBApproachesstageIIandIIIRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointCrossSectionEmbankmentRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointCrossSectionSubbaseRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointCrossSectionBasecourseRadioGroup.setOnCheckedChangeListener(this);
    	nqmLSBContractionjointCrossSectionWearingcourseRadioGroup.setOnCheckedChangeListener(this);
 
		
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBQualityArrangementRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBFoundationStageIRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBSubstructurestageIIRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBSuperstructurestageIIIRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBContractionjointRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBContractionjointSpacingRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBContractionjointCrossSectionRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBResultofloadtestRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBApproachesstageIIandIIIRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBContractionjointCrossSectionEmbankmentRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBContractionjointCrossSectionSubbaseRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBContractionjointCrossSectionBasecourseRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(nqmLSBContractionjointCrossSectionWearingcourseRadioGroup.getCheckedRadioButtonId()));

		selectedRadioButtonArrayList.add( (RadioButton) findViewById(nqmOverallGradeRadioGroup.getCheckedRadioButtonId()));
		
		/*Changes Done by Mahavir Tirkey*/
		/*RadioButton selectedRadioButton = (RadioButton) findViewById(nqmOverallGradeRadioGroup.getCheckedRadioButtonId());
		ArrayList<String> selectvalueArrayList = new ArrayList<String>();
		selectvalueArrayList.add(selectedRadioButton.getText().toString());*/
		/*End*/
		for (RadioButton radioButton : selectedRadioButtonArrayList) {
			selectedRadioList.add(radioButton.getText().toString());
		}
		
		selectedRadioList.get(selectedRadioList.size()-1);
		// ------------------- Dialog Box Code ---------------------------------------
		
		/*if(!QMSHelper.validateChainage(parent, roadDetailsDTO)){
			return;
		}*/
		
		/*if(!validateuserComment()){
			return;
		}*/
		/*Changes Done by Mahavir Tirkey*/
		/*if(selectvalueArrayList.get(0).equals("U")){
			if(nqmOverallGradeCommentEditText.getText().toString().length() == 0 ){
				Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
				QMSHelper.blink(nqmOverallGradeCommentEditText);
				return;
			}else{
				if(!nqmOverallGradeCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(nqmOverallGradeCommentEditText);
					return;	
				}
			}
		}*/
		/*End*/
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
					
						float fromChainage = 0;
						float toChainage = 0;
					/*	float fromChainage = Float.parseFloat(((EditText)  parent.findViewById(R.id.gradingFormFromChainageIdEditText)).getText().toString());
						float toChainage = Float.parseFloat(((EditText) parent.findViewById(R.id.gradingFormToChainageIdEditText)).getText().toString());
					*/	ObservationDetailsDAO observationDetailsDAOObj = new ObservationDetailsDAO();
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
							 //Changes Done By Mahavir
							final Intent intent = new Intent(NqmLSBCompletedGradingActivity.this,StandardMenuActivity.class);
							startActivity(intent);
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

	/*Changes Done by Mahavir Tirkey*/
	public void activateDeactivateUserComment(ArrayList<String> pramselectvalueArrayList){
		if(pramselectvalueArrayList.get(0).equals("U")){
			nqmMaintenanceOfRoadRestorationCommentEditText.setVisibility(View.VISIBLE);
		}else{
			nqmMaintenanceOfRoadRestorationCommentEditText.setVisibility(View.GONE);
			nqmMaintenanceOfRoadRestorationCommentEditText.setText("");
		}
		if(pramselectvalueArrayList.get(1).equals("U")){
			nqmRestorationOfRainCutsCommentEditText.setVisibility(View.VISIBLE);
		}else{
			nqmRestorationOfRainCutsCommentEditText.setVisibility(View.GONE);
			nqmRestorationOfRainCutsCommentEditText.setText("");
		}
		if(pramselectvalueArrayList.get(2).equals("U")){
			nqmConditionOfPavementCommentEditText.setVisibility(View.VISIBLE);
		}else{
			nqmConditionOfPavementCommentEditText.setVisibility(View.GONE);
			nqmConditionOfPavementCommentEditText.setText("");
		}
		if(pramselectvalueArrayList.get(3).equals("U")){
			nqmMaintenanceOfDrainsCommentEditText.setVisibility(View.VISIBLE);
		}else{
			nqmMaintenanceOfDrainsCommentEditText.setVisibility(View.GONE);
			nqmMaintenanceOfDrainsCommentEditText.setText("");
		}
		if(pramselectvalueArrayList.get(4).equals("U")){
			nqmMaintenanceOfRoadSignsCommentEditText.setVisibility(View.VISIBLE);
		}else{
			nqmMaintenanceOfRoadSignsCommentEditText.setVisibility(View.GONE);
			nqmMaintenanceOfRoadSignsCommentEditText.setText("");
		}
		if(pramselectvalueArrayList.get(5).equals("U")){
			nqmMaintenanceOfGuardRailsCommentEditText.setVisibility(View.VISIBLE);
		}else{
			nqmMaintenanceOfGuardRailsCommentEditText.setVisibility(View.GONE);
			nqmMaintenanceOfGuardRailsCommentEditText.setText("");
		}
	}
	/*End*/
	
	/*Changes Done by Mahavir Tirkey*/
	/*public boolean validateuserComment(){

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
		
		
		
		if(selectvalueArrayList.get(0).equals("U")){
			if(nqmMaintenanceOfRoadRestorationCommentEditText.getText().toString().length() == 0 ){
					Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(nqmMaintenanceOfRoadRestorationCommentEditText);
					return Boolean.FALSE;
			}else{
				if(!nqmMaintenanceOfRoadRestorationCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(nqmMaintenanceOfRoadRestorationCommentEditText);
					return Boolean.FALSE;	
				}
			}
		}
		if(selectvalueArrayList.get(1).equals("U")){
			if(nqmRestorationOfRainCutsCommentEditText.getText().toString().length() == 0 ){
				Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
				QMSHelper.blink(nqmRestorationOfRainCutsCommentEditText);
				return Boolean.FALSE;
			}else{
				if(!nqmRestorationOfRainCutsCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(nqmRestorationOfRainCutsCommentEditText);
					return Boolean.FALSE;	
				}
			}
		}
		if(selectvalueArrayList.get(2).equals("U")){
			if(nqmConditionOfPavementCommentEditText.getText().toString().length() == 0 ){
				Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
				QMSHelper.blink(nqmConditionOfPavementCommentEditText);
				return Boolean.FALSE;
			}else{
				if(!nqmConditionOfPavementCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(nqmConditionOfPavementCommentEditText);
					return Boolean.FALSE;	
				}
			}
		}
		if(selectvalueArrayList.get(3).equals("U")){
			if(nqmMaintenanceOfDrainsCommentEditText.getText().toString().length() == 0 ){
				Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
				QMSHelper.blink(nqmMaintenanceOfDrainsCommentEditText);
				return Boolean.FALSE;
			}else{
				if(!nqmMaintenanceOfDrainsCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(nqmMaintenanceOfDrainsCommentEditText);
					return Boolean.FALSE;	
				}
			}
		}
		if(selectvalueArrayList.get(4).equals("U")){
			if(nqmMaintenanceOfRoadSignsCommentEditText.getText().toString().length() == 0 ){
				Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
				QMSHelper.blink(nqmMaintenanceOfRoadSignsCommentEditText);
				return Boolean.FALSE;
			}else{
				if(!nqmMaintenanceOfRoadSignsCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(nqmMaintenanceOfRoadSignsCommentEditText);
					return Boolean.FALSE;	
				}
			}
		}
		if(selectvalueArrayList.get(5).equals("U")){
			if(nqmMaintenanceOfGuardRailsCommentEditText.getText().toString().length() == 0 ){
				Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
				QMSHelper.blink(nqmMaintenanceOfGuardRailsCommentEditText);
				return Boolean.FALSE;
			}else{
				if(!nqmMaintenanceOfGuardRailsCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(nqmMaintenanceOfGuardRailsCommentEditText);
					return Boolean.FALSE;	
				}
			}
		}
		
		return Boolean.TRUE;
	}*/
    /*End*/

	
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
