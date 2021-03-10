package common;

import java.util.Calendar;
import java.util.Date;

public class Constant {

	//wifi password : Egov@54321
	
	//1 QMS_CONFIG_DETAILS Table Columns 
	public static String tblQmsConfigDetails = "QMS_CONFIG_DETAILS";
	public static String colConfigId = "ConfigId";
	public static String colConfigParam = "ConfigParam";
	public static String colConfigValue = "ConfigValue";
	
	//2 QMS_LOGIN_DETAILS Table Columns
	public static String tblQmsLoginDetails = "QMS_LOGIN_DETAILS";
	public static String colUserId = "UserId";
	public static String colUserName = "UserName";
	public static String colPassword = "Password";
	public static String colQmCode = "QmCode";
	public static String colQmType = "QmType";
	public static String colPasswordWithoutHash = "PasswordWithoutHash"; 
	public static String colIsRemember = "IsRemember";
	public static String colMonitorName = "MonitorName";
	
	//3 QMS_SCHEDULE_DETAILS Table Columns
	public static String tblQmsScheduleDetails = "QMS_SCHEDULE_DETAILS";
	public static String colUniqueCodeScheduleDetails = "UniqueCode"; //0
	public static String colScheduleMonth = "ScheduleMonth"; //1
	public static String colScheduleYear = "ScheduleYear"; //2
	public static String colStateName = "StateName"; //3
	public static String colDistrictName = "DistrictName";//4
	public static String colBlockName = "BlockName"; //5
	public static String colPackageId = "PackageId";//6
	public static String colSanctionYear = "SanctionYear"; //7
	public static String colRoadName = "RoadName";//8
	public static String colRoadLength = "RoadLength"; //9
	public static String colRoadStatus = "RoadStatus"; // (C/P/M/L) //10
	public static String colCompletionDate = "CompletionDate"; //11
	public static String colIsRecordUploaded = "IsRecordUploaded"; //12
	public static String colIsGeneratedUniqueId = "IsGeneratedUniqueId"; // 13
	public static String colIsEnquiry = "IsEnquiry"; // 14
	public static String colIsUnplanFinalize = "IsUnplanFinalize"; // 15
	public static String colstatus = "status"; // 16
	public static String colScheduleDownloadDate = "ScheduleDownloadDate"; //17
	
	
	//4 QMS_OBSERVATION_DETAILS Table Columns
	public static String tblQmsObservationDetails = "QMS_OBSERVATION_DETAILS";
	public static String colUniqueCodeObservationDetails = "UniqueCode"; //0
	public static String colInspectionDate = "InspectionDate"; //1
	public static String colFromChainage = "FromChainage"; //2
	public static String colToChainage = "ToChainage"; //3
	public static String colGradingParams = "GradingParams"; //4
	public static String colCommentParams = "CommentParams"; //5
	public static String colStartLatitude = "StartLatitude"; //6
	public static String colStartLongitude = "StartLongitude"; //7
	public static String colEndLatitude = "EndLatitude"; //8
	public static String colEndLongitude = "EndLongitude"; //9
	public static String colObservationIdObservationDetails = "ObservationId"; //10
	public static String colIsUploadedObservationDetails = "IsUploaded"; //11
	
	//5 QMS_IMAGE_DETAILS Table Columns
	public static String tblQmsImageDetails = "QMS_IMAGE_DETAILS";
	public static String colUniqueCodeImageDetails = "UniqueCode";
	public static String colFileId = "FileId";
	public static String colObservationIdImageDetails = "ObservationId";
	public static String colFileName = "FileName";
	public static String colDescriptionImageDetails = "Description";
	public static String colLatitude = "Latitude";
	public static String colLongitude = "Longitude";
	public static String colCaptureDateTime = "CaptureDateTime";
	public static String colIsUploadedImageDetails = "IsUploaded";
	public static String colIsSelected = "IsSelected";
	
	// 6 QMS_IMAGE_DESCRIPTION Table Columns
	public static String tblQmsImageDescription = "QMS_IMAGE_DESCRIPTION";
	public static String colDescriptionId = "DescriptionId";
	public static String colDescription = "Description";
	
