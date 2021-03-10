package com.cdac.egov.mmgsy;

import java.util.ArrayList;

import com.cdac.egov.mmgsy.ViewRoadsToAssignActivity.ViewSchduleList;
//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import db.DAO.ScheduleDetailsDAO;
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

public class ViewScheduleActivity extends AppBaseActivity implements OnItemClickListener, OnClickListener {

	private ListView list;
	private ViewPlannedSchduleList adapter;
	private ArrayList<ScheduleDetailsDTO> scheduleDetailsDTOArrayList;
	private ViewScheduleActivity parent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = ViewScheduleActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_schedule);
		
		registerBaseActivityReceiver();
		
		ImageView btnMainMenuLogoutId = (ImageView) findViewById(R.id.btnMainMenuLogoutId);
		btnMainMenuLogoutId.setOnClickListener(this);
		
		ScheduleDetailsDAO scheduleDetailsDAOObj = new ScheduleDetailsDAO();
		
		scheduleDetailsDTOArrayList =  scheduleDetailsDAOObj.getAllScheduleDetails(this, parent,0,"T");
		
		list = (ListView) findViewById(R.id.viewPlannedScheduleList);
		adapter = new ViewPlannedSchduleList(this, R.layout.activity_view_schedule, android.R.layout.simple_list_item_1);
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
	 
 		TextView textViewLocation = new TextView(ViewScheduleActivity.this);
 		textViewLocation.setTextColor(Color.parseColor("#084B8A"));
 		textViewLocation.setTextSize(20);
 		textViewLocation.setText( (position + 1) + ". Location : " + obj.getStateName() + "/" + obj.getDistrictName() + "/" + obj.getBlockName());
		layout.addView(textViewLocation);
		
		TextView roadNameTextView = new TextView(ViewScheduleActivity.this);
		roadNameTextView.setTextColor(Color.parseColor("#084B8A"));
		roadNameTextView.setTextSize(20);
		roadNameTextView.setText("Road/LSB Name : " + obj.getRoadName());
		layout.addView(roadNameTextView);	
		
		TextView roadStatusTextView = new TextView(ViewScheduleActivity.this);
		roadStatusTextView.setTextColor(Color.parseColor("#084B8A"));
		roadStatusTextView.setTextSize(20);
		roadStatusTextView.setText("Status : " + (obj.getRoadStatus().equalsIgnoreCase("C") 
												  ? "Completed" 
												  : obj.getRoadStatus().equalsIgnoreCase("P")
												 	 ? "In Progress"
												 	 : obj.getRoadStatus().equalsIgnoreCase("M")
												 		 ? "Maintenance"
												 				: obj.getRoadStatus().equalsIgnoreCase("LP")
														 		 ? "LSB(Progress)"
												 		 : "LSB(Completed)").toString());
		layout.addView(roadStatusTextView);	
		
		TextView packageIdTextView = new TextView(ViewScheduleActivity.this);
		packageIdTextView.setTextColor(Color.parseColor("#084B8A"));
		packageIdTextView.setTextSize(20);
		packageIdTextView.setText("Package : " + obj.getPackageId());
		layout.addView(packageIdTextView);	
		
		TextView sanctionYearTextView = new TextView(ViewScheduleActivity.this);
		sanctionYearTextView.setTextColor(Color.parseColor("#084B8A"));
		sanctionYearTextView.setTextSize(20);
		sanctionYearTextView.setText("Sanction Year : " + obj.getSanctionYear());
		layout.addView(sanctionYearTextView);	
		
		TextView roadLengthTextView = new TextView(ViewScheduleActivity.this);
		roadLengthTextView.setTextColor(Color.parseColor("#084B8A"));
		roadLengthTextView.setTextSize(20);
		roadLengthTextView.setText("Length : " + obj.getRoadLength() + "km");
		layout.addView(roadLengthTextView);	
		
		if(obj.getRoadStatus().equalsIgnoreCase("C"))
		{
			TextView completionDateTextView = new TextView(ViewScheduleActivity.this);
			completionDateTextView.setTextColor(Color.parseColor("#084B8A"));
			completionDateTextView.setTextSize(20);
			completionDateTextView.setText("Completion Date : " + obj.getCompletionDate() );
			layout.addView(completionDateTextView);	
			
		}
		
	 
		return layout;
	}
	
}

}
