package common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.regex.Matcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdac.egov.mmgsy.MainActivity;
import com.cdac.egov.mmgsy.MainMenuActivity;
import com.cdac.egov.mmgsy.StandardMenuActivity;
//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import db.DAO.ImageDetailsDAO;
import db.DAO.ObservationDetailsDAO;
import db.DTO.ImageDetailsDTO;
import db.DTO.ScheduleDetailsDTO;

public class QMSHelper {

	public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
           // Log.e(tag, ex.toString());
        }
        return "";
    }
	
	public enum NotificationEnum{
		unhandledException(R.string.E100),
		serverError(R.string.E101),
        serviceNotAvailable(R.string.E102),
		emptyUnamePwd(R.string.E103),
		incorrectUnamePwd(R.string.E104),
		deviceNotReg(R.string.E105),
		internetConn(R.string.E106),
		noShedule(R.string.E107),
		sheduleDownloaded(R.string.E108),
		errorOccurredShedule(R.string.E109),
		GPSoff(R.string.E110),
		GPSon(R.string.E111),
		pointNotSet(R.string.E112),
		startPointAlreadySet(R.string.E113),
		endPointAlreadySet(R.string.E114),
		startPointSetSuccessfully(R.string.E115),
		endPointSetSuccessfully(R.string.E116),
		selectRoad(R.string.E117),
		selectDescription(R.string.E118),
		emptyDescription(R.string.E119),
		exceedDescriptionLength(R.string.E120),
		specialCharDescription(R.string.E121),
		maximumNoOfPhotos(R.string.E122),
		photoSave(R.string.E123),
		photoNotSave(R.string.E124),
		waitForCurrentLoc(R.string.E125),
		enterFromChainage(R.string.E126),
		enterToChainage(R.string.E127),
		fromChainageGreaterZero(R.string.E128),
		toChainageLessThnTotalLen(R.string.E129),
		toChainageGreaterThnFromChainage(R.string.E130),
		enterValidFromChainage(R.string.E131),
		enterValidToChainage(R.string.E132),
		observationDetailSaved(R.string.E133),
		errorObservationDetailSaved(R.string.E134),
		errorGeneratingNewId(R.string.E135),
		plzGenerateId(R.string.E136),
		finalizePrevEntry(R.string.E137),
		noEntryToFinalize(R.string.E138),
		errorInFinalize(R.string.E139),
		entryFinalized(R.string.E140),
		minImagesRequiredToFinalize(R.string.E141),
		newGeneratedId(R.string.E142),
		plzSelectGeneratedId(R.string.E143),
		emptyPackageId(R.string.E144),
		exceedPackageId(R.string.E145),
		specialCharPackageId(R.string.E146),
		noFinalizedEnryToMap(R.string.E147),
		roadMappedWithGenId(R.string.E148),
		errorMappingGeneratedId(R.string.E149),
		uploadingStarted(R.string.E150),
		EnterPassword(R.string.E151),
		incorrectPassword(R.string.E152),
		resetSuccess(R.string.E153),
		unplannedAssignRoadsuccess(R.string.E154),
		unplannedAssignRoadfail(R.string.E155),
		commentEditText(R.string.E156),
		scheduleExpired(R.string.E157),
		
		operationSuccess(R.string.E500),
		connectionTimeout(R.string.E404);

        private final int value;

        private NotificationEnum(final int newValue) {
            value = newValue;
        }
        
        public int getValue() { return value; }
        
	}// NotificationEnum ends here
	

	public static Boolean isNetworkAvailable(Context mycontent) {

		boolean haveConnectedMobile = false;
		

		try {
			ConnectivityManager cm = (ConnectivityManager) mycontent
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (cm.getActiveNetworkInfo() != null) {
				NetworkInfo netinfo = cm.getActiveNetworkInfo();
				if (netinfo.getState() == NetworkInfo.State.CONNECTED) {
					haveConnectedMobile = true;
				}
			}
		} catch (Exception e) {

			Log.e("AppUtility", e.toString());

		}

		return haveConnectedMobile;
	} // isNetworkAvailable function ends here
	
	
	public static void callHelpLine(final Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	
		builder.setMessage("Are sure to call CDAC for help ?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        		Log.d("NQMS", "Call to CDAC");
		        		String number = "tel:"+Constant.helpLineNo;
		    	        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number)); 
		    	        context.startActivity(callIntent);
		    			
		    			
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		
		AlertDialog alert = builder.create();
		alert.show();
	}
	

	public static void blink(View v){
		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(100); //You can manage the time of the blink with this parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(3);
		v.startAnimation(anim);
	}// blink
	
	public static void setInpsectionFormDetails(Activity activity, String monitorName,ScheduleDetailsDTO roadDetailsDTO, Context context,Activity parent){
		
		 	TextView monitorNameId = (TextView)  activity.findViewById(R.id.gradingFormMonitorNameIdTxtView);
	        TextView uniqueNoId = (TextView) activity.findViewById(R.id.gradingFormUniqueNoIdTxtView);
	        TextView roadNameId = (TextView) activity.findViewById(R.id.gradingFormRoadNameIdTxtView);
	        TextView roadStatusId = (TextView) activity.findViewById(R.id.gradingFormRoadStatusIdTxtView);
	        TextView packageId = (TextView) activity.findViewById(R.id.gradingFormPackageIdIdTxtView);
	        TextView sanctionYearId = (TextView) activity.findViewById(R.id.gradingFormSanctionYearIdTxtView);
	        TextView roadLengthId = (TextView) activity.findViewById(R.id.gradingFormRoadLengthIdTxtView);
	        TextView inspDateId = (TextView) activity.findViewById(R.id.gradingFormInspDateIdTxtView);
	        TextView compDateId = (TextView) activity.findViewById(R.id.gradingFormCompDateIdTxtView);
	        TextView stateDistBlockId = (TextView) activity.findViewById(R.id.gradingFormStateDistBlockIdTxtView);
	        TextView isEnquiryId = (TextView) activity.findViewById(R.id.gradingFormIsEnquiryIdTxtView);
	        
	        
	        monitorNameId.setText("Monitor : "+monitorName);
	        uniqueNoId.setText("Unique No : "+roadDetailsDTO.getUniqueCode());
	        roadNameId.setText("Road / LSB : "+roadDetailsDTO.getRoadName());
	        
	        String status = (roadDetailsDTO.getRoadStatus().equalsIgnoreCase("c") 
					?  "Completed" 
					: 	roadDetailsDTO.getRoadStatus().equalsIgnoreCase("p") 
						? "In Progress"
						: roadDetailsDTO.getRoadStatus().equalsIgnoreCase("m") 
						  ? "Maintenance"
								  : roadDetailsDTO.getRoadStatus().equalsIgnoreCase("LC") 
								  ? "LSB(Completed)"
						  : "LSB(In Progress)"		  );	 
	        
	        
	        roadStatusId.setText("Status : "+status);
	        packageId.setText("Package : "+roadDetailsDTO.getPackageId());
	        sanctionYearId.setText("Sanction Year : "+roadDetailsDTO.getSanctionYear()+"-"+(Integer.parseInt(roadDetailsDTO.getSanctionYear())+1));
	        roadLengthId.setText("Length : "+roadDetailsDTO.getRoadLength() + " Km ");
	        
	        //Calendar c = Calendar.getInstance();
			//SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
	        //String currentDateTime = df.format(c.getTime());
	     // Change on 19-June-2014 start here
	        ImageDetailsDAO imageDetailsDAO = new ImageDetailsDAO();
			ArrayList<ImageDetailsDTO> imageDetailsDTOArrayList =  imageDetailsDAO.getImageDetails(roadDetailsDTO.getUniqueCode(), parent, parent);
			String currentDateTime =  imageDetailsDTOArrayList.get(0).getCaptureDateTime();
			// Change on 18-June-2014 ends here
	        
	        
	        inspDateId.setText("Inpsection Date : "+ currentDateTime);
	        
	        if(roadDetailsDTO.getRoadStatus().equalsIgnoreCase("c")){
	        	compDateId.setVisibility(LinearLayout.VISIBLE);
	        	compDateId.setText("Completion Date : "+roadDetailsDTO.getCompletionDate().replace(" ", "-"));
	        }else{
	        	compDateId.setVisibility(LinearLayout.GONE);
	        }
	        
	        stateDistBlockId.setText("Location : "+roadDetailsDTO.getStateName() + " / "+ roadDetailsDTO.getDistrictName() + " / "+ roadDetailsDTO.getBlockName());
	        isEnquiryId.setText(roadDetailsDTO.getIsEnquiry() == 1 ? "Enquiry : Yes" : "Enquiry : No");
	}
	
	
