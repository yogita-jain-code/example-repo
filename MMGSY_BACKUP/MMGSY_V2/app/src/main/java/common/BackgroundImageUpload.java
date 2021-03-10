package common;

import java.util.ArrayList;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import webServices.UploadImageWebServices;

import db.DAO.ImageDetailsDAO;
import db.DAO.ObservationDetailsDAO;
import db.DAO.ScheduleDetailsDAO;
import db.DTO.ImageDetailsDTO;
import db.DTO.ObservationDetailsDTO;
import db.DTO.ScheduleDetailsDTO;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import androidx.core.app.NotificationCompat;
import android.util.Log;

public class BackgroundImageUpload extends IntentService {

	private static BackgroundImageUpload parent;
	private String mode;
	public BackgroundImageUpload() {
		super("BackgroundImageUpload");
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		 
		ObservationDetailsDAO observationDetailsDAOObj = new ObservationDetailsDAO();
		ArrayList<ObservationDetailsDTO> observationDetailsDTOArrayList = observationDetailsDAOObj.getAllObservationDetails(this);
		ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
		ArrayList<ImageDetailsDTO> imageDetailsDTOArrayList = imageDetailsDAOObj.getUploadImageDetailsList(this);
		ScheduleDetailsDAO scheduleDetailsObj = new ScheduleDetailsDAO();
		
		for (ImageDetailsDTO imageDetailsDTO2 : imageDetailsDTOArrayList) {
		
			ScheduleDetailsDTO scheduleDetailDTOObj = scheduleDetailsObj.getScheduleDetails(imageDetailsDTO2.getUniqueCode(), this);
			if(scheduleDetailDTOObj.getIsGeneratedUniqueId() == 1){
				mode = "unplanned";
			}else{
				mode = "planned";
			}
			UploadImageWebServices uploadImageWebServices = new UploadImageWebServices();
			Integer result = uploadImageWebServices.setImageDetails(imageDetailsDTO2, mode, this);
			
			Log.d(Constant.tag, "Result : "+String.valueOf(result));
			if(result == -1){
				break;
			}
			if(result == -2){
				break;
				// image details saved but image not uploaded to server
			}
		
			
			if(result == 1){
				imageDetailsDTO2.setIsUploaded(1);
				imageDetailsDAOObj.updateImageDetails(imageDetailsDTO2, this, null);
				Log.d(Constant.tag, "Photo Uploaded "+imageDetailsDTO2.getFileName() );
				voiceMsg();
			}
			
			if(result == 2){
				imageDetailsDTO2.setIsUploaded(1);
				imageDetailsDAOObj.updateImageDetails(imageDetailsDTO2, this, null);
				Log.d(Constant.tag, "Photo Uploaded ");
				String details[] =  imageDetailsDTO2.getUniqueCode().split("_");
				Integer id = Integer.parseInt(details[1]);
				displayNotification("PMGSY-OMMS", "Photo Uploaded "+ scheduleDetailDTOObj.getRoadName(),id);
			}
			
			if(result == 9){
				imageDetailsDTO2.setIsUploaded(9);
				imageDetailsDAOObj.updateImageDetails(imageDetailsDTO2, this, null);
				//Log.d(Constant.tag, "Photo Uploaded ");
				String details[] =  imageDetailsDTO2.getUniqueCode().split("_");
				Integer id = Integer.parseInt(details[1]);
				displayNotification("PMGSY-OMMS", "Photo Not Found "+ scheduleDetailDTOObj.getRoadName(),id);
			}
		}
	}
	
	public void displayNotification(String title, String msg, Integer notificationId){
		
		NotificationCompat.Builder mBuilder =
			    new NotificationCompat.Builder(this)
			    .setSmallIcon(R.drawable.ic_launcher)
			    .setContentTitle(title)
			    .setContentText(msg);
		
		// Sets an ID for the notification
		int mNotificationId = notificationId;
		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr = 
		        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
		
	}
	
	
	public void voiceMsg(){
		    MediaPlayer mMediaPlayer = new MediaPlayer();
	        mMediaPlayer = MediaPlayer.create(this, R.raw.imageuploadingpleasewait);
	        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	        mMediaPlayer.setLooping(false);
	        mMediaPlayer.start();
	}
	
	
}
