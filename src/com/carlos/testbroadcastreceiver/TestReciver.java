package com.carlos.testbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class TestReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.v("carlos", "receive message");
		Bundle bundle = intent.getExtras();	// 
		Log.v("carlos", "bundle is: "+bundle);
		String msg = "";
		if(bundle != null)
		{
			Log.v("carlos", "the message is != null");
			Object[] pdusObjects = (Object[]) bundle.get("pdus");
			SmsMessage[] messages = new SmsMessage[pdusObjects.length];
			for(int i = 0; i < pdusObjects.length; i++)
			{
				messages[i] = SmsMessage.createFromPdu((byte[])pdusObjects[i]);
			}
			for(SmsMessage message : messages)
			{
				msg += message.getDisplayOriginatingAddress() + " ---- "+message.getDisplayMessageBody();
				Log.v("carlos", "receiver message: "+message.getDisplayOriginatingAddress() + " ---- "+message.getDisplayMessageBody());
			}
		}
		Intent intent1 = new Intent();
		
		
		intent1.putExtra("message", msg);
		intent1.setAction("android.intent.action.lxx");
		context.sendBroadcast(intent1);

	}

}
