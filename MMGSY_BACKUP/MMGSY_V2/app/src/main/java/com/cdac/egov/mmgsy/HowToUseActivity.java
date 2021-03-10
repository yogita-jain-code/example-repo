package com.cdac.egov.mmgsy;


//import com.cdac.nqms.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class HowToUseActivity extends AppBaseActivity implements OnClickListener {
	
	ProgressDialog progressDialog;
	private WebView webView;
	String modeType;

	@Override
    protected void onCreate(Bundle savedInstanceState)
	{

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	      WebView.setWebContentsDebuggingEnabled(true);
		}
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        webView = (WebView) findViewById(R.id.webview_howtouse);
        webView.getSettings().setJavaScriptEnabled(true);
        
        modeType = getIntent().getStringExtra("mode");
        if(modeType.toLowerCase().equalsIgnoreCase("planned"))
        {
        	startWebView("file:///android_asset/www/plannedmode.html");
        }
        else
        {
        	startWebView("file:///android_asset/www/unplannedmode.html");
        }
	}
	
private void startWebView(String url) {
	      
		
		webView.setWebViewClient(new WebViewClient() {      
         
			public boolean shouldOverrideUrlLoading(WebView view, String url) {              
                view.loadUrl(url);
                return true;
            }
            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(HowToUseActivity.this);
                    progressDialog.setTitle("QMS");
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url) {
                try{
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                }catch(Exception exception){
                	exception.printStackTrace();
                }
            }
            
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            	view.loadUrl("file:///android_asset/pageNotFound.html");
            }
        }); 
     
		webView.loadUrl(url);
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void onBackPressed() {
		 if(modeType.toLowerCase().equalsIgnoreCase("planned"))
	        {
			 	Intent intent = new Intent(HowToUseActivity.this,StandardMenuActivity.class);
			 	finish();
				startActivity(intent);
	        }
	        else
	        {
	        	Intent intent = new Intent(HowToUseActivity.this,UnplannedMenuActivity.class);
			 	finish();
				startActivity(intent);
	        }
    }
}
