package db.DAO;

import java.util.ArrayList;

import common.Constant;
import common.ExceptionHandler;
import common.QMSHelper.NotificationEnum;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import db.MySqliteOpenHelper;
import db.DTO.ImageDetailsDTO;
import db.interfaces.IImageDetails;

public class ImageDetailsDAO implements IImageDetails{

	private MySqliteOpenHelper helper;
	private SQLiteDatabase database;

	@Override
	public Boolean setImageDetails(ImageDetailsDTO imageDetailsDTO, Context context, Activity parent) {

		helper = new MySqliteOpenHelper(context);
		if(database == null || !database.isOpen()){
			database = helper.getWritableDatabase();
			 }
		long insertRowCount = 0;

		try{
			 
			ContentValues values = new ContentValues();
			values.put(Constant.colUniqueCodeImageDetails,imageDetailsDTO.getUniqueCode());
			values.put(Constant.colFileId,imageDetailsDTO.getFileId());
			values.put(Constant.colObservationIdImageDetails,imageDetailsDTO.getObservationId());
			values.put(Constant.colFileName,imageDetailsDTO.getFileName());
			values.put(Constant.colDescriptionImageDetails,imageDetailsDTO.getDescription());
			values.put(Constant.colLatitude,imageDetailsDTO.getLatitude());
			values.put(Constant.colLongitude,imageDetailsDTO.getLongitude());
			values.put(Constant.colCaptureDateTime,imageDetailsDTO.getCaptureDateTime());
			values.put(Constant.colIsUploadedImageDetails,imageDetailsDTO.getIsUploaded());
			values.put(Constant.colIsSelected,imageDetailsDTO.getIsSelected());
			
			insertRowCount  = database.insert(Constant.tblQmsImageDetails, null, values);
			
		

		Log.d(Constant.tag, "Insert Complete");
		return insertRowCount > 0 ? true : false;
		}catch(Exception e){
		// throws to custom error class	
		new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		return false;	
		}
		finally{
		if(helper != null)
		helper.close();
		if(database != null || database.isOpen())
		database.close();
		}
	
	}

