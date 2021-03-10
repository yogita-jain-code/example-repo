package db.DAO;

import java.util.ArrayList;
import java.util.Calendar;

import common.Constant;
import common.ExceptionHandler;
import common.Notification;
import common.QMSHelper.NotificationEnum;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.DateTimeKeyListener;
import android.util.Log;

import db.MySqliteOpenHelper;

import db.DTO.ObservationDetailsDTO;
import db.DTO.ScheduleDetailsDTO;
import db.interfaces.IScheduleDetails;

public class ScheduleDetailsDAO implements IScheduleDetails{

	private MySqliteOpenHelper helper;
	private SQLiteDatabase database;
	
	@Override
	public Boolean setScheduleDetails(ArrayList<ScheduleDetailsDTO> scheduleDetailsArrayList,Context context, Activity parent) {

		MySqliteOpenHelper localHelper = new MySqliteOpenHelper(context);
	    SQLiteDatabase	localDatabase = localHelper.getWritableDatabase();
			
		 long insertCount = 0;
		 
		 try {
		 for (ScheduleDetailsDTO scheduleDetailsDTO : scheduleDetailsArrayList) {
			
			 if(getScheduleDetails(scheduleDetailsDTO.getUniqueCode(),context, parent) == null){
			 
			    ContentValues values = new ContentValues();
				values.put(Constant.colUniqueCodeScheduleDetails,scheduleDetailsDTO.getUniqueCode());
				values.put(Constant.colScheduleMonth,scheduleDetailsDTO.getScheduleMonth());
				values.put(Constant.colScheduleYear,scheduleDetailsDTO.getScheduleYear());
				values.put(Constant.colStateName,scheduleDetailsDTO.getStateName());
				values.put(Constant.colDistrictName,scheduleDetailsDTO.getDistrictName());
				values.put(Constant.colBlockName,scheduleDetailsDTO.getBlockName());
				values.put(Constant.colSanctionYear,scheduleDetailsDTO.getSanctionYear());
				values.put(Constant.colPackageId,scheduleDetailsDTO.getPackageId());
				values.put(Constant.colRoadName,scheduleDetailsDTO.getRoadName());
				values.put(Constant.colRoadLength,scheduleDetailsDTO.getRoadLength());
				values.put(Constant.colRoadStatus,scheduleDetailsDTO.getRoadStatus());
				values.put(Constant.colCompletionDate,scheduleDetailsDTO.getCompletionDate());
				values.put(Constant.colIsRecordUploaded,0);
				values.put(Constant.colIsGeneratedUniqueId,0);
				values.put(Constant.colIsEnquiry,scheduleDetailsDTO.getIsEnquiry());
				values.put(Constant.colIsUnplanFinalize,0);
				values.put(Constant.colstatus, scheduleDetailsDTO.getStatus());
				values.put(Constant.colScheduleDownloadDate, scheduleDetailsDTO.getScheduleDownloadDate());
				
				insertCount  = localDatabase.insert(Constant.tblQmsScheduleDetails, null, values);
				
			 }else{
				 insertCount = 1;
			 }
		}
		 }
		 catch (Exception e) {
				e.printStackTrace();
				new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
			}
			finally{
				
				if(localHelper != null)
					localHelper.close();
				if(localDatabase != null)
					localDatabase.close();
			}
		 
		 
			
		return insertCount > 0 ? Boolean.TRUE : Boolean.FALSE ;
	}

