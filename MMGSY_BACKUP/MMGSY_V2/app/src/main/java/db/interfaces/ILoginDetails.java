package db.interfaces;

import common.ExceptionHandler;

import android.app.Activity;
import android.content.Context;
import db.DTO.LoginDetailsDTO;

public interface ILoginDetails {

	public Boolean setLoginDetails(LoginDetailsDTO loginDetailsObj, Boolean isUpdate,  Context context, Activity parent);
 	public LoginDetailsDTO getLoginDetails(String userName, Context context, Activity parent);
 	public LoginDetailsDTO getLoginDetails(String userName, Context context);
 	public Integer getLoginDetailsRecordsCount(Context context, Activity parent);
	
	
}
