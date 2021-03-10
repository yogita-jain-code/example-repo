package db.interfaces;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;

import db.DTO.ScheduleDetailsDTO;

public interface IScheduleDetails {

	public Boolean setScheduleDetails(ArrayList<ScheduleDetailsDTO> scheduleDetailsArrayList, Context context,Activity parent);
	public ScheduleDetailsDTO getScheduleDetails(String uniqueCode, Context context, Activity parent);
	public ArrayList<ScheduleDetailsDTO> getAllScheduleDetails(Context context, Activity parent, Integer entryMode, String status);
	public ScheduleDetailsDTO getGeneratedIds(Context context, Activity parent);
	public ArrayList<ScheduleDetailsDTO> getAllFinalizedGeneratedIds(Context context, Activity parent);
	public ArrayList<ScheduleDetailsDTO> getUnuploadedRecords(Context context, Activity parent);
	public Boolean emptyScheduleDetails(Context context);
	public ScheduleDetailsDTO getScheduleDetails(String uniqueCode, Context context);
	public ArrayList<ScheduleDetailsDTO> getUploadedScheduleDetails(Context context, Activity parent, Integer entryMode);
	
	
}
