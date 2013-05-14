package com.carlos.testbroadcastreceiver;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater.Filter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Button sendmsg;
	MyReceiver receiver;
	TextView textView;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sendmsg = (Button)findViewById(R.id.button1);
		textView = (TextView)findViewById(R.id.textView1);
		
		 Uri uriSMSURI = Uri.parse("content://sms/inbox");
		 Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
		 String sms ="";
		 
		 
		 String smsData = "";
		 for( int i = 0; i < cur.getColumnCount(); i++) {
			 smsData += "Column: " + i +   cur.getColumnName(i)  + cur.getColumnIndex(cur.getColumnName(i)) +"\n";
		 }
		 Log.v("carlos", smsData);
		 
		 Log.v("carlos", "the message lenghth is: "+cur.getCount());
		 
		 while(cur.moveToNext())
		 {
			 for(int i = 0; i< 20; i++)
				 sms += cur.getString(i)+ "   ";
//			 sms += "From " + cur.getString(5) + " : " + cur.getString(13) + "\n";
			 sms +='\n';
		 }
		 Log.v("carlos", sms);
		 sendmsg.setOnClickListener(new View.OnClickListener() {

			 public void onClick(View v) {
				 Log.v("carlos","send msg button is click");
//		            Intent intent = new Intent();
//		            intent.setAction("com.carlos.test.msg1");
//		            sendBroadcast(intent);
		           
//		            SmsManager sms1 = SmsManager.getDefault();
//		            List<String> texts = sms1.divideMessage("hello carlos");
//		            for (String text : texts) {
//		            		sms1.sendTextMessage("15012704713", null, text, null, null);
//		            }
		            
		           
		        }
		    });

	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(receiver != null)
		{
			this.unregisterReceiver(receiver);
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		receiver = new MyReceiver();
		IntentFilter filter=new IntentFilter();
		filter.addAction("android.intent.action.lxx");
		this.registerReceiver(receiver,filter);
	}
	
	public class MyReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals("android.intent.action.lxx")){
				Bundle bundle = intent.getExtras();
				String message = bundle.getString("message");
				Log.v("carlos", "receive MyReceiver broadcast: "+message);
				textView.setText(message);
				
			}
		 }   
	   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
