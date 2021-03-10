package db.DAO;

import java.util.ArrayList;

import common.Constant;
import common.ExceptionHandler;
import common.QMSHelper.NotificationEnum;

import db.MySqliteOpenHelper;
import db.DTO.QmsNotificationDTO;
import db.DTO.ScheduleDetailsDTO;
import db.interfaces.IQmsNotification;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class QmsNotificationDAO implements IQmsNotification{


	private MySqliteOpenHelper helper;
	private SQLiteDatabase database;

	@Override
	public ArrayList<QmsNotificationDTO> getQmsNotificationDetails(Context context,
			Activity parent) {

		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select * from  " + Constant.tblQmsNotificationDetail + " order by "+ Constant.colmessageId +" desc ";

		Cursor cursor = null;
		ArrayList<QmsNotificationDTO> qmsNotificationDTOArrayList = new ArrayList<QmsNotificationDTO>();
		QmsNotificationDTO qmsNotificationDTODTOObj = null;
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					qmsNotificationDTODTOObj = new QmsNotificationDTO();
					qmsNotificationDTODTOObj.setUserId(cursor.getInt(0));
					qmsNotificationDTODTOObj.setMessageId(cursor.getInt(1));
					qmsNotificationDTODTOObj.setMessageText(cursor.getString(2));
					qmsNotificationDTODTOObj.setMessageType(cursor.getString(3));
					qmsNotificationDTODTOObj.setIsDownload(cursor.getInt(4));
					qmsNotificationDTODTOObj.setTimeStamp(cursor.getString(5));
					
					
					qmsNotificationDTOArrayList.add(qmsNotificationDTODTOObj);
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
		return qmsNotificationDTOArrayList;

	}
	
	
	@Override
	public Boolean setQmsNotificationDetails(ArrayList<QmsNotificationDTO> QmsNotificationDetailsArrayList,Context context) {

		MySqliteOpenHelper localHelper = new MySqliteOpenHelper(context);
	    SQLiteDatabase	localDatabase = localHelper.getWritableDatabase();
			
		 long insertCount = 0;
		 
		 try {
		 for (QmsNotificationDTO qmsNotificationDTO : QmsNotificationDetailsArrayList) {
			
			// String sql  = "insert into "+Constant.tblQmsNotificationDetail + " values ("+qmsNotificationDTO.getUserId()+", " + qmsNotificationDTO.getMessageId() +" , '"+ qmsNotificationDTO.getMessageText() +"' , '"+ qmsNotificationDTO.getMessageType() + "' , "+ qmsNotificationDTO.getIsDownload() +" , '"+ qmsNotificationDTO.getTimeStamp() +"'  )";
	 	//	 database.execSQL(sql);
			 
			    ContentValues values = new ContentValues();
				values.put(Constant.colUserId,qmsNotificationDTO.getUserId());
				values.put(Constant.colmessageId,qmsNotificationDTO.getMessageId());
				values.put(Constant.colmessageText,qmsNotificationDTO.getMessageText());
				values.put(Constant.colmessageType,qmsNotificationDTO.getMessageType());
				values.put(Constant.colisDownload,qmsNotificationDTO.getIsDownload());
				values.put(Constant.coltimeStamp,qmsNotificationDTO.getTimeStamp());
		 	 
				insertCount  = localDatabase.insert(Constant.tblQmsNotificationDetail, null, values);
				
			
		}
		 }
		 catch (Exception e) {
				e.printStackTrace();
				
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
	public Boolean emptyNotificationDetails(Context context){
		Integer result = 0;
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		result = database.delete(Constant.tblQmsNotificationDetail, null, null);
		
		return result > 0 ? true : false;
		
	}
}
