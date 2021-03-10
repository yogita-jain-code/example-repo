package db.interfaces;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import db.DTO.ObservationDetailsDTO;
import db.DTO.ScheduleDetailsDTO;

public interface IObservationDetails {

	public Boolean setObservationDetails(ObservationDetailsDTO observationDetailsObj);
	public ObservationDetailsDTO getObservationDetails(String uniqueCode, Context context, Activity parent); 
	public long setLocationPoint(String uniqueCode, Boolean isStartPoint, String latitude,  String longitude, Context context,  Activity parent );
	public long setObservationDetails(ScheduleDetailsDTO roadDetailsDTO, String inspDate, float fromChainage, float toChainage, ArrayList<String> selectedRadioList, ArrayList<String> commentEditText,  Context context, Activity parent);
	public long updateObservationDetails(String uniqueCode, String obervationId, Context context, Activity parent);
	public ArrayList<ObservationDetailsDTO> getAllObservationDetails(Context context);
	public Boolean emptyObservationDetails(Context context);
	public long updateObservationDetailsUnplanned(String uniqueCode, ObservationDetailsDTO obervationDetailsDTOObj, Context context, Activity parent);
	
}
