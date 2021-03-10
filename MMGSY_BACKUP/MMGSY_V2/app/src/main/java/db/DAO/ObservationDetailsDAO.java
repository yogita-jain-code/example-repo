package db.DAO;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import common.Constant;
import common.ExceptionHandler;
import common.QMSHelper.NotificationEnum;

import db.MySqliteOpenHelper;
import db.DTO.LoginDetailsDTO;
import db.DTO.ObservationDetailsDTO;
import db.DTO.ScheduleDetailsDTO;
import db.interfaces.IObservationDetails;

public class ObservationDetailsDAO implements IObservationDetails{

	private MySqliteOpenHelper helper;
	private SQLiteDatabase database;

	

	@Override
	public Boolean setObservationDetails(
			ObservationDetailsDTO observationDetailsObj) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public long setLocationPoint(String uniqueCode, Boolean isStartPoint,String latitude, String longitude, Context context, Activity parent) {
		long updateRowCount = 0 ;
		helper = new MySqliteOpenHelper(context);
		
		try{
			ObservationDetailsDTO  observationDetailsDTO = getObservationDetails(uniqueCode, context, parent);
			
			if(observationDetailsDTO != null && observationDetailsDTO.getUniqueCode() != null ){
				// update
				ContentValues values = new ContentValues();
				
				if(isStartPoint){
					if(observationDetailsDTO.getStartLatitude() == null && observationDetailsDTO.getStartLongitude() == null){ 
							values.put(Constant.colStartLatitude,latitude);
							values.put(Constant.colStartLongitude,longitude);
						}else{
							return 113; // Start point already set
						}
				}else{
					
					if(observationDetailsDTO.getEndLatitude() == null && observationDetailsDTO.getEndLongitude() == null){
						values.put(Constant.colEndLatitude,latitude);
						values.put(Constant.colEndLongitude,longitude);
						}else{
							return 114; // End point already set
						}
				}
				
			if(database == null || !database.isOpen()){
				database = helper.getWritableDatabase();
				 }	
			updateRowCount = database.update(Constant.tblQmsObservationDetails,values,Constant.colUniqueCodeObservationDetails + " = '" + uniqueCode + "'",
						null);
			}else{
				
				// insert
				ContentValues values = new ContentValues();
				
				//values.put(Constant.colUniqueCodeObservationDetails,uniqueCode);
				if(isStartPoint){
					values.put(Constant.colUniqueCodeObservationDetails,uniqueCode);
					values.put(Constant.colStartLatitude,latitude);
					values.put(Constant.colStartLongitude,longitude);
					values.put(Constant.colIsUploadedObservationDetails,0);
					
				}else{
					values.put(Constant.colUniqueCodeObservationDetails,uniqueCode);
					values.put(Constant.colEndLatitude,latitude);
					values.put(Constant.colEndLongitude,longitude);
					values.put(Constant.colIsUploadedObservationDetails,0);
				}
				
				if(database == null || !database.isOpen()){
					database = helper.getWritableDatabase();
					 }	
				updateRowCount  = database.insert(Constant.tblQmsObservationDetails, null, values);
			}
			Log.d(Constant.tag, "Insert Complete");
			return updateRowCount;
		}//try
		catch(Exception e){
		// throws to custom error class	
		new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		return 0;	
		}
		finally{
		if(helper != null)
		helper.close();
		if(database != null || database.isOpen())
		database.close();
		}
		
	}
	
