package com.cdac.egov.mmgsy;





import java.security.PublicKey;
import java.util.ArrayList;

//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import common.Constant;
import common.Notification;
import common.QMSHelper;
import common.QMSHelper.NotificationEnum;

import db.DAO.ImageDetailsDAO;
import db.DTO.ImageDetailsDTO;
import db.DTO.ScheduleDetailsDTO;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * 
 * 
 * 
 */
public class ImageGalleryActivity extends AppBaseActivity implements OnClickListener{
	private GridView gridView;
	
	private ArrayList<ImageDetailsDTO> imageDetailsDTOArrayList;
	
	private Button nextBtn;
	private int i = 0;
	private Button previousBtn;

	private ImageView imageView;

	private TextView noofImageTextView;

	private TextView imageDescriptionTextView;

	private int j;

	private Button selecImageButton;

	private Button deSelecImageButton;
	
	public Button editDescriptionButton;
	public Button updateDescriptionButton;
	public Button updateDescriptionCancelButton;
	public LinearLayout descriptionUpdateView;
	public EditText descriptionEditText;
	public String tmpDescription;
	
	private ImageGalleryActivity parent;

	private TextView noofImageAttachedTextView;

	private String mode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = ImageGalleryActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_gallery);
		registerBaseActivityReceiver();
		Intent intent = getIntent();
		mode = intent.getStringExtra("mode");
		imageDetailsDTOArrayList = (ArrayList<ImageDetailsDTO>) intent.getSerializableExtra("imageDetailsDTOArrayList");
		imageView = (ImageView) findViewById(R.id.displayImageId);
		nextBtn = (Button) findViewById(R.id.imageFlipperNextBtnId);
        previousBtn = (Button) findViewById(R.id.imageFlipperPrevBtnId);
        noofImageTextView = (TextView) findViewById(R.id.noofImageTextView);
        imageDescriptionTextView = (TextView) findViewById(R.id.imageDescriptionTextView);
        noofImageAttachedTextView = (TextView) findViewById(R.id.noofImageAttachedTextView);
        
        selecImageButton = (Button) findViewById(R.id.selecImageButtonId);
        deSelecImageButton = (Button) findViewById(R.id.deSelecImageButtonId);
        
        editDescriptionButton = (Button) findViewById(R.id.editDescriptionBtnId);
        updateDescriptionButton = (Button) findViewById(R.id.descriptionBtnUpdate);
        updateDescriptionCancelButton = (Button) findViewById(R.id.descriptionBtnCancel);
        
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        
        
        selecImageButton.setOnClickListener(this);
        deSelecImageButton.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        previousBtn.setOnClickListener(this);
        
        editDescriptionButton.setOnClickListener(this);
        updateDescriptionButton.setOnClickListener(this);
        updateDescriptionCancelButton.setOnClickListener(this);
        
        
        
        descriptionUpdateView = (LinearLayout) findViewById(R.id.descriptionUpdateView);
        
        noofImageTextView.setText("Current Photo : 1 / "+imageDetailsDTOArrayList.size());
        setFlipperImage(imageDetailsDTOArrayList.get(0), 0);
        imageDescriptionTextView.setText("Description : "+imageDetailsDTOArrayList.get(0).getDescription());
        
        tmpDescription = imageDetailsDTOArrayList.get(0).getDescription();
        
        displayAttachedImageCount(imageDetailsDTOArrayList);
		/* for(int i=0;i<imageDetailsDTOArrayList.size();i++)
	        {
	        //  This will create dynamic image view and add them to ViewFlipper
	         
	        }*/

	}
	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }
	
	
	private void displayAttachedImageCount(ArrayList<ImageDetailsDTO> imageDetailsDTOArrayList){
	 Integer attachedImageCount = 0;
	 for (ImageDetailsDTO imageDetailsDTO : imageDetailsDTOArrayList) {
		if(imageDetailsDTO.getIsSelected() == 1){
			attachedImageCount++;
			
		}
		noofImageAttachedTextView.setText("No of Photo Attached : "+attachedImageCount+ " / "+imageDetailsDTOArrayList.size());
	}
		
	}
	
	private void setFlipperImage(ImageDetailsDTO imageDetailsDTO, int i ) {
		String urlString;
		if(mode.equalsIgnoreCase("unplanned")){
			 urlString =  Environment.getExternalStorageDirectory()+"/"+ Constant.qmsPath +"/" +Constant.unplannedImagePath+"/"+ imageDetailsDTO.getUniqueCode()  +"/"+imageDetailsDTO.getFileName();
		}else{
		
			 urlString =  Environment.getExternalStorageDirectory()+"/"+ Constant.qmsPath +"/" +Constant.stdImagePath+"/"+ imageDetailsDTO.getUniqueCode()  +"/"+imageDetailsDTO.getFileName();
		}
		
	 //   String urlString =  Environment.getExternalStorageDirectory()+"/"+ Constant.qmsPath +"/" +Constant.stdImagePath+"/"+ imageDetailsDTO.getUniqueCode()  +"/"+imageDetailsDTO.getFileName();
	  //  ImageView image = new ImageView(getApplicationContext());
	//    Bitmap  bitmap = BitmapFactory.decodeFile(urlString);
	//    Uri myUri = Uri.parse(urlString);
	  
	    BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inSampleSize = 6; 
	    Bitmap reduceImage = BitmapFactory.decodeFile(urlString,options);
	    
	    imageView.setImageBitmap(reduceImage);
	    imageDescriptionTextView.setText("Description : "+imageDetailsDTO.getDescription());
	    
	    tmpDescription = imageDetailsDTO.getDescription();
	    
	    
	    
	   if(imageDetailsDTO.getIsSelected() == 0){
		   imageDescriptionTextView.setText("Description : "+imageDetailsDTOArrayList.get(0).getDescription());
		   
		   imageDescriptionTextView.setText("Description:" +tmpDescription);
		   selecImageButton.setVisibility(RelativeLayout.VISIBLE);
		   deSelecImageButton.setVisibility(RelativeLayout.GONE);
	   }else{
		 
		   selecImageButton.setVisibility(RelativeLayout.GONE);
		   deSelecImageButton.setVisibility(RelativeLayout.VISIBLE);
	   }
	   // viewFlipper.addView(image);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 
		if(v.getId() == R.id.selecImageButtonId){
			
			ImageDetailsDTO imageDetailsDTOObj= imageDetailsDTOArrayList.get(i);
					//updateImageDetails
			ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
			imageDetailsDTOObj.setIsSelected(1);
			if(imageDetailsDAOObj.updateImageDetails(imageDetailsDTOObj, this, parent)){
				 selecImageButton.setVisibility(RelativeLayout.GONE);
				   deSelecImageButton.setVisibility(RelativeLayout.VISIBLE);
				   displayAttachedImageCount(imageDetailsDTOArrayList);
			}
					
		}
		
		if(v.getId() == R.id.deSelecImageButtonId){
			
			ImageDetailsDTO imageDetailsDTOObj= imageDetailsDTOArrayList.get(i);
					//updateImageDetails
			ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
			imageDetailsDTOObj.setIsSelected(0);
			if(imageDetailsDAOObj.updateImageDetails(imageDetailsDTOObj, this, parent)){
				 selecImageButton.setVisibility(RelativeLayout.VISIBLE);
				 deSelecImageButton.setVisibility(RelativeLayout.GONE);
				  displayAttachedImageCount(imageDetailsDTOArrayList);
			}
					
		}
		
		if(v.getId() == R.id.imageFlipperNextBtnId){
			descriptionUpdateView.setVisibility(View.GONE);
			editDescriptionButton.setVisibility(View.VISIBLE);
			imageView.refreshDrawableState();
		++i;
		if(i<=imageDetailsDTOArrayList.size()-1){
			previousBtn.setEnabled(true);
			j = i+1;
			noofImageTextView.setText("Current Photo : "+j+" / "+imageDetailsDTOArrayList.size());
			setFlipperImage(imageDetailsDTOArrayList.get(i), i);
			
		}else{
			//Toast.makeText(this,"cannot go next",Toast.LENGTH_SHORT).show();
			previousBtn.setEnabled(true);
			nextBtn.setEnabled(false);
			--i;
		
		}
			//viewFlipper.showNext(); 
		}
       if(v.getId() == R.id.imageFlipperPrevBtnId){
    	   descriptionUpdateView.setVisibility(View.GONE);
    	   editDescriptionButton.setVisibility(View.VISIBLE);
    	   imageView.refreshDrawableState();
       	--i;
       	 if(i >= 0){
       		nextBtn.setEnabled(true);
       		j = i+1;
       		noofImageTextView.setText("Current Photo : "+ j+" / "+imageDetailsDTOArrayList.size());
    	   setFlipperImage(imageDetailsDTOArrayList.get(i), i);
       	 }else{
       		nextBtn.setEnabled(true);
       		previousBtn.setEnabled(false);
       		++i;
       	 }
    	 //  viewFlipper.showPrevious(); 
       }
       
       if(v.getId() == R.id.editDescriptionBtnId){
    	   
    	   descriptionEditText.setText(tmpDescription);
    	   editDescriptionButton.setVisibility(View.GONE);
    	   descriptionUpdateView.setVisibility(View.VISIBLE);
    	   
       }
       
       if(v.getId() == R.id.descriptionBtnUpdate){
    	  
    	
	     ImageDetailsDTO imageDetailsDTOObj= imageDetailsDTOArrayList.get(i);
		 //updateImageDetails
	 	 ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
	 	 
	 	 Boolean valid = checkImageDescriptionValidion();
	 	 if(valid == true)
	 	 {
			 imageDetailsDTOObj.setDescription(descriptionEditText.getText().toString().trim());
			 if(imageDetailsDAOObj.updateImageDetails(imageDetailsDTOObj, this, parent)){
				 Toast.makeText(this,"Image Description Updated !!",Toast.LENGTH_SHORT).show();
				 
				 	Intent intent = getIntent();
				    finish();
				    startActivity(intent);
			 }
	 	 }
    	   
       }
		
       if(v.getId() == R.id.descriptionBtnCancel){
    	   editDescriptionButton.setVisibility(View.VISIBLE);  
    	   descriptionUpdateView.setVisibility(View.GONE);
       }
       
       
	}
	
	public Boolean checkImageDescriptionValidion()
	{
		if(descriptionEditText.getText().toString().length()==0){
			Notification.showErrorMessage(NotificationEnum.emptyDescription, this, parent);
			return Boolean.FALSE;
		}
		
		if(descriptionEditText.getText().toString().length() > 200){
			Notification.showErrorMessage(NotificationEnum.exceedDescriptionLength, this, parent);
			return Boolean.FALSE;
		}
		
		String SpChar[] = {"~","!","@,","#","$","%","^","&","*","\\",":",";","`","(",")","{","}","[","]","<",">","?","+","=","   ","_"};
		for(int i = 0; i<SpChar.length;i++){
		if(descriptionEditText.getText().toString().trim().contains(SpChar[i])){
			Notification.showErrorMessage(NotificationEnum.specialCharDescription, this, parent);
			return Boolean.FALSE;
			
			}
		}
		return true;
	}
	
 /*	private ArrayList<ImageItem> getData() {
		final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
		// retrieve String drawable array
		//TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
		for (int i = 0; i < imageDetailsDTOArrayList.size(); i++) {
			String urlString =  Environment.getExternalStorageDirectory()+"/"+ Constant.qmsPath +"/" +Constant.stdImagePath+"/"+ imageDetailsDTOArrayList.get(i).getUniqueCode()  +"/"+imageDetailsDTOArrayList.get(i).getFileName();
			Bitmap  bitmap = BitmapFactory.decodeFile(urlString); //Creation of Thumbnail of image
			Bitmap imageBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
		//	Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),imgs.getResourceId(i, -1));
			imageItems.add(new ImageItem(imageBitmap, "Image#" + i));
		}

		return imageItems;

	}*/

	
	
	

} // class ends here
