package common;


import common.QMSHelper.NotificationEnum;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Notification {

	public static void showErrorMessage(final NotificationEnum errorCode, Context context,final Activity parent){
		try{	
		
			parent.runOnUiThread(new Runnable() {
				public void run() {
	
					Toast toast = Toast.makeText(parent,errorCode.getValue(), Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				    }
				});
		 
		}catch(Exception e){
			
			e.printStackTrace();
		}
	}
	
}
