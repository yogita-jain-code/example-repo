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

public class SqmLSBCompletedGradingActivity extends AppBaseActivity implements OnClickListener, OnCheckedChangeListener {

	private ViewFlipper viewFlipper;
	private Button nextBtn;
	private Button previousBtn;
	private RadioGroup sqmLSBQualityArrangementRadioGroup;
	private RadioGroup sqmLSBFoundationStageIRadioGroup;
	private RadioGroup sqmLSBSubstructurestageIIRadioGroup;
	private RadioGroup sqmLSBSuperstructurestageIIIRadioGroup;
	private RadioGroup sqmLSBContractionjointRadioGroup;
	private RadioGroup sqmLSBContractionjointSpacingRadioGroup;
	private RadioGroup sqmLSBContractionjointCrossSectionRadioGroup;
	private RadioGroup sqmLSBResultofloadtestRadioGroup;
	private RadioGroup sqmLSBApproachesstageIIandIIIRadioGroup;
	private RadioGroup sqmLSBContractionjointCrossSectionEmbankmentRadioGroup;
	private RadioGroup sqmLSBContractionjointCrossSectionSubbaseRadioGroup;
	private RadioGroup sqmLSBContractionjointCrossSectionBasecourseRadioGroup;
	private RadioGroup sqmLSBContractionjointCrossSectionWearingcourseRadioGroup;
	private RadioGroup sqmOverallGradeRadioGroup;
	private Activity parent;
	private Button saveBtn;
	private Button cancelBtn;
	private ScheduleDetailsDTO roadDetailsDTO;
	private ArrayList<String> selectedRadioList;
	private String entryMode;
	
