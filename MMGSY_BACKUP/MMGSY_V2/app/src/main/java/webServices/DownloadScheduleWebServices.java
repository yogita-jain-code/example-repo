package webServices;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.ksoap2.serialization.PropertyInfo;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import common.Constant;
import common.ExceptionHandler;
import common.Notification;
import common.QMSHelper.NotificationEnum;
import common.RijndaelCrypt;
import common.WebService;

import db.DAO.ConfigDetailsDAO;
import db.DAO.LoginDetailsDAO;
import db.DAO.ScheduleDetailsDAO;
import db.DTO.ConfigDetailsDTO;
import db.DTO.LoginDetailsDTO;
import db.DTO.ScheduleDetailsDTO;

public class DownloadScheduleWebServices {

	
	public Boolean downloadSchedule(String userName, Context context, Activity parent){
		 
		 Integer result = 0;
		 RijndaelCrypt fixKeyObject  = new RijndaelCrypt(Constant.fixKey);
		 LoginDetailsDAO loginDAOObject  = new LoginDetailsDAO();
		 LoginDetailsDTO loginDTOObj = loginDAOObject.getLoginDetails(userName, context, parent);
		 

	     PropertyInfo monitorCodeProp =new PropertyInfo();
	     monitorCodeProp.setName("monitorCode");
	     monitorCodeProp.setValue(fixKeyObject.encrypt(loginDTOObj.getQmCode().toString().getBytes()));
	     monitorCodeProp.setType(String.class);
	     
	 	String response =  WebService.callWebService(Constant.URL, Constant.webServiceDownloadSchedule, monitorCodeProp);
	 	String output = fixKeyObject.decrypt(response); 
	 	
		//System.out.println("Prince Paliwal");
	 	//System.out.println("output = " + output);
	 	//Log.v(output, " output" );
		 
	 	if (output != null && !output.isEmpty() && !output.equals("null"))
	 	{
		 	if(output.equalsIgnoreCase("0")){
		 		Notification.showErrorMessage(NotificationEnum.noShedule, context, parent);
		 		return Boolean.FALSE;
		 	}
		 	
			if(output.equalsIgnoreCase("-1")){
		 		Notification.showErrorMessage(NotificationEnum.serverError, context, parent);
		 		return Boolean.FALSE;
		 	}
		}
	 	else
	 	{
	 		Notification.showErrorMessage(NotificationEnum.internetConn, context, parent);
	 		return Boolean.FALSE;
	 	}
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory
					.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource(new StringReader(output));

			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			NodeList nl;

			nl = (NodeList) xPath.evaluate(
					"/ArrayOfUSP_MABQMS_DOWNLOAD_SCHEDULE_Result", is,
					XPathConstants.NODESET);

			Element el = (Element) nl.item(0);
			NodeList table = el
					.getElementsByTagName("USP_MABQMS_DOWNLOAD_SCHEDULE_Result");

			ArrayList<ScheduleDetailsDTO> scheduleDetailsDTOArrayList = new ArrayList<ScheduleDetailsDTO>();
			for (int i = 0; i < table.getLength(); i++) {
				ScheduleDetailsDTO scheduleDetailsDTOObj = new ScheduleDetailsDTO();
				
				Element tableitem = (Element) table.item(i);
				NodeList adminScheduleCodeNodeList = tableitem.getElementsByTagName("ADMIN_SCHEDULE_CODE");
				NodeList imsPrRoadCodeNodeList = tableitem.getElementsByTagName("IMS_PR_ROAD_CODE");
				NodeList schduleMonthNodeList = tableitem.getElementsByTagName("SCHEDULE_MONTH");
				NodeList schduleYearNodeList = tableitem.getElementsByTagName("SCHEDULE_YEAR");
			 	NodeList stateNameNodeList = tableitem.getElementsByTagName("MAST_STATE_NAME");
				NodeList districtNameNodeList = tableitem.getElementsByTagName("MAST_DISTRICT_NAME");
				NodeList blockNameNodeList = tableitem.getElementsByTagName("MAST_BLOCK_NAME");
				NodeList packageIdNodeList = tableitem.getElementsByTagName("IMS_PACKAGE_ID");
				NodeList imsYearNodeList = tableitem.getElementsByTagName("IMS_YEAR");
				NodeList imsRoadNameNodeList  = tableitem.getElementsByTagName("IMS_ROAD_NAME");
				NodeList imsRoadLengthNodeList = tableitem.getElementsByTagName("IMS_PAV_LENGTH");
				NodeList imsIsCompletedNodeList = tableitem.getElementsByTagName("IMS_ISCOMPLETED");
				NodeList completionDateNodeList = tableitem.getElementsByTagName("COMPLETION_DATE");
				NodeList imsPropTypeNodeList = tableitem.getElementsByTagName("IMS_PROPOSAL_TYPE");
				NodeList adminIsEnquiryNodeList = tableitem.getElementsByTagName("ADMIN_IS_ENQUIRY");
				
				//For Schedule Download date 06/10/2017
				NodeList scheduleDownloadDateNodeList = tableitem.getElementsByTagName("SCHEDULE_DOWNLOAD_DATE");
				
				

				Element adminScheduleCodeElement = (Element) adminScheduleCodeNodeList.item(0);
				Element imsPrRoadCodeElement = (Element) imsPrRoadCodeNodeList.item(0);
				Element schduleMonthElement = (Element) schduleMonthNodeList.item(0);
				Element schduleYearElement = (Element) schduleYearNodeList.item(0);
				Element stateNameElement = (Element) stateNameNodeList.item(0);
				Element districtNameElement = (Element) districtNameNodeList.item(0);
				Element blockNameElement = (Element) blockNameNodeList.item(0);
				Element packageIdElement = (Element) packageIdNodeList.item(0);
				Element imsYearElement = (Element) imsYearNodeList.item(0);
				Element imsRoadNameElement = (Element) imsRoadNameNodeList.item(0);
				Element imsRoadLengthElement = (Element) imsRoadLengthNodeList.item(0);
				Element imsIsCompletedElement = (Element) imsIsCompletedNodeList.item(0);
				Element completionDateElement = (Element) completionDateNodeList.item(0);
				Element imsPropTypeElement = (Element) imsPropTypeNodeList.item(0);
				Element adminIsEnquiryElement = (Element) adminIsEnquiryNodeList.item(0);
				
				Element scheduleDownloadDateElement = (Element) scheduleDownloadDateNodeList.item(0);

				scheduleDetailsDTOObj.setUniqueCode((adminScheduleCodeElement.getFirstChild().getNodeValue()+"_"+imsPrRoadCodeElement.getFirstChild().getNodeValue()));
				scheduleDetailsDTOObj.setScheduleMonth(Integer.parseInt(schduleMonthElement.getFirstChild().getNodeValue()));
				scheduleDetailsDTOObj.setScheduleYear(Integer.parseInt(schduleYearElement.getFirstChild().getNodeValue()));
				scheduleDetailsDTOObj.setStateName(stateNameElement.getFirstChild().getNodeValue());
				scheduleDetailsDTOObj.setDistrictName(districtNameElement.getFirstChild().getNodeValue());
				scheduleDetailsDTOObj.setBlockName(blockNameElement.getFirstChild().getNodeValue());
				scheduleDetailsDTOObj.setPackageId(packageIdElement.getFirstChild().getNodeValue());
				scheduleDetailsDTOObj.setSanctionYear(imsYearElement.getFirstChild().getNodeValue());
				scheduleDetailsDTOObj.setRoadName(imsRoadNameElement.getFirstChild().getNodeValue());
				scheduleDetailsDTOObj.setRoadLength(Float.valueOf(imsRoadLengthElement.getFirstChild().getNodeValue()));
				scheduleDetailsDTOObj.setIsEnquiry(adminIsEnquiryElement.getFirstChild().getNodeValue().equalsIgnoreCase("N") ? 0 : 1);
				scheduleDetailsDTOObj.setStatus("T");
				
				if(imsPropTypeElement.getFirstChild().getNodeValue().equalsIgnoreCase("P")){
					scheduleDetailsDTOObj.setRoadStatus(imsIsCompletedElement.getFirstChild().getNodeValue());
					
				}else{
					scheduleDetailsDTOObj.setRoadStatus(imsPropTypeElement.getFirstChild().getNodeValue()+imsIsCompletedElement.getFirstChild().getNodeValue());
				}
				scheduleDetailsDTOObj.setCompletionDate(completionDateElement.getFirstChild().getNodeValue());
				
				scheduleDetailsDTOObj.setScheduleDownloadDate(scheduleDownloadDateElement.getFirstChild().getNodeValue());
				
				scheduleDetailsDTOArrayList.add(scheduleDetailsDTOObj);

			} // for loop ends here
		ScheduleDetailsDAO scheduleDetailsDAO = new ScheduleDetailsDAO();
	 	if(scheduleDetailsDAO.setScheduleDetails(scheduleDetailsDTOArrayList,context, parent) == true){
	 		Notification.showErrorMessage(NotificationEnum.sheduleDownloaded, context, parent);	
	 	}else{
	 		Notification.showErrorMessage(NotificationEnum.errorOccurredShedule, context, parent);
	 	}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
			e.printStackTrace();
		}
		
	 	
		return Boolean.FALSE;
	
	}
}

