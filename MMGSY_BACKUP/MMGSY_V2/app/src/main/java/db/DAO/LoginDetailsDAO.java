package db.DAO;

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
import db.DTO.ConfigDetailsDTO;
import db.DTO.LoginDetailsDTO;
import db.interfaces.ILoginDetails;

public class LoginDetailsDAO implements ILoginDetails {

	private MySqliteOpenHelper helper;
	private SQLiteDatabase database;

	@Override
	public Boolean setLoginDetails(LoginDetailsDTO loginDetailsObj,	Boolean isUpdate, Context context, Activity parent) {

	 	helper = new MySqliteOpenHelper(context);
		if(database == null || !database.isOpen()){
			database = helper.getWritableDatabase();
			 }
		long updateRowCount = 0;

		try{
		if (isUpdate) {
			// second login onwards 
			ContentValues values = new ContentValues();
			values.put(Constant.colUserId,loginDetailsObj.getUserId());
			values.put(Constant.colUserName,loginDetailsObj.getUserName());
			values.put(Constant.colPassword, loginDetailsObj.getPassword());
			values.put(Constant.colQmCode, loginDetailsObj.getQmCode());
			values.put(Constant.colQmType, loginDetailsObj.getQmType());
			values.put(Constant.colMonitorName,loginDetailsObj.getMonitorName());
			values.put(Constant.colPasswordWithoutHash,loginDetailsObj.getPasswordWithoutHash());
			values.put(Constant.colIsRemember,loginDetailsObj.getIsRemember());

			updateRowCount = database.update(Constant.tblQmsLoginDetails,
					values,
					Constant.colUserId + " = " + loginDetailsObj.getUserId(),
					null);
		

		} else {
			// first login  only
			ContentValues values = new ContentValues();
			values.put(Constant.colUserId,loginDetailsObj.getUserId());
			values.put(Constant.colUserName,loginDetailsObj.getUserName());
			values.put(Constant.colPassword,loginDetailsObj.getPassword());
			values.put(Constant.colQmCode,loginDetailsObj.getQmCode());
			values.put(Constant.colQmType,loginDetailsObj.getQmType());
			values.put(Constant.colPasswordWithoutHash,loginDetailsObj.getPasswordWithoutHash());
			values.put(Constant.colIsRemember,loginDetailsObj.getIsRemember());
			values.put(Constant.colMonitorName,loginDetailsObj.getMonitorName());
			
			updateRowCount  = database.insert(Constant.tblQmsLoginDetails, null, values);
			
		}

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
	public LoginDetailsDTO getLoginDetails(String userName, Context context, Activity parent) {

		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select * from  "
				+ Constant.tblQmsLoginDetails;
		if(userName != null && !userName.equals(""))
		{
			sql += " where "+ Constant.colUserName +" = '" + userName+"'";
		}

		Cursor cursor = null;
		LoginDetailsDTO loginDetailsDTOObj = new LoginDetailsDTO();
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					loginDetailsDTOObj.setUserId(cursor.getInt(0));
					loginDetailsDTOObj.setUserName(cursor.getString(1));
					loginDetailsDTOObj.setPassword(cursor.getString(2));
					loginDetailsDTOObj.setQmCode(cursor.getInt(3));
					loginDetailsDTOObj.setQmType(cursor.getString(4));
					loginDetailsDTOObj.setPasswordWithoutHash(cursor.getString(5));
					loginDetailsDTOObj.setIsRemember(cursor.getInt(6));
					loginDetailsDTOObj.setMonitorName(cursor.getString(7));
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}else{
				loginDetailsDTOObj = null;
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
		return loginDetailsDTOObj;
	}
	
	@Override
	public LoginDetailsDTO getLoginDetails(String userName, Context context) {

		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select * from  "
				+ Constant.tblQmsLoginDetails;
		if(userName != null && !userName.equals(""))
		{
			sql += " where "+ Constant.colUserName +" = '" + userName+"'";
		}

		Cursor cursor = null;
		LoginDetailsDTO loginDetailsDTOObj = new LoginDetailsDTO();
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					loginDetailsDTOObj.setUserId(cursor.getInt(0));
					loginDetailsDTOObj.setUserName(cursor.getString(1));
					loginDetailsDTOObj.setPassword(cursor.getString(2));
					loginDetailsDTOObj.setQmCode(cursor.getInt(3));
					loginDetailsDTOObj.setQmType(cursor.getString(4));
					loginDetailsDTOObj.setPasswordWithoutHash(cursor.getString(5));
					loginDetailsDTOObj.setIsRemember(cursor.getInt(6));
					loginDetailsDTOObj.setMonitorName(cursor.getString(7));
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}else{
				loginDetailsDTOObj = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(Constant.tag, e.toString());
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
		return loginDetailsDTOObj;
	}

	@Override
	public Integer getLoginDetailsRecordsCount(Context context, Activity parent) {
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select count(*) Record from  "
				+ Constant.tblQmsLoginDetails;

		Cursor cursor = null;
		Integer login = 0;
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					login = cursor.getInt(0);
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
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
		return login;
	}

}
