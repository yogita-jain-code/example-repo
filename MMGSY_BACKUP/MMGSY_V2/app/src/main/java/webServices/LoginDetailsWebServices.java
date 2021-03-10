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

import com.cdac.egov.mmgsy.MainActivity;
import com.cdac.egov.mmgsy.MainMenuActivity;
//import com.cdac.nqms.R;

import com.cdac.egov.mmgsy.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import common.Constant;
import common.ExceptionHandler;
import common.Notification;
import common.QMSHelper;

import common.QMSHelper.NotificationEnum;
import common.RijndaelCrypt;
import common.WebService;

import db.DAO.LoginDetailsDAO;

import db.DTO.LoginDetailsDTO;

public class LoginDetailsWebServices {

 	
	private PropertyInfo userNameProp;
	private PropertyInfo iemiNoProp;
	
	private String output;
	RijndaelCrypt fixKeyObj = new RijndaelCrypt(Constant.fixKey);
	public void callLoginDetailsWebServices(String userName, String imei, String password,  String loginMode, Boolean isRemember  , Context context, ProgressDialog dialog, Activity parent){
		
		if(userName == null || userName.trim().length() == 0  || password == null || password.trim().length() == 0){
			dialog.dismiss();
			Notification.showErrorMessage(NotificationEnum.emptyUnamePwd, context, parent);
			return;
		}
		
		
		if(loginMode.equalsIgnoreCase("offline")){
			Constant.loginMode = "offline";
			DoLogin(userName, password, context, dialog, parent);
			
		}else{
			if(QMSHelper.isNetworkAvailable(context) == true){
				Constant.loginMode = "online";
			DoOnlineLogin(userName, imei, password, isRemember, context, dialog, parent);
			}else{
				dialog.dismiss();
				Notification.showErrorMessage(NotificationEnum.internetConn, context, parent);
				return;
			}
			
		}//else ends here
		
	}// callLoginDetailsWebServices method ends here
	

	
	/*
	 * Method verifies password with Hash and initiates Login Activity
	 * */
	public void DoLogin(String userName, String password, Context context, ProgressDialog dialog, Activity parent){
		
		LoginDetailsDAO loginDetailsDAOObj = new LoginDetailsDAO();
		LoginDetailsDTO loginDetailsDTOObj = loginDetailsDAOObj.getLoginDetails(userName, context, parent);
		String hashPassword =  RijndaelCrypt.getMD5(password);
		if(loginDetailsDTOObj != null && loginDetailsDTOObj.getPassword().equalsIgnoreCase(hashPassword)){
			// login successful 
			dialog.dismiss();
			Notification.showErrorMessage(NotificationEnum.operationSuccess, context, parent);
			Intent intent = new Intent(context,MainMenuActivity.class);
			intent.putExtra("username", userName);
			context.startActivity(intent);
			parent.finish();
			// and check isRemember me flag is set or not, if set then store in database.
		} else{
			dialog.dismiss();
			final Activity lparent = parent;
			lparent.runOnUiThread(new Runnable() {
			
				public void run() {

					((EditText)lparent.findViewById(R.id.txtUnameId)).setText("");
					((EditText)lparent.findViewById(R.id.txtPasswordId)).setText("");
				    }
				});
			
			Notification.showErrorMessage(NotificationEnum.incorrectUnamePwd, context, parent);
		}
		
	}
	
	
	
