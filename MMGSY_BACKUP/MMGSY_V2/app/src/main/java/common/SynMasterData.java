package common;

import java.sql.Date;
import java.text.SimpleDateFormat;

import db.DAO.ConfigDetailsDAO;
import db.DAO.LoginDetailsDAO;
import db.DTO.ConfigDetailsDTO;
import db.DTO.LoginDetailsDTO;
import webServices.ConfigDetailsWebServices;
import webServices.ImageDescriptionWebServices;
import android.app.IntentService;
import android.content.Intent;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class SynMasterData extends IntentService {

	private static SynMasterData parent;
	public SynMasterData() {
		super("syncMasterData");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		parent = SynMasterData.this;
		
		String lastImageDescriptionDate = Constant.lastImageDescriptionDate;
		if(QMSHelper.isNetworkAvailable(this) == true){
			 
		 	
			 TelephonyManager tm  = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		 	 String imei	  = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
		 	 LoginDetailsDAO loginDetailsDAOObj = new LoginDetailsDAO();
		 	 LoginDetailsDTO loginDetailsDTOObj = 	loginDetailsDAOObj.getLoginDetails(null, this);
		 	if(loginDetailsDTOObj != null){
		 		
		 		ConfigDetailsDAO configDetailsDAOObj = new ConfigDetailsDAO();
			    ConfigDetailsDTO configDetailsDTOObj = configDetailsDAOObj.getConfigDetails(13, this, null); 
			    Constant.lastImageDescriptionDate =  configDetailsDTOObj.getConfigValue();
			    
		 		// Second time onwards server call
		 		ConfigDetailsWebServices configDetailsWebServices  = new ConfigDetailsWebServices();
			    configDetailsWebServices.callConfigDetailsWebServices(loginDetailsDTOObj.getUserName(), imei, this);
			    
			 	    	
			    //	ConfigDetailsDAO configDetailsDAOObj2 = new ConfigDetailsDAO();
				//    ConfigDetailsDTO configDetailsDTOObj2 = configDetailsDAOObj2.getConfigDetails(13, this, null); 
				    
				    ImageDescriptionWebServices imageDescriptionWebServices = new ImageDescriptionWebServices();
			    	imageDescriptionWebServices.callImageDescriptionWebServices(this);
			    	
			    	/*if(!configDetailsDTOObj2.getConfigValue().equalsIgnoreCase(Constant.lastImageDescriptionDate)){
			    		//  server call
			    		ImageDescriptionWebServices imageDescriptionWebServices = new ImageDescriptionWebServices();
				    	imageDescriptionWebServices.callImageDescriptionWebServices(this);

			    	}*/
			    	configDetailsWebServices.DownloadNotificationsWebServices(loginDetailsDTOObj.getUserName(), this);		
			    
		}else{
			// First time server call
			ConfigDetailsWebServices configDetailsWebServices  = new ConfigDetailsWebServices();
		    configDetailsWebServices.callConfigDetailsWebServices("", imei, this);
		    ImageDescriptionWebServices imageDescriptionWebServices = new ImageDescriptionWebServices();
		    imageDescriptionWebServices.callImageDescriptionWebServices(this);
			
		}
			 

	}

		  this.startService(new Intent(this, BackgroundImageUpload.class));
	}
	
	
}


