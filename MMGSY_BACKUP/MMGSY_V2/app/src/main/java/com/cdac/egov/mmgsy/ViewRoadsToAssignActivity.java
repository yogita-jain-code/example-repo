package com.cdac.egov.mmgsy;

import java.io.File;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.http.conn.util.InetAddressUtils;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import webServices.DownloadUnplannedScheduleWebServices;

import common.Constant;
import common.ExceptionHandler;
import common.Notification;
import common.QMSHelper.NotificationEnum;

import db.DAO.ImageDetailsDAO;
import db.DAO.LoginDetailsDAO;
import db.DAO.ObservationDetailsDAO;
import db.DAO.ScheduleDetailsDAO;
import db.DTO.ImageDetailsDTO;
import db.DTO.LoginDetailsDTO;
import db.DTO.ObservationDetailsDTO;
import db.DTO.ScheduleDetailsDTO;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ViewRoadsToAssignActivity extends AppBaseActivity implements OnItemClickListener, OnClickListener {

	private ListView list;
	private ViewSchduleList adapter;
	private ArrayList<ScheduleDetailsDTO> roadDetailsDTOArrayList;
	private ViewRoadsToAssignActivity parent;
	private Serializable selectedGeneratedId;
	private ProgressDialog dialog;
	private ScheduleDetailsDTO scheduleDetailsDTOObj;
	private Integer qmCode;
	private String GenratedId;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = ViewRoadsToAssignActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_roads_to_assign);
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		Intent intent = getIntent();
		roadDetailsDTOArrayList = (ArrayList<ScheduleDetailsDTO>) intent.getSerializableExtra("scheduleDetailsDTOArrayListOfRoadsInPackage");
		selectedGeneratedId = intent.getSerializableExtra("selectedGeneratedId");
		
		
		TextView viewRoadMsg = (TextView) findViewById(R.id.errormsgViewRoadTextView);
		
		if(roadDetailsDTOArrayList != null){
			viewRoadMsg.setVisibility(LinearLayout.GONE);
		list = (ListView) findViewById(R.id.viewUnplannedScheduleList);
		adapter = new ViewSchduleList(this, R.layout.activity_view_roads_to_assign, android.R.layout.simple_list_item_1);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		}else{
			viewRoadMsg.setText("Road not Found");
			viewRoadMsg.setVisibility(LinearLayout.VISIBLE);
		}
		
	}
	
	
	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_roads_to_assign, menu);
		return true;
	}

	
		// code for arrayadapter class start here
		public class ViewSchduleList extends ArrayAdapter<String>{

			private Context context;

			public ViewSchduleList(Context context, int resource,int textViewResourceId) {
				super(context, resource, textViewResourceId);
				this.context = context;
			}
			
			@Override
			public int getCount() {
				//Log.d("MyAdapter", "getCount()");
				return roadDetailsDTOArrayList.size();
				
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				LinearLayout layout = new LinearLayout(context);
				layout.setOrientation(LinearLayout.VERTICAL);
			 	ScheduleDetailsDTO obj = roadDetailsDTOArrayList.get(position);
			 
		 		TextView textViewLocation = new TextView(ViewRoadsToAssignActivity.this);
		 		textViewLocation.setTextColor(Color.parseColor("#084B8A"));
		 		textViewLocation.setTextSize(20);
		 		textViewLocation.setText( (position + 1) + ". Location : " + obj.getStateName() + "/" + obj.getDistrictName() + "/" + obj.getBlockName());
				layout.addView(textViewLocation);
				
				TextView roadNameTextView = new TextView(ViewRoadsToAssignActivity.this);
				roadNameTextView.setTextColor(Color.parseColor("#084B8A"));
				roadNameTextView.setTextSize(20);
				roadNameTextView.setText("Road/LSB Name : " + obj.getRoadName());
				layout.addView(roadNameTextView);	
				
				TextView roadStatusTextView = new TextView(ViewRoadsToAssignActivity.this);
				roadStatusTextView.setTextColor(Color.parseColor("#084B8A"));
				roadStatusTextView.setTextSize(20);
				roadStatusTextView.setText("Status : " + (obj.getRoadStatus().equalsIgnoreCase("C") 
														  ? "Completed" 
														  : obj.getRoadStatus().equalsIgnoreCase("P")
														 	 ? "In Progress"
														 	 : obj.getRoadStatus().equalsIgnoreCase("M")
														 		 ? "Maintenance"
														 		 : "LSB").toString());
				layout.addView(roadStatusTextView);	
				
				TextView packageIdTextView = new TextView(ViewRoadsToAssignActivity.this);
				packageIdTextView.setTextColor(Color.parseColor("#084B8A"));
				packageIdTextView.setTextSize(20);
				packageIdTextView.setText("Package : " + obj.getPackageId());
				layout.addView(packageIdTextView);	
				
				TextView sanctionYearTextView = new TextView(ViewRoadsToAssignActivity.this);
				sanctionYearTextView.setTextColor(Color.parseColor("#084B8A"));
				sanctionYearTextView.setTextSize(20);
				sanctionYearTextView.setText("Sanction Year : " + obj.getSanctionYear());
				layout.addView(sanctionYearTextView);	
				
				TextView roadLengthTextView = new TextView(ViewRoadsToAssignActivity.this);
				roadLengthTextView.setTextColor(Color.parseColor("#084B8A"));
				roadLengthTextView.setTextSize(20);
				roadLengthTextView.setText("Length : " + obj.getRoadLength() + "km");
				layout.addView(roadLengthTextView);	
				
				if(obj.getRoadStatus().equalsIgnoreCase("C"))
				{
					TextView completionDateTextView = new TextView(ViewRoadsToAssignActivity.this);
					completionDateTextView.setTextColor(Color.parseColor("#084B8A"));
					completionDateTextView.setTextSize(20);
					completionDateTextView.setText("Completion Date : " + obj.getCompletionDate() );
					layout.addView(completionDateTextView);	
					
				}
 			
			 
				return layout;
			}
			
		}// ViewSchduleList class ends here 


		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
			
			 scheduleDetailsDTOObj = roadDetailsDTOArrayList.get(position);
			
			
			final String roadName = scheduleDetailsDTOObj.getRoadName();
			
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("Are you sure to map '"+ roadName + "' with the Generated Id : " + selectedGeneratedId)
					       .setCancelable(false)
					       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					    	  

							

							

							public void onClick(DialogInterface dialog1, int id) {
					    		   // mapping process
					    		   // update all details in database
					    		   // if successfully updated then give message and call finish()
					    		   // else keep on same screen and give message to try again
    
					    		   LoginDetailsDAO loginDetail = new LoginDetailsDAO();
								   LoginDetailsDTO loginDetailDTO = loginDetail.getLoginDetails(null, ViewRoadsToAssignActivity.this);
								   qmCode = loginDetailDTO.getQmCode();
								   GenratedId = (String) selectedGeneratedId;
					    		 
					    		  dialog = ProgressDialog.show(ViewRoadsToAssignActivity.this, "", 
					  	                "Assgining Schedule. Please wait...", true);
					    		  new AssignUnplannedScheduleTask().execute();
					    		   
					    		   /*mapRoadWithGeneratedId(obj, selectedGeneratedId.toString());
					    		   
					    		   ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
					    		   long updateRowCnt = scheduleDetailsDAOObj.mapRoadWithGeneratedId(obj, selectedGeneratedId.toString(), ViewRoadsToAssignActivity.this, parent);
					    		   if(updateRowCnt > 0)
					    		   {
					    			  Notification.showErrorMessage(NotificationEnum.roadMappedWithGenId, ViewRoadsToAssignActivity.this, parent);
					    			  finish();
					    		   }
					    		   else
					    		   {
					    			   
					    		   }*/ 
					           }
					          
					       })
					       .setNegativeButton("No", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog1, int id) {
					        	   dialog1.cancel();
					        	   
					           }
					          
					       });
					AlertDialog alert = builder.create();
					alert.show();
				
		}//onItemClick ends here


		@Override
		public void onClick(View v) {
			
			if(v.getId() == R.id.btnMainMenuLogoutId){
				logOut();
			}
		}
		
		
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
		}//logout ends here
		
		
		// function to map the genrated id with schedule code
		public Boolean  mapRoadWithGeneratedId(ScheduleDetailsDTO scheduleDetailsDTO, String GeneratedId){
			
			try{
			ImageDetailsDAO imageDetailsDAO = new ImageDetailsDAO();
			Integer totalImageCount = imageDetailsDAO.getImageDetailsCount(GeneratedId, this, parent);
			
			ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
	        
	         ArrayList<ImageDetailsDTO> imageDetailsArrayList = imageDetailsDAOObj.getImageDetails(GeneratedId, this, parent);
	         File folderPath = new File(Environment.getExternalStorageDirectory(), "/"+Constant.qmsPath + "/" + Constant.unplannedImagePath + "/" + GeneratedId);
	         if(folderPath.exists()){
	        	 File newfolder = new File(Environment.getExternalStorageDirectory(), "/"+Constant.qmsPath + "/" + Constant.unplannedImagePath + "/" + scheduleDetailsDTO.getUniqueCode());	 
	        	 folderPath.renameTo(newfolder);
	         }
	         
			for(int i = 0; i <totalImageCount; i++){
				
				File filePath = new File(Environment.getExternalStorageDirectory(), "/"+Constant.qmsPath + "/" + Constant.unplannedImagePath + "/" + scheduleDetailsDTO.getUniqueCode());
				
				if(filePath.exists()){
					
					
					 File from = new File(filePath,imageDetailsArrayList.get(i).getFileName());
				 	    String newFileName  = scheduleDetailsDTO.getUniqueCode()+"_"+(i+1)+".jpg";
					    File to = new File(filePath,newFileName);
					     if(from.exists()){
					        from.renameTo(to);
					        
					        ImageDetailsDTO imageDetailsDTOObj = new ImageDetailsDTO();
					        imageDetailsDTOObj = imageDetailsArrayList.get(i);
					        
					        imageDetailsDTOObj.setUniqueCode(scheduleDetailsDTO.getUniqueCode());
					        imageDetailsDTOObj.setFileName(newFileName);
					        
					        imageDetailsDAOObj.updateImageDetailsGenratedId(GeneratedId, imageDetailsDTOObj, this, parent);
					
				}
				
				
			}
		}// for loop ends here
	 		
		ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
		scheduleDetailsDTO.setStatus("I");
		scheduleDetailsDAOObj.mapRoadWithGeneratedId(scheduleDetailsDTO, GeneratedId, this, parent);
	   		
			
			}catch(Exception e){
				new ExceptionHandler(e, NotificationEnum.unhandledException, this, parent);
				return Boolean.FALSE;
			}
		
			return Boolean.TRUE;
		}
		
	
		private class AssignUnplannedScheduleTask extends AsyncTask<Context, String, String> {

			 protected void onPostExecute(String result) {
				 if(result.equalsIgnoreCase("1")){
					 Notification.showErrorMessage(NotificationEnum.unplannedAssignRoadsuccess, ViewRoadsToAssignActivity.this, parent);
					 finish();
				 }else{
					 Notification.showErrorMessage(NotificationEnum.unplannedAssignRoadfail, ViewRoadsToAssignActivity.this, parent);
				 }
			 }
			
			@Override
			protected String doInBackground(Context... context) {
				
				String scheduleCode = scheduleDetailsDTOObj.getUniqueCode().split("_")[0];
				String prRoadCode = scheduleDetailsDTOObj.getUniqueCode().split("_")[1];
				String monitorCode = qmCode.toString();
				String ipAddr =  getLocalIpAddress();
				
				 DownloadUnplannedScheduleWebServices unplannedScheduleWebServicesObj = new DownloadUnplannedScheduleWebServices();
	    		 if(unplannedScheduleWebServicesObj.AssignUnplannedSchedule(scheduleCode, prRoadCode, monitorCode, ipAddr)){
	    			 
	    			 if(mapRoadWithGeneratedId(scheduleDetailsDTOObj,GenratedId)){
	    				 dialog.dismiss();
	    				 return "1";
	    			 }else{
	    				 dialog.dismiss();
	    				 return "0";
	    			 }
	    			 
	    		 }else{
	    			 dialog.dismiss();
	    			 return "0";
	    		 }
				
				
				
			}
		}
		
		


		/*public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(Constant.tag, ex.toString());
        }
        return "";
    }*/
		
		public String getLocalIpAddress() {
	        try {
	            for (Enumeration<NetworkInterface> en = NetworkInterface
	                    .getNetworkInterfaces(); en.hasMoreElements();) {
	                NetworkInterface intf = en.nextElement();
	                for (Enumeration<InetAddress> enumIpAddr = intf
	                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                    InetAddress inetAddress = enumIpAddr.nextElement();
	                    if (!inetAddress.isLoopbackAddress() &&  InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
	                        return inetAddress.getHostAddress().toString();
	                    }
	                }
	            }
	        } catch (SocketException ex) {
	           // Log.e(tag, ex.toString());
	        }
	        return "";
	    }
		
		public String getIpAddr() {
			   WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
			   WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			   int ip = wifiInfo.getIpAddress();

			   String ipString = String.format(
			   "%d.%d.%d.%d",
			   (ip & 0xff),
			   (ip >> 8 & 0xff),
			   (ip >> 16 & 0xff),
			   (ip >> 24 & 0xff));

			   return ipString;
			}


		
}
