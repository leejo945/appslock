 
package com.lj.appslock.receiver;

import com.lj.appslock.util.Constant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LockScreenReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		    if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
		    	//Constant.isShowDialog.add("");
		    }
             
	}

}
