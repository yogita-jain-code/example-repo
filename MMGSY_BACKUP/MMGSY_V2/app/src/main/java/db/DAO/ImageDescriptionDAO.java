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
import db.DTO.ImageDescriptionDTO;
import db.DTO.LoginDetailsDTO;
import db.interfaces.IImageDescription;

public class ImageDescriptionDAO implements IImageDescription{

	private MySqliteOpenHelper helper;
	private SQLiteDatabase database;
	
	@Override
	public Boolean updateImageDescription(ArrayList<ImageDescriptionDTO> ImageDescriptionObj, Context context) {


		 helper = new MySqliteOpenHelper(context);
		 if(database == null || !database.isOpen()){
				database = helper.getWritableDatabase();
				 }
		 try{
		 database.execSQL("delete from "+ Constant.tblQmsImageDescription); 
		for (ImageDescriptionDTO imageDescriptionDTO : ImageDescriptionObj) {
			 String sql  = "insert into "+Constant.tblQmsImageDescription + " values ("+imageDescriptionDTO.getDescriptionId()+",'" + imageDescriptionDTO.getDescription() +"')";
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
	public ImageDescriptionDTO getImageDescription(Integer descriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getAllImageDescription(Context context, Activity parent) {
	
		helper = new MySqliteOpenHelper(context);
		database = helper.getWritableDatabase();
		String sql = "select "+ Constant.colDescription  +" from  "
				+ Constant.tblQmsImageDescription;
		

		Cursor cursor = null;
		ArrayList<String> imageDescriptionDTOArrayList = new ArrayList<String>();
		imageDescriptionDTOArrayList.add("Select Description");
		
		try {
			cursor = database.rawQuery(sql, null);

			if (!cursor.isAfterLast()) {
				cursor.moveToFirst();
				do {
					imageDescriptionDTOArrayList.add(cursor.getString(0));
					cursor.moveToNext();
				
				} while (!cursor.isAfterLast());
			}else{
				imageDescriptionDTOArrayList = null;
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
		return imageDescriptionDTOArrayList;
	
	}

} // ImageDescriptionDAO ends here 
