package webServices;

import java.util.ArrayList;

import org.ksoap2.serialization.PropertyInfo;

import common.Constant;
import common.RijndaelCrypt;
import common.WebService;

import db.DAO.ImageDetailsDAO;
import db.DAO.LoginDetailsDAO;
import db.DAO.ObservationDetailsDAO;
import db.DAO.ScheduleDetailsDAO;
import db.DTO.ImageDetailsDTO;
import db.DTO.LoginDetailsDTO;
import db.DTO.ObservationDetailsDTO;
import db.DTO.ScheduleDetailsDTO;
import android.app.Activity;
import android.content.Context;

public class UploadInspectionDetails {

	
	public Integer setInspectionDetails(String uniqueCode, Context context, Activity parent){
		
		 Integer result = 0;
		 RijndaelCrypt fixKeyObject  = new RijndaelCrypt(Constant.fixKey);
		 ObservationDetailsDAO observationDetailsDAOObj  = new ObservationDetailsDAO();
		 ObservationDetailsDTO observationDetailsDTOObj = observationDetailsDAOObj.getObservationDetails(uniqueCode,context,parent);
		
		 ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
		 ScheduleDetailsDTO scheduleDetailsDTOObj = scheduleDetailsDAOObj.getScheduleDetails(uniqueCode, context, parent);
		 
		
		 if(observationDetailsDTOObj.getIsUploaded() == 1){
			 return 1;
		 }
		 
			String details[] = observationDetailsDTOObj.getUniqueCode().split("_");	 
				 
		   PropertyInfo scheduleCodeProp =new PropertyInfo();
		   scheduleCodeProp.setName("scheduleCode");
		   scheduleCodeProp.setValue(fixKeyObject.encrypt(details[0].getBytes()));
		   scheduleCodeProp.setType(String.class);
		   
		   PropertyInfo prRoadCodeProp =new PropertyInfo();
		   prRoadCodeProp.setName("prRoadCode");
		   prRoadCodeProp.setValue(fixKeyObject.encrypt(details[1].getBytes()));
		   prRoadCodeProp.setType(String.class);
		   
		   PropertyInfo inspDateProp =new PropertyInfo();
		   inspDateProp.setName("inspDate");
		   inspDateProp.setValue(fixKeyObject.encrypt(observationDetailsDTOObj.getInspectionDate().getBytes()));
		   inspDateProp.setType(String.class);
		   
		   PropertyInfo startChainageProp =new PropertyInfo();
		   startChainageProp.setName("startChainage");
		   startChainageProp.setValue(fixKeyObject.encrypt(String.valueOf(observationDetailsDTOObj.getFromChainage()).getBytes()));
		   startChainageProp.setType(String.class);
		   
		   PropertyInfo endChainageProp =new PropertyInfo();
		   endChainageProp.setName("endChainage");
		   endChainageProp.setValue(fixKeyObject.encrypt(String.valueOf(observationDetailsDTOObj.getToChainage()).getBytes()));
		   endChainageProp.setType(String.class);
		   
		   PropertyInfo roadStatusProp =new PropertyInfo();
		   roadStatusProp.setName("roadStatus");
		   
		 //  byte[] roadStatus = scheduleDetailsDTOObj.getRoadStatus().getBytes();
		   
		   String roadStatus = scheduleDetailsDTOObj.getRoadStatus();
		   byte[] rdStatusbyte = null;
		   if(roadStatus.equalsIgnoreCase("LC"))
		   {
			   String rdStatus = "LC";
			   rdStatusbyte =  rdStatus.getBytes();
		   }
		   else if(roadStatus.equalsIgnoreCase("LP"))
			   {
				   String rdStatus = "LP";
				   rdStatusbyte =  rdStatus.getBytes();
			   }
			   else  if(roadStatus.equalsIgnoreCase("LM"))
			   {
				   String rdStatus = "LM";
				   rdStatusbyte =  rdStatus.getBytes();
			   }
			   else
			   {
				    rdStatusbyte = scheduleDetailsDTOObj.getRoadStatus().getBytes();
			   }
		   roadStatusProp.setValue(fixKeyObject.encrypt(rdStatusbyte));
		   roadStatusProp.setType(String.class);
		   
		   PropertyInfo startLatitudeProp =new PropertyInfo();
		   startLatitudeProp.setName("startLatitude");
		   if(observationDetailsDTOObj.getStartLatitude() != null){
		   startLatitudeProp.setValue(fixKeyObject.encrypt(observationDetailsDTOObj.getStartLatitude().getBytes()));
		   }else{
		   startLatitudeProp.setValue(fixKeyObject.encrypt("".getBytes()));   
		   }
		   startLatitudeProp.setType(String.class);
		   
		   PropertyInfo startLongitudeProp =new PropertyInfo();
		   startLongitudeProp.setName("startLongitude");
		   if(observationDetailsDTOObj.getStartLongitude() != null){
		   startLongitudeProp.setValue(fixKeyObject.encrypt(observationDetailsDTOObj.getStartLongitude().getBytes()));
		   }else{
		   startLongitudeProp.setValue(fixKeyObject.encrypt("".getBytes()));	   
		   }
		   startLongitudeProp.setType(String.class);
		   
		   PropertyInfo endLatitudeProp =new PropertyInfo();
		   endLatitudeProp.setName("endLatitude");
		   if(observationDetailsDTOObj.getEndLatitude() != null){
		   endLatitudeProp.setValue(fixKeyObject.encrypt(observationDetailsDTOObj.getEndLatitude().getBytes()));
		   }else{
		   endLatitudeProp.setValue(fixKeyObject.encrypt("".getBytes()));	   
		   }
		   endLatitudeProp.setType(String.class);
		   
		   PropertyInfo endLongitudeProp =new PropertyInfo();
		   endLongitudeProp.setName("endLongitude");
		   if(observationDetailsDTOObj.getEndLongitude() != null){
		   endLongitudeProp.setValue(fixKeyObject.encrypt(observationDetailsDTOObj.getEndLongitude().getBytes()));
		   }else{
		   endLongitudeProp.setValue(fixKeyObject.encrypt("".getBytes()));	   
		   }
		   endLongitudeProp.setType(String.class);
		   
		   PropertyInfo gradeStringProp =new PropertyInfo();
		   gradeStringProp.setName("gradeString");
		   gradeStringProp.setValue(fixKeyObject.encrypt(observationDetailsDTOObj.getGradingParams().getBytes()));
		   gradeStringProp.setType(String.class);
		   
		   //PropertyInfo commentStringProp =new PropertyInfo();
		   //commentStringProp.setName("commentString");
		   //commentStringProp.setValue(fixKeyObject.encrypt(observationDetailsDTOObj.getCommentParams().getBytes()));
		   //commentStringProp.setType(String.class);
		  
		 
		   ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
		   ArrayList<ImageDetailsDTO> imageDetailsDTOArrayList = imageDetailsDAOObj.getImageDetails(uniqueCode,context,parent);
		   
		   Integer attachedImageCount = 0;
			 for (ImageDetailsDTO imageDetailsDTO : imageDetailsDTOArrayList) {
				if(imageDetailsDTO.getIsSelected() == 1){
					attachedImageCount++;
					
				}
				}
			 
			   PropertyInfo imgToBeUploadedCntProp =new PropertyInfo();
			   imgToBeUploadedCntProp.setName("imgToBeUploadedCnt");
			   imgToBeUploadedCntProp.setValue(fixKeyObject.encrypt(String.valueOf(attachedImageCount).getBytes()));
			   imgToBeUploadedCntProp.setType(String.class);
		   
				//Avinash url
				 //Constant.URL="http://10.208.23.180:1000/MABQMS/MABQMS.asmx";  
				 
			   String response =  WebService.callWebService(Constant.URL, Constant.webInsertObservationDetails, scheduleCodeProp, 
					   prRoadCodeProp,
					   inspDateProp,
					   startChainageProp,
					   endChainageProp,
					   roadStatusProp,
					   startLatitudeProp,
					   startLongitudeProp,
					   endLatitudeProp,
					   endLongitudeProp,
					   gradeStringProp,
					   imgToBeUploadedCntProp
						 
						 );
				 
				if(response.length() == 0){
					return -1;
				}
				
				if(response.equalsIgnoreCase("404")){
					return -1;
				}
		        String output = fixKeyObject.decrypt(response);
		        
		        if(output.equalsIgnoreCase("-1")){
		        return -1;
		        }
		        
		        String res[]  = output.split("_");
		        if(res[0].equalsIgnoreCase("1")){
		        	// update obs table
		        	observationDetailsDAOObj.updateObservationDetails(uniqueCode, res[1], context, parent);
		        	result = 1;
		        }
		        
		        
		        return result;
			   
		
	}
}
