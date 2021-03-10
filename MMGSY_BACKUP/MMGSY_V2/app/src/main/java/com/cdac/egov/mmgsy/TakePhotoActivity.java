package com.cdac.egov.mmgsy;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import common.Constant;
import common.Notification;
import common.QMSHelper;
import common.QMSHelper.NotificationEnum;

import db.DAO.ImageDescriptionDAO;
import db.DAO.ImageDetailsDAO;
import db.DAO.ObservationDetailsDAO;
import db.DAO.ScheduleDetailsDAO;
import db.DTO.ImageDetailsDTO;
import db.DTO.ScheduleDetailsDTO;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class TakePhotoActivity extends AppBaseActivity implements OnItemSelectedListener, OnClickListener{

	private Spinner roadSpinner;
	private ArrayAdapter roadAdapter;
	private TakePhotoActivity parent;
	private ArrayAdapter imageDescriptionAdapter;
	private Spinner imageDescriptionSpinner;
	private ArrayList<ScheduleDetailsDTO> scheduleDetailsDTOArrayList;
	private TextView takePhotoUniqueNoTextView;
	private TextView takePhotoRoadNameTextView;
	private TextView takePhotoPackageIdTextView;
	private TextView takePhotoSanctionYearTextView;
	private TextView takePhotoStateDistBlockTextView;
	private TextView takePhotoRoadStatusTextView;
	private ArrayList<String> imageDescriptionDAOArrayList;
	private Button takePhotosSetStartPointButton;
	private Button takePhotosSetEndPointButton;
	private Button takePhotosCancelButton;
	private Button takePhotosTakePhotoButton;
	private ProgressDialog progDailog;
	private Boolean isStartPoint;
	private static String uniqueCode;
	private EditText imageDescriptionEditText;
	private static int newImageId;
	private static String imageName;
	private Uri outputFileUri;
	private TextView noOfPhotoTakenTextView;
	private static int selelectedLastRoad = 0;
	public static final int requestCode = 1;
	
	public String ScheduleDownloadDateTime;
	public String ScheduleDownloadDate;
	
	 public LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		parent = TakePhotoActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);

	//	roadSpinner.setSelection(selelectedLastRoad);	
		ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
		
		scheduleDetailsDTOArrayList =  scheduleDetailsDAOObj.getAllScheduleDetails(this, parent,0, "T");
		ArrayList<String> roadList = new ArrayList<String>();
		roadList.add("Select Road");
		for (ScheduleDetailsDTO scheduleDetailsDTO : scheduleDetailsDTOArrayList) {
			roadList.add(scheduleDetailsDTO.getRoadName());
		}
		
		roadSpinner = (Spinner) findViewById(R.id.spinnerRoadNameId);
		roadAdapter = new ArrayAdapter(this,R.layout.spinner_layout, roadList);
		roadSpinner.setAdapter(roadAdapter);
		roadSpinner.setSelection(selelectedLastRoad);
		roadSpinner.setOnItemSelectedListener(this);
		
		ImageDescriptionDAO imageDescriptionDAO = new ImageDescriptionDAO(); 
		imageDescriptionDAOArrayList =  imageDescriptionDAO.getAllImageDescription(this, parent);
		imageDescriptionSpinner = (Spinner) findViewById(R.id.spinnerImageDescriptionId);
		
		if(imageDescriptionDAOArrayList != null){
		imageDescriptionAdapter = new ArrayAdapter(this,R.layout.spinner_layout, imageDescriptionDAOArrayList);
		imageDescriptionSpinner.setAdapter(imageDescriptionAdapter);
		}else{
			imageDescriptionDAOArrayList = getImageDescription();
			imageDescriptionAdapter = new ArrayAdapter(this,R.layout.spinner_layout, imageDescriptionDAOArrayList);
			imageDescriptionSpinner.setAdapter(imageDescriptionAdapter);
		}
		imageDescriptionSpinner.setOnItemSelectedListener(this);
		
		 takePhotosSetStartPointButton = (Button) findViewById(R.id.btnTakePhotosSetStartPointId);
		 takePhotosSetStartPointButton.setOnClickListener(this);
		 takePhotosSetEndPointButton = (Button) findViewById(R.id.btnTakePhotosSetEndPointId);
		 takePhotosSetEndPointButton.setOnClickListener(this);
		 takePhotosCancelButton = (Button) findViewById(R.id.btnTakePhotosCancelId);
		 takePhotosCancelButton.setOnClickListener(this);
		 takePhotosTakePhotoButton = (Button) findViewById(R.id.btnTakePhotosTakePhotoId);
		 takePhotosTakePhotoButton.setOnClickListener(this);
		
		 imageDescriptionEditText = (EditText)findViewById(R.id.imageDescriptionEditTextId);
		 noOfPhotoTakenTextView = (TextView) findViewById(R.id.noOfPhotoTakenId);
		 
		  locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	  	  if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
	  	  {
	  		  //Toast.makeText(getApplicationContext(), "GPS is Enable", Toast.LENGTH_SHORT).show();
	  		  AppLocationService appLocationService;
	  		  appLocationService = new AppLocationService(
	  				  
	  				TakePhotoActivity.this);
	  		  Location gpsLocation = appLocationService
							.getLocation(LocationManager.GPS_PROVIDER);
	  		  if (gpsLocation != null) {
						 Constant.latitude = Double.valueOf(gpsLocation.getLatitude()).toString();
						 Constant.longitude = Double.valueOf(gpsLocation.getLongitude()).toString();
						 Constant.GPSDateTime = new Date(gpsLocation.getTime());
						 
						 //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				         //String currentDateTime = df.format(c.getTime());
					}
	  		  if(gpsLocation == null)
	  		  {
		  		  Location nwLocation = appLocationService
								.getLocation(LocationManager.NETWORK_PROVIDER);
		  		  if (nwLocation != null) {
		  			  Constant.latitude = Double.valueOf(nwLocation.getLatitude()).toString();
		  			  Constant.longitude = Double.valueOf(nwLocation.getLongitude()).toString();
		  			  
		  			 // Calendar c = Calendar.getInstance();
		  			  //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  		     // String currentDateTime = df.format(c.getTime());
		  			  //Constant.GPSDateTime = new Date("Fri Oct 06 08:47:10 GMT+05:30 2017");
					} 
	  		  	}
	  	  }
	  	  else
	  	  {
	  		showGPSDisabledAlertToUser();
	  	  }
	  	  
	  	  
	}
	
	@Override
	public void onBackPressed() {
	}

	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }
	
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long  arg3) {
		// TODO Auto-generated method stub
		int localIndex = i-1;
		if(adapterView.getId() == R.id.spinnerRoadNameId){
			if(localIndex != -1){
				// Theme_DeviceDefault_Dialog_NoActionBar
				 final Dialog dialog = new Dialog(this,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);//new Dialog(this);
				 dialog.setContentView(R.layout.road_details_dialog_layout);
				 dialog.setTitle("Standard Mode Take Photos");
				 
				 Button dialogButton = (Button) dialog.findViewById(R.id.btnTakePhotosRoadDetailOK);
					// if button is clicked, close the custom dialog
					dialogButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
				
				 takePhotoUniqueNoTextView = (TextView) dialog.findViewById(R.id.takePhotoUniqueNoId);
				 takePhotoRoadNameTextView = (TextView) dialog.findViewById(R.id.takePhotoRoadNameId);
				 takePhotoPackageIdTextView = (TextView) dialog.findViewById(R.id.takePhotoPackageIdId);
				 takePhotoSanctionYearTextView = (TextView) dialog.findViewById(R.id.takePhotoSanctionYearId);
				 takePhotoStateDistBlockTextView = (TextView) dialog.findViewById(R.id.takePhotoStateDistBlockId);
				 takePhotoRoadStatusTextView = (TextView) dialog.findViewById(R.id.takePhotoRoadStatusId);
		    
				 
				 ScheduleDownloadDateTime = scheduleDetailsDTOArrayList.get(localIndex).getScheduleDownloadDate();
				 
				 String res[] = ScheduleDownloadDateTime.split("T");
				 
				 if(res.length > 0)
				 {
					 ScheduleDownloadDate = res[0].trim();
				 }
				 
			String status = (scheduleDetailsDTOArrayList.get(localIndex).getRoadStatus().equalsIgnoreCase("c") 
							?  "Completed" 
							: 	scheduleDetailsDTOArrayList.get(localIndex).getRoadStatus().equalsIgnoreCase("p") 
								? "In Progress"
								: scheduleDetailsDTOArrayList.get(localIndex).getRoadStatus().equalsIgnoreCase("m") 
								  ? "Maintenance"
								  : "LSB"		  );	 
				 
				
			takePhotoUniqueNoTextView.setText( "Unique No : " +scheduleDetailsDTOArrayList.get(localIndex).getUniqueCode());
			takePhotoRoadNameTextView.setText("Road : " + scheduleDetailsDTOArrayList.get(localIndex).getRoadName());
			takePhotoRoadStatusTextView.setText("Status : " + status);
			takePhotoPackageIdTextView.setText("Package : " + scheduleDetailsDTOArrayList.get(localIndex).getPackageId());
			takePhotoSanctionYearTextView.setText("Sanction Year : "+ scheduleDetailsDTOArrayList.get(localIndex).getSanctionYear() + " - "+ (Integer.parseInt(scheduleDetailsDTOArrayList.get(localIndex).getSanctionYear())+1));
			takePhotoStateDistBlockTextView.setText("Location : "+scheduleDetailsDTOArrayList.get(localIndex).getStateName() + " / "+ scheduleDetailsDTOArrayList.get(localIndex).getDistrictName() + " / "+ scheduleDetailsDTOArrayList.get(localIndex).getBlockName());
	  		
			 dialog.show();
			// Selected Road uniqueCode  
				uniqueCode = scheduleDetailsDTOArrayList.get(localIndex).getUniqueCode();
				ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
 				Integer imageCount = imageDetailsDAOObj.getImageDetailsCount(uniqueCode, this, parent);
 				noOfPhotoTakenTextView.setText("Photos Taken : "+ imageCount +"/"+ Constant.maxImage);
 				selelectedLastRoad  = roadSpinner.getSelectedItemPosition();
			}
			
		}
		
		if(adapterView.getId() == R.id.spinnerImageDescriptionId){

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			
			if(i == 1){
				
				// code to display the set start point button and hide other buttons and Edit box
				takePhotosSetStartPointButton.setVisibility(RelativeLayout.VISIBLE);
				takePhotosSetEndPointButton.setVisibility(RelativeLayout.GONE);
				takePhotosTakePhotoButton.setVisibility(RelativeLayout.GONE);
				imageDescriptionEditText.setVisibility(RelativeLayout.GONE);
				
				
		 		}else if(i == 2){
						takePhotosSetStartPointButton.setVisibility(RelativeLayout.GONE);
						takePhotosSetEndPointButton.setVisibility(RelativeLayout.VISIBLE);
						takePhotosTakePhotoButton.setVisibility(RelativeLayout.GONE);
						imageDescriptionEditText.setVisibility(RelativeLayout.GONE);
				
					}
		 			else{
		 				
						takePhotosSetStartPointButton.setVisibility(RelativeLayout.GONE);
						takePhotosSetEndPointButton.setVisibility(RelativeLayout.GONE);
						takePhotosTakePhotoButton.setVisibility(RelativeLayout.VISIBLE);
						if(i != 0){
						imageDescriptionEditText.setVisibility(RelativeLayout.VISIBLE);
						imageDescriptionEditText.setText(imageDescriptionSpinner.getSelectedItem().toString());
						}else{
							imageDescriptionEditText.setVisibility(RelativeLayout.GONE);
							imageDescriptionEditText.setText("");	
						}
						
					}
			}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.btnMainMenuLogoutId){
			logOut();
		}
		
		if(v.getId() == R.id.btnTakePhotosSetStartPointId){
			isStartPoint = Boolean.TRUE;
			setLocationPoint();
		}
		
		if(v.getId() == R.id.btnTakePhotosSetEndPointId){
			isStartPoint = Boolean.FALSE;
			setLocationPoint();
			}
		
		if(v.getId() == R.id.btnTakePhotosCancelId){
			finish();
		}
		
		if(v.getId() == R.id.btnTakePhotosTakePhotoId){
			
			if(!validateTakePhoto()){
				return;
			}
			
			ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
			Integer imageCount = imageDetailsDAOObj.getImageDetailsCount(uniqueCode, this, parent);
			if(imageCount == -1){
				Notification.showErrorMessage(NotificationEnum.unhandledException, this, parent);
				return;
			}
			if(imageCount >= Constant.maxImage){
				Notification.showErrorMessage(NotificationEnum.maximumNoOfPhotos, this, parent);
				return;
			}
			
			if((Constant.latitude == null && Constant.longitude == null) || (Constant.latitude == ""  && Constant.longitude == "")){
				Notification.showErrorMessage(NotificationEnum.waitForCurrentLoc, this, parent);
				return;
			}
			
			if(Constant.GPSDateTime != null){
				//Get Schedule Download Date and Compare to current date (30 days validation)
				
				//Constant.GPSDateTime
				
			
			  //Schedule Download Date 
			  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
			  Date sd = null;
			  try 
			  {
				  sd = formatter2.parse(ScheduleDownloadDate);//catch exception
			  } 
			  catch (ParseException e)
			  {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
			  } 
			  
			  Calendar ScheduleDownloadDateFormate = Calendar.getInstance();
			  ScheduleDownloadDateFormate.setTime(sd); //rest is the same....
			  
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  String GPSDate = sdf.format(Constant.GPSDateTime);
			  
			  // Current Date for GPS
			  Date cd = null;
			  try 
			  {
				  cd = formatter.parse(GPSDate);//catch exception
			  } 
			  catch (ParseException e)
			  {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
			  } 
			  
			  Calendar CurrentDateFormate = Calendar.getInstance();
			  CurrentDateFormate.setTime(cd); //rest is the same....

			  
			  long diff = CurrentDateFormate.getTimeInMillis() - ScheduleDownloadDateFormate.getTimeInMillis(); //result in millis
			  long daysDiff = diff / (24 * 60 * 60 * 1000);
			  
			  if(daysDiff < 0 || daysDiff >  Constant.numberOfDays)
			  {
				  	//Notification.showErrorMessage(NotificationEnum.scheduleExpired, this, parent);
					//return;  
			  }
			}
			
			if(Constant.GPSDateTime == null)
			{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				
				// Current Date 
				Calendar c = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				String currentDateTime;
				currentDateTime = df.format(c.getTime());
				
				Date cd = null;
				try 
				{
					cd = formatter.parse(currentDateTime);//catch exception
				} 
				catch (ParseException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				  
				Calendar CurrentDateFormate = Calendar.getInstance();
			  	CurrentDateFormate.setTime(cd); //rest is the same....
				  
			  	
				//Schedule Download Date 
			  	SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			  	
				Date sd = null;
				try 
				{
				  sd = formatter1.parse(ScheduleDownloadDate);//catch exception
				} 
				catch (ParseException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Calendar ScheduleDownloadDateFormate = Calendar.getInstance();
				ScheduleDownloadDateFormate.setTime(sd); //rest is the same....
				
				
				
	  	 		long diff = CurrentDateFormate.getTimeInMillis() - ScheduleDownloadDateFormate.getTimeInMillis(); //result in millis
	  	 		long daysDiff = diff / (24 * 60 * 60 * 1000);
				  
	  	 		if(daysDiff < 0 || daysDiff > Constant.numberOfDays)
	  	 		{
				  	//Notification.showErrorMessage(NotificationEnum.scheduleExpired, this, parent);
					//return;  
	  	 		}
				
			}
			
			 newImageId = imageCount + 1;
			 imageName = uniqueCode+"_"+newImageId+".jpg"; //39_15_1.jpg
			 
			 	File wallperDir = new File(Environment.getExternalStorageDirectory(), "/"+Constant.qmsPath + "/" + Constant.stdImagePath + "/" + uniqueCode);  ///storage/emulated/0/QMS/Planned/39_15
				wallperDir.mkdirs();
				//wallperDir = new File(Environment.getExternalStorageDirectory(), );
				
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				File file = new File(wallperDir, imageName);
			 
				outputFileUri = Uri.fromFile(file);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
				intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024);
				startActivityForResult(intent, requestCode);
			
		}// If ends here 
  		
	}

	
	
	public void setLocationPoint(){
		String msg1,msg2;
		if(isStartPoint){
			 msg1 = "Start Point";
			 msg2 = "setting start point, please wait....";
		}else{
			 msg1 = "End Point ";
			 msg2 = "setting end point, please wait....";
		}
		
		 progDailog = ProgressDialog.show(TakePhotoActivity.this, msg1, msg2,true);
		 
		   new Thread() {
               public void run() {
                                   try{
                    // just doing some long operation
                    sleep(10000);
               } catch (Exception e) {  }
          
            handler.sendEmptyMessage(0);
            progDailog.dismiss();                                   
          }
      }.start();
	
	} // setLocationPoint Ends here 
	
	
	private Handler handler = new Handler() {
		 @Override
		 public void handleMessage(Message msg ) {
			
			if(Constant.latitude != null && Constant.longitude != null){
				ObservationDetailsDAO observationDetailsDAOObj = new ObservationDetailsDAO();
				long isPointSet = observationDetailsDAOObj.setLocationPoint(uniqueCode, isStartPoint, Constant.latitude, Constant.longitude, TakePhotoActivity.this, parent);
				Integer key = Integer.parseInt(String.valueOf(isPointSet));
				
				switch (key) {
				
					case 0:		//Error
						Notification.showErrorMessage(NotificationEnum.pointNotSet, TakePhotoActivity.this, parent);
						break;
						
					case 1: 	// Success
							if(isStartPoint)
						 		Notification.showErrorMessage(NotificationEnum.startPointSetSuccessfully, TakePhotoActivity.this, parent);
							else
								Notification.showErrorMessage(NotificationEnum.endPointSetSuccessfully, TakePhotoActivity.this, parent);
						break;		
						
					case 113: 	//Already set - Start Point
						Notification.showErrorMessage(NotificationEnum.startPointAlreadySet, TakePhotoActivity.this, parent);
						break;	
						
					case 114:	//Already set - End Point
						Notification.showErrorMessage(NotificationEnum.endPointAlreadySet, TakePhotoActivity.this, parent);
						break;
					
					default:
						break;
				} // switch ends here
			}
			else
			{
				Notification.showErrorMessage(NotificationEnum.pointNotSet, TakePhotoActivity.this, parent);
			}
		 }
		};
		
		
	public Boolean validateTakePhoto(){
		
		if(roadSpinner.getSelectedItemPosition() == 0){
			Notification.showErrorMessage(NotificationEnum.selectRoad, this, parent);
			QMSHelper.blink(roadSpinner);
			return Boolean.FALSE;
		}
		
		if(imageDescriptionSpinner.getSelectedItemPosition() == 0){
			
			Notification.showErrorMessage(NotificationEnum.selectDescription, this, parent);
			QMSHelper.blink(imageDescriptionSpinner);
			return Boolean.FALSE;
		}
		
		if(imageDescriptionEditText.getText().toString().length()==0){
			Notification.showErrorMessage(NotificationEnum.emptyDescription, this, parent);
			QMSHelper.blink(imageDescriptionEditText);
			return Boolean.FALSE;
		}
		
		if(imageDescriptionEditText.getText().toString().length() > 200){
			Notification.showErrorMessage(NotificationEnum.exceedDescriptionLength, this, parent);
			QMSHelper.blink(imageDescriptionEditText);
			return Boolean.FALSE;
		}
		
		String SpChar[] = {"~","!","@,","#","$","%","^","&","*","\\",":",";","`","(",")","{","}","[","]","<",">","?","+","="};
		for(int i = 0; i<SpChar.length;i++){
		if(imageDescriptionEditText.getText().toString().contains(SpChar[i])){
			Notification.showErrorMessage(NotificationEnum.specialCharDescription, this, parent);
			QMSHelper.blink(imageDescriptionEditText);
			return Boolean.FALSE;
			}
		}
		
		
		return Boolean.TRUE;
	}
	
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.d("inside result ok", "2");
		
		if (resultCode == RESULT_OK) {
   			
			Integer result = saveImageInformation();
				if(result == 1){
				Notification.showErrorMessage(NotificationEnum.photoSave, this, parent);
				TakePhotoActivity.this.finish();
				}
				else{
					Notification.showErrorMessage(NotificationEnum.photoNotSave, this, parent);
					TakePhotoActivity.this.finish();
					}
				
	 	        
		} // if ends here
		else{
			Notification.showErrorMessage(NotificationEnum.photoNotSave, this, parent);
			TakePhotoActivity.this.finish();
		}
	
		
	} // onActivityResult ends here
	

	public Integer saveImageInformation(){
		
		Integer result = 0;
		// code to store the image information in basicimage table
	
		
		// Code to get the current date and time 
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateTime;
		if(Constant.GPSDateTime == null)
		{
			 currentDateTime = df.format(c.getTime());
		}
		else
		{
			 currentDateTime = df.format(Constant.GPSDateTime);
        }
    	
		ImageDetailsDTO imageDetailsDTO = new ImageDetailsDTO();
		imageDetailsDTO.setUniqueCode(uniqueCode);
		imageDetailsDTO.setFileId(newImageId);
		imageDetailsDTO.setObservationId(null);
		imageDetailsDTO.setFileName(imageName);
		imageDetailsDTO.setDescription(imageDescriptionEditText.getText().toString());
		imageDetailsDTO.setLatitude(Constant.latitude);
		imageDetailsDTO.setLongitude(Constant.longitude);
		imageDetailsDTO.setCaptureDateTime(currentDateTime);
		imageDetailsDTO.setIsUploaded(0);
		imageDetailsDTO.setIsSelected(0);
		
		ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
		if(!imageDetailsDAOObj.setImageDetails(imageDetailsDTO, this, parent)){
			return -1;
		}
		
		
		
		// code to create thumbnail of image start here
		File thumbnailDir = new File(Environment.getExternalStorageDirectory(), "/"+Constant.qmsPath + "/" + Constant.stdImagePath + "/" + uniqueCode + "/" + Constant.thumbnailPath);   ///storage/emulated/0/QMS/Planned/39_15/thumbnail
	//	File thumbnailDir = new File(Environment.getExternalStorageDirectory(), "/thumbnailsNQMS");
        if (!thumbnailDir.exists())
        {
        	thumbnailDir.mkdirs();
        }
        //String urlString = Environment.getExternalStorageDirectory()+"/StandardNQMS/"+filename;
        String urlString =   Environment.getExternalStorageDirectory() + "/" + Constant.qmsPath + "/" + Constant.stdImagePath + "/" + uniqueCode + "/" + imageName;    ///storage/emulated/0/QMS/Planned/39_15/39_15_1.jpg
        String tumbnailUrlString =   Environment.getExternalStorageDirectory() + "/" + Constant.qmsPath + "/" + Constant.stdImagePath + "/" + uniqueCode + "/" + Constant.thumbnailPath;  ///storage/emulated/0/QMS/Planned/39_15/thumbnail
        Bitmap  bitmap = null;
        try{
        	BitmapFactory.Options options=new BitmapFactory.Options();
        	options.inSampleSize = 8;
         bitmap = BitmapFactory.decodeFile(urlString,options);  //android.graphics.Bitmap@7851cd4
        }catch (Exception e) {
        	 e.printStackTrace();
        	 return -1;
		} 
        if(bitmap != null){ 
 	       Bitmap imageBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);  //android.graphics.Bitmap@d59035
 	      try {
 	    	
 	         FileOutputStream out = new FileOutputStream(tumbnailUrlString+"/"+imageName); //java.io.FileOutputStream@971db96    /storage/emulated/0/QMS/Planned/39_15/thumbnail/39_15_1.jpg
 	        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);  //android.graphics.Bitmap@d59035
 	       out.flush();
 	       out.close();
 	      
 	  } catch (Exception e) {
 	         e.printStackTrace();
 	        return -1;
 	  }

 	    
        }
		// code to create thumbnail of image ends here
        return 1;
		
		
	} // saveImageInformation ends here
	
	public void logOut()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(parent);
		builder.setMessage("Are you sure to logout?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		          
				public void onClick(DialogInterface dialog, int id) {
						closeAllActivities();
					}
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		           }
		       });
		
		AlertDialog alert = builder.create();
		alert.show();
	}
	