	@Override
	public ArrayList<ImageDetailsDTO> getImageDetails(String uniqueCode, Context context,Activity parent) {
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select * from  "
				+ Constant.tblQmsImageDetails + " where  " + Constant.colUniqueCodeImageDetails + " =  '" + uniqueCode.trim() + "'";

		Cursor cursor = null;
		
		ArrayList<ImageDetailsDTO> imageDetailsArrayList = new ArrayList<ImageDetailsDTO>();
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					ImageDetailsDTO imageDetailsDTO = new ImageDetailsDTO();
					
					imageDetailsDTO.setUniqueCode(cursor.getString(0));
					imageDetailsDTO.setFileId(cursor.getInt(1));
					imageDetailsDTO.setObservationId(cursor.getInt(2));
					imageDetailsDTO.setFileName(cursor.getString(3));
					imageDetailsDTO.setDescription(cursor.getString(4));
					imageDetailsDTO.setLatitude(cursor.getString(5));
					imageDetailsDTO.setLongitude(cursor.getString(6));
					imageDetailsDTO.setCaptureDateTime(cursor.getString(7));
					imageDetailsDTO.setIsUploaded(cursor.getInt(8));
					imageDetailsDTO.setIsSelected(cursor.getInt(9));
					
					imageDetailsArrayList.add(imageDetailsDTO);
					
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}else{
				
			}
		} catch (Exception e) {
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
			 
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
		
		return imageDetailsArrayList;
	}

	@Override
	public Integer getImageDetailsCount(String uniqueCode, Context context,Activity parent) {

		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select max( " + Constant.colFileId +") from  "
				+ Constant.tblQmsImageDetails + " where  " + Constant.colUniqueCodeImageDetails + " =  '" + uniqueCode.trim() + "'";

		Cursor cursor = null;
		Integer noOfImage = 0;
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					noOfImage =	cursor.getInt(0);
					
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}else{
				noOfImage = 0;
			}
		} catch (Exception e) {
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
			noOfImage = -1; 
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
		
		return noOfImage;
	}

	
	public Integer getImageCountForGeneratedId(Context context,Activity parent) {

		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String generatedUniqueId = "0";
		String sqlGeneratedUniqueId = "select  " + Constant.colUniqueCodeScheduleDetails + " from " + Constant.tblQmsScheduleDetails + 
									   " where " + Constant.colIsUnplanFinalize + " = 0";

		Cursor cursor = null;
		Integer noOfImage = 0;
		
		try {
				cursor = database.rawQuery(sqlGeneratedUniqueId, null);
	
				if (!cursor.isAfterLast()) 
				{
					cursor.moveToFirst();
					do {
						generatedUniqueId =	cursor.getString(0);
						cursor.moveToNext();
					} while (!cursor.isAfterLast());
				}
				else
				{
					return -1;
				}
				
				String sqlMaxImagesTaken = "select max( " + Constant.colFileId +") from  "
						 + Constant.tblQmsImageDetails + " where  " + Constant.colUniqueCodeImageDetails + " =  '" + generatedUniqueId + "'";
				
				cursor = database.rawQuery(sqlMaxImagesTaken, null);
	
				if (!cursor.isAfterLast()) {
					cursor.moveToFirst();
					do {
						noOfImage =	cursor.getInt(0);
						
						cursor.moveToNext();
					} while (!cursor.isAfterLast());
				}else{
					noOfImage = 0;
				}
			
		} catch (Exception e) {
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
			noOfImage = -1; 
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
		
		return noOfImage;
	}
	
	@Override
	public Boolean updateImageDetails(ImageDetailsDTO imageDetailsDTO, Context context, Activity parent) {

		helper = new MySqliteOpenHelper(context);
		if(database == null || !database.isOpen()){
			database = helper.getWritableDatabase();
			 }
		long updateRowCount = 0;

		try{
			 
			ContentValues values = new ContentValues();
			values.put(Constant.colUniqueCodeImageDetails,imageDetailsDTO.getUniqueCode());
			values.put(Constant.colFileId,imageDetailsDTO.getFileId());
			values.put(Constant.colObservationIdImageDetails,imageDetailsDTO.getObservationId());
			values.put(Constant.colFileName,imageDetailsDTO.getFileName());
			values.put(Constant.colDescriptionImageDetails,imageDetailsDTO.getDescription());
			values.put(Constant.colLatitude,imageDetailsDTO.getLatitude());
			values.put(Constant.colLongitude,imageDetailsDTO.getLongitude());
			values.put(Constant.colCaptureDateTime,imageDetailsDTO.getCaptureDateTime());
			values.put(Constant.colIsUploadedImageDetails,imageDetailsDTO.getIsUploaded());
			values.put(Constant.colIsSelected,imageDetailsDTO.getIsSelected());
			
			
			
			updateRowCount = database.update(Constant.tblQmsImageDetails,
					values,
					Constant.colFileId + " = " + imageDetailsDTO.getFileId() + " and "+ Constant.colUniqueCodeImageDetails + " = '" +imageDetailsDTO.getUniqueCode()+"'",
					null);

		Log.d(Constant.tag, "Insert Complete");
		return updateRowCount > 0 ? true : false;
		}catch(Exception e){
		// throws to custom error class	
		new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		return false;	
		}
		finally{
		if(helper != null)
		helper.close();
		if(database != null || database.isOpen())
		database.close();
		}
	
	}
	
	@Override
	public ArrayList<ImageDetailsDTO> getUploadImageDetailsList(Context context) {
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		
		String sql = "select imgd.UniqueCode, imgd.FileId, obd.ObservationId, imgd.FileName, imgd.Description, imgd.Latitude, imgd.Longitude, imgd.CaptureDateTime,  imgd.isUploaded, imgd.isSelected from qms_image_details  imgd , qms_observation_details   obd where imgd.UniqueCode = obd.UniqueCode  and obd.isUploaded = 1  and imgd.isSelected = 1 and imgd.isUploaded = 0 ";
		
	//	String sql = "select * from  "	+ Constant.tblQmsImageDetails + " where  " + Constant.colUniqueCodeImageDetails + " in  ( " + uniqueCode.trim() + " )";

		Cursor cursor = null;
		
		ArrayList<ImageDetailsDTO> imageDetailsArrayList = new ArrayList<ImageDetailsDTO>();
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					ImageDetailsDTO imageDetailsDTO = new ImageDetailsDTO();
					
					imageDetailsDTO.setUniqueCode(cursor.getString(0));
					imageDetailsDTO.setFileId(cursor.getInt(1));
					imageDetailsDTO.setObservationId(cursor.getInt(2));
					imageDetailsDTO.setFileName(cursor.getString(3));
					imageDetailsDTO.setDescription(cursor.getString(4));
					imageDetailsDTO.setLatitude(cursor.getString(5));
					imageDetailsDTO.setLongitude(cursor.getString(6));
					imageDetailsDTO.setCaptureDateTime(cursor.getString(7));
					imageDetailsDTO.setIsUploaded(cursor.getInt(8));
					imageDetailsDTO.setIsSelected(cursor.getInt(9));
					
					
					imageDetailsArrayList.add(imageDetailsDTO);
					
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}else{
				
			}
		} catch (Exception e) {
			//new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
			 
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
		
		return imageDetailsArrayList;
	}
	
	@Override
	public Boolean emptyImageDetails(Context context){
		Integer result = 0;
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		result = database.delete(Constant.tblQmsImageDetails, null, null);
		
		return result > 0 ? true : false;
		
	}
	
	@Override
	public Boolean updateImageDetailsGenratedId(String GenratedId,ImageDetailsDTO imageDetailsDTO, Context context, Activity parent) {

		helper = new MySqliteOpenHelper(context);
		if(database == null || !database.isOpen()){
			database = helper.getWritableDatabase();
			 }
		long updateRowCount = 0;

		try{
			 
			ContentValues values = new ContentValues();
			values.put(Constant.colUniqueCodeImageDetails,imageDetailsDTO.getUniqueCode());
			values.put(Constant.colFileId,imageDetailsDTO.getFileId());
			values.put(Constant.colObservationIdImageDetails,imageDetailsDTO.getObservationId());
			values.put(Constant.colFileName,imageDetailsDTO.getFileName());
			values.put(Constant.colDescriptionImageDetails,imageDetailsDTO.getDescription());
			values.put(Constant.colLatitude,imageDetailsDTO.getLatitude());
			values.put(Constant.colLongitude,imageDetailsDTO.getLongitude());
			values.put(Constant.colCaptureDateTime,imageDetailsDTO.getCaptureDateTime());
			values.put(Constant.colIsUploadedImageDetails,imageDetailsDTO.getIsUploaded());
			values.put(Constant.colIsSelected,imageDetailsDTO.getIsSelected());
			
			
			
			updateRowCount = database.update(Constant.tblQmsImageDetails,
					values,
					Constant.colUniqueCodeImageDetails + " = '" + GenratedId + "' and "+ Constant.colFileId +" = "+imageDetailsDTO.getFileId(),
					null);

		Log.d(Constant.tag, "Insert Complete");
		return updateRowCount > 0 ? true : false;
		}catch(Exception e){
		// throws to custom error class	
		new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		return false;	
		}
		finally{
		if(helper != null)
		helper.close();
		if(database != null || database.isOpen())
		database.close();
		}
	
	}
	
	
}
