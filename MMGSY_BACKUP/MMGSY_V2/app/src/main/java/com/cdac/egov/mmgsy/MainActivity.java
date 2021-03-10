package com.cdac.egov.mmgsy;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;

import common.Constant;
import common.MyCrashApplication;
import common.Notification;
import common.QMSHelper;
import common.QMSHelper.NotificationEnum;
import common.RijndaelCrypt;
import db.DAO.ConfigDetailsDAO;
import db.DAO.LoginDetailsDAO;
import db.DTO.ConfigDetailsDTO;
import db.DTO.LoginDetailsDTO;
import db.MySqliteOpenHelper;
import webServices.LoginDetailsWebServices;

//import com.cdac.nqms.R;

public class MainActivity extends AppBaseActivity implements OnClickListener {


	RijndaelCrypt rc = null;
	String key = "^%V{T%&]@08_01-";
	//	String username = "nqmcdac";
//	String password = "Cdac@123";
//	String imei = "358549040572897";
//	String loginMode = "online";
	private static MainActivity parent;
	String username;
	String password;
	String imei;
	String loginMode;


	private MySqliteOpenHelper helper;
	private SQLiteDatabase database;
	private ProgressDialog dialog;
	private EditText userNameEditText;
	private EditText passwordEditText;
	private RadioGroup workingModeRadioGroup;
	private RadioButton workModeRadioButton;
	private View helpTextView;
	private CheckBox rememberId;
	private Boolean isRemember;
	private TextView versionNo;
	private TextView demoId;
	public LocationManager locationManager;
	private TextView linkUpdateAppIdTextView;
	private static final int REQUEST_LOCATION = 123;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = MainActivity.this;

		Boolean DEVELOPER_MODE = true;
		if (DEVELOPER_MODE) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads()
					.detectDiskWrites()
					.detectNetwork()   // or .detectAll() for all detectable problems
					.penaltyLog()
					.build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects()
					.penaltyLog()
					.penaltyDeath()
					.build());
		}


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerBaseActivityReceiver();

		MyCrashApplication app = new MyCrashApplication();
		app.onCreate();
		// Location Manager Code start
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
				ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
			//ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_PRIVILEGED_PHONE_STATE) != PackageManager.PERMISSION_GRANTED

		)
		{
			ActivityCompat.requestPermissions(this,
					new String[]{
							Manifest.permission.CAMERA,
							Manifest.permission.WRITE_EXTERNAL_STORAGE,
							Manifest.permission.ACCESS_FINE_LOCATION,
							Manifest.permission.ACCESS_COARSE_LOCATION,
							Manifest.permission.BLUETOOTH,
							Manifest.permission.BLUETOOTH_ADMIN,
							Manifest.permission.READ_EXTERNAL_STORAGE,
							Manifest.permission.READ_PHONE_STATE,
					}, REQUEST_LOCATION);

			//int permissionResult = this.checkCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE");
			//boolean isPermissionGranted = permissionResult == PackageManager.PERMISSION_GRANTED;



//			String[] permissions = {"android.permission.READ_PRIVILEGED_PHONE_STATE"};
//			int requestCode = 5;
//			ActivityCompat.requestPermissions(this,permissions, requestCode);
//
//			ActivityCompat.checkSelfPermission(this, "android.permission.READ_PRIVILEGED_PHONE_STATE");

		} else {

			System.out.println("Location permissions available, starting location");



		}