	public Boolean checkInspectionDetailsForPerDayValidation(Context context,ScheduleDetailsDTO scheduleDetailsDTO){
		
		String uniqueCode = scheduleDetailsDTO.getUniqueCode();
		
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		
		String captureDateTime = null;
		int inspectionCount = 0;
    	try{
    	
    		Cursor status= database.rawQuery("select CaptureDateTime from QMS_IMAGE_DETAILS where UniqueCode = '" + uniqueCode + "' ", null);
    		if( status != null && status.moveToFirst() ){
    			captureDateTime = status.getString(status.getColumnIndex("CaptureDateTime"));
    			String[] tokens = captureDateTime.split(" ");
				if(tokens.length > 0)
				{
					captureDateTime = tokens[0].trim();
				}
				status.close();
    		}
			
			if(captureDateTime != null)
			{
				Cursor inspectionCountCursor= database.rawQuery("select count( * ) from  QMS_OBSERVATION_DETAILS Where  InspectionDate like  '%"+captureDateTime+"%' ", null);
				if( inspectionCountCursor != null && inspectionCountCursor.moveToFirst() ){
					
					//inspectionCount = inspectionCountCursor.getInt(status.getColumnIndex("UniqueCode"));
					
					inspectionCount = inspectionCountCursor.getInt(0);
					//inspectionCount = inspectionCountCursor.getInt(0);
					inspectionCountCursor.close();
				}
				
				
				if(scheduleDetailsDTO.getRoadStatus().equalsIgnoreCase("P") || scheduleDetailsDTO.getRoadStatus().equalsIgnoreCase("C") || scheduleDetailsDTO.getRoadStatus().equalsIgnoreCase("L"))
				{
					if(inspectionCount > 3)
					{
						return false;
					}
					else
					{
						return true;
					}
				}
				else if(scheduleDetailsDTO.getRoadStatus().equalsIgnoreCase("M"))
				{
					if(inspectionCount > 5)
					{
						return false;
					}
					else
					{
						return true;
					}
				}
				else
				{
					return true;
				}
			}
			else
			{
				return true;
			}
			
		}
    	catch(Exception e)
    	{
    		Log.e("Error " , e.toString());
    	}
		finally{
    		if(helper != null)
    		helper.close();
    		if(database != null || database.isOpen())
    		database.close();
		}
    	
		return false;
	}


	//Set Observation Details
	public long setObservationDetails(ScheduleDetailsDTO roadDetailsDTO, String inspDate, float fromChainage, float toChainage, ArrayList<String> selectedRadioList, ArrayList<String> commentEditText,  Context context, Activity parent) {
		long updateRowCount = 0 ;
		helper = new MySqliteOpenHelper(context);
		String gradeItems = "";
		String commentItems = "";
		try{
			ObservationDetailsDTO  observationDetailsDTO = getObservationDetails(roadDetailsDTO.getUniqueCode(), context, parent);
			
			for (String strGrading : selectedRadioList) {
				gradeItems = gradeItems + "," + strGrading; //comma separated grading values
			}
			
			for (String strComment : commentEditText) {
				commentItems = commentItems + "," + strComment; //comma separated grading values
			}
			
			if(observationDetailsDTO != null && observationDetailsDTO.getUniqueCode() != null ){
				// update
				ContentValues values = new ContentValues();
				values.put(Constant.colInspectionDate,inspDate);
				values.put(Constant.colFromChainage,fromChainage);
				values.put(Constant.colToChainage,toChainage);
				values.put(Constant.colGradingParams,gradeItems);
				values.put(Constant.colCommentParams,commentItems);
				
					
							
			if(database == null || !database.isOpen()){
				database = helper.getWritableDatabase();
				 }	
			updateRowCount = database.update(Constant.tblQmsObservationDetails,values,Constant.colUniqueCodeObservationDetails + " = '" + roadDetailsDTO.getUniqueCode() + "'",
						null);
			}else{
				
				// insert
				ContentValues values = new ContentValues();
				values.put(Constant.colUniqueCodeObservationDetails,roadDetailsDTO.getUniqueCode());
				values.put(Constant.colInspectionDate,inspDate);
				values.put(Constant.colFromChainage,fromChainage);
				values.put(Constant.colToChainage,toChainage);
				values.put(Constant.colGradingParams,gradeItems);
				values.put(Constant.colCommentParams,commentItems);
				values.put(Constant.colStartLatitude,"");
				values.put(Constant.colStartLongitude,"");
				values.put(Constant.colEndLatitude,"");
				values.put(Constant.colEndLongitude,"");
				values.put(Constant.colIsUploadedObservationDetails,0);
				
				if(database == null || !database.isOpen()){
					database = helper.getWritableDatabase();
					 }	
				updateRowCount  = database.insert(Constant.tblQmsObservationDetails, null, values);
			}
			Log.d(Constant.tag, "Insert Complete");
			return updateRowCount;
		}//try
		catch(Exception e){
		// throws to custom error class	
		new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		return 0;	
		}
		finally{
		if(helper != null)
		helper.close();
		if(database != null || database.isOpen())
		database.close();
		}
		
	}
	
	

