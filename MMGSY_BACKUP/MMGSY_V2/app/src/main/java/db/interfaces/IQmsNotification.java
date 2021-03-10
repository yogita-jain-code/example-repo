package db.interfaces;

import java.util.ArrayList;

import db.DTO.QmsNotificationDTO;

import android.app.Activity;
import android.content.Context;



public interface IQmsNotification {

	public ArrayList<QmsNotificationDTO> getQmsNotificationDetails(Context context, Activity parent);
	public Boolean setQmsNotificationDetails(ArrayList<QmsNotificationDTO> QmsNotificationDetailsArrayList,Context context);
	public Boolean emptyNotificationDetails(Context context);
	
}