	@Override
	public ScheduleDetailsDTO getScheduleDetails(String uniqueCode,
			Context context, Activity parent) {
	
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select * from  "
				+ Constant.tblQmsScheduleDetails + " where " + Constant.colUniqueCodeScheduleDetails + " = '" + uniqueCode + "'";

		Cursor cursor = null;
		ScheduleDetailsDTO ScheduleDetailsDTOObj = null;
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					ScheduleDetailsDTOObj = new ScheduleDetailsDTO();
					ScheduleDetailsDTOObj.setUniqueCode(cursor.getString(0));
					ScheduleDetailsDTOObj.setScheduleMonth(cursor.getInt(1));
					ScheduleDetailsDTOObj.setScheduleYear(cursor.getInt(2));
					ScheduleDetailsDTOObj.setStateName(cursor.getString(3));
					ScheduleDetailsDTOObj.setDistrictName(cursor.getString(4));
					ScheduleDetailsDTOObj.setBlockName(cursor.getString(5));
					ScheduleDetailsDTOObj.setPackageId(cursor.getString(6));
					ScheduleDetailsDTOObj.setSanctionYear(cursor.getString(7));
					ScheduleDetailsDTOObj.setRoadName(cursor.getString(8));
					ScheduleDetailsDTOObj.setRoadLength(cursor.getFloat(9));
					ScheduleDetailsDTOObj.setRoadStatus(cursor.getString(10));
					ScheduleDetailsDTOObj.setCompletionDate(cursor.getString(11));
					ScheduleDetailsDTOObj.setIsRecordUploaded(Integer.parseInt(cursor.getString(12)));
					ScheduleDetailsDTOObj.setIsGeneratedUniqueId(Integer.parseInt(cursor.getString(13)));
					ScheduleDetailsDTOObj.setIsEnquiry(Integer.parseInt(cursor.getString(14)));
					ScheduleDetailsDTOObj.setIsUnplanFinalize(Integer.parseInt(cursor.getString(15)));
					ScheduleDetailsDTOObj.setScheduleDownloadDate(cursor.getString(17));
					
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		}
		finally{
			if(cursor != null){
				cursor.close();
			}
			if(helper != null)
			helper.close();
			if(database != null || database.isOpen())
			database.close();
		}
		return ScheduleDetailsDTOObj;
		
	}

	@Override
	public ArrayList<ScheduleDetailsDTO> getAllScheduleDetails(Context context, Activity parent, Integer entryMode, String status) {

		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select * from  " + Constant.tblQmsScheduleDetails + " where "+Constant.colIsGeneratedUniqueId +" = "+ entryMode + " and "+ Constant.colstatus +" = '"+ status +"'";

		Cursor cursor = null;
		ArrayList<ScheduleDetailsDTO> scheduleDetailsDTOArrayList = new ArrayList<ScheduleDetailsDTO>();
		ScheduleDetailsDTO ScheduleDetailsDTOObj = null;
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					ScheduleDetailsDTOObj = new ScheduleDetailsDTO();
					ScheduleDetailsDTOObj.setUniqueCode(cursor.getString(0));
					ScheduleDetailsDTOObj.setScheduleMonth(cursor.getInt(1));
					ScheduleDetailsDTOObj.setScheduleYear(cursor.getInt(2));
					ScheduleDetailsDTOObj.setStateName(cursor.getString(3));
					ScheduleDetailsDTOObj.setDistrictName(cursor.getString(4));
					ScheduleDetailsDTOObj.setBlockName(cursor.getString(5));
					ScheduleDetailsDTOObj.setPackageId(cursor.getString(6));
					ScheduleDetailsDTOObj.setSanctionYear(cursor.getString(7));
					ScheduleDetailsDTOObj.setRoadName(cursor.getString(8));
					ScheduleDetailsDTOObj.setRoadLength(cursor.getFloat(9));
					ScheduleDetailsDTOObj.setRoadStatus(cursor.getString(10));
					ScheduleDetailsDTOObj.setCompletionDate(cursor.getString(11));
					ScheduleDetailsDTOObj.setIsRecordUploaded(Integer.parseInt(cursor.getString(12)));
					ScheduleDetailsDTOObj.setIsGeneratedUniqueId(Integer.parseInt(cursor.getString(13)));
					ScheduleDetailsDTOObj.setIsEnquiry(Integer.parseInt(cursor.getString(14)));
					ScheduleDetailsDTOObj.setIsUnplanFinalize(Integer.parseInt(cursor.getString(15)));
					ScheduleDetailsDTOObj.setScheduleDownloadDate(cursor.getString(17));
					
					scheduleDetailsDTOArrayList.add(ScheduleDetailsDTOObj);
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		}
		finally{
			if(cursor != null){
				cursor.close();
			}
			if(helper != null)
			helper.close();
			if(database != null || database.isOpen())
			database.close();
		}
		return scheduleDetailsDTOArrayList;

	}
	
	
	public int generateUniqueId(Context context, Activity parent)
	{
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		int maxgeneratedIdCnt = 0;
		int unFinalizeCnt = 0;
		Integer generatedId = 0;
		
		String sqlUnFinalizedEntry = "select count(*) from  " + Constant.tblQmsScheduleDetails +
				 	 			   " where " + Constant.colIsGeneratedUniqueId + " = 1 and "+
				 	 			    Constant.colIsUnplanFinalize + " = 0";
		
		String sqlGenIdRecordCnt = "select count(*) from  " + Constant.tblQmsScheduleDetails + 
	 			   					" where " + Constant.colIsGeneratedUniqueId + " = 1";

		Cursor cursor = null;
		Cursor cursor1 = null;		
		try {
			 //check for very first entry
			 
			
			 //check - for last generated Id's finalization
			 cursor = database.rawQuery(sqlUnFinalizedEntry, null);
			 if (!cursor.isAfterLast()) {
				 cursor.moveToFirst();
				do {
					unFinalizeCnt = cursor.getInt(0); //get number of unfinalized unplanned records with generated Id
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			 }
			
			 if(unFinalizeCnt > 0)
			 {
				 return -1;
			 }
			 cursor.close();
			 
			//Get Max generated Id Count
			 cursor1 = database.rawQuery(sqlGenIdRecordCnt, null);

			if (!cursor1.isAfterLast()) {
				cursor1.moveToFirst();
				do {
					maxgeneratedIdCnt = cursor1.getInt(0); //get number of unplanned records with generated Id
					cursor1.moveToNext();
				} while (!cursor1.isAfterLast());
			}
			
			 //insert code start here
			 generatedId = maxgeneratedIdCnt == 0 ? 1 : (maxgeneratedIdCnt + 1);
			 Calendar c = Calendar.getInstance();
			 int scheduleYear = c.get(Calendar.YEAR);
			 int scheduleMonth = c.get(Calendar.MONTH)+1;
			 
	   		 ContentValues values = new ContentValues();
	   		 values.put(Constant.colUniqueCodeScheduleDetails,generatedId.toString());
	   		 values.put(Constant.colScheduleMonth, scheduleMonth);
		 	 values.put(Constant.colScheduleYear, scheduleYear);
			 values.put(Constant.colStateName,"");
			 values.put(Constant.colDistrictName,"");
			 values.put(Constant.colBlockName,"");
			 values.put(Constant.colSanctionYear,"");
			 values.put(Constant.colPackageId,"");
			 values.put(Constant.colRoadName,"");
			 values.put(Constant.colRoadLength,"");
			 values.put(Constant.colRoadStatus,"");
			 values.put(Constant.colCompletionDate,"");
			 values.put(Constant.colIsRecordUploaded,0);
			 values.put(Constant.colIsGeneratedUniqueId,1);
			 values.put(Constant.colIsEnquiry,0);
			 values.put(Constant.colIsUnplanFinalize,0);
			 values.put(Constant.colstatus,"T");
	   		 
 		 	 if(database.insert(Constant.tblQmsScheduleDetails, null, values) < 1)
 		 	 {
 		 		generatedId = 0;
 		 	 }
		} catch (Exception e) {
			e.printStackTrace();
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		}
		finally{
			if(cursor != null){
				cursor.close();
			}
			if(cursor1 != null){
				cursor1.close();
			}
			if(helper != null)
			helper.close();
			if(database != null || database.isOpen())
			database.close();
		}
		
		return generatedId;
	
	}//generateUniqueId ends here


	@Override
	public ScheduleDetailsDTO getGeneratedIds(Context context, Activity parent) {

		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		
		//Last un-finalized Entry, required in Take Photos screen
		String	sql = "select * from  " + Constant.tblQmsScheduleDetails +
				  " where " + Constant.colIsGeneratedUniqueId + " = 1 and "+
				  Constant.colIsUnplanFinalize + " = 0";
		

		Cursor cursor = null;
		ScheduleDetailsDTO ScheduleDetailsDTOObj = null;
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					ScheduleDetailsDTOObj = new ScheduleDetailsDTO();
					ScheduleDetailsDTOObj.setUniqueCode(cursor.getString(0));
					ScheduleDetailsDTOObj.setScheduleMonth(cursor.getInt(1));
					ScheduleDetailsDTOObj.setScheduleYear(cursor.getInt(2));
					ScheduleDetailsDTOObj.setStateName(cursor.getString(3));
					ScheduleDetailsDTOObj.setDistrictName(cursor.getString(4));
					ScheduleDetailsDTOObj.setBlockName(cursor.getString(5));
					ScheduleDetailsDTOObj.setPackageId(cursor.getString(6));
					ScheduleDetailsDTOObj.setSanctionYear(cursor.getString(7));
					ScheduleDetailsDTOObj.setRoadName(cursor.getString(8));
					ScheduleDetailsDTOObj.setRoadLength(cursor.getFloat(9));
					ScheduleDetailsDTOObj.setRoadStatus(cursor.getString(10));
					ScheduleDetailsDTOObj.setCompletionDate(cursor.getString(11));
					ScheduleDetailsDTOObj.setIsRecordUploaded(Integer.parseInt(cursor.getString(12)));
					ScheduleDetailsDTOObj.setIsGeneratedUniqueId(Integer.parseInt(cursor.getString(13)));
					ScheduleDetailsDTOObj.setIsEnquiry(Integer.parseInt(cursor.getString(14)));
					ScheduleDetailsDTOObj.setIsUnplanFinalize(Integer.parseInt(cursor.getString(15)));
					ScheduleDetailsDTOObj.setStatus("T");
					
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		}
		finally{
			if(cursor != null){
				cursor.close();
			}
			if(helper != null)
			helper.close();
			if(database != null || database.isOpen())
			database.close();
		}
		return ScheduleDetailsDTOObj;

	}//getAllGenerated Ids ends here
	
	
	
	
	@Override
	public ArrayList<ScheduleDetailsDTO> getAllFinalizedGeneratedIds(Context context, Activity parent) {

		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		ArrayList<ScheduleDetailsDTO> scheduleDetailsDTOArrayList = new ArrayList<ScheduleDetailsDTO>();
		ScheduleDetailsDTO ScheduleDetailsDTOObj = null;
		
		//all finalized Ids, required in Assign Road screen
		String	sql = "select * from  " + Constant.tblQmsScheduleDetails +
				  " where " + Constant.colIsGeneratedUniqueId + " = 1 and "+
				  Constant.colIsUnplanFinalize + " = 1 and "+Constant.colstatus +" = 'T'";
			
			//actual condition is below, replace above with this
			/*
			sql = "select * from  " + Constant.tblQmsScheduleDetails +
					  " where " + Constant.colIsGeneratedUniqueId + " = 1 and "+
					  Constant.colIsUnplanFinalize + " = 1 and " + Constant.colUniqueCodeScheduleDetails + 
					  " not like '%_%'"; */
		
		Cursor cursor = null;
			
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					ScheduleDetailsDTOObj = new ScheduleDetailsDTO();
					ScheduleDetailsDTOObj.setUniqueCode(cursor.getString(0));
					ScheduleDetailsDTOObj.setScheduleMonth(cursor.getInt(1));
					ScheduleDetailsDTOObj.setScheduleYear(cursor.getInt(2));
					ScheduleDetailsDTOObj.setStateName(cursor.getString(3));
					ScheduleDetailsDTOObj.setDistrictName(cursor.getString(4));
					ScheduleDetailsDTOObj.setBlockName(cursor.getString(5));
					ScheduleDetailsDTOObj.setPackageId(cursor.getString(6));
					ScheduleDetailsDTOObj.setSanctionYear(cursor.getString(7));
					ScheduleDetailsDTOObj.setRoadName(cursor.getString(8));
					ScheduleDetailsDTOObj.setRoadLength(cursor.getFloat(9));
					ScheduleDetailsDTOObj.setRoadStatus(cursor.getString(10));
					ScheduleDetailsDTOObj.setCompletionDate(cursor.getString(11));
					ScheduleDetailsDTOObj.setIsRecordUploaded(Integer.parseInt(cursor.getString(12)));
					ScheduleDetailsDTOObj.setIsGeneratedUniqueId(Integer.parseInt(cursor.getString(13)));
					ScheduleDetailsDTOObj.setIsEnquiry(Integer.parseInt(cursor.getString(14)));
					ScheduleDetailsDTOObj.setIsUnplanFinalize(Integer.parseInt(cursor.getString(15)));
					ScheduleDetailsDTOObj.setStatus(cursor.getString(16));
					
					scheduleDetailsDTOArrayList.add(ScheduleDetailsDTOObj);
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		}
		finally{
			if(cursor != null){
				cursor.close();
			}
			if(helper != null)
			helper.close();
			if(database != null || database.isOpen())
			database.close();
		}
		return scheduleDetailsDTOArrayList;

	}//getAllFinalizedGeneratedIds ends here
	
	

	public long finalizeEntryWithGeneratedId(Context context, Activity parent)
	{
		long updateRowCount = 0 ;
		helper = new MySqliteOpenHelper(context);
		
		try{
				ContentValues values = new ContentValues();
				values.put(Constant.colIsUnplanFinalize,1);

				if(database == null || !database.isOpen()){
					database = helper.getWritableDatabase();
				}
				
			updateRowCount = database.update(Constant.tblQmsScheduleDetails,values, Constant.colIsUnplanFinalize + " = 0",
						null);
			
			Log.d(Constant.tag, "Update Complete");
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
	}//finalize ends here
	
	
	
	public long mapRoadWithGeneratedId(ScheduleDetailsDTO scheduleDetailsDTO, String selectedGeneratedId, Context context, Activity parent)
	{
		long updateRowCount = 0 ;
		helper = new MySqliteOpenHelper(context);
		if(database == null || !database.isOpen()){
			database = helper.getWritableDatabase();
		}
		
		try{
			ContentValues values = new ContentValues();
			//here set unique code as Admin_Schedule_Code + Ims_Pr_Road_code
			String adminScheduleCode = "NeedToExtract"; //Need to extract adminScheduleCode 
			
			values.put(Constant.colUniqueCodeScheduleDetails,scheduleDetailsDTO.getUniqueCode());
			//values.put(Constant.colScheduleMonth,scheduleDetailsDTO.getScheduleMonth());
			//values.put(Constant.colScheduleYear,scheduleDetailsDTO.getScheduleYear());
			values.put(Constant.colStateName,scheduleDetailsDTO.getStateName());
			values.put(Constant.colDistrictName,scheduleDetailsDTO.getDistrictName());
			values.put(Constant.colBlockName,scheduleDetailsDTO.getBlockName());
			values.put(Constant.colSanctionYear,scheduleDetailsDTO.getSanctionYear());
			values.put(Constant.colPackageId,scheduleDetailsDTO.getPackageId());
			values.put(Constant.colRoadName,scheduleDetailsDTO.getRoadName());
			values.put(Constant.colRoadLength,scheduleDetailsDTO.getRoadLength());
			values.put(Constant.colRoadStatus,scheduleDetailsDTO.getRoadStatus());
			values.put(Constant.colCompletionDate,scheduleDetailsDTO.getCompletionDate());
			values.put(Constant.colIsRecordUploaded,0);
			values.put(Constant.colIsGeneratedUniqueId,1);
			values.put(Constant.colIsEnquiry,0);
			values.put(Constant.colIsUnplanFinalize,1);
			values.put(Constant.colstatus,scheduleDetailsDTO.getStatus());
			
			updateRowCount = database.update(Constant.tblQmsScheduleDetails,values, Constant.colUniqueCodeScheduleDetails + " = '" + selectedGeneratedId  +"'",
						null);
			
			Log.d(Constant.tag, "Update Complete");
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
	}//mapRoadWithGeneratedId ends here
	
	
	@Override
	public ArrayList<ScheduleDetailsDTO> getUnuploadedRecords(Context context, Activity parent) {

		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		ArrayList<ScheduleDetailsDTO> scheduleDetailsDTOArrayList = new ArrayList<ScheduleDetailsDTO>();
		ScheduleDetailsDTO ScheduleDetailsDTOObj = null;
		
		//get unuploaded Road list on screen
		String	sql = "SELECT id."+Constant.colUniqueCodeImageDetails+" , sd."+Constant.colScheduleMonth +" , sd."+Constant.colScheduleYear+" , sd."+Constant.colStateName+" , sd."+Constant.colDistrictName+" , sd."+Constant.colBlockName+" , sd."+Constant.colPackageId+" , sd."+Constant.colSanctionYear+" , sd."+Constant.colRoadName+" , sd."+Constant.colRoadLength+" , sd."+Constant.colRoadStatus+" , sd."+Constant.colCompletionDate+" , sd."+Constant.colIsRecordUploaded+" , sd."+Constant.colIsGeneratedUniqueId+" , sd."+Constant.colIsEnquiry+" FROM "+ Constant.tblQmsImageDetails +" id INNER JOIN "+Constant.tblQmsScheduleDetails+" sd ON id.UniqueCode = sd.UniqueCode INNER JOIN "+Constant.tblQmsObservationDetails+" od ON od.UniqueCode = sd.UniqueCode WHERE id.fileid >"+ Constant.minImage +" AND sd.isRecordUploaded = 0 AND od.gradingparams is not null group by 1";
			
			//actual condition is below, replace above with this
			/*
			sql = "select * from  " + Constant.tblQmsScheduleDetails +
					  " where " + Constant.colIsGeneratedUniqueId + " = 1 and "+
					  Constant.colIsUnplanFinalize + " = 1 and " + Constant.colUniqueCodeScheduleDetails + 
					  " not like '%_%'"; */
		
		Cursor cursor = null;
			
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					ScheduleDetailsDTOObj = new ScheduleDetailsDTO();
					ScheduleDetailsDTOObj.setUniqueCode(cursor.getString(0));
					ScheduleDetailsDTOObj.setScheduleMonth(cursor.getInt(1));
					ScheduleDetailsDTOObj.setScheduleYear(cursor.getInt(2));
					ScheduleDetailsDTOObj.setStateName(cursor.getString(3));
					ScheduleDetailsDTOObj.setDistrictName(cursor.getString(4));
					ScheduleDetailsDTOObj.setBlockName(cursor.getString(5));
					ScheduleDetailsDTOObj.setPackageId(cursor.getString(6));
					ScheduleDetailsDTOObj.setSanctionYear(cursor.getString(7));
					ScheduleDetailsDTOObj.setRoadName(cursor.getString(8));
					ScheduleDetailsDTOObj.setRoadLength(cursor.getFloat(9));
					ScheduleDetailsDTOObj.setRoadStatus(cursor.getString(10));
					ScheduleDetailsDTOObj.setCompletionDate(cursor.getString(11));
					ScheduleDetailsDTOObj.setIsRecordUploaded(Integer.parseInt(cursor.getString(12)));
					ScheduleDetailsDTOObj.setIsGeneratedUniqueId(Integer.parseInt(cursor.getString(13)));
					ScheduleDetailsDTOObj.setIsEnquiry(Integer.parseInt(cursor.getString(14)));
					
					
					scheduleDetailsDTOArrayList.add(ScheduleDetailsDTOObj);
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		}
		finally{
			if(cursor != null){
				cursor.close();
			}
			if(helper != null)
			helper.close();
			if(database != null || database.isOpen())
			database.close();
		}
		return scheduleDetailsDTOArrayList;

	}//getAllFinalizedGeneratedIds ends here
	
	@Override
	public Boolean emptyScheduleDetails(Context context){
		Integer result = 0;
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		result =	database.delete(Constant.tblQmsScheduleDetails, null, null);
		
		return result > 0 ? true : false;
		
	}
	
	
	public long updateStatus(String uniqueCode, String status, Context context, Activity parent)
	{
		long updateRowCount = 0 ;
		helper = new MySqliteOpenHelper(context);
		
		try{
				ContentValues values = new ContentValues();
				values.put(Constant.colstatus,status);

				if(database == null || !database.isOpen()){
					database = helper.getWritableDatabase();
				}
				
			updateRowCount = database.update(Constant.tblQmsScheduleDetails,values, Constant.colUniqueCodeScheduleDetails + " = '"+uniqueCode+"'",
						null);
			
			Log.d(Constant.tag, "Update Complete");
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
	}//finalize ends here
	
	
	@Override
	public ScheduleDetailsDTO getScheduleDetails(String uniqueCode, Context context) {
	
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select * from  "
				+ Constant.tblQmsScheduleDetails + " where " + Constant.colUniqueCodeScheduleDetails + " = '" + uniqueCode + "'";

		Cursor cursor = null;
		ScheduleDetailsDTO ScheduleDetailsDTOObj = null;
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					ScheduleDetailsDTOObj = new ScheduleDetailsDTO();
					ScheduleDetailsDTOObj.setUniqueCode(cursor.getString(0));
					ScheduleDetailsDTOObj.setScheduleMonth(cursor.getInt(1));
					ScheduleDetailsDTOObj.setScheduleYear(cursor.getInt(2));
					ScheduleDetailsDTOObj.setStateName(cursor.getString(3));
					ScheduleDetailsDTOObj.setDistrictName(cursor.getString(4));
					ScheduleDetailsDTOObj.setBlockName(cursor.getString(5));
					ScheduleDetailsDTOObj.setPackageId(cursor.getString(6));
					ScheduleDetailsDTOObj.setSanctionYear(cursor.getString(7));
					ScheduleDetailsDTOObj.setRoadName(cursor.getString(8));
					ScheduleDetailsDTOObj.setRoadLength(cursor.getFloat(9));
					ScheduleDetailsDTOObj.setRoadStatus(cursor.getString(10));
					ScheduleDetailsDTOObj.setCompletionDate(cursor.getString(11));
					ScheduleDetailsDTOObj.setIsRecordUploaded(Integer.parseInt(cursor.getString(12)));
					ScheduleDetailsDTOObj.setIsGeneratedUniqueId(Integer.parseInt(cursor.getString(13)));
					ScheduleDetailsDTOObj.setIsEnquiry(Integer.parseInt(cursor.getString(14)));
					ScheduleDetailsDTOObj.setIsUnplanFinalize(Integer.parseInt(cursor.getString(15)));
					
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		finally{
			if(cursor != null){
				cursor.close();
			}
			if(helper != null)
			helper.close();
			if(database != null || database.isOpen())
			database.close();
		}
		return ScheduleDetailsDTOObj;
		
	}
	
	@Override
	public ArrayList<ScheduleDetailsDTO> getUploadedScheduleDetails(Context context, Activity parent, Integer entryMode) {

		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select * from  " + Constant.tblQmsScheduleDetails + " where "+Constant.colIsGeneratedUniqueId +" = "+ entryMode + " and "+ Constant.colstatus +" = 'U'";

		Cursor cursor = null;
		ArrayList<ScheduleDetailsDTO> scheduleDetailsDTOArrayList = new ArrayList<ScheduleDetailsDTO>();
		ScheduleDetailsDTO ScheduleDetailsDTOObj = null;
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					ScheduleDetailsDTOObj = new ScheduleDetailsDTO();
					ScheduleDetailsDTOObj.setUniqueCode(cursor.getString(0));
					ScheduleDetailsDTOObj.setScheduleMonth(cursor.getInt(1));
					ScheduleDetailsDTOObj.setScheduleYear(cursor.getInt(2));
					ScheduleDetailsDTOObj.setStateName(cursor.getString(3));
					ScheduleDetailsDTOObj.setDistrictName(cursor.getString(4));
					ScheduleDetailsDTOObj.setBlockName(cursor.getString(5));
					ScheduleDetailsDTOObj.setPackageId(cursor.getString(6));
					ScheduleDetailsDTOObj.setSanctionYear(cursor.getString(7));
					ScheduleDetailsDTOObj.setRoadName(cursor.getString(8));
					ScheduleDetailsDTOObj.setRoadLength(cursor.getFloat(9));
					ScheduleDetailsDTOObj.setRoadStatus(cursor.getString(10));
					ScheduleDetailsDTOObj.setCompletionDate(cursor.getString(11));
					ScheduleDetailsDTOObj.setIsRecordUploaded(Integer.parseInt(cursor.getString(12)));
					ScheduleDetailsDTOObj.setIsGeneratedUniqueId(Integer.parseInt(cursor.getString(13)));
					ScheduleDetailsDTOObj.setIsEnquiry(Integer.parseInt(cursor.getString(14)));
					ScheduleDetailsDTOObj.setIsUnplanFinalize(Integer.parseInt(cursor.getString(15)));
					
					scheduleDetailsDTOArrayList.add(ScheduleDetailsDTOObj);
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		}
		finally{
			if(cursor != null){
				cursor.close();
			}
			if(helper != null)
			helper.close();
			if(database != null || database.isOpen())
			database.close();
		}
		return scheduleDetailsDTOArrayList;

	}
	
}

