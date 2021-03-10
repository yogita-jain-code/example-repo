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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ViewFlipper;

public class NqmCompletedGradingActivity extends AppBaseActivity implements OnClickListener, OnCheckedChangeListener {

	private Button nextBtn;
	private Button previousBtn;
	private ViewFlipper viewFlipper;
	private RadioGroup qualityArrangmentRadioGroup;
	private RadioGroup attentionToQualityRadioGroup;
	private RadioGroup geometricsRadioGroup;
	private RadioGroup earthWorkEmbankmentRadioGroup;
	private RadioGroup subBaseRadioGroup;
	private RadioGroup baseCourseWaterRadioGroup;
	private RadioGroup bituminousRadioGroup;
	private RadioGroup shoulderRadioGroup;
	private RadioGroup crossDrainageRadioGroup;
	private RadioGroup sideDrainRadioGroup;
	private RadioGroup cCSemiRigidRadioGroup;
	private RadioGroup roadFurnituresRadioGroup;
	private RadioButton qualityArrangment_S;
	private RadioButton qualityArrangment_RI;
	private RadioButton qualityArrangment_U;
	private RadioButton qualityArrangment_NA;
	private RadioButton attentionToQuality_S;
	private RadioButton attentionToQuality_SRI;
	private RadioButton attentionToQuality_U;
	private RadioButton attentionToQuality_NA;
	private RadioGroup subItemVerificationOfTestResultAttentionToQualityRadioGroup;
	private RadioButton geometrics_S;
	private RadioButton geometrics_SRI;
	private RadioButton geometrics_U;
	private RadioButton geometrics_NA;
	private RadioGroup subItemRoadWayWitdhGeometricsRadioGroup;
	private RadioGroup subItemCarriageWayWidthGeometricsRadioGroup;
	private RadioGroup subItemCamberGeometricsRadioGroup;
	private RadioGroup subItemSuperElevationGeometricsRadioGroup;
	private RadioGroup subItemLongitudinalGradientGeometricsRadioGroup;
	private RadioButton earthWorkEmbankment_S;
	private RadioButton earthWorkEmbankment_SRI;
	private RadioButton earthWorkEmbankment_U;
	private RadioButton earthWorkEmbankment_NA;
	private RadioGroup subItemQualityOfMaterialEarthWorkEmbankmentRadioGroup;
	private RadioGroup subItemCompactionEarthWorkEmbankmentRadioGroup;
	private RadioGroup subItemSideSlopesEarthWorkEmbankmentRadioGroup;
	private RadioGroup subItemStabilityEarthWorkEmbankmentRadioGroup;
	private RadioGroup subItemSlopeProtectionEarthWorkEmbankmentRadioGroup;
	private RadioButton subBase_S;
	private RadioButton subBase_SRI;
	private RadioButton subBase_U;
	private RadioButton subBase_NA;
	private RadioGroup subItemGrainSizeSubBaseRadioGroup;
	private RadioGroup subItemPlasticitySubBaseRadioGroup;
	private RadioGroup subItemCompactionSubBaseRadioGroup;
	private RadioGroup subItemTotalThicknessSubBaseRadioGroup;
	private RadioButton baseCourseWater_S;
	private RadioButton baseCourseWater_SRI;
	private RadioButton baseCourseWater_U;
	private RadioButton baseCourseWater_NA;
	private RadioGroup subItemGrainSizeBaseCourseWaterRadioGroup;
	private RadioGroup subItemPlasticityBaseCourseWaterRadioGroup;
	private RadioGroup subItemCompactionBaseCourseWaterRadioGroup;
	private RadioGroup subItemThicknessBaseCourseWaterRadioGroup;
	private RadioButton bituminous_S;
	private RadioButton bituminous_SRI;
	private RadioButton bituminous_U;
	private RadioButton bituminous_NA;
	private RadioGroup subItemThicknessBituminousRadioGroup;
	private RadioGroup subItemSurfaceBituminousRadioGroup;
	private RadioButton shoulders_S;
	private RadioButton shoulders_SRI;
	private RadioButton shoulders_U;
	private RadioButton shoulders_NA;
	private RadioGroup subItemQualityOfMaterialShouldersRadioGroup;
	private RadioGroup subItemDegreeOfCompactionShouldersRadioGroup;
	private RadioGroup subItemCamberShouldersRadioGroup;
	private RadioButton crossDrainage_S;
	private RadioButton crossDrainage_SRI;
	private RadioButton crossDrainage_U;
	private RadioButton crossDrainage_NA;
	private RadioGroup subItemQualityOfMaterialCrossDrainageRadioGroup;
	private RadioGroup subItemQualityOfWorkmanshipCrossDrainageRadioGroup;
	private RadioButton sideDrain_S;
	private RadioButton sideDrain_SRI;
	private RadioButton sideDrain_U;
	private RadioButton sideDrain_NA;
	private RadioGroup subItemGeneralQualitySideDrainRadioGroup;
	private RadioButton cCSemiRigidPavement_S;
	private RadioButton cCSemiRigidPavement_SRI;
	private RadioButton cCSemiRigidPavement_U;
	private RadioButton cCSemiRigidPavement_NA;
	private RadioGroup subItemQualityMaterialCCSemiRigidPavementRadioGroup;
	private RadioGroup subItemStrengthCCSemiRigidPavementRadioGroup;
	private RadioGroup subItemQualityCCSemiRigidPavementRadioGroup;
	private RadioGroup subItemThicknessCCSemiRigidPavementRadioGroup;
	private RadioButton roadFurnitures_S;
	private RadioButton roadFurnitures_SRI;
	private RadioButton roadFurnitures_U;
	private RadioButton roadFurnitures_NA;
	private RadioGroup subItemLogoBoardsRoadFurnituresRadioGroup;
	private RadioGroup subItemWhetherRoadFurnituresRadioGroup;
	private RadioGroup overallGradeRadioGroup;
	private Activity parent;
	private ArrayList<String> selectedRadioList;
	private Button saveBtn;
	private Button cancelBtn;
	private ScheduleDetailsDTO roadDetailsDTO;
	private String entryMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parent = NqmCompletedGradingActivity.this;
		setContentView(R.layout.activity_nqm_completed_grading);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.nqmCompletedGradingFormViewFlipper);
		nextBtn = (Button) findViewById(R.id.nqmCompletedGradingFormNextBtnId);
        previousBtn = (Button) findViewById(R.id.nqmCompletedGradingFormPrevBtnId);
        saveBtn = (Button) findViewById(R.id.nqmCompletedGradingFormSaveBtn);
        cancelBtn = (Button) findViewById(R.id.nqmCompletedGradingFormCancelBtn);
        
        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        previousBtn.setOnClickListener(this);
        
        // Set Road Details for Grading forms
        Intent intent = getIntent();
        String  monitorName =  (String) intent.getStringExtra("monitorName");
        entryMode =  (String) intent.getStringExtra("entryMode");
        roadDetailsDTO = (ScheduleDetailsDTO) intent.getSerializableExtra("inpectionEntryRoadDTO");
        
        QMSHelper.setInpsectionFormDetails(parent, monitorName, roadDetailsDTO, this, this);
        
    // -------------------------------- Grading Radio Group Listeners -------------------------------------
	qualityArrangmentRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormQualityArrangmentRadioGroup);
	attentionToQualityRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormAttentionToQualityRadioGroup);
	geometricsRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormGeometricsRadioGroup);
	earthWorkEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormEarthWorkEmbankmentRadioGroup);
	subBaseRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormSubBaseRadioGroup);
	baseCourseWaterRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormBaseCourseWaterRadioGroup);
	bituminousRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormBituminousRadioGroup);
	shoulderRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormShouldersRadioGroup);
	crossDrainageRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormCrossDrainageRadioGroup); 
	sideDrainRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormSideDrainRadioGroup);
	cCSemiRigidRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormCCSemiRigidPavementRadioGroup);
	roadFurnituresRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormRoadFurnituresRadioGroup);
     
	//1st Item
	qualityArrangment_S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormQualityArrangmentRadio_S);
	qualityArrangment_RI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormQualityArrangmentRadio_SRI);
	qualityArrangment_U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormQualityArrangmentRadio_U);
	qualityArrangment_NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormQualityArrangmentRadio_NA);
	
	// 2nd Item
	attentionToQuality_S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormAttentionToQualityRadio_S);
	attentionToQuality_SRI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormAttentionToQualityRadio_SRI);
	attentionToQuality_U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormAttentionToQualityRadio_U);
	attentionToQuality_NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormAttentionToQualityRadio_NA);
	
	//2nd Item - SubItems
	subItemVerificationOfTestResultAttentionToQualityRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormVerificationOfTestResultsAttentionToQualityRadioGroup);
	
	//Check Change Listener
	subItemVerificationOfTestResultAttentionToQualityRadioGroup.setOnCheckedChangeListener(this);
	
	//--------------------------------------------------------------------
	
	
	// 3rd Item
	geometrics_S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormGeometricsRadio_S);
	geometrics_SRI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormGeometricsRadio_SRI);
	geometrics_U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormGeometricsRadio_U);
	geometrics_NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormGeometricsRadio_NA);
	
	//3rd Item - SubItems
	subItemRoadWayWitdhGeometricsRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormRoadWayWitdhGeometricsRadioGroup);
	subItemCarriageWayWidthGeometricsRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormCarriageWayWidthGeometricsRadioGroup);
	subItemCamberGeometricsRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormCamberGeometricsRadioGroup);
	subItemSuperElevationGeometricsRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormSuperElevationGeometricsRadioGroup);
	subItemLongitudinalGradientGeometricsRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormLongitudinalGradientGeometricsRadioGroup);
	
	//Check Change Listener
	subItemRoadWayWitdhGeometricsRadioGroup.setOnCheckedChangeListener(this);
	subItemCarriageWayWidthGeometricsRadioGroup.setOnCheckedChangeListener(this);
	subItemCamberGeometricsRadioGroup.setOnCheckedChangeListener(this);
	subItemSuperElevationGeometricsRadioGroup.setOnCheckedChangeListener(this);
	subItemLongitudinalGradientGeometricsRadioGroup.setOnCheckedChangeListener(this);
	
	//--------------------------------------------------------------------
	
	
	//4th Item
	earthWorkEmbankment_S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormEarthWorkEmbankmentRadio_S);
	earthWorkEmbankment_SRI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormEarthWorkEmbankmentRadio_SRI);
	earthWorkEmbankment_U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormEarthWorkEmbankmentRadio_U);
	earthWorkEmbankment_NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormEarthWorkEmbankmentRadio_NA);
	
	//4th Item - SubItems
	subItemQualityOfMaterialEarthWorkEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormQualityOfMaterialEarthWorkEmbankmentRadioGroup);
	subItemCompactionEarthWorkEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormCompactionEarthWorkEmbankmentRadioGroup);
	subItemSideSlopesEarthWorkEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormSideSlopesEarthWorkEmbankmentRadioGroup);
	subItemStabilityEarthWorkEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormStabilityEarthWorkEmbankmentRadioGroup);
	subItemSlopeProtectionEarthWorkEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormSlopeProtectionEarthWorkEmbankmentRadioGroup);
	
	
	//Check Change Listener
	subItemQualityOfMaterialEarthWorkEmbankmentRadioGroup.setOnCheckedChangeListener(this);
	subItemCompactionEarthWorkEmbankmentRadioGroup.setOnCheckedChangeListener(this);
	subItemSideSlopesEarthWorkEmbankmentRadioGroup.setOnCheckedChangeListener(this);
	subItemStabilityEarthWorkEmbankmentRadioGroup.setOnCheckedChangeListener(this);
	subItemSlopeProtectionEarthWorkEmbankmentRadioGroup.setOnCheckedChangeListener(this);
	
	//--------------------------------------------------------------------
	
	//5th Item
	subBase_S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormSubBaseRadio_S);
	subBase_SRI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormSubBaseRadio_SRI);
	subBase_U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormSubBaseRadio_U);
	subBase_NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormSubBaseRadio_NA);
	
	//5th Item - SubItems
	subItemGrainSizeSubBaseRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormGrainSizeSubBaseRadioGroup);
	subItemPlasticitySubBaseRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormPlasticitySubBaseRadioGroup);
	subItemCompactionSubBaseRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormCompactionSubBaseRadioGroup);
	subItemTotalThicknessSubBaseRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormTotalThicknessSubBaseRadioGroup);
	
	//Check Change Listener
	subItemGrainSizeSubBaseRadioGroup.setOnCheckedChangeListener(this);
	subItemPlasticitySubBaseRadioGroup.setOnCheckedChangeListener(this);
	subItemCompactionSubBaseRadioGroup.setOnCheckedChangeListener(this);
	subItemTotalThicknessSubBaseRadioGroup.setOnCheckedChangeListener(this);
		
	//--------------------------------------------------------------------
	
	
	//6th Item0dp
	baseCourseWater_S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormBaseCourseWaterRadio_S);
	baseCourseWater_SRI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormBaseCourseWaterRadio_SRI);
	baseCourseWater_U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormBaseCourseWaterRadio_U);
	baseCourseWater_NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormBaseCourseWaterRadio_NA);
	
	//6th Item - SubItems
	subItemGrainSizeBaseCourseWaterRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormGrainSizeBaseCourseWaterRadioGroup);
	subItemPlasticityBaseCourseWaterRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormPlasticityBaseCourseWaterRadioGroup);
	subItemCompactionBaseCourseWaterRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormCompactionBaseCourseWaterRadioGroup);
	subItemThicknessBaseCourseWaterRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormThicknessBaseCourseWaterRadioGroup);
	
	//Check Change Listener
	subItemGrainSizeBaseCourseWaterRadioGroup.setOnCheckedChangeListener(this);
	subItemPlasticityBaseCourseWaterRadioGroup.setOnCheckedChangeListener(this);
	subItemCompactionBaseCourseWaterRadioGroup.setOnCheckedChangeListener(this);
	subItemThicknessBaseCourseWaterRadioGroup.setOnCheckedChangeListener(this);
		
	//--------------------------------------------------------------------

	
	
	//7th Item
	bituminous_S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormBituminousRadio_S);
	bituminous_SRI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormBituminousRadio_SRI);
	bituminous_U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormBituminousRadio_U);
	bituminous_NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormBituminousRadio_NA);
	
	//7th Item - SubItems
	subItemThicknessBituminousRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormThicknessBituminousRadioGroup);
	subItemSurfaceBituminousRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormSurfaceBituminousRadioGroup);
	
	//Check Change Listener
	subItemThicknessBituminousRadioGroup.setOnCheckedChangeListener(this);
	subItemSurfaceBituminousRadioGroup.setOnCheckedChangeListener(this);
			
	//--------------------------------------------------------------------

	
	//8th Item
	shoulders_S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormShouldersRadio_S);
	shoulders_SRI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormShouldersRadio_SRI);
	shoulders_U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormShouldersRadio_U);
	shoulders_NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormShouldersRadio_NA);
	
	//8th Item - SubItems
	subItemQualityOfMaterialShouldersRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormQualityOfMaterialShouldersRadioGroup);
	subItemDegreeOfCompactionShouldersRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormDegreeOfCompactionShouldersRadioGroup);
	subItemCamberShouldersRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormCamberShouldersRadioGroup);
	
	//Check Change Listener
	subItemQualityOfMaterialShouldersRadioGroup.setOnCheckedChangeListener(this);
	subItemDegreeOfCompactionShouldersRadioGroup.setOnCheckedChangeListener(this);
	subItemCamberShouldersRadioGroup.setOnCheckedChangeListener(this);
			
	//--------------------------------------------------------------------
	
	//9th Item0dp
	crossDrainage_S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormCrossDrainageRadio_S);
	crossDrainage_SRI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormCrossDrainageRadio_SRI);
	crossDrainage_U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormCrossDrainageRadio_U);
	crossDrainage_NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormCrossDrainageRadio_NA);
	
	//9th Item - SubItems
	subItemQualityOfMaterialCrossDrainageRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormQualityOfMaterialCrossDrainageRadioGroup);
	subItemQualityOfWorkmanshipCrossDrainageRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormQualityOfWorkmanshipCrossDrainageRadioGroup);
	
	//Check Change Listener
	subItemQualityOfMaterialCrossDrainageRadioGroup.setOnCheckedChangeListener(this);
	subItemQualityOfWorkmanshipCrossDrainageRadioGroup.setOnCheckedChangeListener(this);
			
	//--------------------------------------------------------------------
	
	
	//10th Item
	sideDrain_S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormSideDrainRadio_S);
	sideDrain_SRI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormSideDrainRadio_SRI);
	sideDrain_U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormSideDrainRadio_U);
	sideDrain_NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormSideDrainRadio_NA);
	
	//10th Item - SubItems
	subItemGeneralQualitySideDrainRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormGeneralQualitySideDrainRadioGroup);
	
	//Check Change Listener
	subItemGeneralQualitySideDrainRadioGroup.setOnCheckedChangeListener(this);
			
	//--------------------------------------------------------------------
	
	
	//11th Item
	cCSemiRigidPavement_S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormCCSemiRigidPavementRadio_S);
	cCSemiRigidPavement_SRI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormCCSemiRigidPavementRadio_SRI);
	cCSemiRigidPavement_U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormCCSemiRigidPavementRadio_U);
	cCSemiRigidPavement_NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormCCSemiRigidPavementRadio_NA);
	
	//11th Item - SubItems
	subItemQualityMaterialCCSemiRigidPavementRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormQualityMaterialCCSemiRigidPavementRadioGroup);
	subItemStrengthCCSemiRigidPavementRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormStrengthCCSemiRigidPavementRadioGroup);
	subItemQualityCCSemiRigidPavementRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormQualityCCSemiRigidPavementRadioGroup);
	subItemThicknessCCSemiRigidPavementRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormThicknessCCSemiRigidPavementRadioGroup);
	
	//Check Change Listener
	subItemQualityMaterialCCSemiRigidPavementRadioGroup.setOnCheckedChangeListener(this);
	subItemStrengthCCSemiRigidPavementRadioGroup.setOnCheckedChangeListener(this);
	subItemQualityCCSemiRigidPavementRadioGroup.setOnCheckedChangeListener(this);
	subItemThicknessCCSemiRigidPavementRadioGroup.setOnCheckedChangeListener(this);
	
	//--------------------------------------------------------------------
	
	
	//12th Item
	roadFurnitures_S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormRoadFurnituresRadio_S);
	roadFurnitures_SRI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormRoadFurnituresRadio_SRI);
	roadFurnitures_U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormRoadFurnituresRadio_U);
	roadFurnitures_NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormRoadFurnituresRadio_NA);
	
	//12th Item - SubItems
	subItemLogoBoardsRoadFurnituresRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormLogoBoardsRoadFurnituresRadioGroup);
	subItemWhetherRoadFurnituresRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormWhetherRoadFurnituresRadioGroup);
	
	//Check Change Listener
	subItemLogoBoardsRoadFurnituresRadioGroup.setOnCheckedChangeListener(this);
	subItemWhetherRoadFurnituresRadioGroup.setOnCheckedChangeListener(this);
	
	//--------------------------------------------------------------------
	
	//Overall Grade
	overallGradeRadioGroup = (RadioGroup) findViewById(R.id.nqmCompletedGradingFormOverallGradeRadioGroup);
	
	}
	
	
	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }
	
	@Override
	public void onClick(View v) {
		
		// Flipper event start 
			if(v.getId() == R.id.nqmCompletedGradingFormNextBtnId)
				viewFlipper.showNext(); 
	       
	       if(v.getId() == R.id.nqmCompletedGradingFormPrevBtnId)
	    	   viewFlipper.showPrevious(); 
	       
	       if(v.getId() == R.id.nqmCompletedGradingFormSaveBtn){
	    	   
	    	   saveGradingValues();
	       }
	       
	       if(v.getId() == R.id.nqmCompletedGradingFormCancelBtn){
	    	   finish();
	       }
	       
	       if(v.getId() == R.id.btnMainMenuLogoutId){
				logOut();
			}
	       
	}

	
	//On Click of subitems,  get checked radio Button and call calculateGrading() Method
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
			//1st item (Quality Arrangement) is not for selection for completed Road
			
			// 2nd Items - Subitem (Attention To Quality)
		 	if(group.getId() == R.id.nqmCompletedGradingFormVerificationOfTestResultsAttentionToQualityRadioGroup){
		 		
		 		RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemVerificationOfTestResultAttentionToQualityRadioGroup.getCheckedRadioButtonId());
		 		ArrayList<String> selectedValueArrLst = new ArrayList<String>();
		 		selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
				
				calculateGrading(selectedValueArrLst, attentionToQuality_S, attentionToQuality_SRI, attentionToQuality_U, attentionToQuality_NA);
		 	}
		 	
		 	
		 	// 3rd Items - Subitem (Geometrics)
		 	if(group.getId() == R.id.nqmCompletedGradingFormRoadWayWitdhGeometricsRadioGroup ||
		 	   group.getId() == R.id.nqmCompletedGradingFormCarriageWayWidthGeometricsRadioGroup ||
		 	   group.getId() == R.id.nqmCompletedGradingFormCamberGeometricsRadioGroup ||
		 	   group.getId() == R.id.nqmCompletedGradingFormSuperElevationGeometricsRadioGroup ||
		 	   group.getId() == R.id.nqmCompletedGradingFormLongitudinalGradientGeometricsRadioGroup ){
		 		
		 		RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemRoadWayWitdhGeometricsRadioGroup.getCheckedRadioButtonId());
		 		RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemCarriageWayWidthGeometricsRadioGroup.getCheckedRadioButtonId());
		 		RadioButton selectedRdoBtn3 = (RadioButton) findViewById(subItemCamberGeometricsRadioGroup.getCheckedRadioButtonId());
		 		RadioButton selectedRdoBtn4 = (RadioButton) findViewById(subItemSuperElevationGeometricsRadioGroup.getCheckedRadioButtonId());
		 		RadioButton selectedRdoBtn5 = (RadioButton) findViewById(subItemLongitudinalGradientGeometricsRadioGroup.getCheckedRadioButtonId());
		 		
		 		ArrayList<String> selectedValueArrLst = new ArrayList<String>();
		 		selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
		 		selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
		 		selectedValueArrLst.add(selectedRdoBtn3.getText().toString());
		 		selectedValueArrLst.add(selectedRdoBtn4.getText().toString());
		 		selectedValueArrLst.add(selectedRdoBtn5.getText().toString());
		 		
				calculateGrading(selectedValueArrLst, geometrics_S, geometrics_SRI, geometrics_U, geometrics_NA);
		 	}
		 	
		 	
		 	
		 	// 4th Items - Subitem (EarthWorkEmbankment)
		 	if(group.getId() == R.id.nqmCompletedGradingFormQualityOfMaterialEarthWorkEmbankmentRadioGroup ||
	 	 	   group.getId() == R.id.nqmCompletedGradingFormCompactionEarthWorkEmbankmentRadioGroup ||
	 	 	   group.getId() == R.id.nqmCompletedGradingFormSideSlopesEarthWorkEmbankmentRadioGroup ||
	 	 	   group.getId() == R.id.nqmCompletedGradingFormStabilityEarthWorkEmbankmentRadioGroup ||
	 	 	   group.getId() == R.id.nqmCompletedGradingFormSlopeProtectionEarthWorkEmbankmentRadioGroup ){
	 	 		
	 	 		RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemQualityOfMaterialEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId());
	 	 		RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemCompactionEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId());
	 	 		RadioButton selectedRdoBtn3 = (RadioButton) findViewById(subItemSideSlopesEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId());
	 	 		RadioButton selectedRdoBtn4 = (RadioButton) findViewById(subItemStabilityEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId());
	 	 		RadioButton selectedRdoBtn5 = (RadioButton) findViewById(subItemSlopeProtectionEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId());
	 	 		
	 	 		ArrayList<String> selectedValueArrLst = new ArrayList<String>();
	 	 		selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
	 	 		selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
	 	 		selectedValueArrLst.add(selectedRdoBtn3.getText().toString());
	 	 		selectedValueArrLst.add(selectedRdoBtn4.getText().toString());
	 	 		selectedValueArrLst.add(selectedRdoBtn5.getText().toString());
	 	 		
	 			calculateGrading(selectedValueArrLst, earthWorkEmbankment_S, earthWorkEmbankment_SRI, earthWorkEmbankment_U, earthWorkEmbankment_NA);
	 	 	}
		 	
		 	
		 	// 5th Items - Subitem (SubBase)
	 	 	if(group.getId() == R.id.nqmCompletedGradingFormGrainSizeSubBaseRadioGroup ||
	  	 	   group.getId() == R.id.nqmCompletedGradingFormPlasticitySubBaseRadioGroup ||
	  	 	   group.getId() == R.id.nqmCompletedGradingFormCompactionSubBaseRadioGroup ||
	  	 	   group.getId() == R.id.nqmCompletedGradingFormTotalThicknessSubBaseRadioGroup ){
	  	 		
	  	 		RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemGrainSizeSubBaseRadioGroup.getCheckedRadioButtonId());
	  	 		RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemPlasticitySubBaseRadioGroup.getCheckedRadioButtonId());
	  	 		RadioButton selectedRdoBtn3 = (RadioButton) findViewById(subItemCompactionSubBaseRadioGroup.getCheckedRadioButtonId());
	  	 		RadioButton selectedRdoBtn4 = (RadioButton) findViewById(subItemTotalThicknessSubBaseRadioGroup.getCheckedRadioButtonId());
	  	 		
	  	 		ArrayList<String> selectedValueArrLst = new ArrayList<String>();
	  	 		selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
	  	 		selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
	  	 		selectedValueArrLst.add(selectedRdoBtn3.getText().toString());
	  	 		selectedValueArrLst.add(selectedRdoBtn4.getText().toString());
	  	 		
	  			calculateGrading(selectedValueArrLst, subBase_S, subBase_SRI, subBase_U, subBase_NA);
	  	 	}
		 	
	 	// 6th Items - Subitem (BaseCourseWater)
	 	if(group.getId() == R.id.nqmCompletedGradingFormGrainSizeBaseCourseWaterRadioGroup ||
	 	   group.getId() == R.id.nqmCompletedGradingFormPlasticityBaseCourseWaterRadioGroup ||
	 	   group.getId() == R.id.nqmCompletedGradingFormCompactionBaseCourseWaterRadioGroup ||
	 	   group.getId() == R.id.nqmCompletedGradingFormThicknessBaseCourseWater ){
	 		
	 		RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemGrainSizeBaseCourseWaterRadioGroup.getCheckedRadioButtonId());
	 		RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemPlasticityBaseCourseWaterRadioGroup.getCheckedRadioButtonId());
	 		RadioButton selectedRdoBtn3 = (RadioButton) findViewById(subItemCompactionBaseCourseWaterRadioGroup.getCheckedRadioButtonId());
	 		RadioButton selectedRdoBtn4 = (RadioButton) findViewById(subItemThicknessBaseCourseWaterRadioGroup.getCheckedRadioButtonId());
	 		
	 		ArrayList<String> selectedValueArrLst = new ArrayList<String>();
	 		selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
	 		selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
	 		selectedValueArrLst.add(selectedRdoBtn3.getText().toString());
	 		selectedValueArrLst.add(selectedRdoBtn4.getText().toString());
	 		
			calculateGrading(selectedValueArrLst, baseCourseWater_S, baseCourseWater_SRI, baseCourseWater_U, baseCourseWater_NA);
	 	}
	 	
	 	
	 	// 7th Items - Subitem (Bituminous)
		if(group.getId() == R.id.nqmCompletedGradingFormThicknessBituminousRadioGroup ||
		   group.getId() == R.id.nqmCompletedGradingFormSurfaceBituminousRadioGroup ){
			
			RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemThicknessBituminousRadioGroup.getCheckedRadioButtonId());
			RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemSurfaceBituminousRadioGroup.getCheckedRadioButtonId());
			
			ArrayList<String> selectedValueArrLst = new ArrayList<String>();
			selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
			selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
			
			calculateGrading(selectedValueArrLst, bituminous_S, bituminous_SRI, bituminous_U, bituminous_NA);
		}
		
		// 8th Items - Subitem (Shoulders)
		if(group.getId() == R.id.nqmCompletedGradingFormQualityOfMaterialShouldersRadioGroup ||
		   group.getId() == R.id.nqmCompletedGradingFormDegreeOfCompactionShouldersRadioGroup ||
		   group.getId() == R.id.nqmCompletedGradingFormCamberShouldersRadioGroup){
			
			RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemQualityOfMaterialShouldersRadioGroup.getCheckedRadioButtonId());
			RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemDegreeOfCompactionShouldersRadioGroup.getCheckedRadioButtonId());
			RadioButton selectedRdoBtn3 = (RadioButton) findViewById(subItemCamberShouldersRadioGroup.getCheckedRadioButtonId());
			
			ArrayList<String> selectedValueArrLst = new ArrayList<String>();
			selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
			selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
			selectedValueArrLst.add(selectedRdoBtn3.getText().toString());
			
			calculateGrading(selectedValueArrLst, shoulders_S, shoulders_SRI, shoulders_U, shoulders_NA);
		}
	 	
		
		// 9th Items - Subitem (Cross Drainage)
		if(group.getId() == R.id.nqmCompletedGradingFormQualityOfMaterialCrossDrainageRadioGroup ||
		   group.getId() == R.id.nqmCompletedGradingFormQualityOfWorkmanshipCrossDrainageRadioGroup ){
			
			RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemQualityOfMaterialCrossDrainageRadioGroup.getCheckedRadioButtonId());
			RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemQualityOfWorkmanshipCrossDrainageRadioGroup.getCheckedRadioButtonId());
			
			ArrayList<String> selectedValueArrLst = new ArrayList<String>();
			selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
			selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
			
			calculateGrading(selectedValueArrLst, crossDrainage_S, crossDrainage_SRI, crossDrainage_U, crossDrainage_NA);
		}
		
		
		// 10th Items - Subitem (Side Drain)
		if(group.getId() == R.id.nqmCompletedGradingFormGeneralQualitySideDrainRadioGroup ){
			
			RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemGeneralQualitySideDrainRadioGroup.getCheckedRadioButtonId());
			
			ArrayList<String> selectedValueArrLst = new ArrayList<String>();
			selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
			
			calculateGrading(selectedValueArrLst, sideDrain_S, sideDrain_SRI, sideDrain_U, sideDrain_NA);
		}
		
		// 11th Items - Subitem (CCSemiRigidPavement)
		if(group.getId() == R.id.nqmCompletedGradingFormQualityMaterialCCSemiRigidPavementRadioGroup ||
		   group.getId() == R.id.nqmCompletedGradingFormStrengthCCSemiRigidPavementRadioGroup ||
		   group.getId() == R.id.nqmCompletedGradingFormQualityCCSemiRigidPavementRadioGroup ||
		   group.getId() == R.id.nqmCompletedGradingFormThicknessCCSemiRigidPavementRadioGroup){
			
			RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemQualityMaterialCCSemiRigidPavementRadioGroup.getCheckedRadioButtonId());
			RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemStrengthCCSemiRigidPavementRadioGroup.getCheckedRadioButtonId());
			RadioButton selectedRdoBtn3 = (RadioButton) findViewById(subItemQualityCCSemiRigidPavementRadioGroup.getCheckedRadioButtonId());
			RadioButton selectedRdoBtn4 = (RadioButton) findViewById(subItemThicknessCCSemiRigidPavementRadioGroup.getCheckedRadioButtonId());
			
			ArrayList<String> selectedValueArrLst = new ArrayList<String>();
			selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
			selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
			selectedValueArrLst.add(selectedRdoBtn3.getText().toString());
			selectedValueArrLst.add(selectedRdoBtn4.getText().toString());
			
			calculateGrading(selectedValueArrLst, cCSemiRigidPavement_S, cCSemiRigidPavement_SRI, cCSemiRigidPavement_U, cCSemiRigidPavement_NA);
		}
	 	 
		
		// 12th Items - Subitem (CCSemiRigidPavement)
		if(group.getId() == R.id.nqmCompletedGradingFormLogoBoardsRoadFurnituresRadioGroup ||
		   group.getId() == R.id.nqmCompletedGradingFormWhetherRoadFurnituresRadioGroup ){
			
			RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemLogoBoardsRoadFurnituresRadioGroup.getCheckedRadioButtonId());
			RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemWhetherRoadFurnituresRadioGroup.getCheckedRadioButtonId());
			
			ArrayList<String> selectedValueArrLst = new ArrayList<String>();
			selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
			selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
			
			calculateGrading(selectedValueArrLst, roadFurnitures_S, roadFurnitures_SRI, roadFurnitures_U, roadFurnitures_NA);
		}
	
	}//OnCheckChangeListner ends here

	
	//Calculate Grading for each Main Item
	public void calculateGrading(ArrayList<String> selectedValueLst, RadioButton S, RadioButton SRI, RadioButton U, RadioButton NA) {

		if (selectedValueLst.contains("U")) {
			U.setChecked(true);
			calculateOverAllGrading();
			return;
		}

		Integer count = 0;
		for (String string : selectedValueLst) {
			if (string.equalsIgnoreCase("NA")) {
				count++;
			}
		}
		
		if (selectedValueLst.size() == count) {
			NA.setChecked(true);
			calculateOverAllGrading();
			return;
		}

		count = 0;
		for (String string : selectedValueLst) {
			if (string.equalsIgnoreCase("S")) {
				count++;
			}
		}

		if (selectedValueLst.size() == count) {
			S.setChecked(true);
			calculateOverAllGrading();
			return;
		}

		if (selectedValueLst.contains("S") && selectedValueLst.contains("NA") && selectedValueLst.contains("SRI")) {
			SRI.setChecked(true);
			calculateOverAllGrading();
			return;
		}
		
		if (selectedValueLst.contains("S") && (selectedValueLst.contains("NA"))) {
			S.setChecked(true);
			calculateOverAllGrading();
			return;
		}
		
		if (selectedValueLst.contains("SRI") && selectedValueLst.contains("U")  && selectedValueLst.contains("NA")) {
			U.setChecked(true);
		} 

		if (selectedValueLst.contains("SRI") && (selectedValueLst.contains("U"))) 
		{
			U.setChecked(true);
		} 
		else 
		{
			SRI.setChecked(true);
		}
		
		calculateOverAllGrading();

	} // calculateGrading ends
	
	
	
	///Calculate Overall Grade
	public void calculateOverAllGrading(){
		
		RadioButton S = (RadioButton) findViewById(R.id.nqmCompletedGradingFormOverallGradeRadio_S);
		RadioButton SRI = (RadioButton) findViewById(R.id.nqmCompletedGradingFormOverallGradeRadio_SRI);
		RadioButton U = (RadioButton) findViewById(R.id.nqmCompletedGradingFormOverallGradeRadio_U);
		RadioButton NA = (RadioButton) findViewById(R.id.nqmCompletedGradingFormOverallGradeRadio_NA);
		
		RadioButton selectedRadioButton1 = (RadioButton) findViewById(qualityArrangmentRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton2 = (RadioButton) findViewById(attentionToQualityRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton3 = (RadioButton) findViewById(geometricsRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton4 = (RadioButton) findViewById(earthWorkEmbankmentRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton5 = (RadioButton) findViewById(subBaseRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton6 = (RadioButton) findViewById(baseCourseWaterRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton7 = (RadioButton) findViewById(bituminousRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton8 = (RadioButton) findViewById(shoulderRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton9 = (RadioButton) findViewById(crossDrainageRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton10 = (RadioButton) findViewById(sideDrainRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton11 = (RadioButton) findViewById(cCSemiRigidRadioGroup.getCheckedRadioButtonId());
		RadioButton selectedRadioButton12 = (RadioButton) findViewById(roadFurnituresRadioGroup.getCheckedRadioButtonId());
		
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
		
		
		/*
	    Condition for Generating OverAllItem Grade.
	    1. If all item grade NA, Then Overall Grade NA.
	    2. If All Item Grade is S, Then Overall Grade S.
	    3. If any one Item 4,5,6,7 is U then Overall Grade U.
	    4. Other wise SRI.
	    */
		
		Integer count = 0;
		for (String string : selectvalueArrayList) {
			if (string.equalsIgnoreCase("NA")) {
				count++;
			}
		}
		if (selectvalueArrayList.size() == count) {
			NA.setChecked(true);
			return;
		}

		if (selectvalueArrayList.contains("S") && selectvalueArrayList.contains("NA") && (selectvalueArrayList.contains("SRI")) && !(selectvalueArrayList.contains("U"))) {
			SRI.setChecked(true);
			return;
		}
		
		if (selectvalueArrayList.contains("S") && selectvalueArrayList.contains("NA") && !(selectvalueArrayList.contains("U"))) {
			S.setChecked(true);
			return;
		}
		
		if(selectvalueArrayList.get(3).equalsIgnoreCase("U") || selectvalueArrayList.get(4).equalsIgnoreCase("U") || selectvalueArrayList.get(5).equalsIgnoreCase("U") || selectvalueArrayList.get(6).equalsIgnoreCase("U")){
			
			U.setChecked(true);
		}else{
			SRI.setChecked(true);
		}
	} // calculate overall Grading ends
	
	
public void saveGradingValues(){
		
		selectedRadioList = new ArrayList<String>();
		ArrayList<RadioButton> selectedRadioButtonArrayList  = new ArrayList<RadioButton>();
		
		selectedRadioButtonArrayList.add((RadioButton) findViewById(qualityArrangmentRadioGroup.getCheckedRadioButtonId()));
		
		selectedRadioButtonArrayList.add((RadioButton) findViewById(attentionToQualityRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemVerificationOfTestResultAttentionToQualityRadioGroup.getCheckedRadioButtonId()));

		selectedRadioButtonArrayList.add((RadioButton) findViewById(geometricsRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemRoadWayWitdhGeometricsRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemCarriageWayWidthGeometricsRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemCamberGeometricsRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemSuperElevationGeometricsRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemLongitudinalGradientGeometricsRadioGroup.getCheckedRadioButtonId()));
		
		
		selectedRadioButtonArrayList.add((RadioButton) findViewById(earthWorkEmbankmentRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemQualityOfMaterialEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemCompactionEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemSideSlopesEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemStabilityEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemSlopeProtectionEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId()));
		
		
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subBaseRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemGrainSizeSubBaseRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemPlasticitySubBaseRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemCompactionSubBaseRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemTotalThicknessSubBaseRadioGroup.getCheckedRadioButtonId()));
		
		selectedRadioButtonArrayList.add((RadioButton) findViewById(baseCourseWaterRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemGrainSizeBaseCourseWaterRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemPlasticityBaseCourseWaterRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemCompactionBaseCourseWaterRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemThicknessBaseCourseWaterRadioGroup.getCheckedRadioButtonId()));
		
		
		selectedRadioButtonArrayList.add((RadioButton) findViewById(bituminousRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemThicknessBituminousRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemSurfaceBituminousRadioGroup.getCheckedRadioButtonId()));
		
		selectedRadioButtonArrayList.add((RadioButton) findViewById(shoulderRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemQualityOfMaterialShouldersRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemDegreeOfCompactionShouldersRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemCamberShouldersRadioGroup.getCheckedRadioButtonId()));
		
		
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(crossDrainageRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemQualityOfMaterialCrossDrainageRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemQualityOfWorkmanshipCrossDrainageRadioGroup.getCheckedRadioButtonId()));
		
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(sideDrainRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemGeneralQualitySideDrainRadioGroup.getCheckedRadioButtonId()));
		
		
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(cCSemiRigidRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemQualityMaterialCCSemiRigidPavementRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemStrengthCCSemiRigidPavementRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemQualityCCSemiRigidPavementRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemThicknessCCSemiRigidPavementRadioGroup.getCheckedRadioButtonId()));
		
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(roadFurnituresRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemLogoBoardsRoadFurnituresRadioGroup.getCheckedRadioButtonId()));
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemWhetherRoadFurnituresRadioGroup.getCheckedRadioButtonId()));
		
		selectedRadioButtonArrayList.add( (RadioButton) findViewById(overallGradeRadioGroup.getCheckedRadioButtonId()));
		
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
						
					   ImageDetailsDAO imageDetailsDAO = new ImageDetailsDAO();
					   ArrayList<ImageDetailsDTO> imageDetailsDTOArrayList =  imageDetailsDAO.getImageDetails(roadDetailsDTO.getUniqueCode(), parent, parent);
					
					   //***********************
					   final ArrayList<String> commentEditText = new ArrayList<String>();
					   
						//Calendar c = Calendar.getInstance();
						//SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
						String inspDate =  imageDetailsDTOArrayList.get(0).getCaptureDateTime();
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
	
} // class ends here