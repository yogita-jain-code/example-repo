package common;

import db.DAO.ConfigDetailsDAO;
import db.DTO.ConfigDetailsDTO;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class QMSServiceReceiver extends BroadcastReceiver {
	private static long pretime = 0;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		long updateTimer = 0;
//		Toast.makeText(context, "Broadcast Intent Detected.",Toast.LENGTH_LONG).show();
			Log.d("onReceive", "UpdateStatusServiceReceiver call");
			if(intent.getAction() != null)
	    	{
	    		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) ||
	    		    intent.getAction().equals(Intent.ACTION_USER_PRESENT))
	    		{
	    			ConfigDetailsDAO configDetailsDAOObj = new ConfigDetailsDAO();
	    			ConfigDetailsDTO configDetailsDTOObj =  configDetailsDAOObj.getConfigDetails(11, context, null);
	    			if(configDetailsDTOObj.getConfigValue() !=null){
	    				updateTimer = Long.parseLong(configDetailsDTOObj.getConfigValue())*60000;
	    			}else{
	    				updateTimer = Constant.updateTimer*60000;
	    			}
	    			
	    			long timeStamp =  System.currentTimeMillis(); //+5*60*1000;
	    			if(pretime == 0){
	    				pretime = timeStamp;
	    				context.startService(new Intent(context, SynMasterData.class));
	    			//	delayCall(context);
	    				
	    				
	    				
	    			}
	    			Log.d("Time diff", String.valueOf((timeStamp - pretime)));
	    			Log.d("updateTimer diff", String.valueOf(updateTimer));
	    			if((timeStamp - pretime) > updateTimer){
	    				pretime = timeStamp;
	    			context.startService(new Intent(context, SynMasterData.class));
	    			//context.startService(new Intent(context, BackgroundImageUpload.class));
	    			//delayCall(context);
	    			}
	    		}
	    	}
		
		
	}
	
	public void delayCall(final Context context){
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		  @Override
		  public void run() {
			
		  }
		}, 1000);
		
		
	}

}
