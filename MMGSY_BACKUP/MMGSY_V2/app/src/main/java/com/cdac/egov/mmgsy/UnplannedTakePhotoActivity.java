package com.cdac.egov.mmgsy;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class UnplannedTakePhotoActivity extends AppBaseActivity implements OnClickListener, OnItemSelectedListener {

	private UnplannedTakePhotoActivity parent;
	private ArrayList<String> imageDescriptionDAOArrayList;
	private Spinner imageDescriptionSpinner;
	private ArrayAdapter imageDescriptionAdapter;
	private Button takePhotosSetStartPointButton;
	private Button takePhotosSetEndPointButton;
	private Button takePhotosCancelButton;
	private Button takePhotosTakePhotoButton;
	private EditText imageDescriptionEditText;
	private TextView noOfPhotoTakenTextView;
	private Boolean isStartPoint;
	private ProgressDialog progDailog;
	private TextView takePhotoUniqueNoTextView;
	private TextView takePhotoRoadNameTextView;
	private TextView takePhotoPackageIdTextView;
	private TextView takePhotoSanctionYearTextView;
	private TextView takePhotoStateDistBlockTextView;
	private TextView takePhotoRoadStatusTextView;
	private static String generatedUniqueId;
	//private Spinner generatedIdSpinner;
	//private ArrayAdapter generatedIdAdapter;
	private static int selelectedLastGeneratedId;
	private static int newImageId;
	private static String imageName;
	private Uri outputFileUri;
	public static final int requestCode = 1;
	private TextView generatedIdTxtView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = UnplannedTakePhotoActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unplanned_take_photo);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		generatedIdTxtView = (TextView) findViewById(R.id.txtGeneratedIdsId);
		ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
		ScheduleDetailsDTO scheduleDetailsDTOObj = scheduleDetailsDAOObj.getGeneratedIds(UnplannedTakePhotoActivity.this, parent);
		generatedUniqueId = scheduleDetailsDTOObj.getUniqueCode();
		generatedIdTxtView.setText("Generated Id : "+ generatedUniqueId.toString());
		
		noOfPhotoTakenTextView = (TextView) findViewById(R.id.noOfPhotoTakenId);
		ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
		Integer imageCount = imageDetailsDAOObj.getImageDetailsCount(generatedUniqueId, this, parent);
		noOfPhotoTakenTextView.setText("Photos Taken : "+ imageCount +"/"+ Constant.maxImage);
		
		
		//fill spinner of Generated Id
		//ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
		//scheduleDetailsDTOArrayList =  scheduleDetailsDAOObj.getAllGeneratedIds(this, parent);
		//ArrayList<String> generatedIdList = new ArrayList<String>();
		//generatedIdList.add("Select Generated Id");
		//for (ScheduleDetailsDTO scheduleDetailsDTO : scheduleDetailsDTOArrayList) {
		//	generatedIdList.add(scheduleDetailsDTO.getUniqueCode());
		//}
		
		//generatedIdSpinner = (Spinner) findViewById(R.id.spinnerGeneratedIdsId);
		//generatedIdAdapter = new ArrayAdapter(this,R.layout.spinner_layout, generatedIdList);
		//generatedIdSpinner.setAdapter(generatedIdAdapter);
		//generatedIdSpinner.setSelection(selelectedLastGeneratedId);
		//generatedIdSpinner.setOnItemSelectedListener(this);
		//scheduleDetailsDTOArrayList = scheduleDetailsDAOObj.getAllScheduleDetails(this, parent);
			
		ImageDescriptionDAO imageDescriptionDAO = new ImageDescriptionDAO(); 
		imageDescriptionDAOArrayList =  imageDescriptionDAO.getAllImageDescription(this, parent);
		imageDescriptionSpinner = (Spinner) findViewById(R.id.spinnerImageDescriptionId);
		imageDescriptionAdapter = new ArrayAdapter(this,R.layout.spinner_layout, imageDescriptionDAOArrayList);
		imageDescriptionSpinner.setAdapter(imageDescriptionAdapter);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.unplanned_take_photo, menu);
		return true;
	}

	
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long  arg3) {
		// TODO Auto-generated method stub
		int localIndex = i-1;
				
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
		
	}//OnItemSelected Ends here
	
	
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
			Integer imageCount = imageDetailsDAOObj.getImageDetailsCount(generatedUniqueId, this, parent);
			if(imageCount == -1){
				Notification.showErrorMessage(NotificationEnum.unhandledException, this, parent);
				return;
			}
			if(imageCount >= Constant.maxImage){
				Notification.showErrorMessage(NotificationEnum.maximumNoOfPhotos, this, parent);
				return;
			}
			
			if(Constant.latitude == null && Constant.longitude == null){
				Notification.showErrorMessage(NotificationEnum.waitForCurrentLoc, this, parent);
				return;
			}
			
			 newImageId = imageCount + 1;
			 imageName = generatedUniqueId+"_"+newImageId+".jpg";
			 
			 	File wallperDir = new File(Environment.getExternalStorageDirectory(), "/"+Constant.qmsPath + "/" + Constant.unplannedImagePath + "/" + generatedUniqueId);
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
		
		 progDailog = ProgressDialog.show(UnplannedTakePhotoActivity.this, msg1, msg2,true);
		 
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
				long isPointSet = observationDetailsDAOObj.setLocationPoint(generatedUniqueId, isStartPoint, Constant.latitude, Constant.longitude, UnplannedTakePhotoActivity.this, parent);
				Integer key = Integer.parseInt(String.valueOf(isPointSet));
				
				switch (key) {
				
					case 0:		//Error
						Notification.showErrorMessage(NotificationEnum.pointNotSet, UnplannedTakePhotoActivity.this, parent);
						break;
						
					case 1: 	// Success
							if(isStartPoint)
						 		Notification.showErrorMessage(NotificationEnum.startPointSetSuccessfully, UnplannedTakePhotoActivity.this, parent);
							else
								Notification.showErrorMessage(NotificationEnum.endPointSetSuccessfully, UnplannedTakePhotoActivity.this, parent);
						break;		
						
					case 113: 	//Already set - Start Point
						Notification.showErrorMessage(NotificationEnum.startPointAlreadySet, UnplannedTakePhotoActivity.this, parent);
						break;	
						
					case 114:	//Already set - End Point
						Notification.showErrorMessage(NotificationEnum.endPointAlreadySet, UnplannedTakePhotoActivity.this, parent);
						break;
					
					default:
						break;
				} // switch ends here
			}
			else
			{
				Notification.showErrorMessage(NotificationEnum.pointNotSet, UnplannedTakePhotoActivity.this, parent);
			}
		 }
		};//Handler Ends Here
		
	
		public Boolean validateTakePhoto(){

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
		}//validateTakePhoto Ends here
		
		
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
			super.onActivityResult(requestCode, resultCode, data);
			
			Log.d("inside result ok", "2");
			
			if (resultCode == RESULT_OK) {
	   			
				Integer result = saveImageInformation();
					if(result == 1){
					Notification.showErrorMessage(NotificationEnum.photoSave, this, parent);
					UnplannedTakePhotoActivity.this.finish();
					}
					else{
						Notification.showErrorMessage(NotificationEnum.photoNotSave, this, parent);
						UnplannedTakePhotoActivity.this.finish();
						}
					
		 	        
			} // if ends here
			else{
				Notification.showErrorMessage(NotificationEnum.photoNotSave, this, parent);
				UnplannedTakePhotoActivity.this.finish();
			}
		
			
		} // onActivityResult ends here
		

		public Integer saveImageInformation(){
			
			Integer result = 0;
			// code to store the image information in basicimage table
		
			
			// Code to get the current date and time 
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String currentDateTime = df.format(c.getTime());
	    	
			ImageDetailsDTO imageDetailsDTO = new ImageDetailsDTO();
			imageDetailsDTO.setUniqueCode(generatedUniqueId);
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
			File thumbnailDir = new File(Environment.getExternalStorageDirectory(), "/"+Constant.qmsPath + "/" + Constant.unplannedImagePath + "/" + generatedUniqueId + "/" + Constant.thumbnailPath);
	        if (!thumbnailDir.exists())
	        {
	        	thumbnailDir.mkdirs();
	        }
	        String urlString =   Environment.getExternalStorageDirectory() + "/" + Constant.qmsPath + "/" + Constant.unplannedImagePath + "/" + generatedUniqueId + "/" + imageName;
	        String tumbnailUrlString =   Environment.getExternalStorageDirectory() + "/" + Constant.qmsPath + "/" + Constant.unplannedImagePath + "/" + generatedUniqueId + "/" + Constant.thumbnailPath;
	        Bitmap  bitmap = null;
	        
	        try{
	        	BitmapFactory.Options options=new BitmapFactory.Options();
	        	options.inSampleSize = 8;
	         bitmap = BitmapFactory.decodeFile(urlString,options);
	        }catch (Exception e) {
	        	 e.printStackTrace();
	        	 return -1;
			} 
	        if(bitmap != null){ 
	 	       Bitmap imageBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
	 	      try {
	 	    	
	 	         FileOutputStream out = new FileOutputStream(tumbnailUrlString+"/"+imageName);
	 	        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
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

}
