package common;

import org.acra.*;
import org.acra.annotation.*;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import android.app.Application;

@ReportsCrashes(
		  customReportContent = {  org.acra.ReportField.APP_VERSION_NAME,org.acra.ReportField.APP_VERSION_CODE, org.acra.ReportField.ANDROID_VERSION, org.acra.ReportField.PHONE_MODEL, org.acra.ReportField.DEVICE_ID, org.acra.ReportField.STACK_TRACE, org.acra.ReportField.LOGCAT,  org.acra.ReportField.AVAILABLE_MEM_SIZE}, 
	      formKey = "", // This is required for backward compatibility but not used
	      formUri = "http://ommas.cdac.in/MABQMS/MobileLog",
	      httpMethod = org.acra.sender.HttpSender.Method.POST,
	      reportType = org.acra.sender.HttpSender.Type.JSON,
          mode = ReportingInteractionMode.SILENT,
          socketTimeout = 100000,
          mailTo = "ommashelp@gmail.com"
	  )
public class MyCrashApplication extends Application {

	 @Override
     public void onCreate() {
         super.onCreate();

         // The following line triggers the initialization of ACRA
         ACRA.init(this);
         
        // ACRAPostSender yourSender = new ACRAPostSender();
        // ACRA.getErrorReporter().setReportSender(yourSender);
        // super.onCreate();
     }
}// MyApplication ends here 