	// 7 QMS_NOTIFICATION_DETAILS Table Columns
		public static String tblQmsNotificationDetail = "QMS_NOTIFICATION_DETAILS";
		public static String colUserIdOmsNotificationDetail = "userId";
		public static String colmessageId = "messageId";
		public static String colmessageText = "messageText";
		public static String colmessageType = "messageType";
		public static String colisDownload = "isDownload";
		public static String coltimeStamp = "timeStamp";
		
	
	/* ----------------------------------Web Services Constant ----------------------------------------------- */
	 public static Integer updateTimer = 1; 
	 public static Integer isSSL = 1; 
	 public static String NAMESPACE = "http://woms.org/";
	 
	
	 
	 // Live Apk With IP Address
	// public static String fixedURL = "https://164.100.78.29/MABQMS/MABQMSConfigParams.asmx";
	  public static String apkURL = "http://mmgsy.wamis-egov.in/MABQMS/MobileApp/woms.apk";
	 //public static String URL = "https://164.100.78.29/MABQMS/MABQMS.asmx";
	 
	  //Live URL 10/05/2018  ........ACTUAL
	  //public static String URL = "http://mmgsy.wamis-egov.in/MABQMS/MABQMS.asmx";
	  //public static String fixedURL = "http://mmgsy.wamis-egov.in/MABQMS/MABQMSConfigParams.asmx";

	  
	  //Local URL 10/05/2018  .....ACTUAL
	  //public static String URL = "http://10.208.23.180:1000/MABQMS/MABQMS.asmx";
	  //public static String fixedURL = "http://10.208.23.180:1000/MABQMS/MABQMSConfigParams.asmx";
	  
	 public static String URL = "http://egovmsdb:86/MABQMS/MABQMS.asmx";
	 public static String fixedURL = "http://egovmsdb:86/MABQMS/MABQMSConfigParams.asmx";
	  
	   
	 
	 // training server : 10.208.36.202
	 public static String webServiceGetConfigDetails = "GetConfigDetails";
	 public static String webServiceLogin = "Login";
	 public static String webServiceInsertLog = "InsertLog";
	 public static String webServiceDownloadSchedule = "DownloadSchedule";
	 public static String webServiceGetImageDescription = "GetImageDescription";
	 public static String webServiceDownloadUnplannedSchedule = "DownloadUnplannedSchedule";
	 public static String webInsertObservationDetails = "InsertObservationDetails";
	 public static String webServiceUploadAndInsertImageDetailsWithDate = "UploadAndInsertImageDetailsWithDate";
	 public static String webServiceAssignUnplannedSchedule = "AssignUnplannedSchedule";
	 public static String webServiceDownloadNotifications = "DownloadNotifications";
	 
	 
	 
		/* ----------------------------------Log Tag Constant ----------------------------------------------- */ 
	 
	 public static String tag = "QMS";
	 public static String helpLineNo = "02025503201";
	 
	 /* ----------------------------------Database Constant ----------------------------------------------- */
	 
	 public static String DB_NAME = "nqms.sqlite";
	 public static int DB_VERSION = 1;
	 
	 /* ----------------------------------Security Constant ----------------------------------------------- */
	 public static String fixKey = "^%V{T%&]@08_01-";
	 
	 /* ----------------------------------longitude & latitude  Constant ----------------------------------------------- */
	
	 public static String longitude = null; // = "73.454554545456";
	 public static String latitude = null;  // =   "18.787280545787";
	 
	 public static Date GPSDateTime = null;  // =   "18.787280545787";
	 
	
	 

	 
	 //public static String longitude =  "73.454554545456";
	 //public static String latitude =    "18.787280545787";
	 
	 public static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in
	 public static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in
	 
	 /* ----------------------------------Splash Constant ----------------------------------------------- */
	 public static long SLEEP_TIME = 1;    // Sleep for some time
	 
	 /* ----------------------------------Take Photo Constant ----------------------------------------------- */
	 public static int maxImage = 20;
	 public static int minImage = 10;
	 public static String qmsPath = "QMS";
	 public static String stdImagePath = "Planned";
	 public static String unplannedImagePath = "Unplanned";
	 public static String thumbnailPath = "thumbnail";
	 public static String lastImageDescriptionDate = "0";
	 public static String crashReportURL = "";
	 public static String crashReportEmail = "ommashelp@gmail.com";
	 public static String lastUpdateDate = "";
	 public static String trainingBatch = "";
	 public static String messageNotinUse = "";
	 
	 public static int numberOfDays = 35;
	 
	 
	 
	 
	 public static String loginMode = "online";
	
}
