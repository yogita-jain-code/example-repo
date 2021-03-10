package db;

import common.Constant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

	public MySqliteOpenHelper(Context context) {
		super(context, Constant.DB_NAME, null, Constant.DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		 //1 QMS_CONFIG_DETAILS Table Create Script
		String sqlTblQmsConfigDetails = "CREATE TABLE "+Constant.tblQmsConfigDetails+" ( "
				    +Constant.colConfigId+" INTEGER PRIMARY KEY NOT NULL , "
				    +Constant.colConfigParam+" TEXT NOT NULL, "
				    +Constant.colConfigValue+" TEXT)";
		
		database.execSQL(sqlTblQmsConfigDetails);
		
		//2 QMS_LOGIN_DETAILS Table Create Script
		String sqlTblQmsLoginDetails = "CREATE TABLE "+ Constant.tblQmsLoginDetails +" ( "
				    +Constant.colUserId + " INTEGER PRIMARY KEY NOT NULL, "
				    +Constant.colUserName + " TEXT NOT NULL, "
				    +Constant.colPassword+ " TEXT NOT NULL, "
				    +Constant.colQmCode + " INTEGER NOT NULL, "
				    +Constant.colQmType + " TEXT NOT NULL, "
				    +Constant.colPasswordWithoutHash+ " TEXT, "
				    +Constant.colIsRemember+ " INTEGER NOT NULL DEFAULT (0), "
				    +Constant.colMonitorName+ " TEXT NOT NULL )";
		
		database.execSQL(sqlTblQmsLoginDetails);
		
		//3 QMS_SCHEDULE_DETAILS Table Create Script
		
		String sqlTblQmsScheduleDetails = "CREATE TABLE "+Constant.tblQmsScheduleDetails+" ("
		    +Constant.colUniqueCodeScheduleDetails+" TEXT NOT NULL, "
		    +Constant.colScheduleMonth+" INTEGER NOT NULL, "
		    +Constant.colScheduleYear+" INTEGER NOT NULL, "
		    +Constant.colStateName+" TEXT NOT NULL, "
		    +Constant.colDistrictName+" TEXT NOT NULL, "
		    +Constant.colBlockName+" TEXT NOT NULL, "
		    +Constant.colPackageId+" TEXT NOT NULL, "
		    +Constant.colSanctionYear+" TEXT NOT NULL, "
		    +Constant.colRoadName+" TEXT NOT NULL, "
		    +Constant.colRoadLength+" REAL NOT NULL, "
		    +Constant.colRoadStatus+" TEXT NOT NULL, "
		    +Constant.colCompletionDate+" TEXT NOT NULL, "
		    +Constant.colIsRecordUploaded+" INTEGER NOT NULL DEFAULT (0), "
		    +Constant.colIsGeneratedUniqueId+" INTEGER NOT NULL DEFAULT (0), "
		    +Constant.colIsEnquiry+" INTEGER NOT NULL DEFAULT (0), "
		    +Constant.colIsUnplanFinalize+" INTEGER NOT NULL DEFAULT (0), "
		    +Constant.colstatus+" TEXT NOT NULL, "
			+Constant.colScheduleDownloadDate+" TEXT NOT NULL )";	
		
		

		database.execSQL(sqlTblQmsScheduleDetails);
		
		//4 QMS_OBSERVATION_DETAILS  Create Script
		String sqlTblQmsObservationDetails = "CREATE TABLE "+Constant.tblQmsObservationDetails+" ( "
		    +Constant.colUniqueCodeObservationDetails+" TEXT NOT NULL, "
		    +Constant.colInspectionDate+" TEXT , "
		    +Constant.colFromChainage+" REAL , "
		    +Constant.colToChainage+" REAL , "
		    +Constant.colGradingParams+" TEXT , "
		    +Constant.colCommentParams+" TEXT, "
		    +Constant.colStartLatitude+" TEXT, "
		    +Constant.colStartLongitude+ " TEXT, "
		    +Constant.colEndLatitude+" TEXT, "
		    +Constant.colEndLongitude+" TEXT, "
		    +Constant.colObservationIdObservationDetails+" INTEGER, "
		    +Constant.colIsUploadedObservationDetails+" INTEGER NOT NULL DEFAULT (0) )";
		
		database.execSQL(sqlTblQmsObservationDetails);
		
		//5 QMS_IMAGE_DETAILS  Create Script
		String sqlTblQmsImageDetails = "CREATE TABLE "+ Constant.tblQmsImageDetails +" ( "
    +Constant.colUniqueCodeImageDetails+" TEXT NOT NULL, "
    +Constant.colFileId+" INTEGER NOT NULL, "
    +Constant.colObservationIdImageDetails+ " INTEGER, "
    +Constant.colFileName+ " TEXT NOT NULL, "
    +Constant.colDescriptionImageDetails+ " TEXT NOT NULL, "
    +Constant.colLatitude+ " TEXT NOT NULL, "
    +Constant.colLongitude+" TEXT NOT NULL, "
    +Constant.colCaptureDateTime+" TEXT NOT NULL, "
    +Constant.colIsUploadedImageDetails+" INTEGER NOT NULL DEFAULT (0), "
    +Constant.colIsSelected+" INTEGER NOT NULL DEFAULT (0) )";
		
		database.execSQL(sqlTblQmsImageDetails);
		
		// 6 QMS_IMAGE_DESCRIPTION Create Script
		String sqlTblQmsImageDescription = "CREATE TABLE "+Constant.tblQmsImageDescription+" ("
    +Constant.colDescriptionId+" INTEGER PRIMARY KEY NOT NULL, "
    +Constant.colDescription+" TEXT NOT NULL )"; 
		
		database.execSQL(sqlTblQmsImageDescription);
		
		// 7 QMS_NOTIFICATION_DETAILS Create Script
				String sqlTblQmsNotification = "CREATE TABLE "+ Constant.tblQmsNotificationDetail +" ("
     + Constant.colUserId + " INTEGER NOT NULL, "
     + Constant.colmessageId + " INTEGER PRIMARY KEY NOT NULL, "
     + Constant.colmessageText + " TEXT NOT NULL, "
     + Constant.colmessageType + " TEXT NOT NULL, "
     + Constant.colisDownload  + " INTEGER DEFAULT (0), "
     + Constant.coltimeStamp   + " TEXT )"; 
				
				database.execSQL(sqlTblQmsNotification);
		
		
	} // onCreate Method ends here 

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
