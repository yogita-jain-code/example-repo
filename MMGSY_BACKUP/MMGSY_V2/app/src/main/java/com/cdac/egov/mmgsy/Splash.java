package com.cdac.egov.mmgsy;

import common.Constant;

import android.os.Bundle;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.activity_splash);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
		  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,     WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar

		  setContentView(R.layout.activity_splash);

		  // Start timer and launch main activity
		  IntentLauncher launcher = new IntentLauncher();
		  launcher.start();
		
	}

	private class IntentLauncher extends Thread {
		  @Override
		  /**
		   * Sleep for some time and than start new activity.
		   */
		  public void run() {
		     try {
		        // Sleeping
		        Thread.sleep(Constant.SLEEP_TIME*1000);
		     } catch (Exception e) {
		        Log.e(Constant.tag, e.getMessage());
		     }

		     // Start main activity
		     Intent intent = new Intent(Splash.this, MainActivity.class);
		     Splash.this.startActivity(intent);
		     Splash.this.finish();
		  }
		}

}