	@Override
	public ObservationDetailsDTO getObservationDetails(String uniqueCode,
			Context context, Activity parent) {
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select * from  "
				+ Constant.tblQmsObservationDetails + " where  " + Constant.colUniqueCodeObservationDetails + " =  '" + uniqueCode.trim() + "'";

		Cursor cursor = null;
		ObservationDetailsDTO observationDetailsDTOObj = new ObservationDetailsDTO();
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					observationDetailsDTOObj.setUniqueCode(cursor.getString(0));
					observationDetailsDTOObj.setInspectionDate(cursor.getString(1));
					observationDetailsDTOObj.setFromChainage(cursor.getFloat(2));
					observationDetailsDTOObj.setToChainage(cursor.getFloat(3));
					observationDetailsDTOObj.setGradingParams(cursor.getString(4));
					observationDetailsDTOObj.setCommentParams(cursor.getString(5));
					observationDetailsDTOObj.setStartLatitude(cursor.getString(6));
					observationDetailsDTOObj.setStartLongitude(cursor.getString(7));
					observationDetailsDTOObj.setEndLatitude(cursor.getString(8));
					observationDetailsDTOObj.setEndLongitude(cursor.getString(9));
					observationDetailsDTOObj.setObservationId(cursor.getInt(10));
					observationDetailsDTOObj.setIsUploaded(cursor.getInt(11));
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}else{
				observationDetailsDTOObj = null;
			}
		} catch (Exception e) {

		}
		finally{
			if(cursor != null){
				cursor.close();
			}
			if(database != null || database.isOpen())
			database.close();
			if(helper != null)
			helper.close();
			
		}
		return observationDetailsDTOObj;
	}


	@Override
	public long updateObservationDetails(String uniqueCode, String obervationId, Context context, Activity parent) {
		long updateRowCount = 0 ;
		helper = new MySqliteOpenHelper(context);
		
		try{
			
		 		// update
				ContentValues values = new ContentValues();
		
	 			values.put(Constant.colObservationIdObservationDetails,obervationId);
				values.put(Constant.colIsUploadedObservationDetails,1);
				
 			    if(database == null || !database.isOpen()){
				database = helper.getWritableDatabase();
				 }	
			updateRowCount = database.update(Constant.tblQmsObservationDetails,values,Constant.colUniqueCodeObservationDetails + " = '" + uniqueCode + "'",
						null);
			
			Log.d(Constant.tag, "Insert Complete");
			return updateRowCount;
		}//try
		catch(Exception e){
		// throws to custom error class	
		new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		return 0;	
		}
		finally{
		if(helper != null)
		helper.close();
		if(database != null || database.isOpen())
		database.close();
		}
		
	}

	
	@Override
	public ArrayList<ObservationDetailsDTO> getAllObservationDetails(Context context) {
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select * from  "
				+ Constant.tblQmsObservationDetails + " where  " + Constant.colIsUploadedObservationDetails + " =  " + 1  ;

		Cursor cursor = null;
		ArrayList<ObservationDetailsDTO> ObservationDetailsDTOArrayList = new ArrayList<ObservationDetailsDTO>();
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					ObservationDetailsDTO observationDetailsDTOObj = new ObservationDetailsDTO();
					/*observationDetailsDTOObj.setUniqueCode(cursor.getString(0));
					observationDetailsDTOObj.setInspectionDate(cursor.getString(1));
					observationDetailsDTOObj.setFromChainage(cursor.getFloat(2));
					observationDetailsDTOObj.setToChainage(cursor.getFloat(3));
					observationDetailsDTOObj.setGradingParams(cursor.getString(4));
					observationDetailsDTOObj.setStartLatitude(cursor.getString(5));
					observationDetailsDTOObj.setStartLongitude(cursor.getString(6));
					observationDetailsDTOObj.setEndLatitude(cursor.getString(7));
					observationDetailsDTOObj.setEndLongitude(cursor.getString(8));
					observationDetailsDTOObj.setObservationId(cursor.getInt(9));
					observationDetailsDTOObj.setIsUploaded(cursor.getInt(10));*/
					
					observationDetailsDTOObj.setUniqueCode(cursor.getString(0));
					observationDetailsDTOObj.setInspectionDate(cursor.getString(1));
					observationDetailsDTOObj.setFromChainage(cursor.getFloat(2));
					observationDetailsDTOObj.setToChainage(cursor.getFloat(3));
					observationDetailsDTOObj.setGradingParams(cursor.getString(4));
					observationDetailsDTOObj.setCommentParams(cursor.getString(5));
					observationDetailsDTOObj.setStartLatitude(cursor.getString(6));
					observationDetailsDTOObj.setStartLongitude(cursor.getString(7));
					observationDetailsDTOObj.setEndLatitude(cursor.getString(8));
					observationDetailsDTOObj.setEndLongitude(cursor.getString(9));
					observationDetailsDTOObj.setObservationId(cursor.getInt(10));
					observationDetailsDTOObj.setIsUploaded(cursor.getInt(11));
					
					ObservationDetailsDTOArrayList.add(observationDetailsDTOObj);
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}else{
				ObservationDetailsDTOArrayList = null;
			}
		} catch (Exception e) {

		}
		finally{
			if(cursor != null){
				cursor.close();
			}
			if(database != null || database.isOpen())
			database.close();
			if(helper != null)
			helper.close();
			
		}
		return ObservationDetailsDTOArrayList;
	}
	
	@Override
	public Boolean emptyObservationDetails(Context context){
		Integer result = 0;
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		result = database.delete(Constant.tblQmsObservationDetails, null, null);
		
		return result > 0 ? true : false;
		
	}
	
	
	@Override
	public long updateObservationDetailsUnplanned(String uniqueCode, ObservationDetailsDTO obervationDetailsDTOObj, Context context, Activity parent) {
		long updateRowCount = 0 ;
		helper = new MySqliteOpenHelper(context);
		
		try{
			
		 		// update
				ContentValues values = new ContentValues();
		
				values.put(Constant.colUniqueCodeObservationDetails,obervationDetailsDTOObj.getUniqueCode());
				values.put(Constant.colInspectionDate,obervationDetailsDTOObj.getInspectionDate());
				values.put(Constant.colFromChainage,obervationDetailsDTOObj.getFromChainage());
				values.put(Constant.colToChainage,obervationDetailsDTOObj.getToChainage());
				values.put(Constant.colGradingParams,obervationDetailsDTOObj.getGradingParams());
				values.put(Constant.colStartLatitude,obervationDetailsDTOObj.getStartLatitude());
				values.put(Constant.colStartLongitude,obervationDetailsDTOObj.getStartLongitude());
				values.put(Constant.colEndLatitude,obervationDetailsDTOObj.getEndLatitude());
				values.put(Constant.colEndLongitude,obervationDetailsDTOObj.getEndLongitude());
				values.put(Constant.colObservationIdObservationDetails,obervationDetailsDTOObj.getObservationId());
				values.put(Constant.colIsUploadedObservationDetails,obervationDetailsDTOObj.getIsUploaded());
				
 			    if(database == null || !database.isOpen()){
				database = helper.getWritableDatabase();
				 }	
			updateRowCount = database.update(Constant.tblQmsObservationDetails,values,Constant.colUniqueCodeObservationDetails + " = '" + uniqueCode + "'",
						null);
			
			Log.d(Constant.tag, "Insert Complete");
			return updateRowCount;
		}//try
		catch(Exception e){
		// throws to custom error class	
		new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		return 0;	
		}
		finally{
		if(helper != null)
		helper.close();
		if(database != null || database.isOpen())
		database.close();
		}
		
	}
	
}
