package db.interfaces;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;

import db.DTO.ConfigDetailsDTO;

public interface IConfigDetails {

	public Boolean updateConfigDetails(ArrayList<ConfigDetailsDTO> configDetailsObj, Context context);
	public ConfigDetailsDTO getConfigDetails(Integer configId, Context context, Activity parent);

}
