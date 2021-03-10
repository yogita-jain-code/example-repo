package db.DAO;

import java.util.ArrayList;

import common.Constant;
import common.ExceptionHandler;
import common.QMSHelper.NotificationEnum;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import db.MySqliteOpenHelper;
import db.DTO.ConfigDetailsDTO;
import db.DTO.LoginDetailsDTO;
import db.interfaces.IConfigDetails;

public class ConfigDetailsDAO implements IConfigDetails{

	private MySqliteOpenHelper helper;
	private SQLiteDatabase database;

	@Override
	public Boolean updateConfigDetails(ArrayList<ConfigDetailsDTO> configDetailsObj, Context context) {

		 helper = new MySqliteOpenHelper(context);
		 if(database == null || !database.isOpen()){
				database = helper.getWritableDatabase();
				 }
		 try{
		 database.execSQL("delete from "+ Constant.tblQmsConfigDetails); 
		for (ConfigDetailsDTO configDetailsDTO : configDetailsObj) {
			 String sql  = "insert into "+Constant.tblQmsConfigDetails + " values ("+configDetailsDTO.getConfigId()+",'" + configDetailsDTO.getConfigParam() +"', '"+ configDetailsDTO.getConfigValue() +"')";
	 		 database.execSQL(sql);
		}
	}
		 catch (Exception e) {
				e.printStackTrace();
				Log.e(Constant.tag, e.toString());
				//new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
			}
			finally{
				
				if(helper != null)
				helper.close();
				if(database != null || database.isOpen())
				database.close();
			}
		Log.d(Constant.tag, "Insert Complete");
		return null;
	}

	@Override
	public ConfigDetailsDTO getConfigDetails(Integer configId, Context context, Activity parent) {
		helper = new MySqliteOpenHelper(context);
		if(database == null || !database.isOpen()){
			database = helper.getWritableDatabase();
			 }
		String sql = "select * from  "
				+ Constant.tblQmsConfigDetails +" where "+ Constant.colConfigId +" = "+ configId ;

		Cursor cursor = null;
		ConfigDetailsDTO configDetailsDTOObj = new ConfigDetailsDTO();
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					configDetailsDTOObj.setConfigId(cursor.getInt(0));
					configDetailsDTOObj.setConfigParam(cursor.getString(1));
					configDetailsDTOObj.setConfigValue(cursor.getString(2));
					cursor.moveToNext();
				} while (!cursor.isAfterLast());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(Constant.tag, e.toString());
			//new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
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
		return configDetailsDTOObj;
		
	}
	
	

}

