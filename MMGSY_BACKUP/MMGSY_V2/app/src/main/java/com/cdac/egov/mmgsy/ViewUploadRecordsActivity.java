package com.cdac.egov.mmgsy;

import java.util.ArrayList;

import com.cdac.egov.mmgsy.ViewRoadsToAssignActivity.ViewSchduleList;
//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import db.DAO.ImageDetailsDAO;
import db.DAO.ScheduleDetailsDAO;
import db.DTO.ImageDetailsDTO;
import db.DTO.ScheduleDetailsDTO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ViewUploadRecordsActivity extends AppBaseActivity implements OnItemClickListener, OnClickListener {

	private ListView list;
	private ViewPlannedSchduleList adapter;
	private ArrayList<ScheduleDetailsDTO> scheduleDetailsDTOArrayList;
	private ViewUploadRecordsActivity parent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = ViewUploadRecordsActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_uploadedrecords);
		
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
		
		scheduleDetailsDTOArrayList =  scheduleDetailsDAOObj.getUploadedScheduleDetails(this, parent,0);
		
		list = (ListView) findViewById(R.id.viewPlannedScheduleList);
		adapter = new ViewPlannedSchduleList(this, R.layout.activity_view_uploadedrecords, android.R.layout.simple_list_item_1);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		
	}
	
	@Override
    protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_schedule, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
	
	
	public class ViewPlannedSchduleList extends ArrayAdapter<String>{

	private Context context;

	public ViewPlannedSchduleList(Context context, int resource,int textViewResourceId) {
		super(context, resource, textViewResourceId);
		this.context = context;
	}
	
	@Override
	public int getCount() {
		//Log.d("MyAdapter", "getCount()");
		return scheduleDetailsDTOArrayList.size();
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
	 	ScheduleDetailsDTO obj = scheduleDetailsDTOArrayList.get(position);
	 	
	 	ImageDetailsDAO imageDetailsDAOObj = new ImageDetailsDAO();
	 	ArrayList<ImageDetailsDTO> imageDetailsDTOArrayList = imageDetailsDAOObj.getImageDetails(obj.getUniqueCode(), ViewUploadRecordsActivity.this, ViewUploadRecordsActivity.this);
	 	
	 	Integer selectImageCount = 0;
	 	Integer  uploadedImageCount = 0;
	 	Integer  imageNotFoundCount = 0;
	 	
	 	for(int i = 0; i < imageDetailsDTOArrayList.size(); i++){
	 		
	 		if(imageDetailsDTOArrayList.get(i).getIsSelected() == 1){
	 			selectImageCount++;
	 		}
	 		if(imageDetailsDTOArrayList.get(i).getIsUploaded() == 1){
	 			uploadedImageCount++;
	 		}
	 		
	 		if(imageDetailsDTOArrayList.get(i).getIsUploaded() == 9){
	 			imageNotFoundCount++;
	 		}
	 		
	 	}
	 
 		TextView textViewLocation = new TextView(ViewUploadRecordsActivity.this);
 		textViewLocation.setTextColor(Color.parseColor("#084B8A"));
 		textViewLocation.setTextSize(20);
 		textViewLocation.setText( (position + 1) + ". Location : " + obj.getStateName() + "/" + obj.getDistrictName() + "/" + obj.getBlockName());
		layout.addView(textViewLocation);
		
		TextView roadNameTextView = new TextView(ViewUploadRecordsActivity.this);
		roadNameTextView.setTextColor(Color.parseColor("#084B8A"));
		roadNameTextView.setTextSize(20);
		roadNameTextView.setText("Road/LSB Name : " + obj.getRoadName());
		layout.addView(roadNameTextView);	
		
		TextView roadStatusTextView = new TextView(ViewUploadRecordsActivity.this);
		roadStatusTextView.setTextColor(Color.parseColor("#084B8A"));
		roadStatusTextView.setTextSize(20);
		roadStatusTextView.setText("Status : " + (obj.getRoadStatus().equalsIgnoreCase("C") 
												  ? "Completed" 
												  : obj.getRoadStatus().equalsIgnoreCase("P")
												 	 ? "In Progress"
												 	 : obj.getRoadStatus().equalsIgnoreCase("M")
												 		 ? "Maintenance"
												 				: obj.getRoadStatus().equalsIgnoreCase("LC")
														 		 ? "LSB(Completed)"
												 		 : "LSB(In Progress)").toString());
		layout.addView(roadStatusTextView);	
		
		TextView packageIdTextView = new TextView(ViewUploadRecordsActivity.this);
		packageIdTextView.setTextColor(Color.parseColor("#084B8A"));
		packageIdTextView.setTextSize(20);
		packageIdTextView.setText("Package : " + obj.getPackageId());
		layout.addView(packageIdTextView);	
		
		TextView sanctionYearTextView = new TextView(ViewUploadRecordsActivity.this);
		sanctionYearTextView.setTextColor(Color.parseColor("#084B8A"));
		sanctionYearTextView.setTextSize(20);
		sanctionYearTextView.setText("Sanction Year : " + obj.getSanctionYear());
		layout.addView(sanctionYearTextView);	
		
		if(imageNotFoundCount > 0 )
		{
			TextView imageDetailsTextView = new TextView(ViewUploadRecordsActivity.this);
			imageDetailsTextView.setTextColor(Color.parseColor("#084B8A"));
			imageDetailsTextView.setTextSize(20);
			imageDetailsTextView.setText("No of Images uploaded : " + uploadedImageCount + " out of " + selectImageCount + " (" + imageNotFoundCount + " Photo Not Found)");
			layout.addView(imageDetailsTextView);	
		}
		else
		{
			TextView imageDetailsTextView = new TextView(ViewUploadRecordsActivity.this);
			imageDetailsTextView.setTextColor(Color.parseColor("#084B8A"));
			imageDetailsTextView.setTextSize(20);
			imageDetailsTextView.setText("No of Images uploaded : " + uploadedImageCount + " out of " + selectImageCount );
			layout.addView(imageDetailsTextView);	
		}
		
		
		/*if(obj.getRoadStatus().equalsIgnoreCase("C"))
		{
			TextView completionDateTextView = new TextView(ViewUploadRecordsActivity.this);
			completionDateTextView.setTextColor(Color.parseColor("#084B8A"));
			completionDateTextView.setTextSize(20);
			completionDateTextView.setText("Completion Date : " + obj.getCompletionDate() );
			layout.addView(completionDateTextView);	
			
		}*/
		
	 
		return layout;
	}
	
}

}