	/*
	 * Method accepts all information from server (Online Login)
	 * Inserts or updates data into local database based on First/First-Onwards Logins
	 * Verifies password with Hash and initiates Login Activity
	 * */
	public void DoOnlineLogin(String userName, String imei, String password, Boolean isRemember, Context context,ProgressDialog dialog, Activity parent){
		LoginDetailsDAO loginDAO = new LoginDetailsDAO();
		
	 	Boolean isUpdate = false;
	 	if(loginDAO.getLoginDetailsRecordsCount(context,parent) == 0){
	 		isUpdate = false; }
	 	else{
	 		isUpdate = true;  }
  		
	 		userNameProp = new PropertyInfo();
			userNameProp.setName("userName");
			userNameProp.setValue(fixKeyObj.encrypt(userName.getBytes()));
			userNameProp.setType(String.class);

			iemiNoProp = new PropertyInfo();
			iemiNoProp.setName("imei");
			iemiNoProp.setValue(fixKeyObj.encrypt(imei.trim().getBytes()));
			iemiNoProp.setType(String.class);
			
	 
		String reponse = WebService.callWebService(Constant.URL,
				Constant.webServiceLogin, userNameProp, iemiNoProp);
		
		if(reponse.length() == 3 && reponse.equals("404"))
		{
			dialog.dismiss();
			Notification.showErrorMessage(NotificationEnum.connectionTimeout, context, parent);
			return;
		}
		
		output = fixKeyObj.decrypt(reponse);
		
		/// Verify Login, IMEI and return user details
        /// Returns Status  0 -  User Not Exists
        ///         Status -2 -  User Exists with Invalid IMEI
        ///         Status -1 -  Exception
       
		if(output.equalsIgnoreCase("0")){
			dialog.dismiss();
			final Activity lparent = parent;
			lparent.runOnUiThread(new Runnable() {
			
				public void run() {

					((EditText)lparent.findViewById(R.id.txtUnameId)).setText("");
					((EditText)lparent.findViewById(R.id.txtPasswordId)).setText("");
				    }
				});
			
			Notification.showErrorMessage(NotificationEnum.incorrectUnamePwd, context, parent);
			return;
		}
		if(output.equalsIgnoreCase("-1")){
			dialog.dismiss();
			Notification.showErrorMessage(NotificationEnum.serverError, context, parent);
			return;
		}
		if(output.equalsIgnoreCase("-2")){
			dialog.dismiss();
			Notification.showErrorMessage(NotificationEnum.deviceNotReg, context, parent);
			return;
		}
		
		
	
		Log.d(Constant.tag, output);
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource(new StringReader(output));

			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			NodeList nl;

			nl = (NodeList) xPath.evaluate("/MABQMSModel", is, XPathConstants.NODESET);
 			Element tableitem = (Element) nl.item(0);
 			LoginDetailsDTO loginDTO = new LoginDetailsDTO();


				NodeList userIdNodeList = tableitem.getElementsByTagName("UserId");
				NodeList userNameNodeList = tableitem.getElementsByTagName("UserName");
	//			NodeList roleIdNodeList = tableitem.getElementsByTagName("RoleId");
				NodeList passwordNodeList = tableitem.getElementsByTagName("Password");
				NodeList qmCodeNodeList = tableitem.getElementsByTagName("AdminQmCode");
				NodeList qmTypeNodeList = tableitem.getElementsByTagName("QmType");
				NodeList statusNodeList = tableitem.getElementsByTagName("Status");
				NodeList monitorNameNodeList = tableitem.getElementsByTagName("MonitorName");

				Element userIdElement = (Element) userIdNodeList.item(0);
				Element userNameElement = (Element) userNameNodeList.item(0);
	//			Element roleIdElement = (Element) roleIdNodeList.item(0);	// not in use in use may required in future :) by new developer 
				Element passwordElement = (Element) passwordNodeList.item(0);
				Element qmCodeElement = (Element) qmCodeNodeList.item(0);
				Element qmTypeElement = (Element) qmTypeNodeList.item(0);
				Element statusElement = (Element) statusNodeList.item(0);
				Element monitorNameElement = (Element) monitorNameNodeList.item(0);

				
				loginDTO.setUserId(Integer.parseInt(userIdElement.getFirstChild().getNodeValue()));
				loginDTO.setUserName(userNameElement.getFirstChild().getNodeValue());
				loginDTO.setPassword(passwordElement.getFirstChild().getNodeValue());
				loginDTO.setQmCode(Integer.parseInt(qmCodeElement.getFirstChild().getNodeValue()));
				loginDTO.setQmType(String.valueOf(qmTypeElement.getFirstChild().getNodeValue()));
			//	loginDTO.setUserId(Integer.parseInt(statusElement.getFirstChild().getNodeValue()));
				loginDTO.setMonitorName(monitorNameElement.getFirstChild().getNodeValue());
				
				if(isRemember == true){
					loginDTO.setPasswordWithoutHash(password);
					loginDTO.setIsRemember(1);
				}else{
					loginDTO.setPasswordWithoutHash("");
					loginDTO.setIsRemember(0);
				}
	 
			
			if(loginDAO.setLoginDetails(loginDTO, isUpdate, context, parent) == false){
				dialog.dismiss();
				Notification.showErrorMessage(NotificationEnum.incorrectUnamePwd, context, parent);
			}else{
				//verify user and start menu Activity
				
				DoLogin(userName, password, context, dialog,parent);
				SetLogWebServices setLogObj = new SetLogWebServices();
				//setLogObj.setLog(userName, context, parent);
				
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			dialog.dismiss();
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			dialog.dismiss();
			new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
		}
	}
	
}
