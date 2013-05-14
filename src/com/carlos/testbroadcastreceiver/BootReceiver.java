package com.carlos.testbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		context.startService(new Intent("com.carlos.testbroadcastreceiverMyService"));
		Toast.makeText(context, "hello I receive boot", Toast.LENGTH_LONG).show();
	}

}
