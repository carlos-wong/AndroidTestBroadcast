package com.carlos.testbroadcastreceiver;



import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	MyThread myThread;
	boolean threadFlag = false;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.v("carlos","service start");
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.v("carlos","on start command");
		doJob();
        return super.onStartCommand(intent, flags, startId);

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
			
			Log.v("carlos","service loop work");

			try{
				Thread.sleep(50);
			}catch(Exception e){
				e.printStackTrace();
			}			 
		 }
	   }	
	}

}
