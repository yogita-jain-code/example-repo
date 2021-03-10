package common;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import common.QMSHelper.NotificationEnum;

public class WebService {

	 	private static String responseResult;

		public static String callWebService(String url,String methodName, PropertyInfo... propertyInfo){
	 		
	 		String NAMESPACE = Constant.NAMESPACE;
		    String URL = url;
		    String SOAP_ACTION = Constant.NAMESPACE+""+methodName;
		    String METHOD_NAME = methodName;
		   
		    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		    
		    for (PropertyInfo propertyInfoObj : propertyInfo) {
		    	request.addProperty(propertyInfoObj);
			}
		    
	 	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet = true;
	        envelope.setOutputSoapObject(request);
	        if(Constant.isSSL == 1){
	        MyX509TrustManager.allowAllSSL();
	        }
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	        
	        try {
	            androidHttpTransport.call(SOAP_ACTION, envelope);
	            SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
	            responseResult = response.toString();
	        }catch(SocketTimeoutException e){
	        	responseResult = "404";
	        } catch (Exception e) {
				// TODO Auto-generated catch block
	        	responseResult = "404";
				e.printStackTrace();
			}
	      
	 		
		    return responseResult;
	 	} // callWebService method ends here
}