	/*Changes done by Mahavir Tirkey*/
	String pattern = "^[0-9a-zA-Z ]*$";
	private TextView sqmMaintenanceOfRoadRestorationCommentEditText;
	private TextView sqmRestorationOfRainCutsCommentEditText;
	private TextView sqmConditionOfPavementCommentEditText;
	private TextView sqmMaintenanceOfDrainsCommentEditText;
	private TextView sqmMaintenanceOfRoadSignsCommentEditText;
	private TextView sqmMaintenanceOfGuardRailsCommentEditText;
	private TextView sqmOverallGradeCommentEditText;
	private TextView gradingFromChainageIdEditText;
	private TextView gradingToChainageIdEditText;
	/*End*/
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = SqmLSBCompletedGradingActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqm_completed_lsb_grading);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.sqmLSBCompletedGradingFormViewFlipper);
		nextBtn = (Button) findViewById(R.id.sqmLSBCompletedGradingFormNextBtnId);
        previousBtn = (Button) findViewById(R.id.sqmLSBCompletedGradingFormPrevBtnId);
        saveBtn = (Button) findViewById(R.id.sqmLSBCompletedGradingFormSaveBtn);
        cancelBtn = (Button) findViewById(R.id.sqmLSBCompletedGradingFormCancelBtn);
        
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
        sqmLSBQualityArrangementRadioGroup  = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormQualityArrangementRadioGroup);
        sqmLSBFoundationStageIRadioGroup  = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormFoundationStageIRadioGroup);
        sqmLSBSubstructurestageIIRadioGroup = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormSubstructurestageIIRadioGroup);
        sqmLSBSuperstructurestageIIIRadioGroup = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormSuperstructurestageIIIRadioGroup);
        sqmLSBContractionjointRadioGroup = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormContractionjointRadioGroup);
        sqmLSBContractionjointSpacingRadioGroup = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormContractionjointSpacingRadioGroup);
        sqmLSBContractionjointCrossSectionRadioGroup = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormContractionjointCrossSectionRadioGroup);
        sqmLSBResultofloadtestRadioGroup = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormResultofloadtestRadioGroup);
        sqmLSBApproachesstageIIandIIIRadioGroup = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormApproachesstageIIandIIIRadioGroup);
        sqmLSBContractionjointCrossSectionEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormContractionjointCrossSectionEmbankmentRadioGroup);
        sqmLSBContractionjointCrossSectionSubbaseRadioGroup = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormContractionjointCrossSectionSubbaseRadioGroup);
        sqmLSBContractionjointCrossSectionBasecourseRadioGroup = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormContractionjointCrossSectionBasecourseRadioGroup);
        sqmLSBContractionjointCrossSectionWearingcourseRadioGroup = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormContractionjointCrossSectionWearingcourseRadioGroup);
    	sqmOverallGradeRadioGroup = (RadioGroup) findViewById(R.id.sqmLSBCompletedGradingFormOverallGradeRadioGroup);
    	
    	sqmLSBQualityArrangementRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBFoundationStageIRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBSubstructurestageIIRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBSuperstructurestageIIIRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointSpacingRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointCrossSectionRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBResultofloadtestRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBApproachesstageIIandIIIRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointCrossSectionEmbankmentRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointCrossSectionSubbaseRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointCrossSectionBasecourseRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointCrossSectionWearingcourseRadioGroup.setOnCheckedChangeListener(this);
    	
    	gradingFromChainageIdEditText = (TextView)findViewById(R.id.gradingFormFromChainageIdEditText);
    	gradingFromChainageIdEditText.setVisibility(View.GONE);
    	gradingToChainageIdEditText = (TextView)findViewById(R.id.gradingFormToChainageIdEditText);
    	gradingToChainageIdEditText.setVisibility(View.GONE);
    	/*Changes Done by Mahavir Tirkey*/
    	/*sqmMaintenanceOfRoadRestorationCommentEditText = (TextView)findViewById(R.id.sqmMaintenanceGradingFormMaintenanceOfRoadRestorationCommentEditText);
    	sqmMaintenanceOfRoadRestorationCommentEditText.setVisibility(View.GONE);
    	sqmRestorationOfRainCutsCommentEditText = (TextView)findViewById(R.id.sqmMaintenanceGradingFormRestorationOfRainCutsCommentEditText);
    	sqmRestorationOfRainCutsCommentEditText.setVisibility(View.GONE);
    	sqmConditionOfPavementCommentEditText = (TextView)findViewById(R.id.sqmMaintenanceGradingFormConditionOfPavementCommentEditText);
    	sqmConditionOfPavementCommentEditText.setVisibility(View.GONE);
    	sqmMaintenanceOfDrainsCommentEditText = (TextView)findViewById(R.id.sqmMaintenanceGradingFormMaintenanceOfDrainsCommentEditText);
    	sqmMaintenanceOfDrainsCommentEditText.setVisibility(View.GONE);
    	sqmMaintenanceOfRoadSignsCommentEditText = (TextView)findViewById(R.id.sqmMaintenanceGradingFormMaintenanceOfRoadSignsCommentEditText);
    	sqmMaintenanceOfRoadSignsCommentEditText.setVisibility(View.GONE);
    	sqmMaintenanceOfGuardRailsCommentEditText = (TextView)findViewById(R.id.sqmMaintenanceGradingFormMaintenanceOfGuardRailsCommentEditText);
    	sqmMaintenanceOfGuardRailsCommentEditText.setVisibility(View.GONE);
     	sqmOverallGradeCommentEditText = (TextView)findViewById(R.id.sqmMaintenanceGradingFormOverallGradeCommentEditText);
     	sqmOverallGradeCommentEditText.setVisibility(View.GONE);*/
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
		getMenuInflater().inflate(R.menu.sqm_maintenance_grading, menu);
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
		
	    if(v.getId() == R.id.sqmLSBCompletedGradingFormSaveBtn){
	    	   saveGradingValues();
		    }
	       
	    if(v.getId() == R.id.sqmLSBCompletedGradingFormCancelBtn){
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

		RadioButton S = (RadioButton) findViewById(R.id.sqmLSBCompletedGradingFormOverallGradeRadio_S);
		RadioButton SRI = (RadioButton) findViewById(R.id.sqmLSBCompletedGradingFormOverallGradeRadio_SRI);
		RadioButton U = (RadioButton) findViewById(R.id.sqmLSBCompletedGradingFormOverallGradeRadio_U);
		RadioButton NA = (RadioButton) findViewById(R.id.sqmLSBCompletedGradingFormOverallGradeRadio_NA);
		
		RadioButton Contractionjoint_S = (RadioButton) findViewById(R.id.sqmLSBCompletedGradingFormContractionjointRadio_S);
		RadioButton Contractionjoint_SRI = (RadioButton) findViewById(R.id.sqmLSBCompletedGradingFormContractionjointRadio_SRI);
		RadioButton Contractionjoint_U = (RadioButton) findViewById(R.id.sqmLSBCompletedGradingFormContractionjointRadio_U);
		RadioButton Contractionjoint_NA = (RadioButton) findViewById(R.id.sqmLSBCompletedGradingFormContractionjointRadio_NA);
		
		RadioButton ApproachesstageIIandIII_S = (RadioButton) findViewById(R.id.sqmLSBCompletedGradingFormApproachesstageIIandIIIRadio_S);
		RadioButton ApproachesstageIIandIII_SRI = (RadioButton) findViewById(R.id.sqmLSBCompletedGradingFormApproachesstageIIandIIIRadio_SRI);
		RadioButton ApproachesstageIIandIII_U = (RadioButton) findViewById(R.id.sqmLSBCompletedGradingFormApproachesstageIIandIIIRadio_U);
		RadioButton ApproachesstageIIandIII_NA = (RadioButton) findViewById(R.id.sqmLSBCompletedGradingFormApproachesstageIIandIIIRadio_NA);
	
		RadioButton selectedRadioButton1 = (RadioButton) findViewById(sqmLSBQualityArrangementRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton2 = (RadioButton) findViewById(sqmLSBFoundationStageIRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton3 = (RadioButton) findViewById(sqmLSBSubstructurestageIIRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton4 = (RadioButton) findViewById(sqmLSBSuperstructurestageIIIRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton5 = (RadioButton) findViewById(sqmLSBContractionjointRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton6 = (RadioButton) findViewById(sqmLSBContractionjointSpacingRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton7 = (RadioButton) findViewById(sqmLSBContractionjointCrossSectionRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton8 = (RadioButton) findViewById(sqmLSBResultofloadtestRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton9 = (RadioButton) findViewById(sqmLSBApproachesstageIIandIIIRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton10 = (RadioButton) findViewById(sqmLSBContractionjointCrossSectionEmbankmentRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton11 = (RadioButton) findViewById(sqmLSBContractionjointCrossSectionSubbaseRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton12 = (RadioButton) findViewById(sqmLSBContractionjointCrossSectionBasecourseRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton13 = (RadioButton) findViewById(sqmLSBContractionjointCrossSectionWearingcourseRadioGroup.getCheckedRadioButtonId());

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
		}else if(selectvalueArrayList.get(5).equalsIgnoreCase("NA") && selectvalueArrayList.get(6).equalsIgnoreCase("NA")){
			Contractionjoint_NA.setChecked(true);
		}else{
			Contractionjoint_S.setChecked(true);
		}
		
		if(selectvalueArrayList.get(9).equalsIgnoreCase("U") || selectvalueArrayList.get(10).equalsIgnoreCase("U")||selectvalueArrayList.get(11).equalsIgnoreCase("U") || selectvalueArrayList.get(12).equalsIgnoreCase("U")){
			ApproachesstageIIandIII_U.setChecked(true);
		}else if(selectvalueArrayList.get(9).equalsIgnoreCase("SRI") || selectvalueArrayList.get(10).equalsIgnoreCase("SRI")||selectvalueArrayList.get(11).equalsIgnoreCase("SRI") || selectvalueArrayList.get(12).equalsIgnoreCase("SRI")){
			ApproachesstageIIandIII_SRI.setChecked(true);
		}else if(selectvalueArrayList.get(9).equalsIgnoreCase("NA") && selectvalueArrayList.get(10).equalsIgnoreCase("NA")&& selectvalueArrayList.get(11).equalsIgnoreCase("NA") && selectvalueArrayList.get(12).equalsIgnoreCase("NA")){
			ApproachesstageIIandIII_NA.setChecked(true);
		}else{
			ApproachesstageIIandIII_S.setChecked(true);
		}
		
		
		if (selectvalueArrayList.size() == count) {
			NA.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//sqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//sqmOverallGradeCommentEditText.setText("");
			/*End*/
		
		}else if(selectvalueArrayList.get(1).equalsIgnoreCase("U") || selectvalueArrayList.get(2).equalsIgnoreCase("U")|| selectvalueArrayList.get(3).equalsIgnoreCase("U") ){
			
			U.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//sqmOverallGradeCommentEditText.setVisibility(View.VISIBLE);
			/*End*/
		
		}else if((selectvalueArrayList.get(1).equalsIgnoreCase("S") && selectvalueArrayList.get(2).equalsIgnoreCase("S") && selectvalueArrayList.get(3).equalsIgnoreCase("S")) && (selectvalueArrayList.get(0).equalsIgnoreCase("U") || selectvalueArrayList.get(5).equalsIgnoreCase("U") || selectvalueArrayList.get(6).equalsIgnoreCase("U") || selectvalueArrayList.get(7).equalsIgnoreCase("U")|| selectvalueArrayList.get(9).equalsIgnoreCase("U")|| selectvalueArrayList.get(10).equalsIgnoreCase("U")|| selectvalueArrayList.get(11).equalsIgnoreCase("U")|| selectvalueArrayList.get(12).equalsIgnoreCase("U"))){
			
			SRI.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//sqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//sqmOverallGradeCommentEditText.setText("");
			/*End*/
		
		}else if(selectvalueArrayList.get(1).equalsIgnoreCase("SRI") || selectvalueArrayList.get(2).equalsIgnoreCase("SRI") || selectvalueArrayList.get(3).equalsIgnoreCase("SRI") || selectvalueArrayList.get(0).equalsIgnoreCase("SRI") || selectvalueArrayList.get(5).equalsIgnoreCase("SRI") || selectvalueArrayList.get(6).equalsIgnoreCase("SRI") || selectvalueArrayList.get(7).equalsIgnoreCase("SRI")|| selectvalueArrayList.get(9).equalsIgnoreCase("SRI")|| selectvalueArrayList.get(10).equalsIgnoreCase("SRI")|| selectvalueArrayList.get(11).equalsIgnoreCase("SRI")|| selectvalueArrayList.get(12).equalsIgnoreCase("SRI")){ 
		  //else if((selectvalueArrayList.get(1).equalsIgnoreCase("S") && selectvalueArrayList.get(2).equalsIgnoreCase("S") && selectvalueArrayList.get(3).equalsIgnoreCase("S")) && (selectvalueArrayList.get(0).equalsIgnoreCase("SRI") || selectvalueArrayList.get(5).equalsIgnoreCase("SRI") || selectvalueArrayList.get(6).equalsIgnoreCase("SRI") || selectvalueArrayList.get(7).equalsIgnoreCase("SRI")|| selectvalueArrayList.get(9).equalsIgnoreCase("SRI")|| selectvalueArrayList.get(10).equalsIgnoreCase("SRI")|| selectvalueArrayList.get(11).equalsIgnoreCase("SRI")|| selectvalueArrayList.get(12).equalsIgnoreCase("SRI"))){
		  // else if (selectvalueArrayList.contains("SRI")) {
			SRI.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//sqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//sqmOverallGradeCommentEditText.setText("");
			/*End*/
		
		}else if((selectvalueArrayList.get(1).equalsIgnoreCase("S") && selectvalueArrayList.get(2).equalsIgnoreCase("S") && selectvalueArrayList.get(3).equalsIgnoreCase("S")) && (selectvalueArrayList.get(0).equalsIgnoreCase("S") || selectvalueArrayList.get(5).equalsIgnoreCase("S") || selectvalueArrayList.get(6).equalsIgnoreCase("S") || selectvalueArrayList.get(7).equalsIgnoreCase("S")|| selectvalueArrayList.get(9).equalsIgnoreCase("S")|| selectvalueArrayList.get(10).equalsIgnoreCase("S")|| selectvalueArrayList.get(11).equalsIgnoreCase("S")|| selectvalueArrayList.get(12).equalsIgnoreCase("S"))){
			
			S.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//sqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//sqmOverallGradeCommentEditText.setText("");
			/*End*/
		
		}else if((selectvalueArrayList.get(1).equalsIgnoreCase("NA") && selectvalueArrayList.get(2).equalsIgnoreCase("NA") && selectvalueArrayList.get(3).equalsIgnoreCase("NA")) && (selectvalueArrayList.get(0).equalsIgnoreCase("S") || selectvalueArrayList.get(5).equalsIgnoreCase("S") || selectvalueArrayList.get(6).equalsIgnoreCase("S") || selectvalueArrayList.get(7).equalsIgnoreCase("S")|| selectvalueArrayList.get(9).equalsIgnoreCase("S")|| selectvalueArrayList.get(10).equalsIgnoreCase("S")|| selectvalueArrayList.get(11).equalsIgnoreCase("S")|| selectvalueArrayList.get(12).equalsIgnoreCase("S"))){
			
			S.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//sqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//sqmOverallGradeCommentEditText.setText("");
			/*End*/
		
		}
		else if((selectvalueArrayList.get(1).equalsIgnoreCase("NA") && selectvalueArrayList.get(2).equalsIgnoreCase("NA") && selectvalueArrayList.get(3).equalsIgnoreCase("NA")) && (selectvalueArrayList.get(0).equalsIgnoreCase("NA") || selectvalueArrayList.get(5).equalsIgnoreCase("NA") || selectvalueArrayList.get(6).equalsIgnoreCase("NA") || selectvalueArrayList.get(7).equalsIgnoreCase("NA")|| selectvalueArrayList.get(9).equalsIgnoreCase("NA")|| selectvalueArrayList.get(10).equalsIgnoreCase("NA")|| selectvalueArrayList.get(11).equalsIgnoreCase("NA")|| selectvalueArrayList.get(12).equalsIgnoreCase("NA"))){
			
			NA.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//sqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//sqmOverallGradeCommentEditText.setText("");
			/*End*/
		
		}
		else{
			
			S.setChecked(true);
			/*Changes Done by Mahavir Tirkey*/
			//sqmOverallGradeCommentEditText.setVisibility(View.GONE);
			//sqmOverallGradeCommentEditText.setText("");
			/*End*/
		}

		
	} // calculateOverAllGrading ends here 

public void saveGradingValues(){
		
		selectedRadioList = new ArrayList<String>();
		ArrayList<RadioButton> selectedRadioButtonArrayList  = new ArrayList<RadioButton>();
		
		//sqmLSBQualityArrangementRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBFoundationStageIRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBSubstructurestageIIRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBSuperstructurestageIIIRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointSpacingRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointCrossSectionRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBResultofloadtestRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBApproachesstageIIandIIIRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointCrossSectionEmbankmentRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointCrossSectionSubbaseRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointCrossSectionBasecourseRadioGroup.setOnCheckedChangeListener(this);
    	sqmLSBContractionjointCrossSectionWearingcourseRadioGroup.setOnCheckedChangeListener(this);
 
		
		//selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBQualityArrangementRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBFoundationStageIRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBSubstructurestageIIRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBSuperstructurestageIIIRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBContractionjointRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBContractionjointSpacingRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBContractionjointCrossSectionRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBResultofloadtestRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBApproachesstageIIandIIIRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBContractionjointCrossSectionEmbankmentRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBContractionjointCrossSectionSubbaseRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBContractionjointCrossSectionBasecourseRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(sqmLSBContractionjointCrossSectionWearingcourseRadioGroup.getCheckedRadioButtonId()));

		selectedRadioButtonArrayList.add( (RadioButton) findViewById(sqmOverallGradeRadioGroup.getCheckedRadioButtonId()));
		
		/*Changes Done by Mahavir Tirkey*/
		/*RadioButton selectedRadioButton = (RadioButton) findViewById(sqmOverallGradeRadioGroup.getCheckedRadioButtonId());
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
			if(sqmOverallGradeCommentEditText.getText().toString().length() == 0 ){
				Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
				QMSHelper.blink(sqmOverallGradeCommentEditText);
				return;
			}else{
				if(!sqmOverallGradeCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(sqmOverallGradeCommentEditText);
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
							final Intent intent = new Intent(SqmLSBCompletedGradingActivity.this,StandardMenuActivity.class);
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
			sqmMaintenanceOfRoadRestorationCommentEditText.setVisibility(View.VISIBLE);
		}else{
			sqmMaintenanceOfRoadRestorationCommentEditText.setVisibility(View.GONE);
			sqmMaintenanceOfRoadRestorationCommentEditText.setText("");
		}
		if(pramselectvalueArrayList.get(1).equals("U")){
			sqmRestorationOfRainCutsCommentEditText.setVisibility(View.VISIBLE);
		}else{
			sqmRestorationOfRainCutsCommentEditText.setVisibility(View.GONE);
			sqmRestorationOfRainCutsCommentEditText.setText("");
		}
		if(pramselectvalueArrayList.get(2).equals("U")){
			sqmConditionOfPavementCommentEditText.setVisibility(View.VISIBLE);
		}else{
			sqmConditionOfPavementCommentEditText.setVisibility(View.GONE);
			sqmConditionOfPavementCommentEditText.setText("");
		}
		if(pramselectvalueArrayList.get(3).equals("U")){
			sqmMaintenanceOfDrainsCommentEditText.setVisibility(View.VISIBLE);
		}else{
			sqmMaintenanceOfDrainsCommentEditText.setVisibility(View.GONE);
			sqmMaintenanceOfDrainsCommentEditText.setText("");
		}
		if(pramselectvalueArrayList.get(4).equals("U")){
			sqmMaintenanceOfRoadSignsCommentEditText.setVisibility(View.VISIBLE);
		}else{
			sqmMaintenanceOfRoadSignsCommentEditText.setVisibility(View.GONE);
			sqmMaintenanceOfRoadSignsCommentEditText.setText("");
		}
		if(pramselectvalueArrayList.get(5).equals("U")){
			sqmMaintenanceOfGuardRailsCommentEditText.setVisibility(View.VISIBLE);
		}else{
			sqmMaintenanceOfGuardRailsCommentEditText.setVisibility(View.GONE);
			sqmMaintenanceOfGuardRailsCommentEditText.setText("");
		}
	}
	/*End*/
	
	/*Changes Done by Mahavir Tirkey*/
	/*public boolean validateuserComment(){

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
		
		
		
		if(selectvalueArrayList.get(0).equals("U")){
			if(sqmMaintenanceOfRoadRestorationCommentEditText.getText().toString().length() == 0 ){
					Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(sqmMaintenanceOfRoadRestorationCommentEditText);
					return Boolean.FALSE;
			}else{
				if(!sqmMaintenanceOfRoadRestorationCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(sqmMaintenanceOfRoadRestorationCommentEditText);
					return Boolean.FALSE;	
				}
			}
		}
		if(selectvalueArrayList.get(1).equals("U")){
			if(sqmRestorationOfRainCutsCommentEditText.getText().toString().length() == 0 ){
				Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
				QMSHelper.blink(sqmRestorationOfRainCutsCommentEditText);
				return Boolean.FALSE;
			}else{
				if(!sqmRestorationOfRainCutsCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(sqmRestorationOfRainCutsCommentEditText);
					return Boolean.FALSE;	
				}
			}
		}
		if(selectvalueArrayList.get(2).equals("U")){
			if(sqmConditionOfPavementCommentEditText.getText().toString().length() == 0 ){
				Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
				QMSHelper.blink(sqmConditionOfPavementCommentEditText);
				return Boolean.FALSE;
			}else{
				if(!sqmConditionOfPavementCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(sqmConditionOfPavementCommentEditText);
					return Boolean.FALSE;	
				}
			}
		}
		if(selectvalueArrayList.get(3).equals("U")){
			if(sqmMaintenanceOfDrainsCommentEditText.getText().toString().length() == 0 ){
				Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
				QMSHelper.blink(sqmMaintenanceOfDrainsCommentEditText);
				return Boolean.FALSE;
			}else{
				if(!sqmMaintenanceOfDrainsCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(sqmMaintenanceOfDrainsCommentEditText);
					return Boolean.FALSE;	
				}
			}
		}
		if(selectvalueArrayList.get(4).equals("U")){
			if(sqmMaintenanceOfRoadSignsCommentEditText.getText().toString().length() == 0 ){
				Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
				QMSHelper.blink(sqmMaintenanceOfRoadSignsCommentEditText);
				return Boolean.FALSE;
			}else{
				if(!sqmMaintenanceOfRoadSignsCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(sqmMaintenanceOfRoadSignsCommentEditText);
					return Boolean.FALSE;	
				}
			}
		}
		if(selectvalueArrayList.get(5).equals("U")){
			if(sqmMaintenanceOfGuardRailsCommentEditText.getText().toString().length() == 0 ){
				Notification.showErrorMessage(NotificationEnum.commentEditText, parent.getApplicationContext(), parent);
				QMSHelper.blink(sqmMaintenanceOfGuardRailsCommentEditText);
				return Boolean.FALSE;
			}else{
				if(!sqmMaintenanceOfGuardRailsCommentEditText.getText().toString().matches(pattern)){
					Notification.showErrorMessage(NotificationEnum.invalidcommentEditText, parent.getApplicationContext(), parent);
					QMSHelper.blink(sqmMaintenanceOfGuardRailsCommentEditText);
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
