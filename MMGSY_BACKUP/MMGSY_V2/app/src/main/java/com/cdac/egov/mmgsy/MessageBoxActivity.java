package com.cdac.egov.mmgsy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.cdac.egov.mmgsy.ViewRoadsToAssignActivity.ViewSchduleList;
//import com.cdac.nqms.R;
import com.cdac.egov.mmgsy.R;

import common.Constant;
import common.QMSHelper;

import db.DAO.QmsNotificationDAO;
import db.DTO.QmsNotificationDTO;
import db.DTO.ScheduleDetailsDTO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MessageBoxActivity extends Activity implements OnItemClickListener{

	private ListView list;
	private ViewMessageList adapter;
	private MessageBoxActivity parent;
	private ArrayList<QmsNotificationDTO> qmsNotificationArrayList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parent = MessageBoxActivity.this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_box);
		
		QmsNotificationDAO qmsNotificationDAOObj = new QmsNotificationDAO();
		qmsNotificationArrayList = qmsNotificationDAOObj.getQmsNotificationDetails(this, parent);
		
		if(qmsNotificationArrayList.size() != 0){
		list = (ListView) findViewById(R.id.viewMessageBoxList);
		adapter = new ViewMessageList(this, R.layout.activity_message_box, android.R.layout.simple_list_item_1);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	// code for arrayadapter class start here
	public class ViewMessageList extends ArrayAdapter<String>{

		private Context context;

		public ViewMessageList(Context context, int resource,int textViewResourceId) {
			super(context, resource, textViewResourceId);
			this.context = context;
		}
		
		@Override
		public int getCount() {
			//Log.d("MyAdapter", "getCount()");
			return qmsNotificationArrayList.size();
			
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.VERTICAL);
		 	QmsNotificationDTO obj = qmsNotificationArrayList.get(position);
		 
	 	 
			
			/*TextView packageIdTextView = new TextView(MessageBoxActivity.this);
			packageIdTextView.setTextColor(Color.parseColor("#084B8A"));
			packageIdTextView.setTextSize(20);
			packageIdTextView.setText("Message Id : " + obj.getMessageId());
			layout.addView(packageIdTextView);	*/
			try{
				
			TextView roadLengthTextView = new TextView(MessageBoxActivity.this);
			roadLengthTextView.setTextColor(Color.parseColor("#084B8A"));
			roadLengthTextView.setTextSize(20);
			roadLengthTextView.setText("Message  : " + obj.getMessageText() );
			layout.addView(roadLengthTextView);	
				
			TextView sanctionYearTextView = new TextView(MessageBoxActivity.this);
			sanctionYearTextView.setTextColor(Color.parseColor("#084B8A"));
			sanctionYearTextView.setTextSize(20);
			
			sanctionYearTextView.setText("Message Date : " + QMSHelper.getDMYDate(obj.getTimeStamp().split("T")[0]));
			layout.addView(sanctionYearTextView);	
			
			
			}catch(Exception e){
				e.printStackTrace();
			}
		 
			return layout;
		}
		
	}// ViewSchduleList class ends here 

}
