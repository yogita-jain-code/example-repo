package webServices;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.ksoap2.serialization.PropertyInfo;

import common.Constant;
import common.RijndaelCrypt;
import common.WebService;

import db.DTO.ImageDetailsDTO;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

public class UploadImageWebServices {

	public Integer setImageDetails(ImageDetailsDTO imageDetailsDTO, String mode , Context context) {
		
		
		 Integer result = 0;
		 RijndaelCrypt fixKeyObject  = new RijndaelCrypt(Constant.fixKey);
		 String path = "";
		 String deviceFlag = "";
		 if(mode.equalsIgnoreCase("planned")){
			 deviceFlag = "MS";
			path =  Environment.getExternalStorageDirectory()+"/"+ Constant.qmsPath +"/" +Constant.stdImagePath+"/"+ imageDetailsDTO.getUniqueCode()  +"/"+imageDetailsDTO.getFileName();
		 }else{
			 deviceFlag = "MU";
			 path =  Environment.getExternalStorageDirectory()+"/"+ Constant.qmsPath +"/" +Constant.unplannedImagePath+"/"+ imageDetailsDTO.getUniqueCode()  +"/"+imageDetailsDTO.getFileName(); 
		 }
		 
		 Bitmap bm = BitmapFactory.decodeFile(path);
		
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
		 
		 File file = new File(path);
		 if(file.exists() && bm != null)      
		 {
			 if(bm.getWidth() >= 2048 && bm.getHeight() >= 1152 ){
					bm.compress(CompressFormat.JPEG, 20, bos);
					}else{
					bm.compress(CompressFormat.JPEG, 75, bos);	
					}
			 
			 byte[] data = bos.toByteArray();
			 
			 String details[] = imageDetailsDTO.getUniqueCode().split("_");	 
			 
			 
			 PropertyInfo imgDataProp =new PropertyInfo();
			 imgDataProp.setName("imgData");
			 imgDataProp.setValue(Base64.encodeToString(data,Base64.DEFAULT));
			 imgDataProp.setType(Byte.class);
			 
			 PropertyInfo scheduleCodeProp =new PropertyInfo();
			 scheduleCodeProp.setName("scheduleCode");
			 scheduleCodeProp.setValue(fixKeyObject.encrypt(details[0].getBytes()));
			 scheduleCodeProp.setType(String.class);
			 
			 PropertyInfo prRoadCodeProp =new PropertyInfo();
			 prRoadCodeProp.setName("prRoadCode");
			 prRoadCodeProp.setValue(fixKeyObject.encrypt(details[1].getBytes()));
			 prRoadCodeProp.setType(String.class);
			 
			 PropertyInfo obsIdProp =new PropertyInfo();
			 obsIdProp.setName("obsId");
			 obsIdProp.setValue(fixKeyObject.encrypt(String.valueOf(imageDetailsDTO.getObservationId()).getBytes()));
			 obsIdProp.setType(String.class);
			 
			 PropertyInfo fileNameProp =new PropertyInfo();
			 fileNameProp.setName("fileName");
			 fileNameProp.setValue(fixKeyObject.encrypt(imageDetailsDTO.getFileName().getBytes()));
			 fileNameProp.setType(String.class);
			 
			 PropertyInfo fileDescProp =new PropertyInfo();
			 fileDescProp.setName("fileDesc");
			 fileDescProp.setValue(fixKeyObject.encrypt(imageDetailsDTO.getDescription().getBytes()));
			 fileDescProp.setType(String.class);
			 
			 PropertyInfo latitudeProp =new PropertyInfo();
			 latitudeProp.setName("latitude");
			 latitudeProp.setValue(fixKeyObject.encrypt(imageDetailsDTO.getLatitude().getBytes()));
			 latitudeProp.setType(String.class);
			 
			 PropertyInfo longitudeProp =new PropertyInfo();
			 longitudeProp.setName("longitude");
			 longitudeProp.setValue(fixKeyObject.encrypt(imageDetailsDTO.getLongitude().getBytes()));
			 longitudeProp.setType(String.class);
			 
			 PropertyInfo deviceFlagProp =new PropertyInfo();
			 deviceFlagProp.setName("deviceFlag");
			 deviceFlagProp.setValue(fixKeyObject.encrypt(deviceFlag.getBytes()));
			 deviceFlagProp.setType(String.class);
			 
			 PropertyInfo inspDateProp =new PropertyInfo();
			 inspDateProp.setName("inspDate");
			 inspDateProp.setValue(fixKeyObject.encrypt(imageDetailsDTO.getCaptureDateTime().getBytes()));
			 inspDateProp.setType(String.class);
			 
			//Avinash url
			 //Constant.URL="http://10.208.23.180:1000/MABQMS/MABQMS.asmx";  
			 String response =  WebService.callWebService(Constant.URL, Constant.webServiceUploadAndInsertImageDetailsWithDate, 
					   imgDataProp,
					   scheduleCodeProp, 
					   prRoadCodeProp,
					   obsIdProp,
					   fileNameProp,
					   fileDescProp,
					   latitudeProp,
					   longitudeProp,
					   deviceFlagProp,
					   inspDateProp
					   );
		    
			 // Integer output = 0;
			 String output = "0";
			
			 
		    if(response.length() == 0){
		    	output = "404";
			}
			
			if(response.equalsIgnoreCase("404")){
				output = "404";
			}
	       
	        if(output.equalsIgnoreCase("-1")){
	        	return -1;
	        }
		    
	         if(response != null){	
				 output = fixKeyObject.decrypt(response);
			 }
			 
			/*if(response != null){		  
			 output = Integer.parseInt(fixKeyObject.decrypt(response));
			}else{
				output = 404;
			}*/
			 
			 return Integer.parseInt(output);
		 }
		 else
		 {
			 return 9;
		 }
		
		 
		
		 
	}
	
}
