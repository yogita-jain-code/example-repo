package common;

import common.QMSHelper.NotificationEnum;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class ExceptionHandler extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExceptionHandler(Exception ex , NotificationEnum errorCode, Context context, Activity parent){
		
		Log.e(Constant.tag, ex.toString());
		Notification.showErrorMessage(errorCode, context, parent);
	}

}