//		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//				Constant.MINIMUM_TIME_BETWEEN_UPDATES,
//				Constant.MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new MyLocationListener());
        
        // Location Manager Code ends here
        
        
        helper = new MySqliteOpenHelper(this);
	    database = helper.getWritableDatabase();
	 
	   userNameEditText = (EditText)findViewById(R.id.txtUnameId);
	   passwordEditText = (EditText)findViewById(R.id.txtPasswordId);
	   helpTextView     = findViewById(R.id.helpTextId);
	   linkUpdateAppIdTextView  = (TextView)findViewById(R.id.linkUpdateAppId);
	   helpTextView.setOnClickListener(this);
	   linkUpdateAppIdTextView.setOnClickListener(this);
	   
	   rememberId = (CheckBox)findViewById(R.id.chkRememberId);
	   versionNo = (TextView)findViewById(R.id.lblVersionNo);
	  
	   demoId = (TextView)findViewById(R.id.lblDemoId);
	   
	   workingModeRadioGroup = (RadioGroup) findViewById(R.id.workingModeRadioGroup);
	   workModeRadioButton = (RadioButton) findViewById(workingModeRadioGroup.getCheckedRadioButtonId());
		
	   TelephonyManager tm  = (TelephonyManager) getSystemService(TELEPHONY_SERVICE); 
	   imei	  = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
	   
	   
	   Button loginButton = (Button) findViewById(R.id.loginButtonId);
       loginButton.setOnClickListener(this);
      
		       
		//ConfigDetailsWebServices configDetailsWebServices  = new ConfigDetailsWebServices();
		//configDetailsWebServices.callConfigDetailsWebServices("nqmcdac", imei, this, parent);
		//LoginDetailsWebServices loginDetailsWebServices = new LoginDetailsWebServices();
		//loginDetailsWebServices.callLoginDetailsWebServices(1, username, imei, password, loginMode, this);
		//LoginDetailsDAO loginDetailsDAOObj = new LoginDetailsDAO();
		//LoginDetailsDTO loginDetailsDTOObj = loginDetailsDAOObj.getLoginDetails(username, this);		
	//   	DownloadScheduleWebServices downloadScheduleWebServices = new DownloadScheduleWebServices();
	//   	downloadScheduleWebServices.downloadSchedule(username, this);
   
       initQMS();
    }


    @Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }
    
    public class CheckLoginTask extends AsyncTask<Context,Integer,Integer> {

		

		@Override
		protected Integer doInBackground(Context ... params) {
			// TODO Auto-generated method stub
			  username = userNameEditText.getText().toString();
			  password = passwordEditText.getText().toString();
			  workModeRadioButton = (RadioButton) findViewById(workingModeRadioGroup.getCheckedRadioButtonId());
				 if(workModeRadioButton.getText().toString().equalsIgnoreCase("online")){
					 loginMode = "online";
				 }else{
					 loginMode = "offline";
				 }
				 
				 if(rememberId.isChecked()){
	    				isRemember = true;	
	    			}else{
	    				isRemember = false;
	    			}
				 
			LoginDetailsWebServices loginDetailsWebServices = new LoginDetailsWebServices();
		   	loginDetailsWebServices.callLoginDetailsWebServices(username, imei, password, loginMode, isRemember, MainActivity.this, dialog, parent);
			return 0;
		}
    	
    	
    } // CheckLoginTask ends here


	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.loginButtonId){
			
			 dialog = ProgressDialog.show(MainActivity.this, "", 
		                "Loading. Please wait...", true);
			 new CheckLoginTask().execute();
		}
		
		if(v.getId() == R.id.helpTextId){
			QMSHelper.callHelpLine(this);
		}
		
		if(v.getId() == R.id.linkUpdateAppId){
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are sure to update application ?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog1, int id) {
			        	   dialog = ProgressDialog.show(MainActivity.this, "", 
			                       "Downloading application. Please wait...", true);
			       		 
			       		 new DownloadAPKTask().execute();
			        	   //downloadapk();
			    			//installApk();
			    			
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			
			AlertDialog alert = builder.create();
			alert.show();
			
		}// code for get updated apk from server and install ends here
		
	}
	
	public void initQMS(){
		
	 
		 try{
		 	 ConfigDetailsDAO configDetailsDAOObj = new ConfigDetailsDAO();
		 	 
			//MABQMS_VERSION_NO
			 ConfigDetailsDTO configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(4, this, parent);
			 if(configDetailsDTOObj != null){
				 versionNo.setText("Version No 1.0.4");	
				/* if(configDetailsDTOObj.getConfigValue() != null){
				 //versionNo.setText("Version No "+configDetailsDTOObj.getConfigValue());
					 versionNo.setText("Version No 5.0.1");	
				 }else{
					 versionNo.setText("Version No 5.0.1");	 
				 }*/
			 }
			 
			 
			 LoginDetailsDAO loginDetailsDAOObj = new LoginDetailsDAO();
			 LoginDetailsDTO loginDetailsDTOObj =  loginDetailsDAOObj.getLoginDetails(null, this, parent);
			
			  
			 if(loginDetailsDTOObj.getQmType().equalsIgnoreCase("I")){
				 // NQM_MAX_IMAGE_CNT and NQM_MIN_IMAGE_CNT
				 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(5, this, parent);
				 if(configDetailsDTOObj != null){
					 Constant.maxImage = Integer.parseInt(configDetailsDTOObj.getConfigValue());
				 }
				 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(17, this, parent);
				 if(configDetailsDTOObj != null){
					 Constant.minImage = Integer.parseInt(configDetailsDTOObj.getConfigValue());
				 }
				 
				 
			 }else{
				 //SQM_MAX_IMAGE_CNT and SQM_MIN_IMAGE_CNT
				 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(6, this, parent);
				 if(configDetailsDTOObj != null){
					 Constant.maxImage = Integer.parseInt(configDetailsDTOObj.getConfigValue());
				 }
				 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(18, this, parent);
				 if(configDetailsDTOObj != null){
					 Constant.minImage = Integer.parseInt(configDetailsDTOObj.getConfigValue());
				 }
			 }
			 
			 if(loginDetailsDTOObj.getIsRemember() == 1){
				 userNameEditText.setText(loginDetailsDTOObj.getUserName());
				 passwordEditText.setText(loginDetailsDTOObj.getPasswordWithoutHash());
				 rememberId.setChecked(true);
			 } 
			 
			 // MABQMS_HELPLINE_NO
			 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(7, this, parent);
			 if(configDetailsDTOObj != null){
				Constant.helpLineNo = configDetailsDTOObj.getConfigValue();
			 }
			 
			 // MABQMS_CRASH_REPORT_URL
			 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(8, this, parent);
			 if(configDetailsDTOObj != null){
				Constant.crashReportURL = configDetailsDTOObj.getConfigValue();
			 }
			 
			 // MABQMS_CRASH_EMAIL
			 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(9, this, parent);
			 if(configDetailsDTOObj != null){
				Constant.crashReportEmail = configDetailsDTOObj.getConfigValue();
			 }
			 
			// MABQMS_LAST_UPDATED_DATE
			 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(10, this, parent);
			 if(configDetailsDTOObj != null){
				Constant.lastUpdateDate = configDetailsDTOObj.getConfigValue();
			 }
			 
			// MABQMS_UPDATE_TIMER in min
			 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(11, this, parent);
			 if(configDetailsDTOObj != null){
				Constant.updateTimer = Integer.parseInt(configDetailsDTOObj.getConfigValue());
			 }
			 
			 //MABQMS_IS_SSL
			 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(12, this, parent);
			 if(configDetailsDTOObj != null){
				Constant.isSSL = Integer.parseInt(configDetailsDTOObj.getConfigValue());
			 }
			 
			 // MABQMS_IMAGE_DESC_LAST_UPDATE
		 	 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(13, this, parent);
			 if(configDetailsDTOObj != null){
				Constant.lastImageDescriptionDate = configDetailsDTOObj.getConfigValue();
			 }
	 	 	
			 //MABQMS_APP_MODE
			 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(14, this, parent);
			 if(configDetailsDTOObj != null){
				 demoId.setText(configDetailsDTOObj.getConfigValue().equalsIgnoreCase("d") ? "Demo" : "Live");
				 
				 if(configDetailsDTOObj.getConfigValue().equalsIgnoreCase("d")){
					 //MABQMS_FIXED_URL
					 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(1, this, parent);
					 if(configDetailsDTOObj != null){
						Constant.fixedURL = configDetailsDTOObj.getConfigValue();
					 }
					 
					 //MABQMS_APP_URL
					 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(2, this, parent);
					 if(configDetailsDTOObj != null){
						Constant.URL = configDetailsDTOObj.getConfigValue();
					 }
					 
					// MABQMS_APK_URL
					 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(3, this, parent);
					 if(configDetailsDTOObj != null){
						Constant.apkURL = configDetailsDTOObj.getConfigValue();
					 }
					 
				}else{
					 //MABQMS_FIXED_URL
					 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(19, this, parent);
					 if(configDetailsDTOObj != null){
						Constant.fixedURL = configDetailsDTOObj.getConfigValue();
					 }
					 //MABQMS_APP_URL
					 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(20, this, parent);
					 if(configDetailsDTOObj != null){
						Constant.URL = configDetailsDTOObj.getConfigValue();
					 }
					 
					// MABQMS_APK_URL
					 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(21, this, parent);
					 if(configDetailsDTOObj != null){
						Constant.apkURL = configDetailsDTOObj.getConfigValue();
					 }
				 }
				 
			 }
			 
			 // MABQMS_TRAINING_BATCH
		 	 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(15, this, parent);
			 if(configDetailsDTOObj != null){
				Constant.trainingBatch = configDetailsDTOObj.getConfigValue();
			 }
			 
						 
			// MABQMS_NOTIFICATION_MESSAGE
		 	 configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(16, this, parent);
			 if(configDetailsDTOObj != null){
				Constant.messageNotinUse = configDetailsDTOObj.getConfigValue();
			 }
			 
			
	
		 
		 }catch(Exception e){
			 Log.d(Constant.tag, e.toString());
			 
		 }
		
	} // fillTextBox
	
    @Override
    protected void onStop() {
    	
    	super.onStop();
    	if(helper != null)
			helper.close();
			if(database != null || database.isOpen())
			database.close();
		}
    
    // This class to set the GPS values
	public class MyLocationListener implements LocationListener {

	    public void onLocationChanged(Location location) {
	        String message = String.format(
	                "New Location \n Longitude: %1$s \n Latitude: %2$s",
	                location.getLongitude(), location.getLatitude()
	        );
	        
	        AppLocationService appLocationService;
	  		  appLocationService = new AppLocationService(
	  				  
	  				MainActivity.this);
	  		  Location gpsLocation = appLocationService
							.getLocation(LocationManager.GPS_PROVIDER);
	  		  if (gpsLocation != null) {
	  			  	Constant.latitude  = Double.valueOf(location.getLatitude()).toString();
	  			  	Constant.longitude = Double.valueOf(location.getLongitude()).toString();
				}
	  		  if(gpsLocation == null)
	  		  {
		  		  Location nwLocation = appLocationService
								.getLocation(LocationManager.NETWORK_PROVIDER);
		  		  if (nwLocation != null) {
		  			  Constant.latitude  = Double.valueOf(location.getLatitude()).toString();
		  			  Constant.longitude = Double.valueOf(location.getLongitude()).toString();
					} 
	  		  	}
	  		  
	        
	      
	        
	        
	     //   Toast.makeText(NationalQualityMonitorsSystemActivity.this, message, Toast.LENGTH_LONG).show();
	    }

	    public void onStatusChanged(String s, int i, Bundle b) {
	    //    Toast.makeText(NationalQualityMonitorsSystemActivity.this, "Provider status changed",
	          //      Toast.LENGTH_LONG).show();
	    }

	    public void onProviderDisabled(String s) {
	    	  Notification.showErrorMessage(NotificationEnum.GPSoff, MainActivity.this, parent);
	    }

	    public void onProviderEnabled(String s) {
	       Notification.showErrorMessage(NotificationEnum.GPSon, MainActivity.this, parent);
	    }


	}
	
		// code to install the updated apk
		 private void installApk(){
		        Intent intent = new Intent(Intent.ACTION_VIEW);
		        String Path = Environment.getExternalStorageDirectory() + "/nqms.apk";
		        Uri uri = Uri.fromFile(new File(Path));
		        intent.setDataAndType(uri, "application/vnd.android.package-archive");
		        startActivity(intent);
		    } // installApk function ends here
    
		 
			// code to install the updated application into mobile device 
		 
		 private void downloadapk(){

			 HttpClient httpclient = new DefaultHttpClient();
			 
			 HttpGet getRequest = new HttpGet();
			 try {
				 
				
				 
				//getRequest.setURI(new URI("http://gateway.cdac.in:7113/apk/NQMSCdac.apk"));  // for training server
				getRequest.setURI(new URI(Constant.apkURL));   // for live server
				HttpResponse response = httpclient.execute(getRequest);

				InputStream InStream = response.getEntity().getContent();
				 File sdcard = Environment.getExternalStorageDirectory();
				 //String urlString =   Environment.getExternalStorageDirectory() + "/" + Constant.qmsPath + "/" + Constant.stdImagePath + "/" + uniqueCode + "/" + imageName;
				 String Path = Environment.getExternalStorageDirectory() + "/nqms.apk";
				// File efile = getBaseContext().getFileStreamPath(Path);
				 File efile = new File(Path);
				 if(efile.exists()){
					 efile.delete();
				 }
				 File file1 = new File(sdcard, "nqms.apk");
			       
				FileOutputStream file = new FileOutputStream(file1);

				byte[] buffer = new byte[1024];
				int len1 = 0;

				while ((len1 = InStream.read(buffer)) > 0) {
				   file.write(buffer, 0, len1);
				}
				 file.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 
			    } // downloadapk function ends here 	 
		 
	
	// code for background  processing of downloading the data from web server start here
			private class DownloadAPKTask extends AsyncTask<Context, String, String> {
			    


			     protected void onProgressUpdate(String... progress) {
			        // setProgressPercent(progress[0]);
			     }

			     protected void onPostExecute(String result) {
			        
			    	installApk();
			    	
			     }
			     
			
				@Override
				protected String doInBackground(Context... params) {
				  
			       downloadapk();
			       dialog.dismiss();
					return "";
				}

			
			 }
    
}