public static Boolean validateChainage(Activity parent, ScheduleDetailsDTO roadDetailsDTO){
		
	EditText fromLength = (EditText)  parent.findViewById(R.id.gradingFormFromChainageIdEditText);
	EditText toLength = (EditText) parent.findViewById(R.id.gradingFormToChainageIdEditText);
	
	
		if(fromLength.getText().toString().length() == 0 ){
			Notification.showErrorMessage(NotificationEnum.enterFromChainage, parent.getApplicationContext() , parent);
			QMSHelper.blink(fromLength);
			return Boolean.FALSE;
		}
		
		if( toLength.getText().toString().length() == 0){
			Notification.showErrorMessage(NotificationEnum.enterToChainage, parent.getApplicationContext() , parent);
			QMSHelper.blink(toLength);
			return Boolean.FALSE;
		}
		
		if(Float.parseFloat(fromLength.getText().toString()) <=  -1.0 ){
			Notification.showErrorMessage(NotificationEnum.fromChainageGreaterZero, parent.getApplicationContext() , parent);
			QMSHelper.blink(fromLength);
			return Boolean.FALSE;
		}
		
		if(Float.parseFloat(toLength.getText().toString()) >  roadDetailsDTO.getRoadLength()){
			Notification.showErrorMessage(NotificationEnum.toChainageLessThnTotalLen, parent.getApplicationContext() , parent);
			QMSHelper.blink(toLength);
			return Boolean.FALSE;
		}
		
		if(Float.parseFloat(toLength.getText().toString()) <  Float.parseFloat(fromLength.getText().toString()) ){
			Notification.showErrorMessage(NotificationEnum.toChainageGreaterThnFromChainage, parent.getApplicationContext() , parent);
			QMSHelper.blink(fromLength);
			return Boolean.FALSE;
		}
		
		String pattern = "^[0-9]{1,3}(\\.[0-9]{1,3})?$";
		
		//String pattern = "[0-9]*\\.?[0-9]+";
		if(!fromLength.getText().toString().matches(pattern)){
			Notification.showErrorMessage(NotificationEnum.enterValidFromChainage, parent.getApplicationContext() , parent);
			QMSHelper.blink(fromLength);
			return Boolean.FALSE;
		}
		
		if(!toLength.getText().toString().matches(pattern)){
			Notification.showErrorMessage(NotificationEnum.enterValidToChainage, parent.getApplicationContext() , parent);
			QMSHelper.blink(fromLength);
			return Boolean.FALSE;
		}
		
		
		
		
		return Boolean.TRUE;
	}


public static String getDMYDate(String str)

{

if (str == null || str.trim().equals("") || str.trim().length() < 10)

{

return str;

}

String reverseStr = "";

reverseStr = str.substring(8,10);

reverseStr = reverseStr+ '-';

reverseStr = reverseStr + str.substring(5,7);

reverseStr = reverseStr+ '-';

reverseStr = reverseStr + str.substring(0,4);

return reverseStr;

}

	
}
