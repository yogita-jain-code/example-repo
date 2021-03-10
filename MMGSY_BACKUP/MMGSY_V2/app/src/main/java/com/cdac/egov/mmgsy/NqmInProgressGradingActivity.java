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

public class NqmInProgressGradingActivity extends AppBaseActivity implements OnClickListener, OnCheckedChangeListener {

	private ViewFlipper viewFlipper;
	private Button nextBtn;
	private Button previousBtn;
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
	private RadioButton qualityArrangment_SRI;
	private RadioButton qualityArrangment_U;
	private RadioButton qualityArrangment_NA;
	private RadioGroup subItemQualityArrangementRadioGroup;
	private RadioButton attentionToQuality_S;
	private RadioButton attentionToQuality_SRI;
	private RadioButton attentionToQuality_U;
	private RadioButton attentionToQuality_NA;
	private RadioGroup subItemMaintenanceOfQCAttentionToQualityRadioGroup;
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
	private RadioGroup subItemGradationBituminousRadioGroup;
	private RadioGroup subItemMixingTemperatureBituminousRadioGroup;
	private RadioGroup subItemLayingTemperatureBituminousRadioGroup;
	private RadioGroup subItemThicknessBituminousRadioGroup;
	private RadioGroup subItemSurfaceBituminousRadioGroup;
	private RadioButton shoulders_S;
	private RadioButton shoulders_SRI;
	private RadioButton shoulders_U;
	private RadioButton shoulders_NA;
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
	private RadioGroup subItemCitizenInfoBoardRoadFurnituresRadioGroup;
	private RadioGroup subItemWhetherRoadFurnituresRadioGroup;
	private RadioGroup overallGradeRadioGroup;
	private Button saveBtn;
	private Button cancelBtn;
	private Activity parent;
	private ArrayList<String> selectedRadioList;
	private ScheduleDetailsDTO roadDetailsDTO;
	private String entryMode;
	private EditText subItemQualityArrangementEditText;
	private EditText subItemMaintenanceOfQCAttentionToQualityEditText;
	private EditText subItemVerificationOfTestResultAttentionToQualityEditText;
	private EditText subItemRoadWayWitdhGeometricsQualityEditText;
	private EditText subItemCarriageWayWidthGeometricsEditText;
	private EditText subItemCamberGeometricsEditText;
	private EditText subItemSuperElevationGeometricsEditText;
	private EditText subItemLongitudinalGradientGeometricsEditText;
	private EditText subItemQualityOfMaterialEarthWorkEmbankmentEditText;
	private EditText subItemCompactionEarthWorkEmbankmentEditText;
	private EditText subItemStabilityEarthWorkEmbankmentEditText;
	private EditText subItemSlopeProtectionEarthWorkEmbankmentEditText;
	private EditText subItemGrainSizeSubBaseEditText;
	private EditText subItemPlasticitySubBaseEditText;
	private EditText subItemCompactionSubBaseEditText;
	private EditText subItemTotalThicknessSubBaseEditText;
	private EditText subItemGrainSizeBaseCourseWaterEditText;
	private EditText subItemPlasticityBaseCourseWaterEditText;
	private EditText subItemCompactionBaseCourseWaterEditText;
	private EditText subItemThicknessBaseCourseWaterEditText;
	private EditText subItemGradationBituminousEditText;
	private EditText subItemMixingTemperatureBituminousEditText;
	private EditText subItemLayingTemperatureBituminousEditText;
	private EditText subItemThicknessBituminousEditText;
	private EditText subItemSurfaceBituminousEditText;
	private EditText subItemQualityOfMaterialCrossDrainageEditText;
	private EditText subItemQualityOfWorkmanshipCrossDrainageEditText;
	private EditText subItemGeneralQualitySideDrainEditText;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = NqmInProgressGradingActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nqm_in_progress_grading);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.nqmInProgressGradingFormViewFlipper);
		nextBtn = (Button) findViewById(R.id.nqmInProgressGradingFormNextBtnId);
        previousBtn = (Button) findViewById(R.id.nqmInProgressGradingFormPrevBtnId);
        saveBtn = (Button) findViewById(R.id.nqmInProgressGradingFormSaveBtn);
        cancelBtn = (Button) findViewById(R.id.nqmInProgressGradingFormCancelBtn);
        
        nextBtn.setOnClickListener(this);
        previousBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        
        // Set Road Details for Grading forms
        Intent intent = getIntent();
        entryMode =  (String) intent.getStringExtra("entryMode");
        String  monitorName =  (String) intent.getStringExtra("monitorName");
        roadDetailsDTO = (ScheduleDetailsDTO) intent.getSerializableExtra("inpectionEntryRoadDTO");
        
        QMSHelper.setInpsectionFormDetails(parent, monitorName, roadDetailsDTO , this, this);
        
        // -------------------------------- Grading Radio Group Listeners -------------------------------------
    	qualityArrangmentRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormQualityArrangmentRadioGroup);
    	attentionToQualityRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormAttentionToQualityRadioGroup);
    	geometricsRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormGeometricsRadioGroup);
    	earthWorkEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormEarthWorkEmbankmentRadioGroup);
    	subBaseRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormSubBaseRadioGroup);
    	baseCourseWaterRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormBaseCourseWaterRadioGroup);
    	bituminousRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormBituminousRadioGroup);
    	shoulderRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormShouldersRadioGroup);
    	crossDrainageRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormCrossDrainageRadioGroup); 
    	sideDrainRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormSideDrainRadioGroup);
    	cCSemiRigidRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormCCSemiRigidPavementRadioGroup);
    	roadFurnituresRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormRoadFurnituresRadioGroup);
        
    	
    	//1st Item
    	qualityArrangment_S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormQualityArrangmentRadio_S);
    	qualityArrangment_SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormQualityArrangmentRadio_SRI);
    	qualityArrangment_U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormQualityArrangmentRadio_U);
    	qualityArrangment_NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormQualityArrangmentRadio_NA);
    	
    	//1st Item - SubItems
    	subItemQualityArrangementRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormSubItemQualityArrangementRadioGroup);
    	subItemQualityArrangementEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormSubItemQualityArrangementCommentEditText);
    	//Check Change Listener
    	subItemQualityArrangementRadioGroup.setOnCheckedChangeListener(this);
   	
    	//--------------------------------------------------------------------
    	
    	// 2nd Item
    	attentionToQuality_S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormAttentionToQualityRadio_S);
    	attentionToQuality_SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormAttentionToQualityRadio_SRI);
    	attentionToQuality_U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormAttentionToQualityRadio_U);
    	attentionToQuality_NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormAttentionToQualityRadio_NA);
    	
    	//2nd Item - SubItems
    	subItemMaintenanceOfQCAttentionToQualityRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormMaintenanceOfQCAttentionToQualityRadioGroup);
    	subItemVerificationOfTestResultAttentionToQualityRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormVerificationOfTestResultsAttentionToQualityRadioGroup);
    	subItemMaintenanceOfQCAttentionToQualityEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormAttentionToQualityRadioGroupCommentEditText);
    	subItemVerificationOfTestResultAttentionToQualityEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormVerificationOfTestResultsAttentionToQualityCommentEditText);
    	
    	//Check Change Listener
    	subItemVerificationOfTestResultAttentionToQualityRadioGroup.setOnCheckedChangeListener(this);
    	subItemMaintenanceOfQCAttentionToQualityRadioGroup.setOnCheckedChangeListener(this);
    	//--------------------------------------------------------------------
        
    	
    	
    	// 3rd Item
    	geometrics_S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormGeometricsRadio_S);
    	geometrics_SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormGeometricsRadio_SRI);
    	geometrics_U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormGeometricsRadio_U);
    	geometrics_NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormGeometricsRadio_NA);
    	
    	//3rd Item - SubItems
    	subItemRoadWayWitdhGeometricsRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormRoadWayWitdhGeometricsRadioGroup);
    	subItemCarriageWayWidthGeometricsRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormCarriageWayWidthGeometricsRadioGroup);
    	subItemCamberGeometricsRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormCamberGeometricsRadioGroup);
    	subItemSuperElevationGeometricsRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormSuperElevationGeometricsRadioGroup);
    	subItemLongitudinalGradientGeometricsRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormLongitudinalGradientGeometricsRadioGroup);
    	
    	subItemRoadWayWitdhGeometricsQualityEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormRoadWayWitdhGeometricsCommentEditText);
    	subItemCarriageWayWidthGeometricsEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormCarriageWayWidthGeometricsCommentEditText);
    	subItemCamberGeometricsEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormCamberGeometricsCommentEditText);
    	subItemSuperElevationGeometricsEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormSuperElevationGeometricsCommentEditText);
    	subItemLongitudinalGradientGeometricsEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormLongitudinalGradientGeometricsCommentEditText);
    	
    	//Check Change Listener
    	subItemRoadWayWitdhGeometricsRadioGroup.setOnCheckedChangeListener(this);
    	subItemCarriageWayWidthGeometricsRadioGroup.setOnCheckedChangeListener(this);
    	subItemCamberGeometricsRadioGroup.setOnCheckedChangeListener(this);
    	subItemSuperElevationGeometricsRadioGroup.setOnCheckedChangeListener(this);
    	subItemLongitudinalGradientGeometricsRadioGroup.setOnCheckedChangeListener(this);
    	
    	//--------------------------------------------------------------------
    	
    	
    	//4th Item
    	earthWorkEmbankment_S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormEarthWorkEmbankmentRadio_S);
    	earthWorkEmbankment_SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormEarthWorkEmbankmentRadio_SRI);
    	earthWorkEmbankment_U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormEarthWorkEmbankmentRadio_U);
    	earthWorkEmbankment_NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormEarthWorkEmbankmentRadio_NA);
    	
    	//4th Item - SubItems
    	subItemQualityOfMaterialEarthWorkEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormQualityOfMaterialEarthWorkEmbankmentRadioGroup);
    	subItemCompactionEarthWorkEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormCompactionEarthWorkEmbankmentRadioGroup);
    	subItemStabilityEarthWorkEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormStabilityEarthWorkEmbankmentRadioGroup);
    	subItemSlopeProtectionEarthWorkEmbankmentRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormSlopeProtectionEarthWorkEmbankmentRadioGroup);
    	
    	subItemQualityOfMaterialEarthWorkEmbankmentEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormQualityOfMaterialEarthWorkEmbankmentCommentEditText);
    	subItemCompactionEarthWorkEmbankmentEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormCompactionEarthWorkEmbankmentCommentEditText);
    	subItemStabilityEarthWorkEmbankmentEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormStabilityEarthWorkEmbankmentCommentEditText);
    	subItemSlopeProtectionEarthWorkEmbankmentEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormSlopeProtectionEarthWorkEmbankmentCommentEditText);
    	
    	//Check Change Listener
    	subItemQualityOfMaterialEarthWorkEmbankmentRadioGroup.setOnCheckedChangeListener(this);
    	subItemCompactionEarthWorkEmbankmentRadioGroup.setOnCheckedChangeListener(this);
    	subItemStabilityEarthWorkEmbankmentRadioGroup.setOnCheckedChangeListener(this);
    	subItemSlopeProtectionEarthWorkEmbankmentRadioGroup.setOnCheckedChangeListener(this);
    	
    	//--------------------------------------------------------------------
    	
    	
    	//5th Item
    	subBase_S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormSubBaseRadio_S);
    	subBase_SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormSubBaseRadio_SRI);
    	subBase_U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormSubBaseRadio_U);
    	subBase_NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormSubBaseRadio_NA);
    	
    	//5th Item - SubItems
    	subItemGrainSizeSubBaseRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormGrainSizeSubBaseRadioGroup);
    	subItemPlasticitySubBaseRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormPlasticitySubBaseRadioGroup);
    	subItemCompactionSubBaseRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormCompactionSubBaseRadioGroup);
    	subItemTotalThicknessSubBaseRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormTotalThicknessSubBaseRadioGroup);
    	
    	subItemGrainSizeSubBaseEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormGrainSizeSubBaseCommentEditText);
    	subItemPlasticitySubBaseEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormPlasticitySubBaseCommentEditText);
    	subItemCompactionSubBaseEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormCompactionSubBaseCommentEditText);
    	subItemTotalThicknessSubBaseEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormTotalThicknessSubBaseCommentEditText);
    	
    	//Check Change Listener
    	subItemGrainSizeSubBaseRadioGroup.setOnCheckedChangeListener(this);
    	subItemPlasticitySubBaseRadioGroup.setOnCheckedChangeListener(this);
    	subItemCompactionSubBaseRadioGroup.setOnCheckedChangeListener(this);
    	subItemTotalThicknessSubBaseRadioGroup.setOnCheckedChangeListener(this);
    		
    	//--------------------------------------------------------------------
    	
    	
    	//6th Item
    	baseCourseWater_S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormBaseCourseWaterRadio_S);
    	baseCourseWater_SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormBaseCourseWaterRadio_SRI);
    	baseCourseWater_U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormBaseCourseWaterRadio_U);
    	baseCourseWater_NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormBaseCourseWaterRadio_NA);
    	
    	//6th Item - SubItems
    	subItemGrainSizeBaseCourseWaterRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormGrainSizeBaseCourseWaterRadioGroup);
    	subItemPlasticityBaseCourseWaterRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormPlasticityBaseCourseWaterRadioGroup);
    	subItemCompactionBaseCourseWaterRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormCompactionBaseCourseWaterRadioGroup);
    	subItemThicknessBaseCourseWaterRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormThicknessBaseCourseWaterRadioGroup);
    	
    	subItemGrainSizeBaseCourseWaterEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormGrainSizeBaseCourseWaterCommentEditText);
    	subItemPlasticityBaseCourseWaterEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormPlasticityBaseCourseWaterCommentEditText);
    	subItemCompactionBaseCourseWaterEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormCompactionBaseCourseWaterCommentEditText);
    	subItemThicknessBaseCourseWaterEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormThicknessBaseCourseWaterCommentEditText);
    	
    	
    	//Check Change Listener
    	subItemGrainSizeBaseCourseWaterRadioGroup.setOnCheckedChangeListener(this);
    	subItemPlasticityBaseCourseWaterRadioGroup.setOnCheckedChangeListener(this);
    	subItemCompactionBaseCourseWaterRadioGroup.setOnCheckedChangeListener(this);
    	subItemThicknessBaseCourseWaterRadioGroup.setOnCheckedChangeListener(this);
    		
    	//--------------------------------------------------------------------

    	
    	//7th Item
    	bituminous_S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormBituminousRadio_S);
    	bituminous_SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormBituminousRadio_SRI);
    	bituminous_U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormBituminousRadio_U);
    	bituminous_NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormBituminousRadio_NA);
    	
    	//7th Item - SubItems
    	subItemGradationBituminousRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormGradationBituminousRadioGroup);
    	subItemMixingTemperatureBituminousRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormMixingTemperatureBituminousRadioGroup);
    	subItemLayingTemperatureBituminousRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormLayingTemperatureBituminousRadioGroup);
    	subItemThicknessBituminousRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormThicknessBituminousRadioGroup);
    	subItemSurfaceBituminousRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormSurfaceBituminousRadioGroup);
    	
    	subItemGradationBituminousEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormGradationBituminousCommentEditText);
    	subItemMixingTemperatureBituminousEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormMixingTemperatureBituminousCommentEditText);
    	subItemLayingTemperatureBituminousEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormLayingTemperatureBituminousCommentEditText);
    	subItemThicknessBituminousEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormThicknessBituminousCommentEditText);
    	subItemSurfaceBituminousEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormSurfaceBituminousCommentEditText);
    	
    	//Check Change Listener
    	subItemGradationBituminousRadioGroup.setOnCheckedChangeListener(this);
    	subItemMixingTemperatureBituminousRadioGroup.setOnCheckedChangeListener(this);
    	subItemLayingTemperatureBituminousRadioGroup.setOnCheckedChangeListener(this);
    	subItemThicknessBituminousRadioGroup.setOnCheckedChangeListener(this);
    	subItemSurfaceBituminousRadioGroup.setOnCheckedChangeListener(this);
    			
    	//--------------------------------------------------------------------

    	
    	//8th Item
    	shoulders_S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormShouldersRadio_S);
    	shoulders_SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormShouldersRadio_SRI);
    	shoulders_U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormShouldersRadio_U);
    	shoulders_NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormShouldersRadio_NA);
    	
    	//8th Item - SubItems
    	// No SubItems under Shoulders are applicable for InProgress 
    			
    	//--------------------------------------------------------------------
    	
    	//9th Item
    	crossDrainage_S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormCrossDrainageRadio_S);
    	crossDrainage_SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormCrossDrainageRadio_SRI);
    	crossDrainage_U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormCrossDrainageRadio_U);
    	crossDrainage_NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormCrossDrainageRadio_NA);
    	
    	//9th Item - SubItems
    	subItemQualityOfMaterialCrossDrainageRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormQualityOfMaterialCrossDrainageRadioGroup);
    	subItemQualityOfWorkmanshipCrossDrainageRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormQualityOfWorkmanshipCrossDrainageRadioGroup);
    	
    	subItemQualityOfMaterialCrossDrainageEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormQualityOfMaterialCrossDrainageCommentEditText);
    	subItemQualityOfWorkmanshipCrossDrainageEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormQualityOfWorkmanshipCrossDrainageCommentEditText);
    	
    	//Check Change Listener
    	subItemQualityOfMaterialCrossDrainageRadioGroup.setOnCheckedChangeListener(this);
    	subItemQualityOfWorkmanshipCrossDrainageRadioGroup.setOnCheckedChangeListener(this);
    			
    	//--------------------------------------------------------------------
    	
    	
    	//10th Item
    	sideDrain_S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormSideDrainRadio_S);
    	sideDrain_SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormSideDrainRadio_SRI);
    	sideDrain_U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormSideDrainRadio_U);
    	sideDrain_NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormSideDrainRadio_NA);
    	
    	//10th Item - SubItems
    	subItemGeneralQualitySideDrainRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormGeneralQualitySideDrainRadioGroup);
    	
    	subItemGeneralQualitySideDrainEditText = (EditText) findViewById(R.id.nqmInProgressGradingFormGeneralQualitySideDrainCommentEditText);
    	
    	//Check Change Listener
    	subItemGeneralQualitySideDrainRadioGroup.setOnCheckedChangeListener(this);
    			
    	//--------------------------------------------------------------------
    	
    	
    	//11th Item
    	cCSemiRigidPavement_S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormCCSemiRigidPavementRadio_S);
    	cCSemiRigidPavement_SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormCCSemiRigidPavementRadio_SRI);
    	cCSemiRigidPavement_U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormCCSemiRigidPavementRadio_U);
    	cCSemiRigidPavement_NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormCCSemiRigidPavementRadio_NA);
    	
    	//11th Item - SubItems
    	subItemQualityMaterialCCSemiRigidPavementRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormQualityMaterialCCSemiRigidPavementRadioGroup);
    	subItemStrengthCCSemiRigidPavementRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormStrengthCCSemiRigidPavementRadioGroup);
    	subItemQualityCCSemiRigidPavementRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormQualityCCSemiRigidPavementRadioGroup);
    	subItemThicknessCCSemiRigidPavementRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormThicknessCCSemiRigidPavementRadioGroup);
    	
    	//Check Change Listener
    	subItemQualityMaterialCCSemiRigidPavementRadioGroup.setOnCheckedChangeListener(this);
    	subItemStrengthCCSemiRigidPavementRadioGroup.setOnCheckedChangeListener(this);
    	subItemQualityCCSemiRigidPavementRadioGroup.setOnCheckedChangeListener(this);
    	subItemThicknessCCSemiRigidPavementRadioGroup.setOnCheckedChangeListener(this);
    	
    	//--------------------------------------------------------------------
    	
    	
    	//12th Item
    	roadFurnitures_S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormRoadFurnituresRadio_S);
    	roadFurnitures_SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormRoadFurnituresRadio_SRI);
    	roadFurnitures_U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormRoadFurnituresRadio_U);
    	roadFurnitures_NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormRoadFurnituresRadio_NA);
    	
    	//12th Item - SubItems
    	subItemCitizenInfoBoardRoadFurnituresRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormCitizenInfoBoardRoadFurnituresRadioGroup);
    	subItemWhetherRoadFurnituresRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormWhetherRoadFurnituresRadioGroup);
    	
    	//Check Change Listener
    	subItemCitizenInfoBoardRoadFurnituresRadioGroup.setOnCheckedChangeListener(this);
    	subItemWhetherRoadFurnituresRadioGroup.setOnCheckedChangeListener(this);
    	
    	//--------------------------------------------------------------------
    	
    	//Overall Grade
    	overallGradeRadioGroup = (RadioGroup) findViewById(R.id.nqmInProgressGradingFormOverallGradeRadioGroup);
    	
	}
	
	
	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nqm_in_progress_grading, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		// Flipper event start 
		   if(v == nextBtn)
			   viewFlipper.showNext(); 
			       
	       if(v == previousBtn)
	    	   viewFlipper.showPrevious(); 
	       
	       if(v.getId() == R.id.nqmInProgressGradingFormSaveBtn){
	    	   saveGradingValues();
		    }
	       
	       if(v.getId() == R.id.nqmInProgressGradingFormCancelBtn){
	    	   finish();
	       	}
	       
	       if(v.getId() == R.id.btnMainMenuLogoutId){
				logOut();
			}
		
	}

	
	
	//On Click of subitems,  get checked radio Button and call calculateGrading() Method
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			
				//1st item - Subitem (Quality Arrangement) 
				if(group.getId() == R.id.nqmInProgressGradingFormSubItemQualityArrangementRadioGroup){
					
					RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemQualityArrangementRadioGroup.getCheckedRadioButtonId());
			 		ArrayList<String> selectedValueArrLst = new ArrayList<String>();
			 		selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
					
					calculateGrading(selectedValueArrLst, qualityArrangment_S, qualityArrangment_SRI, qualityArrangment_U, qualityArrangment_NA);
					
				}
			
				// 2nd Items - Subitem (Attention To Quality)
			 	if(group.getId() == R.id.nqmInProgressGradingFormMaintenanceOfQCAttentionToQualityRadioGroup ||
			 	   group.getId() == R.id.nqmInProgressGradingFormVerificationOfTestResultsAttentionToQualityRadioGroup ){
			 		
			 		RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemVerificationOfTestResultAttentionToQualityRadioGroup.getCheckedRadioButtonId());
			 		RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemMaintenanceOfQCAttentionToQualityRadioGroup.getCheckedRadioButtonId());
			 		ArrayList<String> selectedValueArrLst = new ArrayList<String>();
			 		selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
			 		selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
					
					calculateGrading(selectedValueArrLst, attentionToQuality_S, attentionToQuality_SRI, attentionToQuality_U, attentionToQuality_NA);
			 	}
			 	
			 	
			 	// 3rd Items - Subitem (Geometrics)
			 	if(group.getId() == R.id.nqmInProgressGradingFormRoadWayWitdhGeometricsRadioGroup ||
			 	   group.getId() == R.id.nqmInProgressGradingFormCarriageWayWidthGeometricsRadioGroup ||
			 	   group.getId() == R.id.nqmInProgressGradingFormCamberGeometricsRadioGroup ||
			 	   group.getId() == R.id.nqmInProgressGradingFormSuperElevationGeometricsRadioGroup ||
			 	   group.getId() == R.id.nqmInProgressGradingFormLongitudinalGradientGeometricsRadioGroup ){
			 		
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
			 	if(group.getId() == R.id.nqmInProgressGradingFormQualityOfMaterialEarthWorkEmbankmentRadioGroup ||
		 	 	   group.getId() == R.id.nqmInProgressGradingFormCompactionEarthWorkEmbankmentRadioGroup ||
		 	 	   group.getId() == R.id.nqmInProgressGradingFormStabilityEarthWorkEmbankmentRadioGroup ||
		 	 	   group.getId() == R.id.nqmInProgressGradingFormSlopeProtectionEarthWorkEmbankmentRadioGroup ){
		 	 		
		 	 		RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemQualityOfMaterialEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId());
		 	 		RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemCompactionEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId());
		 	 		RadioButton selectedRdoBtn3 = (RadioButton) findViewById(subItemStabilityEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId());
		 	 		RadioButton selectedRdoBtn4 = (RadioButton) findViewById(subItemSlopeProtectionEarthWorkEmbankmentRadioGroup.getCheckedRadioButtonId());
		 	 		
		 	 		ArrayList<String> selectedValueArrLst = new ArrayList<String>();
		 	 		selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
		 	 		selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
		 	 		selectedValueArrLst.add(selectedRdoBtn3.getText().toString());
		 	 		selectedValueArrLst.add(selectedRdoBtn4.getText().toString());
		 	 		
		 			calculateGrading(selectedValueArrLst, earthWorkEmbankment_S, earthWorkEmbankment_SRI, earthWorkEmbankment_U, earthWorkEmbankment_NA);
		 	 	}
			 	
			 	
			 	// 5th Items - Subitem (SubBase)
		 	 	if(group.getId() == R.id.nqmInProgressGradingFormGrainSizeSubBaseRadioGroup ||
		  	 	   group.getId() == R.id.nqmInProgressGradingFormPlasticitySubBaseRadioGroup ||
		  	 	   group.getId() == R.id.nqmInProgressGradingFormCompactionSubBaseRadioGroup ||
		  	 	   group.getId() == R.id.nqmInProgressGradingFormTotalThicknessSubBaseRadioGroup ){
		  	 		
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
		 	if(group.getId() == R.id.nqmInProgressGradingFormGrainSizeBaseCourseWaterRadioGroup ||
		 	   group.getId() == R.id.nqmInProgressGradingFormPlasticityBaseCourseWaterRadioGroup ||
		 	   group.getId() == R.id.nqmInProgressGradingFormCompactionBaseCourseWaterRadioGroup ||
		 	   group.getId() == R.id.nqmInProgressGradingFormThicknessBaseCourseWater ){
		 		
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
			if(group.getId() == R.id.nqmInProgressGradingFormGradationBituminousRadioGroup ||
			   group.getId() == R.id.nqmInProgressGradingFormMixingTemperatureBituminousRadioGroup ||
			   group.getId() == R.id.nqmInProgressGradingFormLayingTemperatureBituminousRadioGroup ||
			   group.getId() == R.id.nqmInProgressGradingFormThicknessBituminousRadioGroup ||
			   group.getId() == R.id.nqmInProgressGradingFormSurfaceBituminousRadioGroup ){
				
				RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemGradationBituminousRadioGroup.getCheckedRadioButtonId());
				RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemMixingTemperatureBituminousRadioGroup.getCheckedRadioButtonId());
				RadioButton selectedRdoBtn3 = (RadioButton) findViewById(subItemLayingTemperatureBituminousRadioGroup.getCheckedRadioButtonId());
				RadioButton selectedRdoBtn4 = (RadioButton) findViewById(subItemThicknessBituminousRadioGroup.getCheckedRadioButtonId());
				RadioButton selectedRdoBtn5 = (RadioButton) findViewById(subItemSurfaceBituminousRadioGroup.getCheckedRadioButtonId());
				
				ArrayList<String> selectedValueArrLst = new ArrayList<String>();
				selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
				selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
				selectedValueArrLst.add(selectedRdoBtn3.getText().toString());
				selectedValueArrLst.add(selectedRdoBtn4.getText().toString());
				selectedValueArrLst.add(selectedRdoBtn5.getText().toString());
				
				calculateGrading(selectedValueArrLst, bituminous_S, bituminous_SRI, bituminous_U, bituminous_NA);
			}
			
			// 8th Items - Subitem (Shoulders)
			// Not Applicable for InProgress Road
		 	
			
			// 9th Items - Subitem (Cross Drainage)
			if(group.getId() == R.id.nqmInProgressGradingFormQualityOfMaterialCrossDrainageRadioGroup ||
			   group.getId() == R.id.nqmInProgressGradingFormQualityOfWorkmanshipCrossDrainageRadioGroup ){
				
				RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemQualityOfMaterialCrossDrainageRadioGroup.getCheckedRadioButtonId());
				RadioButton selectedRdoBtn2 = (RadioButton) findViewById(subItemQualityOfWorkmanshipCrossDrainageRadioGroup.getCheckedRadioButtonId());
				
				ArrayList<String> selectedValueArrLst = new ArrayList<String>();
				selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
				selectedValueArrLst.add(selectedRdoBtn2.getText().toString());
				
				calculateGrading(selectedValueArrLst, crossDrainage_S, crossDrainage_SRI, crossDrainage_U, crossDrainage_NA);
			}
			
			
			// 10th Items - Subitem (Side Drain)
			if(group.getId() == R.id.nqmInProgressGradingFormGeneralQualitySideDrainRadioGroup ){
				
				RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemGeneralQualitySideDrainRadioGroup.getCheckedRadioButtonId());
				
				ArrayList<String> selectedValueArrLst = new ArrayList<String>();
				selectedValueArrLst.add(selectedRdoBtn1.getText().toString());
				
				calculateGrading(selectedValueArrLst, sideDrain_S, sideDrain_SRI, sideDrain_U, sideDrain_NA);
			}
			
			// 11th Items - Subitem (CCSemiRigidPavement)
			if(group.getId() == R.id.nqmInProgressGradingFormQualityMaterialCCSemiRigidPavementRadioGroup ||
			   group.getId() == R.id.nqmInProgressGradingFormStrengthCCSemiRigidPavementRadioGroup ||
			   group.getId() == R.id.nqmInProgressGradingFormQualityCCSemiRigidPavementRadioGroup ||
			   group.getId() == R.id.nqmInProgressGradingFormThicknessCCSemiRigidPavementRadioGroup){
				
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
			if(group.getId() == R.id.nqmInProgressGradingFormCitizenInfoBoardRoadFurnituresRadioGroup ||
			   group.getId() == R.id.nqmInProgressGradingFormWhetherRoadFurnituresRadioGroup ){
				
				RadioButton selectedRdoBtn1 = (RadioButton) findViewById(subItemCitizenInfoBoardRoadFurnituresRadioGroup.getCheckedRadioButtonId());
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
			
			RadioButton S = (RadioButton) findViewById(R.id.nqmInProgressGradingFormOverallGradeRadio_S);
			RadioButton SRI = (RadioButton) findViewById(R.id.nqmInProgressGradingFormOverallGradeRadio_SRI);
			RadioButton U = (RadioButton) findViewById(R.id.nqmInProgressGradingFormOverallGradeRadio_U);
			RadioButton NA = (RadioButton) findViewById(R.id.nqmInProgressGradingFormOverallGradeRadio_NA);
			
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
		}//mrthod ends here
		
		
		public void saveGradingValues(){
			
			selectedRadioList = new ArrayList<String>();
			ArrayList<RadioButton> selectedRadioButtonArrayList  = new ArrayList<RadioButton>();
			
			selectedRadioButtonArrayList.add((RadioButton) findViewById(qualityArrangmentRadioGroup.getCheckedRadioButtonId()));
			selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemQualityArrangementRadioGroup.getCheckedRadioButtonId()));
			
			selectedRadioButtonArrayList.add((RadioButton) findViewById(attentionToQualityRadioGroup.getCheckedRadioButtonId()));
			selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemMaintenanceOfQCAttentionToQualityRadioGroup.getCheckedRadioButtonId()));
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
			selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemGradationBituminousRadioGroup.getCheckedRadioButtonId()));
			selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemMixingTemperatureBituminousRadioGroup.getCheckedRadioButtonId()));
			selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemLayingTemperatureBituminousRadioGroup.getCheckedRadioButtonId()));
			selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemThicknessBituminousRadioGroup.getCheckedRadioButtonId()));
			selectedRadioButtonArrayList.add((RadioButton) findViewById(subItemSurfaceBituminousRadioGroup.getCheckedRadioButtonId()));
			
			selectedRadioButtonArrayList.add((RadioButton) findViewById(shoulderRadioGroup.getCheckedRadioButtonId()));
						
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
			selectedRadioButtonArrayList.add( (RadioButton) findViewById(subItemCitizenInfoBoardRoadFurnituresRadioGroup.getCheckedRadioButtonId()));
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
			          

					public void onClick(DialogInterface dialog, int id) {
	
							/*Calendar c = Calendar.getInstance();
							SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
							String inspDate = df.format(c.getTime());*/
						// Change on 18-June-2014
						
						 //***********************
						   final ArrayList<String> commentEditText = new ArrayList<String>();
						
						   ImageDetailsDAO imageDetailsDAO = new ImageDetailsDAO();
						   ArrayList<ImageDetailsDTO> imageDetailsDTOArrayList =  imageDetailsDAO.getImageDetails(roadDetailsDTO.getUniqueCode(), parent, parent);
						 	//Calendar c = Calendar.getInstance();
							//SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
							String inspDate =  imageDetailsDTOArrayList.get(0).getCaptureDateTime();//df.format(imageDetailsDTOArrayList.get(0).getCaptureDateTime());
							// Change on 18-June-2014 ends here
						
							float fromChainage = Float.parseFloat(((EditText)  parent.findViewById(R.id.gradingFormFromChainageIdEditText)).getText().toString());
							float toChainage = Float.parseFloat(((EditText) parent.findViewById(R.id.gradingFormToChainageIdEditText)).getText().toString());
							ObservationDetailsDAO observationDetailsDAOObj = new ObservationDetailsDAO();
							long result = observationDetailsDAOObj.setObservationDetails(roadDetailsDTO, inspDate, fromChainage, toChainage, selectedRadioList,commentEditText, parent , parent);
							ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
							String status;
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