public static ArrayList<String> getImageDescription(){
		
		ArrayList<String> ImageDescriptionArrayList = new ArrayList<String>();
		
		
		// SQLiteDatabase database = helper.getWritableDatabase();
		ImageDescriptionArrayList.add("Select Description");
		ImageDescriptionArrayList.add("Start point of the road");
		ImageDescriptionArrayList.add("End point of the road");
		ImageDescriptionArrayList.add("A general view of the road");
		ImageDescriptionArrayList.add("Any Other");
		ImageDescriptionArrayList.add("Bituminous layer at km");
		ImageDescriptionArrayList.add("Camber at km");
		ImageDescriptionArrayList.add("Carriageway at km");
		ImageDescriptionArrayList.add("CC Pavement and Drains at km");
		ImageDescriptionArrayList.add("CD at km");
		ImageDescriptionArrayList.add("Density Test at km");
		ImageDescriptionArrayList.add("Embankment at km");
		ImageDescriptionArrayList.add("Gradation Test GSB – km");
		ImageDescriptionArrayList.add("Gradation Test WBM – km");
		ImageDescriptionArrayList.add("GSB at km");
		ImageDescriptionArrayList.add("Informatory Board at km");
		ImageDescriptionArrayList.add("L-Gradient at km");
		ImageDescriptionArrayList.add("Lab Equipments");
		ImageDescriptionArrayList.add("Logo Board");
		ImageDescriptionArrayList.add("Plasticity of filler material");
		ImageDescriptionArrayList.add("Road-way at km");
		ImageDescriptionArrayList.add("Side Slope at km");
		ImageDescriptionArrayList.add("Sign Board");
		ImageDescriptionArrayList.add("Site laboratory");
		ImageDescriptionArrayList.add("Sub-grade at km");
		ImageDescriptionArrayList.add("Super elevation at km");
		ImageDescriptionArrayList.add("Test pit");
		ImageDescriptionArrayList.add("Volumetric Analysis WBM at km");
		ImageDescriptionArrayList.add("WBM at km");
		
	
		
	
		return ImageDescriptionArrayList;
	} // getImage Description ends here 

		public void showGPSDisabledAlertToUser() {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					TakePhotoActivity.this);
		
			alertDialog.setTitle("GPS" + " SETTINGS");
		
			alertDialog
					.setMessage("GPS" + " is not enabled! Want to go to settings menu?");
		
			alertDialog.setPositiveButton("Settings",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							TakePhotoActivity.this.startActivity(intent);
						}
					});
		
			alertDialog.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(TakePhotoActivity.this,MainMenuActivity.class);
							TakePhotoActivity.this.startActivity(intent);
							dialog.cancel();
							
						}
					});
		
			alertDialog.show();
		}
}
