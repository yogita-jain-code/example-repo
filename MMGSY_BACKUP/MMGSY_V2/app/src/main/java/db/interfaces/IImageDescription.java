package db.interfaces;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;

import db.DTO.ConfigDetailsDTO;
import db.DTO.ImageDescriptionDTO;

public interface IImageDescription {

	public Boolean updateImageDescription(ArrayList<ImageDescriptionDTO> ImageDescriptionObj, Context context);
	public ImageDescriptionDTO getImageDescription(Integer descriptionId );
	public ArrayList<String> getAllImageDescription(Context context, Activity parent);
}
