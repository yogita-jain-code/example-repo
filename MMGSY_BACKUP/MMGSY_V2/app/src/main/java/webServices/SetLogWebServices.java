package webServices;

import org.ksoap2.serialization.PropertyInfo;

import common.Constant;
import common.QMSHelper;
import common.RijndaelCrypt;
import common.WebService;

import db.DAO.ConfigDetailsDAO;
import db.DAO.LoginDetailsDAO;
import db.DTO.ConfigDetailsDTO;
import db.DTO.LoginDetailsDTO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

public class SetLogWebServices {


 public Integer setLog(String userName, Context context, Activity parent){

		 Integer result = 0;
		 RijndaelCrypt fixKeyObject  = new RijndaelCrypt(Constant.fixKey);
		 LoginDetailsDAO loginDAOObject  = new LoginDetailsDAO();
		 LoginDetailsDTO loginDTOObj = loginDAOObject.getLoginDetails(userName, context, parent);

		 ConfigDetailsDAO configDetailsDAO = new ConfigDetailsDAO();
		 ConfigDetailsDTO configDetailsDTOObj =  configDetailsDAO.getConfigDetails(4, context, parent);

		 TelephonyManager tm  = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		 @SuppressLint("MissingPermission") String mobileNumber  = tm.getLine1Number();
		 String imeiNumber	  = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
		 String osVersion 	  = String.valueOf(android.os.Build.VERSION.RELEASE);
		 String modelNo		  =  android.os.Build.MANUFACTURER+ " "+android.os.Build.MODEL;
	//	 String simop 	      = tm.getSimOperatorName();
		 String networkProvider = tm.getNetworkOperatorName();
		 String ownIP = QMSHelper.getLocalIpAddress();
		 
		   PropertyInfo monitorCodeProp =new PropertyInfo();
		   monitorCodeProp.setName("monitorCode");
		   monitorCodeProp.setValue(fixKeyObject.encrypt(loginDTOObj.getQmCode().toString().getBytes()));
		   monitorCodeProp.setType(String.class);
		 
		   PropertyInfo mobileNoProp =new PropertyInfo();
		   mobileNoProp.setName("mobileNo");
		   if(mobileNumber != null){
		   mobileNoProp.setValue(fixKeyObject.encrypt(mobileNumber.getBytes()));
		   }else{
		   mobileNoProp.setValue(fixKeyObject.encrypt("".getBytes()));	   
		   }
		   mobileNoProp.setType(String.class);

		   PropertyInfo imeiNoProp =new PropertyInfo();
		   imeiNoProp.setName("imeiNo");
		   imeiNoProp.setValue(fixKeyObject.encrypt(imeiNumber.getBytes()));
		   imeiNoProp.setType(String.class);
		   
		   PropertyInfo osVersionProp =new PropertyInfo();
		   osVersionProp.setName("osVersion");
		   osVersionProp.setValue(fixKeyObject.encrypt(osVersion.getBytes()));
		   osVersionProp.setType(String.class);
		   
		   PropertyInfo modelNameProp =new PropertyInfo();
		   modelNameProp.setName("modelName");
		   modelNameProp.setValue(fixKeyObject.encrypt(modelNo.getBytes()));
		   modelNameProp.setType(String.class);
		   
		   PropertyInfo nwProviderProp =new PropertyInfo();
		   nwProviderProp.setName("nwProvider");
		   nwProviderProp.setValue(fixKeyObject.encrypt(networkProvider.getBytes()));
		   nwProviderProp.setType(String.class);
		   
		   PropertyInfo appVersionProp =new PropertyInfo();
		   appVersionProp.setName("appVersion");
		   appVersionProp.setValue(fixKeyObject.encrypt(configDetailsDTOObj.getConfigValue().getBytes()));
		   appVersionProp.setType(String.class);
		   
		   PropertyInfo loginModeProp =new PropertyInfo();
		   loginModeProp.setName("loginMode");
		   loginModeProp.setValue(fixKeyObject.encrypt("O".getBytes()));
		   loginModeProp.setType(String.class);
		   
		   PropertyInfo ipAddressProp =new PropertyInfo();
		   ipAddressProp.setName("ipAddress");
		   ipAddressProp.setValue(fixKeyObject.encrypt(ownIP.getBytes()));
		   ipAddressProp.setType(String.class);
		 
		String response =  WebService.callWebService(Constant.URL, Constant.webServiceInsertLog, monitorCodeProp, 
				 mobileNoProp,
				 imeiNoProp,
				 osVersionProp,
				 modelNameProp,
				 nwProviderProp,
				 appVersionProp,
				 loginModeProp,
				 ipAddressProp
				 );
		 
		
        Integer output = Integer.parseInt(fixKeyObject.decrypt(response));
        return output;
  }
	
	// login background process ends here
}
