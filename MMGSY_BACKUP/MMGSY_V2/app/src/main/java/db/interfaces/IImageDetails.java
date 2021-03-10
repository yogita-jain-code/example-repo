package db.interfaces;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import db.DTO.ImageDetailsDTO;


public interface IImageDetails {

	public Boolean setImageDetails(ImageDetailsDTO imageDetailsDTO, Context context, Activity parent);
	public ArrayList<ImageDetailsDTO> getImageDetails(String uniqueCode, Context context,Activity parent);
	public Integer getImageDetailsCount(String uniqueCode, Context context, Activity parent);
	public Boolean updateImageDetails(ImageDetailsDTO imageDetailsDTO, Context context, Activity parent);
	public ArrayList<ImageDetailsDTO> getUploadImageDetailsList(Context context);
	public Boolean emptyImageDetails(Context context);
	public Boolean updateImageDetailsGenratedId(String GenratedId,ImageDetailsDTO imageDetailsDTO, Context context, Activity parent);
	
}
