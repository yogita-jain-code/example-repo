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

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import common.Constant;
import common.ExceptionHandler;
import common.Notification;
import common.RijndaelCrypt;
import common.WebService;
import common.QMSHelper.NotificationEnum;
import db.DAO.ConfigDetailsDAO;
import db.DAO.QmsNotificationDAO;
import db.DTO.ConfigDetailsDTO;
import db.DTO.QmsNotificationDTO;

public class ConfigDetailsWebServices {

	public void callConfigDetailsWebServices(String username, String imei,Context context) {

		RijndaelCrypt rc = new RijndaelCrypt(Constant.fixKey);
		PropertyInfo userNameProp;
		if(username != null){
		userNameProp = new PropertyInfo();
		userNameProp.setName("userName");
		userNameProp.setValue(rc.encrypt(username.getBytes()));
		userNameProp.setType(String.class);
		}else{
		userNameProp = new PropertyInfo();
		userNameProp.setName("userName");
		userNameProp.setValue(rc.encrypt("".getBytes()));
		userNameProp.setType(String.class);
		}

		PropertyInfo iemiNoProp = new PropertyInfo();
		iemiNoProp.setName("imei");
		iemiNoProp.setValue(rc.encrypt(imei.getBytes()));
		iemiNoProp.setType(String.class);

		String reponse = WebService.callWebService(Constant.fixedURL,
				Constant.webServiceGetConfigDetails, userNameProp, iemiNoProp);
		if(reponse.length() == 3 && reponse.equals("404"))
		{
	 		return;
		}
		
		Log.d(Constant.tag, reponse);
		if (!reponse.equalsIgnoreCase("0")) {
			String output = rc.decrypt(reponse);
			Log.d(Constant.tag, output);
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();

				DocumentBuilder db = dbf.newDocumentBuilder();

				InputSource is = new InputSource(new StringReader(output));

				XPathFactory factory = XPathFactory.newInstance();
				XPath xPath = factory.newXPath();
				NodeList nl;

				nl = (NodeList) xPath.evaluate(
						"/ArrayOfADMIN_MODULE_CONFIGURATION", is,
						XPathConstants.NODESET);

				Element el = (Element) nl.item(0);
				NodeList table = el
						.getElementsByTagName("ADMIN_MODULE_CONFIGURATION");

				ArrayList<ConfigDetailsDTO> ConfigDetailsDTOArrayList = new ArrayList<ConfigDetailsDTO>();
				for (int i = 0; i < table.getLength(); i++) {
					ConfigDetailsDTO configDTO = new ConfigDetailsDTO();

					Element tableitem = (Element) table.item(i);
					NodeList id = tableitem.getElementsByTagName("Id");
					NodeList parameter = tableitem
							.getElementsByTagName("Parameter");
					NodeList value = tableitem.getElementsByTagName("Value");

					Element idElement = (Element) id.item(0);
					Element parameterElement = (Element) parameter.item(0);
					Element valueElement = (Element) value.item(0);

					configDTO.setConfigId(Integer.parseInt(idElement
							.getFirstChild().getNodeValue()));
					configDTO.setConfigParam(parameterElement.getFirstChild()
							.getNodeValue());
					configDTO.setConfigValue(valueElement.getFirstChild()
							.getNodeValue());

					ConfigDetailsDTOArrayList.add(configDTO);

				} // for loop ends here
				ConfigDetailsDAO configDAO = new ConfigDetailsDAO();
				configDAO.updateConfigDetails(ConfigDetailsDTOArrayList,context);

			}catch (Exception e) {
				// TODO Auto-generated catch block
				Log.e(Constant.tag, e.toString());
				//new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
				e.printStackTrace();
			}
			Log.d(Constant.tag, output);
		}
	}
	
	public void DownloadNotificationsWebServices(String username, Context context) {
		
		RijndaelCrypt rc = new RijndaelCrypt(Constant.fixKey);
		PropertyInfo userNameProp;
		
		userNameProp = new PropertyInfo();
		userNameProp.setName("userName");
		userNameProp.setValue(rc.encrypt(username.getBytes()));
		userNameProp.setType(String.class);
		
		String reponse = WebService.callWebService(Constant.URL,Constant.webServiceDownloadNotifications, userNameProp);
		if(reponse.length() != 0 && reponse.equals("404"))
		{
	 		return;
		}
		
		String output = rc.decrypt(reponse);
		
		if(output.equalsIgnoreCase("0")){
			return;
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
					"/ArrayOfQUALITY_QM_NOTIFICATIONS", is,
					XPathConstants.NODESET);

			Element el = (Element) nl.item(0);
			NodeList table = el
					.getElementsByTagName("QUALITY_QM_NOTIFICATIONS");

			ArrayList<QmsNotificationDTO> qmsNotificationDTOArrayList = new ArrayList<QmsNotificationDTO>();
			for (int i = 0; i < table.getLength(); i++) {
				QmsNotificationDTO qmsNotificationDTOObj = new QmsNotificationDTO();

				Element tableitem = (Element) table.item(i);
				NodeList userIdNodeList = tableitem.getElementsByTagName("USER_ID");
				NodeList messsageIdNodeList = tableitem.getElementsByTagName("MESSAGE_ID");
				NodeList messageTextNodeList = tableitem.getElementsByTagName("MESSAGE_TEXT");
				NodeList messageTypeNodeList = tableitem.getElementsByTagName("MESSAGE_TYPE");
				NodeList isDownloadNodeList = tableitem.getElementsByTagName("IS_DOWNLOAD");
				NodeList timeStampNodeList = tableitem.getElementsByTagName("TIME_STAMP");

				Element userIdElement = (Element) userIdNodeList.item(0);
				Element messsageIdElement = (Element) messsageIdNodeList.item(0);
				Element messageTextElement = (Element) messageTextNodeList.item(0);
				Element messageTypeElement = (Element) messageTypeNodeList.item(0);
				Element isDownloadElement = (Element) isDownloadNodeList.item(0);
				Element timeStampElement = (Element) timeStampNodeList.item(0);

				qmsNotificationDTOObj.setUserId(Integer.parseInt(userIdElement.getFirstChild().getNodeValue()));
				qmsNotificationDTOObj.setMessageId(Integer.parseInt(messsageIdElement.getFirstChild().getNodeValue()));
				qmsNotificationDTOObj.setMessageText(messageTextElement.getFirstChild().getNodeValue());
				qmsNotificationDTOObj.setMessageType(messageTypeElement.getFirstChild().getNodeValue());
				
				Integer isDownload = 0;
				if(isDownloadElement.getFirstChild().getNodeValue().equalsIgnoreCase("true")){
					isDownload = 1;
				}else{
					isDownload = 0;
				}
				
				qmsNotificationDTOObj.setIsDownload(isDownload);
				qmsNotificationDTOObj.setTimeStamp(timeStampElement.getFirstChild().getNodeValue());

				qmsNotificationDTOArrayList.add(qmsNotificationDTOObj);
				displayNotification("PMGSY-OMMS", "Message : "+ qmsNotificationDTOObj.getMessageText(),qmsNotificationDTOObj.getMessageId(), context);
			} // for loop ends here
			QmsNotificationDAO  qmsNotificationDAO = new QmsNotificationDAO();
			if(qmsNotificationDTOArrayList.size() != 0 ){
				qmsNotificationDAO.setQmsNotificationDetails(qmsNotificationDTOArrayList, context);
			}
			

		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(Constant.tag, e.toString());
			
			e.printStackTrace();
		}
		
		
	}
	
public void displayNotification(String title, String msg, Integer notificationId, Context context){
		
		NotificationCompat.Builder mBuilder =
			    new NotificationCompat.Builder(context)
			    .setSmallIcon(R.drawable.ic_launcher)
			    .setContentTitle(title)
			    .setContentText(msg);
		
		// Sets an ID for the notification
		int mNotificationId = notificationId;
		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
		
	}
	
}

